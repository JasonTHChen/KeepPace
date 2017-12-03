package ca.sclfitness.keeppace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ca.sclfitness.keeppace.Dao.RaceDao;
import ca.sclfitness.keeppace.Dao.RecordDao;
import ca.sclfitness.keeppace.model.FullCrunch;
import ca.sclfitness.keeppace.model.Race;
import ca.sclfitness.keeppace.model.Record;

import static android.view.View.GONE;

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
    private Button pauseResumeBtn, saveBtn, startBtn, resetBtn;

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

    /**
     * Create timer activity
     *
     * @param savedInstanceState - save current state
     */
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
        resetBtn = (Button) findViewById(R.id.button_timer_reset);
        startBtn = (Button) findViewById(R.id.button_timer_start);
        saveBtn = (Button) findViewById(R.id.button_timer_save);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView_timer_markers);


        //scrollView.setScroll

        Intent intent = getIntent();
        beatTime = intent.getBooleanExtra("beatTime", false);
        String raceName = intent.getStringExtra("raceType");

        // setup race
        this.raceSetup(raceName);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_title) + " - " + raceName);
        }

        // Get unit preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String unit = sharedPreferences.getString(getString(R.string.key_unit), "1");
        if (unit.equals("2")) {
            currentSpeedView.append(" " + getString(R.string.pace_mile_per_hr));
            race.setUnit("mile");
        } else {
            currentSpeedView.append(" " + getString(R.string.pace_km_per_hr));
        }



        // make markers scroll view based on the race
        if (race.getName().equals(FullCrunch.FULL_CRUNCH)) {
            this.makeCrunchMarkers();
        } else {
            this.makeMarkers();
        }

        if (beatTime) {
            beatTimeLabel.setVisibility(View.VISIBLE);
            beatTimeView.setVisibility(View.VISIBLE);
            Record record = race.getBestRecord();
            if (record != null) {
                beatTimeView.setText(race.timeTextFormat(record.getTime()));
            }
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
            RecordDao recordDao = new RecordDao(TimerActivity.this);
            List<Record> records = recordDao.findRecordsByRaceId(race.getId());
            if (records != null) {
                Log.d(TAG, records.size() + " records found");
                race.setRecords(records);
            } else {
                Log.d(TAG, "No Record is found");
            }
            recordDao.close();
        } else {
            Log.d(TAG, raceName + " not found");
        }
    }

    /**
     * Start the timer if the race is not finished
     */
    private void startTimer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (!isFinished) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            startBtn.setVisibility(GONE);
            isPaused = false;

            String mode = sharedPreferences.getString(getString(R.string.key_mode), "1");
            if (mode.equals("2")) {
                pauseResumeBtn.setVisibility(GONE);
                pauseResumeBtn.setEnabled(false);
                resetBtn.setEnabled(true);
            } else {
                pauseResumeBtn.setEnabled(true);
                pauseResumeBtn.setVisibility(View.VISIBLE);
                resetBtn.setEnabled(true);
            }
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

    public void onClickReset(View v) {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }

    /**
     * Set on click for the marker buttons
     *
     * @param distance - current marker
     */
    public void onClickMarker(int distance) {
        // find current pace
        double currentPace = race.getCurrentPace(distance, updateTime);
        double pace = currentPace * 1000.0 * 60.0 * 60.0;
        if (race.getUnit().equals("mile")) {
            currentSpeedView.setText(String.format(Locale.getDefault(), "%.2f " + getString(R.string.pace_mile_per_hr)
                    , race.convertToMile(pace)));
        } else {
            currentSpeedView.setText(String.format(Locale.getDefault(), "%.2f " + getString(R.string.pace_km_per_hr)
                    , pace));
        }

        if (distance == race.getMarkers()) {
            // finish
            resetBtn.setVisibility(View.GONE);
            resetBtn.setEnabled(false);
            BigDecimal bd = new BigDecimal(pace);
            bd = bd.setScale(2, RoundingMode.FLOOR);
            pace = bd.doubleValue();
            race.setAveragePace(pace);
            isFinished = true;
            scrollView.setVisibility(GONE);
            pauseTimer();
        } else {
            // increment marker
            long estimateTime = race.getEstimateTime(currentPace);
            if (race.getTime() != 0 && beatTime) {
                if (estimateTime < race.getTime()) {
                    Toast.makeText(this, "Great Job! Keep Up Your Pace...", Toast.LENGTH_SHORT).show();
                    estimatedTimeView.setTextColor(Color.GREEN);
                } else {
                    Toast.makeText(this, "Pick Up Your Pace!", Toast.LENGTH_SHORT).show();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(800);
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
        long finishTime = updateTime;
        race.setAveragePace(pace);
        race.setTime(finishTime);
    }

    /**
     * Save button
     *
     * @param v - view object
     */
    public void onClickSave(View v) {

        final AlertDialog alertDialog = new AlertDialog.Builder(TimerActivity.this).create();
        alertDialog.setTitle("Saving Log");
        alertDialog.setMessage("Would you like to save the race?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveRace();
                final RecordDao recordDao = new RecordDao(TimerActivity.this);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MMM. dd, yyyy", Locale.CANADA);
                String currentDate = df.format(c.getTime());
                final Record record = new Record(race.getAveragePace(), race.getTime(), currentDate, race.getId());
                if (race.addRecord(record)) {
                    recordDao.insert(record);
                } else {
                    dialog.dismiss();
                    AlertDialog warnDialog = new AlertDialog.Builder(TimerActivity.this).create();
                    warnDialog.setTitle("Saving Log");
                    warnDialog.setMessage("This will overwrite your previous records. Would you like to continue?");
                    warnDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recordDao.update(race.getWorstRecord().getId(), record);
                            recordDao.close();
                        }
                    });

                    warnDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recordDao.close();
                        }
                    });
                    warnDialog.show();
                }
                TimerActivity.this.finish();
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
    private void makeMarkers() {
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

    /**
     * Handle special case for crunch buttons.
     */
    public void makeCrunchMarkers() {
        final LinearLayout markers = (LinearLayout) findViewById(R.id.linearLayout_timer_markers);
        for (int id = 0; id <= race.getMarkers() + 1; id++) {
            final Button markerBtn = new Button(this);
            markerBtn.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            if (id == 0 || id == (race.getMarkers() + 1)) {
                markerBtn.setBackground(getResources().getDrawable(R.drawable.bottom_button));
                markerBtn.setVisibility(View.INVISIBLE);
                markerBtn.setEnabled(false);
            } else {
                markerBtn.setId(id);
                switch (id) {
                    case 1:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.two));
                        break;
                    case 2:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.three));
                        break;
                    case 3:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.four));
                        break;
                    case 4:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.five));
                        break;
                    case 5:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.six));
                        break;
                    case 6:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.seven));
                        break;
                    case 7:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.eight));
                        break;
                    default:
                        markerBtn.setBackground(getResources().getDrawable(R.drawable.nine));
                }
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