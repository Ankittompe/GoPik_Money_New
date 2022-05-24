package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AADHAR_VERIFICATION_RESULT_MODEL {

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Expose
    @SerializedName("mobile")
    private String mobile;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Expose
    @SerializedName("gender")
    private String gender;

    public String getAgeBand() {
        return ageBand;
    }

    public void setAgeBand(String ageBand) {
        this.ageBand = ageBand;
    }

    @Expose
    @SerializedName("ageBand")
    private String ageBand;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Expose
    @SerializedName("state")
    private String state;
}
