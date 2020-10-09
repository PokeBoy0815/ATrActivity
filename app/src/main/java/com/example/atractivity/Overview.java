package com.example.atractivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.atractivity.Data.Database.ActivityItemDatabaseHelper;

import java.util.Calendar;

public class Overview extends AppCompatActivity {

    private long currentDate;

    private ActivityItemDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        initUI();
        initData();



    }

    private void initData() {
        databaseHelper = new ActivityItemDatabaseHelper(this);
        currentDate = Calendar.getInstance().getTimeInMillis();
    }

    private void initUI() {

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
            startActivity(intent);        }
        if (id == R.id.stats) {
            Intent intent = new Intent(Overview.this, Overview.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /** Vergleichen zweier calendar objekte ob sie am selben tag sind
     * Calendar cal1 = Calendar.getInstance();
     * Calendar cal2 = Calendar.getInstance();
     * cal1.setTime(date1);
     * cal2.setTime(date2);
     * boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
     *                   cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
     *
     * */
}
