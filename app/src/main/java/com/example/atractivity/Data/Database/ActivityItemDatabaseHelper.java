package com.example.atractivity.Data.Database;

import android.app.Activity;

import androidx.room.Room;

import com.example.atractivity.Data.ActivityItem;

import java.util.List;
import java.util.concurrent.Executors;

public class ActivityItemDatabaseHelper {

    private static final String DATABASE_NAME = "activityitems-db";
    private ActivityItemDatabase db;

    private final Activity activityContext;

    /**
     */
    public ActivityItemDatabaseHelper(Activity activityContext) {
        this.activityContext = activityContext;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(activityContext, ActivityItemDatabase.class, DATABASE_NAME).build();
    }

    /**
     Method to search and return ActivityItem objects in the db
     opens a new thread to seperate from main thread
     */
    public void getActivityItemByName(String name, ActivityItemQueryResultListener listener) {
        FindActivityItemTask task = new FindActivityItemTask(name, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class FindActivityItemTask implements Runnable {

        private String activityName;
        private ActivityItemQueryResultListener listener;

        public FindActivityItemTask(String activityName, ActivityItemQueryResultListener listener) {
            this.activityName = activityName;
            this.listener = listener;
        }

        @Override
        public void run() {
            final ActivityItem activityitem = db.activityitems().getAItemByName(activityName);
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onResult(activityitem);
                }
            });
        }
    }

    /**searches ActivityItems by their id*/

    public void getActivityItemByUID(int id, ActivityItemQueryResultListener listener) {
        FindActivityItemByIdTask task = new FindActivityItemByIdTask(id, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class FindActivityItemByIdTask implements Runnable {

        private int uid;
        private ActivityItemQueryResultListener listener;

        public FindActivityItemByIdTask(int uid, ActivityItemQueryResultListener listener) {
            this.uid = uid;
            this.listener = listener;
        }

        @Override
        public void run() {
            final ActivityItem activityitem = db.activityitems().getAItemByUID(uid);

            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onResult(activityitem);
                }
            });
        }
    }

    /**
    returns all db sheet entrys of activity items as list
    creates a new task to seperate the action from the main thread
    */
    public void getAllActivityItemsFromRoom(ActivityItemQueryResultListener listener){
        getAllItemsTask task = new getAllItemsTask(listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class getAllItemsTask implements Runnable {

        private ActivityItemQueryResultListener listener;

        public getAllItemsTask(ActivityItemQueryResultListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            final List<ActivityItem> activityitems = db.activityitems().getAllItems();
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onListResult(activityitems);
                }
            });
        }
    }

    /**
     Method that is use to safe ActivityItem objects in the datebase
     creates a new task to seperate the action from the main thread
     */
    public void addActivityItemToDatabase(ActivityItem activityitem) {
        AddActivityItemTask task = new AddActivityItemTask(activityitem);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class AddActivityItemTask implements Runnable {

        private ActivityItem activityitem;

        public AddActivityItemTask(ActivityItem activityitem) {
            this.activityitem = activityitem;
        }

        @Override
        public void run() {
            db.activityitems().addActivityItem(activityitem);
        }
    }

    /**
     Method that is use to safe ActivityItem objects in the datebase
     creates a new task to seperate the action from the main thread
     */
    public void deleteActivityItemFromDatabase(ActivityItem activityitem) {

        DeleteActivityItemTask task = new DeleteActivityItemTask(activityitem);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class DeleteActivityItemTask implements Runnable {

        private ActivityItem activityitem;

        public DeleteActivityItemTask(ActivityItem activityitem) {
            this.activityitem = activityitem;
        }

        @Override
        public void run() {
            db.activityitems().delete(activityitem);
        }
    }

    /**
     Method that is use to safe DailyTimeCount objects in the datebase
     creates a new task to seperate the action from the main thread
     */
    public void addDailyTimeCountToDatabase(DaylyTimeCount daylyTimeCount) {
        AddDailytimeCountTask task = new AddDailytimeCountTask(daylyTimeCount);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class AddDailytimeCountTask implements Runnable {

        private DaylyTimeCount daylyTimeCount;

        public AddDailytimeCountTask(DaylyTimeCount daylyTimeCount) {
            this.daylyTimeCount = daylyTimeCount;
        }

        @Override
        public void run() {
            db.timecounts().adDailyTimeCount(daylyTimeCount);
        }
    }

    /**returnes the sum of minutes in all items of a certain name at a certain time*/

    public void getDailyTimeCount(String date, String name, DailyTimeCountQueryResultListener listener) {
        GetDailyTimeCountTimeSumTask task = new GetDailyTimeCountTimeSumTask(date, name, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class GetDailyTimeCountTimeSumTask implements Runnable {

        private String date;
        private String name;
        private DailyTimeCountQueryResultListener listener;

        public GetDailyTimeCountTimeSumTask(String date, String name, DailyTimeCountQueryResultListener listener) {
            this.date = date;
            this.name = name;
            this.listener = listener;
        }

        @Override
        public void run() {
            final int minutes = db.timecounts().getDailyTimeByDate(date, name);

            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onTimeResult(minutes);
                }
            });
        }
    }

/** return all time count items of one day in a List*/
    public void getAllTimeItemsOfADay(String date, DailyTimeCountQueryResultListener listener){
        getAllTimeItemsOfOneDayTask task = new getAllTimeItemsOfOneDayTask(date, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class getAllTimeItemsOfOneDayTask implements Runnable {

        private DailyTimeCountQueryResultListener listener;
        private String date;

        public getAllTimeItemsOfOneDayTask(String date, DailyTimeCountQueryResultListener listener) {
            this.listener = listener;
            this.date = date;
        }

        @Override
        public void run() {
            final List<DaylyTimeCount> timeItems = db.timecounts().getAllDailyTimeCountsOfaDay(date);
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onListResult(timeItems);
                }
            });
        }
    }
    /**
     * sets the time column for a certain object to the time that is given to the method
     * */
    public void setTimeForCertainObject(String date, String nameOfActivity, int time){
        SetTimeForCertainObjectTask task = new SetTimeForCertainObjectTask(nameOfActivity, date, time);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class SetTimeForCertainObjectTask implements Runnable {

        private String nameOfActivity;
        private String date;
        private int time;

        public SetTimeForCertainObjectTask(String nameOfActivity, String date, int time) {
            this.nameOfActivity = nameOfActivity;
            this.date = date;
            this.time = time;
        }

        @Override
        public void run() {
            db.timecounts().setTimeOfOtem(nameOfActivity, date, time);
        }
    }
}
