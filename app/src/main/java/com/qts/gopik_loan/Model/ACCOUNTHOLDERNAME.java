package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ACCOUNTHOLDERNAME {
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Expose
    @SerializedName("score")
    private String score;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Expose
    @SerializedName("result")
    private String result;
}
