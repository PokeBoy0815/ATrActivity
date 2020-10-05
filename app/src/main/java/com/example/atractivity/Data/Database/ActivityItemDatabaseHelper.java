package com.example.atractivity.Data.Database;

import android.app.Activity;

import androidx.room.Room;

import com.example.atractivity.Data.ActivityItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ActivityItemDatabaseHelper {

    private static final String DATABASE_NAME = "activityitems-db";
    private ActivityItemDatabase db;

    private final Activity activityContext;

    /**
     * Dem Konstruktor der Hilfsklasse wird eine Activity als Parameter übergeben, damit deren
     * Kontext für die Erstellung bzw. Bereitstellung der Datenbank verwendet werden kann.
     */
    public ActivityItemDatabaseHelper(Activity activityContext) {
        this.activityContext = activityContext;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(activityContext, ActivityItemDatabase.class, DATABASE_NAME).build();
    }

    /**
     * Wird von anderen Teilen der Anwendung aufgerufen, um das übergebene Friend-Objekt in der
     * Datenbank zu speichern.
     */
    public void addActivityItemToDatabase(ActivityItem activityitem) {
        // Erstellt den Task, in dem die Datenbankoperation ausgeführt werden soll
        AddActivityItemTask task = new AddActivityItemTask(activityitem);
        // Führt den Task in einem neuen Thread durch
        Executors.newSingleThreadExecutor().submit(task);
    }

    /**
     * Wird von anderen Teilen der Anwendung aufgerufen, um eine Suche nach dem Friend-Objekt mit
     * dem übergebenen Namen in der Datenbank zu starten. Nach Abschluss der Anfrage wird das Ergebnis
     * an den übergebenen Listener gesendet.
     */
    public void getActivityItemByName(String name, ActivityItemQueryResultListener listener) {
        FindActivityItemTask task = new FindActivityItemTask(name, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    //searches ActivityItems by their id
    public void getActivityItemByUID(int id, ActivityItemQueryResultListener listener) {
        FindActivityItemByIdTask task = new FindActivityItemByIdTask(id, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    public void getAllItemsFromRoom(ActivityItemQueryResultListener listener){
        getAllItemsTask task = new getAllItemsTask(listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    /**
     * Diese Runnable kapselt den, nebenläufig durchzuführenden, Vorgang des Hinzufügens eines neuen
     * Datenbankeintrags. Das zuspeichernde Objekt wird dem Task im Konstruktor übergeben.
     */
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
     * Diese Runnable kapselt den, nebenläufig durchzuführenden, Vorgang des Heraussuchens eines
     * Eintrags aus der Datenbank. Der Name, anhand dessen der Eintrag identifiziert werden soll,
     * wird dem Task im Konstruktor übergeben. Zusätzlich wird dem Task ein Listener übergeben, der
     * über das Ergebniss der Suche informiert werden soll.
     */
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

}
