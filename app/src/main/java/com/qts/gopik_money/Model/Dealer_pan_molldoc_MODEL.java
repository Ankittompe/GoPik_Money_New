package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer_pan_molldoc_MODEL {
    @Expose
    @SerializedName("code")
    private Integer code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("payload")
    private Supllychain_Pan_Payload payload;

}
