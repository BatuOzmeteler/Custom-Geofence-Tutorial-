package com.example.proximityrenaultmais;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.VIBRATE};
    private GoogleApiClient googleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Custom Geofence Settings");

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.reconnect();
        if (!checkPermissions()) {
            requestPermissions();
            Log.e("Permission Request", "Permissions are being requested");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }


    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Api Client Connected");
        Log.d(TAG, "Start Location Monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (googleApiClient, locationRequest, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "Location Change Lat Lng " +
                                    location.getLatitude() + " " + location.getLongitude());
                        }
                    });
        } catch (SecurityException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)&&
                        ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.VIBRATE);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(MainActivity.this,
                    permissions, CONTEXT_INCLUDE_CODE);
        } else {
            Log.i(TAG, "Requesting permission...");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this, permissions,CONTEXT_INCLUDE_CODE);
        }

    }

    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this, permissions[0]);
        int permissionState2 = ActivityCompat.checkSelfPermission(this, permissions[1]);
        boolean check = (permissionState1 == PackageManager.PERMISSION_GRANTED)
                && (permissionState2 == PackageManager.PERMISSION_GRANTED);
        if(check){
            return true;
        }else{
            return false;
        }
    }

    public void setGeofence(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle extras = new Bundle();

        EditText name = findViewById(R.id.textBar1);
        EditText latitude = findViewById(R.id.textBar2);
        EditText longitude = findViewById(R.id.textBar3);
        EditText radius = findViewById(R.id.textBar4);

        boolean isEmptyCheck = (name.getText().toString().isEmpty() == false)&&
                (latitude.getText().toString().isEmpty() == false) &&
                (longitude.getText().toString().isEmpty() == false) &&
                (radius.getText().toString().isEmpty() == false);

        if(isEmptyCheck){
            double lat = Double.parseDouble(latitude.getText().toString());
            double lon = Double.parseDouble(longitude.getText().toString());
            int rad = Integer.parseInt(radius.getText().toString());

            if(lat < 90 && lat > -90){
                if(lon < 180 && lon > -180){
                    CustomGeofence customGeofence = new CustomGeofence(name.getText().toString(), lat, lon, rad);
                    extras.putParcelable("Custom Geofence", customGeofence);
                    intent.putExtras(extras);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Longitude value should be in between -180 and 180",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Latitude value should be in between -90 and 90",
                        Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }

    }
}
