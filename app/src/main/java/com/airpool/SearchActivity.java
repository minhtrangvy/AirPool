package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.airpool.Fragment.DatePickerFragment;
import com.airpool.Fragment.TimePickerFragment;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;

import java.util.Calendar;


public class SearchActivity extends FragmentActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, DatePickerFragment.OnDatePickedListener,
        TimePickerFragment.OnTimePickedListener{
    Button searchButton, selectDateButton, selectTimeButton;

    DatePickerFragment dateFragment;
    TimePickerFragment timeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();

        // Access the Button defined in search XML
        // and listen for it here
        searchButton = (Button) findViewById(R.id.search_results_button);
        searchButton.setOnClickListener(this);

        selectDateButton = (Button) findViewById(R.id.selectDate_button);
        selectDateButton.setOnClickListener(this);

        selectTimeButton = (Button) findViewById(R.id.selectTime_button);
        selectTimeButton.setOnClickListener(this);

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
            case R.id.search_results_button:
                Intent clickSearch = new Intent(SearchActivity.this, SearchResultsActivity.class);
                startActivity(clickSearch);
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
        String item = parent.getItemAtPosition(pos).toString();
        Log.i("checkItem", item);
        // TODO: Store the item selected in Parse
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO: Store the no preference item
    }

    public void onTimePicked(int hour, int minute, String twelveHrTimeStamp) {
        selectTimeButton.setText("Departure Time: " + hour + ":" + ((minute < 10) ? "0" : "") +
                minute + " " + twelveHrTimeStamp);
    }

    public void onDatePicked(int year, int month, int day) {
        selectDateButton.setText("Departure Date: " + month + "/" + day + "/" + year);
    }
}
