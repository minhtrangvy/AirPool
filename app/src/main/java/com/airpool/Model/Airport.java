package com.airpool.Model;

public enum Airport {
    BUR("Burbank","California","CA","Bob Hope Airport","BUR"),
    LGB("Long Beach","California","CA","Long Beach Airport","LGB"),
    LAX("Los Angeles","California","CA","Los Angeles International Airport","LAX"),
    ONT("Ontario","California","CA","Ontario International Airport","ONT"),
    SNA("Santa Ana","California","CA","John Wayne Airport/ Orange County","SNA");

    private String city;
    private String state;
    private String stateAbbreviation;
    private String airportName;
    private String airportAbbreviation;

    private Airport(String city, String state, String stateAbbreviation,
                    String airportName, String airportAbbreviation) {
        this.city = city;
        this.state = state;
        this.stateAbbreviation = stateAbbreviation;
        this.airportName = airportName;
        this.airportAbbreviation = airportAbbreviation;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getStateAbbreviation() {
        return this.stateAbbreviation;
    }

    public String getAirportName() {
        return this.airportName;
    }

    public String getAirportAbbreviation() {
        return this.airportAbbreviation;
    }

};