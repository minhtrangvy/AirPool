package com.airpool.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.airpool.Model.Airport;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maury on 11/8/14.
 */
abstract public class AirPoolSpinner extends Spinner {
    ArrayAdapter adapter;

    public AirPoolSpinner(Context context) {
        super(context);
    }

    public AirPoolSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    abstract public void initializeSpinner(Context context);

    abstract public class AirPoolAdapter<T> extends ArrayAdapter<T> {
        Context context;
        int layoutResourceId;
        ArrayList<T> list;

        public AirPoolAdapter(Context context, int layoutResourceId, ArrayList<T> list) {
            super(context, layoutResourceId, list);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.list = list;
        }

        @Override
        public View getDropDownView(int position, View view, ViewGroup parent) {
            View newView = super.getDropDownView(position, view, parent);
            return getCustomView(position, newView, parent);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View newView = super.getDropDownView(position, view, parent);
            return getCustomView(position, newView, parent);
        }

        abstract public View getCustomView(int position, View view, ViewGroup parent);

    }
}
