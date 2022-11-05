package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_money.Model.OtpVerificationMODEL;
import com.qts.gopik_money.Model.RegisterMODEL;
import com.qts.gopik_money.Pojo.OtpVerificationPOJO;
import com.qts.gopik_money.Pojo.RegistrationPOJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_OTP_Verification extends AppCompatActivity  implements TextWatcher {
    TextView btnresend;
    TextView etmobileno;
    TextView resendotp;
    LinearLayout otp;
    LinearLayout linerotp;
    ImageView backarrow;
    String TAG="verifyotplogin";
    EditText ed_otp1;
    EditText ed_otp2;
    EditText ed_otp3;
    EditText ed_otp4;
    RelativeLayout relverifyotp;
    CustPrograssbar custPrograssbar;
    String otptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__o_t_p__verification);
        otp = (LinearLayout) findViewById(R.id.tlog1);
        resendotp = (TextView) findViewById(R.id.resendotp);
        linerotp= (LinearLayout) findViewById(R.id.linerotp);
        btnresend = (TextView) findViewById(R.id.btnsend);
        backarrow = (ImageView) findViewById(R.id.arrow);
        ed_otp1= (EditText) findViewById(R.id.ed_otp1);
        ed_otp2= (EditText) findViewById(R.id.ed_otp2);
        ed_otp3= (EditText) findViewById(R.id.ed_otp3);
        ed_otp4= (EditText) findViewById(R.id.ed_otp4);

        custPrograssbar = new CustPrograssbar();
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);

        btnresend.setOnClickListener(v -> otp_verification());
        resendotp.setOnClickListener(view -> sendotp_register());
    }
    private void sendotp_register() {
        custPrograssbar.prograssCreate(Register_OTP_Verification.this);
        RegistrationPOJO pojo = new RegistrationPOJO(SharedPref.getStringFromSharedPref(AppConstants.NAME,getApplicationContext()),SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()
        ));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<RegisterMODEL> call = restApis.sendotp_register(pojo);
        call.enqueue(new Callback<RegisterMODEL>() {
            @Override
            public void onResponse(Call<RegisterMODEL> call, Response<RegisterMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")){
                        Intent it=new Intent(getApplicationContext(), Resend_Register_OTP_Verification.class);
                        startActivity(it);

                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }




                }
            }

            @Override
            public void onFailure(Call<RegisterMODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }

    private void otp_verification() {
        otptext=ed_otp1.getText().toString()+ed_otp2.getText().toString()+ed_otp3.getText().toString()+
                ed_otp4.getText().toString();
        if ((ed_otp1.getText().toString().isEmpty()) && (ed_otp2.getText().toString().isEmpty()) && (ed_otp3.getText().toString().isEmpty())
                && (ed_otp4.getText().toString().isEmpty())) {
            ed_otp1.setError("Please Enter OTP");


        } else {

            otpverify_register();

        }
    }
    private void otpverify_register() {
        OtpVerificationPOJO pojo = new OtpVerificationPOJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER,getApplicationContext()),otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<OtpVerificationMODEL> call = restApis.otpverify_register(pojo);
        call.enqueue(new Callback<OtpVerificationMODEL>() {
            @Override
            public void onResponse(Call<OtpVerificationMODEL> call, Response<OtpVerificationMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")){
                        String otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                                ed_otp4.getText().toString();


                        SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.TOKEN,response.body().getPayload().getToken(),getApplicationContext());
                        if (SharedPref.getStringFromSharedPref(AppConstants.OTP, getApplicationContext()).equals(otptext)) {
                            Intent it=new Intent(Register_OTP_Verification.this, Home.class);
                            startActivity(it);
                        } else {
                            custPrograssbar.closePrograssBar();

                            Toast.makeText(getApplicationContext(), "Entered OTP wrong!", Toast.LENGTH_LONG).show();
                        }


                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }




                }
            }

            @Override
            public void onFailure(Call<OtpVerificationMODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //Do nothing
    }

    @Override
    public void afterTextChanged(Editable editable) {
        //Do nothing

    }
}


