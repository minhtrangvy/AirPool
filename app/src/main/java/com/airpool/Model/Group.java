package com.airpool.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@ParseClassName("Group")
public class Group extends ParseObject {
    int numberOfUsers;

    public Group() {
        // A default constructor is required.
    }

    public Calendar getTimeOfDeparture() {
        Long date = getLong("timeOfDeparture");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public String getTimeOfDepartureString() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a");
        Calendar timeOfDeparture = getTimeOfDeparture();
        return format.format(timeOfDeparture.getTime());
    }

    public void setTimeOfDeparture(Calendar timeOfDeparture) {
        put("timeOfDeparture", timeOfDeparture.getTimeInMillis());
    }

    public TransportationPreference getTransportationPreference() {
        String enumKey = getString("transportationPreference");
        return Enum.valueOf(TransportationPreference.class, enumKey);
    }

    public void setTransportationPreference(TransportationPreference transportationPreference) {
        put("transportationPreference", transportationPreference.name());
    }

    public Airport getAirport() {
        String enumKey = getString("airport");
        return Enum.valueOf(Airport.class, enumKey);
    }

    public void setAirport(Airport airport) {
        put("airport", airport.name());
    }

    public College getCollege() {
        String enumKey = getString("college");
        return Enum.valueOf(College.class, enumKey);
    }

    public void setCollege(College college) {
        put("college", college.name());
    }

    public boolean getIsToAirport() {
        return getBoolean("isToAirport");
    }

    public void setIsToAirport(boolean toAirport) {
        put("isToAirport", toAirport);
    }

    public boolean getIsGroupOpen() {
        return getBoolean("isGroupOpen");
    }

    public void setIsGroupOpen(boolean isGroupOpen) {
        put("isGroupOpen", isGroupOpen);
    }

    public boolean getIsActive() {
        return getBoolean("isActive");
    }

    public void setIsActive(boolean isActive) {
        put("isActive", isActive);
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public String toString() {
        return getAirport() + " " + getCollege();
    }
}