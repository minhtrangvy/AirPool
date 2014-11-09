package com.airpool;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private User _thisUser;
    private String _userId;

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
<<<<<<< HEAD

        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

//        GlobalUser globalUser = ( (GlobalUser) getApplicationContext() );
//        String _userId = globalUser.getUserID();
=======
>>>>>>> a141913d58551221f38708b436669a023f437206
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
//                        _userId = user.getId();
                        Log.i(TAG, "Hello " + user.getName());
                        Log.i(TAG, "Unique ID " + user.getId());



//                        _thisUser = new User(_userId);
//                        _thisUser.setUserID(_userId);
//                        _thisUser.setLoggedIn(_userId, true);
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
            _thisUser.setLoggedIn(_userId, false);
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
}
