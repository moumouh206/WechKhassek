package com.mba2dna.wechkhassek.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.util.UserFunctions;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends ActionBarActivity {
    long Delay = 3000;
    UserFunctions userFunctions;
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
                SharedPreferences prefs = getSharedPreferences("WechKhassek", 0);
                SharedPreferences.Editor editor = prefs.edit();
                Intent intent;
                if (prefs.getString("isInitial", null)=="1")
                {
                    userFunctions = new UserFunctions();
                    if (userFunctions.isUserLoggedIn(getApplicationContext())) {

                        Intent myIntent = new Intent(SplashActivity.this,
                                MainActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                        finish();
                    }else{

                        Intent myIntent = new Intent(SplashActivity.this,
                                LoginActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                        finish();
                    }
                }
                else
                {
                    //First Time App launched, you are putting isInitialAppLaunch to false and calling create password activity.
                    editor.remove("isInitial"); // will delete key email
                    editor.commit();
                    editor.putString("isInitial", "1");
                    editor.commit();
                   // Toast.makeText(SplashActivity.this,"resultat2 :" +prefs.getString("isInitial", null),Toast.LENGTH_LONG).show();

                    intent = new Intent(SplashActivity.this, TutorialActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }




}
