package com.airportapp.test.Models;

import com.airportapp.test.MyRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MyRobolectricTestRunner.class)
public class MyClassTest {
    @Test
    public void testAssertEquals() {
        assertTrue(true);
        assertFalse(false);
    }


}
