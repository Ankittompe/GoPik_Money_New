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
import com.qts.gopik_loan.Model.AADHAR_CONSENT_MODEL;
import com.qts.gopik_loan.Pojo.AADHAR_CONSENT_POJO;
import com.qts.gopik_loan.Pojo.ClientData;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Activity extends AppCompatActivity {
EditText aadharname;
TextView check;
    private static String val3;
    Long tsLong;
    String ts;
    String val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        aadharname=(EditText) findViewById(R.id.aadharname);
        check=(TextView) findViewById(R.id.check1);
        tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();

        val = "" + ((int) (Math.random() * 9000) + 1000);

        getRandomNumberString();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adharconsentapi();
            }
        });

    }
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        val3 = String.format("%06d", number);


        return val3;
    }
    private void adharconsentapi() {
        AADHAR_CONSENT_POJO pojo = new AADHAR_CONSENT_POJO("19", "82", "12.12.12.12"
                , "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:80.0) Gecko/20100101 Firefox/80.0",
                val, val3, "y",aadharname.getText().toString() , ts, "I authorize Karza Technologies Private Limited to access my Aadhaar number and help me fetch my details. I understand that Karza will not be storing or sharing the same in any manner."
                , new ClientData(val3));
        RestApis restApis = NetworkHandler.instanceMaker8().create(RestApis.class);
        Call<AADHAR_CONSENT_MODEL> call = restApis.adharconsentapi(pojo);
        call.enqueue(new Callback<AADHAR_CONSENT_MODEL>() {
            @Override
            public void onResponse(Call<AADHAR_CONSENT_MODEL> call, Response<AADHAR_CONSENT_MODEL> response) {
                if (response.body() != null) {
                    Log.e("hh", "onResponse: " + new Gson().toJson(response.body()));


                    SharedPref.saveStringInSharedPref(AppConstants.CASEID, response.body().getClientData().getCaseId(), getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.ACCESSKEY, response.body().getResult().getAccessKey(), getApplicationContext());
                    Intent it = new Intent(Aadhar_Activity.this, Aadhar_Name_Activity.class);
                    startActivity(it);
                }


            }

            @Override
            public void onFailure(Call<AADHAR_CONSENT_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
}