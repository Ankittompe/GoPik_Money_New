package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeperpo_loan_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeperpo_loan_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.ShopkeeperAdapter.ShopkeeperLoanStatusAdapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanStatusShopkeeper extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    ShopkeeperLoanStatusAdapter mshopkeeperloanStatusAdapter;
    RecyclerView rclview;
    CustPrograssbar custPrograssbar;
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> disb_amount = new ArrayList<>();
    ArrayList<String> date_of_closer = new ArrayList<>();
    ArrayList<String> loan_id = new ArrayList<>();
    ArrayList<String> disb_date = new ArrayList<>();
    ArrayList<String> tenure = new ArrayList<>();
    ArrayList<String> rateofinterest = new ArrayList<>();
    ImageView arrow;
    ImageView hometoolbar;
    String rupee_symbol = "₹";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_status_shopkeeper);
        custPrograssbar = new CustPrograssbar();
        rclview = findViewById(R.id.rclview);
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);

        arrow.setOnClickListener(v -> {
            Intent it = new Intent(LoanStatusShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(LoanStatusShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });

        shopkeeperpo_loan();

    }

    private void shopkeeperpo_loan() {

        custPrograssbar.prograssCreate(this);

        Shopkeeperpo_loan_POJO pojo = new Shopkeeperpo_loan_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeperpo_loan_MODEL> call = restApis.shopkeeperpo_loan(pojo);
        call.enqueue(new Callback<Shopkeeperpo_loan_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeperpo_loan_MODEL> call, Response<Shopkeeperpo_loan_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        custPrograssbar.closePrograssBar();


                        if (response.body().getPayload().size() > 0) {


                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                disb_amount.add(response.body().getPayload().get(i).getDisburse_amount());
                                date_of_closer.add(response.body().getPayload().get(i).getDate_of_close());
                                loan_id.add(response.body().getPayload().get(i).getLoan_id());
                                disb_date.add(response.body().getPayload().get(i).getDisburse_date());
                                tenure.add(response.body().getPayload().get(i).getTenure());
                                rateofinterest.add(response.body().getPayload().get(i).getRate_of_interest());



                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            LoanStatusShopkeeper.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    rclview.setLayoutManager(layoutManager);
                                    mshopkeeperloanStatusAdapter = new ShopkeeperLoanStatusAdapter(getApplicationContext(),po_id,disb_amount,date_of_closer,loan_id,disb_date,
                                            tenure,rateofinterest);
                                    rclview.setAdapter(mshopkeeperloanStatusAdapter);


                                }
                            }
                        }
                    }

                } else {

                    custPrograssbar.closePrograssBar();
                    Toast.makeText(LoanStatusShopkeeper.this,"No Data Found !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Shopkeeperpo_loan_MODEL> call, Throwable t) {

                Toast.makeText(LoanStatusShopkeeper.this, networkError, Toast.LENGTH_LONG).show();

            }

        });


    }


}