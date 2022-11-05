package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerAadharFrontDoc_MODEL {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AADHAR_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(AADHAR_PAYLOAD_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("payload")
    private AADHAR_PAYLOAD_MODEL payload;
}
