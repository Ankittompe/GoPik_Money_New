package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_otp_verify_for_ML_MODEL {
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

    public ML_OTP_MODEL getPayload() {
        return payload;
    }

    public void setPayload(ML_OTP_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ML_OTP_MODEL payload;
}
