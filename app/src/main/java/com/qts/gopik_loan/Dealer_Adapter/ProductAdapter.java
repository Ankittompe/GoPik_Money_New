package com.qts.gopik_loan.Dealer_Adapter;

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
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.ProductDetails_DEALER;
import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class ProductAdapter   extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<String> catagoryimage=new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> product_type = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> product_brand = new ArrayList<>();
    Context context;
    Integer pos;
    public ProductAdapter(Context context, ArrayList<String> catagoryimage, ArrayList<String> catagoryname,
                          ArrayList<String> product_type,ArrayList<String> product_id,ArrayList<String> product_brand) {
        this.context = context;
        this.catagoryimage = catagoryimage;
        this.catagoryname = catagoryname;
        this.product_type = product_type;
        this.product_id = product_id;
        this.product_brand = product_brand;


    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitems, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textview.setText(catagoryname.get(position));


        Glide.with(context)
                .load(catagoryimage.get(position))
                .into(holder.imageview);

        holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.prodname.setText(product_brand.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.PROD_BRANDD,product_brand.get(position),context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_IDD,product_id.get(position),context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPEE,product_type.get(position),context);
                Intent it=new Intent(context, ProductDetails_DEALER.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
               /* parentActivity.finish();
                context.startActivity(it);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return catagoryimage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview,prodname;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.boys);
            textview = itemView.findViewById(R.id.boysname);
            linear = itemView.findViewById(R.id.linear);
            prodname= itemView.findViewById(R.id.prodname);

        }
    }
}
