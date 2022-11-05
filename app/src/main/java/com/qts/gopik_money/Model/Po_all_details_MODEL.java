package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Po_all_details_MODEL {
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("message")
    private String message;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Expose
    @SerializedName("image")
    private String image;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<ALL_PO_PAYLOAD_MODEL> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<ALL_PO_PAYLOAD_MODEL> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<ALL_PO_PAYLOAD_MODEL> payload;

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

    public String getReason_of_rejection() {
        return reason_of_rejection;
    }

    public void setReason_of_rejection(String reason_of_rejection) {
        this.reason_of_rejection = reason_of_rejection;
    }

    @Expose
    @SerializedName("tenure")
    private String tenure;

    @Expose
    @SerializedName("roi")
    private String roi;

    @Expose
    @SerializedName("reason_of_rejection")
    private String reason_of_rejection;
}
