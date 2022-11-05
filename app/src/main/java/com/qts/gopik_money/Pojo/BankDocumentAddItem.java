package com.qts.gopik_money.Pojo;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class BankDocumentAddItem {
    public Bitmap getBankdocument1() {
        return bankdocument1;
    }

    public void setBankdocument1(Bitmap bankdocument1) {
        this.bankdocument1 = bankdocument1;
    }

    @SerializedName("bankdocument1")
    private Bitmap bankdocument1;


    public BankDocumentAddItem(Bitmap bankdocument1, File pdffile, String pdfname) {
        this.bankdocument1 = bankdocument1;
        this.pdffile = pdffile;
        this.pdfname = pdfname;
    }

    public File getPdffile() {
        return pdffile;
    }

    public void setPdffile(File pdffile) {
        this.pdffile = pdffile;
    }

    public String getPdfname() {
        return pdfname;
    }

    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    @SerializedName("pdffile")
    private File pdffile;

    @SerializedName("pdfname")
    private String pdfname;


}
