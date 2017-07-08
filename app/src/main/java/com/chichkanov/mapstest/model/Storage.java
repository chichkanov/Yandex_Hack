package com.chichkanov.mapstest.model;

import android.content.Context;

import com.chichkanov.mapstest.model.parsing.StorageFactory;

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
    }

    private HashMap<String, Place> placesByCategory;

    @Override
    public String[] getCategories() {
        return placesByCategory.keySet().toArray(new String[0]);
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