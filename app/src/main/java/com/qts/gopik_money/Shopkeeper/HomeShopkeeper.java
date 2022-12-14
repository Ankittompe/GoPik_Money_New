package com.qts.gopik_money.Shopkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.LogIn;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Dealer_logout_MODEL;
import com.qts.gopik_money.Model.Shopkeeper_profile_details_MODEL;
import com.qts.gopik_money.Pojo.Dealer_logoutPOJO;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import java.net.URL;

import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeShopkeeper extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler{
    NavigationView nav;
    BottomNavigationView bnv;

    FrameLayout fn;
    ImageView logout;
    ImageView   menus;
    private String version;
    String TAG = "home";
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    ChipNavigationBar chipNavigationBar;
    private WebView mWebVw;
    TextView mTxtUserName;
    ImageView mImgShare;
    ImageView mImgScanner;
    long pressedTime;
    private int REQ_CODE_VERSION_UPDATE = 001;
    InAppUpdateManager inAppUpdateManager;
    LinearLayout mAppBarLayout;
    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_home2);
        chipNavigationBar = findViewById(R.id.bmnav_shopkeeper);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperMyMall()).commit();
        mAppBarLayout = findViewById(R.id.toolbar);
        /*  bnv = (BottomNavigationView) findViewById(R.id.bmnav);*/
        fn = (FrameLayout) findViewById(R.id.frameContainer);
        menus = (ImageView) findViewById(R.id.menus);
        logout = (ImageView) findViewById(R.id.signout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drwer_shopkeeper);
        nav = (NavigationView) findViewById(R.id.navigationview_shopkeeper);
        /*  refresh= (ImageView) findViewById(R.id.refresh);*/
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Menu navMenuLogIn = nav.getMenu();
        bottomMenu();
        fragmentManager = getSupportFragmentManager();
        loadFragmentByType();
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
                    case R.id.home_shopkeeper:

                        mImgScanner.setVisibility(View.VISIBLE);
                        showNav();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperMyMall()).commit();

                        break;


                    case R.id.profile_drawer_shopkeeper:
                        showNav();
                        mImgScanner.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new Tab_Fragment_Shopkeeper()).commit();
                        /*   bnv.setCurrentItem(3);*/
                        break;

                    case R.id.dost_qrcode_shopkeeper:
                        hideNav();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperQRScan()).commit();
                        break;

                    case R.id.signout_shopkeeper:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeShopkeeper.this);
                        new AlertDialog.Builder(HomeShopkeeper.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Log Out")
                                .setMessage("Are you sure, you want to logout?")
                                .setPositiveButton("yes", (dialog, which) -> {
                                    Intent it = new Intent(HomeShopkeeper.this, LogIn.class);
                                    startActivity(it);
                                    dealer_logout();

                                }).setNegativeButton("no", null).show();

                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        menus.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        /*boolean var = SharedPref.getBooleanFromSharedPref(AppConstants.IS_LOGGED_IN, getApplicationContext());


        if (!var) {
            *//*     logout.setVisibility(View.GONE);*//*
            navMenuLogIn.findItem(R.id.signout).setVisible(false);
          *//*  navMenuLogIn.findItem(R.id.menu_login).setVisible(true);*//*

        } else {

            *//*   logout.setVisibility(View.VISIBLE);*//*
            navMenuLogIn.findItem(R.id.signout).setVisible(true);
          *//*  navMenuLogIn.findItem(R.id.menu_login).setVisible(false);*//*
        }*/

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
        mImgScanner.setOnClickListener(view -> {
            hideNav();
            mImgScanner.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperQRScan()).commit();
        });
        generateQRCode();
        shopkeeper_profile_details();
       /* broker_profile_details();*/
        mImgShare.setOnClickListener(view -> {
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
        });

        showNav();
        chipNavigationBar.setItemSelected(R.id.homebtn_shopkeeper,true);

    }
    public void openDrawer(Activity mActivityName){
        drawerLayout = (DrawerLayout) mActivityName.findViewById(R.id.drwer_shopkeeper);
        drawerLayout.openDrawer(GravityCompat.START);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void generateQRCode() {
        String mUserType;
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        String mUserCode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext());
        String wedData = "https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=https://gopikmoney.com/public/QRScan?source_id=" + mUserCode +"&type="+mUserType+ "&chco=000000";
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
/*
    private void broker_profile_details() {
        Broker_profile_details_POJO pojo = new Broker_profile_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_details_MODEL> call = restApis.broker_profile_details(pojo);
        call.enqueue(new Callback<Broker_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_details_MODEL> call, Response<Broker_profile_details_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        */
/*  mTxtUserName.setText(response.body().getPayload().getProfile().get(0).getBroker_name());*//*

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Broker_profile_details_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/
    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.homebtn_shopkeeper:

                    fragment = new ShopkeeperMyMall();
                    break;

                case R.id.myprofile_shopkeeper_btn:

                    fragment = new Tab_Fragment_Shopkeeper();
                    break;

                case R.id.qrcode_shopkeeper:

                    fragment = new ShopkeeperQRScan();
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, fragment).commit();

        });
    }


    public void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameContainer_Shopkeeper, fragment).commit();

    }
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }*/

    private void loadFragmentByType() {
        if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY) != null)
            if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.SHOPKEEPER_MYMALL_FRAGMENT)) {
                loadFragment(new ShopkeeperMyMall());

            }
      else  if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.SHOPKEEPER_PROFILE_FRAGMENT)) {
            loadFragment(new Profile_Details_Shopkeeper());

        }

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


                    if(response.body().getCode().equals("200")) {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("GoPikType" + SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

                        SharedPref.saveBooleanInSharedPref(AppConstants.TOKEN, false, getApplicationContext());
                        SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, false, getApplicationContext());


                    }
                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Dealer_logout_MODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }

    private void getNotificationClickData() {
        String mNotifyData = SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATION_TYPE, getApplicationContext());

        if (mNotifyData.equals("2")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperMyMall()).commit();
        }
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getApplicationContext());  // 10 for no notification
    }
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameContainer_Shopkeeper);
        if (fragment instanceof ShopkeeperQRScan) {
            mImgScanner.setVisibility(View.VISIBLE);
            showNav();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer_Shopkeeper, new ShopkeeperMyMall()).commit();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                }
                pressedTime = System.currentTimeMillis();
            }
        }
    }

    private void shopkeeper_profile_details() {
        Shopkeeper_profile_details_POJO pojo = new Shopkeeper_profile_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_details_MODEL> call = restApis.shopkeeper_profile_details(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeper_profile_details_MODEL> call, Response<Shopkeeper_profile_details_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        mTxtUserName.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_name());
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Shopkeeper_profile_details_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }
    public void showNav() {
        chipNavigationBar.setVisibility(View.VISIBLE);
    }
    public void hideNav() {
        chipNavigationBar.setVisibility(View.GONE);
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {

        if (status.isDownloaded()) {

            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar.make(rootView,
                    "An update has just been downloaded.",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("RESTART", view -> {

                // Triggers the completion of the update of the app for the flexible flow.
                inAppUpdateManager.completeUpdate();

            });

            snackbar.show();
            Toast.makeText(this, "onInAppUpdateStatus "+status.isUpdateAvailable(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE&&resultCode == Activity.RESULT_CANCELED) {

                /*   inAppUpdateManager.checkForAppUpdate();*/
                /* Log.d(TAG, "Update flow failed! Result code: " + resultCode);*/

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}




