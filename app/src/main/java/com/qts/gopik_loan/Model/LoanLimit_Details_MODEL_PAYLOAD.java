package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanLimit_Details_MODEL_PAYLOAD {
    public String getTotal_credit_limit() {
        return total_credit_limit;
    }

    public void setTotal_credit_limit(String total_credit_limit) {
        this.total_credit_limit = total_credit_limit;
    }

    public String getAvailable_limit() {
        return available_limit;
    }

    public void setAvailable_limit(String available_limit) {
        this.available_limit = available_limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*"total_credit_limit": "0",
            "available_limit": "0",
            "status": "Inactive"*/

    @Expose
    @SerializedName("total_credit_limit")
    private String total_credit_limit;

    @Expose
    @SerializedName("available_limit")
    private String available_limit;

    @Expose
    @SerializedName("status")
    private String status;

}
