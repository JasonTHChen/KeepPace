package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickGrind(View v) {
        Intent grindPaceIntent = new Intent(this, PaceActivity.class);

        grindPaceIntent.putExtra("type", 0);    // grind pace
        startActivity(grindPaceIntent);
    }

    public void onClickRace(View v) {
        Intent racePaceIntent = new Intent(this, PaceActivity.class);
        racePaceIntent.putExtra("type", 1);     // race pace
        startActivity(racePaceIntent);
    }
}
