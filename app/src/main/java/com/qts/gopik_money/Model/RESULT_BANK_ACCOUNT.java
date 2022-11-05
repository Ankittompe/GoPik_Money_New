package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESULT_BANK_ACCOUNT {
    public DATA_BANK_ACCOUNT getData() {
        return data;
    }

    public void setData(DATA_BANK_ACCOUNT data) {
        this.data = data;
    }

    @Expose
    @SerializedName("data")
    private DATA_BANK_ACCOUNT data;


    public COMPARISION_DATA getComparisionData() {
        return comparisionData;
    }

    public void setComparisionData(COMPARISION_DATA comparisionData) {
        this.comparisionData = comparisionData;
    }

    @Expose
    @SerializedName("comparisionData")
    private COMPARISION_DATA comparisionData;


}
