package com.example.android.tourguideapp;

public class Place {

    private String name;
    private String description;
    private String location;
    private String price;

    enum Price {
        CHEAP,
        MEDIUM,
        EXPENSIVE
    }

    private int imageResourceID;


    public Place(String name) {
        this.name = name;
        this.description = "";
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
