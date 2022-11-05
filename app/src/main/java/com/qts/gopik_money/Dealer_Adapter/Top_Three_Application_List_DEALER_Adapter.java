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
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class Top_Three_Application_List_DEALER_Adapter extends RecyclerView.Adapter<Top_Three_Application_List_DEALER_Adapter.ViewHolder> {
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
    Context context;
    Integer pos;
    public Top_Three_Application_List_DEALER_Adapter(Context context,   ArrayList<String> firstname,  ArrayList<String> lastname, ArrayList<String> cust_code, ArrayList<String> hubbleid
            , ArrayList<String> model, ArrayList<String> applicationstatus, ArrayList<String> loanamount
            , ArrayList<String> submittedon, ArrayList<String> updatedon, ArrayList<String> appstatus )
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


    }
    @NonNull
    @Override
    public Top_Three_Application_List_DEALER_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topthree_applicationlist_dealer, parent, false);
        return new Top_Three_Application_List_DEALER_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top_Three_Application_List_DEALER_Adapter.ViewHolder holder, int position) {
        holder.txt_oderid.setText(firstname.get(position)+" "+lastname.get(position));
        holder.txt_stuts.setText(appstatus.get(position));

        if( appstatus.get(position).equals("IN_REVIEW")){
            holder. txt_stuts.setBackgroundResource(R.drawable.approve);
        }
        else if( appstatus.get(position).equals("Rejected")){
            holder.txt_stuts.setBackgroundResource(R.drawable.rejecteded);
        }

        else if( appstatus.get(position).equals("Approved")){
            holder.txt_stuts.setBackgroundResource(R.drawable.button_round);
            holder.txt_stuts.setOnClickListener(v -> {
                Intent it=new Intent(context, Draft_Activity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            });

        }



    }

    @Override
    public int getItemCount() {
        return firstname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_oderid;
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