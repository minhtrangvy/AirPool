package com.airpool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airpool.Adapter.GroupUsersAdapter;
import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ViewGroupActivity extends Activity implements View.OnClickListener {
    Group group = null;

    TextView departureTimeText;
    TextView airportText;
    TextView collegeText;
    TextView transportationPreferenceText;

    ListView groupMembersList;
    GroupUsersAdapter groupMembersAdapter;
    ArrayList<User> groupMembers;

    Button wallButton, joinButton, editButton, leaveButton, openCloseButton;
    boolean isUserMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        departureTimeText = (TextView) findViewById(R.id.departure_time_text);
        airportText = (TextView) findViewById(R.id.airport_text);
        collegeText = (TextView) findViewById(R.id.college_text);
        transportationPreferenceText = (TextView) findViewById(R.id.mode_of_transportation_text);

        wallButton = (Button) findViewById(R.id.groupWall_button);
        wallButton.setOnClickListener(this);

        joinButton = (Button) findViewById(R.id.join_group_button);
        joinButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.edit_group_button);
        editButton.setOnClickListener(this);

        openCloseButton = (Button) findViewById(R.id.open_close_button);
        openCloseButton.setOnClickListener(this);

        leaveButton = (Button) findViewById(R.id.leave_group_button);
        leaveButton.setOnClickListener(this);

        groupMembersList = (ListView) findViewById(R.id.group_members_list);

        // Get the group object ID that was passed in, get the group object, and populate
        // tne necessary views.
        groupMembers = new ArrayList<User>();
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
            group = (Group) query.get(getIntent().getStringExtra("groupId"));

            String airportTextContents;
            Resources resources = getResources();

            if (group.getIsToAirport()) {
                airportTextContents = resources.getString(R.string.going_to_airport);
            } else {
                airportTextContents = resources.getString(R.string.leaving_from_airport);
            }

            departureTimeText.setText(group.getTimeOfDepartureString());
            airportText.setText(String.format(airportTextContents, group.getAirport().getAirportName()));
            collegeText.setText(group.getCollege().getFullName());
            transportationPreferenceText.setText(
                    String.format(resources.getString(R.string.mode_of_transportation),
                    group.getTransportationPreference().getPreferenceName()));

            // Populate the open-close button.
            if (group.getIsGroupOpen()) {
                openCloseButton.setText(R.string.close_group);
            } else {
                openCloseButton.setText(R.string.open_group);
            }

            // Populate the list of users.
            ParseRelation relation = group.getRelation("users");

            query = relation.getQuery();
            List<ParseObject> users = query.find();

            GlobalUser context = (GlobalUser) getApplicationContext();
            User currentUser = context.getCurrentUser();
            Log.i("currentUserID =  ", currentUser.getObjectId());

            // Do not know of any other way to cast list of objects.
            for(ParseObject user : users) {
                User userToAdd = (User) user;
                groupMembers.add(userToAdd);
                if (userToAdd.getObjectId().equals(currentUser.getObjectId())) {
                    isUserMember = true;
                }
            }
        } catch (ParseException exception) {
            // Error.
        }

        // Populate the list view.
        groupMembersAdapter = new GroupUsersAdapter(this, android.R.layout.simple_list_item_1,
                groupMembers);
        groupMembersList.setAdapter(groupMembersAdapter);

        if (isUserMember) {
            // Show the edit group and group wall buttons.
            wallButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            leaveButton.setVisibility(View.VISIBLE);
            openCloseButton.setVisibility(View.VISIBLE);
        } else {
            joinButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        GlobalUser context = (GlobalUser) getApplicationContext();
        User currentUser = context.getCurrentUser();

        switch (view.getId()) {
            case R.id.groupWall_button:
                Intent goToWall = new Intent(ViewGroupActivity.this, GroupWallActivity.class);
                startActivity(goToWall);
                break;
            case R.id.join_group_button:
                ParseRelation<ParseObject> newGroupRelation = group.getRelation("users");
                newGroupRelation.add(currentUser);
                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.toast_successfully_created_group),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Error.
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.toast_unsuccessfully_joined_group),
                                    Toast.LENGTH_SHORT).show();
                        }
                        recreate();
                    }
                });

                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_successfully_joined_group),
                        Toast.LENGTH_SHORT).show();

                break;
            case R.id.leave_group_button:
                final User userToRemove = currentUser;
                AlertDialog.Builder leaveGroupBuilder = new AlertDialog.Builder(this);

                leaveGroupBuilder
                        .setMessage("Are you sure you want to leave the group?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ParseRelation<ParseObject> newRelation = group.getRelation("users");

                                newRelation.remove(userToRemove);
                                if (groupMembers.size() - 1 == 0) {
                                    group.setIsActive(false);
                                }
                                group.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(),
                                                    getResources().getString(R.string.toast_successfully_left_group),
                                                    Toast.LENGTH_SHORT).show();
                                            Intent goToHomepage = new Intent(ViewGroupActivity.this, HomepageActivity.class);
                                            startActivity(goToHomepage);
                                        } else {
                                            // Error.
                                            Toast.makeText(getApplicationContext(),
                                                    getResources().getString(R.string.toast_unsuccessfully_left_group),
                                                    Toast.LENGTH_SHORT).show();
                                            recreate();
                                        }


                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog leaveDialog = leaveGroupBuilder.create();
                leaveDialog.show();

                break;

            case R.id.edit_group_button:
                Intent clickEdit = new Intent(ViewGroupActivity.this, EditGroupActivity.class);
                clickEdit.putExtra("isGroupExisting", true);
                clickEdit.putExtra("groupObjectId", group.getObjectId());
                startActivity(clickEdit);
                break;
            case R.id.open_close_button:
                if (group.getIsGroupOpen()) {
                    // Close the group.
                    group.setIsGroupOpen(false);
                    group.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.toast_successfully_marked_group_closed),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.toast_unsuccessfully_marked_group_closed),
                                        Toast.LENGTH_SHORT).show();
                            }

                            recreate();
                        }
                    });
                } else {
                    // Open the group.
                    group.setIsGroupOpen(true);
                    group.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.toast_successfully_marked_group_open),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        getResources().getString(R.string.toast_unsuccessfully_marked_group_opened),
                                        Toast.LENGTH_SHORT).show();
                            }

                            recreate();
                        }
                    });
                }
                break;
        }
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
