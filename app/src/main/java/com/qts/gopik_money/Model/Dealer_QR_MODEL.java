package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer_QR_MODEL {
    @Expose
    @SerializedName("message")
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Expose
    @SerializedName("QRStatus")
    private String QRStatus;
    public String getQRStatus() {
        return QRStatus;
    }
    public void setQRStatus(String QRStatus) {
        this.QRStatus = QRStatus;
    }
}
