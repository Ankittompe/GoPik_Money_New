package com.qts.gopik_money.Pojo;

public class PO_ModifyData {

    String id,quantity;

    public PO_ModifyData(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
