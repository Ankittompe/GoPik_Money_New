package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dealer_doc_confirm_MODEL {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Dealer_doc_confirm_Payload getPayload() {
        return payload;
    }

    public void setPayload(Dealer_doc_confirm_Payload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("payload")
    private Dealer_doc_confirm_Payload payload;

}
