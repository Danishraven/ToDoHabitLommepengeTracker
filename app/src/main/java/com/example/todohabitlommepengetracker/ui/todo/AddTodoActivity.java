package com.example.todohabitlommepengetracker.ui.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.R;

public class AddTodoActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        EditText editTitle = findViewById(R.id.editTodoTitle);
        Button btnSave = findViewById(R.id.btnSaveTodo);

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent result = new Intent();
            result.putExtra(EXTRA_TITLE, title);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
