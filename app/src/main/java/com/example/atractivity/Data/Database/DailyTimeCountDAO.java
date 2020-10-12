package com.example.atractivity.Data.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface DailyTimeCountDAO {

    // ad items to db.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void adDailyTimeCount(DaylyTimeCount timeCount);


    //Set time saved in a certain Object to a certain time
    @Query("UPDATE daylytimecount SET minutes = :time WHERE  date = :dateOfItem AND nameOfActivity = :activityitemName AND dailyId = :did")
    void setTimeOfOtem(String activityitemName, String dateOfItem, int time, int did);


    //sum of Items time per item searched at a date
    @Query("SELECT SUM(minutes) FROM daylytimecount WHERE date = :dateOfItem AND nameOfActivity = :activityitemName")
    int getDailyTimeByDate(String dateOfItem, String activityitemName);

    @Query("SELECT MAX(dailyId) FROM daylytimecount WHERE date = :dateOfItem AND nameOfActivity = :activityitemName")
    int getLatestDailyTime(String dateOfItem, String activityitemName);

    @Delete
    void delete(DaylyTimeCount timeCount);


}
