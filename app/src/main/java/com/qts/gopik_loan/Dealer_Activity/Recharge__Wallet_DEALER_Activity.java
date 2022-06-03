package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Get_wallet_details_MODEL;
import com.qts.gopik_loan.Model.RazorpayOrderResponse;
import com.qts.gopik_loan.Model.Update_wallet_credit_MODEL;
import com.qts.gopik_loan.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_loan.Pojo.RazorpayOrderPojo;
import com.qts.gopik_loan.Pojo.Update_wallet_credit_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Retro.RetrofitMaker;
import com.qts.gopik_loan.Utils.CustPrograssbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recharge__Wallet_DEALER_Activity extends AppCompatActivity implements PaymentResultWithDataListener {
    TextView proceed,dealername,email,mobno;
    CustPrograssbar custPrograssbar;
    String finalprice1;
    EditText topupamount;
    ImageView hometoolbar, backarrow;
    String TAG = "finalbikepayment";
    String paymentId, signature, orderId;
    int amount_razorpay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge___wallet__d_e_a_l_e_r_);
        proceed=(TextView)findViewById(R.id.proceed);
        dealername=(TextView)findViewById(R.id.dealername);
        email=(TextView)findViewById(R.id.email);
        mobno=(TextView)findViewById(R.id.mobno);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        topupamount=(EditText) findViewById(R.id.topupamount);
        dealername.setText(SharedPref.getStringFromSharedPref(AppConstants.NAME_SUBUSER,getApplicationContext()));
        email.setText(SharedPref.getStringFromSharedPref(AppConstants.DEALER_EMAIL,getApplicationContext()));
        mobno.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()));
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupvalidation();
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_WALLET);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });

    }

    private void signupvalidation() {

        if ((topupamount.getText().toString().isEmpty())) {
            topupamount.setError("Please Enter Amount");


        }  else {

            check();

        }
    }
    private void check() {
        finalprice1 = topupamount.getText().toString();
        int amount = Integer.parseInt(finalprice1) * 100;
        //Log.e("Amount",Float.toString(amount));
        startPayment(amount);
    }

    private void startPayment(int amount) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razorpay_key));
        final Activity activity = this;

        try {
            Map opt = new HashMap();
            opt.put("name", "GoPik Money");
            opt.put("currency", "INR");
            opt.put("amount", "amount");

//            int paise, mantisa;
//            float rs, exponent;
//            rs = Float.parseFloat(String.valueOf(amount));
//            mantisa = (int) rs;
//            exponent = rs - (float) mantisa;
//            paise = (int) ((mantisa ) + (exponent * 100));

//            RequestBody rb_name = RequestBody.create(MediaType.parse("multipart/form-data"), "GoPik Dost");
//            RequestBody rb_currency = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(paise));
//            RequestBody rb_amount = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(paise));

            RazorpayOrderPojo data = new RazorpayOrderPojo(String.valueOf(amount), "INR");
            RestApis restApis = RetrofitMaker.razorPayInstanceMaker().create(RestApis.class);
            restApis.createNewOrderID(data).enqueue(new Callback<RazorpayOrderResponse>() {
                @Override
                public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {
                 /*   Log.e(TAG, "New Order ID from Razorpay: " + response.body().getId());
                    Log.e(TAG, "New Order ID from Razorpay: " + response.body().getStatus());
                    Log.e(TAG, "New Order ID from Razorpay: " + response.body().getAmount());*/
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_ID, response.body().getId(), getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_STATUS, response.body().getStatus(), getApplicationContext());

                    Log.e(TAG, "sai " + amount);
                    int temporary_amount=Integer.parseInt(response.body().getAmount());
                    amount_razorpay = temporary_amount/100;
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_AMOUNT, String.valueOf(amount_razorpay), getApplicationContext());
                    try {
                        JSONObject options = new JSONObject();
                        options.put("name", "GoPik Money");
                        options.put("currency", "INR");
                        options.put("amount", amount);
                        options.put("order_id", response.body().getId());

                        JSONObject preFill = new JSONObject();
                        preFill.put("contact", "8327754247");
                        preFill.put("email", "gopikmoney@gmail.com");
                        options.put("prefill", preFill);
                        checkout.open(Recharge__Wallet_DEALER_Activity.this, options);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<RazorpayOrderResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        paymentId = paymentData.getPaymentId();
        signature = paymentData.getSignature();
        orderId = paymentData.getOrderId();

        SharedPref.saveStringInSharedPref(AppConstants.RZPPAYMENTID, paymentData.getPaymentId(), getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPORDERID, paymentData.getOrderId(), getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPPAYMENTID, paymentData.getPaymentId(), getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPORDERID, paymentData.getOrderId(), getApplicationContext());
        int payment_status = 1;
        int code = 0;
        String responsee = "";
        Toast.makeText(Recharge__Wallet_DEALER_Activity.this, "Payment done successfully", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "paymentId > " + paymentId);
        Log.e(TAG, "signature > " + signature);
        Log.e(TAG, "orderId > " + orderId);

        Log.e(TAG, "paymentData " + paymentData.getUserEmail());
        update_wallet_credit();
        /*  razorPaymentResponsemethod();*/


    }




    //Error Method
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        paymentId = paymentData.getPaymentId();
        signature = paymentData.getSignature();
        orderId = paymentData.getOrderId();
        int payment_status = 0;
        String razorpayPaymentID = "";
        Log.e(TAG, "error payment" + s);

        Toast.makeText(Recharge__Wallet_DEALER_Activity.this, "Payment Failed", Toast.LENGTH_SHORT).show();

        finish();

    }

    private void update_wallet_credit() {
        Log.e(TAG, "in NetworkHandler 1");

        Log.e(TAG, "in NetworkHandler 2");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e(TAG, "in NetworkHandler 3");
        Update_wallet_credit_POJO pojo = new Update_wallet_credit_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_AMOUNT, getApplicationContext()),
                "Success", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_ID, getApplicationContext())
        );
        Log.e(TAG, "in NetworkHandler 4");
        Log.e(TAG, "in NetworkHandler");
        Call<Update_wallet_credit_MODEL> call = restApis.update_wallet_credit(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Update_wallet_credit_MODEL>() {
            @Override
            public void onResponse(Call<Update_wallet_credit_MODEL> call, Response<Update_wallet_credit_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equalsIgnoreCase("200")) {
                        //custPrograssbar.closePrograssBar();
                      /*  Intent i=new Intent(Wallet_Details.this,CustomerAcceptancePolicy_Activity.class);
                        startActivity(i);*/
                        get_wallet_details();
                    }
                }
            }

            @Override
            public void onFailure(Call<Update_wallet_credit_MODEL> call, Throwable t) {

                Log.e(TAG, "in Inner method" + t);

            }

        });
    }

    private void get_wallet_details() {

        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                "Gopik Wallet",SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {
                Log.e("sdssad","tysdfyuadgbvja");

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if(response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE,response.body().getPayload().get(i).getBalance(),getApplicationContext());
                                Log.e(TAG, "errorrrrdrgvgh: " + response.body().getPayload().get(i).getBalance());
                                Log.e(TAG, "errorrrrdrgvgh: " + SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));

                                Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
                                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_WALLET);
                                startActivity(it);

                            }
                        }
                    }
                    else  {
                        Toast.makeText(getApplicationContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {

                Log.e("sdssad","tysdfyuadgbvja"+t);
                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }
    //Sucess Method
}

