package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealerdoctoFinance_PAYLOAD {
   /* "usercode": "83206",
            "doc_status": "ApprovalWatting"
*/

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getDoc_status() {
        return doc_status;
    }

    public void setDoc_status(String doc_status) {
        this.doc_status = doc_status;
    }

    @Expose
    @SerializedName("usercode")
    private String usercode;


    @Expose
    @SerializedName("doc_status")
    private String doc_status;

}
