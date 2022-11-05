package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Shopkeeper_PO_MODEL_PAYLOAD {


    public ArrayList<Shopkeeper_PO_profile> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<Shopkeeper_PO_profile> profile) {
        this.profile = profile;
    }

    @Expose
    @SerializedName("podata")
    private ArrayList<Shopkeeper_PO_profile> profile;
}
