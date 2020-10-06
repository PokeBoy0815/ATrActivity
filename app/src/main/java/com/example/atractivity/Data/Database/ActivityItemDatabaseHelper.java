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

    /*
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
            // Ausführen der Datenbankoperation
            final ActivityItem activityitem = db.activityitems().getAItemByName(activityName);
            // Wechsel in den UI Thread, nach dem die Datenbankoperation durchgelaufen ist
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Rückmeldung an den Listener mit dem gefundenen Objekt aus der Datenbank
                    listener.onResult(activityitem);
                }
            });
        }
    }

    //searches ActivityItems by their id
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
            // Ausführen der Datenbankoperation
            final ActivityItem activityitem = db.activityitems().getAItemByUID(uid);
            // Wechsel in den UI Thread, nach dem die Datenbankoperation durchgelaufen ist
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Rückmeldung an den Listener mit dem gefundenen Objekt aus der Datenbank
                    listener.onResult(activityitem);
                }
            });
        }
    }

    /**
    returns all db sheet entrys of activity items as list
    creates a new task to seperate the action from the main thread
    */
    public void getAllItemsFromRoom(ActivityItemQueryResultListener listener){
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
            // Ausführen der Datenbankoperation
            final List<ActivityItem> activityitems = db.activityitems().getAllItems();
            // Wechsel in den UI Thread, nach dem die Datenbankoperation durchgelaufen ist
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Rückmeldung an den Listener mit dem gefundenen Objekt aus der Datenbank
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
        // Erstellt den Task, in dem die Datenbankoperation ausgeführt werden soll
        AddActivityItemTask task = new AddActivityItemTask(activityitem);
        //new thread
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
        // Erstellt den Task, in dem die Datenbankoperation ausgeführt werden soll
        DeleteActivityItemTask task = new DeleteActivityItemTask(activityitem);
        //new thread
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

}
