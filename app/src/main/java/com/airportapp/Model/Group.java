package com.airportapp.Model;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by angel_000 on 9/27/2014.
 */
public class Group {
    User members;
    Timestamp pickupDateAndTime;
    boolean isAcceptingMembers;
    // ? pickupAddress;
    TransportationPreference preference;
    boolean travelingToAirport;
    Airport airport;
    HashMap<User, ?> userLocations;

}
