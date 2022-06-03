package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broker_bank_details_update_MODEL {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private Integer message;

    public Account_DATA_MODEL getAccount_data_model() {
        return account_data_model;
    }

    public void setAccount_data_model(Account_DATA_MODEL account_data_model) {
        this.account_data_model = account_data_model;
    }

    @Expose
    @SerializedName("payload")
    private Account_DATA_MODEL account_data_model;

}
