package com.example.todohabitlommepengetracker;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

// This class now provides the type information directly
public class HabitStorageHandler extends StorageHandler<HabitItem> {
    public HabitStorageHandler() {
        super("habit_prefs", "habits", new TypeToken<ArrayList<HabitItem>>() {}.getType());
    }
}
