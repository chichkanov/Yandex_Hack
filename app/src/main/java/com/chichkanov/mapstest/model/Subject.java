package com.chichkanov.mapstest.model;

/**
 * Created by chichkanov on 08.07.17.
 */

import java.util.Calendar;
import java.util.List;

public class Subject {

    private float duration;
    private String location;
    private List<String> schools = null;
    private List<String> teacher = null;
    private Calendar time;
    private String title;

    public Subject() {
    }

    public Subject(float duration, String location, List<String> schools, List<String> teacher, Calendar time, String title) {
        this.duration = duration;
        this.location = location;
        this.schools = schools;
        this.teacher = teacher;
        this.time = time;
        this.title = title;
    }

    public float getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSchools() {
        return schools;
    }

    public List<String> getTeacher() {
        return teacher;
    }

    public Calendar getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}