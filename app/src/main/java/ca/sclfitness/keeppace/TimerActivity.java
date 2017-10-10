package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private int markerCurrent = 1;

    // pace settings
    private boolean beatTime;
    private String raceName;
    private double raceDistance;
    // race views
    private Button startBtn;
    private TextView currentTimeView, estimatedTimeView, currentSpeedView, beatTimeLabel, beatTimeView;
    // race
    private Race race;
    // timer
    private Handler handler;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L;
    private int seconds, minutes, milliSeconds = 0;
    // marker buttons
    private Button markerBtn;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime / 10) % 100;
            currentTimeView.setText(String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds) + "."
                    + String.format("%02d", milliSeconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // views
        startBtn = (Button) findViewById(R.id.button_timer_start);
        currentTimeView = (TextView) findViewById(R.id.textView_timer_currentTime);
        estimatedTimeView = (TextView) findViewById(R.id.textView_timer_estimatedTime);
        currentSpeedView = (TextView) findViewById(R.id.textView_timer_speed);
        beatTimeLabel = (TextView) findViewById(R.id.textView_timer_beatTimeLabel);
        beatTimeView = (TextView) findViewById(R.id.textView_timer_beatTime);
        markerBtn = (Button) findViewById(R.id.button_timer_marker);

        Intent intent = getIntent();
        beatTime = intent.getBooleanExtra("beatTime", false);
        raceName = intent.getStringExtra("raceName");
        raceDistance = intent.getDoubleExtra("raceDistance", 0.0);

        if (beatTime) {
            beatTimeLabel.setVisibility(View.VISIBLE);
            beatTimeView.setVisibility(View.VISIBLE);
        }

        // create a race
        race = new Race(raceName, raceDistance);

        // timer handler
        handler = new Handler();
    }

    public void onClickStart(View v) {
        startBtn.setVisibility(View.GONE);
        currentTimeView.setVisibility(View.VISIBLE);
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        startBtn.setEnabled(false);
        final Handler makerHandler = new Handler();
        makerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                markerBtn.setEnabled(true);
            }
        }, 2000);
    }

    public void advanceMarker(View v) {
        double currentPace = race.getCurrentPace(markerCurrent, updateTime);
        currentSpeedView.setText(String.format("%.2f", currentPace * 1000.0 * 60.0 * 60.0) + " Km/h");

        if (markerBtn.getText().equals("Finish")) {
            timeBuff += millisecondTime;
            handler.removeCallbacks(runnable);
            finish();
        } else {
            long timeInMilli = race.getEstimateTime(currentPace);
            int secs = (int) (timeInMilli / 1000);
            int min = (secs / 60);
            secs = secs % 60;
            int msecs = (int) (timeInMilli / 10) % 100;
            estimatedTimeView.setText(String.format("%02d", min) + ":"
                    + String.format("%02d", secs) + "."
                    + String.format("%02d", msecs));
            if (markerCurrent > race.getMakers() - 1) {
                markerBtn.setText("Finish");
            } else {
                markerBtn.setText(markerCurrent + "KM");
            }
            markerCurrent++;
        }
    }
}
