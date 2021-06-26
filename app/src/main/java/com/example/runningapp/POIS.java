package com.example.runningapp;

import com.google.android.gms.maps.model.LatLng;

public class POIS {
    private String locationname, description;
    private LatLng latLng;
    private double lat, lng, distance;

    public POIS(String locationname, String description, Double lat, Double lng, Double distance){
        this.locationname = locationname;
        latLng = new LatLng(lat, lng);
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public String getDescription() {
        return description;
    }
}
