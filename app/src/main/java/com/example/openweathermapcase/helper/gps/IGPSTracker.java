package com.example.openweathermapcase.helper.gps;

import android.location.Location;

public interface IGPSTracker {
    void hasNoLocationPermission(String[] permissions);
    void noProviderEnabled();
    void onLocationHandled(Location location);
}
