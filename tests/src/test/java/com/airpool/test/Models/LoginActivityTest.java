package com.airpool.test.Models;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import com.airpool.test.MyRobolectricTestRunner;
import com.airpool.LoginActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class LoginActivityTest {

    private final ActivityController<LoginActivity> controller = buildActivity(LoginActivity.class);


    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

   // @Test
    public void testActivityFound() {
//        Activity activity = buildActivity(LoginActivity.class).create().get();
//        Assert.assertNotNull(activity);
    }

    // Tests that logging in sends the user to preferences.
//    @Test
//    public void test_clickingLogin_shouldStartPreferencesActivity() {
//        LoginActivity activity = controller.create().start().resume().get();
//        activity.findViewById(R.id.login_button).performClick();
//
//        Intent expectedIntent = new Intent(activity, PreferencesActivity.class);
//        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
//    }

}
