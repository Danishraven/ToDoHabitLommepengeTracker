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

public class MainActivity extends AppCompatActivity {
    private final StorageHandler storageHandler = new StorageHandler("todo_prefs", "todos");
    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private ActivityResultLauncher<Intent> addTodoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listViewTodos);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the current activity's navigation item to selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_todo);

        todoList = storageHandler.load(this);

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
            Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
            addTodoLauncher.launch(intent);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_habits) {
                startActivity(new Intent(getApplicationContext(), HabitActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_allowance) {
                startActivity(new Intent(getApplicationContext(), AllowanceActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_todo) {
                return true;
            }
            return false;
        });
    }
}
