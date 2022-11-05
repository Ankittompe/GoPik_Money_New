package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Model.Send_MLData_to_ICICI_MODEL;
import com.qts.gopik_money.Model.Submit_loan_application_MODEL;
import com.qts.gopik_money.Pojo.Send_MLData_to_ICICI_POJO;
import com.qts.gopik_money.Pojo.Submit_loan_application_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  Success extends AppCompatActivity {
TextView btn_proceed,applicationnumber,applicationstatus;
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar,backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        btn_proceed=(TextView)findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), Scratch_Activity.class);
                startActivity(it);
            }
        });
        applicationstatus=(TextView)findViewById(R.id.applicationstatus);
        applicationnumber=(TextView)findViewById(R.id.applicationnumber);


        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        custPrograssbar = new CustPrograssbar();

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Success.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        submit_loan_application();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }
    private void submit_loan_application() {
        custPrograssbar.prograssCreate(Success.this);
        Submit_loan_application_POJO pojo = new Submit_loan_application_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Submit_loan_application_MODEL> call = restApis.submit_loan_application(pojo);
        call.enqueue(new Callback<Submit_loan_application_MODEL>() {
            @Override
            public void onResponse(Call<Submit_loan_application_MODEL> call, Response<Submit_loan_application_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.APPLICATION_NUMBER,response.body().getPayload().getApplication_number(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.APPLICATION_STATUS,response.body().getPayload().getApplication_status() , getApplicationContext());
                        applicationnumber.setText(response.body().getPayload().getApplication_number());
                        applicationstatus.setText(response.body().getPayload().getApplication_status());
                        Send_MLData_to_ICICI();

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Submit_loan_application_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
    private void Send_MLData_to_ICICI() {
        custPrograssbar.prograssCreate(Success.this);
        Send_MLData_to_ICICI_POJO pojo = new Send_MLData_to_ICICI_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.FIRST_NAME,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MIDDILE_NAME,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LAST_NAME,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUSTOMER_MOBILENO,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LOAN_AMOUNT,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Send_MLData_to_ICICI_MODEL> call = restApis.Send_MLData_to_ICICI(pojo);
        call.enqueue(new Callback<Send_MLData_to_ICICI_MODEL>() {
            @Override
            public void onResponse(Call<Send_MLData_to_ICICI_MODEL> call, Response<Send_MLData_to_ICICI_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        custPrograssbar.closePrograssBar();


                    }


                }
            }

            @Override
            public void onFailure(Call<Send_MLData_to_ICICI_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

}