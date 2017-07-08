package com.chichkanov.mapstest.model.parsing.dto;

import java.util.ArrayList;
import java.util.List;

public class FileDto {

    private ArrayList<PeopleDto> people;
    private ArrayList<CategoryDto> places;
    private ArrayList<ScheduleDto> schedule;

    FileDto() {
    }

    public List<PeopleDto> getPeople() {
        return people;
    }

    public List<CategoryDto> getPlaces() {
        return places;
    }

    public List<ScheduleDto> getSchedule() {
        return schedule;
    }
}
