package ca.sclfitness.keeppace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    public int markerCurrent = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
    }

    public void hideButton(View v) {
        final Button startBtn = (Button) findViewById(R.id.button_timer_start);
        final TextView timeView = (TextView) findViewById(R.id.textView_timer_currentTime);
        startBtn.setVisibility(View.GONE);
        timeView.setVisibility(View.VISIBLE);
    }

    public void advanceMarker(View v) {
        final Button left = (Button) findViewById(R.id.button_timer_markerLeft);
        final Button markerBtn = (Button) findViewById(R.id.button_timer_marker);
        final Button right = (Button) findViewById(R.id.button_timer_markerRight);
        if (markerCurrent > 20) {
            markerBtn.setText("Finish");
            right.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
        } else {
            markerBtn.setText(markerCurrent + "KM");
            left.setText((markerCurrent - 1) + "KM");
            right.setText((markerCurrent + 1) + "KM");
            markerCurrent++;
        }
    }
}
