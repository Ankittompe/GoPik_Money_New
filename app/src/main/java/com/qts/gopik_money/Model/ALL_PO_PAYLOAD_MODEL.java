package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ALL_PO_PAYLOAD_MODEL {
    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    @Expose
    @SerializedName("invoice_no")
    private String invoice_no;

    public String getLoan_no() {
        return loan_no;
    }

    public void setLoan_no(String loan_no) {
        this.loan_no = loan_no;
    }

    @Expose
    @SerializedName("loan_no")
    private String loan_no;
    @Expose
    @SerializedName("id")
    private String id;
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
    @SerializedName("tenure")
    private String tenure;

    @Expose
    @SerializedName("roi")
    private String roi;

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

    public String getInvoice_file() {
        return invoice_file;
    }

    public void setInvoice_file(String invoice_file) {
        this.invoice_file = invoice_file;
    }

    @Expose
    @SerializedName("invoice_file")
    private String invoice_file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    @SerializedName("financer")
    private String financer;

    @Expose
    @SerializedName("status")
    private String status;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Expose
    @SerializedName("type")
    private Integer type;



}
