package com.example.proximityrenaultmais;

import com.google.android.gms.location.GeofenceStatusCodes;

public class GeofenceErrorMessages {

    private  GeofenceErrorMessages() {}


    public static  String getErrorString(int errorCode) {

        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GEOFENCE NOT AVAILABLE";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "TOO MANY GEOFENCES";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "TOO MANY PENDING INTENTS";
            default:
                return "UNKNOWN GEOFENCE ERROR";
        }
    }
}
