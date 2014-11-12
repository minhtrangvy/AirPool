package com.airpool.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airpool.Model.Airport;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maury on 11/7/14.
 */
public class AirportSpinner extends AirPoolSpinner {
    public AirportSpinner(Context context) {
        super(context);
    }

    public AirportSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initializeSpinner(Context context) {
        this.adapter = new AirportAdapter(context, android.R.layout.simple_spinner_item,
                new ArrayList<Airport>(Arrays.asList(Airport.values())));
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(this.adapter);
    }


    public class AirportAdapter extends AirPoolAdapter<Airport> {
        public AirportAdapter(Context context, int layoutResourceId, ArrayList<Airport> list) {
            super(context, layoutResourceId, list);
        }

        @Override
        public View getCustomView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(this.layoutResourceId, parent, false);
            }

            Airport airport = this.list.get(position);
            TextView mainText = (TextView) view.findViewById(android.R.id.text1);
            mainText.setText(airport.getAirportAbbreviation()+ " - " + airport.getAirportName());

            return view;
        }
    }
}
