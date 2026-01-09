package com.example.todohabitlommepengetracker.model;

import java.util.Date;

public class TodoItem {
    private final String title;
    private final Date dateCreated;
    private boolean completed;
    private double reward;

    public TodoItem(String title, double reward) {
        this.title = title;
        this.completed = false;
        this.dateCreated = new Date();
        this.reward = reward;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() { return  dateCreated; }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getReward() {
        return reward;
    }
}
