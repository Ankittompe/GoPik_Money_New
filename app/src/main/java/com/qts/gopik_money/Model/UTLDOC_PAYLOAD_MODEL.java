package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UTLDOC_PAYLOAD_MODEL {
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getSelfie() {
        return Selfie;
    }

    public void setSelfie(String selfie) {
        Selfie = selfie;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;
    @Expose
    @SerializedName("UDC")
    private String Selfie;

}