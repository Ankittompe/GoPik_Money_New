package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.qts.gopik_money.Model.Draftview_MODEL;
import com.qts.gopik_money.Model.PINCODE_MODEL;
import com.qts.gopik_money.Pojo.Draftview_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Draft_Activity extends AppCompatActivity implements TextWatcher {
    EditText name;
    EditText email;
    EditText address;
    EditText dob;
    EditText moblog;
    EditText otp;
    EditText pincodevalue;
    EditText city;
    EditText state;
    EditText loanamounthouse;
    EditText moblogwhatsapp;
    TextView btn_proceed;
    TextView btn_proceed1;
    TextView panname;
    CustPrograssbar custPrograssbar;
    String TAG = "loginotp";
    LinearLayout otplinearlayout;
    ImageView hometoolbar;
    ImageView backarrow;

    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String[] courses = {"Odisha", "Maharashtra", "Punjab", "Gujarat", "Uttar Pradesh"};
    Integer AGE_OF_USER = 0;

    TextView dob_error_massage;
    String pin;

    RadioButton uc;
    RadioButton rc;
    RadioButton male;
    RadioButton female;
    RadioButton homeloan;
    RadioButton commercialloan;
    RadioButton loanagainstproperty;
    RadioButton selfconstruction;
    RadioButton yes;
    RadioButton no;
    RadioButton mr;
    RadioButton ms;
    final Calendar myCalendar = Calendar.getInstance();
    TextInputLayout dobvalid;
    TextInputLayout whatsapplayout;
    EditText firstname;
    EditText middlename;
    EditText lastname;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, "", getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.WHATSAPPVALUE, " ", getApplicationContext());
        moblog = (EditText) findViewById(R.id.moblog);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        custPrograssbar = new CustPrograssbar();
        dob = (EditText) findViewById(R.id.dob);
        otp = (EditText) findViewById(R.id.otp);
        btn_proceed = (TextView) findViewById(R.id.btn_proceed);
        pincodevalue = (EditText) findViewById(R.id.pincodeno);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        btn_proceed1 = (TextView) findViewById(R.id.btn_proceed1);
        otplinearlayout = (LinearLayout) findViewById(R.id.otplinearlayout);
        pincodevalue.addTextChangedListener(this);
        loanamounthouse = (EditText) findViewById(R.id.loanamounthouse);
        uc = (RadioButton) findViewById(R.id.uc);
        rc = (RadioButton) findViewById(R.id.rp);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        homeloan = (RadioButton) findViewById(R.id.loan1);
        commercialloan = (RadioButton) findViewById(R.id.loan2);
        loanagainstproperty = (RadioButton) findViewById(R.id.loan3);
        selfconstruction = (RadioButton) findViewById(R.id.loan4);
        whatsapplayout = (TextInputLayout) findViewById(R.id.whatsapplayout);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        moblogwhatsapp = (EditText) findViewById(R.id.moblogwhatsapp);
        mr = (RadioButton) findViewById(R.id.mr);
        ms = (RadioButton) findViewById(R.id.ms);
        firstname = (EditText) findViewById(R.id.firstname);
        middlename = (EditText) findViewById(R.id.middlename);
        lastname = (EditText) findViewById(R.id.lastname);
        SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE, "Property Loan", getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if (getIntent() != null) {
            value = extras.getString("key");
        }

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Draft_Activity.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Draft_Activity.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });


        btn_proceed.setOnClickListener(v -> signupvalidation());


        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Draft_Activity.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Draft_Activity.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            Calendar today = Calendar.getInstance();
            AGE_OF_USER = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) == myCalendar.get(Calendar.MONTH)) {

                if (today.get(Calendar.DAY_OF_MONTH) < myCalendar.get(Calendar.DAY_OF_MONTH)) {

                    AGE_OF_USER = AGE_OF_USER - 1;

                }

            } else if (today.get(Calendar.MONTH) < myCalendar.get(Calendar.MONTH)) {

                AGE_OF_USER = AGE_OF_USER - 1;

            }


            updateLabel();

        };
        dob.setOnClickListener(view -> new DatePickerDialog(Draft_Activity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        draftview();
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(dateFormat.format(myCalendar.getTime()));
        dob_error_massage.setVisibility(View.GONE);
        SharedPref.saveStringInSharedPref(AppConstants.DOBVALUE, dob.getText().toString(), getApplicationContext());

    }

    private void draftview() {
        Draftview_POJO pojo = new Draftview_POJO(value);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Draftview_MODEL> call = restApis.draftview(pojo);
        call.enqueue(new Callback<Draftview_MODEL>() {
            @Override
            public void onResponse(Call<Draftview_MODEL> call, Response<Draftview_MODEL> response) {

                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        name.setText(response.body().getPayload().get(0).getCustomer_name());
                        email.setText(response.body().getPayload().get(0).getCustomer_email());
                        address.setText(response.body().getPayload().get(0).getCustomer_address());
                        loanamounthouse.setText(response.body().getPayload().get(0).getLoan_amount());
                        pincodevalue.setText(response.body().getPayload().get(0).getPin());
                        state.setText(response.body().getPayload().get(0).getState());
                        city.setText(response.body().getPayload().get(0).getCity());
                        SharedPref.saveStringInSharedPref(AppConstants.LOAN_CAT, response.body().getPayload().get(0).getLoan_cat(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.GENDER_VALUE, response.body().getPayload().get(0).getCustomer_gender(), getApplicationContext());
                        if (SharedPref.getStringFromSharedPref(AppConstants.GENDER_VALUE, getApplicationContext()).equals("Male")) {
                            male.setChecked(true);

                        } else {
                            female.setChecked(true);
                        }

                        dob.setText(response.body().getPayload().get(0).getCustomer_dob());
                        moblog.setText(response.body().getPayload().get(0).getCustomer_mobile());
                    }
                }
            }

            @Override
            public void onFailure(Call<Draftview_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

            }

        });
    }

    private void signupvalidation() {
        if (name.getText().toString().isEmpty() && email.getText().toString().isEmpty() && address.getText().toString().isEmpty() && dob.getText().toString().isEmpty()
                && pincodevalue.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter below details", Toast.LENGTH_LONG).show();
        } else if (name.getText().toString().isEmpty()) {

            name.setError("Please Enter Valid Customer Name");

        } else if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Valid Customer Email Address");

        } else if (address.getText().toString().isEmpty()) {
            address.setError("Please Enter Valid Customer Property Address");


        } else if (dob.getText().toString().isEmpty()) {
            dob.setError("Please Enter Valid Customer DOB");

        } else if (pincodevalue.getText().toString().isEmpty()) {
            pincodevalue.setError("Please Enter Valid Pincode");

        } else if (state.getText().toString().isEmpty()) {
            state.setError("Please Enter Valid State");

        } else if (city.getText().toString().isEmpty()) {
            city.setError("Please Enter Valid City");

        } else if (AGE_OF_USER < 18) {
            dob_error_massage.setVisibility(View.VISIBLE);
        } else {

        /*    store_data_for_MLloan();*/

        }

    }

/*
    private void store_data_for_MLloan() {
        custPrograssbar.prograssCreate(Draft_Activity.this);
        Store_data_for_MLloan_POJO pojo = new Store_data_for_MLloan_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SALUTION_LOAN, getApplicationContext()),
                firstname.getText().toString(), middlename.getText().toString(), lastname.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.GENDER, getApplicationContext()),
                moblog.getText().toString(),
                email.getText().toString(), pincodevalue.getText().toString(), state.getText().toString(), city.getText().toString(),
                loanamounthouse.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE1, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LOANSUBCATEGORY, getApplicationContext())
        );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Store_data_for_MLloan_MODEL> call = restApis.store_data_for_MLloan(pojo);
        call.enqueue(new Callback<Store_data_for_MLloan_MODEL>() {
            @Override
            public void onResponse(Call<Store_data_for_MLloan_MODEL> call, Response<Store_data_for_MLloan_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {
                        Log.e("Body", "body2");
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, response.body().getPayload().getCustomer_code(), getApplicationContext());
                        Intent it = new Intent(getApplicationContext(), Property_Loan.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Store_data_for_MLloan_MODEL> call, Throwable t) {



                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/


    private void pincode(String pinCode) {
        custPrograssbar.prograssCreate(Draft_Activity.this);
        RestApis restApis = NetworkHandler.instanceMaker2().create(RestApis.class);
        Call<PINCODE_MODEL> call = restApis.pincode(pinCode);
        call.enqueue(new Callback<PINCODE_MODEL>() {
            @Override
            public void onResponse(Call<PINCODE_MODEL> call, Response<PINCODE_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    if (response.body().getStatus().equals("Success")) {

                        state.setText(response.body().getPostOffice().get(0).getState());
                        city.setText(response.body().getPostOffice().get(0).getTaluk());

                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<PINCODE_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        //nothing to do
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        //nothing to do
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (pincodevalue.getText().toString().length() == 6) {
            pin = pincodevalue.getText().toString();
            pincode(pin);
        }
    }
}