package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.Draft_Activity;
import com.qts.gopik_money.Model.GoatListResponseModel;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class Goat_Top_Three_Application_List_DEALER_Adapter extends RecyclerView.Adapter<Goat_Top_Three_Application_List_DEALER_Adapter.ViewHolder> {

    Context context;
    ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList;

    public Goat_Top_Three_Application_List_DEALER_Adapter(Context context, ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList) {
        this.context = context;
        this.mGoatListResponseModelArrayList = mGoatListResponseModelArrayList;
    }

    @NonNull
    @Override
    public Goat_Top_Three_Application_List_DEALER_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topthree_applicationlist_dealer, parent, false);
        return new Goat_Top_Three_Application_List_DEALER_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Goat_Top_Three_Application_List_DEALER_Adapter.ViewHolder holder, int position) {
        GoatListResponseModel mGoatListResponseModel = mGoatListResponseModelArrayList.get(position);
        holder.txt_oderid.setText(mGoatListResponseModel.getCustName());
        holder.txt_stuts.setText(mGoatListResponseModel.getAppStatus());

        if (mGoatListResponseModel.getAppStatus().equals("IN_REVIEW")) {
            holder.txt_stuts.setBackgroundResource(R.drawable.approve);
        } else if (mGoatListResponseModel.getAppStatus().equals("Rejected")) {
            holder.txt_stuts.setBackgroundResource(R.drawable.rejecteded);
        } else if (mGoatListResponseModel.getAppStatus().equals("Approved")) {
            holder.txt_stuts.setBackgroundResource(R.drawable.button_round);
            holder.txt_stuts.setOnClickListener(v -> {
                Intent it = new Intent(context, Draft_Activity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            });

        }


    }

    @Override
    public int getItemCount() {
        return mGoatListResponseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_oderid ;
        TextView txt_dateandstatus;
        TextView txt_address;
        TextView txt_stuts;
        TextView loan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_oderid = (TextView) itemView.findViewById(R.id.txt_custname);
            txt_dateandstatus = (TextView) itemView.findViewById(R.id.applicationno);
            txt_address = (TextView) itemView.findViewById(R.id.applicationstatus);
            loan = (TextView) itemView.findViewById(R.id.loanamount);
            txt_stuts = (TextView) itemView.findViewById(R.id.txt_stuts);


        }
    }
}