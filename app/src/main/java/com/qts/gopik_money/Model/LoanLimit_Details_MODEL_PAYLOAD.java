package com.qts.gopik_money.Model;

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

    @Expose
    @SerializedName("roi")
    private String roi;

    @Expose
    @SerializedName("tenure")
    private String tenure;

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getFinancer() {
        return financer;
    }

    public void setFinancer(String financer) {
        this.financer = financer;
    }

    @Expose
    @SerializedName("financer")
    private String financer;

}
