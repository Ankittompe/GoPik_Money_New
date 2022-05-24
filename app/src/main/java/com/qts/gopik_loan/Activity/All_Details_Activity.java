package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Model.Verify_mobile_number_forloan_MODEL;
import com.qts.gopik_loan.Pojo.Verify_mobile_number_forloan_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__details_);
    }
/*
    private void verify_mobile_number_forloan() {

        custPrograssbar.prograssCreate(Mortgage_Loan_Application.this);
        Verify_mobile_number_forloan_POJO pojo = new Verify_mobile_number_forloan_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                moblog.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.OTP, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Verify_mobile_number_forloan_MODEL> call = restApis.verify_mobile_number_forloan(pojo);
        call.enqueue(new Callback<Verify_mobile_number_forloan_MODEL>() {
            @Override
            public void onResponse(Call<Verify_mobile_number_forloan_MODEL> call, Response<Verify_mobile_number_forloan_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("Body", "body2" + response.body().getCode());
                    Log.e("bfvhjfd", "jfvbhdfjbv" + otp.getText().toString());
                    if (response.body().getCode().equals("200")) {


                        SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());

                        if (SharedPref.getStringFromSharedPref(AppConstants.OTP, getApplicationContext()).equals(otp.getText().toString())) {
                            custPrograssbar.closePrograssBar();
                            Intent it = new Intent(Mortgage_Loan_Application.this, Terms_Condition_Activity.class);
                            startActivity(it);

                        } else {
                            custPrograssbar.closePrograssBar();

                            Toast.makeText(getApplicationContext(), "Entered OTP wrong!", Toast.LENGTH_LONG).show();
                        }
                        Log.e("Body", "body2" + response.body().getCode());


                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Verify_mobile_number_forloan_MODEL> call, Throwable t) {

                Log.e("Body", "body2" + t);

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/

/*
    private void send_otp_for_loan() {
        custPrograssbar.prograssCreate(Mortgage_Loan_Application.this);
        Send_otp_for_loan_POJO pojo = new Send_otp_for_loan_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()), name.getText().toString(),
                moblog.getText().toString(), email.getText().toString(), address.getText().toString(), dob.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.GET_SPINNER_POSITION,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Send_otp_for_loan_MODEL> call = restApis.send_otp_for_loan(pojo);
        call.enqueue(new Callback<Send_otp_for_loan_MODEL>() {
            @Override
            public void onResponse(Call<Send_otp_for_loan_MODEL> call, Response<Send_otp_for_loan_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER, moblog.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PHONENUMBER, moblog.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.OTP, response.body().getOTP(), getApplicationContext());
                        Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));
                        otplinearlayout.setVisibility(View.VISIBLE);
                        btn_proceed1.setVisibility(View.VISIBLE);
                        btn_proceed.setVisibility(View.GONE);
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE, response.body().getPayload().getCustomer_code(), getApplicationContext());
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Send_otp_for_loan_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/

}