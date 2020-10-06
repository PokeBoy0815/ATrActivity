package com.example.atractivity.Data.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.atractivity.Data.ActivityItem;

@Database(entities = {ActivityItem.class, DaylyChoreCount.class}, version = 1)
public abstract class ActivityItemDatabase extends RoomDatabase {
    public abstract ActivityItemDAO activityitems();
}
