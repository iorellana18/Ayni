package cl.citiaps.coordinaciondevoluntarios.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

import cl.citiaps.coordinaciondevoluntarios.R;

/**
 * Created by Ian on 09-08-2017.
 */

public class SplashScreenActivity extends Activity {


    private static final long SPLASH_SCREEN_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_splashscreen);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = prefs.getBoolean("isLogin", false);

        if(isLogin){
            Intent intent = new Intent(SplashScreenActivity.this,IndexNoEmergencyActivity.class);
            startActivity(intent);
            finish();
        }else{
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }




    }
}
