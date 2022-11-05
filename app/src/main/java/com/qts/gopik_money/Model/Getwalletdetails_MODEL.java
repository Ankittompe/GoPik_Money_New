package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getwalletdetails_MODEL {
    public String getBalance() {
    return balance;
}

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Expose
    @SerializedName("balance")
    private String balance;

}
