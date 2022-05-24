package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calculation_data_MODEL {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;


    public CalculationDATA_MODEL getPayload() {
        return payload;
    }

    public void setPayload(CalculationDATA_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private CalculationDATA_MODEL payload;
}
