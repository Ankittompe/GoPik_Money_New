package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broker_profile_update_MODEL {
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

    public PROFILE_UPLOAD_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(PROFILE_UPLOAD_PAYLOAD_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private PROFILE_UPLOAD_PAYLOAD_MODEL payload;
}
