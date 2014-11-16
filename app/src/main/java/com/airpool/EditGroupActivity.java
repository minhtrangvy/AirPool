package com.airpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.airpool.Model.User;
import com.airpool.View.AirPoolSpinner;
import com.airpool.View.AirportSpinner;
import com.airpool.View.CollegeSpinner;
import com.airpool.View.TransportationPreferenceSpinner;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

            dateFragment.initializeDay(groupBeingEdited.getTimeOfDeparture().get(Calendar.YEAR),
                    groupBeingEdited.getTimeOfDeparture().get(Calendar.MONTH),
                    groupBeingEdited.getTimeOfDeparture().get(Calendar.DAY_OF_MONTH));

            timeFragment.initializeTime(groupBeingEdited.getTimeOfDeparture().get(Calendar.HOUR_OF_DAY),
                    groupBeingEdited.getTimeOfDeparture().get(Calendar.MINUTE));

            onDatePicked(groupBeingEdited.getTimeOfDeparture());
            onTimePicked(groupBeingEdited.getTimeOfDeparture());

        }
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
        Calendar calendar = dateFragment.getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, timeFragment.getHour());
        calendar.set(Calendar.MINUTE, timeFragment.getMinute());

        groupBeingEdited.setTimeOfDeparture(calendar);

        groupBeingEdited.setAirport(airport);
        groupBeingEdited.setCollege(college);
        groupBeingEdited.setTransportationPreference(transportationPreference);
        groupBeingEdited.setIsToAirport(isToAirport);
        groupBeingEdited.setIsGroupOpen(true);
        groupBeingEdited.setIsActive(true);

        groupBeingEdited.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    GlobalUser context = (GlobalUser) getApplicationContext();
                    User currentUser = context.getCurrentUser();

                    // Make the association between Group and User if necessary.
                    if (!isGroupExisting) {

                        ParseRelation<ParseObject> newGroupRelation = groupBeingEdited.getRelation("users");
                        newGroupRelation.add(currentUser);
                        groupBeingEdited.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    indicateEditSuccess();
                                    startGroupActivity();
                                    finish();
                                } else {
                                    indicateEditFailure();
                                }
                            }
                        });
                        indicateEditSuccess();
                        startGroupActivity();
                        finish();
                    } else {
                        indicateEditSuccess();
                        startGroupActivity();
                    }
                } else {
                    // Error.
                    indicateEditFailure();
                }


            }
        });
    }

    public void indicateEditSuccess() {
        String message;
        if (isGroupExisting) {
            message = getResources().getString(R.string.toast_successfully_edited_group);
        } else {
            message = getResources().getString(R.string.toast_successfully_created_group);
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void indicateEditFailure() {
        String message;
        if (isGroupExisting) {
            message = getResources().getString(R.string.toast_unsuccessfully_edited_group);
        } else {
            message = getResources().getString(R.string.toast_unsuccessfully_created_group);
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void startGroupActivity() {
        Intent clickCreateGroup = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
        clickCreateGroup.putExtra("groupId", groupBeingEdited.getObjectId());
        startActivity(clickCreateGroup);
    }

    public void onTimePicked(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        selectTimeButton.setText("Departure Time: " + format.format(calendar.getTime()));
    }

    public void onDatePicked(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        selectDateButton.setText("Departure Date: " + format.format(calendar.getTime()));
    }
}
