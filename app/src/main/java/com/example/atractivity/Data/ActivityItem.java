package com.example.atractivity.Data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 *  class for the items that we use  as data structure for "activities" in our app
 *  contains no column info as we always handle the items as objects in the main activity/actions
 */

@Entity
public class ActivityItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String activityName;
    private boolean min, alertSet;
    private int hours, activityMinutes, color = 0;


    public ActivityItem(String activityName, boolean min, int hours, int activityMinutes, int color, boolean alertSet){
        this.activityName = activityName;
        this.min = min;
        this.hours = hours;
        this.activityMinutes = activityMinutes;
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

    public int getActivityMinutes() {
        return activityMinutes;
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

    public void setActivityMinutes(int activityMinutes) {
        this.activityMinutes = activityMinutes;
    }

    public void setColor(int color) {
        this.color = color;
    }



}
