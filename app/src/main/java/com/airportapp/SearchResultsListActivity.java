package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SearchResultsListActivity extends Activity implements View.OnClickListener {

    Button mapButton, backButton, groupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
