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
import java.util.Date;

public class HabitActivity extends AppCompatActivity {

    private final HabitStorageHandler storageHandler = new HabitStorageHandler();
    private ArrayList<HabitItem> habitList;
    private HabitAdapter adapter;
    private ActivityResultLauncher<Intent> addHabitLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        ListView listView = findViewById(R.id.listViewHabits);
        // Correcting the ID to match the layout file R.layout.activity_habit
        FloatingActionButton fabAdd = findViewById(R.id.fabAddHabit);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        BottomNavigationHandler.setup(bottomNavigationView, this, R.id.navigation_habits);

        habitList = storageHandler.load(this);
        if (habitList == null) {
            habitList = new ArrayList<>();
        }

        adapter = new HabitAdapter(this, habitList, storageHandler);
        listView.setAdapter(adapter);

        addHabitLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String title = data.getStringExtra(AddHabitActivity.EXTRA_TITLE);
                        String frequencyStr = data.getStringExtra(AddHabitActivity.EXTRA_FREQUENCY);
                        String targetStr = data.getStringExtra(AddHabitActivity.EXTRA_TARGET);
                        long startDateMillis = data.getLongExtra(AddHabitActivity.EXTRA_START_DATE, -1);

                        if (title != null && !title.trim().isEmpty() && frequencyStr != null && targetStr != null && startDateMillis != -1) {
                            Frequency frequency = Frequency.valueOf(frequencyStr);
                            Frequency target = Frequency.valueOf(targetStr);
                            Date startDate = new Date(startDateMillis);
                            habitList.add(new HabitItem(title.trim(), frequency, target, startDate));
                            adapter.notifyDataSetChanged();
                            storageHandler.save(this, habitList);
                        }
                    }
                }
        );

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HabitActivity.this, AddHabitActivity.class);
            addHabitLauncher.launch(intent);
        });
    }
}
