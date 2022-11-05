package com.qts.gopik_money.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.Goat_Products_Dealer_Activity;
import com.qts.gopik_money.Dealer_Activity.Products_DEALER;
import com.qts.gopik_money.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatagoryNameAdapter extends RecyclerView.Adapter<CatagoryNameAdapter.ViewHolder> {
    ArrayList<String> catagoryimage=new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> SUB_CATEGORY_NAME = new ArrayList();
    Context context;



    public CatagoryNameAdapter(Context context, ArrayList<String> catagoryimage, ArrayList<String> catagoryname,
                               ArrayList<String> SUB_CATEGORY_NAME) {
        this.context = context;
        this.catagoryimage = catagoryimage;
        this.catagoryname = catagoryname;
        this.SUB_CATEGORY_NAME = SUB_CATEGORY_NAME;



    }
    @NonNull
    @Override
    public CatagoryNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagoryitemname, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatagoryNameAdapter.ViewHolder holder, final int position) {
        holder.textview.setText(SUB_CATEGORY_NAME.get(position));


        Glide.with(context)
                .load(catagoryimage.get(position))
                .into(holder.imageview);

        holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);


        holder.itemView.setOnClickListener(view -> {


            SharedPref.saveStringInSharedPref(AppConstants.CAT_TYPE,catagoryname.get(position),context);
            SharedPref.saveStringInSharedPref(AppConstants.CAT_NAME,SUB_CATEGORY_NAME.get(position),context);
            String mBrand = SharedPref.getStringFromSharedPref(AppConstants.BRAND, context);
            Intent it;
            if (mBrand.equals("Manikstu")) {
                it = new Intent(context, Goat_Products_Dealer_Activity.class);

            } else {
                it = new Intent(context, Products_DEALER.class);
            }

            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return catagoryimage.size();
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
