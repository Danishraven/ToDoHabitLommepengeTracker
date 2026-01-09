package com.example.todohabitlommepengetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.todohabitlommepengetracker.ui.allowance.AllowanceFragment;
import com.example.todohabitlommepengetracker.ui.habit.HabitFragment;
import com.example.todohabitlommepengetracker.ui.todo.TodoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TodoFragment()).commit();
        }
    }

    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.navigation_todo) {
            selectedFragment = new TodoFragment();
        } else if (itemId == R.id.navigation_habits) {
            selectedFragment = new HabitFragment();
        } else if (itemId == R.id.navigation_allowance) {
            selectedFragment = new AllowanceFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }

        return false;
    };
}
