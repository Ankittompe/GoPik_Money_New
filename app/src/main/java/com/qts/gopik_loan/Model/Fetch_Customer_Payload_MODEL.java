package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fetch_Customer_Payload_MODEL {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("id")
    private String id;

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

    public String getDoc_online() {
        return doc_online;
    }

    public void setDoc_online(String doc_online) {
        this.doc_online = doc_online;
    }

    @Expose
    @SerializedName("doc_online")
    private String doc_online;

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

    public String getFin_type() {
        return fin_type;
    }

    public void setFin_type(String fin_type) {
        this.fin_type = fin_type;
    }

    @Expose
    @SerializedName("fin_type")
    private String fin_type;

    public String getCust_app_status() {
        return cust_app_status;
    }

    public void setCust_app_status(String cust_app_status) {
        this.cust_app_status = cust_app_status;
    }

    @Expose
    @SerializedName("cust_app_status")
    private String cust_app_status;

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    @Expose
    @SerializedName("mode_of_payment")
    private String mode_of_payment;

    public String getCust_sal() {
        return cust_sal;
    }

    public void setCust_sal(String cust_sal) {
        this.cust_sal = cust_sal;
    }

    @Expose
    @SerializedName("cust_sal")
    private String cust_sal;

    public String getCust_first_name() {
        return cust_first_name;
    }

    public void setCust_first_name(String cust_first_name) {
        this.cust_first_name = cust_first_name;
    }

    @Expose
    @SerializedName("cust_first_name")
    private String cust_first_name;

    public String getCust_last_name() {
        return cust_last_name;
    }

    public void setCust_last_name(String cust_last_name) {
        this.cust_last_name = cust_last_name;
    }

    @Expose
    @SerializedName("cust_last_name")
    private String cust_last_name;

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    @Expose
    @SerializedName("cust_email")
    private String cust_email;

    public String getCust_mobile() {
        return cust_mobile;
    }

    public void setCust_mobile(String cust_mobile) {
        this.cust_mobile = cust_mobile;
    }

    @Expose
    @SerializedName("cust_mobile")
    private String cust_mobile;

    public String getCust_marital() {
        return cust_marital;
    }

    public void setCust_marital(String cust_marital) {
        this.cust_marital = cust_marital;
    }

    @Expose
    @SerializedName("cust_marital")
    private String cust_marital;

    public String getCust_qual() {
        return cust_qual;
    }

    public void setCust_qual(String cust_qual) {
        this.cust_qual = cust_qual;
    }

    @Expose
    @SerializedName("cust_qual")
    private String cust_qual;

    public String getCust_accomodation_type() {
        return cust_accomodation_type;
    }

    public void setCust_accomodation_type(String cust_accomodation_type) {
        this.cust_accomodation_type = cust_accomodation_type;
    }

    @Expose
    @SerializedName("cust_accomodation_type")
    private String cust_accomodation_type;

    public String getCust_add_type_check() {
        return cust_add_type_check;
    }

    public void setCust_add_type_check(String cust_add_type_check) {
        this.cust_add_type_check = cust_add_type_check;
    }

    @Expose
    @SerializedName("cust_add_type_check")
    private String cust_add_type_check;

    public String getCust_add_time() {
        return cust_add_time;
    }

    public void setCust_add_time(String cust_add_time) {
        this.cust_add_time = cust_add_time;
    }

    @Expose
    @SerializedName("cust_add_time")
    private String cust_add_time;

    public String getCust_org_type() {
        return cust_org_type;
    }

    public void setCust_org_type(String cust_org_type) {
        this.cust_org_type = cust_org_type;
    }

    @Expose
    @SerializedName("cust_org_type")
    private String cust_org_type;

    public String getCust_off_add() {
        return cust_off_add;
    }

    public void setCust_off_add(String cust_off_add) {
        this.cust_off_add = cust_off_add;
    }

    @Expose
    @SerializedName("cust_off_add")
    private String cust_off_add;

    public String getCust_off_pin() {
        return cust_off_pin;
    }

    public void setCust_off_pin(String cust_off_pin) {
        this.cust_off_pin = cust_off_pin;
    }

    @Expose
    @SerializedName("cust_off_pin")
    private String cust_off_pin;

    public String getCust_wrk_exp() {
        return cust_wrk_exp;
    }

    public void setCust_wrk_exp(String cust_wrk_exp) {
        this.cust_wrk_exp = cust_wrk_exp;
    }

    @Expose
    @SerializedName("cust_wrk_exp")
    private String cust_wrk_exp;

    public String getCust_bstrdt() {
        return cust_bstrdt;
    }

    public void setCust_bstrdt(String cust_bstrdt) {
        this.cust_bstrdt = cust_bstrdt;
    }

    @Expose
    @SerializedName("cust_bstrdt")
    private String cust_bstrdt;

    public String getCust_bexp() {
        return cust_bexp;
    }

    public void setCust_bexp(String cust_bexp) {
        this.cust_bexp = cust_bexp;
    }

    @Expose
    @SerializedName("cust_bexp")
    private String cust_bexp;

    public String getCust_bttl_trn() {
        return cust_bttl_trn;
    }

    public void setCust_bttl_trn(String cust_bttl_trn) {
        this.cust_bttl_trn = cust_bttl_trn;
    }

    @Expose
    @SerializedName("cust_bttl_trn")
    private String cust_bttl_trn;

    public String getCust_anl_incm() {
        return cust_anl_incm;
    }

    public void setCust_anl_incm(String cust_anl_incm) {
        this.cust_anl_incm = cust_anl_incm;
    }

    @Expose
    @SerializedName("cust_anl_incm")
    private String cust_anl_incm;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Expose
    @SerializedName("timestamp")
    private String timestamp;

    public String getCust_emp_type() {
        return cust_emp_type;
    }

    public void setCust_emp_type(String cust_emp_type) {
        this.cust_emp_type = cust_emp_type;
    }

    @Expose
    @SerializedName("cust_emp_type")
    private String cust_emp_type;

    public String getCust_cmpny_name() {
        return cust_cmpny_name;
    }

    public void setCust_cmpny_name(String cust_cmpny_name) {
        this.cust_cmpny_name = cust_cmpny_name;
    }

    @Expose
    @SerializedName("cust_cmpny_name")
    private String cust_cmpny_name;

    public String getCust_gross_incm() {
        return cust_gross_incm;
    }

    public void setCust_gross_incm(String cust_gross_incm) {
        this.cust_gross_incm = cust_gross_incm;
    }

    @Expose
    @SerializedName("cust_gross_incm")
    private String cust_gross_incm;

    public String getCust_industry_type() {
        return cust_industry_type;
    }

    public void setCust_industry_type(String cust_industry_type) {
        this.cust_industry_type = cust_industry_type;
    }

    @Expose
    @SerializedName("cust_industry_type")
    private String cust_industry_type;

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public String getCurent_address() {
        return curent_address;
    }

    public void setCurent_address(String curent_address) {
        this.curent_address = curent_address;
    }

    @Expose
    @SerializedName("application_status")
    private String application_status;

    @Expose
    @SerializedName("curent_address")
    private String curent_address;

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    @Expose
    @SerializedName("permanent_address")
    private String permanent_address;

}
