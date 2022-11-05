package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerITRDoc_MODEL {
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

    public ITR_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(ITR_PAYLOAD_MODEL payload) {
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
    private ITR_PAYLOAD_MODEL payload;
}
