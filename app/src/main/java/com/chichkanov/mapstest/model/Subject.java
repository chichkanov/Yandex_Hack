package com.chichkanov.mapstest.model;

/**
 * Created by chichkanov on 08.07.17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Subject {

    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("schools")
    @Expose
    private List<String> schools = null;
    @SerializedName("teacher")
    @Expose
    private List<String> teacher = null;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     */
    public Subject() {
    }

    /**
     * @param title
     * @param time
     * @param duration
     * @param schools
     * @param location
     * @param teacher
     */
    public Subject(Double duration, String location, List<String> schools, List<String> teacher, String time, String title) {
        super();
        this.duration = duration;
        this.location = location;
        this.schools = schools;
        this.teacher = teacher;
        this.time = time;
        this.title = title;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSchools() {
        return schools;
    }

    public void setSchools(List<String> schools) {
        this.schools = schools;
    }

    public List<String> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<String> teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}