package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name_MODEL {
    public String getNameFromPassport() {
        return nameFromPassport;
    }

    public void setNameFromPassport(String nameFromPassport) {
        this.nameFromPassport = nameFromPassport;
    }

    @Expose
    @SerializedName("nameFromPassport")
    private String nameFromPassport ;

    public String getSurnameFromPassport() {
        return surnameFromPassport;
    }

    public void setSurnameFromPassport(String surnameFromPassport) {
        this.surnameFromPassport = surnameFromPassport;
    }

    @Expose
    @SerializedName("surnameFromPassport")
    private String surnameFromPassport ;

    public String getNameMatch() {
        return nameMatch;
    }

    public void setNameMatch(String nameMatch) {
        this.nameMatch = nameMatch;
    }

    @Expose
    @SerializedName("nameMatch")
    private String nameMatch ;

    public String getNameScore() {
        return nameScore;
    }

    public void setNameScore(String nameScore) {
        this.nameScore = nameScore;
    }

    @Expose
    @SerializedName("nameScore")
    private String nameScore ;
}
