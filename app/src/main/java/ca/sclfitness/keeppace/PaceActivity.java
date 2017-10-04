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

        final TextView grindLabel = (TextView) findViewById(R.id.textView_grind_title);
        final Button paceTypeBtn = (Button) findViewById(R.id.btn_grind_paceType);
        Intent i = getIntent();
        String title = i.getStringExtra("paceType");

        grindLabel.setText(title);

        if (title.equals("GRIND PACE")) {
            paceTypeBtn.setText("JUST GRIND");
        } else {
            paceTypeBtn.setText("JUST RACE");
        }
    }

    public void onJustClick(View v) {
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("paceType", "0");
        startActivity(timerIntent);
    }

    public void onBeatClick(View v) {
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent.putExtra("paceType", "1");
        startActivity(timerIntent);
    }
}
