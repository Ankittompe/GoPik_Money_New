package com.qts.gopik_money.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.R;

public class InvoiceViewDownloadDialog {
     Dialog dialogCondition;
    TextView mBtnOk;
    Button mBtnDownload;
    ImageView mImgCamera;

    public void InvoiceDialog(String mImagePath, Activity mActivity) {

        dialogCondition = new Dialog(mActivity, R.style.CustomAlertDialog);
        dialogCondition.setContentView(R.layout.invoice_dailog);
        mBtnOk = dialogCondition.findViewById(R.id.Ok_button);
        mBtnDownload = dialogCondition.findViewById(R.id.btnDownload);
        mImgCamera = dialogCondition.findViewById(R.id.camera_button);
        Glide.with(mActivity).load(mImagePath).into(mImgCamera);
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        mBtnOk.setOnClickListener(v -> dialogCondition.dismiss());


        mBtnDownload.setOnClickListener(v -> {
            dialogCondition.dismiss();
            new FileDownloader().downloadFile(mImagePath, mActivity);
        });
    }
}
