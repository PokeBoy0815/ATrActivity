package com.example.atractivity.Data.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.atractivity.Data.ActivityItem;

import java.util.List;


@Dao
public interface ActivityItemDAO {


    // Einfaches Einfügen des übergebenen Objekts in die Datenbank.
    @Insert
    void addActivityItem(ActivityItem activityitems);

    // Query items by name
    @Query("SELECT * FROM activityitem WHERE activityName = :activityitemName")
    ActivityItem getAItemByName(String activityitemName);

    //query items by uid
    @Query("SELECT * FROM activityitem WHERE uid = :id")
    ActivityItem getAItemByUID(int id);

    @Query("SELECT * FROM activityitem")
    List<ActivityItem> getAllItems();

    @Delete
    void delete(ActivityItem activityItem);


}

