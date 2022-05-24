package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category_brand_wise_MODEL {   public String getMessage() {
    return message;
}

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;


    public ArrayList<CatagorypayloadMODEL> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<CatagorypayloadMODEL> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<CatagorypayloadMODEL> payload;

}
