package com.airpool.test.Models;

import android.app.Activity;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import com.airpool.R;
import com.airpool.ViewGroupActivity;
import com.airpool.test.MyRobolectricTestRunner;
import com.airpool.EditGroupActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(MyRobolectricTestRunner.class)

public class EditGroupActivityTest {

    private final ActivityController<EditGroupActivity> controller = buildActivity(EditGroupActivity.class);

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = buildActivity(EditGroupActivity.class).create().get();

        Assert.assertNotNull(activity);
    }

    @Test
    public void test_clickingViewGroup_shouldStartViewGroupActivity() {
        EditGroupActivity activity = controller.create().start().resume().get();
        activity.findViewById(R.id.viewGroup_button).performClick();

        Intent expectedIntent = new Intent(activity, ViewGroupActivity.class);
        assertThat(shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}
