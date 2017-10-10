package ca.sclfitness.keeppace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        ((TextView) findViewById(R.id.getStarted)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.getStarted)).setText(Html.fromHtml(getResources().getString(R.string.TOS)));
    }

    public void onStart(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
