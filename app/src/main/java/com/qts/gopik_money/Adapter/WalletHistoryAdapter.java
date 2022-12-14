package com.qts.gopik_money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;

import java.util.ArrayList;

public class WalletHistoryAdapter  extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    String input;

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> user_code = new ArrayList<>();
    ArrayList<String> wallet_type = new ArrayList<>();
    ArrayList<String> ref_no = new ArrayList<>();
    ArrayList<String> trans_type = new ArrayList<>();
    ArrayList<String> txn_amt = new ArrayList<>();
    ArrayList<String> txn_tmstmp = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> razorpay_payment_id = new ArrayList<>();
    ArrayList<String> remarks = new ArrayList<>();

    Context context;

    public WalletHistoryAdapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> wallet_type
            , ArrayList<String> ref_no, ArrayList<String> trans_type, ArrayList<String> txn_amt
            , ArrayList<String> txn_tmstmp    , ArrayList<String> status,    ArrayList<String> email
            , ArrayList<String> razorpay_payment_id, ArrayList<String> remarks) {

        this.context = context;
        this.id = id;
        this.user_code = user_code;
        this.wallet_type = wallet_type;
        this.ref_no = ref_no;
        this.trans_type = trans_type;
        this.txn_amt = txn_amt;
        this.txn_tmstmp = txn_tmstmp;
        this.status = status;
        this.email = email;
        this.razorpay_payment_id = razorpay_payment_id;
        this.remarks = remarks;


    }

    @NonNull
    @Override
    public WalletHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history, parent, false);
        return new WalletHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryAdapter.ViewHolder holder, int position) {
        holder.amountwallet.setText(txn_amt.get(position));
         input=txn_tmstmp.get(position);
        String result  = input.split(" ")[0];

        holder.timestamp.setText(result);
        if(wallet_type.get(position).equals("Scratch Card")){
            holder.scratchimg.setVisibility(View.VISIBLE);
            holder.loancomision.setVisibility(View.GONE);

        }
        else{
            holder.scratchimg.setVisibility(View.GONE);
            holder.loancomision.setVisibility(View.VISIBLE);
            holder.loancomision.setText("Commission");
        }
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountwallet;
        TextView timestamp;
        TextView loancomision;
        ImageView scratchimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amountwallet = (TextView) itemView.findViewById(R.id.amountwallet);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);

            scratchimg= (ImageView) itemView.findViewById(R.id.scratchimg);
            loancomision= (TextView) itemView.findViewById(R.id.loancomision);
        }
    }
}


