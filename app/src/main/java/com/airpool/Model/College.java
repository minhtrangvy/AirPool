package com.airpool.Model;

/**
 * Created by Maury on 11/7/14.
 */
public enum College {
    CSUP("CSUP", "California State Polytechnic University, Pomona"),
    FIVECs("FIVECs", "Claremont Colleges"),
    PEPP("PEPP", "Pepperdine University"),
    UCLA("UCLA", "University of California, Los Angeles"),
    UCFU("UCFU", "University of California, Fullerton"),
    UCIR("UCIR", "University of California, Irvine"),
    UCRI("UCRI", "University of California, Riverside"),
    USC("USC", "University of Southern California");

    private String abbreviation;
    private String fullName;

    private College(String abbreviation, String fullName) {
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getFullName() {
        return this.fullName;
    }
};
