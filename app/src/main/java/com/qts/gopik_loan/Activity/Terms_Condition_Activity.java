package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Model.Get_loan_status_MODEL;
import com.qts.gopik_loan.Model.Get_status_MODEL;
import com.qts.gopik_loan.Model.LogInOtpVerifyMODEL;
import com.qts.gopik_loan.Pojo.Get_loan_status_POJO;
import com.qts.gopik_loan.Pojo.Get_status_POJO;
import com.qts.gopik_loan.Pojo.LogInOtpVerifyPOJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Terms_Condition_Activity extends AppCompatActivity {
    String TAG = "loginotp";
    LinearLayout text;
    ImageView hometoolbar, backarrow;
    TextView tv2, wait;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms__condition_);
        wait = (TextView) findViewById(R.id.wait);
        tv2 = (TextView) findViewById(R.id.tv2);
        get_loan_status();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getApplicationContext());
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        Log.e("huiheuir","hvfueisg");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Terms_Condition_Activity.this, Home.class);

                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Terms_Condition_Activity.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_loan_status();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    private void get_loan_status() {

        Get_loan_status_POJO pojo = new Get_loan_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_loan_status_MODEL> call = restApis.get_loan_status(pojo);
        call.enqueue(new Callback<Get_loan_status_MODEL>() {
            @Override
            public void onResponse(Call<Get_loan_status_MODEL> call, Response<Get_loan_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {

                     SharedPref.saveStringInSharedPref(AppConstants.STATUS, response.body().getPayload().get(0).getCust_tc_response(), getApplicationContext());
                        Log.e("huiheuir","hvfueisg"+SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()));
                        if (SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()).equals("Accept")) {
                            Intent it = new Intent(Terms_Condition_Activity.this, KYC_Details.class);
                            startActivity(it);
                            SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN,response.body().getPayload().get(0).getCustomer_code(),getApplicationContext());
                        }

                        else if (SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()).equals("Decline")) {
                            wait.setText("Declined"+ System.getProperty("line.separator") +
                                    "Sorry, we can not process your application without these permissions");
                            tv2.setVisibility(View.GONE);
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<Get_loan_status_MODEL> call, Throwable t) {

                Log.e("Body", "body2" + t);

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }



}