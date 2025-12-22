package com.example.resapp;

public class Reservation {

    private int id;
    private String name;
    private String date;
    private String time;
    private int guests;
    private String requests;

    // Constructor WITH id (used by staff view)
    public Reservation(int id, String name, String date, String time, int guests, String requests) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.requests = requests;
    }

    // Constructor WITHOUT id (used when creating reservation)
    public Reservation(String name, String date, String time, int guests, String requests) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.requests = requests;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getGuests() {
        return guests;
    }

    public String getRequests() {
        return requests;
    }
}
