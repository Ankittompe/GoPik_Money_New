package com.qts.gopik_loan.Activity;



import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.qts.gopik_loan.Model.Login_actPOJO;
import com.qts.gopik_loan.Model.Login_act_MODEL;
import com.qts.gopik_loan.Model.LoginsendOtpMODEL;
import com.qts.gopik_loan.Model.Send_login_otp_for_ML_MODEL;
import com.qts.gopik_loan.Pojo.LoginsendOtpPOJO;
import com.qts.gopik_loan.Pojo.Send_login_otp_for_ML_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;
import com.qts.gopik_loan.Utils.GooglePlayStoreAppVersionNameLoader;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements TextWatcher {

    TextInputEditText moblog,user_name;
    TextView btsend;
    ImageView backarrow;
    String TAG = "loginotp";
    RelativeLayout relativelogin;
    Animation topalpha, bottomalpha;
    LinearLayout firstlinerlayout, bottom_linearLayout;
    CustPrograssbar custPrograssbar;
    CheckBox checkBox;
    ConstraintLayout checkboxlayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    public static final int RequestPermissionCode = 7;

    private Location currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        moblog = (TextInputEditText) findViewById(R.id.moblog);
        user_name = (TextInputEditText) findViewById(R.id.user_name);
        btsend = (TextView) findViewById(R.id.btsend);
        backarrow = (ImageView) findViewById(R.id.arrow);
        relativelogin = (RelativeLayout) findViewById(R.id.relativelogin);
        checkBox = (CheckBox) findViewById(R.id.policy_checkBox);
        custPrograssbar = new CustPrograssbar();
        moblog.addTextChangedListener(this);
        checkboxlayout = (ConstraintLayout) findViewById(R.id.checkboxlayout);
        SharedPref.saveStringInSharedPref(AppConstants.CHECKBOXLOGINVALUE, "false", getApplicationContext());
        new GooglePlayStoreAppVersionNameLoader().execute();
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPref.getStringFromSharedPref(AppConstants.LOGIN_STATUS, getApplicationContext()).equals("201")) {
                    signupvalidation();

                } else {

                 checkedValidation();
                }
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.CHECKBOXLOGINVALUE, "checked", getApplicationContext());
                Log.e("bvnfj", "vfvnjf" + SharedPref.getStringFromSharedPref(AppConstants.CHECKBOXLOGINVALUE, getApplicationContext()));
            }
        });


        // Text view number 2 to add hyperlink
        TextView textView12 = findViewById(R.id.textView);
        textView12.setMovementMethod(LinkMovementMethod.getInstance());

        // method to change color of link
        textView12.setLinkTextColor(Color.BLACK);

        // method to redirect to provided link
       /*     linkTextView2.setMovementMethod(LinkMovementMethod.getInstance());

            // method to change color of link
            linkTextView2.setLinkTextColor(Color.YELLOW);*/
        RequestMultiplePermission();
    }

    private void checkedValidation() {

      if (moblog.getText().toString().isEmpty()){

            Toast.makeText(LogIn.this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();

        }else if (!(android.util.Patterns.PHONE.matcher(moblog.getText().toString()).matches())) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() < 10) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() > 10) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (!(SharedPref.getStringFromSharedPref(AppConstants.CHECKBOXLOGINVALUE, getApplicationContext()).equals("checked"))) {

            Toast.makeText(LogIn.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();

        } else {

            send_login_otp();

        }

    }

    private void signupvalidation() {

        if (moblog.getText().toString().isEmpty()) {

            Toast.makeText(LogIn.this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();

        }
     else if (!(android.util.Patterns.PHONE.matcher(moblog.getText().toString()).matches())) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() < 10) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() > 10) {

            Toast.makeText(LogIn.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else {

            send_login_otp();

        }

    }

    private void send_login_otp() {
        custPrograssbar.prograssCreate(LogIn.this);
        LoginsendOtpPOJO pojo = new LoginsendOtpPOJO(moblog.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<LoginsendOtpMODEL> call = restApis.send_login_otp(pojo);
        call.enqueue(new Callback<LoginsendOtpMODEL>() {
            @Override
            public void onResponse(Call<LoginsendOtpMODEL> call, Response<LoginsendOtpMODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.DEALER_MOBILE_NUMBER, moblog.getText().toString(), getApplicationContext());
                   /*     SharedPref.saveStringInSharedPref(AppConstants.NAME_BROKER, user_name.getText().toString(), getApplicationContext());*/
                        SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER, moblog.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PHONENUMBER, moblog.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.OTP, response.body().getOTP(), getApplicationContext());
                        Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));
                        Intent it = new Intent(getApplicationContext(), LogIn_Otp_Verify.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 10) {
            login_act();
        }
    }

    private void login_act() {
        custPrograssbar.prograssCreate(LogIn.this);
        Login_actPOJO pojo = new Login_actPOJO(moblog.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Login_act_MODEL> call = restApis.login_act(pojo);
        call.enqueue(new Callback<Login_act_MODEL>() {
            @Override
            public void onResponse(Call<Login_act_MODEL> call, Response<Login_act_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {

                        SharedPref.saveStringInSharedPref(AppConstants.LOGIN_STATUS, response.body().getStatus(), getApplicationContext());

                        if (SharedPref.getStringFromSharedPref(AppConstants.LOGIN_STATUS, getApplicationContext()).equals("201")) {

                            checkboxlayout.setVisibility(View.GONE);
                        } else {

                            checkboxlayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }

                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Login_act_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void RequestMultiplePermission() {
        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(LogIn.this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CALL_PHONE
                //FOREGROUND_SERVICE
        }, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {


            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
            }

            break;
        }
    }
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        Log.e(TAG , "Inside getAddress method!");
        Log.e(TAG , "Gecoder isPresent: "+ Geocoder.isPresent());
        if (!Geocoder.isPresent()) {
            Toast.makeText(LogIn.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(LogIn.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            Log.e(TAG , "GopikDost Current Address: "+currentAdd);

        }
    }



}
