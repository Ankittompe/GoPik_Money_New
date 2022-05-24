package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PROFILE_UPLOAD_PAYLOAD_MODEL {

    public String getBroker_name() {
        return broker_name;
    }

    public void setBroker_name(String broker_name) {
        this.broker_name = broker_name;
    }

    @Expose
    @SerializedName("broker_name")
    private String broker_name;

    public String getBroker_email() {
        return broker_email;
    }

    public void setBroker_email(String broker_email) {
        this.broker_email = broker_email;
    }

    @Expose
    @SerializedName("broker_email")
    private String broker_email;

    public String getBroker_mobile() {
        return broker_mobile;
    }

    public void setBroker_mobile(String broker_mobile) {
        this.broker_mobile = broker_mobile;
    }

    @Expose
    @SerializedName("broker_mobile")
    private String broker_mobile;

    public String getBroker_state() {
        return broker_state;
    }

    public void setBroker_state(String broker_state) {
        this.broker_state = broker_state;
    }

    @Expose
    @SerializedName("broker_state")
    private String broker_state;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    @Expose
    @SerializedName("bank_name")
    private String bank_name;

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    @Expose
    @SerializedName("acc_no")
    private String acc_no;

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    @Expose
    @SerializedName("branch_name")
    private String branch_name;

    public String getBroker_address() {
        return broker_address;
    }

    public void setBroker_address(String broker_address) {
        this.broker_address = broker_address;
    }

    @Expose
    @SerializedName("broker_address")
    private String broker_address;
}
