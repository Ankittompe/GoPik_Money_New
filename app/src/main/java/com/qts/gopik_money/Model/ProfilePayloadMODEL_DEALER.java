package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfilePayloadMODEL_DEALER {

    public ArrayList<ProfileData_DEALER> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<ProfileData_DEALER> profile) {
        this.profile = profile;
    }

    @Expose
    @SerializedName("profile")
    private ArrayList<ProfileData_DEALER> profile;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;
}
