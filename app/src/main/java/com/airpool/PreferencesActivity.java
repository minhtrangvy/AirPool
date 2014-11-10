package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.airpool.Model.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class PreferencesActivity extends Activity implements View.OnClickListener {

    Button homepageButton;
    boolean taxi, superShuttle, publicTransit, drive, noPref;
    private User user;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        taxi = false;
        superShuttle = false;
        publicTransit = false;
        drive = false;
        noPref = false;

        GlobalUser currentUser = ((GlobalUser)getApplicationContext());
        userId = currentUser.getUserID();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
        query.whereEqualTo("userId", userId);
        query.getFirstInBackground( new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                // if the user does exist in our database
                if (parseObject != null) {
                    taxi = false;
                    superShuttle = false;
                    publicTransit = false;
                    drive = false;
                    noPref = false;

                    boolean[] preferences = {taxi, superShuttle, publicTransit, drive, noPref};
                    parseObject.put("loggedIn", true);
                    parseObject.put("facebookId", userId);
                    parseObject.put("transportationPreference", preferences);
                    user.setTransPref(preferences);
                }
            }
        });

        homepageButton = (Button) findViewById(R.id.homepage_button);
        homepageButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferences, menu);
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

    // Checks the state of a checkbox whenever checked. TODO: Update after Parse is implemented
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_taxi:
                if (checked)
                    //TODO need to update to Parse for the user
                    taxi = true;
                else
                    taxi = false;
                break;
            case R.id.checkbox_superShuttle:
                if (checked)
                    superShuttle = true;
                else
                    superShuttle = false;
                break;
            case R.id.checkbox_publicTransit:
                if (checked)
                    publicTransit = true;
                else
                    publicTransit = false;
                break;
            case R.id.checkbox_drive:
                if (checked)
                    drive = true;
                else
                    drive = false;
                break;
            case R.id.checkbox_noPref:
                if (checked)
                    noPref = true;
                else
                    noPref = false;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent clickSavePref = new Intent(PreferencesActivity.this, HomepageActivity.class);
        startActivity(clickSavePref);
    }

}
