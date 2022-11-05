package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.PayloadItem;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Utils.PODetailsBottomSheet;

import java.util.ArrayList;

public class POListAdapter extends RecyclerView.Adapter<POListAdapter.ViewHolder> {
    private final ArrayList<PayloadItem> mWAppStatusArrayList;
    Context mContext;
    boolean mIsTopFiveList;

    public POListAdapter(ArrayList<PayloadItem> mWAppStatusList, Boolean mIsTopFiveList) {
        this.mWAppStatusArrayList = mWAppStatusList;
        this.mIsTopFiveList = mIsTopFiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.po_list_item, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PayloadItem mList = mWAppStatusArrayList.get(position);
        holder.mTxtPoId.setText(mList.getPo_id());
        Log.e("PoId",">>"+mList.getPo_id()+mList.getStatus());
        holder.mTxtPoDate.setText(mList.getDate());
        holder.mTxtPoStatus.setText(mList.getStatus());

        switch (mList.getStatus()) {
            case "Approved":
            case "Approved by dealer":
            case "Approved by subdealer":
            case "Approved at OEM":
            case "Approved By Dealer":
            case "Approved by financer":
            case "Disbursed by financer":
            case "Approved at dealer":
                holder.mCardVwStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case "Pending at dealer":
            case "Awaiting Disbursal":
                holder.mCardVwStatus.setCardBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
                break;
            case "Modified by dealer":
            case "Modified at OEM":
                holder.mCardVwStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue));
                break;
            case "Rejected at OEM":
            case "Rejected By Dealer":
            case "Rejected by financer":
            case "Rejected by dealer":
            case "Rejected by subdealer":
                holder.mCardVwStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.hero_red));
                break;
            case "Pending at OEM":
                holder.mCardVwStatus.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow_dark));
                break;
        }

        holder.mLinLayoutPoItem.setOnClickListener(view -> {
            PODetailsBottomSheet mPoDetailsBottomSheet = new PODetailsBottomSheet();
            ((MainActivity) mContext).sendDataViaFragment(mPoDetailsBottomSheet, mList);
        });
    }

    @Override
    public int getItemCount() {
        return mWAppStatusArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtPoId ;
        public  TextView mTxtPoStatus;
        public  TextView mTxtPoDate;
        FrameLayout mLinLayoutPoItem;
        CardView mCardVwStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTxtPoStatus = itemView.findViewById(R.id.txtPoStatus);
            this.mTxtPoId = itemView.findViewById(R.id.txtPoId);
            this.mTxtPoDate = itemView.findViewById(R.id.txtPoDate);
            this.mLinLayoutPoItem = itemView.findViewById(R.id.linLayoutPoItem);
            this.mCardVwStatus = itemView.findViewById(R.id.cardVwStatus);
        }
    }
}
