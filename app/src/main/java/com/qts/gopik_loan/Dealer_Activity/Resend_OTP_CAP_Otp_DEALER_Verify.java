package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Send_otp_to_user_DEALER_MODEL;
import com.qts.gopik_loan.Model.Verify_mobile_number_MODEL;
import com.qts.gopik_loan.Pojo.Send_otp_to_user_POJO;
import com.qts.gopik_loan.Pojo.Verify_mobile_number_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resend_OTP_CAP_Otp_DEALER_Verify extends AppCompatActivity implements TextWatcher {
    TextView confirm,resendotp;
    EditText otp, etmobno;
    LinearLayout linerotp;
    EditText ed_otp1, ed_otp2, ed_otp3, ed_otp4;
    String TAG = "finalbikepayment";
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar, backarrow;
    String otptext;
    private static final int SMS_CONSENT_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_otp_cap_otp_dealer_verify);
        linerotp = (LinearLayout) findViewById(R.id.linerotp);
        custPrograssbar = new CustPrograssbar();
        confirm = (TextView) findViewById(R.id.confirm);
        resendotp = (TextView) findViewById(R.id.resendotp);
        ed_otp1 = (EditText) findViewById(R.id.ed_otp1);
        ed_otp2 = (EditText) findViewById(R.id.ed_otp2);
        ed_otp3 = (EditText) findViewById(R.id.ed_otp3);
        ed_otp4 = (EditText) findViewById(R.id.ed_otp4);

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*    otp_verification();*/

            }
        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this, CustomerAcceptancePolicy_DEALER_Activity.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        Task<Void> task = SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(null);
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                send_otp_to_user();
            }
        });


    }
    private void send_otp_to_user() {
        custPrograssbar.prograssCreate(Resend_OTP_CAP_Otp_DEALER_Verify.this);

        Send_otp_to_user_POJO pojo = new Send_otp_to_user_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()) );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e(TAG, "in NetworkHandler");
        Call<Send_otp_to_user_DEALER_MODEL> call = restApis.send_otp_to_user(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Send_otp_to_user_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Send_otp_to_user_DEALER_MODEL> call, Response<Send_otp_to_user_DEALER_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode()==200) {


                        Intent i=new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this,Resend_OTP_CAP_Otp_DEALER_Verify.class);
                        startActivity(i);

                    }
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Send_otp_to_user_DEALER_MODEL> call, Throwable t) {

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
                }
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // ...
            case SMS_CONSENT_REQUEST:
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
                break;
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 4 digit number in the message
        Log.d(TAG, "SMS Received: " + message);
        //Toast.makeText(this, "SMS Received: "+message, Toast.LENGTH_SHORT).show();
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String opt3 = matcher.group(0);
            String retrievedOpt = opt3.replace("<#> Your Gopik Otp is:", "").trim();
            Log.d(TAG, "onOtpReceived: **********" + retrievedOpt.trim().substring(0, 4));

            try {
                int a = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(0)));
                int b = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(1)));
                int c = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(2)));
                int d = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(3)));

                ed_otp1.setText(String.valueOf(a));
                ed_otp2.setText(String.valueOf(b));
                ed_otp3.setText(String.valueOf(c));
                ed_otp4.setText(String.valueOf(d));
                //btn_login.performClick();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onSmsTimOut:*****" + e.getMessage());

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
        custPrograssbar.prograssCreate(Resend_OTP_CAP_Otp_DEALER_Verify.this);
        Verify_mobile_number_POJO pojo = new Verify_mobile_number_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()),
                otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        Call<Verify_mobile_number_MODEL> call = restApis.verify_mobile_number(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Verify_mobile_number_MODEL>() {
            @Override
            public void onResponse(Call<Verify_mobile_number_MODEL> call, Response<Verify_mobile_number_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));


                    if (response.body().getCode().equals("200")) {
                        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                                ed_otp4.getText().toString();

                        if (SharedPref.getStringFromSharedPref(AppConstants.SEND_OTP_USER_OTP, getApplicationContext()).equals(otptext)) {
                            Intent i = new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this, TermsCondition_DEALER_Activity.class);
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

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1) {
            if (ed_otp1.getText().hashCode() == s.hashCode()) {
                if (ed_otp1.length() == 1) {
                    ed_otp2.requestFocus();
                }
            }
            if (ed_otp2.getText().hashCode() == s.hashCode()) {
                if (ed_otp2.length() == 1) {
                    ed_otp3.requestFocus();
                }
            }
            if (ed_otp3.getText().hashCode() == s.hashCode()) {
                if (ed_otp3.length() == 1) {
                    ed_otp4.requestFocus();
                }
            }
            if (ed_otp4.getText().hashCode() == s.hashCode()) {
                if (ed_otp4.length() == 1) {
                    otp_verification();
                }
            }

        } else if (s.length() == 0) {
            if (ed_otp4.getText().hashCode() == s.hashCode()) {
                if (ed_otp4.length() == 0) {
                    ed_otp3.requestFocus();
                }
            }
            if (ed_otp3.getText().hashCode() == s.hashCode()) {
                if (ed_otp3.length() == 0) {
                    ed_otp2.requestFocus();
                }
            }
            if (ed_otp2.getText().hashCode() == s.hashCode()) {
                if (ed_otp2.length() == 0) {
                    ed_otp1.requestFocus();
                }
            }
            if (ed_otp1.getText().hashCode() == s.hashCode()) {
                if (ed_otp1.length() == 0) {
                    ed_otp1.requestFocus();
                }
            }
        }
    }
}

