package com.chichkanov.mapstest.model.parsing.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private String name;
    private ArrayList<PlaceDto> objects;

    public String getName() {
        return name;
    }

    public List<PlaceDto> getObjects() {
        return objects;
    }
}
