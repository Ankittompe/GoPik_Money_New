package com.qts.gopik_money.Supplychain_Adapter;

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

public class DisbursedAdapter extends RecyclerView.Adapter<DisbursedAdapter.ViewHolder> {


    Context context;
    ArrayList<String> id ;
    ArrayList<String> po_id ;
    ArrayList<String> date ;
    ArrayList<String> brand ;
    ArrayList<String> dealer_id ;
    ArrayList<String> dealer_name ;
    ArrayList<String> product ;
    ArrayList<String> prodt_quantity ;
    ArrayList<String> update_quantity ;
    ArrayList<String> prodt_price ;
    ArrayList<String> update_price ;
    ArrayList<String> total_price ;
    ArrayList<String> update_totl_prc ;
    ArrayList<String> financer ;
    ArrayList<String> status ;
    ArrayList<String> invoicefile ;

    public DisbursedAdapter(Context context, ArrayList<String> id,
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
    public DisbursedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_details_cardview, parent, false);
        return new DisbursedAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DisbursedAdapter.ViewHolder holder, int position) {
        holder.et_product.setText(product.get(position));
        if(update_price.get(position).equals("NA")) {
            holder.et_product_qty.setText(prodt_quantity.get(position));
        }
        else{
            holder.et_product_qty.setText(update_quantity.get(position));
        }
        //  holder.et_product_price.setText(prodt_price.get(position));

      /*  holder.et_id.setText(id.get(position));
        holder.et_po_id.setText(po_id.get(position));
        holder.et_date.setText(date.get(position));
        holder.et_brand.setText(brand.get(position));
        holder.et_dealer_id.setText(dealer_id.get(position));
        holder.et_dealer_name.setText(dealer_name.get(position));
        holder.et_product.setText(product.get(position));
        holder.et_product_qty.setText(prodt_quantity.get(position));
        holder.et_product_price.setText(prodt_price.get(position));
        holder.et_total_price.setText(total_price.get(position));
        holder.et_financer.setText(financer.get(position));
        holder.et_status.setText(status.get(position));
*/
      /*  Glide.with(context)
                .load(invoicefile.get(position))
                .into(holder.invoiceFile);

        holder.invoiceFile.setScaleType(ImageView.ScaleType.CENTER_CROP);*/

    }


    @Override
    public int getItemCount() {

        return id.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id ;
        TextView po_id;
        TextView date;
        TextView brand;
        TextView dealer_id;
        TextView dealer_name;
        TextView product;
        TextView product_qty;
        TextView update_qty;
        TextView update_price;
        TextView total_price;
        TextView update_total_price;
        TextView financer;
        TextView status;
        TextView et_id;
        TextView et_po_id;
        TextView et_date;
        TextView et_brand;
        TextView et_dealer_id;
        TextView et_dealer_name;
        TextView et_product;
        TextView et_product_qty;
        TextView et_update_qty;
        TextView et_product_price;
        TextView et_update_price;
        TextView et_total_price;
        TextView et_update_total_price;
        TextView et_financer;
        TextView et_status;

        ImageView invoiceFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            po_id = itemView.findViewById(R.id.po_id);
            date = itemView.findViewById(R.id.date);
            brand = itemView.findViewById(R.id.brand);
            dealer_id = itemView.findViewById(R.id.dealer_id);
            dealer_name = itemView.findViewById(R.id.dealer_name);
            product = itemView.findViewById(R.id.product);
            product_qty = itemView.findViewById(R.id.product_qty);



            total_price = itemView.findViewById(R.id.total_price);

            financer = itemView.findViewById(R.id.financer);
            status = itemView.findViewById(R.id.status);

            et_id = itemView.findViewById(R.id.et_id);
            et_po_id = itemView.findViewById(R.id.et_po_id);
            et_date = itemView.findViewById(R.id.et_date);
            et_brand = itemView.findViewById(R.id.et_brand);
            et_dealer_id = itemView.findViewById(R.id.et_dealer_id);
            et_dealer_name = itemView.findViewById(R.id.et_dealer_name);
            et_product = itemView.findViewById(R.id.tv1);
            et_product_qty = itemView.findViewById(R.id.tv2);



            et_total_price = itemView.findViewById(R.id.et_total_price);

            et_financer = itemView.findViewById(R.id.et_financer);
            et_status = itemView.findViewById(R.id.et_status);
            invoiceFile=itemView.findViewById(R.id.invoicefile);

        }
    }
}


