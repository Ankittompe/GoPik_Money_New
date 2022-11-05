package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerDocDeleteModel {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DealerDocDeletePayload getPayload() {
        return payload;
    }

    public void setPayload(DealerDocDeletePayload payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("payload")
    private DealerDocDeletePayload payload;


}
