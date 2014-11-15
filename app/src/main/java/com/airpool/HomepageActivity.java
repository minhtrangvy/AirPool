package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.airpool.Adapter.UserGroupsAdapter;
import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class HomepageActivity extends Activity implements View.OnClickListener {
    public static final String PREFS_NAME = "Prefs";
    Button searchButton, preferencesButton, logoutButton;
    boolean isLoggedIn = true;
    User _thisUser;

    ListView userGroupList;
    ArrayAdapter<Group> userGroupListAdapter;
    ArrayList<Group> userGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String objectId = settings.getString("userObjectId", null);

        if(objectId == null) {
            Intent mustLogIn = new Intent(HomepageActivity.this, LoginActivity.class);
            startActivity(mustLogIn);
        } else {
            setContentView(R.layout.activity_homepage);

            userGroupList = (ListView) findViewById(R.id.user_groups);

            searchButton = (Button) findViewById(R.id.search_button);
            searchButton.setOnClickListener(this);

            // Get the groups associated with this user.
            userGroups = new ArrayList<Group>();
            try {
                // First, get the user. This is a hard-coded call right now, but replace this with
                // an actual
                ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("User");
                ParseObject user = userQuery.get(objectId);
                GlobalUser context = (GlobalUser) getApplicationContext();
                context.setCurrentUser((User) user);

                // Then, get the associated groups.
                ParseQuery<ParseObject> groupQuery = ParseQuery.getQuery("Group");
                groupQuery.whereEqualTo("users", user);
                groupQuery.whereEqualTo("isActive", true);

                List<ParseObject> groups = groupQuery.find();

                // Do not know of any other way to cast list of objects.
                for(ParseObject group : groups) {
                    userGroups.add((Group) group);
                }
            } catch (ParseException exception) {
                // Error.
            }

            if (!userGroups.isEmpty()) {
                // Populate the list view.
                userGroupListAdapter = new UserGroupsAdapter(this, R.layout.item_user_groups, userGroups);
                userGroupList.setAdapter(userGroupListAdapter);

                // View a group when you click on a list item.
                userGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent viewGroup = new Intent(HomepageActivity.this, ViewGroupActivity.class);
                        viewGroup.putExtra("groupId", userGroups.get(position).getObjectId());
                        startActivity(viewGroup);
                    }
                });
            } else {
                // Indicate to the user that there are no groups that they've joined.
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
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
    public void onClick(View v) {
        Intent clickSearch = new Intent(HomepageActivity.this, SearchActivity.class);
        startActivity(clickSearch);
    }
}
