package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCreditLimitForShopkeeper_PAYLOAD {
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getDoc_status() {
        return doc_status;
    }

    public void setDoc_status(String doc_status) {
        this.doc_status = doc_status;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;


    @Expose
    @SerializedName("doc_status")
    private String doc_status;

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    @Expose
    @SerializedName("credit_limit")
    private String credit_limit;

    public String getReject_reasn() {
        return reject_reasn;
    }

    public void setReject_reasn(String reject_reasn) {
        this.reject_reasn = reject_reasn;
    }

    @Expose
    @SerializedName("reject_reasn")
    private String reject_reasn;

    public String getRemark_reasn() {
        return remark_reasn;
    }

    public void setRemark_reasn(String remark_reasn) {
        this.remark_reasn = remark_reasn;
    }

    @Expose
    @SerializedName("remark_reasn")
    private String remark_reasn;
}
