package com.airportapp.test.Models;

import android.app.Activity;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import com.airportapp.R;
import com.airportapp.SearchResultsActivity;
import com.airportapp.SearchResultsListActivity;
import com.airportapp.test.MyRobolectricTestRunner;
import com.airportapp.SearchActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class SearchActivityTest {

    private final ActivityController<SearchActivity> controller = buildActivity(SearchActivity.class);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(SearchActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    // Checks that clicking the search button will return search results.
    @Test
    public void test_clickingSearchResultsList_shouldStartSearchResultsListActivity() {
        SearchActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.searchResultsList_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchResultsListActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}
