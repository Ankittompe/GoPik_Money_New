package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Get_loan_status_MODEL;
import com.qts.gopik_loan.Model.Resend_otp_to_user_MODEL;
import com.qts.gopik_loan.Model.Resend_verify_mobile_number_MODEL;
import com.qts.gopik_loan.Pojo.Get_loan_status_POJO;
import com.qts.gopik_loan.Pojo.Resend_otp_to_user_POJO;
import com.qts.gopik_loan.Pojo.Resend_verify_mobile_number_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsCondition_DEALER_Activity extends AppCompatActivity {
    String TAG = "loginotp";
    LinearLayout text;
    TextView tv2, wait;
    ImageView hometoolbar, backarrow;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CustPrograssbar custPrograssbar;
    TextView second_remaining_tv,btn_reenter;
    CountDownTimer cTimer = null;
    ProgressBar time_progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition__d_e_a_l_e_r_);
        wait = (TextView) findViewById(R.id.wait);
        tv2 = (TextView) findViewById(R.id.tv2);
        get_status();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        btn_reenter = (TextView) findViewById(R.id.btn_reenter);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TermsCondition_DEALER_Activity.this, CAP_Otp_DEALER_Verify.class);

                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TermsCondition_DEALER_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        btn_reenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Resend_verify_mobile_number();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_status();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        second_remaining_tv = (TextView) findViewById(R.id.second_remaining_tv);
        time_progressbar = (ProgressBar) findViewById(R.id.time_progressbar);

        startTimer();
    }
    private void Resend_verify_mobile_number() {
        custPrograssbar.prograssCreate(TermsCondition_DEALER_Activity.this);

        Resend_verify_mobile_number_POJO pojo = new Resend_verify_mobile_number_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()) ,
                SharedPref.getStringFromSharedPref(AppConstants.SEND_OTP_USER_OTP,getApplicationContext())
        );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e(TAG, "in NetworkHandler");
        Call<Resend_verify_mobile_number_MODEL> call = restApis.Resend_verify_mobile_number(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Resend_verify_mobile_number_MODEL>() {
            @Override
            public void onResponse(Call<Resend_verify_mobile_number_MODEL> call, Response<Resend_verify_mobile_number_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode()==200) {
                        SharedPref.saveStringInSharedPref(AppConstants.SEND_OTP_USER_OTP,response.body().getOTP(),getApplicationContext());

                        Intent i=new Intent(TermsCondition_DEALER_Activity.this,TermsCondition_DEALER_Activity.class);
                        startActivity(i);

                    }
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Resend_verify_mobile_number_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

            }

        });

    }

    private void startTimer() {
        btn_reenter.setVisibility(View.GONE);
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                second_remaining_tv.setText("You can request result in:   " +String.valueOf(millisUntilFinished/1000)+"seconds");
                time_progressbar.setVisibility(View.VISIBLE);

            }

            public void onFinish() {
                second_remaining_tv.setText("Did not receive the Term and Condition link ?");
                btn_reenter.setVisibility(View.VISIBLE);

                time_progressbar.setVisibility(View.GONE);
            }
        };
        cTimer.start();
    }

    private void get_status() {

        Get_loan_status_POJO pojo = new Get_loan_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_loan_status_MODEL> call = restApis.get_status(pojo);
        call.enqueue(new Callback<Get_loan_status_MODEL>() {
            @Override
            public void onResponse(Call<Get_loan_status_MODEL> call, Response<Get_loan_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {
                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            Log.e("Body", "body3");
                            SharedPref.saveStringInSharedPref(AppConstants.STATUS, response.body().getPayload().get(i).getCust_tc_response(), getApplicationContext());
                            Log.e("huiheuir", "hvfueisg" + SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()));
                            if (SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()).equals("Accept")) {
                                Intent it = new Intent(TermsCondition_DEALER_Activity.this, Voter_ID_CARD_Details.class);
                                startActivity(it);
                            } else if (SharedPref.getStringFromSharedPref(AppConstants.STATUS, getApplicationContext()).equals("Decline")) {
                                wait.setText("Declined"+ System.getProperty("line.separator") +
                                        "Sorry, we can not process your application without these permissions");
                                tv2.setVisibility(View.GONE);
                            }
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<Get_loan_status_MODEL> call, Throwable t) {

                Log.e("Body", "body2" + t);

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

  /*  @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(TermsConditionActivity.this, TermsConditionActivity.class);  //your class
        startActivity(i);
        finish();

    }*/

}