package ca.sclfitness.keeppace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
    }

    public void onClickFive(View v) {
        startTimerIntent("5K");
    }

    public void onClickTen(View v) {
        startTimerIntent("10K");
    }

    public void onClickHalfMarathon(View v) {
        startTimerIntent("Half Marathon");
    }

    public void onClickFullMarathon(View v) {
        startTimerIntent("Full Marathon");
    }

    /**
     * Start timer activity and pass arguments for creating a race
     *
     * @param raceType - name of the race
     */
    private void startTimerIntent(String raceType) {
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("raceType", raceType);
        timerIntent.putExtra("beatTime", getIntent().getBooleanExtra("beatTime", false));
        startActivity(timerIntent);
    }
}
