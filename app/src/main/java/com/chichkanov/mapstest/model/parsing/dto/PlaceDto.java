package com.chichkanov.mapstest.model.parsing.dto;

public class PlaceDto {
    String description;
    float lat;
    float lon;
    String name;

    PlaceDto() {
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getDescription() {
        return description;
    }
}
