package com.example.todohabitlommepengetracker.model;

import java.util.Date;

public class Transaction {
    private final Date date;
    private final double amount;
    private final String description;

    public Transaction(Date date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
