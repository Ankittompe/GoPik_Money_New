package com.qts.gopik_money.ShopkeeperAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.R;

import java.util.ArrayList;

public class ShopkeeperLoanPoAllDetails_Adapter extends RecyclerView.Adapter<ShopkeeperLoanPoAllDetails_Adapter.ViewHolder> {


    Context context;
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

    public ShopkeeperLoanPoAllDetails_Adapter
            (Context context, ArrayList<String> id,
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
             ArrayList<String> invoicefile
            ) {

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

    }

    @NonNull
    @Override
    public ShopkeeperLoanPoAllDetails_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopkeeper_po_details_cardview, parent, false);
        return new ShopkeeperLoanPoAllDetails_Adapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ShopkeeperLoanPoAllDetails_Adapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {

        return id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taxinvoiceno;
        TextView taxinvoiceprice;
        TextView date;
        TextView status;
        TextView name;
        ImageView taxinvoiceimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taxinvoiceno = itemView.findViewById(R.id.taxinvoiceno);
            taxinvoiceprice = itemView. findViewById(R.id.taxinvoiceprice);
            date = itemView. findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            name = itemView. findViewById(R.id.name);
            name =itemView. findViewById(R.id.name);
            taxinvoiceimg = itemView. findViewById(R.id.taxinvoiceimg);

        }
    }
}
