package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOURCE_ARRAYLIST {
    public DATA_BANK_ACCOUNT_SOURCE getData() {
        return data;
    }

    public void setData(DATA_BANK_ACCOUNT_SOURCE data) {
        this.data = data;
    }

    @Expose
    @SerializedName("data")
    private DATA_BANK_ACCOUNT_SOURCE data;

    public String getStatusAsPerSource() {
        return statusAsPerSource;
    }

    public void setStatusAsPerSource(String statusAsPerSource) {
        this.statusAsPerSource = statusAsPerSource;
    }

    @Expose
    @SerializedName("statusAsPerSource")
    private String statusAsPerSource;

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Expose
    @SerializedName("isValid")
    private String isValid;
}
