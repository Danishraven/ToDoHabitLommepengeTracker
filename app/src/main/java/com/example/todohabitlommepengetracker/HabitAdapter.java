package com.example.todohabitlommepengetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HabitAdapter extends ArrayAdapter<HabitItem> {

    final HabitStorageHandler storageHandler;

    public HabitAdapter(Context context, List<HabitItem> habits, HabitStorageHandler storageHandler) {
        super(context, 0, habits);
        this.storageHandler = storageHandler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HabitItem habit = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_habit, parent, false);
        }

        TextView textTitle = convertView.findViewById(R.id.textTitle);
        Button btnInfo = convertView.findViewById(R.id.btnInfo);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        textTitle.setText(habit.getTitle());

        btnInfo.setOnClickListener(v -> {
            // TODO: Implement a more detailed info view
            Toast.makeText(getContext(), "Info for " + habit.getTitle(), Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v -> {
            remove(habit);
            notifyDataSetChanged();
            storageHandler.save(getContext(), new ArrayList<>(getAllItems()));
        });

        return convertView;
    }

    private ArrayList<HabitItem> getAllItems() {
        ArrayList<HabitItem> items = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            items.add(getItem(i));
        }
        return items;
    }
}
