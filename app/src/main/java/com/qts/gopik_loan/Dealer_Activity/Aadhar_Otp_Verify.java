package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Aadhaarverification_MODEL;
import com.qts.gopik_loan.Model.Aadhar_OTP_Verify_MODEL;
import com.qts.gopik_loan.Pojo.Aadhaarverification_POJO;
import com.qts.gopik_loan.Pojo.Aadhar_OTP_Verify_POJO;
import com.qts.gopik_loan.Pojo.ClientData;
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

                getadharfile_otp();
            }
        });
    }
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


                }

            }

            @Override
            public void onFailure(Call<Aadhar_OTP_Verify_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}