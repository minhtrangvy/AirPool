package com.airpool.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Maury on 11/8/14.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int mHour, mMinute;
    Calendar calendar = Calendar.getInstance();

    OnTimePickedListener callback;

    public interface OnTimePickedListener {
        public void onTimePicked(int hour, int minute, String twelveHrTimeStamp);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnTimePickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimePickedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializeTime();

        return new CustomTimePickerDialog(getActivity(), this, mHour, mMinute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int displayedHour = hourOfDay;

        String twelveHrTimeStamp = "am";
        // Set the Selected Date in Select date Button
        if (hourOfDay > 12) {
            displayedHour = hourOfDay % 12;
            twelveHrTimeStamp = "pm";
        }
        else if (hourOfDay == 0) {
            displayedHour = 12;
        }
        callback.onTimePicked(displayedHour, minute, twelveHrTimeStamp);

        mHour = hourOfDay;
        mMinute = minute;
    }

    public void initializeTime() {
        // Reset the hour and minute values for today.
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public boolean isValidInput() {
        return !(mHour == 0 || mMinute == 0);
    }

    public int getHour() {
        return this.mHour;
    }

    public int getMinute() {
        return this.mMinute;
    }

    public static class CustomTimePickerDialog extends TimePickerDialog{

        private final static int TIME_PICKER_INTERVAL = 15;
        private TimePicker timePicker;
        private final OnTimeSetListener callback;

        public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL,
                    is24HourView);
            this.callback = callBack;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (callback != null && timePicker != null) {
                timePicker.clearFocus();
                callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
            }
        }

        @Override
        protected void onStop() {
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                this.timePicker = (TimePicker) findViewById(timePickerField
                        .getInt(null));
                Field field = classForid.getField("minute");

                NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                        .findViewById(field.getInt(null));
                mMinuteSpinner.setMinValue(0);
                mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
                mMinuteSpinner.setWrapSelectorWheel(true);
                List<String> displayedValues = new ArrayList<String>();
                for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                    displayedValues.add(String.format("%02d", i));
                }
                mMinuteSpinner.setDisplayedValues(displayedValues
                        .toArray(new String[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
