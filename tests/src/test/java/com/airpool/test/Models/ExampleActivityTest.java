package com.airpool.test.Models;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.airpool.test.MyRobolectricTestRunner;

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
