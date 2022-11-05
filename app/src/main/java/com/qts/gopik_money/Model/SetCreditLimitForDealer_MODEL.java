package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCreditLimitForDealer_MODEL {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private int code;

    public SetCreditLimitForDealer_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(SetCreditLimitForDealer_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private SetCreditLimitForDealer_PAYLOAD payload;
}
