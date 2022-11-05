package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_PASSPORT_MODEL {
    public Name_MODEL getName() {
        return name;
    }

    public void setName(Name_MODEL name) {
        this.name = name;
    }

    @Expose
    @SerializedName("name")
    private Name_MODEL name;

    public PassportNumber_MODEL getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(PassportNumber_MODEL passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Expose
    @SerializedName("passportNumber")
    private PassportNumber_MODEL passportNumber;

    public DateOfIssue_MODEL getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(DateOfIssue_MODEL dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Expose
    @SerializedName("dateOfIssue")
    private DateOfIssue_MODEL dateOfIssue;

    public String getTypeOfApplication() {
        return typeOfApplication;
    }

    public void setTypeOfApplication(String typeOfApplication) {
        this.typeOfApplication = typeOfApplication;
    }

    @Expose
    @SerializedName("typeOfApplication")
    private String typeOfApplication;

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    @Expose
    @SerializedName("applicationDate")
    private String applicationDate;
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("status-code")
    private String statusCode ;
}
