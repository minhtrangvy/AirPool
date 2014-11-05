package com.airpool.Model;

//public class User {
//    TransportationPreference preference;
//
//    // More things in relation to the facebook API
//}

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

@ParseClassName("User")
public class User extends ParseObject {

    String _userId;

    public User() { }

    public User(String userId) {
        this._userId = userId;
    }

    public String getUserId() {
        return getString("userID");
    }
    public void setUserID(String userID) {
        put("userID", userID);
    }

    public boolean getLoggedIn(String userID) { return getBoolean("loggedIn"); }
    public void setLoggedIn(String userID, boolean loggedIn) { put("loggedIn", loggedIn); }

    public String getTransPref() {
        return getString("transPref");
    }
    public void setTransPref(String transPref) {
        put("transPref", transPref);
    }

    public JSONArray getGroups() {
        return getJSONArray("groups");
    }
    public void setGroups(JSONArray groups) {
        put("groups", groups);
    }

    public String getFirstName() { return getString("firstName"); }
    public void setFirstName(String firstName) { put("firstName", firstName); }

    public String getLastName() { return getString("LastName"); }
    public void setLastName(String lastName) { put("lastName", lastName); }

    public String getPicUrl() { return getString("picUrl"); }
    public void setPicUrl(String picUrl) { put("picUrl", picUrl); }

}
