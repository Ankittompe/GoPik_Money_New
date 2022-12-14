package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Draftview_MODEL {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;


    public ArrayList<DRAFT_PAYLOAD> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<DRAFT_PAYLOAD> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<DRAFT_PAYLOAD> payload;
}
