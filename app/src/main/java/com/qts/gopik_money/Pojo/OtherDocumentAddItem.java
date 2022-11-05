package com.qts.gopik_money.Pojo;

import com.google.gson.annotations.SerializedName;

public class OtherDocumentAddItem {

    public String getOtherdocument1() {
        return otherdocument1;
    }

    public void setOtherdocument1(String otherdocument1) {
        this.otherdocument1 = otherdocument1;
    }


    public OtherDocumentAddItem(String otherdocument1) {
        this.otherdocument1 = otherdocument1;

    }
    @SerializedName("otherdocument1")
    private String otherdocument1;



}
