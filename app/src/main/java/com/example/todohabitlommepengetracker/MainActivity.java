package com.example.todohabitlommepengetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.AddTodoActivity;
import com.example.todohabitlommepengetracker.TodoAdapter;
import com.example.todohabitlommepengetracker.TodoItem;
import com.example.todohabitlommepengetracker.TodoStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> todoList;
    private TodoAdapter adapter;
    private ActivityResultLauncher<Intent> addTodoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listViewTodos);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

//        todoList = new ArrayList<>();
//        todoList.add(new TodoItem("Buy milk"));
//        todoList.add(new TodoItem("Finish Android assignment"));
//        todoList.add(new TodoItem("Go for a walk"));

        todoList = TodoStorage.load(this);

        adapter = new TodoAdapter(this, todoList);
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
                            TodoStorage.save(this, todoList);
                        }
                    }
                }
        );

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
            addTodoLauncher.launch(intent);
        });

    }
}
