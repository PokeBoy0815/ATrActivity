package com.example.atractivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ActivityItemAdapter;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.Data.ReturnKeys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Homescreen extends AppCompatActivity {


    //Instance of databaseHelper for communication with room
    private ActivityItemDatabaseHelper databaseHelper;

    //private Button button;
    private ListView activityList;

    //
    private ArrayList<ActivityItem> activities;
    private ActivityItemAdapter activityitemadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUI();
        adapterStuff();
        fetchDatabaseData();

    }

    //initializes the adapter for the list of Activities
    private void adapterStuff() {
        activities = new ArrayList<>();
        activityitemadapter = new ActivityItemAdapter(this, activities);
        activityList.setAdapter(activityitemadapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //activate or deactivate Time-Machine

            }
        });
        activityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        });
    }

    //Method that is now in a Test state but will be used to get standard activities
    private void fetchDatabaseData() {
        ActivityItem ai1 = new ActivityItem("Test1", true, 1, 0, 1, false);
        databaseHelper = new ActivityItemDatabaseHelper(this);
        //databaseHelper.addActivityItemToDatabase(ai1);
        databaseHelper.getAllItemsFromRoom(new ActivityItemQueryResultListener() {
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

    //Builds th User Interface from Xml and java objects
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

    //method to handle the click on the button to start the activity that will allow the user to create a activity
    // with wanting a result
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}