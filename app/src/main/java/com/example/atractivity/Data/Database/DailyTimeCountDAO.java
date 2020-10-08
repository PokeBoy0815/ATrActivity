package com.example.atractivity.Data.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.atractivity.Data.ActivityItem;

import java.util.List;

@Dao
public interface DailyTimeCountDAO {

    // ad items to db.
    @Insert
    void adDailyTimeCount(DaylyTimeCount timeCount);


    /* Query items which belong to one activity
    @Query("SELECT * FROM daylytimecount WHERE nameOfActivity = :activityitemName")
    List<DaylyTimeCount> getAllItemsByName(String activityitemName);
    */

    //query TimeObjects by date
    @Query("SELECT * FROM daylytimecount WHERE date = :dateOfItem")
    List<DaylyTimeCount> getAllDailyTimeCountsOfaDay(long dateOfItem);

    //sum of Items time per item searched at a date
    @Query("SELECT Count(minutes) FROM daylytimecount WHERE date = :dateOfItem AND nameOfActivity = :activityitemName")
    int getDailyTimeByDate(long dateOfItem, String activityitemName);

    /**
    @Delete
    void delete(DaylyTimeCount timeCount);
    */
}
