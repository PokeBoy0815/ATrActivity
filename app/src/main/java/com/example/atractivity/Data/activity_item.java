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

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setMin(boolean min) {
        this.min = min;
    }

    public void setAlertSet(boolean alertSet) {
        this.alertSet = alertSet;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setColor(Color color) {
        this.color = color;
    }



}
