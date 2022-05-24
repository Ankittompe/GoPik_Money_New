package com.qts.gopik_loan.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.qts.gopik_loan.Activity.AppConstants;

import com.qts.gopik_loan.Activity.Property_Loan;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class BrandCategory_Adapter extends RecyclerView.Adapter<BrandCategory_Adapter.ViewHolder> {
    ArrayList<String> cat_name = new ArrayList<>();
    ArrayList<String> img_url = new ArrayList<>();
    Context context;
    Integer pos;


    public BrandCategory_Adapter(Context context, ArrayList<String> cat_name, ArrayList<String> img_url) {
        Log.e("ghfdddssss", "fftfy");
        this.context = context;
        this.cat_name = cat_name;
        this.img_url = img_url;


    }

    @NonNull
    @Override
    public BrandCategory_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_category, parent, false);
        return new BrandCategory_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandCategory_Adapter.ViewHolder holder, final int position) {
        holder.mortgage.setText(cat_name.get(position));

        Log.e("ghfdddssss", "fftggggggggggggfy");
        Glide.with(context)
                .load(img_url.get(position))
                .into(holder.homeloan1);

        holder.homeloan1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE, cat_name.get(position), context);
        holder.mortgagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent it = new Intent(context, Property_Loan.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cat_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeloan1;
        TextView mortgage;
        LinearLayout mortgagelayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeloan1 = itemView.findViewById(R.id.homeloan1);
            mortgage = itemView.findViewById(R.id.mortgage);
            mortgagelayout = itemView.findViewById(R.id.mortgagelayout);

        }
    }
}

