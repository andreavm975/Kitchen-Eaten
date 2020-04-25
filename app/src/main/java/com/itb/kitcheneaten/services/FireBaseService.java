package com.itb.kitcheneaten.services;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.itb.kitcheneaten.R;
import com.itb.kitcheneaten.SplashActivity;

public class FireBaseService extends FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID = "KitchenEaten_channel";
    String TAG = "Firebase KitchenEaten: ";

    NotificationManager mNotificationManager;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
    }

    /**
     * Mètode del FirebaseMessagingService, que si arriba una notificació, crida al mètode que crea un canal de notificació, la mostra i un intent amb el que es fara si aquesta es clica
     * @param remoteMessage
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            createNotificationChannel();

            Intent intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

            builder.setSmallIcon(R.drawable.ic_kitchen_eaten_logo)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setColor(R.color.colorPrimary)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            mNotificationManager.notify(2000, builder.build());

        }

    }

    /**
     * Mètode que crea un canal de notificació i les seves configuracions
     */
    private void createNotificationChannel() {

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "KitchenEaten channel",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }


    }
}
