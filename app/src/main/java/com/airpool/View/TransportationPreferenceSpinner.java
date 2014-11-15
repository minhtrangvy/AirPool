package com.airpool.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airpool.Model.TransportationPreference;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maury on 11/7/14.
 */
public class TransportationPreferenceSpinner extends AirPoolSpinner {
    public TransportationPreferenceSpinner(Context context) {
        super(context);
    }

    public TransportationPreferenceSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initializeSpinner(Context context) {
        ArrayList<TransportationPreference> preferencesList = new ArrayList<TransportationPreference>(Arrays.asList(TransportationPreference.values()));

        this.adapter = new TransportationPreferenceAdapter(context, android.R.layout.simple_spinner_item, preferencesList);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(this.adapter);
    }

    public class TransportationPreferenceAdapter extends AirPoolAdapter<TransportationPreference> {
        public TransportationPreferenceAdapter(Context context, int layoutResourceId, ArrayList<TransportationPreference> list) {
            super(context, layoutResourceId, list);
        }

        @Override
        public View getCustomView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(this.layoutResourceId, parent, false);
            }

            TransportationPreference preference = this.list.get(position);
            TextView mainText = (TextView) view.findViewById(android.R.id.text1);
            mainText.setText(preference.getPreferenceName());

            return view;
        }


        @Override
        public int getPosition(TransportationPreference transportationPreference) {
            return this.list.indexOf(transportationPreference);
        }
    }
}
