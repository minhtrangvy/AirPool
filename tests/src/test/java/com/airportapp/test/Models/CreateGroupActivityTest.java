package com.airportapp.test.Models;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import com.airportapp.test.MyRobolectricTestRunner;
import com.airportapp.CreateGroupActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MyRobolectricTestRunner.class)

public class CreateGroupActivityTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = Robolectric.buildActivity(CreateGroupActivity.class).create().get();

        Assert.assertNotNull(activity);
    }
}
