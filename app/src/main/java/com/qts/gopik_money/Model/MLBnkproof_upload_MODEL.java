package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MLBnkproof_upload_MODEL {
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


    public BANKPROOF_MODEL getPayload() {
        return payload;
    }

    public void setPayload(BANKPROOF_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private BANKPROOF_MODEL payload;
}
