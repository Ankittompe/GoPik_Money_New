package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Wallet_Details extends AppCompatActivity implements PaymentResultWithDataListener {
    String paymentId, signature, orderId;
    String finalprice1;
    TextView procced, recharge_now, textView2, textview3, currentbalance;
    EditText textview4;
    Integer bal;
    LinearLayout liner2, linear;
    String TAG = "finalbikepayment";
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar, backarrow;
    int amount_razorpay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet__details);
        textview4 = (EditText) findViewById(R.id.textview4);
        recharge_now = (TextView) findViewById(R.id.recharge_now);
        currentbalance = (TextView) findViewById(R.id.currentbalance);
        procced = (TextView) findViewById(R.id.proceed);
        textview3 = (TextView) findViewById(R.id.textview3);
        liner2 = (LinearLayout) findViewById(R.id.liner2);
        linear = (LinearLayout) findViewById(R.id.linear);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        /*check();*/
       /* bal= Integer.valueOf(SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
        if(bal<100){
            textView2.setText ("Your Wallet Balance is Insufficient, Please recharge.");
        }*/
        Log.e(TAG, "saiiiiiiiiiiiiiiiiiiiit" + SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()));
        valid();

        recharge_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }

        });
        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Wallet_Details.this, CustomerAcceptancePolicy_DEALER_Activity.class);
                startActivity(i);
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Wallet_Details.this, Emi_Calculator.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Wallet_Details.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        get_wallet_details();
    }

    private void valid() {
        if (SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()).equals("NA")) {
            Log.e(TAG, "ruhoiiiiiiiiiiii" + SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()));
            currentbalance.setText("₹ 0");
            procced.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);

        } else if (SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()).equals("0")) {
            currentbalance.setText("₹ 0");
            procced.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
        } else {
            procced.setVisibility(View.VISIBLE);
            linear.setVisibility(View.GONE);
            recharge_now.setVisibility(View.GONE);
            textview3.setVisibility(View.GONE);
            textview4.setVisibility(View.GONE);
            currentbalance.setText("₹" + SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()));
        }
    }

    private void validation() {
        if ((textview4.getText().toString().isEmpty())) {
            textview4.setError("Please Enter The Amount");


        } else {

            check();

        }

    }

    private void check() {
        finalprice1 = textview4.getText().toString();
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
                        checkout.open(Wallet_Details.this, options);
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


    //Sucess Method
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
        Toast.makeText(Wallet_Details.this, "Payment done successfully", Toast.LENGTH_SHORT).show();
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

        Toast.makeText(Wallet_Details.this, "Payment Failed", Toast.LENGTH_SHORT).show();

        finish();

    }
 /* private void razorPaymentResponsemethod() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        RazorPaymentResponsePojo pojo = new RazorPaymentResponsePojo(orderId,paymentId,signature);
        Log.e(TAG, "in NetworkHandler");
        Call<RazorPaymentResponse> call = restApis.razorPaymentResponsemethod(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<RazorPaymentResponse>() {
            @Override
            public void onResponse(Call<RazorPaymentResponse> call, Response<RazorPaymentResponse> response) {

                if (response.body() != null) {

                    if (response.body().getCode().equalsIgnoreCase("200")) {
                        update_wallet_credit();
                        Intent i=new Intent(Wallet_Details.this,Home.class);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<RazorPaymentResponse> call, Throwable t) {

                Log.e(TAG, "in Inner method" + t);

            }

        });
    }*/

    private void update_wallet_credit() {


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Update_wallet_credit_POJO pojo = new Update_wallet_credit_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_AMOUNT, getApplicationContext()),
                "Success", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_ID, getApplicationContext())
        );
        Log.e(TAG, "in NetworkHandler");
        Call<Update_wallet_credit_MODEL> call = restApis.update_wallet_credit(pojo);
        Log.e(TAG, "in call");
        call.enqueue(new Callback<Update_wallet_credit_MODEL>() {
            @Override
            public void onResponse(Call<Update_wallet_credit_MODEL> call, Response<Update_wallet_credit_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equalsIgnoreCase("200")) {
                      /*  Intent i=new Intent(Wallet_Details.this,CustomerAcceptancePolicy_Activity.class);
                        startActivity(i);*/
                        textview4.setEnabled(false);
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
        custPrograssbar.prograssCreate(Wallet_Details.this);
        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {
                Log.e("sdssad", "tysdfyuadgbvja");
                custPrograssbar.closePrograssBar();
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE, response.body().getPayload().get(i).getBalance(), getApplicationContext());
                                Log.e(TAG, "errorrrrdrgvgh: " + response.body().getPayload().get(i).getBalance());
                                Log.e(TAG, "errorrrrdrgvgh: " + SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()));
                                /*   currentbalance.setText("₹" +SharedPref.getStringFromSharedPref(AppConstants.BALANCE, getApplicationContext()));*/
                                valid();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Log.e("sdssad", "tysdfyuadgbvja" + t);
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

}
