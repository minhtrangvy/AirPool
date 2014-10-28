package com.airportapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
public class EditGroupActivity extends Activity {
=======

public class EditGroupActivity extends Activity implements View.OnClickListener {

    Button saveEditButton;
>>>>>>> d26c9146fa42e0733ea59844be29ce552ea1ce70

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);


        // Access the Button defined in login XML
        // and listen for it here
        saveEditButton = (Button) findViewById(R.id.viewGroup_button);
        saveEditButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_group, menu);
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
        Intent clickSaveEdits = new Intent(EditGroupActivity.this, ViewGroupActivity.class);
        startActivity(clickSaveEdits);
    }
}
