package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GOATKYCSTORE_MODEL {

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public GOATKYCSTORE_MODEL_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(GOATKYCSTORE_MODEL_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private GOATKYCSTORE_MODEL_PAYLOAD payload;


}
