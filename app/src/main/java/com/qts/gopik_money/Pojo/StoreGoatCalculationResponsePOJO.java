package com.qts.gopik_money.Pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StoreGoatCalculationResponsePOJO {

    @SerializedName("emi")
    private String emi;

    @SerializedName("disbrsl_amt")
    private String disbrslAmt;

    @SerializedName("dlr_code")
    private String dlrCode;

    @SerializedName("loan_amount")
    private String loanAmount;

    @SerializedName("down_payment")
    private String downPayment;

    @SerializedName("rate")
    private String rate;

    @SerializedName("protct_insurnce")
    private String protctInsurnce;

    @SerializedName("gross_price")
    private String grossPrice;

    @SerializedName("up_pay")
    private String upPay;

    @SerializedName("product_details")
    private List<ProductDetailsItem> productDetails;

    @SerializedName("hlth_kit")
    private String hlthKit;

    @SerializedName("brand")
    private String brand;

    @SerializedName("tenure")
    private String tenure;

    @SerializedName("procc_fees")
    private String proccFees;

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getEmi() {
        return emi;
    }

    public void setDisbrslAmt(String disbrslAmt) {
        this.disbrslAmt = disbrslAmt;
    }

    public String getDisbrslAmt() {
        return disbrslAmt;
    }

    public void setDlrCode(String dlrCode) {
        this.dlrCode = dlrCode;
    }

    public String getDlrCode() {
        return dlrCode;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setProtctInsurnce(String protctInsurnce) {
        this.protctInsurnce = protctInsurnce;
    }

    public String getProtctInsurnce() {
        return protctInsurnce;
    }

    public void setGrossPrice(String grossPrice) {
        this.grossPrice = grossPrice;
    }

    public String getGrossPrice() {
        return grossPrice;
    }

    public void setUpPay(String upPay) {
        this.upPay = upPay;
    }

    public String getUpPay() {
        return upPay;
    }

    public void setProductDetails(List<ProductDetailsItem> productDetails) {
        this.productDetails = productDetails;
    }

    public List<ProductDetailsItem> getProductDetails() {
        return productDetails;
    }

    public void setHlthKit(String hlthKit) {
        this.hlthKit = hlthKit;
    }

    public String getHlthKit() {
        return hlthKit;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getTenure() {
        return tenure;
    }

    public void setProccFees(String proccFees) {
        this.proccFees = proccFees;
    }

    public String getProccFees() {
        return proccFees;
    }

    public StoreGoatCalculationResponsePOJO(String emi, String disbrslAmt, String dlrCode, String loanAmount, String downPayment, String rate, String protctInsurnce, String grossPrice, String upPay, List<ProductDetailsItem> productDetails, String hlthKit, String brand, String tenure, String proccFees) {
        this.emi = emi;
        this.disbrslAmt = disbrslAmt;
        this.dlrCode = dlrCode;
        this.loanAmount = loanAmount;
        this.downPayment = downPayment;
        this.rate = rate;
        this.protctInsurnce = protctInsurnce;
        this.grossPrice = grossPrice;
        this.upPay = upPay;
        this.productDetails = productDetails;
        this.hlthKit = hlthKit;
        this.brand = brand;
        this.tenure = tenure;
        this.proccFees = proccFees;
    }
}