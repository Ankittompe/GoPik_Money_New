package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Po_add_MODEL {
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

    public POGenerate_Model getPayload() {
        return payload;
    }

    public void setPayload(POGenerate_Model payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private POGenerate_Model payload;
}
