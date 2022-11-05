package com.qts.gopik_money.Dealer_Adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.GetCatproductModel;
import com.qts.gopik_money.Pojo.GetCatproductPojo;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supply_Chain.PO_Generate_Form_activity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PO_Form_Adapter extends RecyclerView.Adapter<PO_Form_Adapter.ViewHolder> {

    PO_Generate_Form_activity po_generate_form_activity;
    ArrayList<String> PO_List = new ArrayList<>();
    ArrayList<String> category_name = new ArrayList();
    int pos = 1;
    int posvar = 0;
    int quantity = 0;


    public PO_Form_Adapter(PO_Generate_Form_activity po_generate_form_activity, ArrayList<String> PO_List) {
        this.po_generate_form_activity = po_generate_form_activity;
        this.PO_List = PO_List;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_form_cardview, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.decrease_cardview.setBackgroundResource(R.drawable.ic_substraction);
        holder.increase_cardview.setBackgroundResource(R.drawable.ic_addition_btn);

        holder.quantity_tv.setText(String.valueOf(quantity));

        holder.increase_cardview.setOnClickListener(v -> {
            quantity = quantity + 1;
            holder.quantity_tv.setText(String.valueOf(quantity));


        });
        holder.decrease_cardview.setOnClickListener(v -> {
            if (String.valueOf(quantity).equals("0")) {
                Toast.makeText(po_generate_form_activity, "Quanity is 0", Toast.LENGTH_SHORT).show();
            } else {
                quantity = quantity - 1;
                holder.quantity_tv.setText(String.valueOf(quantity));
            }

        });

        GetCatproductPojo pojo = new GetCatproductPojo(SharedPref.getStringFromSharedPref(AppConstants.CAT_TYPE, po_generate_form_activity),
                SharedPref.getStringFromSharedPref(AppConstants.CAT_NAME, po_generate_form_activity), SharedPref.getStringFromSharedPref(AppConstants.BRAND, po_generate_form_activity));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GetCatproductModel> call = restApis.getCatproduct(pojo);
        call.enqueue(new Callback<GetCatproductModel>() {
            @Override
            public void onResponse(Call<GetCatproductModel> call, Response<GetCatproductModel> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");


                                category_name.add(response.body().getPayload().get(i).getProd_name());


                                if (response.body().getPayload().size() - 1 == i) {
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                            (po_generate_form_activity, android.R.layout.simple_spinner_item,
                                                    category_name);

                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                            .simple_spinner_dropdown_item);
                                    // product_spinner.setAdapter(spinnerArrayAdapter);
                                    //holder.product_spinner.setAdapter(spinnerArrayAdapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(po_generate_form_activity, "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetCatproductModel> call, Throwable t) {

                Toast.makeText(po_generate_form_activity, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }


    @Override
    public int getItemCount() {

        return 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView add_cardview ;
        CardView decrease_cardview;
        CardView quantity_cardview;
        CardView increase_cardview;
        TextView quantity_tv;
        Spinner product_spinner;
        ImageView remove_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add_cardview = itemView.findViewById(R.id.add_cardview);
            decrease_cardview = itemView.findViewById(R.id.decrease_cardview);
            quantity_cardview = itemView.findViewById(R.id.quantity_cardview);
            increase_cardview = itemView.findViewById(R.id.increase_cardview);
            remove_item = itemView.findViewById(R.id.remove_item);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
            // product_spinner = itemView.findViewById(R.id.product_spinner);

        }
    }
}
