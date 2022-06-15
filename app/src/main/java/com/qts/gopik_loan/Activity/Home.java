package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.qts.gopik_loan.Fragment.Contest;
import com.qts.gopik_loan.Fragment.HomeFragment;
import com.qts.gopik_loan.Fragment.Notification;
import com.qts.gopik_loan.Fragment.Profile;
import com.qts.gopik_loan.Fragment.Profile_Details;
import com.qts.gopik_loan.Fragment.Reward;
import com.qts.gopik_loan.Fragment.Tab_Fragment;
import com.qts.gopik_loan.Model.Dealer_logout_MODEL;
import com.qts.gopik_loan.Pojo.Dealer_logoutPOJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

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
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                new AlertDialog.Builder(Home.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Log Out")
                        .setMessage("Are you sure, you want to logout?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(Home.this, LogIn.class);
                                startActivity(it);
                                *//* dealer_logout();*//*
                                SharedPref.saveBooleanInSharedPref(AppConstants.IS_LOGGED_IN, false, getApplicationContext());
                            }

                        }).setNegativeButton("no", null).show();



            }

        });
*/
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



}


