package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanLimit_Details_MODEL {
 /*"code": 200,
         "message": "Successfully get the data!!",
         "payload": {*/

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

    public LoanLimit_Details_MODEL_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(LoanLimit_Details_MODEL_PAYLOAD payload) {
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
    private LoanLimit_Details_MODEL_PAYLOAD payload;

}
