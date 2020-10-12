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
import android.widget.TextView;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atractivity.Data.Database.DailyTimeCountQueryResultListener;
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
                // Do after 5s = 5000ms
                initUI();
            }
        }, 35);

    }

    /** This methods are used to get all the needed Data. */
    private void initData() {
        Calendar calendar = Calendar.getInstance();
        //initializes the ArrayList for the getAllActivityItemsFromRoom Method
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

    /** All methods for building the UI and the charts are called. */
    private void initUI() {
        setMaxViewNumber();
        initButtons();
        initPieChart();
        initBarChart();
        updateView();
    }

    /** Override of the methods of the actionbar. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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



    /** Update the view. This method gets called by the buttons and on first access of the activity. */
    private void updateView(){
        if (activeViewNumber==0){
            updatePieChartData();
            pieChart.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();
        }
        else{
            updateBarChartData();
            pieChart.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
            pieChart.notifyDataSetChanged();
            barChart.invalidate();

        }
    }

    /** Depending on updateView one of this two methods gets called.
     * They insert the Data into the Chart and update the view, so the needed chart is displayed. */
    private void updatePieChartData(){
        pieChart.clearValues();
        pieChart.clearAnimation();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < activityItems.size(); i++) {
            ActivityItem activityItem = activityItems.get(i);
            String activityName = activityItem.getActivityName();
            int value;
            if(lastSevenDays.get(0).get(activityName) != null){value = lastSevenDays.get(0).get(activityName);}
            else{value = 0;}
            pieEntries.add(new PieEntry(value,activityName));
            Log.e("Es wurde erstellt",activityName);
            colors.add(getColor(activityItem));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, this.getString(string.overview_title_today));
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animate();
        TextView title = (TextView)findViewById(R.id.chart_title);
        title.setText(string.overview_title_today);
    }
    private void updateBarChartData(){
        barChart.clearValues();
        barChart.clearAnimation();
        ActivityItem activityItem = activityItems.get(activeViewNumber-1);
        String activityName = activityItem.getActivityName();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int value;
            if(lastSevenDays.get(i).get(activityName) != null){value = lastSevenDays.get(i).get(activityName);}
            else{value = 0;}
            int dayValue = (-i+7);
            barEntries.add(new BarEntry(dayValue,value));
            Log.e("Es wurde erstellt",activityName);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, this.getString(string.default_chart_title));
        barDataSet.setColor(getColor(activityItem));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(20);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.animateY(1500);
        TextView title = (TextView)findViewById(R.id.chart_title);
        title.setText(activityName);
    }

    /** The following methods create the needed charts. They set up the pie and bar chart for further usage. */
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

    /** The following methods are creating and setting up the buttons and their listeners. */
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
    /** This methods are used in the button on click listeners. */
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

    /** This method sets the maxViewNumber depending on the amount of activityItems. */
    private void setMaxViewNumber(){
        maxViewNumber = activityItems.size();
    }

    /** This method gives the custom color for the ActivityItem. */
    private int getColor(ActivityItem item) {
        int color = item.getColor();
        switch (color) {
            case 1:
                return Color.parseColor("#FFEB3B");
            case 2:
                return Color.parseColor("holo_orange_dark");
            case 3:
                return Color.parseColor("#FFCC0000");
            case 4:
                return Color.parseColor("#FFAA66CC");
            case 5:
                return Color.parseColor("#FF0099CC");
            case 6:
                return Color.parseColor("#FF00DDFF");
            case 7:
                return Color.parseColor("#FF99CC00");
            default:
                return Color.parseColor("#FFCC0000");
        }
    }

}
