package com.qts.gopik_money.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;

public class SplashActivity extends AppCompatActivity {
    String TAG = "home";
    Boolean var;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext());


        createHandler();
        String channelId = getString(R.string.default_notification_channel_id);
        String channelName = getString(R.string.default_notification_channel_name);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH));

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.e(TAG, "Key: " + key + " Value: " + value);
            }
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic("GoPik");

        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("GoPik")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                  /*  Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();
                    // Log and toast
                    String msg = token;
                    Log.e("Token ", msg);
                  /*  Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                });
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
                            }
                            else if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()).equals("Open Market")){
                                Log.d(TAG,""+SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()) );
                                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
                                Intent it = new Intent(SplashActivity.this, HomeShopkeeper.class);
                                startActivity(it);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }else {
                                Log.d(TAG,""+SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()) );
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