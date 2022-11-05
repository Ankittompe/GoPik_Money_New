package com.qts.gopik_money.Pojo;

public class AADHAR_CONSENT_POJO {

    private String lat;
    private String longi;
    private String ipAddress;
    private String userAgent;
    private String deviceId;
    private String deviceInfo;
    private String consent;
    private String name;
    private String consentTime;
    private String consentText;
    private ClientData clientData;




    public AADHAR_CONSENT_POJO(String lat,String longi,String ipAddress,String userAgent
            ,String deviceId,String deviceInfo,String consent
            ,String name,String consentTime,String consentText
            ,ClientData clientData

    ) {
        this.lat = lat;
        this.longi = longi;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceId = deviceId;
        this.deviceInfo = deviceInfo;
        this.consent = consent;
        this.consentText = consentText;
        this.consentTime = consentTime;
        this.clientData = clientData;




    }
}
