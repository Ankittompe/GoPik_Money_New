package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportNumber_MODEL {
    public String getPassportNumberFromSource() {
        return passportNumberFromSource;
    }

    public void setPassportNumberFromSource(String passportNumberFromSource) {
        this.passportNumberFromSource = passportNumberFromSource;
    }

    @Expose
    @SerializedName("passportNumberFromSource")
    private String passportNumberFromSource ;

    public String getPassportNumberMatch() {
        return passportNumberMatch;
    }

    public void setPassportNumberMatch(String passportNumberMatch) {
        this.passportNumberMatch = passportNumberMatch;
    }

    @Expose
    @SerializedName("passportNumberMatch")
    private String passportNumberMatch ;

}
