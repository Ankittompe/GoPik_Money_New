package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Po_products_Model {


    public ArrayList<PO_PRODUCTS_PAYLOAD> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<PO_PRODUCTS_PAYLOAD> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<PO_PRODUCTS_PAYLOAD> payload;


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
}
