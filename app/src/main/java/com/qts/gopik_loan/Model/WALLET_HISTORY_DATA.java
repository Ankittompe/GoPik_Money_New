package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WALLET_HISTORY_DATA {
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

    public String getTxn_status() {
        return txn_status;
    }

    public void setTxn_status(String txn_status) {
        this.txn_status = txn_status;
    }

    @Expose
    @SerializedName("txn_status")
    private String txn_status;

    public String getTxn_tmstmp() {
        return txn_tmstmp;
    }

    public void setTxn_tmstmp(String txn_tmstmp) {
        this.txn_tmstmp = txn_tmstmp;
    }

    @Expose
    @SerializedName("txn_tmstmp")
    private String txn_tmstmp;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Expose
    @SerializedName("created_at")
    private String created_at;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Expose
    @SerializedName("updated_at")
    private String updated_at;

}
