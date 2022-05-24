package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bankacc_verification_MODEL {
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Expose
    @SerializedName("requestId")
    private String requestId;

    public RESULT_BANK_ACCOUNT getResult() {
        return result;
    }

    public void setResult(RESULT_BANK_ACCOUNT result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private RESULT_BANK_ACCOUNT result;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("statusCode")
    private String statusCode;

}
