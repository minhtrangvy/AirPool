package com.airpool.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("WallPost")
public class WallPost extends ParseObject {
    public WallPost() {
        // A default constructor is required.
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

    public String getSenderObjectId() {
        return getString("senderObjectId");
    }

    public void setSenderObjectId(String senderObjectId) {
        put("senderObjectId", senderObjectId);
    }
}