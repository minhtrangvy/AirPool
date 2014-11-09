package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;


public class ViewGroupActivity extends Activity implements View.OnClickListener {
    Group group = null;

    TextView airportText;
    TextView collegeText;
    TextView transportationPreferenceText;

    ListView groupMembersList;
    ArrayAdapter<User> groupMembersAdapter;
    ArrayList<User> groupMembers;

    Button wallButton, joinButton, editButton;
    boolean isUserMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        airportText = (TextView) findViewById(R.id.airport_text);
        collegeText = (TextView) findViewById(R.id.college_text);
        transportationPreferenceText = (TextView) findViewById(R.id.mode_of_transportation_text);

        wallButton = (Button) findViewById(R.id.groupWall_button);
        wallButton.setOnClickListener(this);

        joinButton = (Button) findViewById(R.id.joinGroup_button);
        joinButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.edit_group_button);
        editButton.setOnClickListener(this);

        groupMembersList = (ListView) findViewById(R.id.group_members_list);

        // Get the group object ID that was passed in, get the group object, and populate
        // tne necessary views.
        groupMembers = new ArrayList<User>();
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
            Group group = (Group) query.get(getIntent().getStringExtra("groupId"));

            String airportTextContents;
            Resources resources = getResources();

            if (group.getIsToAirport()) {
                airportTextContents = resources.getString(R.string.going_to_airport);
            } else {
                airportTextContents = resources.getString(R.string.leaving_from_airport);
            }

            airportText.setText(String.format(airportTextContents, group.getAirport().getAirportName()));
            collegeText.setText(group.getCollege().getFullName());
            transportationPreferenceText.setText(
                    String.format(resources.getString(R.string.mode_of_transportation),
                    group.getTransportationPreference().getPreferenceName()));

            // Populate the list of users.
            ParseRelation relation = group.getRelation("users");

            query = relation.getQuery();
            List<ParseObject> users = query.find();

            // Do not know of any other way to cast list of objects.
            for(ParseObject user : users) {
                groupMembers.add((User) user);
            }
        } catch (ParseException exception) {
            // Error.
        }

        if (!groupMembers.isEmpty()) {
            // Populate the list view.
            groupMembersAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1,
                    groupMembers);
            groupMembersList.setAdapter(groupMembersAdapter);
        } else {
            // Indicate to the user that there are no members of this group.
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_group, menu);
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
            case R.id.groupWall_button:
                Intent goToWall = new Intent(ViewGroupActivity.this, GroupWallActivity.class);
                startActivity(goToWall);
                break;
            case R.id.joinGroup_button:
                // TODO: Store that user has joined group
                if (true) {
                    editButton.setVisibility(View.VISIBLE);
                    joinButton.setText("Leave Group");

                    Toast.makeText(getApplicationContext(),
                            "Successfully Joined Group!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    editButton.setVisibility(View.GONE);
                    joinButton.setText("Join Group");

                    Toast.makeText(getApplicationContext(),
                            "Successfully Left Group!",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edit_group_button:
                Intent clickEdit = new Intent(ViewGroupActivity.this, EditGroupActivity.class);
                clickEdit.putExtra("isGroupExisting", true);
                clickEdit.putExtra("groupObjectId", group.getObjectId());
                startActivity(clickEdit);
                break;
        }
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
