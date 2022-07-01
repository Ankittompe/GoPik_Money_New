package com.qts.gopik_loan.Supplychain_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class PoRequest_Adapter extends RecyclerView.Adapter<PoRequest_Adapter.ViewHolder> {
    Context context;
    ArrayList<String>po_list= new ArrayList<>();
    public ArrayList<Integer> contest_id = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();
    ArrayList<String> dealer_id = new ArrayList<>();
    ArrayList<String> dealer_name = new ArrayList<>();
    ArrayList<String> product = new ArrayList<>();
    ArrayList<String> prodt_quantity = new ArrayList<>();
    ArrayList<String> update_quantity = new ArrayList<>();
    ArrayList<String> prodt_price = new ArrayList<>();
    ArrayList<String> update_price = new ArrayList<>();
    ArrayList<String> total_price = new ArrayList<>();
    ArrayList<String> update_totl_prc = new ArrayList<>();
    ArrayList<String> financer = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> invoicefile = new ArrayList<>();
    public PoRequest_Adapter(Context context, ArrayList<String> id,
                             ArrayList<String> po_id,
                             ArrayList<String> date,
                             ArrayList<String> brand,
                             ArrayList<String> dealer_id,
                             ArrayList<String> dealer_name,
                             ArrayList<String> product,
                             ArrayList<String> prodt_quantity,
                             ArrayList<String> update_quantity,
                             ArrayList<String> prodt_price,
                             ArrayList<String> update_price,
                             ArrayList<String> total_price,
                             ArrayList<String> update_totl_prc,
                             ArrayList<String> financer,
                             ArrayList<String> status,
                             ArrayList<String> invoicefile) {
        this.context = context;
        this.id = id;
        this.po_id = po_id;
        this.date = date;
        this.brand = brand;
        this.dealer_id = dealer_id;
        this.dealer_name = dealer_name;
        this.product = product;
        this.prodt_quantity = prodt_quantity;
        this.update_quantity = update_quantity;
        this.prodt_price = prodt_price;
        this.update_price = update_price;
        this.total_price = total_price;
        this.update_totl_prc = update_totl_prc;
        this.financer = financer;
        this.status = status;
        this.invoicefile = invoicefile;
        this.po_list = po_list;
        this.contest_id = contest_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_list_cardview,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.item_count_tv.setText(Integer.toString(contest_id.get(position)));
       // holder.item_count_tv.setText();
        holder.modified_product_name.setText(product.get(position));
        holder.initial_qty_count.setText(prodt_quantity.get(position));
        holder.modified_qty_count.setText(update_quantity.get(position));
        holder.initial_price_tv.setText(prodt_price.get(position));
        holder.modified_price_tv.setText(update_price.get(position));


    }


    @Override
    public int getItemCount() {
        return id.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_count_tv,modified_product_name,initial_qty_count,modified_qty_count,initial_price_tv,modified_price_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_count_tv = itemView.findViewById(R.id.item_count_tv);
            modified_product_name = itemView.findViewById(R.id.modified_product_name);
            initial_qty_count = itemView.findViewById(R.id.initial_qty_count);
            modified_qty_count = itemView.findViewById(R.id.modified_qty_count);
            initial_price_tv = itemView.findViewById(R.id.initial_price_tv);
            modified_price_tv = itemView.findViewById(R.id.modified_price_tv);

        }
    }
}
