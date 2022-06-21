package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.QR_DataList_MODEL;

import com.qts.gopik_loan.Pojo.Dealer_WhatsApp_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Utils.WhatsAppStatusDetailsBottomSheet;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WhatsAppStatusListAdapter extends RecyclerView.Adapter<WhatsAppStatusListAdapter.ViewHolder> {
    private final ArrayList<Dealer_WhatsApp_POJO> mWAppStatusArrayList;
    Context mContext;

    // RecyclerView recyclerView;
    public WhatsAppStatusListAdapter(ArrayList<Dealer_WhatsApp_POJO> mWAppStatusList) {
        this.mWAppStatusArrayList = mWAppStatusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.whatsapp_list_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dealer_WhatsApp_POJO mList = mWAppStatusArrayList.get(position);
        holder.mTxtCustomerCode.setText(mList.getCustCode());
        holder.mTxtCustomerName.setText(mList.getCustName());
        holder.mTxtCustomerNo.setText(mList.getCustPhone());
        holder.mTxtLoanType.setText(mList.getCaseType());
        holder.mTxtLoanAmount.setText("₹ " + mList.getLoanAmount());
        holder.mTxtPinNo.setText(mList.getCustPin());
        holder.mTxtDealerCode.setText(mList.getDealerCode());
        holder.mTxtDealerBrand.setText(mList.getDealerBrand());
        holder.mTxtDealerName.setText(mList.getDealerName());
        holder.mTxtMessage.setText(mList.getTriggerMsg());
        holder.mTxtLoanPriority.setText(mList.getLoanPriority());
        holder.mTxtApplicationStatus.setText(mList.getApplicationStatus());

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            ZonedDateTime dateTime;
//            dateTime = ZonedDateTime.parse(mList.getCreatedAt());
//            String mDate = dateTime.withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
//            holder.mTxtCreatedAt.setText(mDate);
//        } else {
        holder.mTxtCreatedAt.setText(mList.getCreatedAt());
//        }
/*        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime dateTime;
            dateTime = ZonedDateTime.parse(mList.getUpdatedAt());
            String mDate = dateTime.withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
            holder.mTxtUpdatedAt.setText(mDate);
        } else {*/
        holder.mTxtUpdatedAt.setText(mList.getUpdatedAt());
        //}

        holder.mImgGotoDetails.setOnClickListener(view -> {
            WhatsAppStatusDetailsBottomSheet mWhatsAppStatusDetailsBottomSheet = new WhatsAppStatusDetailsBottomSheet();
            Bundle bundle = new Bundle();
            bundle.putSerializable("whatsAppData", mList);
            mWhatsAppStatusDetailsBottomSheet.setArguments(bundle);
            mWhatsAppStatusDetailsBottomSheet.show(((MainActivity) mContext).getSupportFragmentManager(), "");
        });
    }

    @Override
    public int getItemCount() {
        return mWAppStatusArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtCustomerCode, mTxtCustomerName, mTxtCustomerNo, mTxtLoanType, mTxtLoanAmount, mTxtPinNo,
                mTxtDealerCode, mTxtDealerBrand, mTxtDealerName, mTxtMessage, mTxtLoanPriority, mTxtApplicationStatus, mTxtCreatedAt, mTxtUpdatedAt;
        CardView mCardVwWhatsAppStatus;
        ImageView mImgGotoDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTxtCustomerCode = itemView.findViewById(R.id.txtCustomerCode);
            this.mTxtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            this.mTxtCustomerNo = itemView.findViewById(R.id.txtCustomerNo);
            this.mTxtLoanType = itemView.findViewById(R.id.txtLoanType);
            this.mTxtLoanAmount = itemView.findViewById(R.id.txtLoanAmount);
            this.mTxtPinNo = itemView.findViewById(R.id.txtPinNumber);
            this.mTxtDealerCode = itemView.findViewById(R.id.txtDealerCode);
            this.mTxtDealerBrand = itemView.findViewById(R.id.txtDealerBrand);
            this.mTxtDealerName = itemView.findViewById(R.id.txtDealerName);
            this.mTxtMessage = itemView.findViewById(R.id.txtMessage);
            this.mTxtLoanPriority = itemView.findViewById(R.id.txtLoanPriority);
            this.mTxtApplicationStatus = itemView.findViewById(R.id.txtApplicationStatus);
            this.mTxtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            this.mTxtUpdatedAt = itemView.findViewById(R.id.txtUpdatedAt);
            this.mCardVwWhatsAppStatus = itemView.findViewById(R.id.cardVwWhatsAppStatus);
            this.mImgGotoDetails = itemView.findViewById(R.id.imgGotoDetails);

        }
    }
}
