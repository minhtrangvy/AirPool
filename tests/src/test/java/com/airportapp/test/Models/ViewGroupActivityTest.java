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
import com.airportapp.EditGroupActivity;
import com.airportapp.test.MyRobolectricTestRunner;
import com.airportapp.ViewGroupActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class ViewGroupActivityTest {

    private final ActivityController<ViewGroupActivity> controller = buildActivity(ViewGroupActivity.class);


    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    // Tests that activity is created.
    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(ViewGroupActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    // Tests that the edit group button will send the user to Edit Group.
    @Test
    public void test_clickingEditGroup_shouldStartEditGroupActivity() {
        ViewGroupActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.editGroup_button).performClick();

        Intent expectedIntent = new Intent(activity, EditGroupActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

    // Tests that the back button will send the user to the search results (list).
    @Test
    public void test_clickingSearchResultsList_shouldStartSearchResultsListActivity() {
        ViewGroupActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.searchResultsList_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchResultsListActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}
