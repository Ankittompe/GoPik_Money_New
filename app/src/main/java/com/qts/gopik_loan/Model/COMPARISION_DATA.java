package com.qts.gopik_loan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class COMPARISION_DATA {
    public INPUT_SOURCE getInputVsSource() {
        return inputVsSource;
    }

    public void setInputVsSource(INPUT_SOURCE inputVsSource) {
        this.inputVsSource = inputVsSource;
    }

    @Expose
    @SerializedName("inputVsSource")
    private INPUT_SOURCE inputVsSource;
}
