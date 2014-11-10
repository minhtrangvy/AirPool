package com.airpool.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Maury on 11/8/14.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int mYear, mMonth, mDay;
    Calendar calendar = Calendar.getInstance();

    OnDatePickedListener callback;

    public interface OnDatePickedListener {
        public void onDatePicked(int year, int month, int day);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnDatePickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDatePickedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializeDay();

        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Add 1 to month since months are weirdly offset.
        callback.onDatePicked(year, month + 1, day);

        mYear = year - 1900;
        mMonth = month;
        mDay = day;
    }

    protected void initializeDay() {
        // Reset the year, month, and day to the values for today.
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public boolean isValidInput() {
        return !(mYear == 0 || mMonth == 0 || mDay == 0);
    }

    public int getYear() {
        return this.mYear;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getDay() {
        return this.mDay;
    }
}