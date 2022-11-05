package com.qts.gopik_money.Dealer_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.GoatProductAdapter;
import com.qts.gopik_money.Model.GetCatproductModel;
import com.qts.gopik_money.Model.ProductsListModel;
import com.qts.gopik_money.Pojo.GetCatproductPojo;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Goat_Products_Dealer_Activity extends AppCompatActivity {
    RecyclerView mGoatProductsRecVw;
    ImageView hometoolbar;
    ImageView backarrow;
    ArrayList<ProductsListModel> mProductListModelArrayList ;
    CustPrograssbar custPrograssbar;
    GoatProductAdapter productAdapter;
    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goat_products__d_e_a_l_e_r);
        custPrograssbar = new CustPrograssbar();
        mGoatProductsRecVw = findViewById(R.id.goatProductsRecVw);
        getCatProductList();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        backarrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Goat_Products_Dealer_Activity.this, MainActivity.class);
            startActivity(it);
            finish();
        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Goat_Products_Dealer_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);
        });
    }

    private void getCatProductList() {
        custPrograssbar.prograssCreate(Goat_Products_Dealer_Activity.this);
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
                        mProductListModelArrayList = new ArrayList<>();
                        mProductListModelArrayList.addAll(response.body().getPayload());
                        productAdapter = new GoatProductAdapter(getApplicationContext(), mProductListModelArrayList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        mGoatProductsRecVw.setLayoutManager(gridLayoutManager);
                        mGoatProductsRecVw.setItemAnimator(new DefaultItemAnimator());
                        mGoatProductsRecVw.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
