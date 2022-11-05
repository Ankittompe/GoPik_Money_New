package com.qts.gopik_money.Utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.R;

import org.jetbrains.annotations.Nullable;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    TextView reason_tv;
    TextView id_reason;
    TextView status_reason;
    String reason = "Reason for Rejection";
    String remark = "Please give the following information";
    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottomsheet, null);
        dialog.setContentView(contentView);
        reason_tv = contentView.findViewById(R.id.reason_tv);
        id_reason = contentView.findViewById(R.id.id_reason);
        status_reason = contentView.findViewById(R.id.status_reason);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        id_reason.setVisibility(View.VISIBLE);
        if (SharedPref.getStringFromSharedPref(AppConstants.DOC_STATUS,getContext()).equals("Rejected")){
            id_reason.setText(reason);
            status_reason.setText(SharedPref.getStringFromSharedPref(AppConstants.STATUS_REASON,getContext()));
        } else if (SharedPref.getStringFromSharedPref(AppConstants.DOC_STATUS,getContext()).equals("Approved")){
            id_reason.setVisibility(View.GONE);
            status_reason.setText(SharedPref.getStringFromSharedPref(AppConstants.STATUS_REASON,getContext()));
        }else if (SharedPref.getStringFromSharedPref(AppConstants.DOC_STATUS,getContext()).equals("Remark")){
            status_reason.setText(SharedPref.getStringFromSharedPref(AppConstants.STATUS_REASON,getContext()));
            id_reason.setText(remark);
        }
    }

}
