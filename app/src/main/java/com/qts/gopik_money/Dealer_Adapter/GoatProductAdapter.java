package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.GOAT_ProductDetails_DEALER_Activity;
import com.qts.gopik_money.Model.ProductsListModel;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class GoatProductAdapter extends RecyclerView.Adapter<GoatProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductsListModel> mProductListModelArrayList;

    public GoatProductAdapter(Context context, ArrayList<ProductsListModel> mProductListModelArrayList) {
        this.context = context;
        this.mProductListModelArrayList = mProductListModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goat_products_items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsListModel mProductsListModel = mProductListModelArrayList.get(position);
        holder.textview.setText(mProductsListModel.getProd_name());
        Glide.with(context).load(mProductsListModel.getProd_img_url()).placeholder(R.drawable.goat).into(holder.imageview);
        holder.prodname.setText(mProductsListModel.getProd_brand());
        holder.mTxtPrice.setText("₹ " + mProductsListModel.getProd_mrp());
        holder.itemView.setOnClickListener(view -> {
            SharedPref.saveStringInSharedPref(AppConstants.PROD_BRANDD, mProductsListModel.getProd_brand(), context);
            SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_IDD, mProductsListModel.getId(), context);
            SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPEE, mProductsListModel.getProd_type(), context);
            Intent it = new Intent(context, GOAT_ProductDetails_DEALER_Activity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return mProductListModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview;
        TextView  prodname;
        TextView  mTxtPrice;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imgGoatImage);
            mTxtPrice = itemView.findViewById(R.id.txtPrice);
            textview = itemView.findViewById(R.id.boysname);
            linear = itemView.findViewById(R.id.linear);
            prodname = itemView.findViewById(R.id.prodname);

        }
    }
}
