package com.airpool.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

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

        return new TimePickerDialog(getActivity(), this, mHour, mMinute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;

        String twelveHrTimeStamp = "am";
        // Set the Selected Date in Select date Button
        if (mHour > 12) {
            mHour = mHour % 12;
            twelveHrTimeStamp = "pm";
        }
        else if (mHour ==0) {
            mHour = 12;
        }
        callback.onTimePicked(mHour, mMinute, twelveHrTimeStamp);
    }

    public void initializeTime() {
        // Reset the hour and minute values for today.
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public int getHour() {
        return this.mHour;
    }

    public int getMinute() {
        return this.mMinute;
    }
}
