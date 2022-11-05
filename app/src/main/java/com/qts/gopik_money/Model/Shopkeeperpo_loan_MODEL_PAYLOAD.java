package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeperpo_loan_MODEL_PAYLOAD {

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getDisburse_amount() {
        return disburse_amount;
    }

    public void setDisburse_amount(String disburse_amount) {
        this.disburse_amount = disburse_amount;
    }

    public String getDate_of_close() {
        return date_of_close;
    }

    public void setDate_of_close(String date_of_close) {
        this.date_of_close = date_of_close;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getDisburse_date() {
        return disburse_date;
    }

    public void setDisburse_date(String disburse_date) {
        this.disburse_date = disburse_date;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getRate_of_interest() {
        return rate_of_interest;
    }

    public void setRate_of_interest(String rate_of_interest) {
        this.rate_of_interest = rate_of_interest;
    }

    @Expose
    @SerializedName("po_id")
    private String po_id;

    @Expose
    @SerializedName("disburse_amount")
    private String disburse_amount;

    @Expose
    @SerializedName("date_of_close")
    private String date_of_close;

    @Expose
    @SerializedName("loan_id")
    private String loan_id;

    @Expose
    @SerializedName("disburse_date")
    private String disburse_date;

    @Expose
    @SerializedName("tenure")
    private String tenure;

    @Expose
    @SerializedName("rate_of_interest")
    private String rate_of_interest;

}
