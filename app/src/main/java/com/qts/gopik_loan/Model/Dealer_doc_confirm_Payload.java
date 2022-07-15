package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer_doc_confirm_Payload {
   /* "id": 204,
            "name": "Aarya Enterprises",
            "user_code": "25638",
            "user_type": "Dealer",
            "user_brand": "VST",
            "role": "DLR",
            "email": "aarya_enterprises@yahoo.in",
            "mobile": "9765610862",
            "address": "HOUSE NO 431/4,5 DALI PLAZA HSG SOCIETY, ALIBAG PEN ROAD, PIMPALBHAT VESHVI, RAIGAD, MAHARASHTRA",
            "userstate": "NA",
            "qrstatus": "0",
            "dealer_doc": 1,
            "pinfirst": "402201",
            "pinscd": null,
            "pintrd": null,
            "pinfrth": null,
            "pinfth": null,
            "otp": "NA",
            "password": "NA",
            "created_at": "0000-00-00 00:00:00.000000",
            "updated_at": null,
            "token": "NA",
            "bank_name": "NA",
            "acc_holder": "NA",
            "acc_no": "NA",
            "ifsc": "NA",
            "branch": "NA",
            "cncl_chk_img": "NA",
            "pan_no": "NA",
            "gst_no": "NA",
            "pan_status": "NA",
            "bankpass_status": "NA",
            "status": "Active"*/

    private  Integer id;
    private  String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_brand() {
        return user_brand;
    }

    public void setUser_brand(String user_brand) {
        this.user_brand = user_brand;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }

    public String getQrstatus() {
        return qrstatus;
    }

    public void setQrstatus(String qrstatus) {
        this.qrstatus = qrstatus;
    }

    public Integer getDealer_doc() {
        return dealer_doc;
    }

    public void setDealer_doc(Integer dealer_doc) {
        this.dealer_doc = dealer_doc;
    }

    public String getPinfirst() {
        return pinfirst;
    }

    public void setPinfirst(String pinfirst) {
        this.pinfirst = pinfirst;
    }

    public String getPinscd() {
        return pinscd;
    }

    public void setPinscd(String pinscd) {
        this.pinscd = pinscd;
    }

    public String getPintrd() {
        return pintrd;
    }

    public void setPintrd(String pintrd) {
        this.pintrd = pintrd;
    }

    public String getPinfrth() {
        return pinfrth;
    }

    public void setPinfrth(String pinfrth) {
        this.pinfrth = pinfrth;
    }

    public String getPinfth() {
        return pinfth;
    }

    public void setPinfth(String pinfth) {
        this.pinfth = pinfth;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAcc_holder() {
        return acc_holder;
    }

    public void setAcc_holder(String acc_holder) {
        this.acc_holder = acc_holder;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCncl_chk_img() {
        return cncl_chk_img;
    }

    public void setCncl_chk_img(String cncl_chk_img) {
        this.cncl_chk_img = cncl_chk_img;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getPan_status() {
        return pan_status;
    }

    public void setPan_status(String pan_status) {
        this.pan_status = pan_status;
    }

    public String getBankpass_status() {
        return bankpass_status;
    }

    public void setBankpass_status(String bankpass_status) {
        this.bankpass_status = bankpass_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Expose
    @SerializedName("user_code")
    private  String user_code;
    @Expose
    @SerializedName("user_type")
    private  String user_type;
    @Expose
    @SerializedName("user_brand")
    private  String user_brand;
    @Expose
    @SerializedName("role")
    private  String role;
    @Expose
    @SerializedName("email")
    private  String email;
    @Expose
    @SerializedName("mobile")
    private  String mobile;
    @Expose
    @SerializedName("address")
    private  String address;
    @Expose
    @SerializedName("userstate")

    private  String userstate;
    @Expose
    @SerializedName("qrstatus")
    private  String qrstatus;
    @Expose
    @SerializedName("dealer_doc")
    private  Integer dealer_doc;
    @Expose
    @SerializedName("pinfirst")

    private  String pinfirst;
    @Expose
    @SerializedName("pinscd")
    private  String pinscd;
    @Expose
    @SerializedName("pintrd")
    private  String pintrd;
    @Expose
    @SerializedName("pinfrth")
    private  String pinfrth;
    @Expose
    @SerializedName("pinfth")
    private  String pinfth;
    @Expose
    @SerializedName("otp")
    private  String otp;
    @Expose
    @SerializedName("password")
    private  String password;
    @Expose
    @SerializedName("created_at")
    private  String created_at;
    @Expose
    @SerializedName("updated_at")
    private  String updated_at;
    @Expose
    @SerializedName("token")
    private  String token;
    @Expose
    @SerializedName("bank_name")
    private  String bank_name;
    @Expose
    @SerializedName("acc_holder")
    private  String acc_holder;
    @Expose
    @SerializedName("acc_no")
    private  String acc_no;
    @Expose
    @SerializedName("ifsc")
    private  String ifsc;
    @Expose
    @SerializedName("branch")
    private  String branch;
    @Expose
    @SerializedName("cncl_chk_img")
    private  String cncl_chk_img;
    @Expose
    @SerializedName("pan_no")
    private  String pan_no;
    @Expose
    @SerializedName("gst_no")
    private  String gst_no;
    @Expose
    @SerializedName("pan_status")
    private  String pan_status;
    @Expose
    @SerializedName("bankpass_status")
    private  String bankpass_status;
    @Expose
    @SerializedName("status")
    private  String status;



}
