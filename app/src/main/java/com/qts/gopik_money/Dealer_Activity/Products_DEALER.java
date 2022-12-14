package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.ProductAdapter;
import com.qts.gopik_money.Model.GetCatproductModel;
import com.qts.gopik_money.Pojo.GetCatproductPojo;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Products_DEALER extends AppCompatActivity {
    RecyclerView rv;
    ImageView hometoolbar;
    ImageView backarrow;
    String networkError = "It seems your Network is unstable . Please Try again!";
    ArrayList<String> catagoryimage = new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> product_type = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> product_brand = new ArrayList<>();
    CustPrograssbar custPrograssbar;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products__d_e_a_l_e_r);
        custPrograssbar = new CustPrograssbar();
        rv = (RecyclerView) findViewById(R.id.products);
        productAdapter = new ProductAdapter(getApplicationContext(), catagoryimage, catagoryname, product_type, product_id, product_brand);
        rv.setAdapter(productAdapter);
        getCatproduct();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Products_DEALER.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Products_DEALER.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });
    }

    private void getCatproduct() {
        custPrograssbar.prograssCreate(Products_DEALER.this);
        GetCatproductPojo pojo = new GetCatproductPojo(SharedPref.getStringFromSharedPref(AppConstants.CAT_TYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CAT_NAME, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext())
        );

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GetCatproductModel> call = restApis.getCatproduct(pojo);
        call.enqueue(new Callback<GetCatproductModel>() {
            @Override
            public void onResponse(Call<GetCatproductModel> call, Response<GetCatproductModel> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        ArrayList category_image = new ArrayList();
                        ArrayList category_name = new ArrayList();
                        ArrayList product_type = new ArrayList();
                        ArrayList product_id = new ArrayList();
                        ArrayList product_brand = new ArrayList();
                        if (response.body().getPayload().size() > 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                category_image.add(response.body().getPayload().get(i).getProd_img_url());
                                category_name.add(response.body().getPayload().get(i).getProd_name());
                                product_type.add(response.body().getPayload().get(i).getProd_type());
                                product_id.add(response.body().getPayload().get(i).getId());

                                product_brand.add(response.body().getPayload().get(i).getProd_brand());
                                if (response.body().getPayload().size() - 1 == i) {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    rv.setLayoutManager(gridLayoutManager);

                                    rv.setItemAnimator(new DefaultItemAnimator());
                                    productAdapter = new ProductAdapter(getApplicationContext(), category_image, category_name, product_type, product_id, product_brand);
                                    rv.setAdapter(productAdapter);
                                }
                            }
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }
            @Override
            public void onFailure(Call<GetCatproductModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }
}
