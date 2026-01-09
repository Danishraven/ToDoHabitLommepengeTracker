package com.example.todohabitlommepengetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
    private final TodoStorageHandler storageHandler = new TodoStorageHandler();
    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private ActivityResultLauncher<Intent> addTodoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        ListView listView = findViewById(R.id.listViewTodos);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        BottomNavigationHandler.setup(bottomNavigationView, this, R.id.navigation_todo);

        todoList = storageHandler.load(this);
        // This null check prevents the crash
        if (todoList == null) {
            todoList = new ArrayList<>();
        }

        adapter = new TodoAdapter(this, todoList, storageHandler);
        listView.setAdapter(adapter);

        // Receive result from AddTodoActivity
        addTodoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra(AddTodoActivity.EXTRA_TITLE);
                        if (title != null && !title.trim().isEmpty()) {
                            todoList.add(new TodoItem(title.trim()));
                            adapter.notifyDataSetChanged();
                            storageHandler.save(this, todoList);
                        }
                    }
                }
        );

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TodoActivity.this, AddTodoActivity.class);
            addTodoLauncher.launch(intent);
        });
    }
}
