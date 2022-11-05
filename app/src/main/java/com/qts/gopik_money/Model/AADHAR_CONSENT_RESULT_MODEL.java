package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AADHAR_CONSENT_RESULT_MODEL {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Expose
    @SerializedName("accessKey")
    private String accessKey;

    public String getAccessKeyValidity() {
        return accessKeyValidity;
    }

    public void setAccessKeyValidity(String accessKeyValidity) {
        this.accessKeyValidity = accessKeyValidity;
    }

    @Expose
    @SerializedName("accessKeyValidity")
    private String accessKeyValidity;

}
