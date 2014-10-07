package com.airportapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;


public class PreferencesActivity extends Activity {

    // List of different preferences that will be assigned based on checkbox.
    boolean taxi;
    boolean superShuttle;
    boolean publicTransit;
    boolean drive;
    boolean noPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_taxi:
                if (checked)
                    taxi = true;
                else
                    taxi = false;
                //break;
            case R.id.checkbox_supershuttle:
                if (checked)
                    superShuttle = true;
                else
                    superShuttle = false;
                //break;
            case R.id.checkbox_publictransit:
                if (checked)
                    publicTransit = true;
                else
                    publicTransit = false;
                //break;
            case R.id.checkbox_drive:
                if (checked)
                    drive = true;
                else
                    drive = false;
                //break;
            case R.id.checkbox_nopref:
                if (checked)
                    noPref = true;
                else
                    noPref = false;
                //break;
        }
    }
}
