package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QR_ScannedList_MODEL {
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
    private ArrayList<QR_DataList_MODEL> data;
    public ArrayList<QR_DataList_MODEL> getData() {
        return data;
    }
    public void setData(ArrayList<QR_DataList_MODEL> data) {
        this.data = data;
    }
}
