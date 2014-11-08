package com.airpool.Activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

import com.airpool.R;
import com.airpool.SearchActivity;
import com.airpool.SearchResultsActivity;

/**
 * Created by Maury on 11/7/14.
 */
public class SearchActivityFunctionalTest extends ActivityInstrumentationTestCase2<SearchActivity> {
    public SearchActivityFunctionalTest() {
        super(SearchActivity.class);
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

    public void testSearchResultsButtonClick() {
        // Register the activity that the button click will open.
        Instrumentation.ActivityMonitor activityMonitor =
                getInstrumentation().addMonitor(SearchResultsActivity.class.getName(), null, false);

        final Button searchResultsButton = (Button) getActivity().findViewById(R.id.search_results_button);
        assertNotNull(searchResultsButton);
        TouchUtils.clickView(this, searchResultsButton);

        // Ensure that the new activity finishes loading.
        SearchResultsActivity nextActivity = (SearchResultsActivity)
                activityMonitor.waitForActivityWithTimeout(500);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
