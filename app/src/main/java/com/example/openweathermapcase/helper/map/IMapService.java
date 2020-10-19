package com.example.openweathermapcase.helper.map;

import android.location.Location;

import com.google.android.gms.maps.SupportMapFragment;

public interface IMapService {
    void setMapZoom(float zoom);
    void setMapLocation(Location location);
    void getMap(SupportMapFragment mapFragment);
}
