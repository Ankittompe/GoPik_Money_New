package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Aadhaarverification_MODEL;
import com.qts.gopik_money.Pojo.Aadhaarverification_POJO;
import com.qts.gopik_money.Pojo.ClientData;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

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
        check2.setOnClickListener(v -> aadhaarverification());
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
