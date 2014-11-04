package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;


public class SearchResultsListActivity extends Activity implements View.OnClickListener {

    Button mapButton, backButton, groupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(Group.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_list);

        // Access the Button defined in login XML
        // and listen for it here
        mapButton = (Button) findViewById(R.id.searchResultsMap_button);
        mapButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.search_button);
        backButton.setOnClickListener(this);

        groupButton = (Button) findViewById(R.id.createGroup_button);
        groupButton.setOnClickListener(this);
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
            case R.id.searchResultsMap_button:
                Intent clickMap = new Intent(SearchResultsListActivity.this, SearchResultsMapActivity.class);
                startActivity(clickMap);
                break;
            case R.id.search_button:
                Intent clickBack = new Intent(SearchResultsListActivity.this, SearchActivity.class);
                startActivity(clickBack);
                break;
            case R.id.createGroup_button:
                Intent clickCreate = new Intent(SearchResultsListActivity.this, CreateGroupActivity.class);
                startActivity(clickCreate);
                break;
        }
    }
    
    @ParseClassName("Group")
    public class Group extends ParseObject {

        public Group() {
            // A default constructor is required.
        }

        public String getGroupID() {
            return getString("groupID");
        }

        public void setGroupID(String groupID) {
            put("groupID", groupID);
        }

        public String getDate() {
            return getString("date");
        }

        public void setDate(String date) {
            put("date", date);
        }

        public String getTime() {
            return getString("time");
        }

        public void setTime(String time) {
            put("time", time);
        }

        public String getTransPref() {
            return getString("transPref");
        }

        public void setTransPref(String transPref) {
            put("transPref", transPref);
        }

        public String getAirport() {
            return getString("airport");
        }

        public void setAirport(String airport) {
            put("airport", airport);
        }

        public String getCollege() {
            return getString("college");
        }

        public void setCollege(String college) {
            put("college", college);
        }

        public boolean getToAirport() { return getBoolean("toAirport"); }

        public void setToAirport(Boolean toAirport) { put("toAirport", toAirport); }

        public JSONArray getMembers() {
            return getJSONArray("members");
        }

        public void setMembers(JSONArray members) {
            put("members", members);
        }

        public boolean getGroupOpen() {
            return getBoolean("groupOpen");
        }

        public void setGroupOpen(String groupOpen) {
            put("groupOpen", groupOpen);
        }
    }
}
