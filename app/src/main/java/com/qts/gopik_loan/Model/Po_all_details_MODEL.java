package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Po_all_details_MODEL {
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


    public ArrayList<ALL_PO_PAYLOAD_MODEL> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<ALL_PO_PAYLOAD_MODEL> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<ALL_PO_PAYLOAD_MODEL> payload;
}
