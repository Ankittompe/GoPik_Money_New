package com.qts.gopik_money.Pojo;

public class Hero_Loan_Calculation_Data_POJO {

    private String user_code;
    private String type;
    private String prod_brand;
    private String product_model;
    private String product_price;
    private String down_payment;
    private String tenure;
    private String loan_amount;
    private String rate;
    private String emi;
    private String procc_fees;
    private String ins_fees;
    private String up_pay;
    private String total_amount;

    public Hero_Loan_Calculation_Data_POJO(String user_code, String type, String prod_brand, String product_model, String product_price, String down_payment, String tenure, String loan_amount, String rate, String emi, String procc_fees, String ins_fees, String up_pay, String total_amount) {
        this.user_code = user_code;
        this.type = type;
        this.prod_brand = prod_brand;
        this.product_model = product_model;
        this.product_price = product_price;
        this.down_payment = down_payment;
        this.tenure = tenure;
        this.loan_amount = loan_amount;
        this.rate = rate;
        this.emi = emi;
        this.procc_fees = procc_fees;
        this.ins_fees = ins_fees;
        this.up_pay = up_pay;
        this.total_amount = total_amount;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

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

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getProcc_fees() {
        return procc_fees;
    }

    public void setProcc_fees(String procc_fees) {
        this.procc_fees = procc_fees;
    }

    public String getIns_fees() {
        return ins_fees;
    }

    public void setIns_fees(String ins_fees) {
        this.ins_fees = ins_fees;
    }

    public String getUp_pay() {
        return up_pay;
    }

    public void setUp_pay(String up_pay) {
        this.up_pay = up_pay;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
