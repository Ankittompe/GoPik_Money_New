package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loan_calculation_Model {
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


    public LoanCalculateMODEL getPayload() {
        return payload;
    }

    public void setPayload(LoanCalculateMODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private LoanCalculateMODEL  payload;
}
