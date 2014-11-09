package com.airpool.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;


@ParseClassName("Group")
public class Group extends ParseObject {
    public Group() {
        // A default constructor is required.
    }

    public Date getTimeOfDeparture() {
        Long date = getLong("timeOfDeparture");
        return new Date(date);
    }

    public void setTimeOfDeparture(Date timeOfDeparture) {
        put("timeOfDeparture", timeOfDeparture.getTime());
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

    public String toString() {
        return getAirport() + " " + getCollege();
    }
}