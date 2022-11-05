package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Shopkeeperpo_data_MODEL {
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


    public ArrayList<Shopkeeper_PO_profile> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<Shopkeeper_PO_profile> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    public ArrayList<Shopkeeper_PO_profile> payload;
}
