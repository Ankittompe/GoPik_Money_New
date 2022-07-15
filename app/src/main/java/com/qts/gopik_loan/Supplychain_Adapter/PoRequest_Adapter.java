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
    public ArrayList<Integer> Serial_number = new ArrayList<>();
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
                             ArrayList<String> invoicefile, ArrayList<Integer> serial_number) {
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
        this.Serial_number = serial_number;

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
    /*   // holder.item_count_tv.setText();
        holder.et_id.setText(id.get(position));
        holder.et_po_id.setText(po_id.get(position));
        holder.et_date.setText(date.get(position));
        holder.et_brand.setText(brand.get(position));
        holder.et_dealer_id.setText(dealer_id.get(position));
        holder.et_dealer_name.setText(dealer_name.get(position));*/

       holder.serial_tv.setText((Integer.toString(Serial_number.get(position))));

     holder.et_product_qty.setText(prodt_quantity.get(position));
     holder.update_qty.setText(update_quantity.get(position));
      /*  holder.et_product_price.setText(prodt_price.get(position));*/
      /*  holder.et_total_price.setText(total_price.get(position)+"/"+update_totl_prc.get(position));*/
    /*    holder.et_financer.setText(financer.get(position));
        holder.et_status.setText(status.get(position));
        holder.et_modified_product_price.setText(update_price.get(position));
        holder.et_modified_total_price.setText(update_totl_prc.get(position));
        holder.et_modified_product_quantity.setText(update_quantity.get(position));*/
        holder.et_product.setText(product.get(position));

    }


    @Override
    public int getItemCount() {

        return id.size();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, po_id, date, brand, dealer_id, dealer_name,
                product, product_qty, update_qty, product_price,
                update_price, total_price, update_total_price,
                financer, status, et_id, et_po_id, et_date, et_brand,
                et_dealer_id, et_dealer_name, et_product, et_product_qty, et_update_qty,
                et_product_price, et_update_price,
                et_total_price, et_update_total_price, et_financer, et_status,serial_tv,
                et_modified_product_price,et_modified_product_quantity,et_modified_total_price;
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

            product_price = itemView.findViewById(R.id.product_price);

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
            update_qty = itemView.findViewById(R.id.tv3);
            serial_tv = itemView.findViewById(R.id.serial_tv);



            et_total_price = itemView.findViewById(R.id.et_total_price);

            et_financer = itemView.findViewById(R.id.et_financer);
            et_status = itemView.findViewById(R.id.et_status);
          /*  et_modified_product_price=itemView.findViewById(R.id.et_modified_product_price);
                    et_modified_total_price=itemView.findViewById(R.id.et_modified_total_price);
            et_modified_product_quantity=itemView.findViewById(R.id.et_modified_product_quantity);*/
        }
    }
}
