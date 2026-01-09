package com.example.todohabitlommepengetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

// No longer abstract, and no reflection
public class StorageHandler<T> {

    private final String PREFS_NAME;
    private final String KEY;
    private final Type listType;

    // The type is now passed into the constructor
    public StorageHandler(String prefs_name, String key, Type listType){
        this.PREFS_NAME = prefs_name;
        this.KEY = key;
        this.listType = listType;
    }

    public void save(Context context, ArrayList<T> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String json = new Gson().toJson(list, listType);
        editor.putString(KEY, json);
        editor.apply();
    }

    public ArrayList<T> load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY, null);

        if (json == null) {
            return new ArrayList<>();
        }

        ArrayList<T> list = new Gson().fromJson(json, listType);

        return (list != null) ? list : new ArrayList<>();
    }
}
