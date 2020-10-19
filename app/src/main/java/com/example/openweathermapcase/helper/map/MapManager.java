package com.example.openweathermapcase.helper.map;


import android.location.Location;

import com.google.android.gms.maps.SupportMapFragment;

public class MapManager {

    private static MapManager instance;
    private IMapService service;

    private MapManager(IMapService service) {
        this.service = service;
    }

    public void setMapLocation(Location location){
        service.setMapLocation(location);
    }

    public void setMapZoom(float zoom){
        service.setMapZoom(zoom);
    }

    public void getMap(SupportMapFragment mapFragment){
        service.getMap(mapFragment);
    }


    public static MapManager getInstance() {
        if (instance == null) {
            // GMS veya HMS tercihi burada yapılabilir
            instance = new MapManager(new GMSMap());
        }
        return instance;
    }

}


