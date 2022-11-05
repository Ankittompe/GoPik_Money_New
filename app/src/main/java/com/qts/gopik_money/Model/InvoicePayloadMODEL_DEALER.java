package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoicePayloadMODEL_DEALER {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("po_id")
    private String po_id;
    @Expose
    @SerializedName("date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    @SerializedName("status")
    private String status;


}
