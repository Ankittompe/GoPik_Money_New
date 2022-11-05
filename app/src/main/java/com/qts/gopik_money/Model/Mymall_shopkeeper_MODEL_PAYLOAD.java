package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mymall_shopkeeper_MODEL_PAYLOAD {
    public String getShopkeeper_code() {
        return shopkeeper_code;
    }

    public void setShopkeeper_code(String shopkeeper_code) {
        this.shopkeeper_code = shopkeeper_code;
    }

    @Expose
    @SerializedName("shopkeeper_code")
    private String shopkeeper_code;

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getInvoice_price() {
        return invoice_price;
    }

    public void setInvoice_price(String invoice_price) {
        this.invoice_price = invoice_price;
    }

    public String getInvoice_image() {
        return invoice_image;
    }

    public void setInvoice_image(String invoice_image) {
        this.invoice_image = invoice_image;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("invoice_no")
    private String invoice_no;
    @Expose
    @SerializedName("invoice_price")
    private String invoice_price;
    @Expose
    @SerializedName("invoice_image")
    private String invoice_image;
    @Expose
    @SerializedName("po_id")
    private String po_id;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("status")
    private String status;
}
