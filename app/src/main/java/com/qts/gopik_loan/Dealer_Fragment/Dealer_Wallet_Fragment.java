package com.qts.gopik_loan.Dealer_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Property_Loan;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Activity.Wallet_Activity;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Activity.Products_DEALER;
import com.qts.gopik_loan.Dealer_Activity.Recharge__Wallet_DEALER_Activity;
import com.qts.gopik_loan.Dealer_Adapter.WalletHistory_DEALER_Adapter;
import com.qts.gopik_loan.Model.Get_wallet_details_MODEL;
import com.qts.gopik_loan.Model.Get_wallet_txn_MODEL;
import com.qts.gopik_loan.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_loan.Pojo.Get_wallet_txn_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Dealer_Wallet_Fragment extends Fragment {
    TextView textview3,rechrgnow,bookloan;
    CustPrograssbar custPrograssbar;
    RecyclerView rvblog;
    LinearLayout nulllayout;
    WalletHistory_DEALER_Adapter walletHistory_dealer_adapter;


    public Dealer_Wallet_Fragment() {
        // Required empty public constructor
    }


    public static Dealer_Wallet_Fragment newInstance(String param1, String param2) {
        Dealer_Wallet_Fragment fragment = new Dealer_Wallet_Fragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_kirloskar__wallet_, container, false);
        rechrgnow=(TextView)v.findViewById(R.id.rechrgnow);
        textview3=(TextView)v.findViewById(R.id.textview3);
        rvblog=(RecyclerView) v.findViewById(R.id.rvblog);
        nulllayout=(LinearLayout)v. findViewById(R.id.nulllayout);
        bookloan=(TextView)v.findViewById(R.id.bookloan);
        custPrograssbar = new CustPrograssbar();
        get_wallet_details();
        rechrgnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getContext(), Recharge__Wallet_DEALER_Activity.class);
                startActivity(it);
            }
        });
        valid();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());
        bookloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        return v;
    }
    private void valid() {
        if (SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getContext()).equals("NA")) {
            Log.e(TAG, "ruhoiiiiiiiiiiii"+SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getContext()));
            textview3.setText("Current Balance : ₹ 0");


        } else {

            textview3.setText("Current Balance : ₹ " +SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getContext()));
        }
    }

    private void get_wallet_details() {
        custPrograssbar.prograssCreate(getContext());
        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()),
                "Gopik Wallet",SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {
                custPrograssbar.closePrograssBar();

                Log.e("sdssad","tysdfyuadgbvja");
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE,response.body().getPayload().get(i).getBalance(),getContext());
                                Log.e(TAG, "errorrrrdrgvgh: " + response.body().getPayload().get(i).getBalance());
                                Log.e(TAG, "errorrrrdrgvgh: " + SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getContext()));
                                /*textview3.setText("Current Balance:"+response.body().getPayload().get(i).getBalance());*/
                                valid();

                            }
                        }
                    }
                    else  {
                        Toast.makeText(getContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                Log.e("sdssad","tysdfyuadgbvja"+t);
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });
        get_wallet_txn();
    }
    private void get_wallet_txn() {
        custPrograssbar.prograssCreate(getContext());
        Get_wallet_txn_POJO pojo = new Get_wallet_txn_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()),
                "Gopik Wallet");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_txn_MODEL> call = restApis.get_wallet_txn(pojo);
        call.enqueue(new Callback<Get_wallet_txn_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_txn_MODEL> call, Response<Get_wallet_txn_MODEL> response) {
                custPrograssbar.closePrograssBar();

                Log.e("sdssad","tysdfyuadgbvja");
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));


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

                        if (response.body().getCode().equals("200")) {
                            if (response.body().getPayload().size() > 0) {
                                Log.e(TAG, "getpayloadmethod");
                                Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                                for (int i = 0; i < response.body().getPayload().size(); i++) {
                                    Log.e("Body", "body3");

                                    id.add(response.body().getPayload().get(i).getId());
                                    user_code.add(response.body().getPayload().get(i).getRef_no());
                                    trans_type.add(response.body().getPayload().get(i).getTrans_type());
                                    txn_amt.add(response.body().getPayload().get(i).getTxn_amt());
                                    txn_tmstmp.add(response.body().getPayload().get(i).getTxn_tmstmp());
                                    remarks.add(response.body().getPayload().get(i).getRemarks());
                                    if (response.body().getPayload().size() - 1 == i) {


                                        LinearLayoutManager layoutManager = new LinearLayoutManager(
                                                getContext(), LinearLayoutManager.VERTICAL, false
                                        );
                                        rvblog.setLayoutManager(layoutManager);
                                        rvblog.setItemAnimator(new DefaultItemAnimator());
                                        walletHistory_dealer_adapter = new WalletHistory_DEALER_Adapter(getContext(), id, user_code, wallet_type, ref_no, trans_type, txn_amt,
                                                txn_tmstmp,   status, email,razorpay_payment_id,remarks);
                                        rvblog.setAdapter(walletHistory_dealer_adapter);

                                    }
                                }
                            }   else  {
                                nulllayout.setVisibility(View.VISIBLE);

                            }
                        }

                }

            }

            @Override
            public void onFailure(Call<Get_wallet_txn_MODEL> call, Throwable t) {
                Log.e("sdssad","tysdfyuadgbvja"+t);
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }

}