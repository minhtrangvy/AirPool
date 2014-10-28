package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ViewGroupActivity extends Activity implements View.OnClickListener {

    Button backButton, joinButton, editButton;
    boolean userNotMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        userNotMember = true;

        // TODO: Update once Parse is working
        if (userNotMember) {
            View editButton = findViewById(R.id.editGroup_button);
            editButton.setVisibility(View.GONE);
        }

        // Access the Button defined in login XML
        // and listen for it here
        backButton = (Button) findViewById(R.id.searchResultsList_button);
        backButton.setOnClickListener(this);

        joinButton = (Button) findViewById(R.id.joinGroup_button);
        joinButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.editGroup_button);
        editButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_group, menu);
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
            case R.id.searchResultsList_button:
                Intent clickLogin = new Intent(ViewGroupActivity.this, SearchResultsListActivity.class);
                startActivity(clickLogin);
                break;
            case R.id.joinGroup_button:
                // TODO: Store that user has joined group
                editButton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),
                        "Successfully Joined Group!",
                        Toast.LENGTH_SHORT).show();
                userNotMember = false;
                break;
            case R.id.editGroup_button:
                Intent clickEdit = new Intent(ViewGroupActivity.this, EditGroupActivity.class);
                startActivity(clickEdit);
                break;
        }
    }
}
