package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shopkeeper_ProfileData {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopkeeper_brand() {
        return shopkeeper_brand;
    }

    public void setShopkeeper_brand(String shopkeeper_brand) {
        this.shopkeeper_brand = shopkeeper_brand;
    }

    public String getShopkeeper_type() {
        return shopkeeper_type;
    }

    public void setShopkeeper_type(String shopkeeper_type) {
        this.shopkeeper_type = shopkeeper_type;
    }

    public String getShopkeeper_role() {
        return shopkeeper_role;
    }

    public void setShopkeeper_role(String shopkeeper_role) {
        this.shopkeeper_role = shopkeeper_role;
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

    public String getShopkeeper_state() {
        return shopkeeper_state;
    }

    public void setShopkeeper_state(String shopkeeper_state) {
        this.shopkeeper_state = shopkeeper_state;
    }

    public String getShopkeeper_email() {
        return shopkeeper_email;
    }

    public void setShopkeeper_email(String shopkeeper_email) {
        this.shopkeeper_email = shopkeeper_email;
    }

    public String getShopkeeper_mobile() {
        return shopkeeper_mobile;
    }

    public void setShopkeeper_mobile(String shopkeeper_mobile) {
        this.shopkeeper_mobile = shopkeeper_mobile;
    }

    public String getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(String acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getIfsc_no() {
        return ifsc_no;
    }

    public void setIfsc_no(String ifsc_no) {
        this.ifsc_no = ifsc_no;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("shopkeeper_brand")
    private String shopkeeper_brand;

    @Expose
    @SerializedName("shopkeeper_type")
    private String shopkeeper_type;
    @Expose
    @SerializedName("shopkeeper_role")
    private String shopkeeper_role;
    @Expose
    @SerializedName("shopkeeper_code")
    private String shopkeeper_code;
    @Expose
    @SerializedName("shopkeeper_name")
    private String shopkeeper_name;
    @Expose
    @SerializedName("shopkeeper_state")
    private String shopkeeper_state;

    @Expose
    @SerializedName("shopkeeper_email")
    private String shopkeeper_email;
    @Expose
    @SerializedName("shopkeeper_mobile")
    private String shopkeeper_mobile;
    @Expose
    @SerializedName("acc_holder_name")
    private String acc_holder_name;
    @Expose
    @SerializedName("acc_no")
    private String acc_no;
    @Expose
    @SerializedName("ifsc_no")
    private String ifsc_no;
    @Expose
    @SerializedName("branch_name")
    private String branch_name;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("created_at")
    private String created_at;
    @Expose
    @SerializedName("updated_at")
    private String updated_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("shop_name")
    private String shop_name;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    @Expose
    @SerializedName("shop_address")
    private String shop_address;



}
