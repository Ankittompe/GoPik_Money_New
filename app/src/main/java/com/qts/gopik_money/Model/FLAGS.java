package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FLAGS {
    public ACCOUNTHOLDERNAME getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(ACCOUNTHOLDERNAME accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    @Expose
    @SerializedName("accountHolderName")
    private ACCOUNTHOLDERNAME accountHolderName;
}
