package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_money.Model.RegisterMODEL;
import com.qts.gopik_money.Pojo.RegistrationPOJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText etmobileno,etName;
    String mEnterMobile = "Please Enter Valid Mobile Number";
    TextView btnsign;
    public String TAG = "register";
    private boolean isDuplicate;
    ImageView backarrow;
    RelativeLayout rel;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etmobileno = (EditText) findViewById(R.id.mobileno);
        etName = (EditText) findViewById(R.id.name);
        btnsign = (TextView) findViewById(R.id.btnsend);
        /* rel = (RelativeLayout) findViewById(R.id.rel);*/
        custPrograssbar = new CustPrograssbar();

        btnsign.setOnClickListener(v -> signupvalidation());
    }
    private void signupvalidation() {

        if (etmobileno.getText().toString().isEmpty()) {
            Toast.makeText(Register.this , "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();

        }
        if (etName.getText().toString().isEmpty()) {
            Toast.makeText(Register.this , "Please Enter Your Name", Toast.LENGTH_SHORT).show();

        }else if (!(android.util.Patterns.PHONE.matcher(etmobileno.getText().toString()).matches())) {
            Toast.makeText(Register.this , mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (etmobileno.getText().toString().length() < 10) {
            Toast.makeText(Register.this , mEnterMobile, Toast.LENGTH_SHORT).show();
        }
        else if ( etmobileno.getText().toString().length() > 10 ) {
            Toast.makeText(Register.this , mEnterMobile, Toast.LENGTH_SHORT).show();

        }else if(isDuplicate){
            Toast.makeText(Register.this , "Already Registered", Toast.LENGTH_SHORT).show();

        }
        else{
            sendotp_register();

        }




    }

    private void sendotp_register() {
        custPrograssbar.prograssCreate(Register.this);
        RegistrationPOJO pojo = new RegistrationPOJO(etName.getText().toString(),etmobileno.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RegisterMODEL> call = restApis.sendotp_register(pojo);
        call.enqueue(new Callback<RegisterMODEL>() {
            @Override
            public void onResponse(Call<RegisterMODEL> call, Response<RegisterMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")){
                        SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN,true,getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME,etName.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PHONENUMBER,etmobileno.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.OTP,response.body().getOTP(),getApplicationContext());
                        Intent it=new Intent(getApplicationContext(), Register_OTP_Verification.class);
                        startActivity(it);

                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Mobile Number already registered!!",Toast.LENGTH_LONG).show();
                    }




                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<RegisterMODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }

}

