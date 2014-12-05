package com.airpool.Activities;

import android.content.Intent;
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

//    public void testLeaveGroup() throws Exception {
//        solo.clickOnText("Leave");
//        assertTrue(solo.waitForText("Cancel"));
//        solo.clickOnText("Cancel");
//    }
}
