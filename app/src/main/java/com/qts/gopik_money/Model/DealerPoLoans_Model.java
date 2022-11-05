package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DealerPoLoans_Model {
    /* "code": 200,
             "message": "Product Order data!!",
             "payload": [*/
    @Expose
    @SerializedName("code")
    private  int code;
    @Expose
    @SerializedName("message")
    private  String message;

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

    public ArrayList<DealerPoLoansResponse_Model> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<DealerPoLoansResponse_Model> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<DealerPoLoansResponse_Model> payload;
}
