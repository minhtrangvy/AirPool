package com.airpool.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@ParseClassName("WallPost")
public class WallPost extends ParseObject {
    public WallPost() {
        // A default constructor is required.
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

    public User getSender() {
        return (User) getParseObject("sender");
    }

    public void setSender(User user) {
        put("sender", user);
    }

    public Calendar getTimeSent() {
        Long date = getLong("timeSent");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public String getTimeSentString() {
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy 'at' h:mm a");
        Calendar timeOfDeparture = getTimeSent();
        return format.format(timeOfDeparture.getTime());
    }

    public void setTimeSent(Calendar timeSent) {
        put("timeSent", timeSent.getTimeInMillis());
    }

    public Group getGroup() {
        return (Group) getParseObject("group");
    }

    public void setGroup(Group group) {
        put("group", group);
    }
}