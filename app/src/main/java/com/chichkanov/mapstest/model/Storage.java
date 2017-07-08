package com.chichkanov.mapstest.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chichkanov.mapstest.model.parsing.StorageParser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Storage implements IStorage {

    private static IStorage singleton;

    public static IStorage getInstance(Context context) {
        if(singleton == null) {
            singleton = new Storage(StorageParser.get(context));
        }

        return singleton;
    }

    public static void setInstance(@NonNull Reader in) {
        singleton = new Storage(StorageParser.parse(in));
    }

    private Storage(StorageParser parser) {
        this.placesByCategory = parser.placeHashMap;
        this.schedulesBySchool = parser.subjectHashMap;

        int capacity = 0;
        for (List<Place> ps : parser.placeHashMap.values()) {
            capacity += ps.size();
        }

        this.places = new ArrayList<>(capacity);
        for (List<Place> ps : parser.placeHashMap.values()) {
            this.places.addAll(ps);
        }
    }

    private ArrayList<Place> places;
    private HashMap<String, List<Place>> placesByCategory;
    private HashMap<String, List<Subject>> schedulesBySchool;

    @Override
    public String[] getCategories() {
        return placesByCategory.keySet().toArray(new String[0]);
    }

    @Override
    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public List<Place> getPlacesWithCategories(List<String> categories) {
        final ArrayList<Place> places = new ArrayList<>();
        for (String category : categories) {
            places.addAll(placesByCategory.get(category));
        }
        return places;
    }

    private static boolean match(String pattern, Place place) {
        return place.getName().toLowerCase().contains(pattern);
    }

    @Override
    public List<Place> getPlacesWithCategories(List<String> categories, @NonNull String textSearch) {
        if(textSearch.isEmpty()) {
            return getPlacesWithCategories(categories);
        }

        String pattern = textSearch.toLowerCase();
        final ArrayList<Place> places = new ArrayList<>();
        for (String category : categories) {
            for(Place place : placesByCategory.get(category)) {
                if(match(pattern, place)) {
                    places.add(place);
                }
            }
        }
        return places;
    }

    @Override
    public Collection<Subject> getNearestSchedule(String schoolName) {
        ArrayList<Subject> subjects = new ArrayList<>();
        List<Subject> list = schedulesBySchool.get(schoolName);
        if (list == null) {
            return subjects;
        }

        Calendar now = Calendar.getInstance();
        for(Subject subject : list) {
            if(subject.getTime().after(now)) {
                subjects.add(subject);
            }
        }

        return subjects;
    }
}