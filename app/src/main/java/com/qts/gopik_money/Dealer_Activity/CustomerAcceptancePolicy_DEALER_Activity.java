package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Send_otp_to_user_DEALER_MODEL;
import com.qts.gopik_money.Pojo.Send_otp_to_user_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAcceptancePolicy_DEALER_Activity extends AppCompatActivity {
    EditText moblog;
    TextView btsend;
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView backarrow;
    String TAG = "finalbikepayment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_acceptance_policy__d_e_a_l_e_r_);
        custPrograssbar = new CustPrograssbar();
        moblog=(EditText) findViewById(R.id.moblog);
        btsend=(TextView)findViewById(R.id.btsend);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        btsend.setOnClickListener(view -> validation());
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(CustomerAcceptancePolicy_DEALER_Activity.this, Wallet_Details.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(CustomerAcceptancePolicy_DEALER_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });


    }

    private void validation() {
        if ((moblog.getText().toString().isEmpty()) ) {
            moblog.setError("Please Enter Your Mobile Number");

        }else if (moblog.length()==10){
            send_otp_to_user();
        }
        else{

            moblog.setError("Enter a valid Mobile Number");
        }
    }

    private void send_otp_to_user() {
        custPrograssbar.prograssCreate(CustomerAcceptancePolicy_DEALER_Activity.this);

        Send_otp_to_user_POJO pojo = new Send_otp_to_user_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()), moblog.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e(TAG, "in NetworkHandler");
        Call<Send_otp_to_user_DEALER_MODEL> call = restApis.send_otp_to_user(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Send_otp_to_user_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Send_otp_to_user_DEALER_MODEL> call, Response<Send_otp_to_user_DEALER_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode()==200) {

                        SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER,moblog.getText().toString(),getApplicationContext());

                        SharedPref.saveStringInSharedPref(AppConstants.SEND_OTP_USER_OTP,response.body().getOTP(),getApplicationContext());
                        Intent i=new Intent(CustomerAcceptancePolicy_DEALER_Activity.this,CAP_Otp_DEALER_Verify.class);
                        startActivity(i);

                    }
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Send_otp_to_user_DEALER_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

            }

        });

    }
}