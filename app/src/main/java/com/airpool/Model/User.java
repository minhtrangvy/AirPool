package com.airpool.Model;

//public class User {
//    TransportationPreference preference;
//
//    // More things in relation to the facebook API
//}

import com.airpool.GlobalUser;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.app.Application;
import android.content.Context;

import org.json.JSONArray;

@ParseClassName("User")
public class User extends ParseObject {

//    GlobalUser globalUser = new GlobalUser();
//    Context context;
    public String _userId;
//    public ParseObject _parseUser;
//    public boolean _loggedIn;
//    private String[] transPref;

    public User() { }

    /*
     * User constructor
     * takes in a facebookId
     * because every time we want to find a user, they log in thru fb
     */
//    public User(final String facebookID) { //}), Context c) {
////        String userID = this.globalUser.getUserID(facebookID);
////        this._userId = userID;
////        GlobalUser globalUser = getA;
//        this._facebookID = facebookID;
//
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
//        query.whereEqualTo("facebookId", facebookID);
//        query.getFirstInBackground( new GetCallback<ParseObject>() {
//            ParseObject currentObject;
//
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//                // if the user does not exist in our database, make a new user
//                if (parseObject == null) {
//                    ParseObject newUser = new ParseObject("User");
//                    newUser.put("facebookId", facebookID);
//                }
//            }
//        });
//    }

//    // we only want to set a user object when this is a new user
//    public ParseObject setUser(String facebookID) {
//        ParseObject newUser = new ParseObject("User");
//        newUser.put("facebookId", facebookID);
//        newUser.saveInBackground();
//        return newUser;
//    }

    // we only get this user object when this person is already a user


//    public void checkUserExists(String userId) {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
//        query.whereEqualTo("userId", userId);
//        query.getFirstInBackground( new GetCallback<ParseObject>() {
//
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//                // if the user does not exist in our database, make a new user
//                if (parseObject == null) {
//                    //
//                }
//            }
//        });
//    }

    public boolean getLoggedIn(String userID) { return getBoolean("loggedIn"); }
    public void setLoggedIn(String userID, boolean loggedIn) { put("loggedIn", loggedIn); }

    public Object getTransPref() {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
//        query.whereEqualTo("userId", _userId);
//        query.getFirstInBackground( new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//                if (parseObject != null) {
//                    _transPref = parseObject.get
//                }
//            }
//        });
        return get("transportationPreference");
    }

    public void setTransPref(boolean[] transPref) {
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

    public String getLastName() { return getString("lastName"); }
    public void setLastName(String lastName) { put("lastName", lastName); }

    public String getPicUrl() { return getString("picUrl"); }
    public void setPicUrl(String picUrl) { put("picUrl", picUrl); }


    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
