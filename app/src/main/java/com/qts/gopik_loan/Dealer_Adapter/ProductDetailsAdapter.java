package com.qts.gopik_loan.Dealer_Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.LogIn;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.CashCalculator;
import com.qts.gopik_loan.Dealer_Activity.Emi_Calculator;
import com.qts.gopik_loan.R;

import java.util.ArrayList;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>  {
    private Dialog dialogCondition;

    ArrayList<String> catagoryimage=new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> product_type = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> product_price = new ArrayList<>();
    ArrayList<String> product_description = new ArrayList<>();
    ArrayList<String> finance_type = new ArrayList<>();
    ArrayList<String> product_brand = new ArrayList<>();
    Context context;
    Integer pos;
    String TAG="loginotp"; TextView textview,textview1,ok;



    public ProductDetailsAdapter(Context context, ArrayList<String> catagoryimage, ArrayList<String> catagoryname,
                                 ArrayList<String> product_type,ArrayList<String> product_id,
                                 ArrayList<String> product_price,
                                 ArrayList<String> product_description,ArrayList<String> finance_type,ArrayList<String> product_brand) {
        this.context = context;
        this.catagoryimage = catagoryimage;
        this.catagoryname = catagoryname;
        this.product_type = product_type;
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_description = product_description;
        this.finance_type = finance_type;
        this.product_brand = product_brand;


    }

    @NonNull
    @Override
    public ProductDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details, parent, false);

        return new ProductDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailsAdapter.ViewHolder holder, int position) {


        holder.textview.setText(catagoryname.get(position));
        holder.  brandname.setText(product_brand.get(position));
        holder.customerprice.setText("₹"+product_price.get(position));
        holder.productdescription.setText(product_description.get(position));
        holder.textview.setText(catagoryname.get(position));
        Glide.with(context)
                .load(catagoryimage.get(position))
                .into(holder.imageview);

        holder.imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(finance_type.get(position).equals("Both")){

            holder.proceed_emi.setVisibility(View.VISIBLE);

            holder.proceed_cash.setVisibility(View.VISIBLE);
        }
        else if(finance_type.get(position).equals("Finance")){

            holder.proceed_emi.setVisibility(View.VISIBLE);
        }
        else{
            holder.proceed_cash.setVisibility(View.VISIBLE);
        }

        holder.proceed_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_MODEL_NAME,catagoryname.get(position),context);

                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_ID,product_id.get(position),context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPE,product_type.get(position),context);
                boolean var=SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN ,context);
                Log.e(TAG,"value"+SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN ,context));

                if (!var){
                    Intent it=new Intent(context, LogIn.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);

                }else{
                    Intent it=new Intent(context, Emi_Calculator.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);
                }

            }
        });

        holder.proceed_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_MODEL_NAME,catagoryname.get(position),context);

                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_ID,product_id.get(position),context);
                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TYPE,product_type.get(position),context);

                boolean var=SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN ,context);
                Log.e(TAG,"value"+SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN ,context));

                if (!var){
                    Intent it=new Intent(context, LogIn.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);

                }else{
                    Intent it=new Intent(context, CashCalculator.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(it);

                }


            }
        });



    }
    /* private void TermsAndConditionsDialogPopup(){

         dialogCondition.setContentView(R.layout.pop_up_dialog);
         textview=(TextView)dialogCondition.findViewById(R.id.textview);
         textview1=(TextView)dialogCondition.findViewById(R.id.textview1);
         ok=(TextView)dialogCondition.findViewById(R.id.ok);
         textview.setText(SharedPref.getStringFromSharedPref(AppConstants.PINCODE_NAME,context));
         textview1.setText(SharedPref.getStringFromSharedPref(AppConstants.PINCODE_ADDRESS,context));
         dialogCondition.getWindow().setBackgroundDrawable(
                 new ColorDrawable(Color.WHITE));
         dialogCondition.setCancelable(true);
         dialogCondition.show();
         ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               dialogCondition.dismiss();

             }
         });

     }*/


    @Override
    public int getItemCount() {
        return catagoryimage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview,brandname;
        LinearLayout linear;
        EditText pincode;
        Button proceed_cash,proceed_emi,both,checkpincode;
        TextView customerprice,productdescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.boys);
            textview = itemView.findViewById(R.id.boysname);
            brandname= itemView.findViewById(R.id.brandname);
            linear = itemView.findViewById(R.id.linear);
            customerprice = itemView.findViewById(R.id.customerprice);
            productdescription = itemView.findViewById(R.id.productdescription);
            proceed_emi= itemView.findViewById(R.id.proceed_emi);
            proceed_cash= itemView.findViewById(R.id.proceed_cash);
            both= itemView.findViewById(R.id.both);

      /*  pincode= itemView.findViewById(R.id.pincode);*/
/**/
        }
    }
}

