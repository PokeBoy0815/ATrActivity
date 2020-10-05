package com.example.atractivity.Data.Database;

import com.example.atractivity.Data.ActivityItem;

import java.util.ArrayList;
import java.util.List;

public interface ActivityItemQueryResultListener {

    void onResult(ActivityItem aitem);
    void onListResult(List<ActivityItem> aitems);

}
