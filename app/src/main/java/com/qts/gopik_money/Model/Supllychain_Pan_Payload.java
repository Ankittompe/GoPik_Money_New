package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supllychain_Pan_Payload {

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Integer getPan_img() {
        return pan_img;
    }

    public void setPan_img(Integer pan_img) {
        this.pan_img = pan_img;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;

    @Expose
    @SerializedName("pan_img")
    private Integer pan_img;
}
