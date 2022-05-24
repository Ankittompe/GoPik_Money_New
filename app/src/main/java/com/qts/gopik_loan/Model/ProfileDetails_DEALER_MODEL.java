package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetails_DEALER_MODEL {

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;


    public ProfilePayloadMODEL_DEALER getPayload() {
        return payload;
    }

    public void setPayload(ProfilePayloadMODEL_DEALER payload) {
        this.payload = payload;
    }

    @SerializedName("payload")
    private ProfilePayloadMODEL_DEALER payload;

}