package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loan_Status {
    public String getCust_tc_response() {
        return cust_tc_response;
    }

    public void setCust_tc_response(String cust_tc_response) {
        this.cust_tc_response = cust_tc_response;
    }

    @Expose
    @SerializedName("cust_tc_response")
    private String cust_tc_response;

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    @Expose
    @SerializedName("customer_code")
    private String customer_code;

}
