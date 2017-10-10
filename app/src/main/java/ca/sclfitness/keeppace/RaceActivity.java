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

        int type = (Integer) getIntent().getExtras().get("paceType");
        Intent i = getIntent();
    }

    public void onFiveClick(View v) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("runType", 0);
        timerIntent.putExtra("paceType", (Integer) getIntent().getExtras().get("paceType"));
        startActivity(timerIntent);
    }

    public void onTenClick(View v) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("runType", 1);
        timerIntent.putExtra("paceType", (Integer) getIntent().getExtras().get("paceType"));
        startActivity(timerIntent);
    }

    public void onHalfMarathonClick(View v) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("runType", 2);
        timerIntent.putExtra("paceType", (Integer) getIntent().getExtras().get("paceType"));
        startActivity(timerIntent);
    }
    public void onFullMarathonClick(View v) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("runType", 3);
        timerIntent.putExtra("paceType", (Integer) getIntent().getExtras().get("paceType"));
        startActivity(timerIntent);
    }
}
