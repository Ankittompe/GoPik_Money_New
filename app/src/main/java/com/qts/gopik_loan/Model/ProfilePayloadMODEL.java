package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfilePayloadMODEL {


    public ArrayList<ProfileData> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<ProfileData> profile) {
        this.profile = profile;
    }

    @Expose
    @SerializedName("profile")
    private ArrayList<ProfileData> profile;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;
}
