package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Resend_otp_to_user_MODEL;
import com.qts.gopik_money.Model.Verify_mobile_number_MODEL;
import com.qts.gopik_money.Pojo.Resend_otp_to_user_POJO;
import com.qts.gopik_money.Pojo.Verify_mobile_number_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CAP_Otp_DEALER_Verify extends AppCompatActivity implements TextWatcher {
    TextView confirm;
    TextView resendotp;

    LinearLayout linerotp;
    EditText ed_otp1;
    EditText  ed_otp2;
    EditText  ed_otp3;
    EditText  ed_otp4;
    String TAG = "finalbikepayment";
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView  backarrow;
    String otptext;

    TextView btn_reenter;
    TextView second_remaining_tv;
    CountDownTimer cTimer = null;
    ProgressBar time_progressbar;

    private static final int SMS_CONSENT_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_a_p__otp__d_e_a_l_e_r__verify);
        linerotp = (LinearLayout) findViewById(R.id.linerotp);
        custPrograssbar = new CustPrograssbar();
        confirm = (TextView) findViewById(R.id.confirm);
        resendotp = (TextView) findViewById(R.id.resendotp);
        ed_otp1 = (EditText) findViewById(R.id.ed_otp1);
        ed_otp2 = (EditText) findViewById(R.id.ed_otp2);
        ed_otp3 = (EditText) findViewById(R.id.ed_otp3);
        ed_otp4 = (EditText) findViewById(R.id.ed_otp4);
        btn_reenter = (TextView) findViewById(R.id.btn_reenter);
        second_remaining_tv = (TextView) findViewById(R.id.second_remaining_tv);
        time_progressbar = (ProgressBar) findViewById(R.id.time_progressbar);


        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);
        confirm.setOnClickListener(view -> {


        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(CAP_Otp_DEALER_Verify.this, CustomerAcceptancePolicy_DEALER_Activity.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(CAP_Otp_DEALER_Verify.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });
        Task<Void> task = SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(null);
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        btn_reenter.setOnClickListener(view -> Resend_otp_to_user());

        startTimer();

    }



    private void startTimer() {
        btn_reenter.setVisibility(View.GONE);
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                second_remaining_tv.setText("You can request result in:   " +String.valueOf(millisUntilFinished/1000)+"seconds");
                time_progressbar.setVisibility(View.VISIBLE);

            }

            public void onFinish() {
                second_remaining_tv.setText("Did not receive the OTP ?");
                btn_reenter.setVisibility(View.VISIBLE);
                time_progressbar.setVisibility(View.GONE);
            }
        };
        cTimer.start();
    }


    private void Resend_otp_to_user() {
        custPrograssbar.prograssCreate(CAP_Otp_DEALER_Verify.this);

        Resend_otp_to_user_POJO pojo = new Resend_otp_to_user_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()) );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e(TAG, "in NetworkHandler");
        Call<Resend_otp_to_user_MODEL> call = restApis.Resend_otp_to_user(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Resend_otp_to_user_MODEL>() {
            @Override
            public void onResponse(Call<Resend_otp_to_user_MODEL> call, Response<Resend_otp_to_user_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode()==200) {
                        SharedPref.saveStringInSharedPref(AppConstants.SEND_OTP_USER_OTP,response.body().getOTP(),getApplicationContext());

                        Intent i=new Intent(CAP_Otp_DEALER_Verify.this,CAP_Otp_DEALER_Verify.class);
                        startActivity(i);

                    }
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Resend_otp_to_user_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

            }

        });

    }

    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get consent intent
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            // Handle the exception ...
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Time out occurred, handle the error.
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + smsRetrieverStatus.getStatusCode());
                }
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ...
        if (requestCode == SMS_CONSENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get SMS message content
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                // Extract one-time code from the message and complete verification
                // `sms` contains the entire text of the SMS message, so you will need
                // to parse the string.
                getOtpFromMessage(message);

                // send one time code to the server
            } else {
                // Consent canceled, handle the error ...
            }
        }
    }

    private void getOtpFromMessage(String message) {

        Log.d(TAG, "SMS Received: " + message);

        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String opt3 = matcher.group(0);
            String retrievedOpt = opt3.replace("<#> Your Gopik Otp is:", "").trim();


            try {
                int a = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(0)));
                int b = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(1)));
                int c = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(2)));
                int d = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(3)));

                ed_otp1.setText(String.valueOf(a));
                ed_otp2.setText(String.valueOf(b));
                ed_otp3.setText(String.valueOf(c));
                ed_otp4.setText(String.valueOf(d));

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onSmsTimOut:***" + e.getMessage());

            }
        }
    }
    private void otp_verification() {

        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                ed_otp4.getText().toString();
        Log.e("ggfggf", "hjubhng" + otptext);
        if ((ed_otp1.getText().toString().isEmpty()) && (ed_otp2.getText().toString().isEmpty()) && (ed_otp3.getText().toString().isEmpty())
                && (ed_otp4.getText().toString().isEmpty())) {
            ed_otp1.setError("Please Enter OTP");


        } else {

            verify_mobile_number();

        }

    }

    private void verify_mobile_number() {
        custPrograssbar.prograssCreate(CAP_Otp_DEALER_Verify.this);
        Verify_mobile_number_POJO pojo = new Verify_mobile_number_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()),
                otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        Call<Verify_mobile_number_MODEL> call = restApis.verify_mobile_number(pojo);
        call.enqueue(new Callback<Verify_mobile_number_MODEL>() {
            @Override
            public void onResponse(Call<Verify_mobile_number_MODEL> call, Response<Verify_mobile_number_MODEL> response) {

                if (response.body() != null) {

                    if (response.body().getCode().equalsIgnoreCase("200")) {
                        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                                ed_otp4.getText().toString();

                        if (SharedPref.getStringFromSharedPref(AppConstants.SEND_OTP_USER_OTP, getApplicationContext()).equals(otptext)) {
                            Intent i = new Intent(CAP_Otp_DEALER_Verify.this, TermsCondition_DEALER_Activity.class);
                            startActivity(i);

                        } else {
                            custPrograssbar.closePrograssBar();


                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Entered OTP wrong!", Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Verify_mobile_number_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

            }

        });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //Do Nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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

                    otp_verification();

            }

        } else if (s.length() == 0) {
            if (ed_otp4.getText().hashCode() == s.hashCode()&&ed_otp4.length() == 0) {

                    ed_otp3.requestFocus();

            }
            if (ed_otp3.getText().hashCode() == s.hashCode()&&ed_otp3.length() == 0) {

                    ed_otp2.requestFocus();

            }
            if (ed_otp2.getText().hashCode() == s.hashCode()&&ed_otp2.length() == 0) {

                    ed_otp1.requestFocus();

            }
            if (ed_otp1.getText().hashCode() == s.hashCode()&&ed_otp1.length() == 0) {

                    ed_otp1.requestFocus();

            }
        }
    }
}