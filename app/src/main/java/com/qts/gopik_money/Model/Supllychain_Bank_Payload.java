package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supllychain_Bank_Payload {

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;

    public Integer getPan_img() {
        return pan_img;
    }

    public void setPan_img(Integer pan_img) {
        this.pan_img = pan_img;
    }

    @Expose
    @SerializedName("pan_img")
    private Integer pan_img;

}
