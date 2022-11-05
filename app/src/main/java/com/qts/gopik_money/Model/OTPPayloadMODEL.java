package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPPayloadMODEL {
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
}
