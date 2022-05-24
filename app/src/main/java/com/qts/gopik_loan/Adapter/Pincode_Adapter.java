package com.qts.gopik_loan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class Pincode_Adapter extends RecyclerView.Adapter<Pincode_Adapter.ViewHolder> {
    ArrayList city = new ArrayList();
    ArrayList state = new ArrayList();
    Context context;
    Integer pos;


    public Pincode_Adapter(Context context, ArrayList<String> city, ArrayList<String> state) {
        Log.e("ghfdddssss","fftfy");
        this.context = context;
        this.city = city;
        this.state = state;



    }
    @NonNull
    @Override
    public Pincode_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagoryitemname, parent, false);
        return new Pincode_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pincode_Adapter.ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return city.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.boys);
            textview = itemView.findViewById(R.id.boysname);
            linear = itemView.findViewById(R.id.linear);


        }
    }
}

