package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Get_loan_status_MODEL {
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

    public ArrayList<Loan_Status> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<Loan_Status> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<Loan_Status> payload;
}