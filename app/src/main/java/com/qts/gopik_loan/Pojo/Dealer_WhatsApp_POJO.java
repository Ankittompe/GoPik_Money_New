
package com.qts.gopik_loan.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dealer_WhatsApp_POJO implements Serializable {

    @SerializedName("application_status")
    private String applicationStatus;
    @SerializedName("case_type")
    private String caseType;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("cust_code")
    private String custCode;
    @SerializedName("cust_location")
    private String custLocation;
    @SerializedName("cust_name")
    private String custName;
    @SerializedName("cust_phone")
    private String custPhone;
    @SerializedName("cust_pin")
    private String custPin;
    @SerializedName("dealer_brand")
    private String dealerBrand;
    @SerializedName("dealer_code")
    private String dealerCode;
    @SerializedName("dealer_name")
    private String dealerName;
    @Expose
    private Long id;
    @SerializedName("is_pin_valid")
    private String isPinValid;
    @SerializedName("loan_amount")
    private String loanAmount;
    @SerializedName("loan_priority")
    private String loanPriority;
    @SerializedName("loan_priority_id")
    private String loanPriorityId;
    @Expose
    private String q1;
    @Expose
    private String q2;
    @Expose
    private String q3;
    @Expose
    private String q4;
    @Expose
    private String q5;
    @SerializedName("trigger_msg")
    private String triggerMsg;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustPin() {
        return custPin;
    }

    public void setCustPin(String custPin) {
        this.custPin = custPin;
    }

    public String getDealerBrand() {
        return dealerBrand;
    }

    public void setDealerBrand(String dealerBrand) {
        this.dealerBrand = dealerBrand;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsPinValid() {
        return isPinValid;
    }

    public void setIsPinValid(String isPinValid) {
        this.isPinValid = isPinValid;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanPriority() {
        return loanPriority;
    }

    public void setLoanPriority(String loanPriority) {
        this.loanPriority = loanPriority;
    }

    public String getLoanPriorityId() {
        return loanPriorityId;
    }

    public void setLoanPriorityId(String loanPriorityId) {
        this.loanPriorityId = loanPriorityId;
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    public String getTriggerMsg() {
        return triggerMsg;
    }

    public void setTriggerMsg(String triggerMsg) {
        this.triggerMsg = triggerMsg;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
