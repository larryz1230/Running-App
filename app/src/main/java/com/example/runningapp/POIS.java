package com.example.runningapp;

import com.google.android.gms.maps.model.LatLng;

public class POIS {
    private String locationname;
    private LatLng latLng;

    public POIS(String locationname, Double lat, Double lng){
        this.locationname = locationname;
        latLng = new LatLng(lat, lng);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getLocationname() {
        return locationname;
    }
}
