package ca.sclfitness.keeppace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import ca.sclfitness.keeppace.Dao.RaceDao;
import ca.sclfitness.keeppace.model.Race;

public class UserLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        setRaceList();
    }

    public void setRaceList() {
        final ListView listView = (ListView) findViewById(R.id.listView_userLog_raceList);
        RaceDao raceDao = new RaceDao(UserLogActivity.this);
        List<Race> races = raceDao.findAllRaces();
        UserLogAdapter adapter = new UserLogAdapter(UserLogActivity.this, races);
        listView.setAdapter(adapter);
    }
}
