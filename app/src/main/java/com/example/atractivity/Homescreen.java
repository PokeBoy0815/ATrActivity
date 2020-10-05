package com.example.atractivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ActivityItemAdapter;
import com.example.atractivity.Data.Database.ActivityItemAdapterBase;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.Data.ReturnKeys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Homescreen extends AppCompatActivity {


    //
    private ActivityItemDatabaseHelper databaseHelper;

    //private Button button;
    private ListView activityList;

    private ArrayList<ActivityItem> activities;
    private ActivityItemAdapter activityitemadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchTestData();
        adapterStuff();
        buildUI();
    }

    //initializes the adapter for the list of Activities
    private void adapterStuff() {
        activityitemadapter = new ActivityItemAdapter(this, activities);
        activityList.setAdapter(activityitemadapter);
    }

    //Method that is now in a Test state but will be used to get standard activities
    private void fetchTestData() {
        activities = new ArrayList<>();
        ActivityItem ai1 = new ActivityItem("Test1", true, 1, 0, 1, false);
        databaseHelper.addActivityItemToDatabase(ai1);
        databaseHelper.getAllItemsFromRoom(new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {

            }

            @Override
            public void onListResult(List<ActivityItem> aitems) {
                activities.addAll(aitems);
            }
        });
        activityitemadapter.notifyDataSetChanged();

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