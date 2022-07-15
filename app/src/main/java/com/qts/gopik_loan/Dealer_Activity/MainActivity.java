package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.LogIn;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_Profile_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_QR_Code_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.My_Mall_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Tab_Fragment_Dealer;
import com.qts.gopik_loan.Fragment.HomeFragment;
import com.qts.gopik_loan.Dealer_Fragment.Home_Dealer_Fragment;
import com.qts.gopik_loan.Fragment.Profile;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_Add_User_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_ApplicationList_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_Contest_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_Notificarion_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_Wallet_Fragment;
import com.qts.gopik_loan.Model.Dealer_logout_MODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Pojo.Dealer_logoutPOJO;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    FrameLayout fn;
    ImageView logout, refresh, menus;
    private String version;
    String TAG = "home";
    FragmentManager fragmentManagerdealer;
    DrawerLayout drawerLayout;
    ChipNavigationBar chipNavigationBar;
    private WebView mWebVw;
    TextView mTxtUserName;
    ImageView mImgShare,mImgScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        chipNavigationBar = findViewById(R.id.bmnav_dealer);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Home_Dealer_Fragment()).commit();
        fn = (FrameLayout) findViewById(R.id.frameContainer_dealer);
        menus = (ImageView) findViewById(R.id.menus_dealer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drwer_dealer);
        nav = (NavigationView) findViewById(R.id.navigationview_dealer);
        Menu navMenuLogIn = nav.getMenu();
        bottomMenudealer();
        fragmentManagerdealer = getSupportFragmentManager();
        loadFragmentByType1();
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;
        Menu m = nav.getMenu();
        SubMenu topChannelMenu = m.addSubMenu("App Version " + version);
        topChannelMenu.add("");
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;

            @Override

            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dealer_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Home_Dealer_Fragment()).commit();
                        /*    bnv.setCurrentItem(0);*/
                        break;

                    case R.id.dealer_application:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_ApplicationList_Fragment()).commit();
                        /*   bnv.setCurrentItem(2);*/
                        break;
                    case R.id.dealer_wallet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_Wallet_Fragment()).commit();
                        /*   bnv.setCurrentItem(2);*/
                        break;
                    case R.id.dealer_adduser:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_Add_User_Fragment()).commit();
                        /*   bnv.setCurrentItem(2);*/
                        break;


                    case R.id.dealer_menu_login:
                        Intent it = new Intent(MainActivity.this, LogIn.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                        startActivity(it);
                        break;

                    case R.id.dealer_qrcode:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_QR_Code_Fragment()).commit();
                        break;

                    case R.id.dealer_signout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Log Out")
                                .setMessage("Are you sure, you want to logout?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent it = new Intent(MainActivity.this, LogIn.class);
                                        startActivity(it);
                                         dealer_logout();

                                    }

                                }).setNegativeButton("no", null).show();

                        break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        boolean var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext());
        Log.e(TAG, "value" + SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext()));

        if (!var) {
            /*     logout.setVisibility(View.GONE);*/
            navMenuLogIn.findItem(R.id.dealer_menu_login).setVisible(false);
            navMenuLogIn.findItem(R.id.dealer_signout).setVisible(true);

        } else {
            Log.e(TAG, "innn");
            /*   logout.setVisibility(View.VISIBLE);*/
            navMenuLogIn.findItem(R.id.dealer_signout).setVisible(true);
            navMenuLogIn.findItem(R.id.dealer_menu_login).setVisible(false);
        }

        getNotificationClickData();

        FirebaseMessaging.getInstance().subscribeToTopic("GoPikType" + SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()))
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                    /*  Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                });

        View headerView = nav.inflateHeaderView(R.layout.menuui_dealer);
        mWebVw = headerView.findViewById(R.id.webVw);
        mTxtUserName = headerView.findViewById(R.id.txtUserName);
        mImgShare = headerView.findViewById(R.id.imgShare);
        mImgScanner = (ImageView) findViewById(R.id.imgScanner);
        mImgScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgScanner.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_QR_Code_Fragment()).commit();
            }
        });      /*  mImgScanner.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Home_Dealer_Fragment()).commit();
        */
        generateQRCode();
        profile_details();
        mImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL url = null;
                try {
                 /*   url = new URL("https://filesamples.com/samples/document/pdf/sample2.pdf");*/
                    url = new URL("https://gopikmoney.com/public/getPDFlink?user_id="+SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(url))));
                } catch (Exception e) {
                    e.printStackTrace();
                }

              /*  Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.scanone);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Select"));*/
            }
        });

    }
    public void openDrawer(Activity mActivityName){
        drawerLayout = (DrawerLayout) mActivityName.findViewById(R.id.drwer_dealer);
        drawerLayout.openDrawer(GravityCompat.START);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void generateQRCode() {
        String mUserCode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext());
        String wedData = "https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=https://gopikmoney.com/public/QRScan?source_id=" + mUserCode + "&chco=000000";
        mWebVw.setWebViewClient(new MyBrowser());
        mWebVw.getSettings().setLoadsImagesAutomatically(true);
        mWebVw.getSettings().setJavaScriptEnabled(true);
        mWebVw.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebVw.loadUrl(wedData);
    }
    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private void profile_details() {
        ProfileDetails_DEALER_POJO pojo = new ProfileDetails_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<ProfileDetails_DEALER_MODEL> call = restApis.profile_details(pojo);
        call.enqueue(new Callback<ProfileDetails_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<ProfileDetails_DEALER_MODEL> call, Response<ProfileDetails_DEALER_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode()==200) {
                        mTxtUserName.setText(response.body().getPayload().getProfile().get(0).getName());
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ProfileDetails_DEALER_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void bottomMenudealer() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.homebtn_dealer:
                        Log.e("HomeClass", "Inside home");
                        fragment = new Home_Dealer_Fragment();
                        break;

                    case R.id.not_dealer:
                        Log.e("HomeClass", "Inside home");
                        fragment = new Dealer_Notificarion_Fragment();
                        break;


                    case R.id.profile_dealer:
                        Log.e("HomeClass", "Inside profile");
                        fragment = new Tab_Fragment_Dealer();
                        break;
                    case R.id.mymall:
                        Log.e("HomeClass", "Inside profile");
                        fragment = new My_Mall_Fragment();
                        break;
                    case R.id.contest_dealer:
                        Log.e("HomeClass", "Inside profile");
                        fragment = new Dealer_Contest_Fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, fragment).commit();

            }
        });
    }


    public void loadFragment(Fragment fragment) {
        fragmentManagerdealer.beginTransaction().replace(R.id.frameContainer_dealer, fragment).commit();

    }


    private void loadFragmentByType1() {
        if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY) != null) {
            if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.HOME__DELAER_FRAGMENT)) {
                loadFragment(new Home_Dealer_Fragment());

            } else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.PROFILE__DEALER_FRAGMENT)) {
                loadFragment(new Dealer_Profile_Fragment());

            } else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.MY_DEALER_WALLET)) {
                loadFragment(new Dealer_Wallet_Fragment());

            } else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.SUBUSER_DEALER_FRAGMENT)) {
                loadFragment(new Dealer_Add_User_Fragment());

            } else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.NOTIFICATION_FARG)) {
                loadFragment(new Dealer_Notificarion_Fragment());

            } else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.APPLICATION_DEALER_FRAG)) {
                loadFragment(new Dealer_ApplicationList_Fragment());

            }
            else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.MY_MALL_DEALER_FRAG)) {
                loadFragment(new My_Mall_Fragment());

            }


        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!", Toast.LENGTH_LONG).show();
        mImgScanner.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Home_Dealer_Fragment()).commit();
        return false;
        // Disable back button..............
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void dealer_logout() {
        Dealer_logoutPOJO pojo = new Dealer_logoutPOJO( SharedPref.getStringFromSharedPref(AppConstants.BRAND,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_logout_MODEL> call = restApis.dealer_logout(pojo);
        call.enqueue(new Callback<Dealer_logout_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_logout_MODEL> call, Response<Dealer_logout_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("GoPikType" + SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
                        Log.e("Body", "body2");
                        SharedPref.saveBooleanInSharedPref(AppConstants.TOKEN, false, getApplicationContext());
                        SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, false, getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_SUBUSER, null, getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DEALER_EMAIL, null, getApplicationContext());

                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Something went wrong!!!",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Dealer_logout_MODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }
    private void getNotificationClickData() {
        String mNotifyData = SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATION_TYPE, getApplicationContext());
        Log.e("Notification Details Type ", mNotifyData);
        if (mNotifyData.equals("0")) {
//            chipNavigationBar.setItemSelected(R.id.not_dealer, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new Dealer_QR_Code_Fragment()).commit();
        } else if (mNotifyData.equals("1")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_dealer, new My_Mall_Fragment()).commit();
        }
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getApplicationContext());  // 10 for no notification
    }
}