package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class Product_Cat_Adapter extends RecyclerView.Adapter<Product_Cat_Adapter.ViewHolder> {
    Context context;
    ArrayList<String> product_cat = new ArrayList<>();

    public Product_Cat_Adapter(Context context, ArrayList<String> product_cat) {

        this.context = context;
        this.product_cat = product_cat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cat_itemview, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



    }



    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_imageview;
        CardView cardView13;
        TextView cat_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_imageview = (ImageView) itemView.findViewById(R.id.cat_imageview);
            cardView13 = (CardView) itemView.findViewById(R.id.cardView13);
            cat_name = (TextView) itemView.findViewById(R.id.cat_name);

        }
    }
}
