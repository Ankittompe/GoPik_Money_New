package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BANK_DOCUMENT_MODEL {


    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("message")
    private String message;

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

    @Expose
    @SerializedName("payload")
    private BANK_DOCUMENT_PAYLOAD_MODEL payload;
}
