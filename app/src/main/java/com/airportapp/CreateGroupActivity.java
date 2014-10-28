package com.airportapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.parse.Parse;
import com.parse.ParseAnalytics;
//import com.parse.Parse;
//import com.parse.ParseAnalytics;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateGroupActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button createGroupButton, backButton, selectDateButton, selectTimeButton;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;

    // variables to save user selected date and time
    public  int year,month,day,hour,minute;
    // declare  the variables to Show/Set the date and time when Time and  Date Picker Dialog first appears
    private int mYear, mMonth, mDay,mHour,mMinute;

    public CreateGroupActivity() {
        // Assign current Date and Time Values to Variables
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        // Access the Button defined in login XML
        // and listen for it here
        createGroupButton = (Button) findViewById(R.id.viewGroup_button);
        createGroupButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.searchResultsList_button);
        backButton.setOnClickListener(this);

        selectDateButton = (Button) findViewById(R.id.selectDate_button);
        selectDateButton.setOnClickListener(this);

        selectTimeButton = (Button) findViewById(R.id.selectTime_button);
        selectTimeButton.setOnClickListener(this);


        // Set the spinner requirements
        Spinner transPrefSpinner = (Spinner) findViewById(R.id.transPrefs_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transPrefs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        transPrefSpinner.setAdapter(adapter);
        transPrefSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewGroup_button:
                Intent clickCreateGroup = new Intent(CreateGroupActivity.this, ViewGroupActivity.class);
                startActivity(clickCreateGroup);
                break;
            case R.id.searchResultsList_button:
                Intent clickBack = new Intent(CreateGroupActivity.this, SearchResultsListActivity.class);
                startActivity(clickBack);
                break;
            case R.id.selectDate_button:
                onCreated(DATE_DIALOG_ID).show();
                break;
            case R.id.selectTime_button:
                onCreated(TIME_DIALOG_ID).show();
                break;
        }
    }


    // Registers the item selected in the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();
        Log.i("checkItem", item);
        // TODO: Store the item selected in Parse
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO: Store the no preference item
    }

    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    selectDateButton.setText("Departure Date: " + ((month < 10) ? "0" : "") + month +"/"+
                            ((day < 10) ? "0" : "") + day+"/"+year);
                }
            };

    // Register  TimePickerDialog listener
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hour = hourOfDay;
                    minute = min;
                    String twelveHrTimeStamp = "am";
                    // Set the Selected Date in Select date Button
                    if (hour > 12) {
                        hour = hour % 12;
                        twelveHrTimeStamp = "pm";
                    }
                    else if (hour ==0) {
                        hour = 12;
                    }
                    selectTimeButton.setText("Departure Time: " + hour + ":" + ((minute < 10) ? "0" : "") +
                            minute + " " + twelveHrTimeStamp);
                }
            };


    // Method automatically gets Called when you call showDialog()  method
    protected Dialog onCreated(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // create a new DatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TimePickerDialog with values you want to show
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

}
