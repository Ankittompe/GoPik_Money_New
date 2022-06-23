package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class All_product_list_Adapter extends RecyclerView.Adapter<All_product_list_Adapter.ViewHolder> {
    Context context;
    ArrayList<String> All_product_list = new ArrayList<>();
    ArrayList<String> All_product_id = new ArrayList<>();

    public All_product_list_Adapter(Context applicationContext, ArrayList<String> all_product_list, ArrayList<String> all_product_id) {
        this.context = applicationContext;
        this.All_product_list = all_product_list;
        this.All_product_id = all_product_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_list_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_tv.setText(All_product_list.get(position));
        holder.serial_tv.setText(All_product_id.get(position));
    }

    @Override
    public int getItemCount() {
        return All_product_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serial_tv;
        TextView name_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serial_tv = itemView.findViewById(R.id.serial_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }
}
