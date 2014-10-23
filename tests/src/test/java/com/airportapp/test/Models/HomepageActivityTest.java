package com.airportapp.test.Models;

import android.app.Activity;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import com.airportapp.LoginActivity;
import com.airportapp.PreferencesActivity;
import com.airportapp.R;
import com.airportapp.SearchActivity;
import com.airportapp.test.MyRobolectricTestRunner;
import com.airportapp.HomepageActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class HomepageActivityTest {

    private final ActivityController<HomepageActivity> controller = buildActivity(HomepageActivity.class);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(HomepageActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    @Test
    public void test_clickingLogin_shouldStartLoginActivity() {
        HomepageActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.logout_button).performClick();

        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

    @Test
    public void test_clickingPreferences_shouldStartPreferencesActivity() {
        HomepageActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.preferences_button).performClick();

        Intent expectedIntent = new Intent(activity, PreferencesActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

    @Test
    public void test_clickingSearch_shouldStartSearchActivity() {
        HomepageActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.search_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }

}
