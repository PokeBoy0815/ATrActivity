package com.example.atractivity.Data.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.atractivity.Data.ActivityItem;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ActivityItemDAO {


    // Einfaches Einfügen des übergebenen Objekts in die Datenbank.
    @Insert
    void addActivityItem(ActivityItem activityitems);

    // Sucht ein Friend-Objekt anhand seines Names aus der Datenbank heraus
    @Query("SELECT * FROM activityitem WHERE activityName = :activityitemName")
    ActivityItem getAItemByName(String activityitemName);

    @Query("SELECT * FROM activityitem WHERE uid = :id")
    ActivityItem getAItemByUID(int id);

    @Query("SELECT * FROM activityitem")
    List<ActivityItem> getAllItems();



}

