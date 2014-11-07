package com.airpool.Model;

import java.sql.Timestamp;
import java.util.HashMap;


public class Group {
    User members;
    Timestamp pickupDateAndTime;
    boolean isAcceptingMembers;
    // ? pickupAddress;
    TransportationPreference preference;
    boolean travelingToAirport;
    // Airport airport;
    HashMap<User, ?> userLocations;

}
