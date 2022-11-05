package com.qts.gopik_money.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.qts.gopik_money.R;

public class CustPrograssbar {


    Dialog dialogCondition;
    public void prograssCreate(Context context) {

        try {
            if (dialogCondition != null && dialogCondition.isShowing()) {
                return;
            } else {
                dialogCondition = new Dialog(context);
                dialogCondition.setContentView(R.layout.custumpro_dailog);
                dialogCondition.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                dialogCondition.setCancelable(true);
                dialogCondition.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closePrograssBar() {
     if (dialogCondition != null) {
            try {
                dialogCondition.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
