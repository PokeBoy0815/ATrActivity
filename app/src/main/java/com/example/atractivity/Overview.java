package com.example.atractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.atractivity.R.string;


public class Overview extends AppCompatActivity {

    private String currentDate;
    private Calendar calendar;


    private ActivityItemDatabaseHelper databaseHelper;
    ArrayList<ActivityItem> activityItems  = new ArrayList<>();
    ArrayList<HashMap<String, Integer>> lastSevenDays = new ArrayList<>();
    HashMap<String, Integer> timesForActivityNames = new HashMap<>();


    private int activeViewNumber = 0;
    private int maxViewNumber = 0;
    private Button leftButton;
    private Button rightButton;
    private BarChart barChart;
    private PieChart pieChart;


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
                Log.e("size", "" + activityItems.size() + ", shit");
                setArrayWithHashmapsPerDay();
            }
        });

    }

    private void setArrayWithHashmapsPerDay() {
        for (int a = 0; a < 7; a++) {
            Calendar c = Calendar.getInstance();
            timesForActivityNames.clear();
            c.add(c.DATE, -a);
            String date = ""+ c.DAY_OF_MONTH + c.MONTH + c.YEAR+"";
            for (int i = 0; i < activityItems.size(); i++) {
                final int e = i;


                databaseHelper.getDailyTimeSumForItem(date, activityItems.get(e).getActivityName(), new DailyTimeCountQueryResultListener() {

                    @Override
                    public void onListResult(List<DaylyTimeCount> timeCounts) {

                    }

                    @Override
                    public int onIntegerResult(int i) {
                        timesForActivityNames.put(activityItems.get(e).getActivityName(), i);
                        Log.e("map", "" + activityItems.get(e).getActivityName() + "," + i + "");
                        return 0;
                    }
                });
                lastSevenDays.add(timesForActivityNames);
            }

        }
    }

    private void initUI() {
        setMaxViewNumber();
        initButtons();
        initPieChart();
        initBarChart();
        updateView();
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
        databaseHelper.getDailyTimeSumForItem(currentDate, activityName, new DailyTimeCountQueryResultListener() {
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

    /** Update the view. This method gets called by the buttons and on first access of the activity. */
    private void updateView(){
        if (activeViewNumber==0){
            updatePieChartData();
            pieChart.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
            pieChart.invalidate();
        }
        else{
            updateBarChartData();
            pieChart.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
            barChart.invalidate();
        }
    }

    private void updatePieChartData(){
        pieChart.clearValues();
        pieChart.clearAnimation();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < activityItems.size(); i++) {
            String activityName = activityItems.get(i).getActivityName();
            int value;
            if(lastSevenDays.get(0).get(activityName) != null){value = lastSevenDays.get(0).get(activityName);}
            else{value = 0;}
            pieEntries.add(new PieEntry(value,activityName));
            Log.e("Es wurde erstellt",activityName);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, this.getString(string.default_chart_title));
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animate();
    }
    private void updateBarChartData(){
        barChart.clearValues();
        barChart.clearAnimation();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String activityName = activityItems.get(activeViewNumber-1).getActivityName();
            int value;
            if(lastSevenDays.get(i).get(activityName) != null){value = lastSevenDays.get(i).get(activityName);}
            else{value = 0;}
            int dayValue = (-i+7);
            barEntries.add(new BarEntry(dayValue,value));
            Log.e("Es wurde erstellt",activityName);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, this.getString(string.default_chart_title));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(20);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1500);
    }

    /** Set up the pie and bar chart. */
    private void initPieChart(){
        pieChart = findViewById(R.id.pie_chart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet pieDataSet = new PieDataSet(pieEntries, this.getString(string.default_chart_title));
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();
        pieChart.setVisibility(View.GONE);
    }
    private void initBarChart(){
        barChart = findViewById(R.id.bar_chart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(barEntries, this.getString(string.default_chart_title));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(20);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animate();
        barChart.setVisibility(View.GONE);
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
        updateView();
    }
    private void onRightButtonClicked(){
        //do something
        activeViewNumber = activeViewNumber + 1;
        setButtonEnabledStates();
        updateView();
    }
    /** Method to enable and disable the buttons based on the showed View. */
    private void setButtonEnabledStates(){
        if (activeViewNumber == maxViewNumber){rightButton.setEnabled(false);}
        else {rightButton.setEnabled(true);}
        if (activeViewNumber == 0){leftButton.setEnabled(false);}
        else {leftButton.setEnabled(true);}
    }

    private void setMaxViewNumber(){
        maxViewNumber = activityItems.size();
    }


}
