package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeper_profile_update_MODEL {
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

    public SHOPKEEPER_PROFILE_UPLOAD_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(SHOPKEEPER_PROFILE_UPLOAD_PAYLOAD_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private SHOPKEEPER_PROFILE_UPLOAD_PAYLOAD_MODEL payload;
}

