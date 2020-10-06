package com.example.atractivity.Data.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class DaylyTimeCount {

    @PrimaryKey
    private long date;
    //should be derived from the ActivityItems name, to which it belongs
    private String nameOfActivity;
    private int  minutes;



    public DaylyTimeCount(int minutes, String nameOfActivity){
        date = Calendar.getInstance().getTimeInMillis();
        this.nameOfActivity = nameOfActivity;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return nameOfActivity;
    }

    public void setName(String name) {
        this.nameOfActivity = name;
    }
}
