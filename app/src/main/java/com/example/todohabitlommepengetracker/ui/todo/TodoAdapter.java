package com.example.todohabitlommepengetracker.ui.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.data.TodoStorageHandler;
import com.example.todohabitlommepengetracker.data.TransactionStorageHandler;
import com.example.todohabitlommepengetracker.model.TodoItem;
import com.example.todohabitlommepengetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    final TodoStorageHandler storageHandler;
    final TransactionStorageHandler transactionStorageHandler;

    public TodoAdapter(Context context, List<TodoItem> todos, TodoStorageHandler storageHandler) {
        super(context, 0, todos);
        this.storageHandler = storageHandler;
        this.transactionStorageHandler = new TransactionStorageHandler();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodoItem todo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_todo, parent, false);
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
            // Only create a transaction if the item is being completed and has a reward
            if (isChecked && !todo.isCompleted() && todo.getReward() > 0) {
                ArrayList<Transaction> transactions = transactionStorageHandler.load(getContext());
                transactions.add(new Transaction(new Date(), todo.getReward(), todo.getTitle()));
                transactionStorageHandler.save(getContext(), transactions);
            }
            todo.setCompleted(isChecked);
            notifyDataSetChanged();
            storageHandler.save(getContext(), new ArrayList<>(getAllItems()));
        });

        btnDelete.setOnClickListener(v -> {
            remove(todo);
            notifyDataSetChanged();
            storageHandler.save(getContext(), new ArrayList<>(getAllItems()));
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
