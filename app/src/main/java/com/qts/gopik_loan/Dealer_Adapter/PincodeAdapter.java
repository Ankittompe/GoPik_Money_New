package com.qts.gopik_loan.Dealer_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PincodeAdapter extends RecyclerView.Adapter<PincodeAdapter.ViewHolder> {
    ArrayList<String> pincode_name = new ArrayList<>();
    ArrayList<String> pincode_address = new ArrayList<>();

    Context context;
    Integer pos;

    public PincodeAdapter(Context context, ArrayList<String> pincode_name, ArrayList<String> pincode_address) {
        this.context = context;
        this.pincode_name = pincode_name;
        this.pincode_address = pincode_address;


    }

    @NonNull
    @Override
    public PincodeAdapter. ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pincode_list, parent, false);
        return new PincodeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e(TAG, "City Payload Size: " +pincode_name.get(position));
        holder.name.setText(pincode_name.get(position));
        holder.address.setText(pincode_address.get(position));
    }

    @Override
    public int getItemCount() {
        return pincode_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name,address;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            linear = itemView.findViewById(R.id.linear);

        }
    }
}


