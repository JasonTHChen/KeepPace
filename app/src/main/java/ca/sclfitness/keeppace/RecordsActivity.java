package ca.sclfitness.keeppace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import java.util.List;

import ca.sclfitness.keeppace.Dao.RecordDao;
import ca.sclfitness.keeppace.model.Record;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        ListView recordList = (ListView) findViewById(R.id.listView_records_recordList);
        int raceId = getIntent().getIntExtra("raceId", -1);
        if (raceId > 0) {
            RecordDao recordDao = new RecordDao(this);
            List<Record> records = recordDao.findRecordsByRaceId(raceId);
            recordDao.close();
            if (records != null) {
                RecordAdapter adapter = new RecordAdapter(this, records);
                recordList.setAdapter(adapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.record_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
