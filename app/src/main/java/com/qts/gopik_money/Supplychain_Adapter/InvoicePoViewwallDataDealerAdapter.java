package com.qts.gopik_money.Supplychain_Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.qts.gopik_money.Model.InvoicePayloadMODEL_DEALER;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Supply_Chain.AwaitingDisbursal;
import com.qts.gopik_money.Supply_Chain.Disbursed;
import com.qts.gopik_money.Supply_Chain.PoDetail_Approve_Dealer_Activity;
import com.qts.gopik_money.Supply_Chain.PoDetail_Rejected_Dealer_Activity;
import com.qts.gopik_money.Supply_Chain.Po_Generate_Approved_By_Financer_Activity;
import com.qts.gopik_money.Supply_Chain.Po_Generate_Rejected_By_Financer_Activity;

import java.util.ArrayList;

public class InvoicePoViewwallDataDealerAdapter extends RecyclerView.Adapter<InvoicePoViewwallDataDealerAdapter.ViewHolder> {
    private final ArrayList<InvoicePayloadMODEL_DEALER> mArraylist;
    Context mContext;
    boolean mIsTopFiveList;
    private final String mApprovedByDealer = "Approved By Dealer";
    private final String mApprovedByGoPik = "Approved By GoPik";
    private final String mApprovedByFinancier = "Approved by financer";
    private final String mRejectedByFinancier = "Rejected by financer";
    private final String mDisbursedByFinancier = "Disbursed by financer";
    private final String mRejectedByDealer = "Rejected by Dealer";
    private final String mAwaitingDisbursal = "Awaiting Disbursal";
    private final String mPendingByGoPik = "Pending by GoPik";
    public InvoicePoViewwallDataDealerAdapter(ArrayList<InvoicePayloadMODEL_DEALER> mWAppStatusList, Boolean mIsTopFiveList) {
        this.mArraylist = mWAppStatusList;
        this.mIsTopFiveList = mIsTopFiveList;
    }

    @Override
    public InvoicePoViewwallDataDealerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.shopkeeper_viewall_ui, parent, false);
        mContext = parent.getContext();
        return new InvoicePoViewwallDataDealerAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(InvoicePoViewwallDataDealerAdapter.ViewHolder holder, int position) {
        InvoicePayloadMODEL_DEALER mList = mArraylist.get(position);
        holder.tax_id_number.setText(mList.getPo_id());
        holder.name_tv.setText(mList.getDate());
        holder.status.setText(mList.getStatus());

        switch (mList.getStatus()) {
            case mApprovedByGoPik:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mPendingByGoPik:
                holder.status_background.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
                break;
            case mApprovedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mApprovedByDealer:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mRejectedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.rejecteded);
                break;
            case mDisbursedByFinancier:
                holder.status_background.setBackgroundResource(R.drawable.approved_layout);
                break;
            case mRejectedByDealer:
                holder.status_background.setBackgroundResource(R.drawable.rejecteded);
                break;
            case mAwaitingDisbursal:
                holder.status_background.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
                break;
        }

        holder.itemView.setOnClickListener(v -> {

            switch (mList.getStatus()){
                case mRejectedByFinancier:
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                    Intent it = new Intent(mContext, Po_Generate_Rejected_By_Financer_Activity.class);
                    mContext.startActivity(it);
                    break;
                case mDisbursedByFinancier:
                     it = new Intent(mContext, Disbursed.class);
                    mContext.startActivity(it);
                    break;
                case mApprovedByFinancier:
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                     it = new Intent(mContext, Po_Generate_Approved_By_Financer_Activity.class);
                    mContext.startActivity(it);
                    break;
                case mAwaitingDisbursal:
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                     it = new Intent(mContext, AwaitingDisbursal.class);
                    mContext.startActivity(it);
                    break;
                case mApprovedByDealer:
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                    it = new Intent(mContext, PoDetail_Approve_Dealer_Activity.class);
                    mContext.startActivity(it);
                    break;
                case mRejectedByDealer:
                    SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                    it = new Intent(mContext, PoDetail_Rejected_Dealer_Activity.class);
                    mContext.startActivity(it);
                    break;


            }

          /* if (mList.getStatus().equals(mRejectedByFinancier)) {
               SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                Intent it = new Intent(mContext, Po_Generate_Rejected_By_Financer_Activity.class);
                mContext.startActivity(it);
            } else if (mList.getStatus().equals(mDisbursedByFinancier)) {
                Intent it = new Intent(mContext, Disbursed.class);
                mContext.startActivity(it);
            }

            else if (mList.getStatus().equals(mApprovedByFinancier)) {
               SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                Intent it = new Intent(mContext, Po_Generate_Approved_By_Financer_Activity.class);
                mContext.startActivity(it);
            }
            else if (mList.getStatus().equals(mAwaitingDisbursal)) {
               SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
                Intent it = new Intent(mContext, AwaitingDisbursal.class);
                mContext.startActivity(it);
            }
           else if (mList.getStatus().equals(mApprovedByDealer)) {
               SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
               Intent it = new Intent(mContext, PoDetail_Approve_Dealer_Activity.class);
               mContext.startActivity(it);
           }
           else if (mList.getStatus().equals(mRejectedByDealer)) {
               SharedPref.saveStringInSharedPref(AppConstants.PO_ID, mList.getPo_id(), v.getContext());
               Intent it = new Intent(mContext, PoDetail_Rejected_Dealer_Activity.class);
               mContext.startActivity(it);
           }*/
        });
    }

    @Override
    public int getItemCount() {
//        return mWAppStatusArrayList.size();

        return mArraylist.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tax_id_number, name_tv, status;
        ConstraintLayout cardView_of_item, status_background;
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