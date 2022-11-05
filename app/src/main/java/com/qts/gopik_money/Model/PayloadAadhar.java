package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class PayloadAadhar {
    @SerializedName("key")
    String key;
    @SerializedName("id")
    String id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}