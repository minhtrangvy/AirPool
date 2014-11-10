package com.airpool;

import android.app.Application;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by minhtrangvy on 11/8/14.
 */
public class GlobalUser extends Application {

//    private String _name;
//    private String ;
    private String _facebookId;
    private String _userId;

//    public String getName() {
//        return this._name;
//    }
//
//    public void setName(String thisName) {
//        this._name = thisName;
//
//    }

//    public String getUserID(String facebookID) {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//        final String[] userID = new String[1];
//        query.getInBackground(facebookID, new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//                if (e == null ) {
//                    userID[0] = parseObject.getString("objectId");
//                } else {
//                    userID[0] = "";
//                }
//            }
//        });
//        return userID[0];
//    }

    public String getFacebookId() {
        return this._facebookId;
    }

    public void setFacebookId(String facebookID) {
        this._facebookId = facebookID;
    }

    public String getUserID() {
        return this._userId;
    }

    public void setUserID(String userID) {
        this._userId = userID;
    }

//    public void getUserPref()

}
