package com.airpool;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.airpool.Model.User;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    public static final String PREFS_NAME = "Prefs";

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
                public void onCompleted(final GraphUser user, Response response) {
                    if (user != null) {
                        final String facebookId = user.getId();
                        Log.i(TAG, "Hello " + user.getName());
                        Log.i(TAG, "Unique ID " + facebookId);

                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
                        query.whereEqualTo("facebookID", facebookId);
                        query.getFirstInBackground( new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                // if the user does exist in our database, set their loggedIn to true
                                if (parseObject != null) {
                                    Log.i(TAG, "We found you! Parse Object ID" + parseObject.getObjectId().toString());

                                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editSettings = settings.edit();
                                    editSettings.putString("userObjectId", parseObject.getObjectId());
                                    editSettings.commit();

                                    ((GlobalUser) getApplicationContext()).setCurrentUser((User) parseObject);
                                // if the user does not exist in our database, create them
                                } else {
                                    Log.i(TAG, "We did not find you! And fb id is " + facebookId);
                                    User newUser = new User();
                                    newUser.put("facebookID", user.getId());
                                    newUser.saveInBackground();

                                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                    SharedPreferences.Editor editSettings = settings.edit();
                                    editSettings.putString("userObjectId", newUser.getObjectId());
                                    editSettings.commit();

                                    ((GlobalUser) getApplicationContext()).setCurrentUser((User) newUser);
                                }

//                                finish();
                                Intent sendToHomepage = new Intent(LoginActivity.this,HomepageActivity.class);
                                startActivity(sendToHomepage);
                            }
                        });
                    }
                }
            }).executeAsync();

//            this._userId = currentUser.getUserID();
//            Log.i(TAG, "User ID is " + this._userId);
//
//            setContentView(R.layout.activity_homepage);
//
//            searchButton = (Button) findViewById(R.id.search_button);
//            searchButton.setOnClickListener(this);
//
//            preferencesButton = (Button) findViewById(R.id.preferences_button);
//            preferencesButton.setOnClickListener(this);

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
//            case R.id.preferences_button:
//                Intent clickPreference = new Intent(LoginActivity.this, PreferencesActivity.class);
//                startActivity(clickPreference);
//                break;
        }
    }
}
