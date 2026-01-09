package com.example.todohabitlommepengetracker.ui.common;

import android.app.Activity;
import android.content.Intent;

import com.example.todohabitlommepengetracker.ui.allowance.AllowanceActivity;
import com.example.todohabitlommepengetracker.R;
import com.example.todohabitlommepengetracker.ui.habit.HabitActivity;
import com.example.todohabitlommepengetracker.ui.todo.TodoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHandler {

    public static void setup(BottomNavigationView bottomNavigationView, final Activity activity, final int currentNavItemId) {
        bottomNavigationView.setSelectedItemId(currentNavItemId);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == currentNavItemId) {
                return true;
            }

            Intent intent = null;
            if (itemId == R.id.navigation_todo) {
                intent = new Intent(activity, TodoActivity.class);
            } else if (itemId == R.id.navigation_habits) {
                intent = new Intent(activity, HabitActivity.class);
            } else if (itemId == R.id.navigation_allowance) {
                intent = new Intent(activity, AllowanceActivity.class);
            }

            if (intent != null) {
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
                activity.finish();
                return true;
            }

            return false;
        });
    }
}
