package com.qts.gopik_money.Supplychain_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Pojo.OtherDocumentAddItem;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Utils.OtherDocumentAddListener;
import com.qts.gopik_money.Utils.RemoveOtherDocumentListener;

import java.util.ArrayList;

public class OtherDocumentAdapter extends RecyclerView.Adapter<OtherDocumentAdapter.ViewHolder>{
    Context context;
    ArrayList<OtherDocumentAddItem> otherDocAddItemArrayList;
    OtherDocumentAddListener otherDocumentAddListener;
    RemoveOtherDocumentListener removeOtherDocumentListener;
    OtherDocumentAddItem otherDocumentAddItem;
    public OtherDocumentAdapter(Context context, ArrayList<OtherDocumentAddItem> otherDocAddItemArrayList, OtherDocumentAddListener otherDocumentAddListener, RemoveOtherDocumentListener removeOtherDocumentListener) {
        this.context=context;
        this.otherDocAddItemArrayList = otherDocAddItemArrayList;
        this.otherDocumentAddListener = otherDocumentAddListener;
        this.removeOtherDocumentListener = removeOtherDocumentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_document_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherDocumentAdapter.ViewHolder holder, int pos) {
        int position = holder.getAdapterPosition();
        otherDocumentAddItem = otherDocAddItemArrayList.get(position);
        if (position>0){
            holder.removeOtherDocumentButton.setVisibility(View.VISIBLE);
        }

        holder.removeOtherDocumentButton.setOnClickListener(v -> {

            removeOtherDocumentListener.onRemoveClick(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, otherDocAddItemArrayList.size());

        });
    }

    @Override
    public int getItemCount() {
        return otherDocAddItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView otherDocumentButton;
        ImageButton removeOtherDocumentButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherDocumentButton = itemView.findViewById(R.id.other_document_button);
            removeOtherDocumentButton = itemView.findViewById(R.id.remove_other_document_button);
        }
    }
}
