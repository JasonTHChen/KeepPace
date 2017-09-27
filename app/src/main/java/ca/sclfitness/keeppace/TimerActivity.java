package ca.sclfitness.keeppace;

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
    }

    protected void onHiddenBtn(View v) {
        final Button startBtn = (Button) findViewById(R.id.button_timer_start);
        final TextView timeView = (TextView) findViewById(R.id.textView_timer_currentTime);
        startBtn.setVisibility(View.GONE);
        timeView.setVisibility(View.VISIBLE);
    }
}
