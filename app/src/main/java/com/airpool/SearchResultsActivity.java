package com.airpool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airpool.Adapter.SearchResultGroupAdapter;
import com.airpool.Model.College;
import com.airpool.Model.Group;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsActivity extends Activity implements View.OnClickListener {
    ProgressBar progressBar;
    TextView searchResultHeading;
    Button createGroup;
    ListView searchResultList;
    ArrayAdapter<Group> searchResultListAdapter;
    ArrayList<Group> searchResultGroups;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResultHeading = (TextView) findViewById(R.id.search_result_heading);

        String searchResultHeader;
        Intent intent = getIntent();

        // Get the college and display in the header.
        College college = Enum.valueOf(College.class, intent.getStringExtra("college"));
        if (intent.getBooleanExtra("isToAirport", true)) {
            searchResultHeader = String.format(getResources().getString(R.string.rides_from),
                    college.getFullName());
        } else {
            searchResultHeader = String.format(getResources().getString(R.string.rides_to),
                    college.getFullName());
        }
        searchResultHeading.setText(searchResultHeader);

        searchResultList = (ListView) findViewById(R.id.search_result_list);

        createGroup = (Button) findViewById(R.id.create_group_button);
        createGroup.setOnClickListener(this);

        // Setup the activity circle.
        progressBar = (ProgressBar) findViewById(R.id.search_result_progress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        // Perform the query on a background_me_wallpost thread.
        FetchSearchResultsTask task = new FetchSearchResultsTask(this);
        task.execute();

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_group_button:
                Intent clickCreate = new Intent(SearchResultsActivity.this, EditGroupActivity.class);
                clickCreate.putExtra("isGroupExisting", false);
                startActivity(clickCreate);
                break;
        }
    }

    private class FetchSearchResultsTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Group>> {
        private Context context;

        public FetchSearchResultsTask(Context context) {
            this.context = context;
        }

        protected ArrayList<Group> doInBackground(ArrayList<String>... requestedGroupIds) {
            ArrayList<Group> groups = new ArrayList<Group>();

            List<ParseObject> searchResultGroups = new ArrayList<ParseObject>();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");

            try {
                Intent passedInIntent = getIntent();

                query.whereEqualTo("airport", passedInIntent.getStringExtra("airport"));
                query.whereEqualTo("isToAirport", passedInIntent.getBooleanExtra("isToAirport", false));
                query.whereEqualTo("isActive", true);
                query.whereEqualTo("isGroupOpen", true);
                query.whereEqualTo("college", passedInIntent.getStringExtra("college"));

                ArrayList<String> preferences = passedInIntent.getStringArrayListExtra("preferences");
                if (!preferences.isEmpty()) {
                    query.whereContainedIn("transportationPreference", preferences);
                }

                // Do some math to return dates within a 12-hour window.
                Long twelveHoursBefore = passedInIntent.getLongExtra("timeOfDeparture", 0) - (43200 * 1000);
                Long twelveHoursAfter = passedInIntent.getLongExtra("timeOfDeparture", 0) + (43200 * 1000);

                query.whereLessThan("timeOfDeparture", twelveHoursAfter);
                query.whereGreaterThan("timeOfDeparture", twelveHoursBefore);

                searchResultGroups = query.find();

                // Do not know of any other way to cast list of objects.
                ParseQuery countQuery;
                for(ParseObject group : searchResultGroups) {
                    Group groupToAdd = (Group) group;
                    ParseRelation relation = group.getRelation("users");
                    countQuery = relation.getQuery();
                    ((Group) group).setNumberOfUsers(countQuery.count());
                    groups.add((Group) group);
                }
            } catch (ParseException exception) {
                // Error.
            }

            return groups;
        }

        protected void onProgressUpdate() {
            // Do nothing.
        }

        protected void onPostExecute(ArrayList<Group> result) {
            searchResultGroups = result;
            progressBar.setVisibility(View.INVISIBLE);
            createGroup.setVisibility(View.VISIBLE);

            if (!searchResultGroups.isEmpty()) {
                // Populate the list view.
                searchResultListAdapter = new SearchResultGroupAdapter(this.context,
                        R.layout.item_search_result, searchResultGroups);
                searchResultList.setAdapter(searchResultListAdapter);

                // View a group when you click on a list item.
                searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent viewGroup = new Intent(SearchResultsActivity.this, ViewGroupActivity.class);
                        viewGroup.putExtra("groupId", searchResultGroups.get(position).getObjectId());
                        startActivity(viewGroup);
                    }
                });
            } else {
                // Indicate to the user that there were no search results.
                searchResultList.setEmptyView((TextView) findViewById(R.id.no_search_results));
            }
        }
    }
}
