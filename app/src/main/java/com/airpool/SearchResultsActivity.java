package com.airpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.airpool.Model.Group;
import com.parse.Parse;
import com.parse.ParseObject;


public class SearchResultsActivity extends Activity implements View.OnClickListener {

    Button mapButton, backButton, groupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ", "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_list);

        groupButton = (Button) findViewById(R.id.create_group_button);
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
            case R.id.create_group_button:
                Intent clickCreate = new Intent(SearchResultsActivity.this, EditGroupActivity.class);
                startActivity(clickCreate);
                break;
        }
    }
}
