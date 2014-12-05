package com.airpool.Activities;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.airpool.HomepageActivity;
import com.airpool.SearchActivity;
import com.airpool.ViewGroupActivity;
import com.robotium.solo.Solo;

/**
 * Created by angelachin on 12/5/14.
 */
public class HomepageActivityRobotiumTest extends ActivityInstrumentationTestCase2<HomepageActivity> {

    private Solo solo;

    public HomepageActivityRobotiumTest() {
            super(HomepageActivity.class);
    }

    public void setUp() throws Exception {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("fbID", "10152890848460992");
            setActivityIntent(i);
            solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
            solo.finishOpenedActivities();
    }

    public void testClickGroup() {
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        solo.clickInList(1);
        solo.assertCurrentActivity("wrong activity", ViewGroupActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        solo.clickOnButton("Search for Group");
        solo.assertCurrentActivity("wrong activity", SearchActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);
    }

}
