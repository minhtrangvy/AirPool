package com.airportapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

public class EditGroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(Group.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_group, menu);
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

        public boolean getToFrom() {
            return getBoolean("toFrom");
        }

        public void setToFrom(String toFrom) {
            put("toFrom", toFrom);
        }

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
