package com.airpool.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airpool.Model.College;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maury on 11/7/14.
 */
public class CollegeSpinner extends AirPoolSpinner {
    College currentCollege = null;
    CollegeAdapter adapter;

    public CollegeSpinner(Context context) {
        super(context);
    }

    public CollegeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initializeSpinner(Context context) {
        this.adapter = new CollegeAdapter(context, android.R.layout.simple_spinner_item,
                new ArrayList<College>(Arrays.asList(College.values())));
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(this.adapter);
    }

    public class CollegeAdapter extends AirPoolAdapter<College> {
        public CollegeAdapter(Context context, int layoutResourceId, ArrayList<College> list) {
            super(context, layoutResourceId, list);
        }

        @Override
        public View getCustomView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(this.layoutResourceId, parent, false);
            }

            College college = this.list.get(position);
            TextView mainText = (TextView) view.findViewById(android.R.id.text1);
            mainText.setText(college.getFullName());

            return view;
        }

        @Override
        public int getPosition(College college) {
            return this.list.indexOf(college);
        }
    }
}
