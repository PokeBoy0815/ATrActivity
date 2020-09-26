package com.example.atractivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CreateActivity extends AppCompatActivity {


    private FloatingActionButton notificationButton;
    private EditText activityName;
    private Button creatinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createactivity);



        activityName = findViewById(R.id.nameofactivity);

        notificationButton = findViewById(R.id.fab);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        creatinButton = findViewById(R.id.createbutton);
        creatinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnActivityDataOnCall();
            }
        });


    }

    /*Creates an intent and adds the data that the calling activity requested then terminates the Activity
    * this will only happen if the dataset is complete
    * else there will be an error massage and termination without data
    * */
    private void returnActivityDataOnCall() {



        finish();
    }


    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }


}
