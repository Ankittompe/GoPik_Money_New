package com.qts.gopik_money.Dealer_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.GoatProductDetailsAdapter;
import com.qts.gopik_money.Dealer_Adapter.PincodeAdapter;
import com.qts.gopik_money.Model.Pincode_list_MODEL;
import com.qts.gopik_money.Model.ProductLIstDataModel;
import com.qts.gopik_money.Model.Product_details_Model;
import com.qts.gopik_money.Pojo.Pincode_list_POJO;
import com.qts.gopik_money.Pojo.Product_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GOAT_ProductDetails_DEALER_Activity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView pincoderv;
    ImageView hometoolbar;
    ImageView backarrow;
    GoatProductDetailsAdapter productDetailsAdapter;
    PincodeAdapter pincodeAdapter;
    ArrayList<ProductLIstDataModel> mProductLIstDataModelArrayList;
    CustPrograssbar custPrograssbar;
    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goat_activity_product_details__d_e_a_l_e_r);
        rv = findViewById(R.id.products);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        product_Details();
        backarrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(GOAT_ProductDetails_DEALER_Activity.this, Goat_Products_Dealer_Activity.class);
            startActivity(it);
        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(GOAT_ProductDetails_DEALER_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);
        });
    }

    private void product_Details() {
        custPrograssbar.prograssCreate(GOAT_ProductDetails_DEALER_Activity.this);
        Product_details_POJO pojo = new Product_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TYPEE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_IDD, getApplicationContext())
        );

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Product_details_Model> call = restApis.product_Details(pojo);
        call.enqueue(new Callback<Product_details_Model>() {
            @Override
            public void onResponse(Call<Product_details_Model> call, Response<Product_details_Model> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        mProductLIstDataModelArrayList = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {
                            mProductLIstDataModelArrayList.addAll(response.body().getPayload());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
                            rv.setLayoutManager(layoutManager);
                            rv.setItemAnimator(new DefaultItemAnimator());
                            productDetailsAdapter = new GoatProductDetailsAdapter(getApplicationContext(), mProductLIstDataModelArrayList);
                            rv.setAdapter(productDetailsAdapter);
                            productDetailsAdapter.notifyDataSetChanged();
                        }
                    } else {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Product_details_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void pincode_list() {
        custPrograssbar.prograssCreate(GOAT_ProductDetails_DEALER_Activity.this);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Pincode_list_POJO pojo = new Pincode_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.PROD_BRAND, getApplicationContext()), "751001");

        Call<Pincode_list_MODEL> call = restApis.pincode_list(pojo);

        call.enqueue(new Callback<Pincode_list_MODEL>() {
            @Override
            public void onResponse(Call<Pincode_list_MODEL> call, Response<Pincode_list_MODEL> response) {
                if (response.body() != null) {

                    ArrayList pincode_name = new ArrayList<>();
                    ArrayList pincode_address = new ArrayList<>();
                    if (response.body().getPayload().size() > 0) {

                        for (int i = 0; i < response.body().getPayload().size(); i++) {

                            SharedPref.saveStringInSharedPref(AppConstants.PINCODE_NAME, response.body().getPayload().get(i).getName(), getApplicationContext());
                            SharedPref.saveStringInSharedPref(AppConstants.PINCODE_ADDRESS, response.body().getPayload().get(i).getAddress(), getApplicationContext());
                            if (response.body().getPayload().size() - 1 == i) {


                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                                        getApplicationContext(), LinearLayoutManager.VERTICAL, true
                                );
                                pincoderv.setLayoutManager(layoutManager1);
                                pincoderv.setItemAnimator(new DefaultItemAnimator());
                                pincodeAdapter = new PincodeAdapter(getApplicationContext(), pincode_name, pincode_address);
                                pincoderv.setAdapter(pincodeAdapter);

                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<Pincode_list_MODEL> call, Throwable t) {
                Toast.makeText(GOAT_ProductDetails_DEALER_Activity.this, networkError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

