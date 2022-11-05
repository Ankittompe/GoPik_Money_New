package com.qts.gopik_money.Utils;

import android.app.Activity;
import android.widget.Toast;

public class ShowToast {

    public void ShowToastHere(Activity mActivityName, String mMessage) {
        Toast.makeText(mActivityName, mMessage, Toast.LENGTH_SHORT).show();
    }
}
