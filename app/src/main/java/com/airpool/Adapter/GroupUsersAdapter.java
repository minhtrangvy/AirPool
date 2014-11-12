package com.airpool.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airpool.Model.User;
import com.airpool.R;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Maury on 11/9/14.
 */
public class GroupUsersAdapter extends ArrayAdapter<User> {
    public GroupUsersAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
    }

    static class ViewHolder {
        TextView memberName;
        ProfilePictureView memberPicture;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User item = getItem(position);

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_group_members, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.memberName = (TextView) convertView.findViewById(R.id.member_name);
            viewHolder.memberPicture = (ProfilePictureView)
                    convertView.findViewById(R.id.member_picture);

            convertView.setTag(viewHolder);
        }

        // Fill the data.
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.memberName.setText(item.toString());
        viewHolder.memberPicture.setProfileId(item.getFacebookId().toString());

        return convertView;
    }
}