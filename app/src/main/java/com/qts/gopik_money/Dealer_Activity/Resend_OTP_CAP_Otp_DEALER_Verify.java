package com.qts.gopik_money.Dealer_Activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Send_otp_to_user_DEALER_MODEL;
import com.qts.gopik_money.Model.Verify_mobile_number_MODEL;
import com.qts.gopik_money.Pojo.Send_otp_to_user_POJO;
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

public class Resend_OTP_CAP_Otp_DEALER_Verify extends AppCompatActivity implements TextWatcher {
    TextView confirm;
    TextView resendotp;
    LinearLayout linerotp;
    EditText ed_otp1;
    EditText ed_otp2;
    EditText ed_otp3;
    EditText ed_otp4;
    String TAG = "finalbikepayment";
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView backarrow;
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
        confirm.setOnClickListener(view -> {


        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this, CustomerAcceptancePolicy_DEALER_Activity.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        resendotp.setOnClickListener(view -> send_otp_to_user());


    }
    private void send_otp_to_user() {
        custPrograssbar.prograssCreate(Resend_OTP_CAP_Otp_DEALER_Verify.this);

        Send_otp_to_user_POJO pojo = new Send_otp_to_user_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()) );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Send_otp_to_user_DEALER_MODEL> call = restApis.send_otp_to_user(pojo);
        call.enqueue(new Callback<Send_otp_to_user_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Send_otp_to_user_DEALER_MODEL> call, Response<Send_otp_to_user_DEALER_MODEL> response) {

                if (response.body() != null&&response.body().getCode()==200) {

                        Intent i=new Intent(Resend_OTP_CAP_Otp_DEALER_Verify.this,Resend_OTP_CAP_Otp_DEALER_Verify.class);
                        startActivity(i);

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

                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {

                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                    default:
                        break;
                }
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SMS_CONSENT_REQUEST&&resultCode == RESULT_OK) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
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

            }
        }
    }
    private void otp_verification() {

        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                ed_otp4.getText().toString();
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
        call.enqueue(new Callback<Verify_mobile_number_MODEL>() {
            @Override
            public void onResponse(Call<Verify_mobile_number_MODEL> call, Response<Verify_mobile_number_MODEL> response) {

                if (response.body() != null&&response.body().getCode().equals("200")) {

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

        //Do nothing
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

