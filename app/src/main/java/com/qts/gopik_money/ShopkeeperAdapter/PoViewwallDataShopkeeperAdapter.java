package com.qts.gopik_money.ShopkeeperAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeper_PO_profile;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Shopkeeper.ApprovedFinacerShopkeeper;
import com.qts.gopik_money.Shopkeeper.PendingAtGopik;
import com.qts.gopik_money.Shopkeeper.RejectedShopkeeper;

import java.util.ArrayList;

public class PoViewwallDataShopkeeperAdapter extends RecyclerView.Adapter<PoViewwallDataShopkeeperAdapter.ViewHolder> {
    private final ArrayList<Shopkeeper_PO_profile> mArraylist;
    Context mContext;
    boolean mIsTopFiveList;
    private final String mPendingByGoPik = "Pending by GoPik";
    private final String mApprovedByGoPik = "Approved by GoPik";
    private final String mApprovedByFinancier = "Approved at financer";
    private final String mRejectedByFinancier = "Rejected by financer";
    private final String mDisbursedByFinancier = "Disbursed by financer";
    private final String mRejectedByGoPik = "Rejected by GoPik";
    private final String mAwaitingDisbursal = "Awaiting Disbursal";

    public PoViewwallDataShopkeeperAdapter(ArrayList<Shopkeeper_PO_profile> mWAppStatusList, Boolean mIsTopFiveList) {
        this.mArraylist = mWAppStatusList;
        this.mIsTopFiveList = mIsTopFiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.shopkeeper_viewall_ui, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shopkeeper_PO_profile mList = mArraylist.get(position);
        holder.tax_id_number.setText(mList.getPo_id());

        holder.name_tv.setText(mList.getDate());
        holder.status.setText(mList.getStatus());

        switch (mList.getStatus()) {
            case mApprovedByGoPik:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mPendingByGoPik:
                holder.status_background.setBackgroundResource(R.drawable.pending_financer);
                break;
            case mApprovedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mRejectedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.rejecteded);
                break;
            case mDisbursedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mRejectedByGoPik:
                holder.status_background.setBackgroundResource(R.drawable.rejecteded);
                break;
            case mAwaitingDisbursal:
                holder.status_background.setBackgroundResource(R.drawable.pending_financer);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            switch (mList.getStatus()){
                case mApprovedByGoPik:
                case mDisbursedByFinancier:
                case mAwaitingDisbursal:
                case mPendingByGoPik:
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_IDD, mList.getPo_id(), mContext.getApplicationContext());
                    Log.e("PO_IDD","-->"+SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_IDD, mContext.getApplicationContext()));
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_ID, String.valueOf(mList.getId()), mContext.getApplicationContext());
                    Log.e("PO_ID","-->"+SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_ID, mContext.getApplicationContext()));

                    Intent it = new Intent(mContext, PendingAtGopik.class);
                    mContext.startActivity(it);
                    break;
                case mRejectedByFinancier:
                case mRejectedByGoPik:
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_IDD, mList.getPo_id(), mContext.getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_ID, String.valueOf(mList.getId()), mContext.getApplicationContext());
                    it = new Intent(mContext, RejectedShopkeeper.class);
                    mContext.startActivity(it);
                    break;
                default:
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_IDD, mList.getPo_id(), mContext.getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_ID, String.valueOf(mList.getId()), mContext.getApplicationContext());
                     it = new Intent(mContext, ApprovedFinacerShopkeeper.class);
                    mContext.startActivity(it);
                   break;
            }


        });
    }

    @Override
    public int getItemCount() {


        return mArraylist.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tax_id_number;
        TextView  name_tv;
        TextView  status;
        ConstraintLayout cardView_of_item;
        ConstraintLayout  status_background;
        ImageView next_arrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            tax_id_number = itemView.findViewById(R.id.tax_id_number);
            name_tv = itemView.findViewById(R.id.name_tv);
            cardView_of_item = itemView.findViewById(R.id.cardView_of_item);
            status_background = itemView.findViewById(R.id.status_background);
            next_arrow = itemView.findViewById(R.id.next_arrow);
        }
    }
}