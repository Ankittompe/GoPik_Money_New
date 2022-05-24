package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fetch_Application_MODEL {

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

    @Expose
    @SerializedName("ins_amt")
    private String ins_amt;

    @Expose
    @SerializedName("up_pay")
    private String up_pay;

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

    @Expose
    @SerializedName("type")
    private String type;

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

    @Expose
    @SerializedName("mode_of_payment")
    private String mode_of_payment;

    @Expose
    @SerializedName("epic_no")
    private String epic_no;




    @Expose
    @SerializedName("vid_name")
    private String vid_name;

    @Expose
    @SerializedName("vid_name_hi")
    private String vid_name_hi;

    @Expose
    @SerializedName("vid_rln_name")
    private String vid_rln_name;

    @Expose
    @SerializedName("vid_rln_name_hi")
    private String vid_rln_name_hi;

    @Expose
    @SerializedName("vid_rln_type")
    private String vid_rln_type;

    @Expose
    @SerializedName("vid_gender")
    private String vid_gender;

    @Expose
    @SerializedName("vid_age")
    private String vid_age;

    @Expose
    @SerializedName("vid_dob")
    private String vid_dob;

    @Expose
    @SerializedName("vid_house_no")
    private String vid_house_no;


    @Expose
    @SerializedName("vid_distt")
    private String vid_distt;

    @Expose
    @SerializedName("vid_state")
    private String vid_state;

    @Expose
    @SerializedName("vid_status")
    private String vid_status;

    @Expose
    @SerializedName("vid_timestamp")
    private String vid_timestamp;

    @Expose
    @SerializedName("pan_no")
    private String pan_no;
    @Expose
    @SerializedName("pan_name")
    private String pan_name;

    @Expose
    @SerializedName("pan_status")
    private String pan_status;

    @Expose
    @SerializedName("pan_timestamp")
    private String pan_timestamp;




    @Expose
    @SerializedName("cust_vid_front_path")
    private String cust_vid_front_path;

    @Expose
    @SerializedName("cust_vid_back_path")
    private String cust_vid_back_path;

    @Expose
    @SerializedName("cust_pan_path")
    private String cust_pan_path;

    @Expose
    @SerializedName("cust_margin_path")
    private String cust_margin_path;

    @Expose
    @SerializedName("cust_animal_front_path")
    private String cust_animal_front_path;

    @Expose
    @SerializedName("cust_animal_side_path")
    private String cust_animal_side_path;

    @Expose
    @SerializedName("cust_photo_path")
    private String cust_photo_path;

    @Expose
    @SerializedName("hubble_img_upload_status")
    private String hubble_img_upload_status;

    @Expose
    @SerializedName("mobile_number")
    private String mobile_number;





    @Expose
    @SerializedName("cast")
    private String cast;

    @Expose
    @SerializedName("aadhar_number")
    private String aadhar_number;

    @Expose
    @SerializedName("mothers_name")
    private String mothers_name;

    @Expose
    @SerializedName("fathers_name")
    private String fathers_name;

    @Expose
    @SerializedName("bank_account")
    private String bank_account;
    @Expose
    @SerializedName("bank_name")
    private String bank_name;

    @Expose
    @SerializedName("ifsc")
    private String ifsc;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Expose
    @SerializedName("updated_at")
    private String updated_at;



    @Expose
    @SerializedName("status")
    private String status;

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

    @Expose
    @SerializedName("cust_email")
    private String cust_email;

    @Expose
    @SerializedName("cust_mobile")
    private String cust_mobile;

    @Expose
    @SerializedName("cust_marital")
    private String cust_marital;

    @Expose
    @SerializedName("cust_qual")
    private String cust_qual;

    @Expose
    @SerializedName("cust_accomodation_type")
    private String cust_accomodation_type;


    @Expose
    @SerializedName("cust_add_type_check")
    private String cust_add_type_check;

    @Expose
    @SerializedName("cust_add_time")
    private String cust_add_time;

    @Expose
    @SerializedName("curent_address")
    private String curent_address;

    @Expose
    @SerializedName("permanent_address")
    private String permanent_address;

    @Expose
    @SerializedName("cust_org_type")
    private String cust_org_type;

    @Expose
    @SerializedName("cust_off_add")
    private String cust_off_add;

    @Expose
    @SerializedName("cust_off_pin")
    private String cust_off_pin;

    @Expose
    @SerializedName("cust_wrk_exp")
    private String cust_wrk_exp;

    @Expose
    @SerializedName("cust_bstrdt")
    private String cust_bstrdt;






    @Expose
    @SerializedName("cust_bexp")
    private String cust_bexp;


    @Expose
    @SerializedName("cust_bttl_trn")
    private String cust_bttl_trn;

    @Expose
    @SerializedName("cust_anl_incm")
    private String cust_anl_incm;

    @Expose
    @SerializedName("timestamp")
    private String timestamp;

    @Expose
    @SerializedName("cust_emp_type")
    private String cust_emp_type;

    @Expose
    @SerializedName("cust_cmpny_name")
    private String cust_cmpny_name;

    @Expose
    @SerializedName("cust_gross_incm")
    private String cust_gross_incm;

    @Expose
    @SerializedName("cust_industry_type")
    private String cust_industry_type;

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    @Expose
    @SerializedName("application_status")
    private String application_status;
}
