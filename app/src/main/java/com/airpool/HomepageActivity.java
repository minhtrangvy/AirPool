package com.airpool;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.airpool.Fragment.HomepageFragment;
import com.airpool.Model.User;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class HomepageActivity extends FragmentActivity {
    private static final String TAG = "HomepageActivity";
    private static final int LOGIN = 0;
    private static final int HOMEPAGE = 1;
    private static final int FRAGMENT_COUNT = HOMEPAGE + 1;

    private boolean isResumed = false;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

    private UiLifecycleHelper uiHelper;
    public Session.StatusCallback callback =
            new Session.StatusCallback() {
                @Override
                public void call(Session session,
                                 SessionState state, Exception exception) {
                    onSessionStateChange(session, state, exception);
                }
            };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

            FragmentManager fm = getSupportFragmentManager();
            fragments[LOGIN] = fm.findFragmentById(R.id.login_fragment);
            fragments[HOMEPAGE] = fm.findFragmentById(R.id.homepage_fragment);

            FragmentTransaction transaction = fm.beginTransaction();
            for(int i = 0; i < fragments.length; i++) {
                transaction.hide(fragments[i]);
            }
            transaction.commit();

            uiHelper = new UiLifecycleHelper(this, callback);
            uiHelper.onCreate(savedInstanceState);

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            //showFragment(HOMEPAGE, false);
        } else {
            showFragment(LOGIN, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isResumed = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();

            int backStackSize = manager.getBackStackEntryCount();

            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            if (state.isOpened()) {
                final Bundle extras = getIntent().getExtras();

                // Get the Facebook User ID.
                Request.newMeRequest(session, new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(final GraphUser user, Response response) {
                        if (user != null && extras == null) {
                            final String facebookId = user.getId();

                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
                            query.whereEqualTo("facebookID", facebookId);
                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parseObject, ParseException e) {
                                    // Create the user if they don't exist.
                                    if (parseObject == null) {
                                        final User newUser = new User();
                                        newUser.put("facebookID", user.getId());
                                        newUser.put("firstName", user.getFirstName());
                                        newUser.put("lastName", user.getLastName());
                                        newUser.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    Log.e(TAG, "Error saving new user 1: " + e.toString());
                                                }
                                                ((GlobalUser) getApplicationContext())
                                                        .setCurrentUser((User) newUser);
                                                showFragment(HOMEPAGE, false);
                                            }
                                        });

                                    }
                                }
                            });
                        } else if (user != null && extras!=null) {
                            final String facebookId = extras.getString("fbID");

                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
                            query.whereEqualTo("facebookID", facebookId);
                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parseObject, ParseException e) {
                                    // Create the user if they don't exist.
                                    if (parseObject == null) {
                                        final User newUser = new User();
                                        newUser.put("facebookID", user.getId());
                                        newUser.put("firstName", user.getFirstName());
                                        newUser.put("lastName", user.getLastName());
                                        newUser.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e != null) {
                                                    Log.e(TAG, "Error saving new user 2.");
                                                }
                                                ((GlobalUser) getApplicationContext())
                                                        .setCurrentUser((User) newUser);
                                                showFragment(HOMEPAGE, false);
                                            }
                                        });

                                    }
                                }
                            });
                        } else {
                            showFragment(HOMEPAGE, false);
                        }
                    }
                }).executeAsync();
            } else if (state.isClosed()) {
                ((GlobalUser) getApplicationContext()).setCurrentUser(null);
                showFragment(LOGIN, false);
            }
        }
    }

    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
                if (i == HOMEPAGE && isNetworkConnected()) {
                    HomepageFragment fragment = (HomepageFragment) fragments[i];
                    fragment.populateGroups();
                } else if (i == HOMEPAGE && !isNetworkConnected()){
                    HomepageFragment fragment = (HomepageFragment) fragments[i];
                    fragment.noNetwork();
                }
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
       transaction.commit();
    }

}
