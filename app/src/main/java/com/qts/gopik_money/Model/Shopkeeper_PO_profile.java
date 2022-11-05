package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeper_PO_profile {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("shopkeeper_code")
    private String shopkeeper_code;

    @Expose
    @SerializedName("shopkeeper_name")
    private String shopkeeper_name;

    @Expose
    @SerializedName("po_id")
    private String po_id;

    @Expose
    @SerializedName("date")
    private String date;

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
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("gopik_timestamp")
    private String gopik_timestamp;

    @Expose
    @SerializedName("shopkeeper_timestamp")
    private String shopkeeper_timestamp;

    @Expose
    @SerializedName("created_at")
    private String created_at;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getShopkeeper_code() {
      return shopkeeper_code;
   }

   public void setShopkeeper_code(String shopkeeper_code) {
      this.shopkeeper_code = shopkeeper_code;
   }

   public String getShopkeeper_name() {
      return shopkeeper_name;
   }

   public void setShopkeeper_name(String shopkeeper_name) {
      this.shopkeeper_name = shopkeeper_name;
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

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getGopik_timestamp() {
      return gopik_timestamp;
   }

   public void setGopik_timestamp(String gopik_timestamp) {
      this.gopik_timestamp = gopik_timestamp;
   }

   public String getShopkeeper_timestamp() {
      return shopkeeper_timestamp;
   }

   public void setShopkeeper_timestamp(String shopkeeper_timestamp) {
      this.shopkeeper_timestamp = shopkeeper_timestamp;
   }

   public String getCreated_at() {
      return created_at;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }

   public String getUpdated_at() {
      return updated_at;
   }

   public void setUpdated_at(String updated_at) {
      this.updated_at = updated_at;
   }

   @Expose
    @SerializedName("updated_at")
    private String updated_at;



}
