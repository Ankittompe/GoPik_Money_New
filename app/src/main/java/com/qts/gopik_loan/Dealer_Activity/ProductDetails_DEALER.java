package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Adapter.PincodeAdapter;
import com.qts.gopik_loan.Dealer_Adapter.ProductDetailsAdapter;
import com.qts.gopik_loan.Model.Pincode_list_MODEL;
import com.qts.gopik_loan.Model.Product_details_Model;
import com.qts.gopik_loan.Pojo.Pincode_list_POJO;
import com.qts.gopik_loan.Pojo.Product_details_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductDetails_DEALER extends AppCompatActivity {
    RecyclerView rv,pincoderv;
    Button checkpincode,checkpincode1;
    Dialog dialog;
    ImageView hometoolbar,backarrow;
    ArrayList<String> catagoryimage = new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> product_type = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> product_price = new ArrayList<>();
    ArrayList<String> product_description = new ArrayList<>();
    ArrayList<String> finance_type = new ArrayList<>();
    ArrayList<String> product_brand = new ArrayList<>();
    ProductDetailsAdapter productDetailsAdapter;
    PincodeAdapter pincodeAdapter;
    ArrayList<String> pincode_name = new ArrayList<>();
    ArrayList<String> pincode_address = new ArrayList<>();
    /* EditText pincode;
     Button proceed_cash,proceed_emi,both,checkpincode;*/
    CustPrograssbar custPrograssbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details__d_e_a_l_e_r);
        rv=(RecyclerView) findViewById(R.id.products);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        productDetailsAdapter =new ProductDetailsAdapter(getApplicationContext(),catagoryimage,catagoryname,product_type,product_id,
                product_price,product_description,finance_type ,product_brand);
        rv.setAdapter(productDetailsAdapter);

        product_Details();
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProductDetails_DEALER.this, Products_DEALER.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ProductDetails_DEALER.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });

       /* View view = getLayoutInflater().inflate(R.layout.pincodes_listview, null);
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view);*/
        /*  pincoderv=(RecyclerView)findViewById(R.id.pincoderv);
         *//*pincodeAdapter =new PincodeAdapter(getApplicationContext(),pincode_name,pincode_address);
        pincoderv.setAdapter(pincodeAdapter);*//*

        pincode_list();*/
      /*  checkpincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pincode_list();
            }
        });*/




    }

    private void product_Details() {
        custPrograssbar.prograssCreate(ProductDetails_DEALER.this);
        Product_details_POJO pojo = new Product_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TYPEE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_IDD,getApplicationContext())
        );

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Product_details_Model> call = restApis.product_Details(pojo);
        call.enqueue(new Callback<Product_details_Model>() {
            @Override
            public void onResponse(Call<Product_details_Model> call, Response<Product_details_Model> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");
                        ArrayList category_image = new ArrayList();
                        ArrayList category_name = new ArrayList();
                        ArrayList product_type = new ArrayList();
                        ArrayList product_id = new ArrayList();
                        ArrayList product_price = new ArrayList();
                        ArrayList product_description = new ArrayList();
                        ArrayList finance_type = new ArrayList();
                        ArrayList product_brand = new ArrayList();
                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: "+response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

                                category_image.add(response.body().getPayload().get(i).getProd_img_url());
                                category_name.add(response.body().getPayload().get(i).getProd_name());
                                product_type.add(response.body().getPayload().get(i).getProd_type());
                                product_id.add(response.body().getPayload().get(i).getId());
                                product_price.add(response.body().getPayload().get(i).getProd_mrp());
                                product_description.add(response.body().getPayload().get(i).getProd_desc());
                                finance_type.add(response.body().getPayload().get(i).getFinance_type());
                                product_brand.add(response.body().getPayload().get(i).getProd_brand());

                                SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_PRICE,response.body().getPayload().get(i).getProd_mrp(),getApplicationContext());
                                if (response.body().getPayload().size() - 1 == i) {


                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getApplicationContext(), LinearLayoutManager.VERTICAL, true
                                    );
                                    rv.setLayoutManager(layoutManager);
                                    rv.setItemAnimator(new DefaultItemAnimator());
                                    productDetailsAdapter = new ProductDetailsAdapter(getApplicationContext(),category_image,category_name, product_type,product_id,product_price,
                                            product_description,finance_type,product_brand);
                                    rv.setAdapter(productDetailsAdapter);


                                }
                            }
                        }
                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }

                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Product_details_Model> call, Throwable t) {



                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }
    private void pincode_list()
    {
        custPrograssbar.prograssCreate(ProductDetails_DEALER.this);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Pincode_list_POJO pojo = new Pincode_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.PROD_BRAND,getApplicationContext()),"751001");

        Log.e(TAG, "in NetworkHandler");
        Call<Pincode_list_MODEL> call = restApis.pincode_list(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Pincode_list_MODEL>() {
            @Override
            public void onResponse(Call<Pincode_list_MODEL> call, Response<Pincode_list_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    ArrayList pincode_name = new ArrayList<>();
                    ArrayList pincode_address = new ArrayList<>();
                    if (response.body().getPayload().size() > 0) {
                        Log.e(TAG, "getpayloadmethod");
                        Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            Log.e("Body", "body3");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().get(i).getName());
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().get(i).getAddress());
                            SharedPref.saveStringInSharedPref(AppConstants.PINCODE_NAME,response.body().getPayload().get(i).getName(),getApplicationContext());
                            SharedPref.saveStringInSharedPref(AppConstants.PINCODE_ADDRESS,response.body().getPayload().get(i).getAddress(),getApplicationContext());
                            if (response.body().getPayload().size() - 1 == i) {


                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                                        getApplicationContext(), LinearLayoutManager.VERTICAL, true
                                );
                                pincoderv.setLayoutManager(layoutManager1);
                                pincoderv.setItemAnimator(new DefaultItemAnimator());
                                pincodeAdapter = new PincodeAdapter(getApplicationContext(),pincode_name,pincode_address);
                                pincoderv.setAdapter(pincodeAdapter);

                            }
                        }
                    }
                }}

            @Override
            public void onFailure(Call<Pincode_list_MODEL> call, Throwable t) {

                Log.e(TAG, "in Inner method" + t);

            }

        });
    }
}

