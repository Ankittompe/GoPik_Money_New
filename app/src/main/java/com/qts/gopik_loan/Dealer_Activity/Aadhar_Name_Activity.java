package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Aadhaarverification_MODEL;
import com.qts.gopik_loan.Pojo.Aadhaarverification_POJO;
import com.qts.gopik_loan.Pojo.ClientData;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Name_Activity extends AppCompatActivity {
    EditText aadharnumber;
    TextView check2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_name);
        aadharnumber=(EditText) findViewById(R.id.aadharname);
        check2=(TextView) findViewById(R.id.check2);
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aadhaarverification();
            }
        });
    }
    private void aadhaarverification() {

        Aadhaarverification_POJO pojo = new Aadhaarverification_POJO(aadharnumber.getText().toString(), "y",
                "",
                SharedPref.getStringFromSharedPref(AppConstants.ACCESSKEY, getApplicationContext()),
                new ClientData(SharedPref.getStringFromSharedPref(AppConstants.CASEID, getApplicationContext())));
        RestApis restApis = NetworkHandler.instanceMaker6().create(RestApis.class);
        Call<Aadhaarverification_MODEL> call = restApis.aadhaarverification(pojo);
        call.enqueue(new Callback<Aadhaarverification_MODEL>() {
            @Override
            public void onResponse(Call<Aadhaarverification_MODEL> call, Response<Aadhaarverification_MODEL> response) {
                if (response.body() != null) {
                    Log.e("pp", "onResponse: " + new Gson().toJson(response.body()));
                    Intent it = new Intent(Aadhar_Name_Activity.this, Aadhar_Otp_Verify.class);
                    startActivity(it);

                }

            }

            @Override
            public void onFailure(Call<Aadhaarverification_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }


}
