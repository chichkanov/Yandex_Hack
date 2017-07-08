package com.chichkanov.mapstest.model;

import java.util.Collection;
import java.util.List;

public interface IStorage {

    String[] getCategories();

    Collection<Place> getPlaces();

    Collection<Place> getPlacesWithCategories(List<String> categories);
}
