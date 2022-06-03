package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account_DATA_MODEL {


    public Integer getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(Integer acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    @Expose
    @SerializedName("acc_holder_name")
    private Integer acc_holder_name;

    public Integer getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(Integer acc_no) {
        this.acc_no = acc_no;
    }

    @Expose
    @SerializedName("acc_no")
    private Integer acc_no;

    public Integer getIfsc_no() {
        return ifsc_no;
    }

    public void setIfsc_no(Integer ifsc_no) {
        this.ifsc_no = ifsc_no;
    }

    @Expose
    @SerializedName("ifsc_no")
    private Integer ifsc_no;

    public Integer getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(Integer branch_name) {
        this.branch_name = branch_name;
    }

    @Expose
    @SerializedName("branch_name")
    private Integer branch_name;


}
