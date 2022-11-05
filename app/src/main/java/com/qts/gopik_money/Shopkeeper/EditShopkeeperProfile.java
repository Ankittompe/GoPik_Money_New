package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_money.Model.Shopkeeper_profile_details_MODEL;
import com.qts.gopik_money.Model.Shopkeeper_profile_update_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_details_POJO;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_update_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditShopkeeperProfile extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    EditText ifsccode;
    EditText  email;
    EditText  name1;
    EditText  state;
    EditText shopname;
    EditText shopaddress;

    Integer x = 0;
    LinearLayout  st;

    TextView bankname ;
    TextView tt;
    TextView phonenumber;
    TextView address;
    TextView branch;
    TextView btn_countinue;
    TextView statetextview;
    TextView reaccountno;
    TextView accountno;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    String networkError = "It seems your Network is unstable . Please Try again!";
    ImageView  visible;

    ImageView hometoolbar;
    ImageView  backarrow;

    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopkeeper_profile);
        branch = (TextView) findViewById(R.id.branch);
        btn_countinue = (TextView) findViewById(R.id.btn_countinuee);
        address = (TextView) findViewById(R.id.address);
        bankname = (TextView) findViewById(R.id.bankname);
        ifsccode = (EditText) findViewById(R.id.ifsccode);
        accountno = (TextView) findViewById(R.id.accountno);
        email = (EditText) findViewById(R.id.email);
        name1 = (EditText) findViewById(R.id.name1);
        state = (EditText) findViewById(R.id.state);
        shopaddress= (EditText) findViewById(R.id.shopaddress);
        shopname= (EditText)findViewById(R.id.shopname);
        phonenumber = (TextView) findViewById(R.id.phonenumber);

        reaccountno = (TextView) findViewById(R.id.reaccountno);
        custPrograssbar = new CustPrograssbar();

        choose_identity = (Spinner) findViewById(R.id.choose_identity);
        statetextview = (TextView) findViewById(R.id.statetextview);
        statespinner = (LinearLayout) findViewById(R.id.statespinner);
        st = (LinearLayout) findViewById(R.id.st);
        tt = (TextView) findViewById(R.id.tt);

        visible = (ImageView) findViewById(R.id.eye);



        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getApplicationContext());
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));
      /*  st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.VISIBLE);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.VISIBLE);
            }
        });*/

        statetextview.setOnTouchListener((view, motionEvent) -> {
            statespinner.setVisibility(View.VISIBLE);

            return true;
        });


        btn_countinue.setOnClickListener(view -> {
            statespinner.setVisibility(View.GONE);
            shopkeeper_profile_update();
        });



     /*   visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visible.setVisibility(View.VISIBLE);

                accountno.setTransformationMethod(new PasswordTransformationMethod());
            }
        });*/

        //spinner1
        ArrayAdapter ad
                = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                statelocation);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_identity.setAdapter(ad);
        choose_identity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = choose_identity.getSelectedItemPosition();

                SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER, statelocation[index], getApplicationContext());
                /*       SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER_ITEM,statelocation,getContext());*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (x == 1) {
            statespinner.setVisibility(View.GONE);
        }
        shopkeeper_profile_details();
       /* SharedPref.saveStringInSharedPref(AppConstants.STATETEXTVIEW,statetextview.getText().toString(),getContext());
        if ((SharedPref.getStringFromSharedPref(AppConstants.STATETEXTVIEW,getContext())).equals("State")) {

            statespinner.setVisibility(View.VISIBLE);

        } else  {

            statetextview.setVisibility(View.VISIBLE);
            statespinner.setVisibility(View.GONE);
            statetextview.setText(SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER,getContext()));

        }*/
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(EditShopkeeperProfile.this, HomeShopkeeper.class);
            startActivity(it);

        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(EditShopkeeperProfile.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });

        return;

    }

    private void shopkeeper_profile_details() {
        custPrograssbar.prograssCreate(EditShopkeeperProfile.this);
        Shopkeeper_profile_details_POJO pojo = new Shopkeeper_profile_details_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_details_MODEL> call = restApis.shopkeeper_profile_details(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeper_profile_details_MODEL> call, Response<Shopkeeper_profile_details_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getShopkeeper_code(), getApplicationContext());
                        if ((response.body().getPayload().getProfile().get(0).getShopkeeper_name().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            name1.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_name());
                            email.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_email());

                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME,response.body().getPayload().getProfile().get(0).getShopkeeper_name(),getApplicationContext());
                            shopname.setText(response.body().getPayload().getProfile().get(0).getShop_name());
                            shopaddress.setText(response.body().getPayload().getProfile().get(0).getShop_address());
                            statetextview.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_state());
                            /*  reaccountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());*/
                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getApplicationContext());
                            /*      int state_position=statelocation.get*/



                        }

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void afterTextChanged(Editable editable) {



    }


    private void shopkeeper_profile_update() {
        custPrograssbar.prograssCreate(getApplicationContext());

        Shopkeeper_profile_update_POJO pojo = new Shopkeeper_profile_update_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                        name1.getText().toString(),
                        SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER,getApplicationContext()),
                        email.getText().toString(),shopname.getText().toString(),shopaddress.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_update_MODEL> call = restApis.shopkeeper_profile_update(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_update_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeper_profile_update_MODEL> call, Response<Shopkeeper_profile_update_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {

                        x = 1;
                        Intent it = new Intent(EditShopkeeperProfile.this, HomeShopkeeper.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_PROFILE_FRAGMENT);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);


                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Shopkeeper_profile_update_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*int index = choose_identity.getSelectedItemPosition();
        SharedPref.saveStringInSharedPref(AppConstants.STATE_VALUE, statelocation[index], getContext());*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void account(String acc) {
        custPrograssbar.prograssCreate(getApplicationContext());

        RestApis restApis = NetworkHandler.instanceMaker3().create(RestApis.class);
        Call<ACCOUNT_NO_MODEL> call = restApis.account(acc);
        call.enqueue(new Callback<ACCOUNT_NO_MODEL>() {
            @Override
            public void onResponse(Call<ACCOUNT_NO_MODEL> call, Response<ACCOUNT_NO_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();


                    address.setText(response.body().getADDRESS());
                    branch.setText(response.body().getBRANCH());

                    bankname.setText(response.body().getBANK());


                }

                else {
                    custPrograssbar.closePrograssBar();

                    ifsccode.setError("Please enter valid IFSC code");
                }

                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<ACCOUNT_NO_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                ifsccode.setError("Please enter IFSC code");
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

}