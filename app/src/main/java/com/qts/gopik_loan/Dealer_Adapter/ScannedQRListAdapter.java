package com.qts.gopik_loan.Dealer_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Dealer_Fragment.Dealer_QR_Code_Fragment;
import com.qts.gopik_loan.Model.QR_DataList_MODEL;
import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class ScannedQRListAdapter extends RecyclerView.Adapter<ScannedQRListAdapter.ViewHolder>{
    private final ArrayList<QR_DataList_MODEL> mQRScannedList;

    // RecyclerView recyclerView;
    public ScannedQRListAdapter(ArrayList<QR_DataList_MODEL> listdata) {
        this.mQRScannedList = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QR_DataList_MODEL mList = mQRScannedList.get(position);
//        holder.textViewId.setText("ID : "+mList.getId());
        holder.textViewSourceId.setText("Dealer Code : "+mList.getSource_id());
        holder.textViewTime.setText("Date/Time : "+mList.getDatetime());
    }
    @Override
    public int getItemCount() {
        return mQRScannedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewId,textViewSourceId,textViewTime;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewId = (TextView) itemView.findViewById(R.id.textViewId);
            this.textViewSourceId = (TextView) itemView.findViewById(R.id.textViewSourceId);
            this.textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }
}
