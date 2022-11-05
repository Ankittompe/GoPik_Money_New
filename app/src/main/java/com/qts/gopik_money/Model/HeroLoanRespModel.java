
package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class HeroLoanRespModel {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("payload")
    private Payload mPayload;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Payload getPayload() {
        return mPayload;
    }

    public void setPayload(Payload payload) {
        mPayload = payload;
    }

}
