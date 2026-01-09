package com.example.todohabitlommepengetracker.data;

import com.example.todohabitlommepengetracker.model.TodoItem;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

// This class now provides the type information directly
public class TodoStorageHandler extends StorageHandler<TodoItem> {
    public TodoStorageHandler() {
        super("todo_prefs", "todos", new TypeToken<ArrayList<TodoItem>>() {}.getType());
    }
}
