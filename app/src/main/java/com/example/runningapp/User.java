package com.example.runningapp;

public class User {
    private int id, distance;
    private String fname, lname, email;

    public User (String fname, String lname, String email, int id, int distance){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
         this.id = id;
         this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getDistance() {
        return distance;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
