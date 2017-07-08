package com.chichkanov.mapstest.model.parsing;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chichkanov.mapstest.R;
import com.chichkanov.mapstest.model.Place;
import com.chichkanov.mapstest.model.Subject;
import com.chichkanov.mapstest.model.parsing.dto.CategoryDto;
import com.chichkanov.mapstest.model.parsing.dto.FileDto;
import com.chichkanov.mapstest.model.parsing.dto.PlaceDto;
import com.chichkanov.mapstest.model.parsing.dto.ScheduleDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StorageParser {

    public HashMap<String, List<Place>> placeHashMap = new HashMap<>();
    public HashMap<String, List<Subject>> subjectHashMap = new HashMap<>();

    public static StorageParser parse(@NonNull Reader in) {
        JsonReader jsonReader = new JsonReader(in);

        FileDto dto = null;
        try {
            Gson gson = new GsonBuilder()
                    .create();
            dto = gson.fromJson(jsonReader, FileDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                jsonReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(dto == null) {
            return null;
        }

        StorageParser parser = new StorageParser();
        for (CategoryDto cat : dto.getPlaces()) {
            List<PlaceDto> objects = cat.getObjects();
            ArrayList<Place> places = new ArrayList<>(objects.size());
            for (PlaceDto pl : objects) {
                places.add(new Place(pl.getDescription(), pl.getLat(), pl.getLon(), pl.getName(), cat.getName()));
            }

            parser.placeHashMap.put(cat.getName(), places);
        }

        for(ScheduleDto schedule : dto.getSchedule()) {
            Subject subject = new Subject(schedule.getDuration(), schedule.getLocation(), schedule.getSchools(), schedule.getTeacher(), schedule.getTime(), schedule.getTitle());
            for(String school : schedule.getSchools()) {
                if(!parser.subjectHashMap.containsKey(school)) {
                    parser.subjectHashMap.put(school, new ArrayList<Subject>());
                }

                parser.subjectHashMap.get(school).add(subject);
            }
        }

        return parser;
    }

    public static StorageParser get(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.bot_data);
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        return parse(streamReader);
    }
}
