package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateOfIssue_MODEL {

    public String getDispatchedOnFromSource() {
        return dispatchedOnFromSource;
    }

    public void setDispatchedOnFromSource(String dispatchedOnFromSource) {
        this.dispatchedOnFromSource = dispatchedOnFromSource;
    }

    @Expose
    @SerializedName("dispatchedOnFromSource")
    private String dispatchedOnFromSource ;

    public String getDateOfIssueMatch() {
        return dateOfIssueMatch;
    }

    public void setDateOfIssueMatch(String dateOfIssueMatch) {
        this.dateOfIssueMatch = dateOfIssueMatch;
    }

    @Expose
    @SerializedName("dateOfIssueMatch")
    private String dateOfIssueMatch ;

}
