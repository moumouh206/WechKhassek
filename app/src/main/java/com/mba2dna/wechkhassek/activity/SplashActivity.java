package com.mba2dna.wechkhassek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.mba2dna.wechkhassek.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends ActionBarActivity {
    long Delay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  Window.setStatusBarColor(R.color.primaryColorDark);
        Toast.makeText(SplashActivity.this,Locale.getDefault().getLanguage(),Toast.LENGTH_LONG).show();
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                finish();

                // Start MainActivity.class
                Intent myIntent = new Intent(SplashActivity.this,
                        TutorialActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }




}
