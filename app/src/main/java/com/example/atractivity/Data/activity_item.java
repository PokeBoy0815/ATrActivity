package com.example.atractivity.Data;

import android.graphics.Color;

public class activity_item {

    private String activityName;
    private boolean min, alertSet;
    private int hours, minutes = 0;
    private Color color;

    public activity_item(String activityName, boolean min, int hours, int minutes, Color color, boolean alertSet){
        this.activityName = activityName;
        this.min = min;
        this.hours = hours;
        this.minutes = minutes;
        this.color = color;
        this.alertSet = alertSet;
    }

    public String getActivityName() {
        return activityName;
    }

    public boolean isMin() {
        return min;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public Color getColor() {
        return color;
    }

    public boolean isAlertSet() {
        return alertSet;
    }
}
