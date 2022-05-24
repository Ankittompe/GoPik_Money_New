package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Fetch_customer_data_MODEL;
import com.qts.gopik_loan.Model.Submit_customer_application_MODEL;
import com.qts.gopik_loan.Model.Submit_loan_application_MODEL;
import com.qts.gopik_loan.Pojo.Fetch_customer_data_POJO;
import com.qts.gopik_loan.Pojo.Submit_customer_application_POJO;
import com.qts.gopik_loan.Pojo.Submit_loan_application_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_Details_Verification_Dealer extends AppCompatActivity {
    TextView salution1,customername1,emailid1,mobileno1,maritialstatus1,qualification1,accomodation1,currentaddress1,iscurrentaddress1,
            permanentaddress1,noofyears1,organizationtype1,industrytype1,employementtype1,totalexpbussiness1,totalturnover1,
            annualincome1,companyname1,officeaddress1,officepincode1,totalworfexp1,grossmonthlyincome1;
    String TAG="loginotp";
    LinearLayout salaried,self_employed;
    CustPrograssbar custPrograssbar;
    TextView   btsend;
    ImageView hometoolbar,backarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__details__d_e_a_l_e_r__verification);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        salution1=(TextView) findViewById(R.id.salution1);
        customername1=(TextView) findViewById(R.id.customername1);
        emailid1=(TextView) findViewById(R.id.emailid1);
        mobileno1=(TextView) findViewById(R.id.mobileno1);
        maritialstatus1=(TextView) findViewById(R.id.maritialstatus1);
        qualification1=(TextView) findViewById(R.id.qualification1);
        accomodation1=(TextView) findViewById(R.id.accomodation1);
        currentaddress1=(TextView) findViewById(R.id.currentaddress1);
        iscurrentaddress1=(TextView) findViewById(R.id.iscurrentaddress1);
        permanentaddress1=(TextView) findViewById(R.id.permanentaddress1);
        noofyears1=(TextView) findViewById(R.id.noofyears1);
        organizationtype1=(TextView) findViewById(R.id.organizationtype1);
        industrytype1=(TextView) findViewById(R.id.industrytype1);
        employementtype1=(TextView) findViewById(R.id.employementtype1);
        totalexpbussiness1=(TextView) findViewById(R.id.totalexpbussiness1);
        totalturnover1=(TextView) findViewById(R.id.totalturnover1);
        annualincome1=(TextView) findViewById(R.id.annualincome1);
        companyname1=(TextView) findViewById(R.id.companyname1);
        officeaddress1=(TextView) findViewById(R.id.officeaddress1);
        officepincode1=(TextView) findViewById(R.id.officepincode1);
        totalworfexp1=(TextView) findViewById(R.id.totalworfexp1);
        grossmonthlyincome1=(TextView) findViewById(R.id.grossmonthlyincome1);
        btsend=(TextView)findViewById(R.id.save);
        salaried=(LinearLayout)findViewById(R.id.salaried);
        self_employed=(LinearLayout)findViewById(R.id.self_employed);

        fetch_customer_data();
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_customer_application();


            }
        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Customer_Details_Verification_Dealer.this, Additional_KYC_DEALER_Details.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Customer_Details_Verification_Dealer.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });




    }

    private void fetch_customer_data() {
        custPrograssbar.prograssCreate(Customer_Details_Verification_Dealer.this);
        Fetch_customer_data_POJO pojo = new Fetch_customer_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Fetch_customer_data_MODEL> call = restApis.fetch_customer_data(pojo);
        call.enqueue(new Callback<Fetch_customer_data_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_customer_data_MODEL> call, Response<Fetch_customer_data_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            Log.e("Body", "body3");

                            custPrograssbar.closePrograssBar();
                            salution1.setText(response.body().getPayload().get(i).getCust_sal());
                            customername1.setText(response.body().getPayload().get(i).getCust_first_name()+" "+ response.body().getPayload().get(i).getCust_last_name());
                            emailid1.setText(response.body().getPayload().get(i).getCust_email());
                            mobileno1.setText(response.body().getPayload().get(i).getCust_mobile());
                            maritialstatus1.setText(response.body().getPayload().get(i).getCust_marital());
                            qualification1.setText(response.body().getPayload().get(i).getCust_qual());
                            accomodation1.setText(response.body().getPayload().get(i).getCust_accomodation_type());
                            currentaddress1.setText(response.body().getPayload().get(i).getCurent_address());
                            iscurrentaddress1.setText(response.body().getPayload().get(i).getCust_add_type_check());
                            permanentaddress1.setText(response.body().getPayload().get(i).getPermanent_address());
                            noofyears1.setText("5");
                            organizationtype1.setText(response.body().getPayload().get(i).getCust_org_type());
                            industrytype1.setText(response.body().getPayload().get(i).getCust_industry_type());
                            employementtype1.setText(response.body().getPayload().get(i).getCust_emp_type());
                            totalexpbussiness1.setText(response.body().getPayload().get(i).getCust_bexp());
                            totalturnover1.setText(response.body().getPayload().get(i).getCust_bttl_trn());
                            annualincome1.setText(response.body().getPayload().get(i).getCust_anl_incm());
                            companyname1.setText(response.body().getPayload().get(i).getCust_cmpny_name());
                            officeaddress1.setText(response.body().getPayload().get(i).getCust_off_add());
                            officepincode1.setText(response.body().getPayload().get(i).getCust_off_pin());
                            totalworfexp1.setText(response.body().getPayload().get(i).getCust_wrk_exp());
                            grossmonthlyincome1.setText(response.body().getPayload().get(i).getCust_gross_incm());


                            if(response.body().getPayload().get(i).getCust_emp_type().equals("SALARIED")){
                                salaried.setVisibility(View.VISIBLE);
                                self_employed.setVisibility(View.GONE);
                            }
                            else{
                                self_employed.setVisibility(View.VISIBLE);
                                salaried.setVisibility(View.GONE);
                            }
                        }

                    } else {


                    }


                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Fetch_customer_data_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }
    private void submit_loan_application() {
        custPrograssbar.prograssCreate(Customer_Details_Verification_Dealer.this);
        Submit_loan_application_POJO pojo = new Submit_loan_application_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Submit_loan_application_MODEL> call = restApis.submit_loan_application(pojo);
        call.enqueue(new Callback<Submit_loan_application_MODEL>() {
            @Override
            public void onResponse(Call<Submit_loan_application_MODEL> call, Response<Submit_loan_application_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();



                    } else {

                    }


                }
            }

            @Override
            public void onFailure(Call<Submit_loan_application_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void submit_customer_application() {
        custPrograssbar.prograssCreate(Customer_Details_Verification_Dealer.this);
        Submit_customer_application_POJO pojo = new Submit_customer_application_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Submit_customer_application_MODEL> call = restApis.submit_customer_application(pojo);
        call.enqueue(new Callback<Submit_customer_application_MODEL>() {
            @Override
            public void onResponse(Call<Submit_customer_application_MODEL> call, Response<Submit_customer_application_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode()==200) {
                        custPrograssbar.closePrograssBar();
                        Intent it=new Intent(Customer_Details_Verification_Dealer.this,Success_Dealer.class);
                        startActivity(it);


                    } else {

                    }


                }
            }

            @Override
            public void onFailure(Call<Submit_customer_application_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "App restricts,back button not allowed on this screen!!",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }
}