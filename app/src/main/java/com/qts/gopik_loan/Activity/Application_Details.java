package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Adapter.Application_Adapter;
import com.qts.gopik_loan.Adapter.Top_Three_Adapter;
import com.qts.gopik_loan.Model.Fetch_current_loanappliation_list_MODEL;
import com.qts.gopik_loan.Model.Fetch_loan_application_list_MODEL;
import com.qts.gopik_loan.Pojo.Fetch_current_loanappliation_list_POJO;
import com.qts.gopik_loan.Pojo.Fetch_loan_application_list_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Application_Details extends AppCompatActivity {
    LinearLayout applicationlayout;
    RecyclerView applicationlist;
    CustPrograssbar custPrograssbar;
    Application_Adapter application_adapter;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> user_code = new ArrayList<>();
    ArrayList<String> customer_code = new ArrayList<>();
    ArrayList<String> customer_name = new ArrayList<>();
    ArrayList<String> customer_mobile = new ArrayList<>();
    ArrayList<String> customer_email = new ArrayList<>();
    ArrayList<String> customer_address = new ArrayList<>();
    ArrayList<String> customer_dob = new ArrayList<>();
    ArrayList<String> loan_type = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> date_time = new ArrayList<>();
    ArrayList<String> application_status = new ArrayList<>();
    ArrayList<String> cust_tc_response = new ArrayList<>();
    ArrayList<String> info_img = new ArrayList<>();
    ImageView hometoolbar, backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application__details);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getApplicationContext());
        applicationlist = (RecyclerView) findViewById(R.id.applicationlist2);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Application_Details.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Application_Details.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        fetch_loan_application_list();
    }

    private void fetch_loan_application_list() {
        custPrograssbar.prograssCreate(Application_Details.this);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_loan_application_list_POJO pojo = new Fetch_loan_application_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

        Call<Fetch_loan_application_list_MODEL> call = restApis.fetch_loan_application_list(pojo);
        call.enqueue(new Callback<Fetch_loan_application_list_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_loan_application_list_MODEL> call, Response<Fetch_loan_application_list_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");

                        ArrayList<String> id = new ArrayList<>();
                        ArrayList<String> user_code = new ArrayList<>();
                        ArrayList<String> customer_code = new ArrayList<>();
                        ArrayList<String> customer_name = new ArrayList<>();
                        ArrayList<String> customer_mobile = new ArrayList<>();
                        ArrayList<String> customer_email = new ArrayList<>();
                        ArrayList<String> customer_address = new ArrayList<>();
                        ArrayList<String> customer_dob = new ArrayList<>();
                        ArrayList<String> loan_type = new ArrayList<>();
                        ArrayList<String> state = new ArrayList<>();
                        ArrayList<String> date_time = new ArrayList<>();
                        ArrayList<String> application_status = new ArrayList<>();
                        ArrayList<String> cust_tc_response = new ArrayList<>();

                        ArrayList<String> loan_amt_approve = new ArrayList<>();
                        ArrayList<String> emi_amt = new ArrayList<>();
                        ArrayList<String> tenure = new ArrayList<>();
                        ArrayList<String> rate_of_interest = new ArrayList<>();
                        ArrayList<String> comments = new ArrayList<>();
                        ArrayList<String> reason_of_rejection = new ArrayList<>();
                        ArrayList<String> info_img = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {

                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                id.add(response.body().getPayload().get(i).getId());
                                user_code.add(response.body().getPayload().get(i).getUser_code());
                                customer_code.add(response.body().getPayload().get(i).getCustomer_code());
                                customer_name.add(response.body().getPayload().get(i).getCustomer_name());
                                loan_type.add(response.body().getPayload().get(i).getLoan_type());
                                customer_mobile.add(response.body().getPayload().get(i).getCustomer_mobile());
                                application_status.add(response.body().getPayload().get(i).getApplication_status());
                                loan_amt_approve.add(response.body().getPayload().get(i).getLoan_amt_approve());
                                emi_amt.add(response.body().getPayload().get(i).getEmi_amt());
                                tenure.add(response.body().getPayload().get(i).getTenure());
                                rate_of_interest.add(response.body().getPayload().get(i).getRate_of_interest());
                                comments.add(response.body().getPayload().get(i).getComments());
                                reason_of_rejection.add(response.body().getPayload().get(i).getReason_of_rejection());


                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getApplicationContext(), LinearLayoutManager.VERTICAL, false
                                    );
                                    applicationlist.setLayoutManager(layoutManager);
                                    applicationlist.setItemAnimator(new DefaultItemAnimator());


                                    application_adapter = new Application_Adapter(getApplicationContext(), id, user_code, customer_code, customer_mobile,
                                            customer_name, customer_email, customer_address, customer_dob, loan_type, state, date_time, application_status, cust_tc_response,loan_amt_approve,
                                            emi_amt,tenure,rate_of_interest,comments ,reason_of_rejection,info_img);
                                    applicationlist.setAdapter(application_adapter);
                                    Log.e("Body", "body3");
                                }

                            }
                        }
                        else if(response.body().getPayload().size()==2){
                            Log.e("Body", "body3777777"+response.body().getPayload().size());
                            Toast.makeText(getApplicationContext(), "No application list is avaliable for now", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Fetch_loan_application_list_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

}