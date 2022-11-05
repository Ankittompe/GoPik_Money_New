package com.qts.gopik_money.Pojo;

import java.util.ArrayList;

public class PO_MODIFY_POJO {

    private String po_id;
    private String invoice_no;
    private String estm_amount;
    private ArrayList<PO_ModifyData> update_prod;

    public PO_MODIFY_POJO(String po_id, String invoice_no, String estm_amount, ArrayList<PO_ModifyData> update_prod) {
        this.po_id = po_id;
        this.invoice_no = invoice_no;
        this.estm_amount = estm_amount;
        this.update_prod = update_prod;
    }
}
