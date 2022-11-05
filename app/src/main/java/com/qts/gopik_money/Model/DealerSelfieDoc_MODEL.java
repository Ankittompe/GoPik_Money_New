package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerSelfieDoc_MODEL {
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

    public SELFIE_PAYLOAD_MODEL getPayload() {
        return payload;
    }

    public void setPayload(SELFIE_PAYLOAD_MODEL payload) {
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
    private SELFIE_PAYLOAD_MODEL payload;
}
