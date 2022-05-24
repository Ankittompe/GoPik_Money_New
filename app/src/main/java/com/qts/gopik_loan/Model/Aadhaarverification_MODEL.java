package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aadhaarverification_MODEL {
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Expose
    @SerializedName("requestId")
    private String requestId;

    public AADHAR_VERIFICATION_RESULT_MODEL getResult() {
        return result;
    }

    public void setResult(AADHAR_VERIFICATION_RESULT_MODEL result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("result")
    private AADHAR_VERIFICATION_RESULT_MODEL result;
    @Expose
    @SerializedName("statusCode")
    private String statusCode;

    public AADHAR_VERIFICATION_CLIENTDATA_MODEL getClientData() {
        return clientData;
    }

    public void setClientData(AADHAR_VERIFICATION_CLIENTDATA_MODEL clientData) {
        this.clientData = clientData;
    }

    @Expose
    @SerializedName("clientData")
    private AADHAR_VERIFICATION_CLIENTDATA_MODEL clientData;


}
