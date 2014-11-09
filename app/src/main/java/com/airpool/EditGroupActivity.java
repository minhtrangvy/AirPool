package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.airpool.Fragment.DatePickerFragment;
import com.airpool.Fragment.TimePickerFragment;
import com.airpool.Model.Group;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;
import com.airpool.View.TransportationPreferenceSpinner;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;

import java.util.List;

public class EditGroupActivity extends FragmentActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, DatePickerFragment.OnDatePickedListener,
        TimePickerFragment.OnTimePickedListener{
    String pref, transPref, college, airport, date, time;
    Boolean toAirport;

    Button createGroupButton, selectDateButton, selectTimeButton;

    DatePickerFragment dateFragment;
    TimePickerFragment timeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();

        // Access the Button defined in login XML
        // and listen for it here
        createGroupButton = (Button) findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(this);

        selectDateButton = (Button) findViewById(R.id.selectDate_button);
        selectDateButton.setOnClickListener(this);
        selectDateButton.setText("Departure Date");

        selectTimeButton = (Button) findViewById(R.id.selectTime_button);
        selectTimeButton.setOnClickListener(this);
        selectTimeButton.setText("Departure Time");


        // Set the spinner requirements
        Spinner toFromSpinner = (Spinner) findViewById(R.id.toFrom_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> toFromAdapter = ArrayAdapter.createFromResource(this,
                R.array.toFrom_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        toFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        toFromSpinner.setAdapter(toFromAdapter);
        toFromSpinner.setOnItemSelectedListener(this);

        CollegeSpinner collegeSpinner = (CollegeSpinner) findViewById(R.id.college_spinner);
        collegeSpinner.initializeSpinner(this);
        collegeSpinner.setOnItemSelectedListener(this);

        AirportSpinner airportSpinner = (AirportSpinner) findViewById(R.id.airport_spinner);
        airportSpinner.initializeSpinner(this);
        airportSpinner.setOnItemSelectedListener(this);

        TransportationPreferenceSpinner preferenceSpinner = (TransportationPreferenceSpinner) findViewById(R.id.trans_pref_spinner);
        preferenceSpinner.initializeSpinner(this);
        preferenceSpinner.setOnItemSelectedListener(this);
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
            case R.id.create_group_button:
                // If group is created, set all the variables.
                final Group newGroup = new Group();

//                newGroup.setAirport(airport);
//                newGroup.setCollege(college);
////                newGroup.setDate(date);
//                newGroup.setTimeOfDeparture(time);
//                newGroup.setTransportationPreference(transPref);
//                newGroup.setAirport(toAirport);

                newGroup.saveInBackground();
                String parseId = newGroup.getObjectId();
//                newGroup.setGroupID(parseId);

                // Finds the user parse object to create relation with
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("userID", "1");//_userId);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
//                            ParseRelation<ParseObject> userRelation = newGroup.getRelation("users");
//                            userRelation.add(user);
                        } else {
                            // Error
                        }
                    }
                });

                newGroup.saveInBackground();

                Intent clickCreateGroup = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
                startActivity(clickCreateGroup);
                break;
            case R.id.selectDate_button:
                this.dateFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.selectTime_button:
                this.timeFragment.show(getSupportFragmentManager(), "timePicker");
                break;
        }
    }


    // Registers the item selected in the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        pref = parent.getItemAtPosition(pos).toString();
        switch(view.getId()) {
            case R.id.airport_spinner:
                airport = pref;
                break;
            case R.id.college_spinner:
                college = pref;
                break;
            case R.id.toFrom_spinner:
                if(pref == "To the Airport")
                {
                    toAirport = true;
                } else {
                    toAirport = false;
                }
                break;
            case R.id.trans_pref_spinner:
                transPref = pref;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        transPref = "TBA";
        airport = "TBA";
        college = "TBA";
        toAirport = null;
    }

    public void onTimePicked(int hour, int minute, String twelveHrTimeStamp) {
        selectTimeButton.setText("Departure Time: " + hour + ":" + ((minute < 10) ? "0" : "") +
                minute + " " + twelveHrTimeStamp);
    }

    public void onDatePicked(int year, int month, int day) {
        selectDateButton.setText("Departure Date: " + month + "/" + day + "/" + year);
    }
}
