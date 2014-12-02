package com.airpool;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.airpool.R;
import com.robotium.solo.Solo;
import com.airpool.SearchActivity;
import com.airpool.SearchResultsActivity;

/**
 * Created by angelachin on 12/1/14.
 */
public class SearchActivityRobotiumTest extends
        ActivityInstrumentationTestCase2<SearchActivity> {

    private Solo solo;

    public SearchActivityRobotiumTest() {
        super(SearchActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testSearchResultsButtonClick() {
        // check that we have the right activity
        solo.assertCurrentActivity("wrong activity", SearchActivity.class);

        solo.clickOnText("Departure Date");
        solo.setDatePicker(0, 2015, 1, 16);
        solo.clickOnText("Done");

        solo.clickOnText("Departure Time");
        solo.setTimePicker(0, 10, 45);
        solo.clickOnText("Done");

        // Click a button which will start a new Activity
        // Here we use the ID of the string to find the right button
        solo.clickOnButton(solo.getString(R.string.search));
        // Validate that the Activity is the correct one
        solo.assertCurrentActivity("wrong activity", SearchResultsActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("wrong activity", SearchActivity.class);
    }


    public void testAirportSpinner() {
        boolean actual;

        solo.pressSpinnerItem(1, 0);
        actual = solo.isSpinnerTextSelected(1, "BUR - Bob Hope Airport");
        assertEquals("spinner item BUR is not selected", true, actual);

        solo.pressSpinnerItem(1, 4);
        actual = solo.isSpinnerTextSelected(1, "SNA - John Wayne Airport/ Orange County");
        assertEquals("spinner item SNA is not selected",true, actual);

        solo.pressSpinnerItem(1, -2);
        actual = solo.isSpinnerTextSelected(1, "LAX - Los Angeles International Airport");
        assertEquals("spinner item LAX is not selected",true, actual);
    }

    public void testCollegeSpinner() {
        boolean actual;

        solo.pressSpinnerItem(2, 0);
        actual = solo.isSpinnerTextSelected(2, "California State Polytechnic University, Pomona");
        assertEquals("spinner item Cal Poly is not selected",true, actual);

        solo.pressSpinnerItem(2, 7);
        actual = solo.isSpinnerTextSelected(2, "University of Southern California");
        assertEquals("spinner item USC is not selected",true, actual);

        solo.pressSpinnerItem(2, -2);
        actual = solo.isSpinnerTextSelected(2, "University of California, Irvine");
        assertEquals("spinner item UC Irvine is not selected",true, actual);
    }

    public void testDatePicker() {
        boolean correctDate;

        solo.clickOnText(solo.getString(R.string.selectDate));
        solo.setDatePicker(0, 2015, 1, 16);
        solo.clickOnText("Done");

        correctDate = solo.searchText("Departure Date: 02/16/2015");
        assertEquals("Departure Date not correctly selected", true, correctDate);
    }

    public void testTimePicker() {
        boolean correctTime;

        solo.clickOnText("Departure Time");
        solo.setTimePicker(0, 9, 45);
        solo.clickOnText("Done");

        correctTime = solo.searchText("Departure Time: 8:45 AM");
        assertEquals("Departure Time not correctly selected", true, correctTime);
    }

    public void testSearchResultsText() {
        boolean correctText;

        // check that we have the right activity
        solo.assertCurrentActivity("wrong activity", SearchActivity.class);

        solo.clickOnText("Departure Date");
        solo.setDatePicker(0, 2015, 1, 16);
        solo.clickOnText("Done");

        solo.clickOnText("Departure Time");
        solo.setTimePicker(0, 10, 45);
        solo.clickOnText("Done");

        solo.pressSpinnerItem(2, 1);

        // Click a button which will start a new Activity
        // Here we use the ID of the string to find the right button
        solo.clickOnButton(solo.getString(R.string.search));
        // Validate that the Activity is the correct one
        solo.assertCurrentActivity("wrong activity", SearchResultsActivity.class);

        //Check that the top text is correct
        correctText = solo.searchText("Group Rides from Claremont Colleges");
        assertEquals("Top text display not correct", true, correctText);

    }
}
