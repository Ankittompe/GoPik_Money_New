package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class Payload {
    @SerializedName("product_price")
    String product_price;
    @SerializedName("down_payment")
    String down_payment;
    @SerializedName("tenure")
    String tenure;
    @SerializedName("vacc_hlth_kit")
    String vacc_hlth_kit;
    @SerializedName("loan_amount")
    String loan_amount;
    @SerializedName("Disbrs_amnt")
    String Disbrs_amnt;
    @SerializedName("procc_fees")
    String procc_fees;
    @SerializedName("gopik_prtct_ins")
    String gopik_prtct_ins;
    @SerializedName("up_pay")
    String up_pay;
    @SerializedName("emi")
    String emi;
    @SerializedName("rate_of_intest")
    String rate_of_intest;

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(String down_payment) {
        this.down_payment = down_payment;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getVacc_hlth_kit() {
        return vacc_hlth_kit;
    }

    public void setVacc_hlth_kit(String vacc_hlth_kit) {
        this.vacc_hlth_kit = vacc_hlth_kit;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getDisbrs_amnt() {
        return Disbrs_amnt;
    }

    public void setDisbrs_amnt(String disbrs_amnt) {
        Disbrs_amnt = disbrs_amnt;
    }

    public String getProcc_fees() {
        return procc_fees;
    }

    public void setProcc_fees(String procc_fees) {
        this.procc_fees = procc_fees;
    }

    public String getGopik_prtct_ins() {
        return gopik_prtct_ins;
    }

    public void setGopik_prtct_ins(String gopik_prtct_ins) {
        this.gopik_prtct_ins = gopik_prtct_ins;
    }

    public String getUp_pay() {
        return up_pay;
    }

    public void setUp_pay(String up_pay) {
        this.up_pay = up_pay;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getRate_of_intest() {
        return rate_of_intest;
    }

    public void setRate_of_intest(String rate_of_intest) {
        this.rate_of_intest = rate_of_intest;
    }
}