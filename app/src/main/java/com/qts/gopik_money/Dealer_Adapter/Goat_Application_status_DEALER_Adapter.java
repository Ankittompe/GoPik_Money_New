package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Model.GoatListResponseModel;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class Goat_Application_status_DEALER_Adapter extends RecyclerView.Adapter<Goat_Application_status_DEALER_Adapter.ViewHolder> {
    Context context;
    ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList;

    public Goat_Application_status_DEALER_Adapter(Context context, ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList) {
        this.context = context;
        this.mGoatListResponseModelArrayList = mGoatListResponseModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_details, parent, false);
        return new Goat_Application_status_DEALER_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoatListResponseModel mGoatListResponseModel = mGoatListResponseModelArrayList.get(position);
        holder.customername.setText(mGoatListResponseModel.getCustName());
        holder.applicationno.setText(mGoatListResponseModel.getApplictnCode());
//        holder.hubbleid.setText(cust_code.get(position));
        /* holder.txt_address.setText(applicationstatus.get(position));*/
        holder.loanamount.setText(mGoatListResponseModel.getLoanAmount());
        holder.modelapplication.setText(mGoatListResponseModel.getProductName());
        holder.submittedon.setText(mGoatListResponseModel.getApplyTime());
        holder.updatedon.setText( mGoatListResponseModel.getUpdatedAt());
        holder.txt_stuts.setText(mGoatListResponseModel.getAppStatus());
        holder. dowmarrow.setOnClickListener(view -> {
            holder.uparrow.setVisibility(View.VISIBLE);
            holder.secoondlayout.setVisibility(View.VISIBLE);
            holder.dowmarrow.setVisibility(View.GONE);
        });
        holder. uparrow.setOnClickListener(view -> {
            holder.uparrow.setVisibility(View.GONE);
            holder.secoondlayout.setVisibility(View.GONE);
            holder.dowmarrow.setVisibility(View.VISIBLE);
        });

        if( mGoatListResponseModel.getAppStatus().equals("IN_REVIEW")){
            holder. txt_stuts.setBackgroundResource(R.drawable.approve);
        }
        else if( mGoatListResponseModel.getAppStatus().equals("Rejected")){
            holder.txt_stuts.setBackgroundResource(R.drawable.rejecteded);
        }

        else if( mGoatListResponseModel.getAppStatus().equals("Approved")){
            holder.txt_stuts.setBackgroundResource(R.drawable.button_round);
        }


    }

    @Override
    public int getItemCount() {
        return mGoatListResponseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customername;
        TextView applicationno;
        TextView hubbleid;
        TextView loanamount;
        TextView modelapplication;
        TextView submittedon;
        TextView updatedon;
        TextView txt_stuts;
        LinearLayout secoondlayout;
        ImageView dowmarrow;
        ImageView uparrow;
        /* txt_oderid,txt_dateandstatus,txt_pricetotla,txt_address,txt_stuts,loan,appliedmodel,submittedon,updatedom;*/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customername = (TextView) itemView.findViewById(R.id.customername);
            applicationno = (TextView) itemView.findViewById(R.id.applicationno);
            hubbleid = (TextView) itemView.findViewById(R.id.hubbleid);
            loanamount = (TextView) itemView.findViewById(R.id.loanamount);
            modelapplication = (TextView) itemView.findViewById(R.id.modelapplication);
            submittedon = (TextView) itemView.findViewById(R.id.submittedon);
            updatedon = (TextView) itemView.findViewById(R.id.updatedon);
            txt_stuts = (TextView) itemView.findViewById(R.id.txt_stuts);
            secoondlayout=(LinearLayout)itemView.findViewById(R.id.secoondlayout);
            uparrow=(ImageView)itemView.findViewById(R.id.uparrow);
            dowmarrow=(ImageView)itemView.findViewById(R.id.dowmarrow);

        }
    }
}
