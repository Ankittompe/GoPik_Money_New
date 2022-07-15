package com.qts.gopik_loan.Activity;

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
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Fragment.Dealer_QR_Code_Fragment;
import com.qts.gopik_loan.Dealer_Fragment.Home_Dealer_Fragment;
import com.qts.gopik_loan.Fragment.Contest;
import com.qts.gopik_loan.Fragment.Broker_QR_Code_Fragment;
import com.qts.gopik_loan.Fragment.HomeFragment;
import com.qts.gopik_loan.Fragment.Notification;
import com.qts.gopik_loan.Fragment.Profile;
import com.qts.gopik_loan.Fragment.Reward;
import com.qts.gopik_loan.Fragment.Tab_Fragment;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.Dealer_logout_MODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_loan.Pojo.Dealer_logoutPOJO;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    NavigationView nav;
    BottomNavigationView bnv;
    /*BottomNavigationView bnv;*/
    FrameLayout fn;
    ImageView logout, refresh, menus;
    private String version;
    String TAG = "home";
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    ChipNavigationBar chipNavigationBar;
    private WebView mWebVw;
    TextView mTxtUserName;
    ImageView mImgShare,mImgScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bmnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();

        /*  bnv = (BottomNavigationView) findViewById(R.id.bmnav);*/
        fn = (FrameLayout) findViewById(R.id.frameContainer);
        menus = (ImageView) findViewById(R.id.menus);
        logout = (ImageView) findViewById(R.id.signout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drwer);
        nav = (NavigationView) findViewById(R.id.navigationview);
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
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();

                        break;


                    case R.id.profile_drawer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new Tab_Fragment()).commit();
                        /*   bnv.setCurrentItem(3);*/
                        break;
                    case R.id.menu_login:
                        Intent it = new Intent(Home.this, LogIn.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME);
                        startActivity(it);
                        break;
                    case R.id.wallet:
                        Intent itt = new Intent(Home.this, Application_Details.class);
                        itt.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME);
                        startActivity(itt);
                        break;

                    case R.id.transaction:
                        Intent ittt = new Intent(Home.this, Wallet_Activity.class);
                        ittt.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME);
                        startActivity(ittt);

                        break;
                    case R.id.dost_qrcode:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new Broker_QR_Code_Fragment()).commit();
                        break;
                    case R.id.signout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                        new AlertDialog.Builder(Home.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Log Out")
                                .setMessage("Are you sure, you want to logout?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent it = new Intent(Home.this, LogIn.class);
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
            navMenuLogIn.findItem(R.id.signout).setVisible(false);
            navMenuLogIn.findItem(R.id.menu_login).setVisible(true);

        } else {
            Log.e(TAG, "innn");
            /*   logout.setVisibility(View.VISIBLE);*/
            navMenuLogIn.findItem(R.id.signout).setVisible(true);
            navMenuLogIn.findItem(R.id.menu_login).setVisible(false);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new Broker_QR_Code_Fragment()).commit();
            }
        });
        generateQRCode();
        broker_profile_details();
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
        drawerLayout = (DrawerLayout) mActivityName.findViewById(R.id.drwer);
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
    private void broker_profile_details() {
        Broker_profile_details_POJO pojo = new Broker_profile_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_details_MODEL> call = restApis.broker_profile_details(pojo);
        call.enqueue(new Callback<Broker_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_details_MODEL> call, Response<Broker_profile_details_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        mTxtUserName.setText(response.body().getPayload().getProfile().get(0).getBroker_name());
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
    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.homebtn:
                        Log.e("HomeClass", "Inside home");
                        fragment = new HomeFragment();
                        break;


                  /*  case R.id.profile:
                        Log.e("HomeClass", "Inside profile");
                        fragment = new Profile();
                        break;*/
                    case R.id.myprofile:
                        Log.e("HomeClass", "Inside profile");
                        fragment = new Tab_Fragment();
                        break;
                    case R.id.wallet_type:
                        fragment = new Reward();
                        break;

                    case R.id.notification:
                        fragment = new Notification();
                        break;
                      case R.id.contest:
                        fragment = new Contest();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();

            }
        });
    }


    public void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();

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
            if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.HOME_FRAGMENT)) {
                loadFragment(new HomeFragment());

            }  else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.PROFILE_FRAGMENT)) {
                loadFragment(new Profile());

            }
            else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.NOTIFICATION)) {
                loadFragment(new Notification());

            }
            else if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.REWARD_FRAGMENT)) {
                loadFragment(new Reward());}

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home.super.onBackPressed();
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
//            chipNavigationBar.setItemSelected(R.id.not_dealer,true);
        }
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, "10", getApplicationContext());  // 10 for no notification
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!", Toast.LENGTH_LONG).show();
        mImgScanner.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();
        return false;
        // Disable back button..............
    }
}


