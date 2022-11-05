package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.ProductDetailsAdapter;
import com.qts.gopik_money.Model.Product_details_Model;
import com.qts.gopik_money.Pojo.Product_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails_DEALER extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    RecyclerView rv;
    ImageView hometoolbar;
    ImageView backarrow;
    ArrayList<String> catagoryimage = new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> product_type = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> product_price = new ArrayList<>();
    ArrayList<String> product_description = new ArrayList<>();
    ArrayList<String> finance_type = new ArrayList<>();
    ArrayList<String> product_brand = new ArrayList<>();
    ProductDetailsAdapter productDetailsAdapter;
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
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(ProductDetails_DEALER.this, Products_DEALER.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(ProductDetails_DEALER.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

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
                if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        ArrayList category_image = new ArrayList();
                        ArrayList category_name = new ArrayList();
                        ArrayList product_type = new ArrayList();
                        ArrayList product_id = new ArrayList();
                        ArrayList product_price = new ArrayList();
                        ArrayList product_description = new ArrayList();
                        ArrayList finance_type = new ArrayList();
                        ArrayList product_brand = new ArrayList();
                        if (response.body().getPayload().size() > 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
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

                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Product_details_Model> call, Throwable t) {

                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }
}

