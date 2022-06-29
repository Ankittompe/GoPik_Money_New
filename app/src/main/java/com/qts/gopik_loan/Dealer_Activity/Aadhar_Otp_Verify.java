package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Goat_Activity.Aadhar_Name_Number_Activity;
import com.qts.gopik_loan.Model.Aadhaarverification_MODEL;
import com.qts.gopik_loan.Model.Aadhar_OTP_Verify_MODEL;
import com.qts.gopik_loan.Model.GoatAadharvalidation1_MODEL;
import com.qts.gopik_loan.Pojo.Aadhaarverification_POJO;
import com.qts.gopik_loan.Pojo.AadharResponsePOJODTO;
import com.qts.gopik_loan.Pojo.Aadhar_OTP_Verify_POJO;
import com.qts.gopik_loan.Pojo.ClientData;
import com.qts.gopik_loan.Pojo.GoatAadharvalidation1_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Otp_Verify extends AppCompatActivity implements TextWatcher {
    EditText ed_otp1, ed_otp2, ed_otp3, ed_otp4,ed_otp5,ed_otp6,namepopupet;
    String otptext;
    TextView btnresend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_otp_verify);
        ed_otp1 = (EditText) findViewById(R.id.ed_otp1);
        ed_otp2 = (EditText) findViewById(R.id.ed_otp2);
        ed_otp3 = (EditText) findViewById(R.id.ed_otp3);
        ed_otp4 = (EditText) findViewById(R.id.ed_otp4);
        ed_otp5 = (EditText) findViewById(R.id.ed_otp5);
        ed_otp6 = (EditText) findViewById(R.id.ed_otp6);
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);
        ed_otp5.addTextChangedListener(this);
        ed_otp6.addTextChangedListener(this);
        btnresend = (TextView) findViewById(R.id.btn_reenter);
        btnresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                        ed_otp4.getText().toString()+  ed_otp5.getText().toString()+  ed_otp6.getText().toString();


             GoatAadharvalidation1();
            }
        });
    }

    private void GoatAadharvalidation1() {

        GoatAadharvalidation1_POJO pojo = new GoatAadharvalidation1_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                "262646068645",
                SharedPref.getStringFromSharedPref(AppConstants.ACCESSKEY, getApplicationContext()),
            SharedPref.getStringFromSharedPref(AppConstants.CASEID, getApplicationContext()),
                otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GoatAadharvalidation1_MODEL> call = restApis.GoatAadharvalidation1(pojo);
        call.enqueue(new Callback<GoatAadharvalidation1_MODEL>() {
            @Override
            public void onResponse(Call<GoatAadharvalidation1_MODEL> call, Response<GoatAadharvalidation1_MODEL> response) {
                if (response.body() != null) {
                    Log.e("pp", "onResponse: " + new Gson().toJson(response.body()));

                    Intent it = new Intent(Aadhar_Otp_Verify.this, PAN_CARD_Details.class);
                    startActivity(it);


                }

            }

            @Override
            public void onFailure(Call<GoatAadharvalidation1_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
/*
    private void getadharfile_otp() {

        Aadhar_OTP_Verify_POJO pojo = new Aadhar_OTP_Verify_POJO( "y",otptext,"1234",
                SharedPref.getStringFromSharedPref(AppConstants.ACCESSKEY, getApplicationContext()),
                new ClientData(SharedPref.getStringFromSharedPref(AppConstants.CASEID, getApplicationContext())));
        RestApis restApis = NetworkHandler.instanceMaker6().create(RestApis.class);
        Call<Aadhar_OTP_Verify_MODEL> call = restApis.getadharfile_otp(pojo);
        call.enqueue(new Callback<Aadhar_OTP_Verify_MODEL>() {
            @Override
            public void onResponse(Call<Aadhar_OTP_Verify_MODEL> call, Response<Aadhar_OTP_Verify_MODEL> response) {
                if (response.body() != null) {
                    Log.e("pp", "onResponse: " + new Gson().toJson(response.body()));

 Aadhar_OTP_Verify_MODEL aadhar_otp_verify_model=new Aadhar_OTP_Verify_MODEL();
 aadhar_otp_verify_model=response.body();


                }

            }

            @Override
            public void onFailure(Call<Aadhar_OTP_Verify_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1) {
            if (ed_otp1.getText().hashCode() == s.hashCode()) {
                if (ed_otp1.length() == 1) {
                    ed_otp2.requestFocus();
                }
            }
            if (ed_otp2.getText().hashCode() == s.hashCode()) {
                if (ed_otp2.length() == 1) {
                    ed_otp3.requestFocus();
                }
            }
            if (ed_otp3.getText().hashCode() == s.hashCode()) {
                if (ed_otp3.length() == 1) {
                    ed_otp4.requestFocus();
                }
            }
            if (ed_otp4.getText().hashCode() == s.hashCode()) {
                if (ed_otp4.length() == 1) {
                    ed_otp5.requestFocus();
                }
            }
            if (ed_otp5.getText().hashCode() == s.hashCode()) {
                if (ed_otp5.length() == 1) {
                    ed_otp6.requestFocus();
                }
            }
            if (ed_otp6.getText().hashCode() == s.hashCode()) {
                if (ed_otp6.length() == 1) {

                }
            }
        }
    }
}