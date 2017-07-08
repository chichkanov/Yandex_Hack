package com.chichkanov.mapstest.model;

import android.content.Context;

import com.chichkanov.mapstest.model.parsing.StorageFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Storage implements IStorage {

    private static IStorage singleton;

    static IStorage getInstance(Context context) {
        if(singleton == null) {
            singleton = StorageFactory.get(context);
        }

        return singleton;
    }

    Storage(HashMap<String, Place> placesByCategory) {
        this.placesByCategory = placesByCategory;
        this.categories = new ArrayList<>(placesByCategory.keySet());
    }


    private ArrayList<String> categories;
    private HashMap<String, Place> placesByCategory;

    @Override
    public ArrayList<String> getCategories() {
        return categories;
    }

    @Override
    public Collection<Place> getPlaces() {
        return placesByCategory.values();
    }

    @Override
    public Collection<Place> getPlacesWithCategories(List<String> categories) {
        return placesByCategory.values();
    }
}