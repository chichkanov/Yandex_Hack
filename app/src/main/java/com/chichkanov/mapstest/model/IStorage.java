package com.chichkanov.mapstest.model;

import java.util.Collection;
import java.util.List;

public interface IStorage {

    String[] getCategories();

    List<Place> getPlaces();

    List<Place> getPlacesWithCategories(List<String> categories);

    List<Place> getPlacesWithCategories(List<String> categories, String textSearch);

    Collection<Subject> getNearestSchedule(String schoolName);
}
