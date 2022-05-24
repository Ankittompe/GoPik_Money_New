package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Send_otp_for_loan_MODEL {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @Expose
    @SerializedName("OTP")
    private String OTP;

    public OTPPAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(OTPPAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private OTPPAYLOAD payload;


}
