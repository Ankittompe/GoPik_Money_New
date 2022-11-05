package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeperpo_data_MODEL_datalist {
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


    public Shopkeeper_PO_profile_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(Shopkeeper_PO_profile_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    public Shopkeeper_PO_profile_PAYLOAD payload;
}
