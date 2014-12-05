package com.airpool.Activities;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.airpool.R;
import com.airpool.GroupWallActivity;
import com.robotium.solo.Solo;

/**
 * Created by angelachin on 12/4/14.
 */
public class GroupWallActivityRobotiumTest extends
        ActivityInstrumentationTestCase2<GroupWallActivity> {

    private Solo solo;

    public GroupWallActivityRobotiumTest() {
        super(GroupWallActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testCreatingMessage() {
        EditText finalText;
        boolean messageAppeared;

        solo.assertCurrentActivity("wrong activity", GroupWallActivity.class);

        solo.clickOnEditText(1);
        solo.enterText(1, "Hello");

        // Click a button which will start a new Activity
        // Here we use the ID of the string to find the right button
        solo.clickOnButton("Send");
        // Validate that the Activity is the correct one
        solo.assertCurrentActivity("wrong activity", GroupWallActivity.class);

        finalText = solo.getEditText(1);
        solo.clearEditText(1);
        assertEquals(finalText, solo.getEditText(1));

        messageAppeared = solo.searchText("Hello");
        assertEquals(true, messageAppeared);
    }
}
