package com.qts.gopik_loan.Goat_Activity;

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
import com.qts.gopik_loan.Dealer_Activity.Aadhar_Name_Activity;
import com.qts.gopik_loan.Dealer_Activity.Aadhar_Otp_Verify;
import com.qts.gopik_loan.Model.Aadhar_OTP_Verify_MODEL;
import com.qts.gopik_loan.Pojo.Aadhar_OTP_Verify_POJO;
import com.qts.gopik_loan.Pojo.ClientData;
import com.qts.gopik_loan.Pojo.GoatAadharvalidation_POJO;
import com.qts.gopik_loan.Pojo.GoatAdharvalidationResponseDTO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Name_Number_Activity extends AppCompatActivity {
  EditText numberaadhar,aadharname;
  TextView check1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_name2);
        numberaadhar=(EditText) findViewById(R.id.numberaadhar);
        aadharname=(EditText) findViewById(R.id.aadharname);
        check1=(TextView) findViewById(R.id.check1);
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       GoatAadharvalidation();
            }
        });
    }

    private void GoatAadharvalidation() {
        GoatAadharvalidation_POJO pojo = new GoatAadharvalidation_POJO(aadharname.getText().toString(),numberaadhar.getText().toString() );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GoatAdharvalidationResponseDTO> call = restApis.GoatAadharvalidation(pojo);
        call.enqueue(new Callback<GoatAdharvalidationResponseDTO>() {
            @Override
            public void onResponse(Call<GoatAdharvalidationResponseDTO> call, Response<GoatAdharvalidationResponseDTO> response) {
                if (response.body() != null) {
                    SharedPref.saveStringInSharedPref(AppConstants.NAME_VOTERID, aadharname.getText().toString(), getApplicationContext());
                    Log.e("pp", "onResponse: " + String.valueOf(response.body().getPayload().getId()));
                    Log.e("pp", "onResponse: " + response.body().getPayload().getKey());
                    SharedPref.saveStringInSharedPref(AppConstants.CASEID, String.valueOf(response.body().getPayload().getId()), getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.ACCESSKEY, response.body().getPayload().getKey(), getApplicationContext());
                    Intent it = new Intent(Aadhar_Name_Number_Activity.this, Aadhar_Otp_Verify.class);
                    startActivity(it);


                }

            }

            @Override
            public void onFailure(Call<GoatAdharvalidationResponseDTO> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
}