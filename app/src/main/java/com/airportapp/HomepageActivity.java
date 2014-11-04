package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;


public class HomepageActivity extends Activity implements View.OnClickListener {


    Button searchButton, preferencesButton, logoutButton;
    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isLoggedIn != true) {
            Intent mustLogIn = new Intent(HomepageActivity.this, LoginActivity.class);
            startActivity(mustLogIn);
        }
        else{
            setContentView(R.layout.activity_homepage);


            searchButton = (Button) findViewById(R.id.search_button);
            searchButton.setOnClickListener(this);

            preferencesButton = (Button) findViewById(R.id.preferences_button);
            preferencesButton.setOnClickListener(this);

//            logoutButton = (Button) findViewById(R.id.logout_button);
//            logoutButton.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.search_button:
                Intent clickSearch = new Intent(HomepageActivity.this, SearchActivity.class);
                startActivity(clickSearch);
                break;
            case R.id.preferences_button:
                Intent clickPreference = new Intent(HomepageActivity.this, PreferencesActivity.class);
                startActivity(clickPreference);
                break;
//            case R.id.logout_button:
//                Intent clickLogout = new Intent(HomepageActivity.this, LoginActivity.class);
//                startActivity(clickLogout);
//                break;
        }

    }

    @ParseClassName("User")
    public class User extends ParseObject {

        public User() {
            // A default constructor is required
        }

        public String getUserId() {
            return getString("userID");
        }

        public void setUserID(String userID) {
            put("userID", userID);
        }

        public String getTransPref() {
            return getString("transPref");
        }

        public void setTransPref(String transPref) {
            put("transPref", transPref);
        }

        public JSONArray getGroups() {
            return getJSONArray("groups");
        }

        public void setGroups(JSONArray groups) {
            put("groups", groups);
        }

        public String getFirstName() { return getString("firstName"); }
        public void setFirstName(String firstName) { put("firstName", firstName); }
        public String getLastName() { return getString("LastName"); }
        public void setLastName(String lastName) { put("lastName", lastName); }

    }
}
