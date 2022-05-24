package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ACCOUNT_NO_MODEL {

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @Expose
    @SerializedName("ADDRESS")
    private String ADDRESS;
    @Expose
    @SerializedName("MICR")
    private String MICR;
    @Expose
    @SerializedName("IMPS")
    private String IMPS;
    @Expose
    @SerializedName("CONTACT")
    private String CONTACT;
    @Expose
    @SerializedName("DISTRICT")
    private String DISTRICT;
    @Expose
    @SerializedName("NEFT")
    private String NEFT;

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }

    @Expose
    @SerializedName("BRANCH")
    private String BRANCH;

    public String getBANK() {
        return BANK;
    }

    public void setBANK(String BANK) {
        this.BANK = BANK;
    }

    @Expose
    @SerializedName("BANK")
    private String BANK;

    public String getBANKCODE() {
        return BANKCODE;
    }

    public void setBANKCODE(String BANKCODE) {
        this.BANKCODE = BANKCODE;
    }

    @Expose
    @SerializedName("BANKCODE")
    private String BANKCODE;

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    @Expose
    @SerializedName("IFSC")
    private String IFSC;


}
