package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AADHAR_CONSENT_CLIENTDATA_MODEL {
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @Expose
    @SerializedName("caseId")
    private String caseId;

}
