package com.qts.gopik_money.Dealer_Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.Home;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Pojo.Dealer_WhatsApp_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Utils.WhatsAppStatusDetailsBottomSheet;

import java.util.ArrayList;

public class WhatsAppStatusListAdapter extends RecyclerView.Adapter<WhatsAppStatusListAdapter.ViewHolder> {
    private final ArrayList<Dealer_WhatsApp_POJO> mWAppStatusArrayList;
    Context mContext;
    TextView Ok_button;
    TextView reason_info_tv;
    String mUserType;
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
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER,mContext);
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

        if (mList.getApplicationStatus().equals("Approved")) {
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (mList.getApplicationStatus().equals("Submitted")) {
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else if (mList.getApplicationStatus().equals("Declined")) {
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.red));
        } else if (mList.getApplicationStatus().equals("Pending")) {
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        }
        holder.reason_button.setVisibility(View.VISIBLE);

        if (mList.getApplicationStatus().equals("Approved")) {
            holder.reason_button.setVisibility(View.GONE);
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (mList.getApplicationStatus().equals("Submitted")) {
            holder.reason_button.setVisibility(View.GONE);
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else if (mList.getApplicationStatus().equals("Declined")) {
            holder.reason_button.setVisibility(View.VISIBLE);
            String declined_reason = mList.getRor();
            Log.e("declined","-->>"+declined_reason);
            // SharedPref.saveStringInSharedPref(AppConstants.WP_STATUS,mList.getApplicationStatus(),mContext);
            SharedPref.saveStringInSharedPref(AppConstants.WP_REASON,mList.getRor(),mContext);

            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(R.color.red));
        } else if (mList.getApplicationStatus().equals("Pending")) {
            holder.reason_button.setVisibility(View.GONE);
            holder.mTxtApplicationStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        }else if (mList.getApplicationStatus().equals("In Progress")) {
            holder.reason_button.setVisibility(View.GONE);
        }
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

        holder.mCardVwWhatsAppStatus.setOnClickListener(view -> {
            WhatsAppStatusDetailsBottomSheet mWhatsAppStatusDetailsBottomSheet = new WhatsAppStatusDetailsBottomSheet();
            Bundle bundle = new Bundle();
            bundle.putSerializable("whatsAppData", mList);
            mWhatsAppStatusDetailsBottomSheet.setArguments(bundle);
            if(mUserType.equals("Dealer")){
                mWhatsAppStatusDetailsBottomSheet.show(((MainActivity) mContext).getSupportFragmentManager(), "");
            }
            else if(mUserType.equals("SubDealer")){
                mWhatsAppStatusDetailsBottomSheet.show(((MainActivity) mContext).getSupportFragmentManager(), "");
            }
            else if(mUserType.equals("Shop Keeper")){
                mWhatsAppStatusDetailsBottomSheet.show(((HomeShopkeeper) mContext).getSupportFragmentManager(), "");
            }
            else if(mUserType.equals("Broker")){
                mWhatsAppStatusDetailsBottomSheet.show(((Home) mContext).getSupportFragmentManager(), "");
            }


//            Intent mIntent = new Intent(mContext,TestActivity.class);
//            mIntent.putExtra("whatsAppData",mList);
//            mContext.startActivity(mIntent);

        });

        holder.reason_button.setOnClickListener(v -> {
            Dialog dialogCondition = new Dialog(v.getRootView().getContext());
            View view= LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.wp_rejection_dialog,null);
            dialogCondition.setContentView(R.layout.wp_rejection_dialog);
            reason_info_tv = (TextView) dialogCondition.findViewById(R.id.reason_info_tv);
            Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

            reason_info_tv.setText(mList.getRor());

            Ok_button.setOnClickListener(v1 -> dialogCondition.cancel());

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            dialogCondition.setCancelable(true);
            dialogCondition.show();
        });
    }

    @Override
    public int getItemCount() {
        return mWAppStatusArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtCustomerCode;
        public TextView mTxtCustomerName;
        public TextView mTxtCustomerNo;
        public TextView mTxtLoanType;
        public TextView mTxtLoanAmount;
        public TextView mTxtPinNo;
        public TextView mTxtDealerCode;
        public TextView mTxtDealerBrand;
        public TextView mTxtDealerName;
        public TextView mTxtMessage;
        public TextView mTxtLoanPriority;
        public TextView mTxtApplicationStatus;
        public TextView mTxtCreatedAt;
        public TextView mTxtUpdatedAt;
        CardView mCardVwWhatsAppStatus;
        ImageView mImgGotoDetails;
        ImageView reason_button;


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
            this.reason_button = itemView.findViewById(R.id.reason_button);
        }
    }
}
