package com.example.atractivity.Data.Database;

import java.util.List;

public interface DailyTimeCountQueryResultListener {

    void onListResult(List<DaylyTimeCount> timeCounts);
    //void onNameListResult(List<String> timeNames);
    int onIntegerResult(int i);
}
