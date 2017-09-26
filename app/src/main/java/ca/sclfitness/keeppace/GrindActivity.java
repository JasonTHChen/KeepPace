package ca.sclfitness.keeppace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GrindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grind);

        final TextView grindLabel = (TextView) findViewById(R.id.textView_grind_title);
        final Button paceTypeBtn = (Button) findViewById(R.id.btn_grind_paceType);
        Intent i = getIntent();
        String title = i.getStringExtra("paceType");

        grindLabel.setText(title);

        if (title.equals("GRIND PACE")) {
            paceTypeBtn.setText("JUST GRIND");
        } else {
            paceTypeBtn.setText("JUST RACE");
        }
    }
}
