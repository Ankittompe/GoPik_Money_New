package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculationDATA_MODEL {
    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    @Expose
    @SerializedName("cust_code")
    private String cust_code;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    @Expose
    @SerializedName("user_code")
    private String user_code;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Expose
    @SerializedName("model")
    private String model;

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    @Expose
    @SerializedName("productprice")
    private String productprice;

    public String getReq_tenure() {
        return req_tenure;
    }

    public void setReq_tenure(String req_tenure) {
        this.req_tenure = req_tenure;
    }

    @Expose
    @SerializedName("req_tenure")
    private String req_tenure;

    public String getLoan_amt_app() {
        return loan_amt_app;
    }

    public void setLoan_amt_app(String loan_amt_app) {
        this.loan_amt_app = loan_amt_app;
    }

    @Expose
    @SerializedName("loan_amt_app")
    private String loan_amt_app;

    public String getDown_pay() {
        return down_pay;
    }

    public void setDown_pay(String down_pay) {
        this.down_pay = down_pay;
    }

    @Expose
    @SerializedName("down_pay")
    private String down_pay;

    public String getProc_fees() {
        return proc_fees;
    }

    public void setProc_fees(String proc_fees) {
        this.proc_fees = proc_fees;
    }

    @Expose
    @SerializedName("proc_fees")
    private String proc_fees;

    public String getIns_amt() {
        return ins_amt;
    }

    public void setIns_amt(String ins_amt) {
        this.ins_amt = ins_amt;
    }

    @Expose
    @SerializedName("ins_amt")
    private String ins_amt;

    public String getUp_pay() {
        return up_pay;
    }

    public void setUp_pay(String up_pay) {
        this.up_pay = up_pay;
    }

    @Expose
    @SerializedName("up_pay")
    private String up_pay;

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    @Expose
    @SerializedName("emi")
    private String emi;

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    @Expose
    @SerializedName("subsidy")
    private String subsidy;


    public String getApp_start_tmstmp() {
        return app_start_tmstmp;
    }

    public void setApp_start_tmstmp(String app_start_tmstmp) {
        this.app_start_tmstmp = app_start_tmstmp;
    }

    @Expose
    @SerializedName("app_start_tmstmp")
    private String app_start_tmstmp;

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    @Expose
    @SerializedName("app_status")
    private String app_status;

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    @Expose
    @SerializedName("api_name")
    private String api_name;

    public String getApi_id() {
        return api_id;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    @Expose
    @SerializedName("api_id")
    private String api_id;

    public String getEff_rate() {
        return eff_rate;
    }

    public void setEff_rate(String eff_rate) {
        this.eff_rate = eff_rate;
    }

    @Expose
    @SerializedName("eff_rate")
    private String eff_rate;

    public String getApp_end_tmstmp() {
        return app_end_tmstmp;
    }

    public void setApp_end_tmstmp(String app_end_tmstmp) {
        this.app_end_tmstmp = app_end_tmstmp;
    }

    @Expose
    @SerializedName("app_end_tmstmp")
    private String app_end_tmstmp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Expose
    @SerializedName("type")
    private String type;

    public String getFin_type() {
        return fin_type;
    }

    public void setFin_type(String fin_type) {
        this.fin_type = fin_type;
    }

    @Expose
    @SerializedName("fin_type")
    private String fin_type;

    public String getDoc_online() {
        return doc_online;
    }

    public void setDoc_online(String doc_online) {
        this.doc_online = doc_online;
    }

    @Expose
    @SerializedName("doc_online")
    private String doc_online;

    public String getGopik_convenience_fees() {
        return gopik_convenience_fees;
    }

    public void setGopik_convenience_fees(String gopik_convenience_fees) {
        this.gopik_convenience_fees = gopik_convenience_fees;
    }

    @Expose
    @SerializedName("gopik_convenience_fees")
    private String gopik_convenience_fees;


}
