package com.qts.gopik_money.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.URL;

public class CheckPDFOrImage {
    public boolean checkIsPDFOrImage(String mPath){
        if(mPath.equals("NA")){
            return false;
        }
        else {
            String extension = mPath.substring(mPath.lastIndexOf("."));
            if (extension.equals(".pdf")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void openPDFFromURL(Activity mActivityName, String mPath){
        Log.e("pdfpath ", ""+mPath);
        URL url;
        try {
            url = new URL(mPath);
             mActivityName.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(url))));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
