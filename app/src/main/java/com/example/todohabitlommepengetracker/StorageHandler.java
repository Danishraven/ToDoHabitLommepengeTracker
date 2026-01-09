package com.example.todohabitlommepengetracker;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageHandler {

    private final String PREFS_NAME;
    private final String KEY;

    public StorageHandler(String prefs_name, String key){
        PREFS_NAME = prefs_name;
        KEY = key;
    }

    public <T> void save(Context context, ArrayList<T> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String json = new Gson().toJson(list);
        editor.putString(KEY, json);
        editor.apply();
    }

    public <T> ArrayList<T> load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY, null);

        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<ArrayList<TodoItem>>() {}.getType();
        ArrayList<T> list = new Gson().fromJson(json, type);

        return (list != null) ? list : new ArrayList<>();
    }
}
