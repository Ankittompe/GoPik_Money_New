package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supllychain_Aadhar_Payload {
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;

    public Integer getAdhar_img() {
        return adhar_img;
    }

    public void setAdhar_img(Integer adhar_img) {
        this.adhar_img = adhar_img;
    }

    @Expose
    @SerializedName("adhar_img")
    private Integer adhar_img;

}
