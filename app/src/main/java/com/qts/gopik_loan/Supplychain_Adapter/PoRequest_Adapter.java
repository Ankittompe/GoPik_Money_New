package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class PoRequest_Adapter extends RecyclerView.Adapter<PoRequest_Adapter.ViewHolder> {
    Context context;
    ArrayList<String>po_list= new ArrayList<>();
    public PoRequest_Adapter(Context context, ArrayList<String> po_list) {
        this.context = context;
        this.po_list = po_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_list_cardview,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 30;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
