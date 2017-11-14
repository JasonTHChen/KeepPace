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
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import ca.sclfitness.keeppace.Dao.RaceDao;
import ca.sclfitness.keeppace.model.Race;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG = TimerActivity.class.getSimpleName();

    private int counter = 0;
    private boolean isPaused = false;
    private boolean isFinished = false;
    private boolean beatTime = false;

    // race views
    private TextView currentTimeView, estimatedTimeView, currentSpeedView, beatTimeLabel, beatTimeView;
    // race
    private Race race;
    // timer
    private Handler handler;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L;
    // buttons
    private Button pauseResumeBtn, markerBtn, saveBtn;

    private int mode = 0;

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
        markerBtn = (Button) findViewById(R.id.button_timer_marker);
        saveBtn = (Button) findViewById(R.id.button_timer_save);

        Intent intent = getIntent();
        beatTime = intent.getBooleanExtra("beatTime", false);
        String raceName = intent.getStringExtra("raceType");
        this.raceSetup(raceName);

        if (beatTime) {
            beatTimeLabel.setVisibility(View.VISIBLE);
            beatTimeView.setVisibility(View.VISIBLE);
            beatTimeView.setText(race.timeTextFormat(race.getBestTime()));
        }

        // timer handler
        handler = new Handler();
    }

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
            markerBtn.setEnabled(false);
            markerBtn.setVisibility(View.GONE);
            pauseResumeBtn.setEnabled(false);
            pauseResumeBtn.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setEnabled(true);
        }
    }


    public void onClickPauseResume(View v) {
        if (!isPaused) {
            pauseTimer();
            pauseResumeBtn.setText(getResources().getString(R.string.timer_resume));
        } else {
            startTimer();
            pauseResumeBtn.setText(getResources().getString(R.string.timer_pause));
        }
        isFinished = false;
        markerBtn.setEnabled(!isPaused);
    }

    public void onClickMarker(View v) {
        if (counter <= race.getMarkers()) {
            if (counter == 0) {
                // Hit start
                startTimer();
                counter++;
            } else {
                // find current pace
                double currentPace = race.getCurrentPace(counter, updateTime);
                currentSpeedView.setText(String.format(Locale.getDefault(), "%.2f " + getResources().getString(R.string.pace_unit)
                        , currentPace * 1000.0 * 60.0 * 60.0));
                if (counter == race.getMarkers()) {
                    // finish
                    isFinished = true;
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
                    counter++;
                }
            }
            markerBtn.setText(race.getMarkerName(counter));
        }
    }

    /**
     * Store finish time and average pace
     */
    private void saveRace() {
        double pace = race.getCurrentPace(counter, updateTime) * 1000.0 * 60.0 * 60.0;
        BigDecimal bd = new BigDecimal(pace);
        bd = bd.setScale(2, RoundingMode.FLOOR);
        pace = bd.doubleValue();
        long finishTime = updateTime;
        race.setAveragePace(pace);
        race.setBestTime(finishTime);
    }

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
}
