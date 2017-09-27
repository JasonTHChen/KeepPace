package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final String sentTextGrind = "GRIND PACE";
    final String sentTextRace = "RACE PACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToGrind(View v) {
        Intent grindPaceIntent = new Intent(this, PaceActivity.class);

        grindPaceIntent.putExtra("paceType", sentTextGrind);
        startActivity(grindPaceIntent);
    }

    public void sendToRace(View v) {
        Intent racePaceIntent = new Intent(this, PaceActivity.class);

        racePaceIntent.putExtra("paceType", sentTextRace);
        startActivity(racePaceIntent);
    }
}
