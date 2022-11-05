package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeper_bank_details_update_MODEL {

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

    public Shopkeeper_Account_DATA_MODEL getAccount_data_model() {
        return account_data_model;
    }

    public void setAccount_data_model(Shopkeeper_Account_DATA_MODEL account_data_model) {
        this.account_data_model = account_data_model;
    }

    @Expose
    @SerializedName("payload")
    private Shopkeeper_Account_DATA_MODEL account_data_model;
}
