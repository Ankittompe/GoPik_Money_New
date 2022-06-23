package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class Top_Five_Adapter extends RecyclerView.Adapter<Top_Five_Adapter.ViewHolder> {
    Context applicationContext;
    ArrayList<String> Top_five_list = new ArrayList<>();
    ArrayList<String> Top_five_ID = new ArrayList<>();
    public Top_Five_Adapter(Context applicationContext, ArrayList<String> top_five_list, ArrayList<String> top_five_ID) {
        this.applicationContext = applicationContext;
        this.Top_five_list = top_five_list;
        this.Top_five_ID = top_five_ID;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_five_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.name_tv.setText(Top_five_list.get(position));
       holder.serial_tv.setText(Top_five_ID.get(position));
    }


    @Override
    public int getItemCount() {
        return Top_five_ID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serial_tv,name_tv;
        ConstraintLayout cardView_of_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serial_tv = itemView.findViewById(R.id.serial_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            cardView_of_item = itemView.findViewById(R.id.cardView_of_item);



        }
    }
}
