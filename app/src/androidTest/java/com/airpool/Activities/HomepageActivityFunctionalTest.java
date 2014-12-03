package com.airpool.Activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

import com.airpool.HomepageActivity;
<<<<<<< HEAD
=======
//import com.airpool.PreferencesActivity;
>>>>>>> 3b9817a67aae2439313a8e802a2d99935e649086
import com.airpool.R;
import com.airpool.SearchActivity;

/**
 * Created by Maury on 11/7/14.
 */
public class HomepageActivityFunctionalTest extends ActivityInstrumentationTestCase2<HomepageActivity> {
    public HomepageActivityFunctionalTest() {
        super(HomepageActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Does nothing.
    }


    public void testSearchButtonClick() {
/*
        getActivity().setIsLoggedIn(true);
*/

        // Register the activity that the button click will open.
        Instrumentation.ActivityMonitor activityMonitor =
                getInstrumentation().addMonitor(SearchActivity.class.getName(), null, false);

        final Button searchButton = (Button) getActivity().findViewById(R.id.search_button);
        assertNotNull(searchButton);
        TouchUtils.clickView(this, searchButton);

        // Ensure that the new activity finishes loading.
        SearchActivity nextActivity = (SearchActivity)
                activityMonitor.waitForActivityWithTimeout(500);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    public void testLogoutButtonClick() {
        // Should go to LoginActivity.
    }

}
