package com.example.atractivity.Data.Database;

import java.util.HashMap;
import java.util.List;

public interface DailyTimeCountQueryResultListener {

    void onListResult(List<DaylyTimeCount> timeCounts);
    int onIntegerResult(int i);
}
