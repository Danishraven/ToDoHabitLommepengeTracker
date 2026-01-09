package com.example.todohabitlommepengetracker.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HabitItem {
    private String title;
    private Frequency frequency;
    private Frequency target; // This seems unused, but keeping it for now
    private Date startDate;
    private ArrayList<Date> completionDates = new ArrayList<>();

    public HabitItem(String title, Frequency frequency, Frequency target, Date startDate) {
        this.title = title;
        this.frequency = frequency;
        this.target = target;
        this.startDate = startDate;
    }

    // --- Getters and Setters ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public ArrayList<Date> getCompletionDates() { return completionDates; }

    // --- Completion Logic ---
    public void toggleCompletionForToday() {
        Date today = getStartOfDay(new Date());
        if (isCompletedOn(today)) {
            // If it was completed today, remove the completion
            completionDates.removeIf(date -> getStartOfDay(date).equals(today));
        } else {
            // Otherwise, add a completion for today
            completionDates.add(today);
        }
    }

    public boolean isCompletedOn(Date date) {
        Date targetDate = getStartOfDay(date);
        for (Date completionDate : completionDates) {
            if (getStartOfDay(completionDate).equals(targetDate)) {
                return true;
            }
        }
        return false;
    }

    // --- Statistics Logic ---

    public int getCompletionsInLast(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        Date limit = cal.getTime();
        int count = 0;
        for (Date completion : completionDates) {
            if (completion.after(limit)) {
                count++;
            }
        }
        return count;
    }

    public int getCurrentStreak() {
        if (completionDates.isEmpty()) {
            return 0;
        }

        Collections.sort(completionDates, Collections.reverseOrder());

        int streak = 0;
        Calendar expectedDate = Calendar.getInstance();

        // If not completed today or yesterday, streak is 0, unless it's a weekly habit
        if (!isCompletedOn(expectedDate.getTime())) {
             expectedDate.add(Calendar.DAY_OF_YEAR, -1);
             if (frequency == Frequency.Daily && !isCompletedOn(expectedDate.getTime())) {
                return 0;
             }
        }

        for (Date completion : completionDates) {
            if (isCompletedOn(expectedDate.getTime())) {
                streak++;
                int daysToSubtract = (frequency == Frequency.Daily) ? 1 : 7;
                expectedDate.add(Calendar.DAY_OF_YEAR, -daysToSubtract);
            } else {
                // Found a gap in the streak
                break;
            }
        }
        return streak;
    }

    // --- Utility Methods ---
    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
