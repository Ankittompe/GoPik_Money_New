package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeper_Account_DATA_MODEL {

    public String getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(String acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    @Expose
    @SerializedName("acc_holder_name")
    private String acc_holder_name;

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    @Expose
    @SerializedName("acc_no")
    private String acc_no;

    public String getIfsc_no() {
        return ifsc_no;
    }

    public void setIfsc_no(String ifsc_no) {
        this.ifsc_no = ifsc_no;
    }

    @Expose
    @SerializedName("ifsc_no")
    private String ifsc_no;

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    @Expose
    @SerializedName("branch_name")
    private String branch_name;


}
