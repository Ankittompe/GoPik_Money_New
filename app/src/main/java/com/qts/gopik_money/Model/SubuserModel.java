package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubuserModel {
    public String getCode() {
    return code;
}

    public void setCode(String code) {
        this.code = code;
    }

    @Expose
    @SerializedName("code")
    private String code;

    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    @Expose
    @SerializedName("firm_name")
    private String firm_name;

    public String getSub_user() {
        return sub_user;
    }

    public void setSub_user(String sub_user) {
        this.sub_user = sub_user;
    }

    @Expose
    @SerializedName("sub_user")
    private String sub_user;

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    @Expose
    @SerializedName("mob")
    private String mob;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Expose
    @SerializedName("role")
    private String role;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;

    public String getMod_timestamp() {
        return mod_timestamp;
    }

    public void setMod_timestamp(String mod_timestamp) {
        this.mod_timestamp = mod_timestamp;
    }

    @Expose
    @SerializedName("mod_timestamp")
    private String mod_timestamp;

}
