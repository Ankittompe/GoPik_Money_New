package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInOtpVerifyMODEL {


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

    public LogInOTPPayloadMODEL getPayload() {
        return payload;
    }

    public void setPayload(LogInOTPPayloadMODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private  LogInOTPPayloadMODEL payload;
}
