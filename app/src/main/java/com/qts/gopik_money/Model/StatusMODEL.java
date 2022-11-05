package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusMODEL {
    public String getCust_tc_response() {
        return cust_tc_response;
    }

    public void setCust_tc_response(String cust_tc_response) {
        this.cust_tc_response = cust_tc_response;
    }

    @Expose
    @SerializedName("cust_tc_response")
    private String cust_tc_response;

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    @Expose
    @SerializedName("cust_code")
    private String cust_code;

}
