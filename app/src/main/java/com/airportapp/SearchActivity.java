package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SearchActivity extends Activity implements View.OnClickListener {

    EditText toEdit, fromEdit;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Access the Button defined in login XML
        // and listen for it here
        searchButton = (Button) findViewById(R.id.searchResultsList_button);
        searchButton.setOnClickListener(this);

        toEdit = (EditText) findViewById(R.id.location_editTo);
        fromEdit = (EditText) findViewById(R.id.location_editFrom);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
        Intent clickLogin = new Intent(SearchActivity.this, SearchResultsListActivity.class);
        startActivity(clickLogin);
    }
}
