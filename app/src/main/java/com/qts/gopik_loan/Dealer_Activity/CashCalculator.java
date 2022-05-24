package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashCalculator extends AppCompatActivity  implements PaymentResultWithDataListener {
    String paymentId,signature,orderId;
    String finalprice1;
    TextView procced,recharge_now,textView2,textview3,currentbalance;
    EditText textview4;
    Integer bal;
    LinearLayout liner2,linear;
    String TAG = "finalbikepayment";
    Integer val1;
    Integer val2;
    CustPrograssbar custPrograssbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_calculator);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        textview4 = (EditText) findViewById(R.id.textview4);
        recharge_now = (TextView) findViewById(R.id.recharge_now);
        currentbalance = (TextView) findViewById(R.id.currentbalance);

        procced = (TextView) findViewById(R.id.proceed);
        textview3= (TextView) findViewById(R.id.textview3);
        liner2= (LinearLayout) findViewById(R.id.liner2);
        linear= (LinearLayout) findViewById(R.id.linear);

        get_wallet_details();
        val1= Integer.valueOf(SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
        val2= Integer.valueOf(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE,getApplicationContext()));
        Log.e("bjjvbdfj","hbdshfjd"+SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
        Log.e("bjjvbdfj","hbdshfjd"+SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE,getApplicationContext()));
        if(val1>val2){
            Log.e("bjjvbdfj","hbdshfjd"+val1);
            Log.e("bjjvbdfj","hbdshfjd"+val2);
            linear.setVisibility(View.GONE);
            liner2.setVisibility(View.GONE);
            procced.setVisibility(View.VISIBLE);
            recharge_now.setVisibility(View.GONE);
            currentbalance.setText(SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
        }
        else{
            procced.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
            liner2.setVisibility(View.VISIBLE);
            recharge_now.setVisibility(View.VISIBLE);


        }
        recharge_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }

        });
        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(CashCalculator.this,Cash_Details.class);
                startActivity(it);
            }
        });
    }

    private void check() {
        finalprice1 = "100";
        Float amount = Float.parseFloat(finalprice1);
        amount = amount * 100;
        startPayment(amount);
    }

    private void startPayment(Float amount) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razorpay_key));
        final Activity activity = this;

        try {
            Map opt = new HashMap();
            opt.put("name", "GoPik Dost");
            opt.put("currency", "INR");
            opt.put("amount", amount);

            int paise,mantisa;
            float rs,exponent;
            rs=Float.parseFloat(String.valueOf(1));
            mantisa=(int)rs;
            exponent=rs-(float)mantisa;
            paise=(int)((mantisa*100) + (exponent*100));

            RequestBody rb_name = RequestBody.create(MediaType.parse("multipart/form-data") , "YoYo Rydes");
            RequestBody rb_currency = RequestBody.create(MediaType.parse("multipart/form-data") , "1");
            RequestBody rb_amount = RequestBody.create(MediaType.parse("multipart/form-data") , String.valueOf(paise));

            RazorpayOrderPojo data = new RazorpayOrderPojo(String.valueOf(paise) , "INR");
            RestApis restApis = RetrofitMaker.razorPayInstanceMaker().create(RestApis.class);
            restApis.createNewOrderID(data).enqueue(new Callback<RazorpayOrderResponse>() {
                @Override
                public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {
                    Log.e(TAG , "New Order ID from Razorpay: "+response.body().getId());
                    Log.e(TAG , "New Order ID from Razorpay: "+response.body().getStatus());
                    Log.e(TAG , "New Order ID from Razorpay: "+response.body().getAmount());
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_ID,response.body().getId(),getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_STATUS,response.body().getStatus(),getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_AMOUNT,response.body().getAmount(),getApplicationContext());

                    try {
                        JSONObject options = new JSONObject();
                        options.put("name", "GoPik Dost");
                        options.put("currency", "INR");
                        options.put("amount", amount);
                        options.put("order_id", response.body().getId());

                        JSONObject preFill = new JSONObject();
                        preFill.put("contact", "8327754247");
                        preFill.put("email", "example@gopikdost.com");
                        options.put("prefill", preFill);
                        checkout.open(CashCalculator.this, options);
                    }catch (Exception e){
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

        SharedPref.saveStringInSharedPref(AppConstants.RZPPAYMENTID,paymentData.getPaymentId(),getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPORDERID,paymentData.getOrderId(),getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPPAYMENTID,paymentData.getPaymentId(),getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.RZPORDERID,paymentData.getOrderId(),getApplicationContext());
        int payment_status = 1;
        int code = 0;
        String responsee = "";
        Toast.makeText(CashCalculator.this, "Payment done successfully", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"paymentId > "+paymentId);
        Log.e(TAG,"signature > "+signature);
        Log.e(TAG,"orderId > "+orderId);
        Log.e(TAG,"paymentData "+paymentData);

        /*  razorPaymentResponsemethod();*/


        update_wallet_credit();
    }



    //Error Method
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        paymentId = paymentData.getPaymentId();
        signature = paymentData.getSignature();
        orderId = paymentData.getOrderId();
        int payment_status = 0;
        String razorpayPaymentID = "";
        Log.e(TAG,"error payment"+s);

        Toast.makeText(CashCalculator.this, "Payment Failed", Toast.LENGTH_SHORT).show();

        finish();

    }
    private void update_wallet_credit() {
        custPrograssbar.prograssCreate(CashCalculator.this);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Update_wallet_credit_POJO pojo = new Update_wallet_credit_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()),
                "Gopik Wallet",SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_AMOUNT,getApplicationContext()),
                "Success",  SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_ID,getApplicationContext())
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
                        currentbalance.setText(SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
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
                        custPrograssbar.closePrograssBar();
                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE,response.body().getPayload().get(i).getBalance(),getApplicationContext());
                                Log.e(TAG, "errorrrrdrgvgh: " + response.body().getPayload().get(i).getBalance());
                                Log.e(TAG, "errorrrrdrgvgh: " + SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));
                                currentbalance.setText(SharedPref.getStringFromSharedPref(AppConstants.BALANCE,getApplicationContext()));


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
}

