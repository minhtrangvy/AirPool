package com.airpool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.airpool.Model.WallPost;
import com.airpool.R;
import com.facebook.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by Maury on 12/2/14.
 */
public class GroupWallPostsAdapter extends ArrayAdapter<WallPost> {
    private String userId;
    public GroupWallPostsAdapter(Context context, int resource, ArrayList<WallPost> objects, String userId) {
        super(context, resource, objects);
        this.userId = userId;
    }

    static class ViewHolder {
        TextView userName;
        TextView userDate;
        TextView userMessage;
        ProfilePictureView userPicture;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WallPost item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_wall_post, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userDate = (TextView) convertView.findViewById(R.id.user_date);
            viewHolder.userMessage = (TextView) convertView.findViewById(R.id.user_message);
            viewHolder.userPicture = (ProfilePictureView)
                    convertView.findViewById(R.id.user_picture);

            convertView.setTag(viewHolder);
        }

        // Fill the data.
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.userName.setText(item.getSender().getFirstName());
        viewHolder.userDate.setText("Sent " + item.getTimeSentString());
        viewHolder.userMessage.setText(item.getMessage());
        viewHolder.userPicture.setProfileId(item.getSender().getFacebookId());

        return convertView;
    }
}
