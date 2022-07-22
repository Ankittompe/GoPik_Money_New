package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Activity.Interfaces.IPoItemClickListener;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.ItemClickListener;
import com.qts.gopik_loan.Supply_Chain.PO_Generate_Form_activity;
import com.qts.gopik_loan.Supply_Chain.PO_Product;

import java.util.ArrayList;

public class Po_Get_List_Adapter extends RecyclerView.Adapter<Po_Get_List_Adapter.ViewHolder> {
    IPoItemClickListener mIPoItemClickListener;
    PO_Generate_Form_activity po_generate_form_activity;
    ArrayList<PO_Product> mProductArrayList;
    Context context;
    ItemClickListener itemClickListener;
    int selectedPosition = -1;

    public Po_Get_List_Adapter(Context context, ArrayList<PO_Product> productList, ItemClickListener itemClickListener) {
        this.context = context;
        this.mProductArrayList = productList;
        this.itemClickListener = itemClickListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_form_cardview, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.increase_cardview.setBackgroundResource(R.drawable.ic_addition_btn);
        holder.decrease_cardview.setBackgroundResource(R.drawable.ic_substraction);
        PO_Product dat = mProductArrayList.get(position);
        holder.producttv.setText(dat.getName());

        Log.e("gg", "hhh" + dat.getQuantity());
        Log.e("gg", "hhh" + dat.getName());


        holder.quantity_tv.setText(dat.getQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get adapter position
                int position = holder.getAdapterPosition();
                // call listener
                itemClickListener.onClick(position, mProductArrayList.get(position));
                // update position
                selectedPosition = position;
                // notify
                notifyDataSetChanged();
            }
        });
        holder.remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                mProductArrayList.remove(position);
                notifyItemChanged(position);
                 notifyItemRangeChanged(position, mProductArrayList.size());
                itemClickListener.onClick(position, mProductArrayList.get(position));
            }
        });
        // check conditions


    }


    @Override
    public int getItemCount() {

        return mProductArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView add_cardview, decrease_cardview, quantity_cardview, increase_cardview;
        TextView quantity_tv;
        TextView producttv;
        ImageView add_tv, remove_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add_cardview = itemView.findViewById(R.id.add_cardview);
            decrease_cardview = itemView.findViewById(R.id.decrease_cardview);
            quantity_cardview = itemView.findViewById(R.id.quantity_cardview);
            increase_cardview = itemView.findViewById(R.id.increase_cardview);
            add_tv = itemView.findViewById(R.id.remove_item);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
            producttv = itemView.findViewById(R.id.producttv);
            remove_item = itemView.findViewById(R.id.remove_item);
        }
    }
}
