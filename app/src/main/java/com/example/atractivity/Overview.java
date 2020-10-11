package com.example.atractivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.atractivity.Data.ActivityItem;
import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.ActivityItemQueryResultListener;
import com.example.atractivity.overviewFragments.OverviewPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;
import com.example.atractivity.Data.Database.DailyTimeCountQueryResultListener;
import com.example.atractivity.Data.Database.DaylyTimeCount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Overview extends AppCompatActivity {

    private String currentDate;
    private Calendar calendar;
    private ArrayList<DaylyTimeCount> dailys;

    private ActivityItemDatabaseHelper databaseHelper;


    OverviewPagerAdapter overviewPagerAdapter;
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        initUI();
        initData();

        overviewPagerAdapter = new OverviewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(overviewPagerAdapter);


    }

    private void initData() {
        databaseHelper = new ActivityItemDatabaseHelper(this);
        databaseHelper.getActivityItemByName("iuugjzv", new ActivityItemQueryResultListener() {
            @Override
            public void onResult(ActivityItem aitem) {
                Log.i("ISITEMTHERE",""+aitem.getMinutes()+"");
            }

            @Override
            public void onListResult(List<ActivityItem> aitems) {

            }
        });
        calendar = Calendar.getInstance();
        //produces a Date String that can be found in the database
        currentDate = ""+ calendar.DAY_OF_MONTH + calendar.MONTH + calendar.YEAR+"";
        //initializes the ArrayList for the getItemsOfCurrentDay Method
        dailys = new ArrayList<>();
    }

    private void initUI() {

    }

    //Method to get all Daily Time Obkéjects from db for the actual day
    private void getItemsOfCurrentDayFromDB(){

        databaseHelper.getAllTimeItemsOfADay(currentDate, new DailyTimeCountQueryResultListener() {
            @Override
            public void onListResult(List<DaylyTimeCount> timeCounts) {
                dailys.addAll(timeCounts);
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
