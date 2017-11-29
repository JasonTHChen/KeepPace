package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CrunchPaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunch_pace);
    }

    public void onClickFullCrunch(View v) {
        Intent i = new Intent(this, PaceActivity.class);
        i.putExtra("type", 2);    // grind pace
        startActivity(i);
    }

    public void onClickStairCrunch(View v) {
        Intent i = new Intent(this, PaceActivity.class);
        i.putExtra("type", 3);    // crunch pace
        startActivity(i);
    }
}
