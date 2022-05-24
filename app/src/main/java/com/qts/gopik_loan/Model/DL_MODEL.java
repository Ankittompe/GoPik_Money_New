package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DL_MODEL {
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("statusCode")
    private String statusCode ;


    public Result_DL_PASSPORT_MODEL getResult() {
        return result;
    }

    public void setResult(Result_DL_PASSPORT_MODEL result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private Result_DL_PASSPORT_MODEL result;

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
