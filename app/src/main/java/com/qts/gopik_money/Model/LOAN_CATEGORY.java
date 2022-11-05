package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LOAN_CATEGORY {


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Expose
    @SerializedName("category_name")
    private String category_name;

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    @Expose
    @SerializedName("subcategory_name")
    private String subcategory_name;

    public String getSubcategory_img() {
        return subcategory_img;
    }

    public void setSubcategory_img(String subcategory_img) {
        this.subcategory_img = subcategory_img;
    }

    @Expose
    @SerializedName("subcategory_img")
    private String subcategory_img;
}
