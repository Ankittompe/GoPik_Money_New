package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Wallethistory_MODEL {
    public ArrayList<WALLET_HISTORY_DATA> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<WALLET_HISTORY_DATA> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<WALLET_HISTORY_DATA> payload;
}
