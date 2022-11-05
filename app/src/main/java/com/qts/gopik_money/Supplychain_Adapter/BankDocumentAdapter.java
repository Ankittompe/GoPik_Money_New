package com.qts.gopik_money.Supplychain_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.Pojo.BankDocumentAddItem;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.BankRemoveListener;
import com.qts.gopik_money.Utils.BankDocumentAddListener;
import com.qts.gopik_money.Utils.SetImageListener;

import java.util.ArrayList;

public class BankDocumentAdapter extends RecyclerView.Adapter<BankDocumentAdapter.ViewHolder> {
    Bitmap bankimage;
    Context context;
    ArrayList<BankDocumentAddItem> mBankDocumentList;
    BankDocumentAddListener bankDocumentAddListener;
    BankRemoveListener bankRemoveListener;
    BankDocumentAddItem bankDocumentAddItem;
    SetImageListener setImageListener;

    public BankDocumentAdapter(Context context, ArrayList<BankDocumentAddItem> bankDocumentList, BankDocumentAddListener bankDocumentAddListener, BankRemoveListener bankRemoveListener, SetImageListener setImageListener) {
        this.context = context;
        this.mBankDocumentList = bankDocumentList;
        this.bankDocumentAddListener = bankDocumentAddListener;
        this.bankRemoveListener = bankRemoveListener;
        this.setImageListener = setImageListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_document_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        int position = holder.getAdapterPosition();
        bankDocumentAddItem = mBankDocumentList.get(position);

        if (position > 0) {
            holder.mRemoveDocButton.setVisibility(View.VISIBLE);
        }

        if (bankDocumentAddItem.getBankdocument1() != null){

            Glide.with(context).load(bankDocumentAddItem.getBankdocument1()).into(holder.mBankButton);
            holder.mBankPdfName.setVisibility(View.GONE);
            Log.e("Pdf","*****"+bankDocumentAddItem.getBankdocument1());
        }else if(bankDocumentAddItem.getPdffile() != null&&bankDocumentAddItem.getPdfname()!=null){
            holder.mBankPdfName.setVisibility(View.VISIBLE);
            holder.mBankPdfName.setText(bankDocumentAddItem.getPdfname());
            holder.mBankButton.setImageResource(R.drawable.c3);
            holder.mBankButton.getLayoutParams().height = 150;
            holder.mBankButton.getLayoutParams().width = 150;
            holder.mBankButton.setScaleType(ImageView.ScaleType.FIT_XY);
            Log.e("Pdf","*****"+bankDocumentAddItem.getPdffile()+bankDocumentAddItem.getPdfname());
        }else {
           
            holder.mBankButton.setImageResource(R.drawable.camera);
            holder.mBankButton.getLayoutParams().height = 150;
            holder.mBankButton.getLayoutParams().width = 150;
            holder.mBankButton.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        holder.mBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageListener.setImageOnClick(position);

            }
        });

        holder.mRemoveDocButton.setOnClickListener(v -> {
            bankRemoveListener.onRemoveClick(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mBankDocumentList.size());
        });

    }

    @Override
    public int getItemCount() {
        return mBankDocumentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton mRemoveDocButton;
        ImageView mBankButton;
        TextView mBankPdfName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRemoveDocButton = itemView.findViewById(R.id.removee_doc_button);
            mBankButton = itemView.findViewById(R.id.bank_button);
            mBankPdfName = itemView.findViewById(R.id.bankpdf_name);

        }
    }
}
