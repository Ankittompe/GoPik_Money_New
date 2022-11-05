package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanCalculateMODEL {
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Expose
    @SerializedName("product_id")
    private String product_id;

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    @Expose
    @SerializedName("product_model")
    private String product_model;

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    @Expose
    @SerializedName("product_price")
    private String product_price;

    public String getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(String down_payment) {
        this.down_payment = down_payment;
    }

    @Expose
    @SerializedName("down_payment")
    private String down_payment;

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    @Expose
    @SerializedName("loan_amount")
    private String loan_amount;

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    @Expose
    @SerializedName("tenure")
    private String tenure;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Expose
    @SerializedName("rate")
    private String rate;

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    @Expose
    @SerializedName("emi")
    private String emi;

    public String getProcc_fees() {
        return procc_fees;
    }

    public void setProcc_fees(String procc_fees) {
        this.procc_fees = procc_fees;
    }

    @Expose
    @SerializedName("procc_fees")
    private String procc_fees;

    public String getIns_fees() {
        return ins_fees;
    }

    public void setIns_fees(String ins_fees) {
        this.ins_fees = ins_fees;
    }

    @Expose
    @SerializedName("ins_fees")
    private String ins_fees;

    public String getUp_pay() {
        return up_pay;
    }

    public void setUp_pay(String up_pay) {
        this.up_pay = up_pay;
    }

    @Expose
    @SerializedName("up_pay")
    private String up_pay;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    @Expose
    @SerializedName("total_amount")
    private String total_amount;
}
