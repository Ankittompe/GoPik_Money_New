package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Dealer_invoice_data_MODEL {
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


    public ArrayList<InvoicePayloadMODEL_DEALER> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<InvoicePayloadMODEL_DEALER> payload) {
        this.payload = payload;
    }

    @SerializedName("payload")
    private ArrayList<InvoicePayloadMODEL_DEALER> payload;
}
