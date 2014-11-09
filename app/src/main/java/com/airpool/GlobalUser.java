package com.airpool;

import android.app.Application;

/**
 * Created by minhtrangvy on 11/8/14.
 */
public class GlobalUser extends Application {

    private String _name;
    private String _userID;

    public String getName() {
        return this._name;
    }

    public void setName(String thisName) {
        this._name = thisName;
    }

    public String getUserID() {
        return _userID;
    }

    public void setUserID(String userID) {
        this._userID = userID;
    }

}
