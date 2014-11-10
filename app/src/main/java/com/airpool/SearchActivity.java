package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airpool.Fragment.DatePickerFragment;
import com.airpool.Fragment.TimePickerFragment;
import com.airpool.Model.Airport;
import com.airpool.Model.College;
import com.airpool.Model.Group;
import com.airpool.Model.TransportationPreference;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchActivity extends FragmentActivity implements View.OnClickListener,
        DatePickerFragment.OnDatePickedListener,
        TimePickerFragment.OnTimePickedListener {
    TransportationPreference transportationPreference;
    College college = null;
    Airport airport = null;
    Long departureTime = null;
    boolean isToAirport = true;

    Button searchButton, selectDateButton, selectTimeButton;
    TextView toFromCollege;

    DatePickerFragment dateFragment;
    TimePickerFragment timeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();

        toFromCollege = (TextView) findViewById(R.id.to_from_college_text);
        toFromCollege.setText(getResources().getString(R.string.from));

        searchButton = (Button) findViewById(R.id.search_results_button);
        searchButton.setOnClickListener(this);

        selectDateButton = (Button) findViewById(R.id.selectDate_button);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        selectTimeButton = (Button) findViewById(R.id.selectTime_button);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        Spinner toFromSpinner = (Spinner) findViewById(R.id.toFrom_spinner);
        ArrayAdapter<CharSequence> toFromAdapter = ArrayAdapter.createFromResource(this,
                R.array.toFrom_array, android.R.layout.simple_spinner_item);
        toFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toFromSpinner.setAdapter(toFromAdapter);
        toFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                // The text in the first position is assumed to be "To Airport."
                isToAirport = position == 0;

                if (isToAirport) {
                    toFromCollege.setText(getResources().getString(R.string.from));
                } else {
                    toFromCollege.setText(getResources().getString(R.string.to));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isToAirport = true;
                toFromCollege.setText(getResources().getString(R.string.from));
            }
        });

        final CollegeSpinner collegeSpinner = (CollegeSpinner) findViewById(R.id.college_spinner);
        collegeSpinner.initializeSpinner(this);
        collegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                college = (College) collegeSpinner.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                college = null;
            }
        });

        final AirportSpinner airportSpinner = (AirportSpinner) findViewById(R.id.airport_spinner);
        airportSpinner.initializeSpinner(this);
        airportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                airport = (Airport) airportSpinner.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                airport = null;
            }
        });
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
        if (!dateFragment.isValidInput() || !timeFragment.isValidInput()) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_blank_spinner),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Send the list of groupIds that match the search over to the search results.
        Intent clickSearch = new Intent(SearchActivity.this, SearchResultsActivity.class);

        // Send over the search criteria.
        clickSearch.putExtra("airport", airport.name());
        clickSearch.putExtra("isToAirport", isToAirport);
        clickSearch.putExtra("college", college.name());
        clickSearch.putExtra("departureDate", new Date(
                dateFragment.getYear(), dateFragment.getMonth(), dateFragment.getDay(),
                timeFragment.getHour(), timeFragment.getMinute(), 0).getTime());

        startActivity(clickSearch);
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
