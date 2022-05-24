package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getconterst_result_MODEL {

    @Expose
    @SerializedName("userid")
    private String userid;
    @Expose
    @SerializedName("username")
    private String username;

    public Integer getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        Total_amount = total_amount;
    }

    @Expose
    @SerializedName("Totalamt")
    private Integer Total_amount;

    public Integer getPoints() {
        return Points;
    }

    public void setPoints(Integer points) {
        Points = points;
    }

    @Expose
    @SerializedName("Points")
    private Integer Points;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Expose
    @SerializedName("state")
    private String state;


}
