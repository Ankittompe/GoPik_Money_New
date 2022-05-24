package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDataModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("id")
    private String id;


    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    @Expose
    @SerializedName("cat_name")
    private String cat_name;

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    @Expose
    @SerializedName("cat_type")
    private String cat_type;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Expose
    @SerializedName("count")
    private String count;


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Expose
    @SerializedName("img_url")
    private String img_url;


    public String getCat_rol() {
        return cat_rol;
    }

    public void setCat_rol(String cat_rol) {
        this.cat_rol = cat_rol;
    }

    @Expose
    @SerializedName("cat_rol")
    private String cat_rol;

    public String getCat_code() {
        return cat_code;
    }

    public void setCat_code(String cat_code) {
        this.cat_code = cat_code;
    }

    @Expose
    @SerializedName("cat_code")
    private String cat_code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Expose
    @SerializedName("timestamp")
    private String timestamp;
}
