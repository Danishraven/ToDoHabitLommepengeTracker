package com.example.todohabitlommepengetracker;


import java.util.Date;

public class HabitItem {
    private String title;
    private Frequency frequency;
    private Frequency target;
    private Date startDate;

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Frequency getTarget() {
        return target;
    }

    public void setTarget(Frequency target) {
        this.target = target;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public HabitItem(String title, Frequency frequency, Frequency target, Date startDate){
        this.title = title;
        this.frequency = frequency;
        this.target = target;
        this.startDate = startDate;
    }
}
