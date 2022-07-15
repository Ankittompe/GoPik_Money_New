package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoanPoAllDetails_MODEL {
 /* "code": 200,
          "message": "Complete details!!",
          "payload": [
    {
        "id": 525,
            "po_id": "PO072298613",
            "date": "2022-07-13 12:41:41",
            "brand": "Hero",
            "dealer_id": "09270",
            "dealer_name": "sai",
            "product": "MIG 24T RS",
            "prodt_quantity": "2",
            "update_quantity": "3",
            "prodt_price": "9100",
            "update_price": "10000",
            "total_price": "18200",
            "update_totl_prc": "30000",
            "invoice_no": "NA",
            "financer": "NA",
            "status": "Disbursed by financer",
            "loan_limit": "120000",
            "app_amt": "30000",
            "tenure": "90",
            "roi": "9",
            "comments": "Fine",
            "reason_of_rejection": "NA",
            "invoice_file": "POinvoice_doc/PO072298613/POInvoice.jpg",
            "disvrsal_report": "public/DealerSupplyChainreport/PO072298613/DealerSupplyChainreport.jpg",
            "oem_time": "2022-07-13 12:43:21",
            "dealer_action_time": "2022-07-13 12:43:47",
            "financer_action_time": "2022-07-13 15:26:25",
            "financer_disbursal_time": "NA",
            "delivery_gen_time": "2022-07-13 15:26:12"
    }
    ],
            "po_id": "PO072298613",
            "loan_id": "LN095678544534",
            "disbursal_amount": "30000",
            "date_of_disbursal": "2022-07-13 15:26:25",
            "date_of_closure": "2022-10-11 10:21:45"*/
 @Expose
 @SerializedName("code")
 private int code;

    @Expose
    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LoanPoAllDetails_Response_MODEL> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<LoanPoAllDetails_Response_MODEL> payload) {
        this.payload = payload;
    }

    @Expose
    @SerializedName("payload")
    private ArrayList<LoanPoAllDetails_Response_MODEL> payload;

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getDisbursal_amount() {
        return disbursal_amount;
    }

    public void setDisbursal_amount(String disbursal_amount) {
        this.disbursal_amount = disbursal_amount;
    }

    public String getDate_of_disbursal() {
        return date_of_disbursal;
    }

    public void setDate_of_disbursal(String date_of_disbursal) {
        this.date_of_disbursal = date_of_disbursal;
    }

    public String getDate_of_closure() {
        return date_of_closure;
    }

    public void setDate_of_closure(String date_of_closure) {
        this.date_of_closure = date_of_closure;
    }

    @Expose
    @SerializedName("po_id")
    private String po_id;
    @Expose
    @SerializedName("loan_id")
    private String loan_id;

    @Expose
    @SerializedName("disbursal_amount")
    private String disbursal_amount;
    @Expose
    @SerializedName("date_of_disbursal")
    private String date_of_disbursal;
    @Expose
    @SerializedName("date_of_closure")
    private String date_of_closure;


}
