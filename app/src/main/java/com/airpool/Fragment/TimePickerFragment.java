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
    Calendar calendar = Calendar.getInstance();
    OnTimePickedListener callback;

    public interface OnTimePickedListener {
        public void onTimePicked(Calendar calendar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnTimePickedListener) activity;
            callback.onTimePicked(this.getCalendar());
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimePickedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new CustomTimePickerDialog(getActivity(), this, getHour(), getMinute(),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    // Create a new calendar object with the date passed in.
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        callback.onTimePicked(calendar);
    }

    public void initializeTime(int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, (minute / 15));
    }

    public boolean isValidInput() {
        return true;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public static class CustomTimePickerDialog extends TimePickerDialog {

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
