package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class all_POs_MODEL {

    /*
         "code": 200,
                 "message": "Product Order data!!",
                 "payload":*/
    public ArrayList<all_POs_DATA> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<all_POs_DATA> payload) {
        this.payload = payload;
    }


@Expose
@SerializedName("payload")
private ArrayList<all_POs_DATA> payload;


    public String getCode() {
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


}
