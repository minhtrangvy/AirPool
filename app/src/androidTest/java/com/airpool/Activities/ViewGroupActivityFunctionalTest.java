package com.airpool.Activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

import com.airpool.EditGroupActivity;
import com.airpool.Model.Group;
import com.airpool.R;
import com.airpool.ViewGroupActivity;

/**
 * Created by Maury on 11/7/14.
 */
public class ViewGroupActivityFunctionalTest extends ActivityInstrumentationTestCase2<ViewGroupActivity> {
    public ViewGroupActivityFunctionalTest() {
        super(ViewGroupActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Does nothing.
    }

    public void testEditGroupButtonClick() {
        // Register the activity that the button click will open.
        Instrumentation.ActivityMonitor activityMonitor =
                getInstrumentation().addMonitor(EditGroupActivity.class.getName(), null, false);

        // Inject a group with the necessary attributes.
        Group group = new Group();
        group.setObjectId("R8qXcxSNag");
        getActivity().setGroup(group);

        final Button editGroupButton = (Button) getActivity().findViewById(R.id.edit_group_button);
        assertNotNull(editGroupButton);
        TouchUtils.clickView(this, editGroupButton);

        // Ensure that the new activity finishes loading.
        EditGroupActivity nextActivity = (EditGroupActivity)
                activityMonitor.waitForActivityWithTimeout(500);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
