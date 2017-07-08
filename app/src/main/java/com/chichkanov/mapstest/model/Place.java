package com.chichkanov.mapstest.model;

public class Place {

    private String description;
    private float lat;
    private float lon;
    private String name;
    private String category;

    public Place(String description, float lat, float lon, String name, String category) {
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
