package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PO_PRODUCTS_PAYLOAD {
    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    @Expose
    @SerializedName("prod_name")
    private String prod_name;

    public String getProd_mrp() {
        return prod_mrp;
    }

    public void setProd_mrp(String prod_mrp) {
        this.prod_mrp = prod_mrp;
    }

    @Expose
    @SerializedName("prod_mrp")
    private String prod_mrp;

    public String getProd_qty() {
        return prod_qty;
    }

    public void setProd_qty(String prod_qty) {
        this.prod_qty = prod_qty;
    }

    @Expose
    @SerializedName("prod_qty")
    private String prod_qty;



}
