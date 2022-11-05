package com.qts.gopik_money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;

import java.util.ArrayList;

public class Application_Adapter extends RecyclerView.Adapter<Application_Adapter.ViewHolder> {
    String mLineSeparator = "line.separator";
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

    ArrayList<String> loan_amt_approve = new ArrayList<>();
    ArrayList<String> emi_amt = new ArrayList<>();
    ArrayList<String> tenure = new ArrayList<>();
    ArrayList<String> rate_of_interest = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();
    ArrayList<String> reason_of_rejection = new ArrayList<>();
    ArrayList<String> info_img = new ArrayList<>();


    Context context;


    public Application_Adapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> customer_code, ArrayList<String> customer_name
            , ArrayList<String> customer_mobile, ArrayList<String> customer_email, ArrayList<String> customer_address
            , ArrayList<String> customer_dob, ArrayList<String> loan_type, ArrayList<String> state,
                               ArrayList<String> date_time, ArrayList<String> application_status, ArrayList<String> cust_tc_response,
                               ArrayList<String> loan_amt_approve, ArrayList<String> emi_amt,
                               ArrayList<String> tenure, ArrayList<String> rate_of_interest,
                               ArrayList<String> comments, ArrayList<String> reason_of_rejection, ArrayList<String> info_img) {
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
        this.loan_amt_approve = loan_amt_approve;
        this.emi_amt = emi_amt;
        this.tenure = tenure;
        this.rate_of_interest = rate_of_interest;
        this.comments = comments;
        this.reason_of_rejection = reason_of_rejection;
        this.info_img = info_img;


    }

    @NonNull
    @Override
    public Application_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicationalllist, parent, false);
        return new Application_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Application_Adapter.ViewHolder holder, int position) {
        holder.txt_custname.setText(customer_name.get(position));
        holder.applicationno.setText(customer_code.get(position));
        holder.number.setText(customer_mobile.get(position));
        holder.loanamount.setText(loan_type.get(position));
        holder.status.setText(application_status.get(position));


        if (application_status.get(position).equals("Submitted")) {
            holder.status.setBackgroundResource(R.drawable.button_green);
        } else if (application_status.get(position).equals("Declined")) {
            holder.status.setBackgroundResource(R.drawable.button_red);
        } else if (application_status.get(position).equals("Approved")) {
            holder.status.setBackgroundResource(R.drawable.button_round);
        } else if (application_status.get(position).equals("DRAFT")) {
            holder.status.setBackgroundResource(R.drawable.draft);
        }
          holder.tooltip.setOnClickListener(v -> holder.tooltip.setVisibility(View.GONE));
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
        holder.tooltipimg.setOnClickListener(view -> {
            holder.tooltip.setVisibility(View.VISIBLE);

            if (application_status.get(position).equals("Submitted")) {
                holder.tv_tooltipdata.setText("Application Submitted");

            }
            else if (application_status.get(position).equals("Approved")) {
                holder.tv_tooltipdata.setText("Loan Amount: "+loan_amt_approve.get(position) +
                        System.getProperty(mLineSeparator) +
                       "Emi Amount: "+ emi_amt.get(position) +
                        System.getProperty(mLineSeparator) +
                       "Tenure: "+ tenure.get(position) +
                        System.getProperty(mLineSeparator) +
                       "Rate Of Interest: " + rate_of_interest.get(position) +
                        System.getProperty(mLineSeparator) +
                        "Comments: "+comments.get(position));
            }
            else if (application_status.get(position).equals("DRAFT")) {

                holder.tv_tooltipdata.setText("Application status id DRAFT.");
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_custname;
        TextView applicationno;
        TextView number;
        TextView loanamount;
        TextView status;
        TextView tv_tooltipdata;
        RelativeLayout tooltip;
        ImageView tooltipimg;
        ImageView dowmarrow;
        ImageView uparrow;
        LinearLayout secoondlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_custname = (TextView) itemView.findViewById(R.id.txt_custname);
            applicationno = (TextView) itemView.findViewById(R.id.applicationno);
            number = (TextView) itemView.findViewById(R.id.txt_custname);
            loanamount = (TextView) itemView.findViewById(R.id.loanamount);
            status = (TextView) itemView.findViewById(R.id.status);
            tooltip = (RelativeLayout) itemView.findViewById(R.id.iv_tooltip);
            tv_tooltipdata = (TextView) itemView.findViewById(R.id.tv_tooltipdata);
            tooltipimg = (ImageView) itemView.findViewById(R.id.tooltipimg);
            uparrow=(ImageView)itemView.findViewById(R.id.uparrow);
            dowmarrow=(ImageView)itemView.findViewById(R.id.dowmarrow);
            secoondlayout=(LinearLayout)itemView.findViewById(R.id.secoondlayout);
        }
    }
}
