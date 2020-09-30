package com.example.atractivity.Data;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* class for the items that we use  as data structure for "activities" in our app*/
@Entity
public class ActivityItem {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String activityName;
    private boolean min, alertSet;
    private int hours, minutes, color = 0;


    public ActivityItem(String activityName, boolean min, int hours, int minutes, int color, boolean alertSet){
        this.activityName = activityName;
        this.min = min;
        this.hours = hours;
        this.minutes = minutes;
        this.color = color;
        this.alertSet = alertSet;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getColor() {
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

    public void setColor(int color) {
        this.color = color;
    }



}
