package com.example.todohabitlommepengetracker.ui.allowance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.data.TransactionStorageHandler;
import com.example.todohabitlommepengetracker.model.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AllowanceFragment extends Fragment {

    private TransactionStorageHandler transactionStorageHandler;
    private ArrayList<Transaction> transactions;
    private TransactionAdapter adapter;
    private TextView balanceAmount;
    private ActivityResultLauncher<Intent> addTransactionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionStorageHandler = new TransactionStorageHandler();
        transactions = transactionStorageHandler.load(requireContext());

        addTransactionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        String description = result.getData().getStringExtra(AddTransactionActivity.EXTRA_DESCRIPTION);
                        double amount = result.getData().getDoubleExtra(AddTransactionActivity.EXTRA_AMOUNT, 0);

                        if (description != null && !description.trim().isEmpty() && amount != 0) {
                            transactions.add(new Transaction(new Date(), amount, description));
                            adapter.notifyDataSetChanged();
                            transactionStorageHandler.save(requireContext(), transactions);
                            updateBalance();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allowance, container, false);

        balanceAmount = view.findViewById(R.id.balance_amount);
        ListView transactionsList = view.findViewById(R.id.transactions_list);
        FloatingActionButton fabAddTransaction = view.findViewById(R.id.fabAddTransaction);

        adapter = new TransactionAdapter(requireContext(), transactions);
        transactionsList.setAdapter(adapter);

        updateBalance();

        fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
            addTransactionLauncher.launch(intent);
        });

        return view;
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
