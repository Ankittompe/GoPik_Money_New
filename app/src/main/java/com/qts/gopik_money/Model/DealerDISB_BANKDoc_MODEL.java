package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerDISB_BANKDoc_MODEL {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BANKDISB_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(BANKDISB_PAYLOAD_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("payload")
    private BANKDISB_PAYLOAD_MODEL payload;
}
