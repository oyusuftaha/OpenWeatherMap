package com.example.openweathermapcase.helper.gps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Objects;

public class GPSTracker extends Service implements LocationListener {

    public static final int LOCATION_PERMISSION_CODE = 9;
    private final Context mContext;
    private boolean mCanGetLocation = false;
    private Location mLocation; // mLocation
    private double mLatitude; // mLatitude
    private double mLongitude; // mLongitude

    private IGPSTracker iGPSTracker;


    public GPSTracker(IGPSTracker iGPSTracker, Context context) {
        this.mContext = context;
        this.iGPSTracker = iGPSTracker;
    }


    public void requestLocation() {
        try {

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                iGPSTracker.hasNoLocationPermission(permissions);


                return;
            }

            LocationManager locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = Objects.requireNonNull(locationManager)
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                iGPSTracker.noProviderEnabled();
            } else {
                this.mCanGetLocation = true;

                Criteria locationMode = new Criteria();
                locationMode.setBearingRequired(false);
                locationMode.setSpeedRequired(true);
                locationMode.setAccuracy(Criteria.ACCURACY_FINE);
                locationMode.setAltitudeRequired(false);

                locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.removeUpdates(this);
                locationManager.requestSingleUpdate(locationMode, this, null);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GPSTracker", e.getMessage());
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        iGPSTracker.onLocationHandled(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("GPSTracker", "Provider Disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GPSTracker", "Provider Enabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Log.d("GPSTracker", "status: " + status);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public double getLatitude() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
        }

        // return mLatitude
        return mLatitude;
    }

    /**
     * Function to get mLongitude
     */
    public double getLongitude() {
        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
        }

        // return mLongitude
        return mLongitude;
    }


    public boolean canGetLocation() {
        return this.mCanGetLocation;
    }


}