package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FORM_DATA_PAYLOAD {

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

    public String getCustomer_gender() {
        return customer_gender;
    }

    public void setCustomer_gender(String customer_gender) {
        this.customer_gender = customer_gender;
    }

    @Expose
    @SerializedName("customer_gender")
    private String customer_gender;

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Expose
    @SerializedName("pin")
    private String pin;

    public String getLoan_cat() {
        return loan_cat;
    }

    public void setLoan_cat(String loan_cat) {
        this.loan_cat = loan_cat;
    }

    @Expose
    @SerializedName("loan_cat")
    private String loan_cat;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Expose
    @SerializedName("city")
    private String city;

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




}
