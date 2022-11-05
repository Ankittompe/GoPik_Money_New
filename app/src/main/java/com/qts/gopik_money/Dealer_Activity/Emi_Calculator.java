package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.qts.gopik_money.Model.Loan_calculation_Model;
import com.qts.gopik_money.Pojo.Calculation_data_POJO;
import com.qts.gopik_money.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_money.Pojo.Loan_calculation_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Emi_Calculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView  loan_amount_2;
    TextView processing_fees;
    TextView gopik_protect_insurance;
    TextView upfront_payment_by_customer;
    TextView emi;
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView interst_per_annum;
    TextView calculate;
    Spinner  spino;
    EditText down_payment;
    EditText couponcode;
    EditText subsidy;
    LinearLayout linearlayout_aftercalculation;
    TextView apply;
    TextView customer_price;
    TextView applycoupon;
    String[] courses = { "18","24","36" };
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView backarrow;
    Integer custprice;
    Integer cust;
    ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi__calculator);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        customer_price = (TextView) findViewById(R.id.customer_price);
        calculate= (TextView) findViewById(R.id.calculate);
        apply= (TextView) findViewById(R.id.apply);

        linearlayout_aftercalculation=(LinearLayout)findViewById(R.id.linearlayout_aftercalculation);
        down_payment = (EditText) findViewById(R.id.down_payment);
        loan_amount_2 = (TextView) findViewById(R.id.loan_amount_2);
        processing_fees = (TextView) findViewById(R.id.processing_fees);
        gopik_protect_insurance = (TextView) findViewById(R.id.gopik_protect_insurance);
        upfront_payment_by_customer = (TextView) findViewById(R.id.upfront_payment_by_customer);
        emi = (TextView) findViewById(R.id.emi);
        interst_per_annum = (TextView) findViewById(R.id.interst_per_annum);
        applycoupon= (TextView) findViewById(R.id.applycoupon);
        couponcode= (EditText) findViewById(R.id.couponcode);
        customer_price.setText(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()));
        spino = findViewById(R.id.choosehubb);
        spino.setOnItemSelectedListener(this);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        subsidy=(EditText)findViewById(R.id.subsidy);

               ad = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);
        calculate.setOnClickListener(view -> validation());
        apply.setOnClickListener(view -> {
            /*get_wallet_details();*/
            calculation_data();
        });
        applycoupon.setOnClickListener(view -> {
            Intent it = new Intent(Emi_Calculator.this, Promocode_Activity.class);
            startActivity(it);

        });
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Emi_Calculator.this, ProductDetails_DEALER.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Emi_Calculator.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        custprice=Integer.valueOf(customer_price.getText().toString());
        cust=  custprice*(15/100);
      /*  if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY) != null)

        {

            if (getIntent().getStringExtra(AppConstants.ACTFRAG_TYPE_KEY).equals(AppConstants.TRENDI)) {
                Log.e(TAG, "from service");
 couponcode.setText(SharedPref.getStringFromSharedPref(AppConstants.LOANPRO_PAYMENT,getApplicationContext()));
            } else {
                Log.e(TAG, "from adapter");
                couponcode.setText("Use Coupons");
            }
        }*/

       /* down_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cust <= down_payment.getText().toString()){

                }
            }
        });*/
    }

    private void validation() {
        if ((down_payment.getText().toString().isEmpty()) ) {
            down_payment.setError("Please Enter The Down Payment");


        }
        else if ((subsidy.getText().toString().isEmpty()) ) {
            subsidy.setError("Please Enter Subsidy");


        }
        else{

            loan_calculation();

        }

    }







    private void calculation_data() {

        custPrograssbar.prograssCreate(Emi_Calculator.this);
        Calculation_data_POJO pojo = new Calculation_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND,getApplicationContext()),
                "200",
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                "Dealer", SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_ID,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_MODEL,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE,getApplicationContext())
                ,
                subsidy.getText().toString()   ,SharedPref.getStringFromSharedPref(AppConstants.DOWN_PAYMENT,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.LOAN_PAYMENT,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.TENURE,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_RATE,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_EMI,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_FEES,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_INSURANCE_FEES,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_UP_PAY,getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TOTAL_AMOUNT,getApplicationContext())
                ,"NA", SharedPref.getStringFromSharedPref(AppConstants.BRAND,getApplicationContext()),"1","20");


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Calculation_data_MODEL> call = restApis.calculation_data(pojo);
        call.enqueue(new Callback<Calculation_data_MODEL>() {
            @Override
            public void onResponse(Call<Calculation_data_MODEL> call, Response<Calculation_data_MODEL> response) {
                Log.e("sdssad","tysdfyuadgbvja");
                if (response.body() != null) {


                    if(response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE,response.body().getPayload().getCust_code(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE,response.body().getPayload().getUser_code(),getApplicationContext());

                        SharedPref.saveStringInSharedPref(AppConstants.SUBSIDY,response.body().getPayload().getSubsidy(),getApplicationContext());
                        get_wallet_details();
                    }
                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Calculation_data_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }

    private void get_wallet_details() {
        custPrograssbar.prograssCreate(Emi_Calculator.this);
        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                "Gopik Wallet",SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    custPrograssbar.closePrograssBar();
                    if(response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE,response.body().getPayload().get(i).getBalance(),getApplicationContext());
                                Intent it=new Intent(Emi_Calculator.this,Wallet_Details.class);
                                startActivity(it);
                            }
                        }
                    }
                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }

    private void loan_calculation() {
        custPrograssbar.prograssCreate(Emi_Calculator.this);

        Loan_calculation_POJO pojo = new Loan_calculation_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND,getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_ID,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_MODEL_NAME,getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.GET_SPINNER_POSITION,getApplicationContext()),subsidy.getText().toString() ,down_payment.getText().toString());


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Loan_calculation_Model> call = restApis.loan_calculation(pojo);
        call.enqueue(new Callback<Loan_calculation_Model>() {
            @Override
            public void onResponse(Call<Loan_calculation_Model> call, Response<Loan_calculation_Model> response) {
                Log.e("sdssad","tysdfyuadgbvja");
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        linearlayout_aftercalculation.setVisibility(View.VISIBLE);


                        loan_amount_2.setText(response.body().getPayload().getLoan_amount());
                        processing_fees.setText(response.body().getPayload().getProcc_fees());
                        gopik_protect_insurance.setText(response.body().getPayload().getIns_fees());
                        upfront_payment_by_customer.setText(response.body().getPayload().getUp_pay());
                        emi.setText(response.body().getPayload().getEmi());
                        interst_per_annum.setText(response.body().getPayload().getRate());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_MODEL,response.body().getPayload().getProduct_model(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DOWN_PAYMENT,response.body().getPayload().getDown_payment(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.LOAN_PAYMENT,response.body().getPayload().getLoan_amount(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.TENURE,response.body().getPayload().getTenure(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_RATE,response.body().getPayload().getRate(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_EMI,response.body().getPayload().getEmi(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_FEES,response.body().getPayload().getProcc_fees(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_INSURANCE_FEES,response.body().getPayload().getIns_fees(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_UP_PAY,response.body().getPayload().getUp_pay(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PRODUCT_TOTAL_AMOUNT,response.body().getPayload().getTotal_amount(),getApplicationContext());

                    }
                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Loan_calculation_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int index = spino.getSelectedItemPosition();

        SharedPref.saveStringInSharedPref(AppConstants.GET_SPINNER_POSITION,courses[index] , getApplicationContext());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        //Do nothing
    }
}