package com.qts.gopik_money.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.qts.gopik_money.Model.Login_actPOJO;
import com.qts.gopik_money.Model.Login_act_MODEL;
import com.qts.gopik_money.Model.LoginsendOtpMODEL;
import com.qts.gopik_money.Pojo.LoginsendOtpPOJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.GooglePlayStoreAppVersionNameLoader;


import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements TextWatcher{
    String mEnterMobile = "Please Enter Valid Mobile Number";
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextInputEditText moblog;
    TextInputEditText user_name;
    TextView btsend;
    ImageView backarrow;
    String TAG = "loginotp";
    RelativeLayout relativelogin;

    CustPrograssbar custPrograssbar;
    CheckBox checkBox;
    ConstraintLayout checkboxlayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    public static final int REQUESTPERMISSIONCODE = 7;
    FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        moblog = (TextInputEditText) findViewById(R.id.moblog);
        mFusedLocationProviderClient = new FusedLocationProviderClient(this);
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
        btsend.setOnClickListener(v -> {

            if (SharedPref.getStringFromSharedPref(AppConstants.LOGIN_STATUS, LogIn.this.getApplicationContext()).equals("201")) {
                LogIn.this.signupvalidation();
                LogIn.this.startLocationUpdates();
            } else {
                LogIn.this.startLocationUpdates();
                LogIn.this.checkedValidation();
            }
        });
        checkBox.setOnClickListener(v -> SharedPref.saveStringInSharedPref(AppConstants.CHECKBOXLOGINVALUE, "checked", getApplicationContext()));


        // Text view number 2 to add hyperlink
        TextView textView12 = findViewById(R.id.textView);
        textView12.setMovementMethod(LinkMovementMethod.getInstance());

        // method to change color of link
        textView12.setLinkTextColor(Color.BLACK);

        // method to redirect to provided link
        RequestMultiplePermission();
        startLocationUpdates();



    }

    private void checkedValidation() {

        if (moblog.getText().toString().isEmpty()) {

            Toast.makeText(LogIn.this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (!(android.util.Patterns.PHONE.matcher(moblog.getText().toString()).matches())) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() < 10) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() > 10) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (!(SharedPref.getStringFromSharedPref(AppConstants.CHECKBOXLOGINVALUE, getApplicationContext()).equals("checked"))) {

            Toast.makeText(LogIn.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();

        } else {

            send_login_otp();

        }

    }

    private void signupvalidation() {

        if (moblog.getText().toString().isEmpty()) {

            Toast.makeText(LogIn.this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (!(android.util.Patterns.PHONE.matcher(moblog.getText().toString()).matches())) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() < 10) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

        } else if (moblog.getText().toString().length() > 10) {

            Toast.makeText(LogIn.this, mEnterMobile, Toast.LENGTH_SHORT).show();

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
                        Intent it = new Intent(getApplicationContext(), LogIn_Otp_Verify.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<LoginsendOtpMODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        //Do nothing
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
                    Log.e("Dealer","status"+response.body().getStatus());
                    if (response.body().getCode().equals("200")) {
                        Log.e("Dealer","status"+response.body().getStatus());
                        SharedPref.saveStringInSharedPref(AppConstants.LOGIN_STATUS, response.body().getStatus(), getApplicationContext());

                        if (SharedPref.getStringFromSharedPref(AppConstants.LOGIN_STATUS, getApplicationContext()).equals("201")) {

                            checkboxlayout.setVisibility(View.GONE);
                        } else {

                            checkboxlayout.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }

                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Login_act_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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

        }, REQUESTPERMISSIONCODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission not granted, " +
                                "restart the app if you want the feature",
                        Toast.LENGTH_SHORT).show();
            }
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

            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                 /*   Log.e("Location ", "lat " + locationResult.getLastLocation().getLatitude() + " long " + locationResult.getLastLocation().getLongitude() + " address " );
                    Toast.makeText(getApplicationContext(), "Location permission ", Toast.LENGTH_SHORT).show();*/
                  /*  try {
                        addresses = geocoder.getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                        String city = addresses.get(0).getLocality();
//                        String state = addresses.get(0).getAdminArea();
//                        String country = addresses.get(0).getCountryName();
//                        String postalCode = addresses.get(0).getPostalCode();
//                        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        Log.e("Location ", "lat " + locationResult.getLastLocation().getLatitude() + " long " + locationResult.getLastLocation().getLongitude() + " address " + address);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            }, null);
        }
    }




}
