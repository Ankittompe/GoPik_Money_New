package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer_adhar_molldoc_MODEL {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    @Expose
    @SerializedName("message")
    private String message;

    public Supllychain_Aadhar_Payload getPayload() {
        return payload;
    }

    public void setPayload(Supllychain_Aadhar_Payload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private Supllychain_Aadhar_Payload payload;

}
