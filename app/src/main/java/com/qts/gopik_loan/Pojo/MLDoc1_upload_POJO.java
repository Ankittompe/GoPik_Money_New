package com.qts.gopik_loan.Pojo;

public class MLDoc1_upload_POJO {
    private String custcode;
    private String proof_type;
    private String idproof_no;
    private String name;
    private String reltn_name;
    private String age;
    private String gender;
    private String dob;
    private String bloodgrp;
    private String doi;
    private String disptchfrmsrc;



    public MLDoc1_upload_POJO(String custcode ,String proof_type,String idproof_no ,String name
            ,String reltn_name,String age,String gender ,String dob
            ,String bloodgrp ,String doi,String disptchfrmsrc ) {

        this.custcode = custcode;
        this.proof_type = proof_type;
        this.idproof_no = idproof_no;
        this.name = name;
        this.reltn_name = reltn_name;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.bloodgrp = bloodgrp;
        this.doi = doi;
        this.disptchfrmsrc = disptchfrmsrc;

    }
}
