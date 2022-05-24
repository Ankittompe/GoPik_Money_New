package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Model.OtpVerificationMODEL;
import com.qts.gopik_loan.Pojo.OtpVerificationPOJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resend_Register_OTP_Verification extends AppCompatActivity implements TextWatcher {
    TextView btnresend, etmobileno,resendotp;
    LinearLayout otp,linerotp;
    ImageView backarrow;
    String TAG="verifyotplogin";
    EditText ed_otp1,ed_otp2,ed_otp3,ed_otp4;
    RelativeLayout relverifyotp;
    CustPrograssbar custPrograssbar;
    String otptext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend__register__o_t_p__verification);
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

        btnresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp_verification();
            }
        });
    }
    private void otp_verification() {
        otptext=ed_otp1.getText().toString()+ed_otp2.getText().toString()+ed_otp3.getText().toString()+
                ed_otp4.getText().toString();
        Log.e("ggfggf","hjubhng"+otptext);
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
                        Log.e("Body","body2");
                        if (SharedPref.getStringFromSharedPref(AppConstants.OTP, getApplicationContext()).equals(otptext)) {
                            Intent it=new Intent(Resend_Register_OTP_Verification.this, Home.class);
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

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}