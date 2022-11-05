package com.qts.gopik_money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.R;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;

public class NotificationFragmentAdapter extends RecyclerView.Adapter<NotificationFragmentAdapter.ViewHolder> {
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> logo = new ArrayList<>();
    ToolTipsManager toolTipsManager;

    Context context;


    public NotificationFragmentAdapter(Context context, ArrayList<String> message, ArrayList<String> timestamp, ArrayList<String> logo)
    {

        this.context = context;
        this.message = message;
        this.timestamp = timestamp;
        this.logo = logo;


    }
    @NonNull
    @Override
    public NotificationFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_lay, parent, false);
        return new NotificationFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationFragmentAdapter.ViewHolder holder, int position) {
        holder.tv_tooltipdata.setText(message.get(position));
        holder.timestamp.setText(timestamp.get(position));

  Glide.with(context)
                .load(logo.get(position))
                .into(holder.img);

        holder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    @Override
    public int getItemCount() {
        return timestamp.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_tooltipdata;
        TextView timestamp;

        LinearLayout tooltip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp= (TextView) itemView.findViewById(R.id.timestamp);
            img=(ImageView)itemView.findViewById(R.id.i);
            tv_tooltipdata = (TextView) itemView.findViewById(R.id.tv_tooltipdata);
        }
    }
}



