package com.airpool.Model;

//public class User {
//    TransportationPreference preference;
//
//    // More things in relation to the facebook API
//}

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {

    public User() { }

    public Object getTransPref() { return get("transportationPreference"); }
    public void setTransPref(boolean[] transPref) {
        put("transportationPreference", transPref);
    }

    public String getFirstName() { return getString("firstName"); }
    public void setFirstName(String firstName) { put("firstName", firstName); }

    public String getLastName() { return getString("lastName"); }
    public void setLastName(String lastName) { put("lastName", lastName); }

    public String getPicUrl() { return getString("picUrl"); }
    public void setPicUrl(String picUrl) { put("picUrl", picUrl); }

    public String getFacebookId() { return getString("facebookID"); }
    public void setFacebookId(String facebookId) { put("facebookID", facebookId); }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
