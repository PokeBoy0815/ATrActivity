package com.example.atractivity.Data.Database;

import com.example.atractivity.Data.ActivityItem;

import java.util.List;

public interface DailyTimeCountQueryResultListener {

    void onListResult(List<DaylyTimeCount> timeCounts);
    void onTimeResult(int minutes);

}
