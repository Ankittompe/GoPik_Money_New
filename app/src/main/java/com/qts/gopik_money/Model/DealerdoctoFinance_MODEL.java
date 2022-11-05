package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerdoctoFinance_MODEL {
    /*"code": 200,
            "payload":*/

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private int code;

    public DealerdoctoFinance_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(DealerdoctoFinance_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private DealerdoctoFinance_PAYLOAD payload;

}
