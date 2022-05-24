package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class INPUT_SOURCE {
    public FLAGS getFlags() {
        return flags;
    }

    public void setFlags(FLAGS flags) {
        this.flags = flags;
    }

    @Expose
    @SerializedName("flags")
    private FLAGS flags;


    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Expose
    @SerializedName("validity")
    private String validity;
}
