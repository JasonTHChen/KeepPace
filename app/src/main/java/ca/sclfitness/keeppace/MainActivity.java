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
        startActivity(new Intent(this, SplashActivity.class));
    }

    /**
     * Create options menu
     *
     * @param menu - Menu object
     * @return true if any option menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Listen which menu item is selected.
     *
     * @param item - MenuItem object.
     * @return true if menu item is found.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.userLog_action:
                intent = new Intent(MainActivity.this, UserLogActivity.class);
                startActivity(intent);
                return true;
            case R.id.setting_action:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
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

    public void onClickCrunch(View v) {
        Intent i = new Intent(this, CrunchPaceActivity.class);
        startActivity(i);
    }

    public void onClickRace(View v) {
        Intent racePaceIntent = new Intent(this, PaceActivity.class);
        racePaceIntent.putExtra("type", 1);     // race pace
        startActivity(racePaceIntent);
    }
}
