package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import org.json.JSONArray;

public class LoginActivity extends Activity implements View.OnClickListener {

    EditText editUsername;
    EditText editPassword;
    Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Access the Button defined in login XML
        // and listen for it here
        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);

        editUsername = (EditText) findViewById(R.id.username_edittext);
        editPassword = (EditText) findViewById(R.id.password_edittext);

        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
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

    @Override
    public void onClick(View view) {
        Intent clickLogin = new Intent(LoginActivity.this, PreferencesActivity.class);
        startActivity(clickLogin);
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
    }


}
