package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class Application_status_DEALER_Adapter extends RecyclerView.Adapter<Application_status_DEALER_Adapter.ViewHolder> {
    ArrayList<String> firstname = new ArrayList<>();
    ArrayList<String> lastname = new ArrayList<>();
    ArrayList<String> cust_code = new ArrayList<>();
    ArrayList<String> hubbleid = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();
    ArrayList<String> applicationstatus = new ArrayList<>();
    ArrayList<String> loanamount = new ArrayList<>();
    ArrayList<String> submittedon = new ArrayList<>();
    ArrayList<String> updatedon = new ArrayList<>();
    ArrayList<String> appstatus = new ArrayList<>();
    ArrayList<String> appstarttimestamp = new ArrayList<>();
    ArrayList<String> appendtimestp = new ArrayList<>();
    Context context;
    Integer pos;
    public Application_status_DEALER_Adapter(Context context,   ArrayList<String> firstname,  ArrayList<String> lastname, ArrayList<String> cust_code, ArrayList<String> hubbleid
            , ArrayList<String> model, ArrayList<String> applicationstatus, ArrayList<String> loanamount
            , ArrayList<String> submittedon, ArrayList<String> updatedon, ArrayList<String> appstatus,
                                      ArrayList<String> appstarttimestamp, ArrayList<String> appendtimestp)
    {
        this.context = context;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cust_code = cust_code;
        this.hubbleid = hubbleid;
        this.model = model;
        this.applicationstatus = applicationstatus;
        this.loanamount = loanamount;
        this.submittedon = submittedon;
        this.updatedon = updatedon;
        this.appstatus = appstatus;
        this.appstarttimestamp = appstarttimestamp;
        this.appendtimestp = appendtimestp;



    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_details, parent, false);
        return new Application_status_DEALER_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.customername.setText(firstname.get(position)+" "+lastname.get(position));
        holder.applicationno.setText(cust_code.get(position));
        holder.hubbleid.setText(cust_code.get(position));
        /* holder.txt_address.setText(applicationstatus.get(position));*/
        holder.loanamount.setText(loanamount.get(position));
        holder.modelapplication.setText(model.get(position));
        holder.submittedon.setText(appstarttimestamp.get(position));
        holder.updatedon.setText( appendtimestp.get(position));
        holder.txt_stuts.setText(appstatus.get(position));
        holder. dowmarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.uparrow.setVisibility(View.VISIBLE);
                holder.secoondlayout.setVisibility(View.VISIBLE);
                holder.dowmarrow.setVisibility(View.GONE);
            }
        });
        holder. uparrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.uparrow.setVisibility(View.GONE);
                holder.secoondlayout.setVisibility(View.GONE);
                holder.dowmarrow.setVisibility(View.VISIBLE);
            }
        });

        if( appstatus.get(position).equals("IN_REVIEW")){
            holder. txt_stuts.setBackgroundResource(R.drawable.approve);
        }
        else if( appstatus.get(position).equals("Rejected")){
            holder.txt_stuts.setBackgroundResource(R.drawable.rejected);
        }

        else if( appstatus.get(position).equals("Approved")){
            holder.txt_stuts.setBackgroundResource(R.drawable.button_round);
        }


    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customername,applicationno,hubbleid,loanamount,modelapplication,submittedon,updatedon,txt_stuts;
        LinearLayout secoondlayout;
        ImageView dowmarrow,uparrow;
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
