package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VOTER_MODEL {
    public Result_VOTER_ID_MODEL getResult() {
        return result;
    }

    public void setResult(Result_VOTER_ID_MODEL result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private Result_VOTER_ID_MODEL result;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    @Expose
    @SerializedName("request_id")
    private String request_id;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    @Expose
    @SerializedName("status-code")
    private String statuscode;

}
