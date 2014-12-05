package com.airpool.Activities;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.airpool.GlobalUser;
import com.airpool.HomepageActivity;
import com.airpool.Model.User;
import com.airpool.SearchActivity;
import com.airpool.ViewGroupActivity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.robotium.solo.Solo;

import java.util.List;

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

        // Choose first group
        solo.clickInList(1);
        solo.assertCurrentActivity("wrong activity", ViewGroupActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        solo.clickOnButton("Search for Group");
        solo.assertCurrentActivity("wrong activity", SearchActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);
    }

    public void testIsGroupMember() {
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        // Choose first group
        solo.clickInList(1);
        solo.assertCurrentActivity("wrong activity", ViewGroupActivity.class);

        // Grab name of user
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("facebookID", "10152890848460992");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (e == null) {
                    String firstName = user.getString("firstName");
                    String lastName = user.getString("lastName");
                    Log.i("testIsGroupMember", "testing name is " + firstName + " " + lastName);

                    //Check that my name appears
                    boolean isInGroup;
                    isInGroup = solo.searchText(firstName + " " + lastName);
                    assertEquals("User name does not show up in Group View", true, isInGroup);

                } else {
                    Log.i("testIsGroupMember", "failed to get name");
                    assertNull(e);
                }
            }
        });

        solo.goBack();
    }

    public void testLeaveGroupPossible() throws Exception {
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        // Choose first group
        solo.clickInList(1);
        solo.assertCurrentActivity("wrong activity", ViewGroupActivity.class);

        // Test leave option
        solo.clickOnText("Leave");
        assertTrue(solo.waitForText("Cancel"));
        solo.clickOnText("Cancel");
    }

    public void testCloseGroup() throws Exception {
        solo.assertCurrentActivity("wrong activity", HomepageActivity.class);

        // Choose first group
        solo.clickInList(1);
        solo.assertCurrentActivity("wrong activity", ViewGroupActivity.class);

//        // Test leave option
//        solo.clickOnText("Leave");
//        assertTrue(solo.waitForText("Cancel"));
//        solo.clickOnText("Cancel");
        String groupStatus;
        if (solo.searchText("Close")) {
            solo.clickOnText("Close Group");
            assertTrue(solo.waitForText("You've successfully closed the group to new member."));
            solo.clickOnText("Open Group");
        } else {
            solo.clickOnText("Open Group");
            assertTrue(solo.waitForText("You've successfully opened the group to new members."));
            solo.clickOnText("Close Group");
        }
    }


}
