package com.airpool.Activities;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;

import com.airpool.EditGroupActivity;
import com.airpool.R;
import com.airpool.ViewGroupActivity;

/**
 * Created by Maury on 11/7/14.
 */
public class EditGroupActivityFunctionalTest extends ActivityInstrumentationTestCase2<EditGroupActivity> {
    public EditGroupActivityFunctionalTest() {
        super(EditGroupActivity.class);
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

    public void testCreateGroupButtonClick() {
        // Register the activity that the button click will open.
        Instrumentation.ActivityMonitor activityMonitor =
                getInstrumentation().addMonitor(ViewGroupActivity.class.getName(), null, false);

        final Button createGroupButton = (Button) getActivity().findViewById(R.id.create_group_button);
        assertNotNull(createGroupButton);
        TouchUtils.clickView(this, createGroupButton);

        // Ensure that the new activity finishes loading.
        ViewGroupActivity nextActivity = (ViewGroupActivity)
                activityMonitor.waitForActivityWithTimeout(500);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
}
