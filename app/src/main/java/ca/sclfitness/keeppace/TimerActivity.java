package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private int counter = 0;
    private boolean isPause = false;

    // race views
    private Button pauseResumeBtn;
    private TextView currentTimeView, estimatedTimeView, currentSpeedView, beatTimeLabel, beatTimeView;
    // race
    private Race race;
    // timer
    private Handler handler;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L;
    //private int seconds, minutes, milliSeconds = 0;
    // marker buttons
    private Button markerBtn;

    private int mode = 0;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            int seconds = (int) (updateTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliSeconds = (int) (updateTime / 10) % 100;
            currentTimeView.setText(String.format(Locale.getDefault(), "%02d:%02d.%02d", minutes, seconds, milliSeconds));
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

        Intent intent = getIntent();
        boolean beatTime = intent.getBooleanExtra("beatTime", false);
        String raceName = intent.getStringExtra("raceType");
        double raceDistance = intent.getDoubleExtra("raceDistance", 0.0);

        if (raceName.equalsIgnoreCase("Grouse Grind")) {
            mode = 1;
        } else {
            mode = 0;
        }

        if (beatTime) {
            beatTimeLabel.setVisibility(View.VISIBLE);
            beatTimeView.setVisibility(View.VISIBLE);
        }

        // create a race
        race = new Race(raceName, raceDistance);

        // timer handler
        handler = new Handler();
    }

    public void onClickPauseResume(View v) {
        if (!isPause) {
            timeBuff += millisecondTime;
            handler.removeCallbacks(runnable);
            markerBtn.setEnabled(false);
            isPause = true;
            pauseResumeBtn.setText(getResources().getString(R.string.timer_resume));
        } else {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            markerBtn.setEnabled(true);
            isPause = false;
            pauseResumeBtn.setText(getResources().getString(R.string.timer_pause));
        }
    }

    public void onClickMarker(View v) {
        if (counter <= race.getMakers()) {
            if (counter == 0) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                counter++;
                pauseResumeBtn.setEnabled(true);
                pauseResumeBtn.setVisibility(View.VISIBLE);
                isPause = false;
            } else {
                double currentPace = race.getCurrentPace(counter, updateTime, mode);
                currentSpeedView.setText(String.format(Locale.getDefault(), "%.2f " + getResources().getString(R.string.pace_unit)
                        , currentPace * 1000.0 * 60.0 * 60.0));
                if (counter == race.getMakers()) {
                    timeBuff += millisecondTime;
                    handler.removeCallbacks(runnable);
                    markerBtn.setEnabled(false);
                    markerBtn.setVisibility(View.INVISIBLE);
                    pauseResumeBtn.setEnabled(false);
                    pauseResumeBtn.setVisibility(View.INVISIBLE);
                } else {
                    long timeInMilli = race.getEstimateTime(currentPace);
                    int secs = (int) (timeInMilli / 1000);
                    int min = (secs / 60);
                    secs = secs % 60;
                    int msecs = (int) (timeInMilli / 10) % 100;
                    estimatedTimeView.setText(String.format(Locale.getDefault(), "%02d:%02d.%02d", min, secs, msecs));
                    counter++;
                }
            }
            markerBtn.setText(race.getMarkerName(counter, mode));
        }
    }
}
