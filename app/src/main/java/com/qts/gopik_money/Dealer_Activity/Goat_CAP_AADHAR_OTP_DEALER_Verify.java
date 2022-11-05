package com.qts.gopik_money.Dealer_Activity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.AadharEnterResponseModel;
import com.qts.gopik_money.Model.TagID_MODEL;
import com.qts.gopik_money.Pojo.AadharNext_POJO;
import com.qts.gopik_money.Pojo.Aadhar_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Goat_CAP_AADHAR_OTP_DEALER_Verify extends AppCompatActivity implements TextWatcher {
    TextView confirm;
    TextView resendotp;
    String networkError = "It seems your Network is unstable . Please Try again!";
    LinearLayout linerotp;
    EditText ed_otp1;
    EditText  ed_otp2;
    EditText  ed_otp3;
    EditText  ed_otp4;
    EditText  ed_otp5;
    EditText  ed_otp6;
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
        setContentView(R.layout.goat_activity_c_a_p__otp__d_e_a_l_e_r__verify);
        linerotp = (LinearLayout) findViewById(R.id.linerotp);
        custPrograssbar = new CustPrograssbar();
        confirm = (TextView) findViewById(R.id.confirm);
        resendotp = (TextView) findViewById(R.id.resendotp);
        ed_otp1 = (EditText) findViewById(R.id.ed_otp1);
        ed_otp2 = (EditText) findViewById(R.id.ed_otp2);
        ed_otp3 = (EditText) findViewById(R.id.ed_otp3);
        ed_otp4 = (EditText) findViewById(R.id.ed_otp4);
        ed_otp5 = (EditText) findViewById(R.id.ed_otp5);
        ed_otp6 = (EditText) findViewById(R.id.ed_otp6);
        btn_reenter = (TextView) findViewById(R.id.btn_reenter);
        second_remaining_tv = (TextView) findViewById(R.id.second_remaining_tv);
        time_progressbar = (ProgressBar) findViewById(R.id.time_progressbar);


        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);
        ed_otp5.addTextChangedListener(this);
        ed_otp6.addTextChangedListener(this);

        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {

            Intent it = new Intent(Goat_CAP_AADHAR_OTP_DEALER_Verify.this, Aadhar_Activity.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Goat_CAP_AADHAR_OTP_DEALER_Verify.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        btn_reenter.setOnClickListener(view -> Resend_otp_to_user(
                SharedPref.getStringFromSharedPref(AppConstants.ADHAR_NUMBER, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AADHAR_NAME, getApplicationContext())
        ));

        startTimer();

    }


    private void startTimer() {
        btn_reenter.setVisibility(View.GONE);
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                second_remaining_tv.setText("You can request result in:   " + String.valueOf(millisUntilFinished / 1000) + "seconds");
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

    private void Resend_otp_to_user(String mNumber, String mName) {
        custPrograssbar.prograssCreate(Goat_CAP_AADHAR_OTP_DEALER_Verify.this);
        Aadhar_POJO mAadhar_POJO = new Aadhar_POJO(mName, mNumber);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<AadharEnterResponseModel> call = restApis.GoatAadharValidation(mAadhar_POJO);
        call.enqueue(new Callback<AadharEnterResponseModel>() {
            @Override
            public void onResponse(Call<AadharEnterResponseModel> call, Response<AadharEnterResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.ADHAR_NUMBER, mNumber, getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AadharKey, response.body().getPayload().getKey(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AadharKeyID, response.body().getPayload().getId(), getApplicationContext());

                        startActivity(new Intent(Goat_CAP_AADHAR_OTP_DEALER_Verify.this, Goat_CAP_AADHAR_OTP_DEALER_Verify.class));

                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<AadharEnterResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                            //Do nothing
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:

                        break;
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
                int e = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(4)));
                int f = Integer.parseInt(String.valueOf(retrievedOpt.trim().charAt(5)));

                ed_otp1.setText(String.valueOf(a));
                ed_otp2.setText(String.valueOf(b));
                ed_otp3.setText(String.valueOf(c));
                ed_otp4.setText(String.valueOf(d));
                ed_otp5.setText(String.valueOf(e));
                ed_otp6.setText(String.valueOf(f));
            } catch (Exception e) {
                e.printStackTrace();


            }
        }
    }

    private void otp_verification() {
        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString()
                + ed_otp3.getText().toString() + ed_otp4.getText().toString()
                + ed_otp5.getText().toString() + ed_otp6.getText().toString();

        if ((ed_otp1.getText().toString().isEmpty())
                && (ed_otp2.getText().toString().isEmpty())
                && (ed_otp3.getText().toString().isEmpty())
                && (ed_otp4.getText().toString().isEmpty())
                && (ed_otp5.getText().toString().isEmpty())
                && (ed_otp6.getText().toString().isEmpty())) {
            ed_otp1.setError("Please Enter OTP");
        } else {
            GoatAadharValidationNext(
                    SharedPref.getStringFromSharedPref(AppConstants.ADHAR_NUMBER, getApplicationContext()),
                    SharedPref.getStringFromSharedPref(AppConstants.AadharKeyID, getApplicationContext()),
                    SharedPref.getStringFromSharedPref(AppConstants.AadharKey, getApplicationContext()), otptext);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        // Do nothing
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

                    ed_otp5.requestFocus();

            }
            if (ed_otp5.getText().hashCode() == s.hashCode()&&ed_otp5.length() == 1) {

                    ed_otp6.requestFocus();

            }
            if (ed_otp6.getText().hashCode() == s.hashCode()&&ed_otp6.length() == 1) {

                    otp_verification();

            }

        } else if (s.length() == 0) {
            if (ed_otp6.getText().hashCode() == s.hashCode()&&ed_otp6.length() == 0) {

                    ed_otp5.requestFocus();

            }
            if (ed_otp5.getText().hashCode() == s.hashCode()&&ed_otp5.length() == 0) {

                    ed_otp4.requestFocus();

            }
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

    private void GoatAadharValidationNext(String mNo, String mID, String mKey, String mOtp) {
        custPrograssbar.prograssCreate(Goat_CAP_AADHAR_OTP_DEALER_Verify.this);
        AadharNext_POJO mAadharNext_POJO = new AadharNext_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                mNo, mKey, mID, mOtp);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.GoatAadharValidationNext(mAadharNext_POJO);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.body() != null&&response.body().getCode() == 200) {


                        custPrograssbar.closePrograssBar();
                        Intent it = new Intent(Goat_CAP_AADHAR_OTP_DEALER_Verify.this, GOAT_PAN_CARD_Details.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }

                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }
}