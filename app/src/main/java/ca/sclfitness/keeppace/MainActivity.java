package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final String sentTextGrind = "JUST GRIND";
    final String sentTextRace = "JUST RACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
    }

    public void sendToGrind(View v) {
        Intent grindPaceIntent = new Intent(this, PaceActivity.class);

        grindPaceIntent.putExtra("paceType", sentTextGrind);
        grindPaceIntent.putExtra("type", 0);
        startActivity(grindPaceIntent);
    }

    public void sendToRace(View v) {
        Intent racePaceIntent = new Intent(this, PaceActivity.class);
        racePaceIntent.putExtra("paceType", sentTextRace);
        racePaceIntent.putExtra("type", 1);
        startActivity(racePaceIntent);
    }
}
