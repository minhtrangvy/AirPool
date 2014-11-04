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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

public class EditGroupActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // TODO: get group ID that is being edited.
    Group currentGroup = new Group();

    String transPref = currentGroup.getTransPref();
    String college = currentGroup.getCollege();
    String airport = currentGroup.getAirport();
    Boolean toAirport = currentGroup.getToAirport();
    String date = currentGroup.getDate();
    String time = currentGroup.getTime();


    Button saveEditButton, cancelEditButton, selectDateButton, selectTimeButton;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;

    // variables to save user selected date and time
    public  int year,month,day,hour,minute;
    // declare  the variables to Show/Set the date and time when Time and  Date Picker Dialog first appears
    private int mYear, mMonth, mDay,mHour,mMinute;

    public EditGroupActivity() {
        // Assign current Date and Time Values to Variables
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    Button saveEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(Group.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);


        // Access the Button defined in EditGroup XML
        // and listen for it here
        saveEditButton = (Button) findViewById(R.id.viewGroupSave_button);
        saveEditButton.setOnClickListener(this);

        cancelEditButton = (Button) findViewById(R.id.viewGroupCancel_button);
        cancelEditButton.setOnClickListener(this);

        selectDateButton = (Button) findViewById(R.id.selectDate_button);
        selectDateButton.setOnClickListener(this);
        selectDateButton.setText("Departure Date: " + date);

        selectTimeButton = (Button) findViewById(R.id.selectTime_button);
        selectTimeButton.setOnClickListener(this);
        selectTimeButton.setText("Departure Time: " + time);


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
        getMenuInflater().inflate(R.menu.edit_group, menu);
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
                // TODO: Save changes with Parse
                currentGroup.setAirport(airport);
                currentGroup.setCollege(college);
                currentGroup.setDate(date);
                currentGroup.setTime(time);
                currentGroup.setTransPref(transPref);
                currentGroup.setToAirport(toAirport);
                Intent clickSaveEdits = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
                startActivity(clickSaveEdits);
                break;
            case R.id.viewGroupCancel_button:
                Intent clickCancel = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
                startActivity(clickCancel);
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
        // Need to check for which spinner is being called.
        transPref = parent.getItemAtPosition(pos).toString();
        Log.i("checkItem", transPref);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        transPref = parent.getItemAtPosition(4).toString();
        Log.i("checkItem", transPref);
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
                    date = ((month < 10) ? "0" : "") + month +"/"+
                            ((day < 10) ? "0" : "") + day+"/"+year;
                    // Set the Selected Date in Select date Button
                    selectDateButton.setText("Departure Date: " + date);
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
                    time = hour + ":" + ((minute < 10) ? "0" : "") +
                            minute + " " + twelveHrTimeStamp;
                    selectTimeButton.setText("Departure Time: " + time);
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

    @ParseClassName("Group")
    public class Group extends ParseObject {

        public Group() {
            // A default constructor is required.
        }

        public String getGroupID() {
            return getString("groupID");
        }

        public void setGroupID(String groupID) {
            put("groupID", groupID);
        }

        public String getDate() {
            return getString("date");
        }

        public void setDate(String date) {
            put("date", date);
        }

        public String getTime() {
            return getString("time");
        }

        public void setTime(String time) {
            put("time", time);
        }

        public String getTransPref() {
            return getString("transPref");
        }

        public void setTransPref(String transPref) {
            put("transPref", transPref);
        }

        public String getAirport() {
            return getString("airport");
        }

        public void setAirport(String airport) {
            put("airport", airport);
        }

        public String getCollege() {
            return getString("college");
        }

        public void setCollege(String college) {
            put("college", college);
        }

        public boolean getToAirport() {
            return getBoolean("toAirport");
        }

        public void setToAirport(Boolean toAirport) {
            put("toAirport", toAirport);
        }

        public JSONArray getMembers() {
            return getJSONArray("members");
        }

        public void setMembers(JSONArray members) {
            put("members", members);
        }

        public boolean getGroupOpen() {
            return getBoolean("groupOpen");
        }

        public void setGroupOpen(String groupOpen) {
            put("groupOpen", groupOpen);
        }
    }
}
