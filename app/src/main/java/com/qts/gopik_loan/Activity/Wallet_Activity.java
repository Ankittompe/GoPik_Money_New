package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Adapter.WalletHistoryAdapter;
import com.qts.gopik_loan.Model.Update_scratchcard_MODEL;
import com.qts.gopik_loan.Model.Wallet_balance_MODEL;
import com.qts.gopik_loan.Model.Wallethistory_MODEL;
import com.qts.gopik_loan.Pojo.Update_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.Wallet_balance_POJO;
import com.qts.gopik_loan.Pojo.Wallethistory_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Wallet_Activity extends AppCompatActivity {
    TextView textview3,rechrgnow,bookloan;
    CustPrograssbar custPrograssbar;
    WalletHistoryAdapter walletHistoryAdapter;
    RecyclerView rvblog;
    LinearLayout nulllayout;
    ImageView hometoolbar, backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_);
        textview3=(TextView)findViewById(R.id.textview3);
        bookloan=(TextView)findViewById(R.id.bookloan);
        custPrograssbar = new CustPrograssbar();
        rvblog=(RecyclerView) findViewById(R.id.rvblog);
        backarrow = (ImageView) findViewById(R.id.arrow);
        nulllayout=(LinearLayout) findViewById(R.id.nulllayout);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getApplicationContext());
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Wallet_Activity.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.REWARD_FRAGMENT);
                startActivity(it);

            }
        });
        bookloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Wallet_Activity.this, Property_Loan.class);
                startActivity(it);

            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Wallet_Activity.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        wallethistory();
    }

    private void wallethistory() {
        custPrograssbar.prograssCreate(Wallet_Activity.this);
        Wallethistory_POJO pojo = new Wallethistory_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Wallethistory_MODEL> call = restApis.wallethistory(pojo);
        call.enqueue(new Callback<Wallethistory_MODEL>() {
            @Override
            public void onResponse(Call<Wallethistory_MODEL> call, Response<Wallethistory_MODEL> response) {
                if (response.body() != null) {


                    custPrograssbar.closePrograssBar();
                    ArrayList<String> id = new ArrayList<>();
                    ArrayList<String> user_code = new ArrayList<>();
                    ArrayList<String> wallet_type = new ArrayList<>();
                    ArrayList<String> ref_no = new ArrayList<>();
                    ArrayList<String> trans_type = new ArrayList<>();
                    ArrayList<String> txn_amt = new ArrayList<>();
                    ArrayList<String> txn_tmstmp = new ArrayList<>();
                    ArrayList<String> status = new ArrayList<>();
                    ArrayList<String> email = new ArrayList<>();
                    ArrayList<String> razorpay_payment_id = new ArrayList<>();
                    ArrayList<String> remarks = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: "+response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

                                id.add(response.body().getPayload().get(i).getId());
                                wallet_type.add(response.body().getPayload().get(i).getWallet_type());
                                ref_no.add(response.body().getPayload().get(i).getRef_no());
                                trans_type.add(response.body().getPayload().get(i).getTrans_type());
                                txn_amt.add(response.body().getPayload().get(i).getTxn_amt());
                                txn_tmstmp.add(response.body().getPayload().get(i).getTxn_tmstmp());

                                if (response.body().getPayload().size() - 1 == i) {


                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getApplicationContext(), LinearLayoutManager.VERTICAL, true
                                    );
                                    rvblog.setLayoutManager(layoutManager);
                                    rvblog.setItemAnimator(new DefaultItemAnimator());
                                    walletHistoryAdapter = new WalletHistoryAdapter(getApplicationContext(), id,  user_code,  wallet_type
                                            , ref_no,  trans_type,  txn_amt
                                            ,  txn_tmstmp    ,  status,     email
                                            ,  razorpay_payment_id,  remarks);
                                    rvblog.setAdapter(walletHistoryAdapter);


                                }

                            }
                        }

                    else  {
                            nulllayout.setVisibility(View.VISIBLE);

                    }

                }
            }
            @Override
            public void onFailure(Call<Wallethistory_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void wallet_balance() {
        custPrograssbar.prograssCreate(Wallet_Activity.this);
        Wallet_balance_POJO pojo = new Wallet_balance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Wallet_balance_MODEL> call = restApis.wallet_balance(pojo);
        call.enqueue(new Callback<Wallet_balance_MODEL>() {
            @Override
            public void onResponse(Call<Wallet_balance_MODEL> call, Response<Wallet_balance_MODEL> response) {
                if (response.body() != null) {

                    custPrograssbar.closePrograssBar();
                    textview3.setText("Current Balance: "+response.body().getBalance());


                }
            }

            @Override
            public void onFailure(Call<Wallet_balance_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

}