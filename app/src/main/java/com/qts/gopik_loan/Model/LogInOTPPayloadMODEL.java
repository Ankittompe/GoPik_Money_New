package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInOTPPayloadMODEL {
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Expose
    @SerializedName("brand")
    private String brand;

}
