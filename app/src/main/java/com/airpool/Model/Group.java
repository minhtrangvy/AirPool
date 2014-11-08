package com.airpool.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.HashMap;


@ParseClassName("Group")
public class Group extends ParseObject {

    public Group() {
        // A default constructor is required.
    }

    public String getGroupID() {
        return getString("groupID");
    }

    public void setGroupID(String groupID) {
        put("groupID", groupID);
    }

    public String getDate() {
        return getString("date");
    }

    public void setDate(String date) {
        put("date", date);
    }

    public String getTime() {
        return getString("time");
    }

    public void setTime(String time) {
        put("time", time);
    }

    public String getTransPref() {
        return getString("transPref");
    }

    public void setTransPref(String transPref) {
        put("transPref", transPref);
    }

    public String getAirport() {
        return getString("airport");
    }

    public void setAirport(String airport) {
        put("airport", airport);
    }

    public String getCollege() {
        return getString("college");
    }

    public void setCollege(String college) {
        put("college", college);
    }

    public boolean getToAirport() {
        return getBoolean("toAirport");
    }

    public void setToAirport(Boolean toAirport) {
        put("toAirport", toAirport);
    }

    public JSONArray getMembers() {
        return getJSONArray("members");
    }

    public void setMembers(JSONArray members) {
        put("members", members);
    }

    public boolean getGroupOpen() {
        return getBoolean("groupOpen");
    }

    public void setGroupOpen(String groupOpen) {
        put("groupOpen", groupOpen);
    }

}