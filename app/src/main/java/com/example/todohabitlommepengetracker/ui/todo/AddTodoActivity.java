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
    public static final String EXTRA_REWARD = "extra_reward";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        EditText editTitle = findViewById(R.id.editTodoTitle);
        EditText editReward = findViewById(R.id.editTodoReward);
        Button btnSave = findViewById(R.id.btnSaveTodo);

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String rewardStr = editReward.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            double reward = 0;
            if (!rewardStr.isEmpty()) {
                try {
                    reward = Double.parseDouble(rewardStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid reward amount", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent result = new Intent();
            result.putExtra(EXTRA_TITLE, title);
            result.putExtra(EXTRA_REWARD, reward);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
