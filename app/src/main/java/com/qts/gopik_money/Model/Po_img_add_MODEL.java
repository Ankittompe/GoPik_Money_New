package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Po_img_add_MODEL {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("massage")
    private String massage;
    @Expose
    @SerializedName("payload")
    private String payload;
}
