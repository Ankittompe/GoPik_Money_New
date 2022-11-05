package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Get_wallet_details_MODEL;
import com.qts.gopik_money.Model.RazorpayOrderResponse;
import com.qts.gopik_money.Model.Update_wallet_credit_MODEL;
import com.qts.gopik_money.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_money.Pojo.RazorpayOrderPojo;
import com.qts.gopik_money.Pojo.Update_wallet_credit_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Retro.RetrofitMaker;
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
    TextView proceed;
    TextView dealername;
    TextView email;
    TextView mobno;
    String networkError = "It seems your Network is unstable . Please Try again!";

    String finalprice1;
    EditText topupamount;
    ImageView hometoolbar;
    ImageView backarrow;
    String TAG = "finalbikepayment";
    String paymentId;
    String signature;
    String orderId;
    int amount_razorpay = 0;
    String mAmount = "amount";
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
        proceed.setOnClickListener(view -> signupvalidation());
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_WALLET);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

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
        startPayment(amount);
    }
    private void startPayment(int amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razorpay_key));

        try {
            Map opt = new HashMap();
            opt.put("name", "GoPik Money");
            opt.put("currency", "INR");
            opt.put(mAmount, mAmount);


            RazorpayOrderPojo data = new RazorpayOrderPojo(String.valueOf(amount), "INR");
            RestApis restApis = RetrofitMaker.razorPayInstanceMaker().create(RestApis.class);
            restApis.createNewOrderID(data).enqueue(new Callback<RazorpayOrderResponse>() {
                @Override
                public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {

                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_ID, response.body().getId(), getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_STATUS, response.body().getStatus(), getApplicationContext());

                    int temporary_amount=Integer.parseInt(response.body().getAmount());
                    amount_razorpay = temporary_amount/100;
                    SharedPref.saveStringInSharedPref(AppConstants.RAZARPAY_AMOUNT, String.valueOf(amount_razorpay), getApplicationContext());
                    try {
                        JSONObject options = new JSONObject();
                        options.put("name", "GoPik Money");
                        options.put("currency", "INR");
                        options.put(mAmount, amount);
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
                    Toast.makeText(Recharge__Wallet_DEALER_Activity.this, networkError, Toast.LENGTH_SHORT).show();
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


    }




    //Error Method
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        paymentId = paymentData.getPaymentId();
        signature = paymentData.getSignature();
        orderId = paymentData.getOrderId();
        int payment_status = 0;
        String razorpayPaymentID = "";
        Toast.makeText(Recharge__Wallet_DEALER_Activity.this, "Payment Failed", Toast.LENGTH_SHORT).show();

        finish();

    }

    private void update_wallet_credit() {
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Update_wallet_credit_POJO pojo = new Update_wallet_credit_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_AMOUNT, getApplicationContext()),
                "Success", SharedPref.getStringFromSharedPref(AppConstants.RAZARPAY_ID, getApplicationContext())
        );
        Call<Update_wallet_credit_MODEL> call = restApis.update_wallet_credit(pojo);
        call.enqueue(new Callback<Update_wallet_credit_MODEL>() {
            @Override
            public void onResponse(Call<Update_wallet_credit_MODEL> call, Response<Update_wallet_credit_MODEL> response) {

                if (response.body() != null&&response.body().getCode().equalsIgnoreCase("200")) {
                        get_wallet_details();
                }
            }

            @Override
            public void onFailure(Call<Update_wallet_credit_MODEL> call, Throwable t) {
                Toast.makeText(Recharge__Wallet_DEALER_Activity.this, networkError, Toast.LENGTH_SHORT).show();
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
                if (response.body() != null&&response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE,response.body().getPayload().get(i).getBalance(),getApplicationContext());
                                Intent it = new Intent(Recharge__Wallet_DEALER_Activity.this, MainActivity.class);
                                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_WALLET);
                                startActivity(it);

                            }
                        }

                    else  {
                        Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(),networkError,Toast.LENGTH_LONG).show();
            }

        });

    }
    //Sucess Method
}

