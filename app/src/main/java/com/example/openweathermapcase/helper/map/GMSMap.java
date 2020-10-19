package com.example.openweathermapcase.helper.map;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GMSMap implements IMapService, OnMapReadyCallback {
    private Location location;
    private float zoom;

    @Override
    public void setMapLocation(Location location){
        this.location = location;
    }

    @Override
    public void setMapZoom(float zoom){
        this.zoom = zoom;
    }

    @Override
    public void getMap(SupportMapFragment mapFragment){
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( zoom ) );

    }
}
