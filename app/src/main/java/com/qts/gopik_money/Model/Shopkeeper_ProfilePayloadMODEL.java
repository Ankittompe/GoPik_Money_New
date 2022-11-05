package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Shopkeeper_ProfilePayloadMODEL {

    public ArrayList<Shopkeeper_ProfileData> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<Shopkeeper_ProfileData> profile) {
        this.profile = profile;
    }

    @Expose
    @SerializedName("profile")
    private ArrayList<Shopkeeper_ProfileData> profile;


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
