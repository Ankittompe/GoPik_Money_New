package com.qts.gopik_money.Pojo;

public class Bankacc_verification_POJO {
    private String accountNumber;
    private String accountHolderName;
    private String ifsc;
    private String consent;
    private String nameMatchType;

    public Bankacc_verification_POJO(String accountNumber, String accountHolderName, String ifsc
            , String consent, String nameMatchType) {

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.ifsc = ifsc;
        this.consent = consent;
        this.nameMatchType = nameMatchType;


    }
}
