package com.airpool.Activities;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.airpool.HomepageActivity;
import com.airpool.R;

/**
 * Created by Maury on 11/7/14.
 */
public class HomepageActivityUnitTest extends ActivityUnitTestCase<HomepageActivity> {
    public HomepageActivityUnitTest() {
        super(HomepageActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                HomepageActivity.class);
        startActivity(intent, null, null);
    }

    @Override
    protected void tearDown() throws Exception {
        // Does nothing.
    }

/*    public void testIsLoggedIn() {
        getActivity().setIsLoggedIn(true);
        assertTrue(getActivity().getIsLoggedIn());
    }

    public void testIsNotLoggedIn() {
        getActivity().setIsLoggedIn(false);
        assertFalse(getActivity().getIsLoggedIn());
    }

    public void testSearchButton() {
        assertNotNull(getActivity().findViewById(R.id.search_button));
    }

    public void testPreferencesButton() {
        assertNotNull(getActivity().findViewById(R.id.preferences_button));
    }*/

    public void testFacebookLogoutButton() {
        assertNotNull(getActivity().findViewById(R.id.facebook_login_button));
    }
}
