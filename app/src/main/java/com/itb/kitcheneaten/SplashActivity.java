package com.itb.kitcheneaten;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Classe que mostra un layout mentre carrega en segon pla el MainActivity
 */
public class SplashActivity extends AppCompatActivity {

    private Handler myHandler;
    private Runnable myRunnable;

    /**
     * Creació d'un thread que fa un intent al MainActivity i mostra el layout de l'splash durant 2 segons
     * @param savedInstanceState
     */
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

    /**
     * Mètode que no deixa tornar enrere un cop passat aquest Acitvity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null && myRunnable != null) {
            myHandler.removeCallbacks(myRunnable);
        }
    }
}
