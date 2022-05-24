package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOPTHREEAPPLIACTIONMODEL {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("id")
    private String id;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    @Expose
    @SerializedName("user_code")
    private String user_code;

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    @Expose
    @SerializedName("customer_code")
    private String customer_code;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    @Expose
    @SerializedName("customer_name")
    private String customer_name;

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    @Expose
    @SerializedName("customer_mobile")
    private String customer_mobile;

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    @Expose
    @SerializedName("customer_email")
    private String customer_email;

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    @Expose
    @SerializedName("customer_address")
    private String customer_address;

    public String getCustomer_dob() {
        return customer_dob;
    }

    public void setCustomer_dob(String customer_dob) {
        this.customer_dob = customer_dob;
    }

    @Expose
    @SerializedName("customer_dob")
    private String customer_dob;

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    @Expose
    @SerializedName("loan_type")
    private String loan_type;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Expose
    @SerializedName("state")
    private String state;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    @Expose
    @SerializedName("date_time")
    private String date_time;

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    @Expose
    @SerializedName("application_status")
    private String application_status;

    public String getCust_tc_response() {
        return cust_tc_response;
    }

    public void setCust_tc_response(String cust_tc_response) {
        this.cust_tc_response = cust_tc_response;
    }

    @Expose
    @SerializedName("cust_tc_response")
    private String cust_tc_response;

    public String getLoan_amt_approve() {
        return loan_amt_approve;
    }

    public void setLoan_amt_approve(String loan_amt_approve) {
        this.loan_amt_approve = loan_amt_approve;
    }

    @Expose
    @SerializedName("loan_amt_approve")
    private String loan_amt_approve;

    public String getEmi_amt() {
        return emi_amt;
    }

    public void setEmi_amt(String emi_amt) {
        this.emi_amt = emi_amt;
    }

    @Expose
    @SerializedName("emi_amt")
    private String emi_amt;

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    @Expose
    @SerializedName("tenure")
    private String tenure;

    public String getRate_of_interest() {
        return rate_of_interest;
    }

    public void setRate_of_interest(String rate_of_interest) {
        this.rate_of_interest = rate_of_interest;
    }

    @Expose
    @SerializedName("rate_of_interest")
    private String rate_of_interest;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Expose
    @SerializedName("comments")
    private String comments;

    public String getReason_of_rejection() {
        return reason_of_rejection;
    }

    public void setReason_of_rejection(String reason_of_rejection) {
        this.reason_of_rejection = reason_of_rejection;
    }

    @Expose
    @SerializedName("reason_of_rejection")
    private String reason_of_rejection;

}
