package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace);

        final TextView grindLabel = (TextView) findViewById(R.id.btn_grind_paceType);
        final Button paceTypeBtn = (Button) findViewById(R.id.btn_grind_paceType);
        Intent i = getIntent();
        int type = (Integer) getIntent().getExtras().get("type");
        String title = i.getStringExtra("paceType");
        grindLabel.setText(title);
    }

   public void onJustClick(View v) {
       Intent timerIntent;
        if ((Integer) getIntent().getExtras().get("type") == 1) {
            timerIntent = new Intent(this, RaceActivity.class);
        } else {
            timerIntent = new Intent(this, TimerActivity.class);
        }
        timerIntent.putExtra("paceType", 0);
        startActivity(timerIntent);
    }

    public void onBeatClick(View v) {
        Intent timerIntent;
        if ((Integer) getIntent().getExtras().get("type") == 1) {
            timerIntent = new Intent(this, RaceActivity.class);
        } else {
            timerIntent = new Intent(this, TimerActivity.class);
        }
        timerIntent.putExtra("paceType", 1);
        startActivity(timerIntent);
    }
}
