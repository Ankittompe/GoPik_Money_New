package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class shopkeeperpo_5data_list_MODEL {
/*"code": 200,
        "message": "All Data Fetched Successfully!!",
        "payload":*/

    @Expose
    @SerializedName("code")
    private int code ;

    @Expose
    @SerializedName("message")
    private String message ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public shopkeeperpo_5data_list_PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(shopkeeperpo_5data_list_PAYLOAD payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private shopkeeperpo_5data_list_PAYLOAD payload ;

}
