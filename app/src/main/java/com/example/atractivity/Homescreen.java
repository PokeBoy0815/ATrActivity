package com.example.atractivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ActivityItemAdapter;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.Data.Database.DailyTimeCountQueryResultListener;
import com.example.atractivity.Data.Database.DaylyTimeCount;
import com.example.atractivity.Data.ReturnKeys;
import com.example.atractivity.broadcast.ActivityTimerBroadcastListener;
import com.example.atractivity.broadcast.ActivityTimerBroadcastReceiver;
import com.example.atractivity.timer.ActivityTimerService;
import com.example.atractivity.timer.IsRunning;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Homescreen extends AppCompatActivity implements ActivityTimerBroadcastListener {


    //Instance of databaseHelper for communication with room
    private ActivityItemDatabaseHelper databaseHelper;
    //private Button button;
    private ListView activityList;

    private ArrayList<ActivityItem> activities;
    private ActivityItemAdapter activityitemadapter;

    private ActivityTimerBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new ActivityItemDatabaseHelper(this);
        buildUI();
        adapterStuff();
        checkFirstRun();
        fetchDatabaseData();


    }

    /** Override methods from the ActivityTimerBroadcastListener to register and unregister the broadcast receiver. */
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastReceiver();
    }

    /** This method initializes the adapter for the list of activities. */
    private void adapterStuff() {
        activities = new ArrayList<>();
        activityitemadapter = new ActivityItemAdapter(this, activities);
        activityList.setAdapter(activityitemadapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClickListener(i);
            }
        });
        activityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return onItemLongClickListener(i);
            }
        });
    }

    /** The following methods handle actions of the onClickListener and the onLongClickListener.*/
    private void onItemClickListener(int i){
        if (!IsRunning.testRunning()){
            ActivityItem activityItem = activities.get(i);
            startActivity(activityItem, i);
        }
        else if (IsRunning.testActiveNumber() != i){
            stopActivity();
            ActivityItem activityItem = activities.get(i);
            startActivity(activityItem, i);
        }
        else {
            stopActivity();
        }
    }
    private boolean onItemLongClickListener(int i){
        AlertDialog.Builder adb=new AlertDialog.Builder(Homescreen.this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete " + i);
        final int positionToRemove = i;
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteActivityItemFromDatabase(activities.get(positionToRemove));
                activities.remove(positionToRemove);
                activityitemadapter.notifyDataSetChanged();
            }});
        adb.show();
        return false;
    }

    /** Method for creating first time example Data. */
    private void checkFirstRun() {
        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            // Do first run stuff here then set 'firstrun' as false
            ActivityItem a1 = new ActivityItem("Android", true, 1, 45, 3, false);
            activities.add(a1);
            databaseHelper.addActivityItemToDatabase(a1);
            ActivityItem a2 = new ActivityItem("Sport", true, 0, 45, 5, false);
            activities.add(a2);
            databaseHelper.addActivityItemToDatabase(a2);
            ActivityItem a3 = new ActivityItem("Netflix", false, 2, 0, 1, true);
            activities.add(a3);
            databaseHelper.addActivityItemToDatabase(a3);
            activityitemadapter.notifyDataSetChanged();
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).apply();

        } else if (currentVersionCode > savedVersionCode) {
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }


    /** Method that puts all ActivityItem objects into an arrayList so we can use the arrayListAdapter*/
    private void fetchDatabaseData() {
        databaseHelper.getAllActivityItemsFromRoom(new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {

            }

            @Override
            public void onListResult(List<ActivityItem> aitems) {
                activities.addAll(aitems);
                activityitemadapter.notifyDataSetChanged();
            }
        });
    }

    /** Builds th User Interface from Xml and java objects */
    private void buildUI() {
        setContentView(R.layout.homescreen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button to get the Activity Data
        Button button = findViewById(R.id.Change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity();
            }
        });

        activityList = findViewById(R.id.activity_list);
    }

    /**
    method to handle the click on the button to start the activity that will allow the user to create a activity
     with wanting a result
    */
    private void getActivity() {
        Intent intent = new Intent(Homescreen.this, CreateActivity.class);
        startActivityForResult(intent, ReturnKeys.KEY_TO_RETURN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ReturnKeys.KEY_TO_RETURN_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    if(data != null) {
                        addActivityFromData(data);
                    }
                }
        }
    }

    /**creates and adds an item from the extras of the input activity*/
    private void addActivityFromData(@NotNull Intent data) {
        String activityName =  data.getExtras().getString(ReturnKeys.NAME_KEY);
        boolean min = data.getExtras().getBoolean(ReturnKeys.MIN_KEY);
        int hours = data.getExtras().getInt(ReturnKeys.HOUR_KEY);
        int minutes = data.getExtras().getInt(ReturnKeys.MINUTES_KEY);
        int color = data.getExtras().getInt(ReturnKeys.COLOR_KEY);
        boolean alertSet = data.getExtras().getBoolean(ReturnKeys.ALERT_KEY);
        ActivityItem aitem = new ActivityItem(activityName, min, hours, minutes, color, alertSet);
        activities.add(aitem);
        databaseHelper.addActivityItemToDatabase(aitem);
        activityitemadapter.notifyDataSetChanged();
    }

/** Methods to start and stop Activities by activating and deactivating new threads. */
    private void startActivity(ActivityItem activityItem, int i){
        Intent intent = new Intent (this, ActivityTimerService.class);
        intent.putExtra(ReturnKeys.ACTIVITY_EXTRA_KEY, activityItem);
        startService(intent);
        IsRunning.setRunning();
        IsRunning.setActiveNumber(i);
        DaylyTimeCount dtc = new DaylyTimeCount(0, activityItem.getActivityName());
        Log.e("TimeCount", ""+ dtc.getNameOfActivity()+""+dtc.getDailyId()+"");
        databaseHelper.addDailyTimeCountToDatabase(dtc);
    }
    private void stopActivity(){
        Intent intent = new Intent (this, ActivityTimerService.class);
        stopService(intent);
        IsRunning.setNotRunning();
    }

    /** Methods to register and unregister broadcast receiver. */
    private void registerBroadcastReceiver() {
        unregisterBroadcastReceiver();
        broadcastReceiver = new ActivityTimerBroadcastReceiver(this);
        this.registerReceiver(broadcastReceiver, ActivityTimerBroadcastReceiver.getIntentFilter());
    }
    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }


    /** Override methods from the Timer. */
    @Override
    public void onTimerFinished() {
        IsRunning.setNotRunning();
    }
    @Override
    public void onTimerUpdate(final int remainingTimeInSeconds) {
        //time got updated  (original für zeitanzeige im overlay, bei uns für update der zeit in der datenbank)
        final ActivityItem activityItem = activities.get(IsRunning.testActiveNumber());
        final String date = "" + Calendar.DAY_OF_MONTH + Calendar.MONTH + Calendar.YEAR + "";
        databaseHelper.returnLatestItemID(date, activityItem.getActivityName(), new DailyTimeCountQueryResultListener() {


            @Override
            public int onIntegerResult(int i) {

                    databaseHelper.setTimeForCertainObject(date, activityItem.getActivityName(),
                            (((activityItem.getHours() * 60 + activityItem.getActivityMinutes()) * 60) - remainingTimeInSeconds) / 60, i);
                    Log.e("Krieg ich ID?", "" + activityItem.getActivityName() + "," + i +", " + remainingTimeInSeconds +"");
                    return 0;

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            Intent intent = new Intent(Homescreen.this, Homescreen.class);
            startActivity(intent);
        }
        if (id == R.id.stats) {
            Intent intent = new Intent(Homescreen.this, Overview.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




}