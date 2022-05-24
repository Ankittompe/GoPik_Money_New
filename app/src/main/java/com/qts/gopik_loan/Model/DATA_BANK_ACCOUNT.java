package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DATA_BANK_ACCOUNT {
    public ArrayList<SOURCE_ARRAYLIST> getSource() {
        return source;
    }

    public void setSource(ArrayList<SOURCE_ARRAYLIST> source) {
        this.source = source;
    }

    @Expose
    @SerializedName("source")
    private ArrayList<SOURCE_ARRAYLIST> source;
}
