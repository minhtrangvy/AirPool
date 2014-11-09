package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.airpool.Model.Group;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class SearchResultsActivity extends Activity implements View.OnClickListener {

    Button createGroup;
    ListView searchResultList;
    ArrayAdapter<Group> searchResultListAdapter;
    ArrayList<Group> searchResultGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchResultList = (ListView) findViewById(R.id.search_result_list);

        createGroup = (Button) findViewById(R.id.create_group_button);
        createGroup.setOnClickListener(this);

        // Perform the query.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");

        searchResultGroups = new ArrayList<Group>();
        try {
            List<ParseObject> groups = query.find();

            // Do not know of any other way to cast list of objects.
            for(ParseObject group : groups) {
                searchResultGroups.add((Group) group);
            }
        } catch (ParseException exception) {
            // Error.
        }

        // Populate the list view.
        searchResultListAdapter = new ArrayAdapter<Group>(this, android.R.layout.simple_list_item_1, searchResultGroups);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results_list, menu);
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
            case R.id.create_group_button:
                Intent clickCreate = new Intent(SearchResultsActivity.this, EditGroupActivity.class);
                clickCreate.putExtra("isGroupExisting", false);
                startActivity(clickCreate);
                break;
        }
    }
}
