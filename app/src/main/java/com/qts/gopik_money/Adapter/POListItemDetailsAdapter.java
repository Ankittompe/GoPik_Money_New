package com.qts.gopik_money.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Pojo.PayloadItemData;
import com.qts.gopik_money.Pojo.PO_ModifyData;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.ItemPlusClickListener;

import java.util.ArrayList;

public class POListItemDetailsAdapter extends RecyclerView.Adapter<POListItemDetailsAdapter.ViewHolder> {
    private final ArrayList<PayloadItemData> mPayloadItemDataArrayList;
    Context mContext;
    ItemPlusClickListener mItemPlusClickListener;
    int quantity_count = 0;

    public POListItemDetailsAdapter(ArrayList<PayloadItemData> mPayloadItemDataArrayList, ItemPlusClickListener mItemPlusClickListener) {
        this.mPayloadItemDataArrayList = mPayloadItemDataArrayList;
        this.mItemPlusClickListener = mItemPlusClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.po_list_item_details, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PayloadItemData mList = mPayloadItemDataArrayList.get(position);
        if (!mList.getProduct().equals("NA")) {
            holder.mTxtPOName.setText(mList.getProduct());
        } else {
            holder.mTxtPOName.setText("");
        }
        if (mList.getUpdate_quantity().equals("0")) {
            if (!mList.getProdt_quantity().equals("0")) {
                holder.mTxtPoQuantity.setText(mList.getProdt_quantity());
            } else {
                holder.mTxtPoQuantity.setText("0");
            }
        } else {
            if (!mList.getUpdate_quantity().equals("0")) {
                holder.mTxtPoQuantity.setText(mList.getUpdate_quantity());
            } else {
                holder.mTxtPoQuantity.setText("0");
            }
        }

        quantity_count = Integer.parseInt(mList.getProdt_quantity());

        holder.mImgPlusQty.setOnClickListener(view -> {
            quantity_count = quantity_count + 1;
            holder.mTxtPoQuantity.setText(quantity_count + "");
            mItemPlusClickListener.onClick(position, new PO_ModifyData(mList.getId(), "" + quantity_count));
        });
        holder.mImgMinQty.setOnClickListener(view -> {
            if (quantity_count != 0) {
                quantity_count = quantity_count - 1;
                holder.mTxtPoQuantity.setText(quantity_count + "");
                mItemPlusClickListener.onClick(position, new PO_ModifyData(mList.getId(), "" + quantity_count));
            } else {
                holder.mTxtPoQuantity.setText("0");
                mItemPlusClickListener.onClick(position, new PO_ModifyData(mList.getId(), "0"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPayloadItemDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtPOName;
        public TextView mTxtPoQuantity;
        ImageView mImgMinQty;
        ImageView mImgPlusQty;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTxtPoQuantity = itemView.findViewById(R.id.txtPoQuantity);
            this.mTxtPOName = itemView.findViewById(R.id.txtPOName);
            this.mImgMinQty = itemView.findViewById(R.id.imgMinQty);
            this.mImgPlusQty = itemView.findViewById(R.id.imgPlusQty);
        }
    }
}
