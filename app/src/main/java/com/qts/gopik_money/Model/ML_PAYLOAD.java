package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ML_PAYLOAD {

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    @Expose
    @SerializedName("customer_mobile")
    private String customer_mobile;

    public String getCust_tc_response() {
        return cust_tc_response;
    }

    public void setCust_tc_response(String cust_tc_response) {
        this.cust_tc_response = cust_tc_response;
    }

    @Expose
    @SerializedName("cust_tc_response")
    private String cust_tc_response;
}
