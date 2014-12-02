package com.airpool.Activities;

import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import com.airpool.R;
import com.airpool.ViewGroupActivity;
import com.robotium.solo.Solo;

/**
 * Created by minhtrangvy on 12/2/14.
 */
public class ViewGroupActivityRobotiumTest extends
        ActivityInstrumentationTestCase2<ViewGroupActivity> {

    private Solo solo;

    public ViewGroupActivityRobotiumTest() {
        super(ViewGroupActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    // CANNOT TEST VIEW GROUP? because if we just open view group, it doesn't know which group to pick
    // so we must test from homepageactivity?
//    public void testLeaveGroup() throws Exception {
//        solo.clickOnText("Leave");
//        assertTrue(solo.waitForText("Cancel"));
//        solo.clickOnText("Cancel");
//    }
}
