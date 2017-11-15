package ca.sclfitness.keeppace;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import ca.sclfitness.keeppace.Dao.RaceDao;
import ca.sclfitness.keeppace.model.Race;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG = TimerActivity.class.getSimpleName();

    // Pause check
    private boolean isPaused = false;

    // Finish Check
    private boolean isFinished = false;

    // Beat your best mode toggle
    private boolean beatTime = false;

    // race views
    private TextView currentTimeView, estimatedTimeView, currentSpeedView, beatTimeLabel, beatTimeView;
    // race
    private Race race;
    // timer
    private Handler handler;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L;
    // buttons
    private Button pauseResumeBtn, saveBtn, startBtn;

    // markers scroll view
    private HorizontalScrollView scrollView;

    // Timer thread
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            currentTimeView.setText(race.timeTextFormat(updateTime));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // views
        currentTimeView = (TextView) findViewById(R.id.textView_timer_currentTime);
        estimatedTimeView = (TextView) findViewById(R.id.textView_timer_estimatedTime);
        currentSpeedView = (TextView) findViewById(R.id.textView_timer_pace);
        beatTimeLabel = (TextView) findViewById(R.id.textView_timer_beatTimeLabel);
        beatTimeView = (TextView) findViewById(R.id.textView_timer_beatTime);
        pauseResumeBtn = (Button) findViewById(R.id.button_timer_pauseResume);
        startBtn = (Button) findViewById(R.id.button_timer_start);
        saveBtn = (Button) findViewById(R.id.button_timer_save);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView_timer_markers);

        Intent intent = getIntent();
        beatTime = intent.getBooleanExtra("beatTime", false);
        String raceName = intent.getStringExtra("raceType");

        // setup race
        this.raceSetup(raceName);

        // make markers scroll view based on the race
        this.makeMarkers();

        if (beatTime) {
            beatTimeLabel.setVisibility(View.VISIBLE);
            beatTimeView.setVisibility(View.VISIBLE);
            beatTimeView.setText(race.timeTextFormat(race.getBestTime()));
        }

        // timer handler
        handler = new Handler();
    }

    /**
     * Setup race object and initialize it
     *
     * @param raceName - name of the race
     */
    private void raceSetup(String raceName) {
        RaceDao raceDao = new RaceDao(TimerActivity.this);
        race = raceDao.findRaceByName(raceName);
        raceDao.close();
        if (race != null) {
            Log.d(TAG, "Found " + raceName);
        } else {
            Log.d(TAG, raceName + " not found");
        }
    }

    /**
     * Start the timer if the race is not finished
     */
    private void startTimer() {
        if (!isFinished) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            pauseResumeBtn.setEnabled(true);
            pauseResumeBtn.setVisibility(View.VISIBLE);
            startBtn.setVisibility(View.GONE);
            isPaused = false;
        }
    }

    /**
     * Pause the timer
     */
    private void pauseTimer() {
        timeBuff += millisecondTime;
        handler.removeCallbacks(runnable);
        isPaused = true;
        if (isFinished) {
            pauseResumeBtn.setEnabled(false);
            pauseResumeBtn.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setEnabled(true);
        }
    }

    /**
     * Pause/Resumes the timer
     *
     * @param v - view object
     */
    public void onClickPauseResume(View v) {
        if (!isPaused) {
            pauseTimer();
            pauseResumeBtn.setText(getResources().getString(R.string.timer_resume));
        } else {
            startTimer();
            pauseResumeBtn.setText(getResources().getString(R.string.timer_pause));
        }
        isFinished = false;
    }

    /**
     * Set on click for the marker buttons
     *
     * @param distance - current marker
     */
    public void onClickMarker(int distance) {
        // find current pace
        distance += 1;
        double currentPace = race.getCurrentPace(distance, updateTime);
        currentSpeedView.setText(String.format(Locale.getDefault(), "%.2f " + getResources().getString(R.string.pace_unit)
                , currentPace * 1000.0 * 60.0 * 60.0));
        if (distance == race.getMarkers()) {
            // finish
            race.setAveragePace(currentPace);
            isFinished = true;
            scrollView.setVisibility(View.GONE);
            pauseTimer();
        } else {
            // increment marker
            long estimateTime = race.getEstimateTime(currentPace);
            if (race.getBestTime() != 0 && beatTime) {
                if (estimateTime < race.getBestTime()) {
                    estimatedTimeView.setTextColor(Color.GREEN);
                } else {
                    estimatedTimeView.setTextColor(Color.RED);
                }
            }

            estimatedTimeView.setText(race.estimateTimeText(currentPace));
        }
    }

    /**
     * Store finish time and average pace
     */
    private void saveRace() {
        double pace = race.getAveragePace();
        BigDecimal bd = new BigDecimal(pace);
        bd = bd.setScale(2, RoundingMode.FLOOR);
        pace = bd.doubleValue();
        long finishTime = updateTime;
        race.setAveragePace(pace);
        race.setBestTime(finishTime);
    }

    /**
     * Save button
     *
     * @param v - view object
     */
    public void onClickSave(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(TimerActivity.this).create();
        alertDialog.setTitle("Saving Log");
        alertDialog.setMessage("This will overwrite your previous records. Would you like to continue?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveRace();
                RaceDao raceDao = new RaceDao(TimerActivity.this);
                raceDao.update(race);
                raceDao.close();
                finish();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * Start button
     *
     * @param v - view object
     */
    public void onClickStart(View v) {
        // Hit start
        scrollView.setVisibility(View.VISIBLE);
        startTimer();
    }

    /**
     * Create markers
     */
    public void makeMarkers() {
        final LinearLayout markers = (LinearLayout) findViewById(R.id.linearLayout_timer_markers);
        for (int id = 0; id <= race.getMarkers() + 1; id++) {
            final Button markerBtn = new Button(this);
            markerBtn.setText(race.getMarkerName(id));
            markerBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            if (id == 0 || id == (race.getMarkers() + 1)) {
                markerBtn.setBackground(getResources().getDrawable(R.drawable.bottom_button));
                markerBtn.setVisibility(View.INVISIBLE);
                markerBtn.setEnabled(false);
            } else {
                markerBtn.setId(id);
                markerBtn.setBackground(getResources().getDrawable(R.drawable.bottom_button));
                markerBtn.setTextColor(Color.WHITE);
                markerBtn.setTextSize(30);
            }

            markerBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onClickMarker(markerBtn.getId());
                    scrollView.smoothScrollBy(markerBtn.getWidth(), 0);
                }
            });
            markers.addView(markerBtn);
        }


    }
}
