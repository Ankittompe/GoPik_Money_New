package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet_Status {
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

    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(String wallet_type) {
        this.wallet_type = wallet_type;
    }

    @Expose
    @SerializedName("wallet_type")
    private String wallet_type;

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    @Expose
    @SerializedName("ref_no")
    private String ref_no;

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    @Expose
    @SerializedName("trans_type")
    private String trans_type;

    public String getTxn_amt() {
        return txn_amt;
    }

    public void setTxn_amt(String txn_amt) {
        this.txn_amt = txn_amt;
    }

    @Expose
    @SerializedName("txn_amt")
    private String txn_amt;

    public String getTxn_tmstmp() {
        return txn_tmstmp;
    }

    public void setTxn_tmstmp(String txn_tmstmp) {
        this.txn_tmstmp = txn_tmstmp;
    }

    @Expose
    @SerializedName("txn_tmstmp")
    private String txn_tmstmp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Expose
    @SerializedName("email")
    private String email;

    public String getRazorpay_payment_id() {
        return razorpay_payment_id;
    }

    public void setRazorpay_payment_id(String razorpay_payment_id) {
        this.razorpay_payment_id = razorpay_payment_id;
    }

    @Expose
    @SerializedName("razorpay_payment_id")
    private String razorpay_payment_id;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Expose
    @SerializedName("remarks")
    private String remarks;




}
