package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getusercontest_MODEL {


    public String getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(String total_amount) {
        Total_amount = total_amount;
    }

    @Expose
    @SerializedName("Total amount")
    private String Total_amount;

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    @Expose
    @SerializedName("Points")
    private String Points;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Expose
    @SerializedName("Status")
    private String Status;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Expose
    @SerializedName("position")
    private String position;

    public String getRequire() {
        return Require;
    }

    public void setRequire(String require) {
        Require = require;
    }

    @Expose
    @SerializedName("Require points")
    private String Require;

    public String getMinimum_points() {
        return Minimum_points;
    }

    public void setMinimum_points(String minimum_points) {
        Minimum_points = minimum_points;
    }

    @Expose
    @SerializedName("Minimum points")
    private String Minimum_points;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Expose
    @SerializedName("info")
    private String info;
}
