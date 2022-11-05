package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.LogInOtpVerifyMODEL;
import com.qts.gopik_money.Model.LoginsendOtpMODEL;
import com.qts.gopik_money.Model.bkr_declrtn_MODEL;
import com.qts.gopik_money.Pojo.LogInOtpVerifyPOJO;
import com.qts.gopik_money.Pojo.LoginsendOtpPOJO;
import com.qts.gopik_money.Pojo.bkr_declrtn_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResendOTPVerify extends AppCompatActivity  implements TextWatcher {

    private static final int RESOLVE_HINT = 100;
    TextView btnresend, etmobileno, resendotp, yes, no,btsendpopup;
    // Set to an unused request code  private static final int SMS_CONSENT_REQUEST = 2;  // Set to an unused request code
    private static final int SMS_CONSENT_REQUEST = 2;
    // Set to an unused request code
    LinearLayout otp, linerotp, lin;
    ImageView backarrow;
    String TAG = "verifyotplogin";
    EditText ed_otp1, ed_otp2, ed_otp3, ed_otp4,namepopupet;
    RelativeLayout relverifyotp;
    CustPrograssbar custPrograssbar;
    String otptext;
    private Dialog dialogCondition;
    TextView second_remaining_tv, seconds_tv;
    CountDownTimer cTimer = null;
    ProgressBar time_progressbar;
    TextView ok, textview_cancel,btn_reenter;
    RadioButton broker, shopkeeper, none;
    RelativeLayout lay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_o_t_p_verify);
        dialogCondition = new Dialog(ResendOTPVerify.this);
        otp = (LinearLayout) findViewById(R.id.tlog1);
        linerotp = (LinearLayout) findViewById(R.id.linerotp);
        btnresend = (TextView) findViewById(R.id.btnsend);
        backarrow = (ImageView) findViewById(R.id.arrow);
        ed_otp1 = (EditText) findViewById(R.id.ed_otp1);
        ed_otp2 = (EditText) findViewById(R.id.ed_otp2);
        ed_otp3 = (EditText) findViewById(R.id.ed_otp3);
        ed_otp4 = (EditText) findViewById(R.id.ed_otp4);
        resendotp = (TextView) findViewById(R.id.resendotp);
        lin = (LinearLayout) findViewById(R.id.lin);
        custPrograssbar = new CustPrograssbar();
        ed_otp1.addTextChangedListener(this);
        ed_otp2.addTextChangedListener(this);
        ed_otp3.addTextChangedListener(this);
        ed_otp4.addTextChangedListener(this);
        second_remaining_tv = (TextView) findViewById(R.id.second_remaining_tv);
        time_progressbar = (ProgressBar) findViewById(R.id.time_progressbar);
        btn_reenter = (TextView) findViewById(R.id.btn_reenter);
      /*  Task<Void> task = SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER,getApplicationContext()));*/
        btnresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btn_reenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_login_otp();
            }
        });
        Task<Void> task = SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(null);

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        startTimer();
    }

    private void startTimer() {
        btn_reenter.setVisibility(View.GONE);
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                second_remaining_tv.setText("You can request resilt in: " +String.valueOf(millisUntilFinished/1000)+"seconds");
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

    private void send_login_otp() {
        custPrograssbar.prograssCreate(ResendOTPVerify.this);
        LoginsendOtpPOJO pojo = new LoginsendOtpPOJO(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<LoginsendOtpMODEL> call = restApis.send_login_otp(pojo);
        call.enqueue(new Callback<LoginsendOtpMODEL>() {
            @Override
            public void onResponse(Call<LoginsendOtpMODEL> call, Response<LoginsendOtpMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.OTP, response.body().getOTP(), getApplicationContext());
                        Intent it = new Intent(getApplicationContext(), ResendOTPVerify.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<LoginsendOtpMODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void otp_verification() {
        otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                ed_otp4.getText().toString();
        if ((ed_otp1.getText().toString().isEmpty()) && (ed_otp2.getText().toString().isEmpty()) && (ed_otp3.getText().toString().isEmpty())
                && (ed_otp4.getText().toString().isEmpty())) {
            ed_otp1.setError("Please Enter OTP");


        } else {

            login_otp_verify();

        }
    }

    private void login_otp_verify() {
        custPrograssbar.prograssCreate(ResendOTPVerify.this);
        LogInOtpVerifyPOJO pojo = new LogInOtpVerifyPOJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), otptext);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<LogInOtpVerifyMODEL> call = restApis.login_otp_verify(pojo);
        call.enqueue(new Callback<LogInOtpVerifyMODEL>() {
            @Override
            public void onResponse(Call<LogInOtpVerifyMODEL> call, Response<LogInOtpVerifyMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("Body", "body2" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        if (SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()).equals("8327754247")) {
                            if (response.body().getPayload().getBrand().equals("ML")) {
                                if ((response.body().getPayload().getBkr_declaration()).equals("NA") |
                                        (response.body().getPayload().getBkr_declaration()).equals("No")) {
                                    SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                    TermsAndConditionsDialogPopup();
                                } else {
                                    SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                    SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                                    Intent it = new Intent(ResendOTPVerify.this, Home.class);
                                    startActivity(it);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                }
                            } else {
                                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                                Intent it = new Intent(ResendOTPVerify.this, MainActivity.class);
                                startActivity(it);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        }
                        String otptext = ed_otp1.getText().toString() + ed_otp2.getText().toString() + ed_otp3.getText().toString() +
                                ed_otp4.getText().toString();
                        SharedPref.saveStringInSharedPref(AppConstants.BRANDUSER, response.body().getPayload().getBkr_declaration(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getBkr_code(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.TOKEN, response.body().getPayload().getToken(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.BRAND, response.body().getPayload().getBrand(), getApplicationContext());
                        if (SharedPref.getStringFromSharedPref(AppConstants.OTP, getApplicationContext()).equals(otptext)) {
                            /* profile_details();*/
                            if (response.body().getPayload().getBrand().equals("ML")) {
                                if ((response.body().getPayload().getBkr_declaration()).equals("NA") |
                                        (response.body().getPayload().getBkr_declaration()).equals("No")) {
                                    SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                    TermsAndConditionsDialogPopup();
                                } else {
                                    SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                    SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                                    Intent it = new Intent(ResendOTPVerify.this, Home.class);
                                    startActivity(it);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                }
                            } else if (response.body().getPayload().getBrand().equals("Open Market")) {
                                if ((response.body().getPayload().getBkr_declaration()).equals("NA") |
                                        (response.body().getPayload().getBkr_declaration()).equals("No")) {
                                    TermsAndConditionsDialogPopup();
                                } else {
                                    SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                                    Intent it = new Intent(ResendOTPVerify.this, HomeShopkeeper.class);
                                    startActivity(it);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                }
                            } else {
                                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "1", getApplicationContext());
                                SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                                Intent it = new Intent(ResendOTPVerify.this, MainActivity.class);
                                startActivity(it);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        } else {
                            custPrograssbar.closePrograssBar();
                            Toast.makeText(getApplicationContext(), "Entered OTP wrong!", Toast.LENGTH_LONG).show();
                        }

                    } else if (response.body().getCode() == 400) {
                        Toast.makeText(getApplicationContext(), "Entered OTP wrong6!", Toast.LENGTH_LONG).show();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Entered OTP wrong6!", Toast.LENGTH_LONG).show();
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<LogInOtpVerifyMODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void TermsAndConditionsDialogPopup() {
        dialogCondition = new Dialog(ResendOTPVerify.this, R.style.CustomAlertDialog);
        dialogCondition.setContentView(R.layout.popup_dialog);
        broker = (RadioButton) dialogCondition.findViewById(R.id.broker);
        shopkeeper = (RadioButton) dialogCondition.findViewById(R.id.shopkeeper);
        none = (RadioButton) dialogCondition.findViewById(R.id.none);
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        broker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.VALUE, "Broker", getApplicationContext());
                namepopup();
                /* bkr_declrtn();*/

            }
        });
        shopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.VALUE, "Shopkeeper", getApplicationContext());
                namepopup();
                /* bkr_declrtn();*/

            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.VALUE, "No", getApplicationContext());
                bkr_declrtn();
                /* bkr_declrtn();*/

            }
        });

/*
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.saveStringInSharedPref(AppConstants.VALUE, "No", getApplicationContext());
                bkr_declrtn();


            }
        });
*/

    }
    private void namepopup() {


        dialogCondition.setContentView(R.layout.name_layout);
        btsendpopup = (TextView) dialogCondition.findViewById(R.id.btsendpopup);
        namepopupet = (EditText) dialogCondition.findViewById(R.id.namepopupet);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        btsendpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.BROKER_NAME, namepopupet.getText().toString(), getApplicationContext());
                bkr_declrtn();


            }
        });
    }
    private void bkr_declrtn() {
        custPrograssbar.prograssCreate(ResendOTPVerify.this);
        bkr_declrtn_POJO pojo = new bkr_declrtn_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.BROKER_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.VALUE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<bkr_declrtn_MODEL> call = restApis.bkr_declrtn(pojo);
        call.enqueue(new Callback<bkr_declrtn_MODEL>() {
            @Override
            public void onResponse(Call<bkr_declrtn_MODEL> call, Response<bkr_declrtn_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("Body", "body2" + response.body().getCode());
                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.BRAND, response.body().getPayload().getBrand(), getApplicationContext());

                        custPrograssbar.closePrograssBar();
                        if (SharedPref.getStringFromSharedPref(AppConstants.VALUE, getApplicationContext()).equals("Broker")) {

                            SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                            Intent it = new Intent(ResendOTPVerify.this, Home.class);
                            startActivity(it);
                            SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                        } else if (SharedPref.getStringFromSharedPref(AppConstants.VALUE, getApplicationContext()).equals("Shopkeeper")) {

                            SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                            Intent it = new Intent(ResendOTPVerify.this, HomeShopkeeper.class);
                            startActivity(it);
                            SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, true, getApplicationContext());
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                        } else {

                            lin.setClickable(false);
                            cancelpopup();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<bkr_declrtn_MODEL> call, Throwable t) {

                Log.e("Body", "body2" + t);

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void cancelpopup() {
        dialogCondition.setContentView(R.layout.cancel_popup);
        textview_cancel = (TextView) dialogCondition.findViewById(R.id.textview_cancel);
        ok = (TextView) dialogCondition.findViewById(R.id.ok);
        lay = (RelativeLayout) dialogCondition.findViewById(R.id.lay);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialogCondition.setCancelable(true);
        dialogCondition.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();

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



    /* private void profile_details() {
         ProfileDetailsPOJO pojo = new ProfileDetailsPOJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER,getApplicationContext()),SharedPref.getStringFromSharedPref(AppConstants.TOKEN,getApplicationContext()));
         RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
         Call<ProfileDetailsMODEL> call = restApis.profile_details(pojo);
         call.enqueue(new Callback<ProfileDetailsMODEL>() {
             @Override
             public void onResponse(Call<ProfileDetailsMODEL> call, Response<ProfileDetailsMODEL> response) {
                 if (response.body() != null) {
                     Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                     if(response.body().getCode().equals("200")){
 //
                         if (response.body().getPayload().getProfile().size() > 0) {

                             for (int i = 0; i < response.body().getPayload().getProfile().size(); i++) {
                                 Log.e("Body", "body3");

                                 Log.e(TAG, "errorrrrdrgvgh: " + response.body().getPayload().getProfile().get(i).getUser_code());
                             }
                         }

                         Intent it=new Intent(LogIn_Otp_Verify.this, Home.class);
                         startActivity(it);

                     }
                     else  {
                         Toast.makeText(getApplicationContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                     }




                 }
                 custPrograssbar.closePrograssBar();
             }

             @Override
             public void onFailure(Call<ProfileDetailsMODEL> call, Throwable t) {
                 Log.e(TAG, "errorrrrdrgvgh: " + t);


                 Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
             }

         });

     }
 */
