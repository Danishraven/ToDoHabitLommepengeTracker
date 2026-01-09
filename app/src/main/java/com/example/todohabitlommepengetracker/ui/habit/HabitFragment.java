package com.example.todohabitlommepengetracker.ui.habit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.data.HabitStorageHandler;
import com.example.todohabitlommepengetracker.model.Frequency;
import com.example.todohabitlommepengetracker.model.HabitItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class HabitFragment extends Fragment {

    private HabitStorageHandler storageHandler;
    private ArrayList<HabitItem> habitList;
    private HabitAdapter adapter;
    private ActivityResultLauncher<Intent> addHabitLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storageHandler = new HabitStorageHandler();
        habitList = storageHandler.load(requireContext());
        if (habitList == null) {
            habitList = new ArrayList<>();
        }

        addHabitLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String title = data.getStringExtra(AddHabitActivity.EXTRA_TITLE);
                        double reward = data.getDoubleExtra(AddHabitActivity.EXTRA_REWARD, 0);
                        String frequencyStr = data.getStringExtra(AddHabitActivity.EXTRA_FREQUENCY);
                        String targetStr = data.getStringExtra(AddHabitActivity.EXTRA_TARGET);
                        long startDateMillis = data.getLongExtra(AddHabitActivity.EXTRA_START_DATE, -1);

                        if (title != null && !title.trim().isEmpty() && frequencyStr != null && targetStr != null && startDateMillis != -1) {
                            Frequency frequency = Frequency.valueOf(frequencyStr);
                            Frequency target = Frequency.valueOf(targetStr);
                            Date startDate = new Date(startDateMillis);
                            habitList.add(new HabitItem(title.trim(), frequency, target, startDate, reward));
                            adapter.notifyDataSetChanged();
                            storageHandler.save(requireContext(), habitList);
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit, container, false);

        ListView listView = view.findViewById(R.id.listViewHabits);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddHabit);

        adapter = new HabitAdapter(requireContext(), habitList, storageHandler);
        listView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddHabitActivity.class);
            addHabitLauncher.launch(intent);
        });

        return view;
    }
}
