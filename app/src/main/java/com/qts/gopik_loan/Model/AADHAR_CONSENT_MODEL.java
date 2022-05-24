package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AADHAR_CONSENT_MODEL {
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Expose
    @SerializedName("requestId")
    private String requestId;

    public AADHAR_CONSENT_RESULT_MODEL getResult() {
        return result;
    }

    public void setResult(AADHAR_CONSENT_RESULT_MODEL result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private AADHAR_CONSENT_RESULT_MODEL result;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("statusCode")
    private String statusCode;

    public AADHAR_CONSENT_CLIENTDATA_MODEL getClientData() {
        return clientData;
    }

    public void setClientData(AADHAR_CONSENT_CLIENTDATA_MODEL clientData) {
        this.clientData = clientData;
    }

    @Expose
    @SerializedName("clientData")
    private AADHAR_CONSENT_CLIENTDATA_MODEL clientData;
}

