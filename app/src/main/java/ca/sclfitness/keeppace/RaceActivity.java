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
        startTimerIntent("5k", 5);
    }

    public void onClickTen(View v) {
        startTimerIntent("10k", 10);
    }

    public void onClickHalfMarathon(View v) {
        startTimerIntent("Half Marathon", 21.1);
    }

    public void onClickFullMarathon(View v) {
        startTimerIntent("Full Marathon", 42.2);
    }

    private void startTimerIntent(String raceType, double raceDistance) {
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("raceType", raceType);
        timerIntent.putExtra("raceDistance", raceDistance);
        timerIntent.putExtra("beatTime", getIntent().getBooleanExtra("beatTime", false));
        startActivity(timerIntent);
    }
}
