package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airpool.Model.Group;
import com.parse.Parse;
import com.parse.ParseObject;


public class ViewGroupActivity extends Activity implements View.OnClickListener {

    // TODO: pass around the ParseObject user and ParseObject group.
    //User currentUser = new User();
    Group currentGroup = new Group();
    String groupId = currentGroup.getGroupID();

    Button backButton, wallButton, joinButton, editButton;
    boolean userNotMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(Group.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        userNotMember = true;

//        // Get user ID and then get Group's user array. Check if user in the array-- if not, set userNotMember to true.
//        // Finds the user parse object to create relation with
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//        query.whereEqualTo("groupID", groupId);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> groupUsers, ParseException e) {
//                if (e == null) {
//                    for(ParseObject user : groupUsers) {
//                        if(user == currentUser) {
//                            userNotMember = false;
//                        }
//                    }
//                } else {
//                    // Error
//                }
//            }
//        });
//
//
//        userNotMember = true;
//
//        // TODO: Update once Parse is working
//        if (userNotMember) {
//            View editButton = findViewById(R.id.editGroup_button);
//            editButton.setVisibility(View.GONE);
//        }

        wallButton = (Button) findViewById(R.id.groupWall_button);
        wallButton.setOnClickListener(this);

        joinButton = (Button) findViewById(R.id.joinGroup_button);
        joinButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.edit_group_button);
        editButton.setOnClickListener(this);
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
                if (userNotMember) {
                    editButton.setVisibility(View.VISIBLE);
                    joinButton.setText("Leave Group");

                    Toast.makeText(getApplicationContext(),
                            "Successfully Joined Group!",
                            Toast.LENGTH_SHORT).show();
                    userNotMember = false;
                } else {
                    editButton.setVisibility(View.GONE);
                    joinButton.setText("Join Group");

                    Toast.makeText(getApplicationContext(),
                            "Successfully Left Group!",
                            Toast.LENGTH_SHORT).show();
                    userNotMember = true;
                }
                break;
            case R.id.edit_group_button:
                Intent clickEdit = new Intent(ViewGroupActivity.this, EditGroupActivity.class);
                startActivity(clickEdit);
                break;
        }
    }
}
