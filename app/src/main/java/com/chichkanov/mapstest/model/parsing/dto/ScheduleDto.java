package com.chichkanov.mapstest.model.parsing.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by turist on 08.07.2017.
 */

public class ScheduleDto {

    private final static SimpleDateFormat dateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    float duration;
    String location;
    ArrayList<String> schools;
    ArrayList<String> teacher;
    String time; // "2017-07-03 10:00:00+0300"
    String title;

    ScheduleDto() {
    }

    public String getTitle() {
        return title;
    }

    public Calendar getTime() {
        Calendar d = Calendar.getInstance();

        try {
            d.setTimeInMillis(dateAndTime.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public List<String> getTeacher() {
        return teacher;
    }

    public String getLocation() {
        return location;
    }

    public float getDuration() {
        return duration;
    }

    public List<String> getSchools() {
        return schools;
    }
}
