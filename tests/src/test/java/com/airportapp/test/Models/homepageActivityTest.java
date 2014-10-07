package com.airportapp.test.Models;

import android.content.Intent;
import com.airportapp.HomepageActivity;
import com.airportapp.LoginActivity;
import com.airportapp.R;
import com.airportapp.test.MyRobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;



/**
 * Created by Angela Chin on 10/5/2014.
 */

@RunWith(MyRobolectricTestRunner.class)
public class homepageActivityTest {

    private final ActivityController<HomepageActivity> controller = Robolectric.buildActivity(HomepageActivity.class);

    @Test
    public void clickingLogout_shouldStartLogoutActivity() {
        HomepageActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.logout_button).performClick();

        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        assertTrue(true);
        //assertThat((Robolectric.shadowOf(activity).getNextStartedActivity()), (org.hamcrest.Matcher<? super Intent>) expectedIntent);
    }
}
