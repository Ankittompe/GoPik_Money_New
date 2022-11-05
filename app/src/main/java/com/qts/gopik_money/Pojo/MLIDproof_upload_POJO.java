package com.qts.gopik_money.Pojo;

public class MLIDproof_upload_POJO {
    private String cust_code;
    private String proof_type;
    private String idproof_no;
    private String name;
    private String reltn_name;
    private String age;
    private String gender;
    private String dob;
    private String doi;
    private String bloodgrp;
    private String disptchfrmsrc;



    public MLIDproof_upload_POJO(String cust_code, String proof_type , String idproof_no
            , String name, String reltn_name, String age, String gender
            , String dob, String doi, String bloodgrp
            , String disptchfrmsrc)
    {
        this.cust_code = cust_code;
        this.proof_type = proof_type;
        this.idproof_no = idproof_no;
        this.name = name;
        this.reltn_name = reltn_name;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.doi = doi;
        this.bloodgrp = bloodgrp;
        this.disptchfrmsrc = disptchfrmsrc;



    }
}
