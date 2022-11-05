package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerDocDeletePayload {

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Expose
    @SerializedName("usercode")
    private  String usercode;
    @Expose
    @SerializedName("type")
    private  String type;

}
