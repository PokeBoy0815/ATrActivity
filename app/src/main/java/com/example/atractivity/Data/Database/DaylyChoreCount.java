package com.example.atractivity.Data.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class DaylyChoreCount {


    @PrimaryKey(autoGenerate = true)
    private int did;
    private Date date;


    public DaylyChoreCount(){
        date = new Date(System.currentTimeMillis());

    }

}
