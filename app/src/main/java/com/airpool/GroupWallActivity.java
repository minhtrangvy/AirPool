package com.airpool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.airpool.Adapter.GroupWallPostsAdapter;
import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.airpool.Model.WallPost;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertNotNull;


public class GroupWallActivity extends Activity {
    private static final String TAG = "GroupWallActivity";

    private EditText newMessage;
    private Group associatedGroup;
    private GroupWallPostsAdapter wallPostsAdapter;
    private ListView wallPostsListView;
    private ArrayList<WallPost> wallPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_wall);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        wallPosts = new ArrayList<WallPost>();

        wallPostsListView = (ListView) findViewById(R.id.group_wall_posts);
        newMessage = (EditText) findViewById(R.id.message_body_field);

        GlobalUser context = (GlobalUser) getApplicationContext();
        ParseObject user = context.getCurrentUser();
        assertNotNull(user);

        wallPostsAdapter = new GroupWallPostsAdapter(this, R.layout.item_wall_post,
                wallPosts, user.getObjectId());
        wallPostsListView.setAdapter(wallPostsAdapter);

        final ParseQuery<ParseObject> wallPostQuery = ParseQuery.getQuery("WallPost");
        ParseQuery<ParseObject> groupQuery = ParseQuery.getQuery("Group");

        groupQuery.getInBackground(getIntent().getStringExtra("groupId"), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    associatedGroup = (Group) parseObject;
                    wallPostQuery.whereEqualTo("group", associatedGroup);
                    wallPostQuery.include("sender");
                    wallPostQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            if (e == null) {
                                for (ParseObject object : parseObjects) {
                                    wallPosts.add((WallPost) object);
                                }

                                wallPostsAdapter.notifyDataSetChanged();

                            } else {
                                Log.e(TAG, "Error fetching group messages: " + e.toString());
                            }

                        }
                    });

                } else {
                    Log.e(TAG, "Error fetching group: " + e.toString());
                }
            }
        });
    }

    public void refreshWall() {

    }

    public void onSendMessage(View view) {
        View currentFocus = this.getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        WallPost newPost = new WallPost();

        GlobalUser context = (GlobalUser) getApplicationContext();
        ParseObject user = context.getCurrentUser();
        assertNotNull(user);
        newPost.setSender((User) user);

        assertNotNull(associatedGroup);
        newPost.setGroup(associatedGroup);
        newPost.setMessage(newMessage.getText().toString());
        newPost.setTimeSent(Calendar.getInstance());

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.toast_successfully_posted_wallpost),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error creating message: " + e.toString());
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.toast_unsuccessfully_posted_wallpost),
                            Toast.LENGTH_SHORT).show();
                }
                recreate();
            }
        });
        
        newMessage.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
