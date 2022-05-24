package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ML_OTP_MODEL {
    public String getBroker_mobile() {
        return broker_mobile;
    }

    public void setBroker_mobile(String broker_mobile) {
        this.broker_mobile = broker_mobile;
    }

    @Expose
    @SerializedName("broker_mobile")
    private String broker_mobile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Expose
    @SerializedName("token")
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;


    public String getBkr_declaration() {
        return bkr_declaration;
    }

    public void setBkr_declaration(String bkr_declaration) {
        this.bkr_declaration = bkr_declaration;
    }

    @Expose
    @SerializedName("bkr_declaration")
    private String bkr_declaration;


    public String getBkr_code() {
        return bkr_code;
    }

    public void setBkr_code(String bkr_code) {
        this.bkr_code = bkr_code;
    }

    @Expose
    @SerializedName("bkr_code")
    private String bkr_code;
}
