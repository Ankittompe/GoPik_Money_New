package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DATA_BANK_ACCOUNT_SOURCE {

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Expose
    @SerializedName("accountNumber")
    private String accountNumber;

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @Expose
    @SerializedName("ifsc")
    private String ifsc;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Expose
    @SerializedName("accountName")
    private String accountName;

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }

    @Expose
    @SerializedName("bankResponse")
    private String bankResponse;

    public String getBankTxnStatus() {
        return bankTxnStatus;
    }

    public void setBankTxnStatus(String bankTxnStatus) {
        this.bankTxnStatus = bankTxnStatus;
    }

    @Expose
    @SerializedName("bankTxnStatus")
    private String bankTxnStatus;

    public String getBankRRN() {
        return bankRRN;
    }

    public void setBankRRN(String bankRRN) {
        this.bankRRN = bankRRN;
    }

    @Expose
    @SerializedName("bankRRN")
    private String bankRRN;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Expose
    @SerializedName("statusCode")
    private String statusCode;


}
