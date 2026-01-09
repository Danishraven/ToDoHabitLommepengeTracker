package com.example.todohabitlommepengetracker;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    public TodoAdapter(Context context, List<TodoItem> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodoItem todo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_todo, parent, false);
        }

        TextView textTitle = convertView.findViewById(R.id.textTitle);
        CheckBox checkCompleted = convertView.findViewById(R.id.checkCompleted);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        textTitle.setText(todo.getTitle());
        checkCompleted.setChecked(todo.isCompleted());

        // Strike-through text if completed
        if (todo.isCompleted()) {
            textTitle.setPaintFlags(textTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textTitle.setPaintFlags(textTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        checkCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
            notifyDataSetChanged();
            TodoStorage.save(getContext(), new ArrayList<>(getAllItems()));
        });

        btnDelete.setOnClickListener(v -> {
            remove(todo);
            notifyDataSetChanged();
            TodoStorage.save(getContext(), new ArrayList<>(getAllItems()));
        });


        return convertView;
    }

    private ArrayList<TodoItem> getAllItems() {
        ArrayList<TodoItem> items = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            items.add(getItem(i));
        }
        return items;
    }

}
