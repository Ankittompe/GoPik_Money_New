package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.LogIn;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.CashCalculator;
import com.qts.gopik_money.Dealer_Activity.Emi_Calculator_Hero;
import com.qts.gopik_money.Dealer_Activity.Goat_Emi_Calculator;
import com.qts.gopik_money.Model.ProductLIstDataModel;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class GoatProductDetailsAdapter extends RecyclerView.Adapter<GoatProductDetailsAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductLIstDataModel> mProductLIstDataModelArrayList;

    public GoatProductDetailsAdapter(Context context, ArrayList<ProductLIstDataModel> mProductLIstDataModelArrayList) {
        this.context = context;
        this.mProductLIstDataModelArrayList = mProductLIstDataModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goat_product_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductLIstDataModel mProductLIstDataModel = mProductLIstDataModelArrayList.get(position);
        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_PRICE, mProductLIstDataModel.getProd_mrp(), context);
        holder.textview.setText(mProductLIstDataModel.getProd_name());
        holder.brandname.setText(mProductLIstDataModel.getProd_brand());
        holder.customerprice.setText("₹" + mProductLIstDataModel.getProd_mrp());
        holder.productdescription.setText(mProductLIstDataModel.getProd_desc());
        Glide.with(context).load(mProductLIstDataModel.getProd_img_url()).placeholder(R.drawable.gop).into(holder.imageview);
        holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (mProductLIstDataModel.getFinance_type().equals("Both")) {
            holder.proceed_emi.setVisibility(View.VISIBLE);
//            holder.proceed_cash.setVisibility(View.VISIBLE);
        } else if (mProductLIstDataModel.getFinance_type().equals("Finance")) {
            holder.proceed_emi.setVisibility(View.VISIBLE);
        } else {
            holder.proceed_cash.setVisibility(View.VISIBLE);
        }

        holder.proceed_emi.setOnClickListener(view -> {
            SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_MODEL_NAME, mProductLIstDataModel.getProd_cat(), context);

            SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_ID, mProductLIstDataModel.getId(), context);
            SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPE, mProductLIstDataModel.getProd_type(), context);
            boolean var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, context);

            if (!var) {
                Intent it = new Intent(context, LogIn.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            } else {
                if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, context).equals("Hero")) {
                    Intent it = new Intent(context, Emi_Calculator_Hero.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                } else {
                    Intent it = new Intent(context, Goat_Emi_Calculator.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                }
            }
        });

        holder.proceed_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_MODEL_NAME, mProductLIstDataModel.getProd_cat(), context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_ID, mProductLIstDataModel.getId(), context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPE, mProductLIstDataModel.getProd_type(), context);
                boolean var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, context);

                if (!var) {
                    Intent it = new Intent(context, LogIn.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);

                } else {
                    Intent it = new Intent(context, CashCalculator.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductLIstDataModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview, brandname;
        LinearLayout linear;
        EditText pincode;
        Button proceed_cash, proceed_emi, both, checkpincode;
        TextView customerprice, productdescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.boys);
            textview = itemView.findViewById(R.id.boysname);
            brandname = itemView.findViewById(R.id.brandname);
            linear = itemView.findViewById(R.id.linear);
            customerprice = itemView.findViewById(R.id.customerprice);
            productdescription = itemView.findViewById(R.id.productdescription);
            proceed_emi = itemView.findViewById(R.id.proceed_emi);
            proceed_cash = itemView.findViewById(R.id.proceed_cash);
            both = itemView.findViewById(R.id.both);
        }
    }
}

