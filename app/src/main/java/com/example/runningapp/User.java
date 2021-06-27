package com.example.runningapp;

public class User {
    private int id, credits;
    private String fname, lname, email;
    private double lat, lng, distance;

    public User (String fname, String lname, String email, int id, int distance, double lat, double lng, int credits){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
         this.id = id;
         this.distance = distance;
         this.lat = lat;
         this.lng = lng;
         this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getCredits() {
        return credits;
    }

    public String getEmail() {
        return email;
    }

    public double getDistance() {
        return distance;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
