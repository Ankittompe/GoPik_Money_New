package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank_Upload_MODEL {
    @Expose
    @SerializedName("message")
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("code")
    private Integer code;
    public Integer getCode() {return code;}
    public void setCode(Integer code) {
        this.code = code;
    }

}
