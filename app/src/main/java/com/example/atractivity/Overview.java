package com.example.atractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atractivity.Data.Database.DailyTimeCountQueryResultListener;
import com.example.atractivity.Data.Database.DaylyTimeCount;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Overview extends AppCompatActivity {

    private String currentDate;
    private Calendar calendar;
    private ArrayList<String> namesOfActivities = new ArrayList<>();

    private ActivityItemDatabaseHelper databaseHelper;

    private int activeViewNumber = 0;
    private int maxViewNumber = 5; //maxViewNumber = amount of Activities, 0 if 0 Activites exist

    private Button leftButton;
    private Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        initData();
        initUI();

        // Create View Pager
    }

    private void initData() {
        databaseHelper = new ActivityItemDatabaseHelper(this);
        databaseHelper.getAllActivityItemsFromRoom(new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {
            }
            @Override
            public void onListResult(List<ActivityItem> aitems) {
                for (int i = 0; i < aitems.size(); i++) {
                    namesOfActivities.add(aitems.get(i).getActivityName());
                }
            }
        });
        calendar = Calendar.getInstance();
        //produces a Date String that can be found in the database
        currentDate = ""+ calendar.DAY_OF_MONTH + calendar.MONTH + calendar.YEAR+"";
        //initializes the ArrayList for the getItemsOfCurrentDay Method
    }

    private void initUI() {
        initButtons();
        PieChart todayPieChart = findViewById(R.id.today_pie_chart);

        ArrayList<PieEntry> testData = new ArrayList<>();
        //PieEntry pieEntry = new PieEntry();
        testData.add(new PieEntry(483, "Sport"));
        testData.add(new PieEntry(1476, "Netflix"));
        testData.add(new PieEntry(2675, "Android"));

        PieDataSet todayDataSet = new PieDataSet(testData, "Test");
        int[] colors = {Color.GREEN, Color.YELLOW, Color.rgb(100,0,255)};
        todayDataSet.setColors(colors);
        todayDataSet.setValueTextColor(Color.BLACK);
        todayDataSet.setValueTextSize(16f);

        PieData todayPieData = new PieData(todayDataSet);

        todayPieChart.setData(todayPieData);
        todayPieChart.getDescription().setEnabled(false);
        todayPieChart.setCenterText("test");
        todayPieChart.animate();
    }

    //Method to get all Daily Time Obkéjects from db for the actual day
    private void getItemsOfCurrentDayFromDB(){

        databaseHelper.getAllTimeItemsOfADay(currentDate, new DailyTimeCountQueryResultListener() {
            @Override
            public void onListResult(List<DaylyTimeCount> timeCounts) {

            }

            @Override
            public int onIntegerResult(int minutes) {

                return minutes;
            }
        });
    }

    //Method to return all the time in minutes that was spent for one activity at one day
    private int getTimeSpentOnActivityFromDB(String currentDate, String activityName){
        databaseHelper.getDailyTimeCount(currentDate, activityName, new DailyTimeCountQueryResultListener() {
            @Override
            public void onListResult(List<DaylyTimeCount> timeCounts) {

            }

            @Override
            public int onIntegerResult(int minutes) {
                return minutes;
            }
        });
        return 0;
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
        if (id == R.id.home) {
            Intent intent = new Intent(Overview.this, Homescreen.class);
            startActivity(intent);
        }
        if (id == R.id.stats) {
            Intent intent = new Intent(Overview.this, Overview.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /** Vergleichen zweier calendar objekte ob sie am selben tag sind
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(date1);
      cal2.setTime(date2);
      boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

      */

    private int[] getColors(){
    //create array with the colors from the database
        return null;
    }

    /** Set up the buttons. */
    private void initButtons(){
        leftButton = findViewById(R.id.button_left);
        rightButton = findViewById(R.id.button_right);

        setButtonEnabledStates();

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLeftButtonClicked();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRightButtonClicked();
            }
        });
    }
    /** Button Methods. */
    private void onLeftButtonClicked(){
        // do something
        activeViewNumber = activeViewNumber - 1 ;
        setButtonEnabledStates();
    }
    private void onRightButtonClicked(){
        //do something
        activeViewNumber = activeViewNumber + 1;
        setButtonEnabledStates();
    }
    /** Method to enable and disable the buttons based on the showed View. */
    private void setButtonEnabledStates(){
        if (activeViewNumber == maxViewNumber){rightButton.setEnabled(false);}
        else {rightButton.setEnabled(true);}
        if (activeViewNumber == 0){leftButton.setEnabled(false);}
        else {leftButton.setEnabled(true);}
    }

}
