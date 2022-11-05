package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Submit_loan_application_MODEL {
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

    public StatusPayload getPayload() {
        return payload;
    }

    public void setPayload(StatusPayload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private StatusPayload payload;
}

