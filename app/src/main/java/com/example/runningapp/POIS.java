package com.example.runningapp;

import com.google.android.gms.maps.model.LatLng;

public class POIS {
    private String locationname;
    private LatLng latLng;
    private double lat, lng;

    public POIS(String locationname, Double lat, Double lng){
        this.locationname = locationname;
        latLng = new LatLng(lat, lng);
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getLocationname() {
        return locationname;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
