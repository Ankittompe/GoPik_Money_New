package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DATA_UPLOAD_IDENTITY_PAYLOAD {

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    @Expose
    @SerializedName("customer_code")
    private String customer_code;

    public String getProof_name() {
        return proof_name;
    }

    public void setProof_name(String proof_name) {
        this.proof_name = proof_name;
    }

    @Expose
    @SerializedName("proof_name")
    private String proof_name;

    public String getProof_type() {
        return proof_type;
    }

    public void setProof_type(String proof_type) {
        this.proof_type = proof_type;
    }

    @Expose
    @SerializedName("proof_type")
    private String proof_type;

    public String getIdproof_no() {
        return idproof_no;
    }

    public void setIdproof_no(String idproof_no) {
        this.idproof_no = idproof_no;
    }

    @Expose
    @SerializedName("idproof_no")
    private String idproof_no;

    public String getRln_name() {
        return rln_name;
    }

    public void setRln_name(String rln_name) {
        this.rln_name = rln_name;
    }

    @Expose
    @SerializedName("rln_name")
    private String rln_name;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Expose
    @SerializedName("dob")
    private String dob;

    public String getBloodgrup() {
        return bloodgrup;
    }

    public void setBloodgrup(String bloodgrup) {
        this.bloodgrup = bloodgrup;
    }

    @Expose
    @SerializedName("bloodgrup")
    private String bloodgrup;


}
