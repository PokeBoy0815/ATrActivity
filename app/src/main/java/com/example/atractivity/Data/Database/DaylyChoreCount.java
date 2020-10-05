package com.example.atractivity.Data.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Entity
public class DaylyChoreCount {


    @PrimaryKey(autoGenerate = true)
    private int did;
    //private Calendar date;
    private int hours, minutes;



    public DaylyChoreCount(int hours, int minutes){
        //date = Calendar.getInstance();
        this.hours = hours;
        this.minutes = minutes;
    }


    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }



    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
