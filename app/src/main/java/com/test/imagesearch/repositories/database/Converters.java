package com.test.imagesearch.repositories.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.imagesearch.models.Images;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Images> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Images>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Images> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}