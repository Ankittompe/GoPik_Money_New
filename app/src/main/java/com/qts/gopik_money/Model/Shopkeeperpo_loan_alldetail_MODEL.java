package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeperpo_loan_alldetail_MODEL {
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


    public Shopkeeperpo_loan_alldetail_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(Shopkeeperpo_loan_alldetail_PAYLOAD_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private Shopkeeperpo_loan_alldetail_PAYLOAD_MODEL payload;
}
