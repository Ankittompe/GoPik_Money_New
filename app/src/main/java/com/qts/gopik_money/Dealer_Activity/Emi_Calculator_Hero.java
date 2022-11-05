package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Calculation_data_MODEL;
import com.qts.gopik_money.Model.Get_wallet_details_MODEL;
import com.qts.gopik_money.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_money.Pojo.Hero_Loan_Calculation_Data_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

import org.json.JSONObject;

public class Emi_Calculator_Hero extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {
    TextView loan_amount_2 ;
    TextView processing_fees;
    TextView gopik_protect_insurance;
    TextView upfront_payment_by_customer;
    TextView emi;
    TextView interst_per_annum;
    Spinner spino;
    EditText down_payment;
    EditText couponcode;
    EditText  subsidy;
    LinearLayout linearlayout_aftercalculation;
    TextView calculate;
    TextView  apply;
    TextView  customer_price;
    TextView  applycoupon;
    String[] courses = {"6", "9", "12"};
    String[] coursesTwo = {"6", "12"};
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView  backarrow;
    Integer custprice;
    Integer cust;
    String networkError = "It seems your Network is unstable . Please Try again!";
    LinearLayout mLinLayoutSubsidy;
    LinearLayout mLinLayConvenience;
    ArrayAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calculator_hero);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        customer_price = (TextView) findViewById(R.id.customer_price);
        calculate = (TextView) findViewById(R.id.calculate);
        apply = (TextView) findViewById(R.id.apply);

        linearlayout_aftercalculation = (LinearLayout) findViewById(R.id.linearlayout_aftercalculation);
        mLinLayConvenience = (LinearLayout) findViewById(R.id.linLayConvenience);

        down_payment = (EditText) findViewById(R.id.down_payment);
        loan_amount_2 = (TextView) findViewById(R.id.loan_amount_2);
        processing_fees = (TextView) findViewById(R.id.processing_fees);
        gopik_protect_insurance = (TextView) findViewById(R.id.gopik_protect_insurance);
        upfront_payment_by_customer = (TextView) findViewById(R.id.upfront_payment_by_customer);
        emi = (TextView) findViewById(R.id.emi);
        interst_per_annum = (TextView) findViewById(R.id.interst_per_annum);
        applycoupon = (TextView) findViewById(R.id.applycoupon);
        couponcode = (EditText) findViewById(R.id.couponcode);
        customer_price.setText(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()));
        spino = findViewById(R.id.choosehubb);
        spino.setOnItemSelectedListener(this);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        subsidy = (EditText) findViewById(R.id.subsidy);


        down_payment.addTextChangedListener(this);


        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spino.setAdapter(ad);
        calculate.setOnClickListener(view -> {

            if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()).equals("Hero")) {
                validationHero();
            } else {
                validation();
            }
        });
        apply.setOnClickListener(view -> {

            calculation_data();

        });
        applycoupon.setOnClickListener(view -> {
            Intent it = new Intent(Emi_Calculator_Hero.this, Promocode_Activity.class);
            startActivity(it);

        });
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Emi_Calculator_Hero.this, ProductDetails_DEALER.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Emi_Calculator_Hero.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        custprice = Integer.valueOf(customer_price.getText().toString());
        cust = custprice * (15 / 100);

        mLinLayoutSubsidy = findViewById(R.id.linLayoutSubsidy);
        if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()).equals("Hero")) {
            mLinLayoutSubsidy.setVisibility(View.GONE);
        } else {
            mLinLayoutSubsidy.setVisibility(View.VISIBLE);
        }

    }

    private void validationHero() {
        if ((down_payment.getText().toString().isEmpty())) {
            down_payment.setError("Please Enter The Down Payment");
        } else {
            mLinLayConvenience.setVisibility(View.GONE);

        }
    }

    private void validation() {
        if ((down_payment.getText().toString().isEmpty())) {
            down_payment.setError("Please Enter The Down Payment");
        }
    }

    private void calculation_data() {

        custPrograssbar.prograssCreate(Emi_Calculator_Hero.this);

        Hero_Loan_Calculation_Data_POJO pojo = new Hero_Loan_Calculation_Data_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "200",
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_MODEL_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOWN_PAYMENT, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TENURE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LOAN_PAYMENT, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_RATE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_EMI, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_FEES, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_INSURANCE_FEES, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_UP_PAY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TOTAL_AMOUNT, getApplicationContext()));


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Calculation_data_MODEL> call = restApis.hero_calculation_data(pojo);
        call.enqueue(new Callback<Calculation_data_MODEL>() {
            @Override
            public void onResponse(Call<Calculation_data_MODEL> call, Response<Calculation_data_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE, response.body().getPayload().getCust_code(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getUser_code(), getApplicationContext());


                        get_wallet_details();
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (response.code() == 400) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String errorMsg = jObjError.getString("message");
                            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Calculation_data_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void get_wallet_details() {
        custPrograssbar.prograssCreate(Emi_Calculator_Hero.this);
        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE, response.body().getPayload().get(i).getBalance(), getApplicationContext());

                                Intent it = new Intent(Emi_Calculator_Hero.this, HeroWallet_DetailsActivity.class);
                                startActivity(it);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int index = spino.getSelectedItemPosition();

        SharedPref.saveStringInSharedPref(AppConstants.GET_SPINNER_POSITION, courses[index], getApplicationContext());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        // Do nothing
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Dp nothing
    }
    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().isEmpty()) {
            int mDownPrice = Integer.parseInt(editable.toString());
            if (mDownPrice >= 6000) {
                ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
            } else {
                ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesTwo);
            }
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spino.setAdapter(ad);
            ad.notifyDataSetChanged();
        } else {
            ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spino.setAdapter(ad);
            ad.notifyDataSetChanged();
        }
    }
}