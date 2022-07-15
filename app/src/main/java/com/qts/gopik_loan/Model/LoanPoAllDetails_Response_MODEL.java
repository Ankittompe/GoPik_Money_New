package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanPoAllDetails_Response_MODEL {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProdt_quantity() {
        return prodt_quantity;
    }

    public void setProdt_quantity(String prodt_quantity) {
        this.prodt_quantity = prodt_quantity;
    }

    public String getUpdate_quantity() {
        return update_quantity;
    }

    public void setUpdate_quantity(String update_quantity) {
        this.update_quantity = update_quantity;
    }

    public String getProdt_price() {
        return prodt_price;
    }

    public void setProdt_price(String prodt_price) {
        this.prodt_price = prodt_price;
    }

    public String getUpdate_price() {
        return update_price;
    }

    public void setUpdate_price(String update_price) {
        this.update_price = update_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUpdate_totl_prc() {
        return update_totl_prc;
    }

    public void setUpdate_totl_prc(String update_totl_prc) {
        this.update_totl_prc = update_totl_prc;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getFinancer() {
        return financer;
    }

    public void setFinancer(String financer) {
        this.financer = financer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoan_limit() {
        return loan_limit;
    }

    public void setLoan_limit(String loan_limit) {
        this.loan_limit = loan_limit;
    }

    public String getApp_amt() {
        return app_amt;
    }

    public void setApp_amt(String app_amt) {
        this.app_amt = app_amt;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReason_of_rejection() {
        return reason_of_rejection;
    }

    public void setReason_of_rejection(String reason_of_rejection) {
        this.reason_of_rejection = reason_of_rejection;
    }

    public String getInvoice_file() {
        return invoice_file;
    }

    public void setInvoice_file(String invoice_file) {
        this.invoice_file = invoice_file;
    }

    public String getDisvrsal_report() {
        return disvrsal_report;
    }

    public void setDisvrsal_report(String disvrsal_report) {
        this.disvrsal_report = disvrsal_report;
    }

    public String getOem_time() {
        return oem_time;
    }

    public void setOem_time(String oem_time) {
        this.oem_time = oem_time;
    }

    public String getDealer_action_time() {
        return dealer_action_time;
    }

    public void setDealer_action_time(String dealer_action_time) {
        this.dealer_action_time = dealer_action_time;
    }

    public String getFinancer_action_time() {
        return financer_action_time;
    }

    public void setFinancer_action_time(String financer_action_time) {
        this.financer_action_time = financer_action_time;
    }

    public String getFinancer_disbursal_time() {
        return financer_disbursal_time;
    }

    public void setFinancer_disbursal_time(String financer_disbursal_time) {
        this.financer_disbursal_time = financer_disbursal_time;
    }

    public String getDelivery_gen_time() {
        return delivery_gen_time;
    }

    public void setDelivery_gen_time(String delivery_gen_time) {
        this.delivery_gen_time = delivery_gen_time;
    }

    /* "id": 525,
                 "po_id": "PO072298613",
                 "date": "2022-07-13 12:41:41",
                 "": "Hero",
                 "": "09270",
                 "": "sai",
                 "": "MIG 24T RS",
                 "": "2",
                 "": "3",
                 "": "9100",
                 "": "10000",
                 "": "18200",
                 "": "30000",
                 "": "NA",
                 "": "NA",
                 "": "Disbursed by financer",
                 "": "120000",
                 "": "30000",
                 "": "90",
                 "roi": "9",
                 "comments": "Fine",
                 "reason_of_rejection": "NA",
                 "invoice_file": "POinvoice_doc/PO072298613/POInvoice.jpg",
                 "disvrsal_report": "public/DealerSupplyChainreport/PO072298613/DealerSupplyChainreport.jpg",
                 "oem_time": "2022-07-13 12:43:21",
                 "dealer_action_time": "2022-07-13 12:43:47",
                 "financer_action_time": "2022-07-13 15:26:25",
                 "financer_disbursal_time": "NA",
                 "delivery_gen_time": "2022-07-13 15:26:12"*/
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("po_id")
    private String po_id;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("dealer_id")
    private String dealer_id;
    @Expose
    @SerializedName("dealer_name")
    private String dealer_name;

    @Expose
    @SerializedName("product")
    private String product;
    @Expose
    @SerializedName("prodt_quantity")
    private String prodt_quantity;
    @Expose
    @SerializedName("update_quantity")
    private String update_quantity;
    @Expose
    @SerializedName("prodt_price")
    private String prodt_price;

    @Expose
    @SerializedName("update_price")
    private String update_price;
    @Expose
    @SerializedName("total_price")
    private String total_price;
    @Expose
    @SerializedName("update_totl_prc")
    private String update_totl_prc;
    @Expose
    @SerializedName("invoice_no")
    private String invoice_no;
    @Expose
    @SerializedName("financer")
    private String financer;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("loan_limit")
    private String loan_limit;
    @Expose
    @SerializedName("app_amt")
    private String app_amt;
    @Expose
    @SerializedName("tenure")
    private String tenure;
    @Expose
    @SerializedName("roi")
    private String roi;
    @Expose
    @SerializedName("comments")
    private String comments;
    @Expose
    @SerializedName("reason_of_rejection")
    private String reason_of_rejection;
    @Expose
    @SerializedName("invoice_file")
    private String invoice_file;
    @Expose
    @SerializedName("disvrsal_report")
    private String disvrsal_report;
    @Expose
    @SerializedName("oem_time")
    private String oem_time;
    @Expose
    @SerializedName("dealer_action_time")
    private String dealer_action_time;
    @Expose
    @SerializedName("financer_action_time")
    private String financer_action_time;
    @Expose
    @SerializedName("financer_disbursal_time")
    private String financer_disbursal_time;
    @Expose
    @SerializedName("delivery_gen_time")
    private String delivery_gen_time;



}
