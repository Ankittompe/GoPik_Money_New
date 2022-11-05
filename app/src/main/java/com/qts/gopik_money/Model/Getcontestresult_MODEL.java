package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Getcontestresult_MODEL {


    @Expose
    @SerializedName("payload")
    private ArrayList<Getconterst_result_MODEL> payload;



    public ArrayList<Getconterst_result_MODEL> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<Getconterst_result_MODEL> payload) {
        this.payload = payload;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    @SerializedName("message")
    private String message;

    public String getFirst_Prize() {
        return first_Prize;
    }

    public void setFirst_Prize(String first_Prize) {
        this.first_Prize = first_Prize;
    }

    @Expose
    @SerializedName("1st Prize")
    private String first_Prize;

    public String getSecond_Prize() {
        return Second_Prize;
    }

    public void setSecond_Prize(String second_Prize) {
        Second_Prize = second_Prize;
    }

    @Expose
    @SerializedName("2nd Prize")
    private String Second_Prize;

    public String getThird_Prize() {
        return Third_Prize;
    }

    public void setThird_Prize(String third_Prize) {
        Third_Prize = third_Prize;
    }

    @Expose
    @SerializedName("3rd Prize")
    private String Third_Prize;
}