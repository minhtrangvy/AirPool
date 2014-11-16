package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airpool.Fragment.DatePickerFragment;
import com.airpool.Fragment.PreferencePickerFragment;
import com.airpool.Fragment.TimePickerFragment;
import com.airpool.Model.Airport;
import com.airpool.Model.College;
import com.airpool.Model.TransportationPreference;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class SearchActivity extends FragmentActivity implements View.OnClickListener,
        DatePickerFragment.OnDatePickedListener,
        TimePickerFragment.OnTimePickedListener,
        PreferencePickerFragment.OnPreferencePickedListener {
    College college = null;
    Airport airport = null;
    boolean isToAirport = true;
    List<TransportationPreference> selectedPreferences;
    List<TransportationPreference> transportationPreferences;

    Button searchButton, selectDateButton, selectTimeButton, selectPreferencesButton;
    TextView toFromCollege;

    DatePickerFragment dateFragment;
    TimePickerFragment timeFragment;
    PreferencePickerFragment preferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();
        preferenceFragment = new PreferencePickerFragment();

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

        selectPreferencesButton = (Button) findViewById(R.id.selectPreferences_button);
        selectPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceFragment.updateSelectedTransportationPreferences((ArrayList<TransportationPreference>) selectedPreferences);
                preferenceFragment.show(getSupportFragmentManager(), "preferencesPicker");
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

        transportationPreferences =
                new ArrayList<TransportationPreference>(Arrays.asList(TransportationPreference.values()));
        selectedPreferences = new ArrayList<TransportationPreference>();
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

        // Assemble the time of departure based on the spinner's values.
        Calendar calendar = dateFragment.getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, timeFragment.getHour());
        calendar.set(Calendar.MINUTE, timeFragment.getMinute());
        Log.v("hai", calendar.toString());

        clickSearch.putExtra("timeOfDeparture", calendar.getTimeInMillis());

        ArrayList<String> preferences = new ArrayList<String>();
        for (TransportationPreference preference : selectedPreferences) {
            preferences.add(preference.name());
        }
        clickSearch.putStringArrayListExtra("preferences", preferences);

        startActivity(clickSearch);
    }

    public void onTimePicked(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        selectTimeButton.setText("Departure Time: " + format.format(calendar.getTime()));
    }

    public void onDatePicked(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        selectDateButton.setText("Departure Date: " + format.format(calendar.getTime()));
    }

    public void onPreferencePicked(int which, boolean isChecked) {
        TransportationPreference preference = transportationPreferences.get(which);
        if (which == preferenceFragment.getNoPreferenceWhich()) {
            selectedPreferences.clear();
        } else {
            if (isChecked) {
                selectedPreferences.add(preference);
            } else {
                selectedPreferences.remove(preference);
            }
        }
    }
}
