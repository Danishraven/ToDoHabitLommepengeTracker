package com.example.todohabitlommepengetracker.ui.habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todohabitlommepengetracker.data.HabitStorageHandler;
import com.example.todohabitlommepengetracker.data.TransactionStorageHandler;
import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.model.HabitItem;
import com.example.todohabitlommepengetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HabitAdapter extends ArrayAdapter<HabitItem> {

    final HabitStorageHandler storageHandler;
    final TransactionStorageHandler transactionStorageHandler;

    public HabitAdapter(Context context, List<HabitItem> habits, HabitStorageHandler storageHandler) {
        super(context, 0, habits);
        this.storageHandler = storageHandler;
        this.transactionStorageHandler = new TransactionStorageHandler();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HabitItem habit = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_habit, parent, false);
        }

        TextView textTitle = convertView.findViewById(R.id.textTitle);
        CheckBox checkCompleted = convertView.findViewById(R.id.checkCompleted);
        Button btnInfo = convertView.findViewById(R.id.btnInfo);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        textTitle.setText(habit.getTitle());
        checkCompleted.setChecked(habit.isCompletedOn(new Date()));

        checkCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Only create a transaction if the item is being completed and has a reward
            if (isChecked && !habit.isCompletedOn(new Date()) && habit.getReward() > 0) {
                ArrayList<Transaction> transactions = transactionStorageHandler.load(getContext());
                transactions.add(new Transaction(new Date(), habit.getReward(), habit.getTitle()));
                transactionStorageHandler.save(getContext(), transactions);
            }
            habit.toggleCompletionForToday();
            notifyDataSetChanged();
            storageHandler.save(getContext(), new ArrayList<>(getAllItems()));
        });

        btnInfo.setOnClickListener(v -> {
            String stats = "Last 7 Days: " + habit.getCompletionsInLast(7) + "\n"
                       + "Last 30 Days: " + habit.getCompletionsInLast(30) + "\n"
                       + "Current Streak: " + habit.getCurrentStreak();
            Toast.makeText(getContext(), stats, Toast.LENGTH_LONG).show();
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
