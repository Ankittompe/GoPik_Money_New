package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerPoLoansResponse_Model {
/*"po_id": "PO072285101",
        "loan_no": "LN095678544534",
        "Approved_amount": "27300",
        "start_date": "2022-07-13 07:19:53",
        "end_date": "2022-07-13 07:19:53"*/

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getLoan_no() {
        return loan_no;
    }

    public void setLoan_no(String loan_no) {
        this.loan_no = loan_no;
    }

    public String getApproved_amount() {
        return Approved_amount;
    }

    public void setApproved_amount(String approved_amount) {
        Approved_amount = approved_amount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    @Expose
    @SerializedName("po_id")
    private String po_id;
    @Expose
    @SerializedName("loan_no")
    private String loan_no;
    @Expose
    @SerializedName("Approved_amount")
    private String Approved_amount;
    @Expose
    @SerializedName("start_date")
    private String start_date;
    @Expose
    @SerializedName("end_date")
    private String end_date;

}
