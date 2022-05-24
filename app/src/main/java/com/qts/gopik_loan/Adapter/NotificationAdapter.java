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
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements ToolTipsManager.TipListener {
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> logo = new ArrayList<>();
    ToolTipsManager toolTipsManager;
    String TAG="hi";
    Context context;


    public NotificationAdapter(Context context, ArrayList<String> message, ArrayList<String> timestamp, ArrayList<String> logo)
    {

        this.context = context;
        this.message = message;
        this.timestamp = timestamp;
        this.logo = logo;


    }
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homenotification, parent, false);
        Log.e("anhii","anhuiii");

        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Log.e("anhii","anhuiii");
        holder.tv_tooltipdata.setText(message.get(position));
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tooltip.setVisibility(View.GONE);
            }
        });
        Glide.with(context)
                .load(logo.get(position))
                .into(holder.img);

        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder. cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tooltip.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timestamp.size();
    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cross,img;
        TextView tv_tooltipdata;

        LinearLayout tooltip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            toolTipsManager = new ToolTipsManager((ToolTipsManager.TipListener) tooltip);
            cross=(ImageView)itemView.findViewById(R.id.cross);
            tooltip = (LinearLayout)itemView.findViewById(R.id.iv_tooltip);
            img=(ImageView)itemView.findViewById(R.id.i);
            tv_tooltipdata = (TextView) itemView.findViewById(R.id.tv_tooltipdata);
        }
    }
}


