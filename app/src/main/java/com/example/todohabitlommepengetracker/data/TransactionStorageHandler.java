package com.example.todohabitlommepengetracker.data;

import com.example.todohabitlommepengetracker.model.Transaction;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

// This concrete class tells the StorageHandler it will be working with Transactions
public class TransactionStorageHandler extends StorageHandler<Transaction> {
    public TransactionStorageHandler() {
        // Pass the specific preference name and key for transactions
        super("transaction_prefs", "transactions", new TypeToken<ArrayList<Transaction>>() {}.getType());
    }
}
