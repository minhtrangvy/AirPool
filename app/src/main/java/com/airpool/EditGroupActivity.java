package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.airpool.View.AirPoolSpinner;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;
import com.airpool.View.TransportationPreferenceSpinner;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.Date;

public class EditGroupActivity extends FragmentActivity implements View.OnClickListener,
        DatePickerFragment.OnDatePickedListener, TimePickerFragment.OnTimePickedListener {
    boolean isGroupExisting = false;
    Group groupBeingEdited;

    TransportationPreference transportationPreference;
    College college = null;
    Airport airport = null;
    Long departureTime = null;
    boolean isToAirport = true;
    TextView toFromCollege;

    Button createGroupButton, selectDateButton, selectTimeButton;

    DatePickerFragment dateFragment;
    TimePickerFragment timeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        Intent myIntent = getIntent();
        isGroupExisting = myIntent.getBooleanExtra("isGroupExisting", false);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();

        toFromCollege = (TextView) findViewById(R.id.to_from_college_text);
        toFromCollege.setText(getResources().getString(R.string.from));

        createGroupButton = (Button) findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(this);

        if (isGroupExisting) {
            setTitle(R.string.title_activity_edit_group);
            createGroupButton.setText(R.string.editGroup);

        } else {
            setTitle(R.string.title_activity_create_group);
            createGroupButton.setText(R.string.createGroup);
        }

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

        this.groupBeingEdited = null;
        if (isGroupExisting) {
            try {
                // Get the group that's passed in.
                ParseQuery<ParseObject> groupQuery = ParseQuery.getQuery("Group");
                groupBeingEdited = (Group) groupQuery.get(getIntent().getStringExtra("groupObjectId"));
            } catch (ParseException exception) {
                // Error.
            }
        }


        Spinner toFromSpinner = (Spinner) findViewById(R.id.toFrom_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
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
            public void onNothingSelected(AdapterView<?> arg0) {
                isToAirport = false;
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

        final TransportationPreferenceSpinner preferenceSpinner = (TransportationPreferenceSpinner)
                findViewById(R.id.trans_pref_spinner);
        preferenceSpinner.initializeSpinner(this);
        preferenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                transportationPreference = (TransportationPreference)
                        preferenceSpinner.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                transportationPreference = null;
            }
        });

        if (isGroupExisting) {
            AirPoolSpinner.AirPoolAdapter adapter =
                    (AirPoolSpinner.AirPoolAdapter) airportSpinner.getAdapter();
            airportSpinner.setSelection(adapter.getPosition(groupBeingEdited.getAirport()));

            adapter = (AirPoolSpinner.AirPoolAdapter) collegeSpinner.getAdapter();
            collegeSpinner.setSelection(adapter.getPosition(groupBeingEdited.getCollege()));

            adapter = (AirPoolSpinner.AirPoolAdapter) preferenceSpinner.getAdapter();
            preferenceSpinner.setSelection(adapter.getPosition(groupBeingEdited.getTransportationPreference()));


        }
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
        // Check for valid input.
        if (!dateFragment.isValidInput() || !timeFragment.isValidInput()) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toast_blank_spinner),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // If group is created, set all the variables.
        if (!isGroupExisting) {
            this.groupBeingEdited = new Group();
        }

        // Assemble the time of departure based on the spinner's values.
        groupBeingEdited.setTimeOfDeparture(new Date(
                dateFragment.getYear(), dateFragment.getMonth(), dateFragment.getDay(),
                timeFragment.getHour(), timeFragment.getMinute(), 0));

        groupBeingEdited.setAirport(airport);
        groupBeingEdited.setCollege(college);
        groupBeingEdited.setTransportationPreference(transportationPreference);
        groupBeingEdited.setIsToAirport(isToAirport);
        groupBeingEdited.setIsGroupOpen(true);

        groupBeingEdited.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    indicateEditSuccess();
                } else {
                    // Error.
                    indicateEditFailure();
                }
            }
        });

        // Make the association between Group and User if necessary.
        if (!isGroupExisting) {
            ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("User");
            userQuery.getInBackground("68xwdmZ1IE", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        ParseRelation<ParseObject> newGroupRelation = groupBeingEdited.getRelation("users");
                        newGroupRelation.add(object);
                        groupBeingEdited.saveInBackground();
                        indicateEditSuccess();
                    } else {
                        // Error.
                        indicateEditFailure();
                    }

                }
            });
        }

        Intent clickCreateGroup = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
        startActivity(clickCreateGroup);
    }

    public void indicateEditSuccess() {
        Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.toast_successfully_created_group),
                Toast.LENGTH_SHORT).show();
    }

    public void indicateEditFailure() {
        Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.toast_unsuccessfully_created_group),
                Toast.LENGTH_SHORT).show();
    }

    public void onTimePicked(int hour, int minute, String twelveHrTimeStamp) {
        selectTimeButton.setText("Departure Time: " + hour + ":" + ((minute < 10) ? "0" : "") +
                minute + " " + twelveHrTimeStamp);
    }

    public void onDatePicked(int year, int month, int day) {
        selectDateButton.setText("Departure Date: " + month + "/" + day + "/" + year);
    }
}
