package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.GoatAadharvalidation1_MODEL;
import com.qts.gopik_money.Pojo.GoatAadharvalidation1_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Otp_Verify extends AppCompatActivity implements TextWatcher {
    EditText ed_otp1;
    EditText ed_otp2;
    EditText ed_otp3;
    EditText ed_otp4;
    EditText ed_otp5;
    EditText ed_otp6;
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
        btnresend.setOnClickListener(v -> {

            otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                    ed_otp4.getText().toString()+  ed_otp5.getText().toString()+  ed_otp6.getText().toString();


         GoatAadharvalidation1();
        });
    }

    private void GoatAadharvalidation1() {

        GoatAadharvalidation1_POJO pojo = new GoatAadharvalidation1_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NUMBER_AADHARCARD, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ACCESSKEY, getApplicationContext()),
            SharedPref.getStringFromSharedPref(AppConstants.CASEID, getApplicationContext()),
                otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GoatAadharvalidation1_MODEL> call = restApis.GoatAadharvalidation1(pojo);
        call.enqueue(new Callback<GoatAadharvalidation1_MODEL>() {
            @Override
            public void onResponse(Call<GoatAadharvalidation1_MODEL> call, Response<GoatAadharvalidation1_MODEL> response) {
                if (response.body() != null) {
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

        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1) {
            if (ed_otp1.getText().hashCode() == s.hashCode()&&ed_otp1.length() == 1) {

                    ed_otp2.requestFocus();

            }
            if (ed_otp2.getText().hashCode() == s.hashCode()&&ed_otp2.length() == 1) {

                    ed_otp3.requestFocus();

            }
            if (ed_otp3.getText().hashCode() == s.hashCode()&&ed_otp3.length() == 1) {

                    ed_otp4.requestFocus();

            }
            if (ed_otp4.getText().hashCode() == s.hashCode()&&ed_otp4.length() == 1) {

                    ed_otp5.requestFocus();

            }
            if (ed_otp5.getText().hashCode() == s.hashCode()&&ed_otp5.length() == 1) {

                    ed_otp6.requestFocus();

            }
            if (ed_otp6.getText().hashCode() == s.hashCode()&&ed_otp6.length() == 1) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            }
        }
    }
}