package com.example.atractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atractivity.Data.ReturnKeys;

public class CreateActivity extends AppCompatActivity {


    // boolean value that will determine if the time is min or max for the result
    private boolean minOrMax = true;
    private int colorCode = 1;

    private EditText activityName, hours, minutes;
    private Button creatinButton;
    private CheckBox notificationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createactivity);

        initUI();


    }

    private void initUI() {
        activityName = findViewById(R.id.nameofactivity);
        hours = findViewById(R.id.hoursedit);
        minutes = findViewById(R.id.minutesedit);


        notificationButton = findViewById(R.id.notification_button);
        notificationButton.setChecked(true);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfCheckedAndUncheck();
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

    private void checkIfCheckedAndUncheck() {
        if(notificationButton.isChecked()){
            notificationButton.setChecked(true);
        }
        else {
            notificationButton.setChecked(false);
        }

    }

    /*Creates an intent and adds the data that the calling activity requested then terminates the Activity
    * this will only happen if the dataset is complete
    * else there will be an error massage and termination without data
    * */
    private void returnActivityDataOnCall() {
        String name = getActivityName();
        boolean min = minOrMax;
        int hourreader = getInputAsInteger(hours);
        int minutesreader = getInputAsInteger(minutes);
        int givencolor = colorCode;
        boolean alertset;
        if (notificationButton.isChecked()) alertset = true;
        else alertset = false;
        Intent dataActivity = new Intent();
        dataActivity.putExtra(ReturnKeys.NAME_KEY, name);
        dataActivity.putExtra(ReturnKeys.MIN_KEY, min);
        dataActivity.putExtra(ReturnKeys.HOUR_KEY, hourreader);
        dataActivity.putExtra(ReturnKeys.MINUTES_KEY, minutesreader);
        dataActivity.putExtra(ReturnKeys.COLOR_KEY, givencolor);
        dataActivity.putExtra(ReturnKeys.ALERT_KEY, alertset);
        setResult(Activity.RESULT_OK, dataActivity);
        finish();
    }

    private String getActivityName() {
        if(activityName.getText().toString().equals("")){
            return "Unnamed Activity";
        } else {
            return activityName.getText().toString();
        }
    }

    private int getInputAsInteger(EditText input) {
        if (input.getText().toString().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(input.getText().toString());
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            //the first two cases are used to determin the decision between min or max time, so that it can be transfered in the requests answer/callback
            case R.id.minbox:
                if (checked)
                    minOrMax = true;
                    break;
            case R.id.maxbox:
                if (checked)
                    minOrMax = false;
                    break;
            //the other seven cases are used to determin the decision between the colors, so that it can be transfered in the requests answer/callback
            case R.id.option1:
                if (checked)
                    colorCode = 1;
                    break;
            case R.id.option2:
                if (checked)
                    colorCode = 2;
                    break;
            case R.id.option3:
                if (checked)
                    colorCode = 3;
                    break;
            case R.id.option4:
                if (checked)
                    colorCode = 4;
                    break;
            case R.id.option5:
                if (checked)
                    colorCode= 5;
                    break;
            case R.id.option6:
                if (checked)
                    colorCode = 6;
                    break;
            case R.id.option7:
                if (checked)
                    colorCode = 7;
                    break;
        }
    }



}
