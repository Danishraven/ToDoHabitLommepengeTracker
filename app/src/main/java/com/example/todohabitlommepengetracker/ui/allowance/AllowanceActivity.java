package com.example.todohabitlommepengetracker.ui.allowance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.data.TransactionStorageHandler;
import com.example.todohabitlommepengetracker.model.Transaction;
import com.example.todohabitlommepengetracker.ui.common.BottomNavigationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AllowanceActivity extends AppCompatActivity {

    private TransactionStorageHandler transactionStorageHandler;
    private ArrayList<Transaction> transactions;
    private TransactionAdapter adapter;
    private TextView balanceAmount;
    private ActivityResultLauncher<Intent> addTransactionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowance);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationHandler.setup(bottomNavigationView, this, R.id.navigation_allowance);

        balanceAmount = findViewById(R.id.balance_amount);
        ListView transactionsList = findViewById(R.id.transactions_list);
        FloatingActionButton fabAddTransaction = findViewById(R.id.fabAddTransaction);

        transactionStorageHandler = new TransactionStorageHandler();
        transactions = transactionStorageHandler.load(this);

        adapter = new TransactionAdapter(this, transactions);
        transactionsList.setAdapter(adapter);

        updateBalance();

        addTransactionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String description = result.getData().getStringExtra(AddTransactionActivity.EXTRA_DESCRIPTION);
                        double amount = result.getData().getDoubleExtra(AddTransactionActivity.EXTRA_AMOUNT, 0);

                        // Only add transaction if the amount is not zero
                        if (description != null && !description.trim().isEmpty() && amount != 0) {
                            transactions.add(new Transaction(new Date(), amount, description));
                            adapter.notifyDataSetChanged();
                            transactionStorageHandler.save(this, transactions);
                            updateBalance();
                        }
                    }
                }
        );

        fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(AllowanceActivity.this, AddTransactionActivity.class);
            addTransactionLauncher.launch(intent);
        });
    }

    private void updateBalance() {
        double total = 0;
        for (Transaction t : transactions) {
            total += t.getAmount();
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
        balanceAmount.setText(format.format(total));
    }
}
