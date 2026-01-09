package com.example.todohabitlommepengetracker.data;

import com.example.todohabitlommepengetracker.model.HabitItem;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

// This class now provides the type information directly
public class HabitStorageHandler extends StorageHandler<HabitItem> {
    public HabitStorageHandler() {
        super("habit_prefs", "habits", new TypeToken<ArrayList<HabitItem>>() {}.getType());
    }
}
