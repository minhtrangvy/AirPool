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
    private Calendar calendar = Calendar.getInstance();
    private boolean isSelected = false;

    OnDatePickedListener callback;

    public interface OnDatePickedListener {
        public void onDatePicked(Calendar calendar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnDatePickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDatePickedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, getYear(), getMonth(), getDay());
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Create a new calendar object with the date passed in.
        calendar.set(year, month, day);
        isSelected = true;
        callback.onDatePicked(calendar);
    }

    public void initializeDay(int year, int month, int day) {
        calendar.set(year, month, day);
    }

    public boolean isValidInput() {
        return isSelected;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public int getYear() {
        return this.calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return this.calendar.get(Calendar.MONTH);
    }

    public int getDay() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }
}