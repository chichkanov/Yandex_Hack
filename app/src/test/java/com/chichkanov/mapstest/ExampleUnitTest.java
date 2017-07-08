package com.chichkanov.mapstest;

import com.chichkanov.mapstest.model.IStorage;
import com.chichkanov.mapstest.model.Storage;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void parseJson() throws Exception {

        StringReader stringReader = new StringReader("{\n" +
                "    \"people\": [\n" +
                "        {\n" +
                "            \"birthday\": \"01-08\", \n" +
                "            \"name\": \"\\u0410\", \n" +
                "            \"roles\": [\n" +
                "                \"manager\"\n" +
                "            ], \n" +
                "            \"schools\": [\n" +
                "                \"sh1\"\n" +
                "            ], \n" +
                "            \"telegram\": \"t1\"\n" +
                "        }\n" +
                "    ], \n" +
                "    \"places\": [\n" +
                "        {\n" +
                "            \"name\": \"\\u042f\", \n" +
                "            \"objects\": [\n" +
                "                {\n" +
                "                    \"description\": \"\\u0430, 16 \", \n" +
                "                    \"lat\": 51.7, \n" +
                "                    \"lon\": 32.5, \n" +
                "                    \"name\": \"\\u0441\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ], \n" +
                "    \"schedule\": [\n" +
                "        {\n" +
                "            \"duration\": 2.0, \n" +
                "            \"location\": \"\\u043e\\u0438\", \n" +
                "            \"schools\": [\n" +
                "                \"sh1\"\n" +
                "            ], \n" +
                "            \"teacher\": [\n" +
                "                \"\\u0440\\u044f\", \n" +
                "                \"\\u041d\\u0440\"\n" +
                "            ], \n" +
                "            \"time\": \"2017-02-01 10:00:00+0300\", \n" +
                "            \"title\": \"\\u0442\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        Storage.setInstance(stringReader);
        IStorage s = Storage.getInstance(null);

        Assert.assertArrayEquals(s.getCategories(), new Object[]{ "Я" });
        Assert.assertEquals(s.getPlaces().size(), 1);
    }
}