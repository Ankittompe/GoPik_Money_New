package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class View_scratchcard_MODEL {
    public ArrayList<VIEW_SCRATCH_CARD_DATA> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<VIEW_SCRATCH_CARD_DATA> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<VIEW_SCRATCH_CARD_DATA> payload;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private Integer status;

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