package com.qts.gopik_loan.Pojo;

public class Aadhar_OTP_Verify_POJO {
    private String consent;
    private String otp;
    private String  shareCode;
    private String accessKey;
    private ClientData clientData;

    public Aadhar_OTP_Verify_POJO(String consent, String otp, String shareCode, String accessKey, ClientData clientData) {
        this.consent = consent;
        this.otp = otp;
        this.shareCode = shareCode;
        this.accessKey = accessKey;
        this.clientData = clientData;
    }
}
