package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;

import java.util.ArrayList;

public class WalletHistory_DEALER_Adapter extends RecyclerView.Adapter<WalletHistory_DEALER_Adapter.ViewHolder> {

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

    String TAG = "hi";
    Context context;

    public WalletHistory_DEALER_Adapter(Context context, ArrayList<String> id, ArrayList<String> user_code, ArrayList<String> wallet_type
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
    public WalletHistory_DEALER_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallethistorydetails, parent, false);

        return new WalletHistory_DEALER_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistory_DEALER_Adapter.ViewHolder holder, int position) {


        holder.referencenouser.setText(user_code.get(position));
        holder.transactiontypeuser.setText(trans_type.get(position));
        holder.amountuser.setText(txn_amt.get(position));
        Log.e("anhii", "anhuiii");
        holder.dateandtimeuser.setText(txn_tmstmp.get(position));
        holder.remark.setText(remarks.get(position));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView referencenouser;
        TextView  transactiontypeuser;
        TextView  amountuser;
        TextView  dateandtimeuser;
        TextView remark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            referencenouser = (TextView) itemView.findViewById(R.id.referencenouser);
            transactiontypeuser = (TextView) itemView.findViewById(R.id.transactiontypeuser);
            amountuser = (TextView) itemView.findViewById(R.id.amountuser);
            dateandtimeuser = (TextView) itemView.findViewById(R.id.dateandtimeuser);
            remark = (TextView) itemView.findViewById(R.id.remark);

        }
    }
}
