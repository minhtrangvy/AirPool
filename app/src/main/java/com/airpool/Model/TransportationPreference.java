package com.airpool.Model;

public enum TransportationPreference {
    TAXI("TAXI", "Taxi"),
    SS("SS", "SuperShuttle"),
    PUBTRANS("PUBTRANS", "Public Transit"),
    DRIVE("DRIVE", "Drive"),
    NOPREF("NOPREF", "No preference");

    private String abbreviation;
    private String preference;

    private TransportationPreference(String abbreviation, String preference) {
        this.abbreviation = abbreviation;
        this.preference = preference;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getPreferenceName() {
        return this.preference;
    }

    public String toString() {
        return getPreferenceName();
    }
}
