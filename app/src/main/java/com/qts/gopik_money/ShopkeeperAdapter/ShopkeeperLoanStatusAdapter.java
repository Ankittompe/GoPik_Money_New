package com.qts.gopik_money.ShopkeeperAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Shopkeeper.ShopkeeperLoanDetails;

import java.util.ArrayList;

public class ShopkeeperLoanStatusAdapter extends RecyclerView.Adapter<ShopkeeperLoanStatusAdapter.ViewHolder> {


    Context context;

    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> disb_amount = new ArrayList<>();
    ArrayList<String> date_of_closer = new ArrayList<>();
    ArrayList<String> loan_id = new ArrayList<>();
    ArrayList<String> disb_date = new ArrayList<>();
    ArrayList<String> tenure = new ArrayList<>();
    ArrayList<String> rateofinterest = new ArrayList<>();
    public ShopkeeperLoanStatusAdapter(Context context, ArrayList<String> po_id,
                             ArrayList<String> disb_amount,
                             ArrayList<String> date_of_closer,
                             ArrayList<String> loan_id,
                             ArrayList<String> disb_date,
                             ArrayList<String> tenure,
                             ArrayList<String> rateofinterest

    ) {

        this.context = context;

        this.po_id = po_id;
        this.disb_amount = disb_amount;
        this.date_of_closer = date_of_closer;
        this.loan_id = loan_id;
        this.disb_date = disb_date;
        this.tenure = tenure;
        this.rateofinterest = rateofinterest;


    }

    @NonNull
    @Override
    public ShopkeeperLoanStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopkeeperloanstatusui, parent, false);
        return new ShopkeeperLoanStatusAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ShopkeeperLoanStatusAdapter.ViewHolder holder, int position) {
        //Used for Comma

   /*     String number1 = disb_amount.get(position);
        double amount = Double.parseDouble(number1);
        DecimalFormat formatter = new DecimalFormat("##,##,###");
        String formatted = formatter.format(amount);*/


        holder.po_id_tv.setText(po_id.get(position));
      holder.disb_amount_tv.setText("₹"+disb_amount.get(position));
        holder.disb_date_tv.setText(disb_date.get(position));
        holder.date_of_closer_tv.setText(date_of_closer.get(position));
        holder.loan_id_tv.setText(loan_id.get(position));
        holder.tenure.setText(tenure.get(position)+" days");
        holder.rateofinterest.setText(rateofinterest.get(position)+" %");

        holder.itemView.setOnClickListener(v -> {
            SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_ID,po_id.get(position),context);
            Intent intent = new Intent(context, ShopkeeperLoanDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {

        return po_id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView po_id_tv;
        TextView disb_amount_tv;
        TextView date_of_closer_tv;
        TextView disb_date_tv;
        TextView loan_id_tv;
        TextView rateofinterest;
        TextView tenure;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            po_id_tv=itemView.findViewById(R.id.po_id_tv);
            disb_amount_tv=itemView.findViewById(R.id.disb_amount_tv);
            date_of_closer_tv=itemView.findViewById(R.id.date_of_closer_tv);
            disb_date_tv=itemView.findViewById(R.id.disb_date_tv);
            loan_id_tv=itemView.findViewById(R.id.loan_id_tv);
            tenure=itemView.findViewById(R.id.tenure);
            rateofinterest=itemView.findViewById(R.id.rateofinterest);


        }
    }
}


