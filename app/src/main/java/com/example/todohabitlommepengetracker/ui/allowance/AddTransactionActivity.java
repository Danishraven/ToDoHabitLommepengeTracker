package com.example.todohabitlommepengetracker.ui.allowance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.R;

public class AddTransactionActivity extends AppCompatActivity {

    public static final String EXTRA_DESCRIPTION = "extra_description";
    public static final String EXTRA_AMOUNT = "extra_amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        EditText editDescription = findViewById(R.id.edit_transaction_description);
        EditText editAmount = findViewById(R.id.edit_transaction_amount);
        Button btnSave = findViewById(R.id.button_save_transaction);

        btnSave.setOnClickListener(v -> {
            String description = editDescription.getText().toString().trim();
            String amountStr = editAmount.getText().toString().trim();

            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
                return;
            }

            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = 0;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent result = new Intent();
            result.putExtra(EXTRA_DESCRIPTION, description);
            result.putExtra(EXTRA_AMOUNT, amount);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
