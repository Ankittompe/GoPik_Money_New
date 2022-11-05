package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BANK_DOCUMENT_PAYLOAD_MODEL {
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getDISB_BANK() {
        return DISB_BANK;
    }

    public void setDISB_BANK(String DISB_BANK) {
        this.DISB_BANK = DISB_BANK;
    }

    public String getDISB_BANK1() {
        return DISB_BANK1;
    }

    public void setDISB_BANK1(String DISB_BANK1) {
        this.DISB_BANK1 = DISB_BANK1;
    }

    public String getDISB_BANK2() {
        return DISB_BANK2;
    }

    public void setDISB_BANK2(String DISB_BANK2) {
        this.DISB_BANK2 = DISB_BANK2;
    }

    public String getDISB_BANK3() {
        return DISB_BANK3;
    }

    public void setDISB_BANK3(String DISB_BANK3) {
        this.DISB_BANK3 = DISB_BANK3;
    }

    public String getDISB_BANK4() {
        return DISB_BANK4;
    }

    public void setDISB_BANK4(String DISB_BANK4) {
        this.DISB_BANK4 = DISB_BANK4;
    }

    public String getDISB_BANK5() {
        return DISB_BANK5;
    }

    public void setDISB_BANK5(String DISB_BANK5) {
        this.DISB_BANK5 = DISB_BANK5;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;
    @Expose
    @SerializedName("DISB_BANK")
    private String DISB_BANK;

    @Expose
    @SerializedName("DISB_BANK1")
    private String DISB_BANK1;

    @Expose
    @SerializedName("DISB_BANK2")
    private String DISB_BANK2;

    @Expose
    @SerializedName("DISB_BANK3")
    private String DISB_BANK3;
    @Expose
    @SerializedName("DISB_BANK4")
    private String DISB_BANK4;
    @Expose
    @SerializedName("DISB_BANK5")
    private String DISB_BANK5;

}
