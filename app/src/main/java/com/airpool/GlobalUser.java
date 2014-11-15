package com.airpool;

import android.app.Application;
import android.util.Log;

import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by minhtrangvy on 11/8/14.
 */
public class GlobalUser extends Application {

//    private String _name;
//    private String ;
//    private String _facebookId;
    private String _userId;
    User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, "JFLuGOh9LQsqGsbVwuunD9uSSXgp8hDuDGBgHguJ",
                "0x2FoxHDKmIF81PqcK0wuh8OS8Ga2FsM6RTUmmcu");
    }

    public void setCurrentUser(User user) {

        this.currentUser = user;
        Log.i("GlobalUser", "got the user!" + user.getObjectId().toString());
    }
    public User getCurrentUser() {
        return this.currentUser;
    }

    public void findUserID(String facebookId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("facebookId", facebookId);
        query.getFirstInBackground( new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                // if the user does exist in our database
                if (parseObject != null) {
                    setUserID(parseObject.getObjectId());
                }
            }
        });
    }

    public String getUserID() {
        return this._userId;
    }

    public void setUserID(String userId) {
        this._userId = userId;
    }

//    public void getUserPref()

}
