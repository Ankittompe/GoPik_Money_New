package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductLIstDataModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("id")
    private String id;

    public String getProd_type() {
        return prod_type;
    }

    public void setProd_type(String prod_type) {
        this.prod_type = prod_type;
    }

    @Expose
    @SerializedName("prod_type")
    private String prod_type;

    public String getProd_cat() {
        return prod_cat;
    }

    public void setProd_cat(String prod_cat) {
        this.prod_cat = prod_cat;
    }

    @Expose
    @SerializedName("prod_cat")
    private String prod_cat;

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    @Expose
    @SerializedName("prod_name")
    private String prod_name;

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    @Expose
    @SerializedName("prod_desc")
    private String prod_desc;

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    @Expose
    @SerializedName("prod_brand")
    private String prod_brand;

    public String getProd_qty() {
        return prod_qty;
    }

    public void setProd_qty(String prod_qty) {
        this.prod_qty = prod_qty;
    }

    @Expose
    @SerializedName("prod_qty")
    private String prod_qty;

    public String getProd_avblty() {
        return prod_avblty;
    }

    public void setProd_avblty(String prod_avblty) {
        this.prod_avblty = prod_avblty;
    }

    @Expose
    @SerializedName("prod_avblty")
    private String prod_avblty;

    public String getProd_mrp() {
        return prod_mrp;
    }

    public void setProd_mrp(String prod_mrp) {
        this.prod_mrp = prod_mrp;
    }

    @Expose
    @SerializedName("prod_mrp")
    private String prod_mrp;

    public String getProd_img_url() {
        return prod_img_url;
    }

    public void setProd_img_url(String prod_img_url) {
        this.prod_img_url = prod_img_url;
    }

    @Expose
    @SerializedName("prod_img_url")
    private String prod_img_url;

    public String getProd_status() {
        return prod_status;
    }

    public void setProd_status(String prod_status) {
        this.prod_status = prod_status;
    }

    @Expose
    @SerializedName("prod_status")
    private String prod_status;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Expose
    @SerializedName("timestamp")
    private String timestamp;

    public String getLink_prod_supp() {
        return link_prod_supp;
    }

    public void setLink_prod_supp(String link_prod_supp) {
        this.link_prod_supp = link_prod_supp;
    }

    @Expose
    @SerializedName("link_prod_supp")
    private String link_prod_supp;

    public String getProd_del_type() {
        return prod_del_type;
    }

    public void setProd_del_type(String prod_del_type) {
        this.prod_del_type = prod_del_type;
    }

    @Expose
    @SerializedName("prod_del_type")
    private String prod_del_type;

    public String getProd_code() {
        return prod_code;
    }

    public void setProd_code(String prod_code) {
        this.prod_code = prod_code;
    }

    @Expose
    @SerializedName("prod_code")
    private String prod_code;

    public String getProd_payment_accept() {
        return prod_payment_accept;
    }

    public void setProd_payment_accept(String prod_payment_accept) {
        this.prod_payment_accept = prod_payment_accept;
    }

    @Expose
    @SerializedName("prod_payment_accept")
    private String prod_payment_accept;

    public String getFinance_type() {
        return finance_type;
    }

    public void setFinance_type(String finance_type) {
        this.finance_type = finance_type;
    }

    @Expose
    @SerializedName("finance_type")
    private String finance_type;

}
