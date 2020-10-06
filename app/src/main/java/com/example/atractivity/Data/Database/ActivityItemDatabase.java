package com.example.atractivity.Data.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.atractivity.Data.ActivityItem;

/**
 * Abstract db class where we define our db as db with two sheets, one for ActivityItems and one for TimeItems
 */

@Database(entities = {ActivityItem.class, DaylyTimeCount.class}, version = 1)
public abstract class ActivityItemDatabase extends RoomDatabase {
    public abstract ActivityItemDAO activityitems();
}
