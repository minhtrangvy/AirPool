package com.airportapp.test.Models;

import android.app.Activity;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import com.airportapp.test.MyRobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MyRobolectricTestRunner.class)

public class ExampleActivityTest {

    public void setup() {
        //do whatever is necessary before each test
    }

    @Test
    public void testAssertEquals() {
        assertTrue(true);
        assertFalse(false);
    }
}