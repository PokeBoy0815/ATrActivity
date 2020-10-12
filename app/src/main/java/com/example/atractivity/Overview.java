package com.example.atractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.Data.Database.DailyTimeCountQueryResultListener;
import com.example.atractivity.Data.Database.DaylyTimeCount;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Overview extends AppCompatActivity {

    private String currentDate;
    private Calendar calendar;
    private ArrayList<ActivityItem> activityItems = new ArrayList<>();
    private HashMap<String, Integer> timesForActivityNames = new HashMap<String, Integer>();

    private ActivityItemDatabaseHelper databaseHelper;

    private int activeNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                initUI();
            }
        }, 35);

    }

    private void initData() {
        calendar = Calendar.getInstance();
        //produces a Date String that can be found in the database
        currentDate = ""+ calendar.DAY_OF_MONTH + calendar.MONTH + calendar.YEAR+"";
        //initializes the ArrayList for the getItemsOfCurrentDay Method
        databaseHelper = new ActivityItemDatabaseHelper(this);
        databaseHelper.getAllActivityItemsFromRoom(new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {

            }

            @Override
            public void onListResult(List<ActivityItem> aitems) {
                activityItems.addAll(aitems);
                Log.e("size", "" + activityItems.size() + "");
                databaseHelper.getDailyTimeCount(currentDate, activityItems.get(0).getActivityName(), new DailyTimeCountQueryResultListener() {
                    @Override
                    public void onListResult(List<DaylyTimeCount> timeCounts) {

                    }

                    @Override
                    public int onIntegerResult(int i) {
                        timesForActivityNames.put(activityItems.get(0).getActivityName(), i);
                        Log.e("map", "" + timesForActivityNames.get(activityItems.get(0)) + "," + i + "");
                        return 0;
                    }
                });
            }
            //for (int i = 0; i < activityItems.size(); i++) {
        });
    }

    private void initUI() {
        PieChart todayPieChart = findViewById(R.id.today_pie_chart);

        ArrayList<PieEntry> testData = new ArrayList<>();
        testData.add(new PieEntry(483, "Sport"));
        testData.add(new PieEntry(1476, "Netflix"));
        testData.add(new PieEntry(2675, "Android"));
        String itemName = activityItems.get(0).getActivityName();
        //testData.add(new PieEntry(timesForActivityNames.get(itemName), itemName));
        testData.add(new PieEntry(2000, itemName));

        PieDataSet todayDataSet = new PieDataSet(testData, "Test");
        todayDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
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


}
