package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class top_five_POs_MODEL {


    public ArrayList<top_five_POs_DATA> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<top_five_POs_DATA> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<top_five_POs_DATA> payload;


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

}
