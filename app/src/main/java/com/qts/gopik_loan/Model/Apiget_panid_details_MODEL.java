package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apiget_panid_details_MODEL {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;


    public PanCard_Payload getPayload() {
        return payload;
    }

    public void setPayload(PanCard_Payload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private PanCard_Payload payload;
}
