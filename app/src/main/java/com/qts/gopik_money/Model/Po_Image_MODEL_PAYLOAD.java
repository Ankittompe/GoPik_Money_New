package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Po_Image_MODEL_PAYLOAD {

    @Expose
    @SerializedName("usercode")
    private String usercode;
    @Expose
    @SerializedName("Po_image")
    private String Po_image;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("po_code")
    private String po_code;
    @Expose
    @SerializedName("status")
    private String status;

    public String getUsercode() {

        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPo_image() {
        return Po_image;
    }

    public void setPo_image(String po_image) {
        Po_image = po_image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPo_code() {
        return po_code;
    }

    public void setPo_code(String po_code) {
        this.po_code = po_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Expose
    @SerializedName("date")
    private String date;
}
