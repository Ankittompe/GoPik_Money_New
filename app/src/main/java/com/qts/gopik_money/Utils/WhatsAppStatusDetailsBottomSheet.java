package com.qts.gopik_money.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qts.gopik_money.Pojo.Dealer_WhatsApp_POJO;
import com.qts.gopik_money.R;

public class WhatsAppStatusDetailsBottomSheet extends BottomSheetDialogFragment {

    public TextView mTxtCustomerCode, mTxtCustomerName, mTxtCustomerNo, mTxtLoanType, mTxtLoanAmount, mTxtPinNo,
            mTxtDealerCode, mTxtDealerBrand, mTxtDealerName, mTxtLoanPriority, mTxtApplicationStatus, mTxtCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.whatsapp_status_details_bottom_sheet_layout, container, false);
        initData(v);
        receiveData();
        return v;
    }

    private void initData(View itemView) {
        mTxtCustomerCode = itemView.findViewById(R.id.txtDetailsCustCode);
        mTxtCustomerName = itemView.findViewById(R.id.txtDetailsCustName);
        mTxtCustomerNo = itemView.findViewById(R.id.txtDetailsCustNo);
        mTxtLoanType = itemView.findViewById(R.id.txtDetailsLoanType);
        mTxtLoanAmount = itemView.findViewById(R.id.txtDetailsLoanAmount);
        mTxtPinNo = itemView.findViewById(R.id.txtDetailsPinNo);
        mTxtDealerCode = itemView.findViewById(R.id.txtDetailsDealerCode);
        mTxtDealerBrand = itemView.findViewById(R.id.txtDetailsDealerBrand);
        mTxtDealerName = itemView.findViewById(R.id.txtDetailsDealerName);
        mTxtLoanPriority = itemView.findViewById(R.id.txtDetailsFinancer);
        mTxtApplicationStatus = itemView.findViewById(R.id.txtDetailsAppStatus);
        mTxtCreatedAt = itemView.findViewById(R.id.txtDetailsTime);

    }

    private void receiveData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Dealer_WhatsApp_POJO mDealer_WhatsApp_POJO = (Dealer_WhatsApp_POJO) bundle.getSerializable("whatsAppData");
            Log.e("", mDealer_WhatsApp_POJO.getCustName());
            mTxtCustomerCode.setText(mDealer_WhatsApp_POJO.getCustCode());
            mTxtCustomerName.setText(mDealer_WhatsApp_POJO.getCustName());
            mTxtCustomerNo.setText(mDealer_WhatsApp_POJO.getCustPhone());
            mTxtLoanType.setText(mDealer_WhatsApp_POJO.getCaseType());
            mTxtLoanAmount.setText("₹ " + mDealer_WhatsApp_POJO.getLoanAmount());
            mTxtPinNo.setText(mDealer_WhatsApp_POJO.getCustPin());
            mTxtDealerCode.setText(mDealer_WhatsApp_POJO.getDealerCode());
            mTxtDealerBrand.setText(mDealer_WhatsApp_POJO.getDealerBrand());
            mTxtDealerName.setText(mDealer_WhatsApp_POJO.getDealerName());
            mTxtLoanPriority.setText(mDealer_WhatsApp_POJO.getLoanPriority());
            mTxtApplicationStatus.setText(mDealer_WhatsApp_POJO.getApplicationStatus());
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                ZonedDateTime dateTime;
//                dateTime = ZonedDateTime.parse(mDealer_WhatsApp_POJO.getCreatedAt());
//                String mDate = dateTime.withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
//                mTxtCreatedAt.setText(mDate);
//            } else {
//            }
            mTxtCreatedAt.setText(mDealer_WhatsApp_POJO.getCreatedAt());
        } else {
            Log.e("", "Empty Data");
        }

    }
}