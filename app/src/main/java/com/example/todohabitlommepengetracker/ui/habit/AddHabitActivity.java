package com.example.todohabitlommepengetracker.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.model.Frequency;
import com.example.todohabitlommepengetracker.R;

import java.util.Calendar;
import java.util.Date;

public class AddHabitActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_REWARD = "extra_reward";
    public static final String EXTRA_FREQUENCY = "extra_frequency";
    public static final String EXTRA_TARGET = "extra_target";
    public static final String EXTRA_START_DATE = "extra_start_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        EditText editTitle = findViewById(R.id.edit_text_title);
        EditText editReward = findViewById(R.id.edit_text_reward);
        Spinner spinnerFrequency = findViewById(R.id.spinner_frequency);
        Spinner spinnerTarget = findViewById(R.id.spinner_target);
        DatePicker datePickerStartDate = findViewById(R.id.date_picker_start_date);
        Button btnSave = findViewById(R.id.button_save);

        ArrayAdapter<Frequency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Frequency.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter);
        spinnerTarget.setAdapter(adapter);

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

            Frequency frequency = (Frequency) spinnerFrequency.getSelectedItem();
            Frequency target = (Frequency) spinnerTarget.getSelectedItem();

            int day = datePickerStartDate.getDayOfMonth();
            int month = datePickerStartDate.getMonth();
            int year = datePickerStartDate.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            Date startDate = calendar.getTime();

            Intent result = new Intent();
            result.putExtra(EXTRA_TITLE, title);
            result.putExtra(EXTRA_REWARD, reward);
            result.putExtra(EXTRA_FREQUENCY, frequency.name());
            result.putExtra(EXTRA_TARGET, target.name());
            result.putExtra(EXTRA_START_DATE, startDate.getTime());
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
