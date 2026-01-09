package com.example.todohabitlommepengetracker;

import java.util.Date;

public class TodoItem {
    private final String title;
    private final Date dateCreated;
    private boolean completed;

    public TodoItem(String title) {
        this.title = title;
        this.completed = false;
        this.dateCreated = new Date();
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
}

