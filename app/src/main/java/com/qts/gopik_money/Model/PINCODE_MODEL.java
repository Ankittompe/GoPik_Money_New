package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PINCODE_MODEL {


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Expose
    @SerializedName("Message")
    private String Message;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Expose
    @SerializedName("Status")
    private String Status;


    public ArrayList<PostOffice_PAYLOAD> getPostOffice() {
        return PostOffice;
    }

    public void setPostOffice(ArrayList<PostOffice_PAYLOAD> postOffice) {
        PostOffice = postOffice;
    }

    @Expose
    @SerializedName("PostOffice")
    private ArrayList<PostOffice_PAYLOAD> PostOffice;
}
