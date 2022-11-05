package com.qts.gopik_money.Dealer_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.R;

import java.util.ArrayList;

public class BannerAdapter  extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> banners = new ArrayList<>();
    ArrayList<String> namebanner = new ArrayList<>();

    Context context;
    Integer pos;
    public BannerAdapter(Context context,   ArrayList<String> id, ArrayList<String> banners, ArrayList<String> namebanner)
    {
        this.context = context;
        this.id = id;
        this.banners = banners;
        this.namebanner = namebanner;




    }
    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_layout, parent, false);
        return new BannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {


        Glide.with(context)
                .load(banners.get(position))
                .into(holder.bannersimg);

        holder.bannersimg.setScaleType(ImageView.ScaleType.FIT_XY);



    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView bannersimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bannersimg=itemView.findViewById(R.id.img_banner);


        }
    }
}


