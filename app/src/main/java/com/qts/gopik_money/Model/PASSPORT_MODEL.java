package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PASSPORT_MODEL {
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("status-code")
    private String statusCode ;

    public Result_PASSPORT_MODEL getResult() {
        return result;
    }

    public void setResult(Result_PASSPORT_MODEL result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private Result_PASSPORT_MODEL result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Expose
    @SerializedName("requestId")
    private String requestId;

}
