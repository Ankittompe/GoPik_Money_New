package com.qts.gopik_money.Supply_Chain;

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
import com.qts.gopik_money.Model.Dealer_invoice_data_MODEL;
import com.qts.gopik_money.Model.InvoicePayloadMODEL_DEALER;
import com.qts.gopik_money.Pojo.Dealer_invoice_data_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Supplychain_Adapter.InvoicePoViewwallDataDealerAdapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceTopFiveStatusDealer extends AppCompatActivity {
    CustPrograssbar custPrograssbar;
    private ArrayList<InvoicePayloadMODEL_DEALER> mInvoiceShopkeeperPOStatusArraylist ;
/*    private ArrayList<InvoicePayloadMODEL_DEALER> mShopkeeperpodataArraylist ;*/
    RecyclerView mRecVwrSubDealerPoList;
    InvoicePoViewwallDataDealerAdapter mInvoicePoViewwallDataDealerAdapter;
    TextView mTxtMore;
    TextView mTxtTop5;
    TextView pogen;
    boolean mIsTopFiveStatus = true;
    ImageView hometoolbar;
    ImageView backarrow;
    SwipeRefreshLayout swiperefreshlayout;
    String mUserType;
    Call<Dealer_invoice_data_MODEL> mDealerInvoiceDataCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_top_five_status_dealer);
        custPrograssbar = new CustPrograssbar();
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        mRecVwrSubDealerPoList =(RecyclerView)findViewById(R.id.top_five_recyclerview);
        mTxtMore =(TextView)findViewById(R.id.txtMore);
        mTxtTop5 =(TextView)findViewById(R.id.txtTop5);
        pogen =(TextView)findViewById(R.id.pogen);

        mTxtMore.setOnClickListener(new InvoiceTopFiveStatusDealer.openMorePoDetailsListener());
        mTxtTop5.setOnClickListener(new InvoiceTopFiveStatusDealer.openTop5PoDetailsListener());
        dealer_invoice_data(mIsTopFiveStatus);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(() -> {
            swiperefreshlayout.setRefreshing(false);
            dealer_invoice_data(mIsTopFiveStatus);
        });
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(InvoiceTopFiveStatusDealer.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(InvoiceTopFiveStatusDealer.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });

    }
    private class openMorePoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtTop5.setVisibility(View.VISIBLE);
            mTxtMore.setVisibility(View.GONE);
            dealer_invoice_data(false);
        }
    }

    private class openTop5PoDetailsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mTxtMore.setVisibility(View.VISIBLE);
            mTxtTop5.setVisibility(View.GONE);
            dealer_invoice_data(true);
        }
    }
    private void dealer_invoice_data(boolean mIsTopFiveStatus) {
        custPrograssbar.prograssCreate(getApplicationContext());
        Dealer_invoice_data_POJO pojo;
        if(mIsTopFiveStatus){
            pojo = new Dealer_invoice_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                    "isTopFive");
        }
        else{
            pojo = new Dealer_invoice_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                    "isAll");
        }

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals("Dealer")) {
            if (mIsTopFiveStatus) {
                mDealerInvoiceDataCall = restApis.dealer_invoice_data_api(pojo);
            } else {
                mDealerInvoiceDataCall = restApis.dealer_invoice_data_api(pojo);
            }
        }
            else if(mUserType.equals("SubDealer")){
                if (mIsTopFiveStatus) {
                    mDealerInvoiceDataCall = restApis.subdealer_invoice_data(pojo);
                } else {
                    mDealerInvoiceDataCall = restApis.subdealer_invoice_data(pojo);
                }
            }


            mDealerInvoiceDataCall.enqueue(new Callback<Dealer_invoice_data_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_invoice_data_MODEL> call, Response<Dealer_invoice_data_MODEL> response) {
                custPrograssbar.closePrograssBar();
                mInvoiceShopkeeperPOStatusArraylist = new ArrayList<>();
                mInvoiceShopkeeperPOStatusArraylist.clear();
                mInvoicePoViewwallDataDealerAdapter = new InvoicePoViewwallDataDealerAdapter(mInvoiceShopkeeperPOStatusArraylist, mIsTopFiveStatus);
                if (response.body()!=null&&response.body().getPayload().size() != 0) {
                    mInvoiceShopkeeperPOStatusArraylist.addAll(response.body().getPayload());
                    mRecVwrSubDealerPoList.setHasFixedSize(true);
                    mRecVwrSubDealerPoList.setLayoutManager(new LinearLayoutManager(InvoiceTopFiveStatusDealer.this));
                    mRecVwrSubDealerPoList.setAdapter(mInvoicePoViewwallDataDealerAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Data is empty", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Dealer_invoice_data_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                custPrograssbar.closePrograssBar();
            }
        });
    }
}