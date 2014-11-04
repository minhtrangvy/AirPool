package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD:app/src/main/java/com/airportapp/LoginActivity.java
import android.widget.EditText;

import android.widget.TextView;
=======
>>>>>>> 560f2573950c8829cafd0bcc9e4514d684fa908b:app/src/main/java/com/airpool/LoginActivity.java

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
<<<<<<< HEAD:app/src/main/java/com/airportapp/LoginActivity.java

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.ParseObject;

import org.json.JSONArray;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    Button searchButton, preferencesButton;
//    EditText editUsername, editPassword;

    private Session.StatusCallback loginCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiHelper = new UiLifecycleHelper(this, loginCallback);
        uiHelper.onCreate(savedInstanceState);

        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (session.isOpened()) {
            Log.i(TAG, "User has logged in...");
            // make request to the /me API
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        Log.i(TAG, "Hello " + user.getName());
                        Log.i(TAG, "Unique ID " + user.getId());
                    }
                }
            }).executeAsync();

            setContentView(R.layout.activity_homepage);


            searchButton = (Button) findViewById(R.id.search_button);
            searchButton.setOnClickListener(this);

            preferencesButton = (Button) findViewById(R.id.preferences_button);
            preferencesButton.setOnClickListener(this);

        } else if (session.isClosed()) {
            Log.i(TAG, "User has logged out...");

            setContentView(R.layout.activity_login);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                Intent clickSearch = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(clickSearch);
                break;
            case R.id.preferences_button:
                Intent clickPreference = new Intent(LoginActivity.this, PreferencesActivity.class);
                startActivity(clickPreference);
                break;
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
<<<<<<< HEAD
=======

        public String getFirstName() { return getString("firstName"); }
        public void setFirstName(String firstName) { put("firstName", firstName); }

        public String getLastName() { return getString("LastName"); }
        public void setLastName(String lastName) { put("lastName", lastName); }

        public String getPicUrl() { return getString("picUrl"); }
        public void setPicUrl(String picUrl) { put("picUrl", picUrl); }
>>>>>>> bc586e0a31ea8446e37a52c985addf695b71d799
    }
}
