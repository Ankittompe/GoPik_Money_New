package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanCard_Payload {
    public Result_PanCard_Payload getResult() {
    return result;
}

    public void setResult(Result_PanCard_Payload result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private Result_PanCard_Payload result;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    @Expose
    @SerializedName("request_id")
    private String request_id;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @Expose
    @SerializedName("status-code")
    private String status_code;

}
