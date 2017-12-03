package ca.sclfitness.keeppace;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sclfitness.keeppace.Dao.RecordDao;
import ca.sclfitness.keeppace.model.Record;

public class RecordsActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private RecordAdapter adapter;
    private List<Record> records;
    private ListView recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordList = (ListView) findViewById(R.id.listView_records_recordList);
        int raceId = getIntent().getIntExtra("raceId", -1);
        String raceName = getIntent().getStringExtra("raceName");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_title) + " - " + raceName);
        }

        if (raceId > 0) {
            RecordDao recordDao = new RecordDao(this);
            records = recordDao.findRecordsByRaceId(raceId);
            recordDao.close();
            if (records != null) {
                adapter = new RecordAdapter(this, records);
                recordList.setAdapter(adapter);
            } else {
                records = new ArrayList<>();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.record_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.upload_action);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionProvider(recordFormat());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                final AlertDialog alertDialog = new AlertDialog.Builder(RecordsActivity.this).create();
                alertDialog.setTitle("Clear Records");
                alertDialog.setMessage("Would you like to clear all records?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RecordDao recordDao = new RecordDao(RecordsActivity.this);
                        for (Record r : records) {
                            recordDao.delete(r.getId());
                        }
                        recordDao.close();
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setShareActionProvider(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(i);
    }

    private String recordFormat() {
        String raceName = getIntent().getStringExtra("raceName");
        String recordsShareText = raceName + "\n";
        for (Record record : records) {
            String header = "DATE";
            String temp = record.getDate() + "  " + record.getAveragePace() + "km  " + record.timeTextFormat(record.getTime()) + "\n";
            recordsShareText += temp;
        }

        return recordsShareText;
    }
}
