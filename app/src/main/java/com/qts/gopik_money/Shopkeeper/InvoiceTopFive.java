package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeper_PO_profile;
import com.qts.gopik_money.Model.Shopkeeperpo_data_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeperpo_data_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.ShopkeeperAdapter.InvoicePoViewwallDataShopkeeperAdapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceTopFive extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    CustPrograssbar custPrograssbar;
    //private ArrayList<Invoice_Shopkeeper_PO_status> mInvoiceShopkeeperPOStatusArraylist ;
    private ArrayList<Shopkeeper_PO_profile> mShopkeeperpodataArraylist ;
    RecyclerView mRecVwrSubDealerPoList;
   InvoicePoViewwallDataShopkeeperAdapter mInvoicePoViewwallDataShopkeeperAdapter;
    TextView mTxtMore;
    TextView mTxtTop5;
    TextView pogen;
    boolean mIsTopFiveStatus = true;
    ImageView hometoolbar;
    ImageView backarrow;
    SwipeRefreshLayout swiperefreshlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_top_five);
        custPrograssbar = new CustPrograssbar();
        mRecVwrSubDealerPoList =(RecyclerView)findViewById(R.id.top_five_recyclerview);
        mTxtMore =(TextView)findViewById(R.id.txtMore);
        mTxtTop5 =(TextView)findViewById(R.id.txtTop5);
        pogen =(TextView)findViewById(R.id.pogen);

        mTxtMore.setOnClickListener(new InvoiceTopFive.openMorePoDetailsListener());
        mTxtTop5.setOnClickListener(new InvoiceTopFive.openTop5PoDetailsListener());
        shopkeeperpo_dataa(mIsTopFiveStatus);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(() -> {
            swiperefreshlayout.setRefreshing(false);
            shopkeeperpo_dataa(mIsTopFiveStatus);
        });
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(InvoiceTopFive.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(InvoiceTopFive.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });

    }
    private class openMorePoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtTop5.setVisibility(View.VISIBLE);
            mTxtMore.setVisibility(View.GONE);
            shopkeeperpo_dataa(false);
        }
    }

    private class openTop5PoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtMore.setVisibility(View.VISIBLE);
            mTxtTop5.setVisibility(View.GONE);
            shopkeeperpo_dataa(true);
        }
    }
    private void shopkeeperpo_dataa(boolean mIsTopFiveStatus) {
        custPrograssbar.prograssCreate(getApplicationContext());
        Shopkeeperpo_data_POJO pojo;
        if(mIsTopFiveStatus){
            pojo = new Shopkeeperpo_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                    "isTopFive");
        }
        else{
            pojo = new Shopkeeperpo_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                    "isAll");
        }

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeperpo_data_MODEL> call = restApis.shopkeeperpo_data(pojo);
        call.enqueue(new Callback<Shopkeeperpo_data_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeperpo_data_MODEL> call, Response<Shopkeeperpo_data_MODEL> response) {
                custPrograssbar.closePrograssBar();
                mShopkeeperpodataArraylist = new ArrayList<>();
                mShopkeeperpodataArraylist.clear();
                mInvoicePoViewwallDataShopkeeperAdapter = new InvoicePoViewwallDataShopkeeperAdapter(mShopkeeperpodataArraylist, mIsTopFiveStatus);
                if (response.body().getPayload().size() != 0) {
                    mShopkeeperpodataArraylist.addAll(response.body().getPayload());
                    mRecVwrSubDealerPoList.setHasFixedSize(true);
                    mRecVwrSubDealerPoList.setLayoutManager(new LinearLayoutManager(InvoiceTopFive.this));
                    mRecVwrSubDealerPoList.setAdapter(mInvoicePoViewwallDataShopkeeperAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Data is empty", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Shopkeeperpo_data_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }
}