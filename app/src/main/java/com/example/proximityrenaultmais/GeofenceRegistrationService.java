package com.example.proximityrenaultmais;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;


import static android.content.ContentValues.TAG;

public class GeofenceRegistrationService extends IntentService {

    public GeofenceRegistrationService() {
        super("Geofence Registration Service");
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "Geofencing Event Error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            if (transaction == Geofence.GEOFENCE_TRANSITION_DWELL || transaction == Geofence.GEOFENCE_TRANSITION_ENTER) {
                sendEnterNotification();
            } else if (transaction == Geofence.GEOFENCE_TRANSITION_EXIT) {
                sendExitNotification();
            } else {
                Log.e(TAG, "WE DON'T KNOW YOUR LOCATION PLEASE STANDBY");
            }
        }
    }

    public void sendEnterNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2000")
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle("Hey, you are here!!!")
                .setContentText("You have entered the specified territory")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setVibrate(new long[]{500});

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, builder.build());
    }

    public void sendExitNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2001")
                .setSmallIcon(R.drawable.bell_icon)
                .setContentTitle("Sorry to see you leave!!!")
                .setContentText("You have exited the specified territory")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setVibrate(new long[]{500});;


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(002, builder.build());
    }
}


