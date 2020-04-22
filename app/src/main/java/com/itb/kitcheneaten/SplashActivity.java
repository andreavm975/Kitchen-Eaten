package com.itb.kitcheneaten;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler myHandler;
    private Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        myRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        };

        myHandler = new Handler();

        myHandler.postDelayed(myRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null && myRunnable != null) {
            myHandler.removeCallbacks(myRunnable);
        }
    }
}
