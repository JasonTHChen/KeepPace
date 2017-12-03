package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PaceActivity extends AppCompatActivity {

    private int paceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace);

        final Button raceTypeBtn = (Button) findViewById(R.id.btn_pace_paceType);
        Intent i = getIntent();
        paceType = i.getIntExtra("type", -1);

        switch (paceType) {
            case 0:
                // Grind Pace
                raceTypeBtn.setText(R.string.pace_grind);
                break;
            case 1:
                // Race Pace
                raceTypeBtn.setText(R.string.pace_race);
                break;
            case 2:
                raceTypeBtn.setText(getString(R.string.pace_crunch));
                break;
            case 3:
                raceTypeBtn.setText(getString(R.string.pace_steps));
                break;
            default:
                System.err.println("passing error code " + paceType);
                finish();
        }

    }

    public void onJustClick(View v) {
        beatTimeIntent(false);
    }

    public void onBeatClick(View v) {
        beatTimeIntent(true);
    }

    /**
     * pass information to next intent
     * depend on which arguments is passed, it will show different intent
     *
     * @param beatTime - true if user select beat your best time
     */
    private void beatTimeIntent(boolean beatTime) {
        Intent intent = null;
        if (paceType == 0) {
            intent = new Intent(this, TimerActivity.class);
            intent.putExtra("raceType", "Grouse Grind");
        } else if (paceType == 1) {
            intent = new Intent(this, RaceActivity.class);
        } else if (paceType == 2 ) {
            intent = new Intent(this, TimerActivity.class);
            intent.putExtra("raceType", "Full Crunch");
        } else if (paceType == 3) {
            intent = new Intent(this, TimerActivity.class);
            intent.putExtra("raceType", "437 Steps");
        } else {
            System.err.println("passing error code " + paceType);
            finish();
        }

        if (intent != null) {
            intent.putExtra("beatTime", beatTime);
            startActivity(intent);
        }
    }
}
