package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resend_login_otp_MODEL {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @Expose
    @SerializedName("OTP")
    private String OTP;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

}
