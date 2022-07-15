package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class top_five_POs_DATA {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     /*"id": "20",
             "po_id": "PO062222987",
             "date": "NA",
             "brand": "Hero",
             "dealer_id": "47436",
             "dealer_name": "Hero",
             "product": "Mega T 15 Delux",
             "prodt_quantity": "5",
             "prodt_price": "200",
             "total_price": "300",
             "financer": "NA",
             "status": "Waiting"
},*/
   @Expose
   @SerializedName("id")
   private String id;

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    @Expose
   @SerializedName("po_id")
   private String po_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public top_five_POs_DATA(String brand) {
        this.brand = brand;
    }

    @Expose
   @SerializedName("date")
   private String date;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Expose
   @SerializedName("brand")
   private String brand;

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    @Expose
   @SerializedName("dealer_id")
   private String dealer_id;

    @Expose
   @SerializedName("dealer_name")
   private String dealer_name;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Expose
    @SerializedName("product")
    private String product;

    public String getProdt_quantity() {
        return prodt_quantity;
    }

    public void setProdt_quantity(String prodt_quantity) {
        this.prodt_quantity = prodt_quantity;
    }

    @Expose
    @SerializedName("prodt_quantity")
    private String prodt_quantity;

    public String getProdt_price() {
        return prodt_price;
    }

    public void setProdt_price(String prodt_price) {
        this.prodt_price = prodt_price;
    }

    @Expose
    @SerializedName("prodt_price")
    private String prodt_price;

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    @Expose
    @SerializedName("total_price")
    private String total_price;

    public String getFinancer() {
        return financer;
    }

    public void setFinancer(String financer) {
        this.financer = financer;
    }

    @Expose
    @SerializedName("financer")
    private String financer;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;


}
