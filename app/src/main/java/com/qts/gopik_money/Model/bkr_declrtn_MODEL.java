package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bkr_declrtn_MODEL {  public String getCode() {
    return code;
}

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;


    public BKR_MODEL getPayload() {
        return payload;
    }

    public void setPayload(BKR_MODEL payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private BKR_MODEL payload;

}
