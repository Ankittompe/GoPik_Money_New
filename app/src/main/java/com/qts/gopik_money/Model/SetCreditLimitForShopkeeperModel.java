package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCreditLimitForShopkeeperModel {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private int code;


    public SetCreditLimitForShopkeeper_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(SetCreditLimitForShopkeeper_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private SetCreditLimitForShopkeeper_PAYLOAD payload;
}
