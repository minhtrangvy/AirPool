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

import com.airportapp.HomepageActivity;
import com.airportapp.PreferencesActivity;
import com.airportapp.R;
import com.airportapp.test.MyRobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class PreferencesActivityTest {

    private final ActivityController<PreferencesActivity> controller = buildActivity(PreferencesActivity.class);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(PreferencesActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    // Tests that saving preferences will return the user to the homepage.
    @Test
    public void test_clickingPreferences_shouldStartHomepageActivity() {
        PreferencesActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.homepage_button).performClick();

        Intent expectedIntent = new Intent(activity, HomepageActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

}
