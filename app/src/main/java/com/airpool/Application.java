package com.airpool;

import com.airpool.Model.Group;
import com.airpool.Model.User;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Maury on 11/8/14.
 */
public class Application extends android.app.Application {
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
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
