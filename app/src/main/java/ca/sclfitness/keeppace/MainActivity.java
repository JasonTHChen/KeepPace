package ca.sclfitness.keeppace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.userLog_action:
                Intent i = new Intent(MainActivity.this, UserLogActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
