package ca.sclfitness.keeppace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingBuffer();
    }

    public void loadingBuffer() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        SplashActivity.this.finish();
                    }
                },
                5000
        );
    }
}
