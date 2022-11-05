package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qts.gopik_money.Pojo.Dealer_WhatsApp_POJO;

import java.util.ArrayList;

public class WhatsAppStatusList_MODEL {
    @Expose
    @SerializedName("message")
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


    @Expose
    @SerializedName("data")
    private ArrayList<Dealer_WhatsApp_POJO> data;
    public ArrayList<Dealer_WhatsApp_POJO> getData() {
        return data;
    }
    public void setData(ArrayList<Dealer_WhatsApp_POJO> data) {
        this.data = data;
    }
}
