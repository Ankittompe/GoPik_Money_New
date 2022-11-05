package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_voterid_details_MODEL {
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

    public Voter_Payload getPayload() {
        return payload;
    }

    public void setPayload(Voter_Payload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private Voter_Payload payload;


}
