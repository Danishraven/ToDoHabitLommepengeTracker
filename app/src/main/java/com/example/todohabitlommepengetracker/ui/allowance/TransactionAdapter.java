package com.example.todohabitlommepengetracker.ui.allowance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.model.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transaction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }

        TextView description = convertView.findViewById(R.id.transaction_description);
        TextView date = convertView.findViewById(R.id.transaction_date);
        TextView amount = convertView.findViewById(R.id.transaction_amount);

        description.setText(transaction.getDescription());

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        date.setText(dateFormat.format(transaction.getDate()));

        // Format the amount and set color
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
        amount.setText(currencyFormat.format(transaction.getAmount()));

        if (transaction.getAmount() < 0) {
            amount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        } else {
            amount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
        }

        return convertView;
    }
}
