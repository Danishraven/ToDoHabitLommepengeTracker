package com.example.todohabitlommepengetracker.ui.allowance;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.ui.common.BottomNavigationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AllowanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowance);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationHandler.setup(bottomNavigationView, this, R.id.navigation_allowance);
    }
}
