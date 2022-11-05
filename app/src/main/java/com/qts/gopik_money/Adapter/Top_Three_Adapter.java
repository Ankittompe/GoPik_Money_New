package com.qts.gopik_money.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.Draft_Activity;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class Top_Three_Adapter extends RecyclerView.Adapter<Top_Three_Adapter.ViewHolder> {

    ArrayList<String> id ;
    ArrayList<String> user_code ;
    ArrayList<String> customer_code ;
    ArrayList<String> customer_name ;
    ArrayList<String> customer_mobile ;
    ArrayList<String> customer_email ;
    ArrayList<String> customer_address ;
    ArrayList<String> customer_dob ;
    ArrayList<String> loan_type;
    ArrayList<String> state;
    ArrayList<String> date_time ;
    ArrayList<String> application_status;
    ArrayList<String> cust_tc_response ;

    Context context;

    public Top_Three_Adapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> customer_code, ArrayList<String> customer_name
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
    public Top_Three_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topthree, parent, false);
        return new Top_Three_Adapter.ViewHolder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Top_Three_Adapter.ViewHolder holder,   int position) {

        holder.idcustomer.setText(id.get(position));
        holder.n.setText(customer_name.get(position));
        holder.s.setText(application_status.get(position));

        if(application_status.get(position).equals("Submitted")){
            holder.s.setBackgroundResource(R.drawable.button_green);
            holder.coins.setVisibility(View.GONE);
        }
       else if(application_status.get(position).equals("Declined")){
            holder.s.setBackgroundResource(R.drawable.button_red);
            holder.coins.setVisibility(View.GONE);
        }

        else if(application_status.get(position).equals("Approved")){
            holder.s.setBackgroundResource(R.drawable.button_round);
            holder.coins.setVisibility(View.GONE);

        }
        else if(application_status.get(position).equals("Disbursed")){
            holder.s.setBackgroundResource(R.drawable.button_round);
            holder.coins.setVisibility(View.VISIBLE);
        }
        else if(application_status.get(position).equals("DRAFT")){
            holder.s.setBackgroundResource(R.drawable.draft);
            holder.coins.setVisibility(View.GONE);
            holder.s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(context, Draft_Activity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    it.putExtra("key",customer_code.get(position));
                    context.startActivity(it);
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idcustomer;
        TextView n;
        TextView s;
        ImageView coins;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idcustomer=itemView.findViewById(R.id.idname);
            n =  itemView.findViewById(R.id.n);
            s =itemView.findViewById(R.id.s);
            coins=itemView.findViewById(R.id.coins);


        }
    }
}



