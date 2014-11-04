package com.airpool.test.Models;

import android.app.Activity;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import com.airpool.R;
import com.airpool.SearchResultsMapActivity;
import com.airpool.test.MyRobolectricTestRunner;
import com.airpool.SearchResultsListActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class SearchResultsListActivityTest {

    private final ActivityController<SearchResultsListActivity> controller = buildActivity(SearchResultsListActivity.class);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(SearchResultsListActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    // Tests that the map button will send the user to a map view of search results.
    @Test
    public void test_clickingSearchResultsMap_shouldStartSearchResultsMapActivity() {
        SearchResultsListActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.searchResultsMap_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchResultsMapActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

}
