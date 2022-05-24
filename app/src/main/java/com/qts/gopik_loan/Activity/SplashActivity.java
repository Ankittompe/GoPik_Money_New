package com.qts.gopik_loan.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.R;

public class SplashActivity extends AppCompatActivity {
    String TAG = "home";
    Boolean var;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext());


        createHandler();

    }

    private void createHandler() {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        // Do Work
                        handler.removeCallbacks(this);
                        Looper.myLooper().quit();


                        Log.e(TAG, "value" + SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext()));

                        if (!var) {
                            Intent it = new Intent(SplashActivity.this, LogIn.class);

                            startActivity(it);

                        } else {
                            if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()).equals("ML")) {
                                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getApplicationContext());
                                Intent it = new Intent(SplashActivity.this, Home.class);
                                startActivity(it);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            } else {
                                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
                                Intent it = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(it);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }

                        }

                    }
                }, 3000);

                Looper.loop();
            }
        };
        thread.start();
    }
}