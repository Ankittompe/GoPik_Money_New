package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostOffice_PAYLOAD {
    public String getTaluk() {
        return Taluk;
    }

    public void setTaluk(String taluk) {
        Taluk = taluk;
    }

    @Expose
    @SerializedName("Taluk")
    private String Taluk;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    @Expose
    @SerializedName("State")
    private String State;

    public void setCountry(String country) {
        Country = country;
    }

    @Expose
    @SerializedName("Country")
    private String Country;
}
