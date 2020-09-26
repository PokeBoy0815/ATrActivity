package com.example.atractivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.ActivityItemAdapter;
import com.example.atractivity.Data.ReturnKeys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Homescreen extends AppCompatActivity {

    private Button button;
    private ListView activityList;

    private ArrayList<ActivityItem> activities;
    private ActivityItemAdapter activityitemadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUI();
        fetchTestData();
        adapterStuff();



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
        activities.add(ai1);

    }

    //Builds th User Interface from Xml and java objects
    private void buildUI() {
        setContentView(R.layout.homescreen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button to get the Activity Data
        button = findViewById(R.id.Change);
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