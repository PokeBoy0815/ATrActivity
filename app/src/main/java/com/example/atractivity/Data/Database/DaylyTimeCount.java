package com.example.atractivity.Data.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class DaylyTimeCount {

    @PrimaryKey(autoGenerate = true)
    private int dailyId;
    //should be derived from the ActivityItems name, to which it belongs
    private String date;
    private String nameOfActivity;
    @ColumnInfo(name = "minutes")
    private int  minutes;



    public DaylyTimeCount(int minutes, String nameOfActivity){
        Calendar calendar = Calendar.getInstance();
        date = ""+ calendar.DAY_OF_MONTH + calendar.MONTH + calendar.YEAR+"";
        this.nameOfActivity = nameOfActivity;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameOfActivity() {
        return nameOfActivity;
    }

    public void setName(String nameOfActivity) {
        this.nameOfActivity = nameOfActivity;
    }

    public int getDailyId() {
        return dailyId;
    }

    public void setDailyId(int dailyId) {
        this.dailyId = dailyId;
    }
}
