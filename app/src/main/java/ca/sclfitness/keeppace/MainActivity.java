package ca.sclfitness.keeppace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    final String sentTextGrind = "GRIND PACE";
    final String sentTextRace = "RACE PACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToGrind(View v) {
        Intent grindPaceIntent = new Intent(this, GrindActivity.class);

        grindPaceIntent.putExtra("paceType", sentTextGrind);
        startActivity(grindPaceIntent);
    }

    public void sendToRace(View v) {
        Intent racePaceIntent = new Intent(this, GrindActivity.class);

        racePaceIntent.putExtra("paceType", sentTextRace);
        startActivity(racePaceIntent);
    }
}
