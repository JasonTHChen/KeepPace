package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        int type = (Integer) getIntent().getExtras().get("paceType");
        final TextView bestTimeLabelView = (TextView) findViewById(R.id.textView_timer_BestTimeLabel);
        final TextView bestTimeView = (TextView) findViewById(R.id.textView_timer_BestTime);

        switch (type) {
            case 0:
                break;
            case 1:
                bestTimeLabelView.setVisibility(View.VISIBLE);
                bestTimeView.setVisibility(View.VISIBLE);
            break;
            default:
                break;
        }
    }



    public void onHiddenBtn(View v) {
        final Button startBtn = (Button) findViewById(R.id.button_timer_start);
        final TextView timeView = (TextView) findViewById(R.id.textView_timer_currentTime);
        startBtn.setVisibility(View.GONE);
        timeView.setVisibility(View.VISIBLE);
    }
}
