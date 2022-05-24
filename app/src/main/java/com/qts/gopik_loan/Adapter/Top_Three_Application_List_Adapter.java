package com.qts.gopik_loan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class Top_Three_Application_List_Adapter extends RecyclerView.Adapter<Top_Three_Application_List_Adapter.ViewHolder> {

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> user_code = new ArrayList<>();
    ArrayList<String> customer_code = new ArrayList<>();
    ArrayList<String> customer_name = new ArrayList<>();
    ArrayList<String> customer_mobile = new ArrayList<>();
    ArrayList<String> customer_email = new ArrayList<>();
    ArrayList<String> customer_address = new ArrayList<>();
    ArrayList<String> customer_dob = new ArrayList<>();
    ArrayList<String> loan_type = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> date_time = new ArrayList<>();
    ArrayList<String> application_status = new ArrayList<>();
    ArrayList<String> cust_tc_response = new ArrayList<>();

    Context context;
    Integer pos;
    public Top_Three_Application_List_Adapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> customer_code, ArrayList<String> customer_name
            , ArrayList<String> customer_mobile, ArrayList<String> customer_email, ArrayList<String> customer_address
            , ArrayList<String> customer_dob, ArrayList<String> loan_type, ArrayList<String> state,
                                              ArrayList<String> date_time, ArrayList<String> application_status, ArrayList<String> cust_tc_response )
    {
        this.context = context;
        this.id = id;
        this.user_code = user_code;
        this.customer_code = customer_code;
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.customer_address = customer_address;
        this.customer_dob = customer_dob;
        this.loan_type = loan_type;
        this.state = state;
        this.date_time = date_time;
        this.application_status = application_status;
        this.cust_tc_response = cust_tc_response;


    }
    @NonNull
    @Override
    public Top_Three_Application_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topthree, parent, false);
        return new Top_Three_Application_List_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top_Three_Application_List_Adapter.ViewHolder holder, int position) {
        Log.e("dhvgdjkbvgn","dkhjdvg");
        holder.txt_oderid.setText(customer_name.get(position));
        holder.txt_dateandstatus.setText(application_status.get(position));




    }

    @Override
    public int getItemCount() {
        return application_status.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_oderid,txt_dateandstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_oderid = (TextView) itemView.findViewById(R.id.name);
            txt_dateandstatus = (TextView) itemView.findViewById(R.id.status);



        }
    }
}


