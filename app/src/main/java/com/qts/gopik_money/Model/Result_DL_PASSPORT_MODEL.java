package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_DL_PASSPORT_MODEL {

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @Expose
    @SerializedName("issueDate")
    private String issueDate ;

    public String getFatherhusband() {
        return fatherhusband;
    }

    public void setFatherhusband(String fatherhusband) {
        this.fatherhusband = fatherhusband;
    }

    @Expose
    @SerializedName("father/husband")
    private String fatherhusband ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Expose
    @SerializedName("name")
    private String name ;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Expose
    @SerializedName("bloodGroup")
    private String bloodGroup ;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Expose
    @SerializedName("dob")
    private String dob ;

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    @Expose
    @SerializedName("dlNumber")
    private String dlNumber ;

}
