package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.qts.gopik_loan.Model.PINCODE_MODEL;
import com.qts.gopik_loan.Model.Store_data_for_MLloan_MODEL;
import com.qts.gopik_loan.Model.Verify_mobile_number_MODEL;
import com.qts.gopik_loan.Pojo.Store_data_for_MLloan_POJO;
import com.qts.gopik_loan.Pojo.Verify_mobile_number_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Property_Loan extends AppCompatActivity implements TextWatcher {
    RadioGroup loan5rg;
    EditText name, email, address, dob, moblog, otp, pan, pincodevalue, city, state, loanamounthouse, moblogwhatsapp;
    TextView btn_proceed, btn_proceed1, panname;
    CustPrograssbar custPrograssbar;
    String TAG = "loginotp";
    LinearLayout otplinearlayout;
    ImageView hometoolbar, backarrow;
    Spinner spino;
    DatePickerDialog datePickerDialog;
    Matcher matcher;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] courses = {"Odisha", "Maharashtra", "Punjab", "Gujarat", "Uttar Pradesh"};
    Integer AGE_OF_USER = 0;
    TextView btsend, dob_error_massage;
    String pin;
    Button ok;
    Pattern pattern1;
    RadioButton uc, rc, male, female, loan5, homeloan, commercialloan, loanagainstproperty, selfconstruction, yes, no, mr, ms;
    final Calendar myCalendar = Calendar.getInstance();
    TextInputLayout dobvalid;
    TextInputLayout whatsapplayout;
    EditText firstname, middlename, lastname;
    LinearLayout loansubcatlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property__loan);
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
        loan5rg = (RadioGroup) findViewById(R.id.loan5rg);
        rc = (RadioButton) findViewById(R.id.rp);
      male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        homeloan = (RadioButton) findViewById(R.id.loan1);
        commercialloan = (RadioButton) findViewById(R.id.loan2);
        loanagainstproperty = (RadioButton) findViewById(R.id.loan3);
        selfconstruction = (RadioButton) findViewById(R.id.loan4);
        loan5 = (RadioButton) findViewById(R.id.loan5);
        whatsapplayout = (TextInputLayout) findViewById(R.id.whatsapplayout);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        moblogwhatsapp = (EditText) findViewById(R.id.moblogwhatsapp);
        mr = (RadioButton) findViewById(R.id.mr);
        ms = (RadioButton) findViewById(R.id.ms);
        firstname = (EditText) findViewById(R.id.firstname);
        middlename = (EditText) findViewById(R.id.middilename);
        middlename.addTextChangedListener(this);
        lastname = (EditText) findViewById(R.id.lastname);
        lastname.addTextChangedListener(this);
        firstname.addTextChangedListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loansubcatlayout = (LinearLayout) findViewById(R.id.loansubcatlayout);
        SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE, "Property Loan", getApplicationContext());

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupvalidation();
            }
        });


        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Property_Loan.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Property_Loan.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        pincodevalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hhguu", "bfvn" + pincodevalue.getText().toString().length());

                if (pincodevalue.length() == 0) {
                    state.setText(" ");
                    city.setText(" ");
                }
            }
        });
        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALUTION_LOAN, "Mr", getApplicationContext());
            }
        });
        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALUTION_LOAN, "Ms", getApplicationContext());
            }
        });

        uc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE1, "Under Construction", getApplicationContext());
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPref.saveStringInSharedPref(AppConstants.WHATSAPPVALUE, "Yes", getApplicationContext());
                Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()));
                if (SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()).equals("Yes")) {
                    Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()));
                    Log.e("Body", "body2" + moblog.getText().toString());
                    whatsapplayout.setVisibility(View.VISIBLE);
                    moblogwhatsapp.setText(moblog.getText().toString());
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.WHATSAPPVALUE, "No", getApplicationContext());
                Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()));
                if (SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()).equals("No")) {
                    Log.e("Body", "body2" + SharedPref.getStringFromSharedPref(AppConstants.WHATSAPPVALUE, getApplicationContext()));
                    Log.e("Body", "body2" + moblog.getText().toString());
                    whatsapplayout.setVisibility(View.VISIBLE);
                    moblogwhatsapp.setText("");
                }
            }
        });
        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE1, "Ready Possession", getApplicationContext());
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.GENDER, "Male", getApplicationContext());
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.GENDER, "Female", getApplicationContext());
            }
        });
        homeloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.LOANSUBCATEGORY, "Home Loan", getApplicationContext());
                loansubcatlayout.setVisibility(View.VISIBLE);
                commercialloan.setChecked(false);
                loanagainstproperty.setChecked(false);
                selfconstruction.setChecked(false);
                homeloan.setChecked(true);
                loan5.setChecked(false);
            }
        });
        commercialloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.LOANSUBCATEGORY, "Commercial Loan", getApplicationContext());
                commercialloan.setChecked(true);
                loanagainstproperty.setChecked(false);
                selfconstruction.setChecked(false);
                homeloan.setChecked(false);
                loan5.setChecked(false);
                loansubcatlayout.setVisibility(View.VISIBLE);
            }
        });
        loanagainstproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.LOANSUBCATEGORY, "Loan Against Property", getApplicationContext());
                loansubcatlayout.setVisibility(View.GONE);
                commercialloan.setChecked(false);
                loanagainstproperty.setChecked(true);
                selfconstruction.setChecked(false);
                homeloan.setChecked(false);
                loan5.setChecked(false);
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE1, " ", getApplicationContext());
            }
        });
        selfconstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loansubcatlayout.setVisibility(View.GONE);
                commercialloan.setChecked(false);
                loanagainstproperty.setChecked(false);
                selfconstruction.setChecked(true);
                homeloan.setChecked(false);
                loan5.setChecked(false);
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE1, " ", getApplicationContext());
                SharedPref.saveStringInSharedPref(AppConstants.LOANSUBCATEGORY, "Self Construction Loan", getApplicationContext());
            }
        });
        loan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loan5rg.clearCheck();
                Log.e("ankit", "amit1");
                loansubcatlayout.setVisibility(View.GONE);
                Log.e("ankit", "amit2");
                commercialloan.setChecked(false);
                Log.e("ankit", "amit3");
                loanagainstproperty.setChecked(false);
                Log.e("ankit", "amit4");
                selfconstruction.setChecked(false);
                Log.e("ankit", "amit5");
                homeloan.setChecked(false);
                Log.e("ankit", "amit6");
                loan5.setChecked(true);
                Log.e("ankit", "amit7");
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE1, " ", getApplicationContext());
                SharedPref.saveStringInSharedPref(AppConstants.LOANSUBCATEGORY, "Loan For Land Purchase", getApplicationContext());
            }
        });


    }


    private void signupvalidation() {
        if (firstname.getText().toString().isEmpty() && lastname.getText().toString().isEmpty() && email.getText().toString().isEmpty()
                && email.getText().toString().isEmpty() && loanamounthouse.getText().toString().isEmpty()
                && pincodevalue.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter above details", Toast.LENGTH_LONG).show();
        } else if (firstname.getText().toString().isEmpty()) {

            firstname.setError("Please Enter Valid Customer Name");

        } else if (lastname.getText().toString().isEmpty()) {

            firstname.setError("Please Enter Valid Customer Name");

        } else if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Valid Customer Email Address");

        } else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please Enter Valid Customer Email Address");
        } else if (pincodevalue.getText().toString().isEmpty()) {
            pincodevalue.setError("Please Enter Valid Pincode");

        } else if (state.getText().toString().isEmpty()) {
            state.setError("Please Enter Valid State");

        } else if (city.getText().toString().isEmpty()) {
            city.setError("Please Enter Valid City");

        } else if (moblog.getText().toString().isEmpty()) {
            moblog.setError("Please Enter Valid Phone Number");

        } else if (moblogwhatsapp.getText().toString().isEmpty()) {
            moblogwhatsapp.setError("Please Enter Valid Whatsapp Number");

        } else if (SharedPref.getStringFromSharedPref(AppConstants.SALUTION_LOAN, getApplicationContext()).isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter salution ", Toast.LENGTH_LONG).show();

        } else if (SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE1, getApplicationContext()).isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter loan type", Toast.LENGTH_LONG).show();

        } else if (SharedPref.getStringFromSharedPref(AppConstants.LOANSUBCATEGORY, getApplicationContext()).isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter sub-category loan type", Toast.LENGTH_LONG).show();

        } else {

            store_data_for_MLloan();

        }

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private void pincode(String pinCode) {
        custPrograssbar.prograssCreate(Property_Loan.this);
        Log.e("ankit", "akit" + pin);
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

                    } else if (response.body().getStatus().equals("Error")) {
                        pincodevalue.setError("Please Enter Valid Pincode");

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

    private void store_data_for_MLloan() {
        custPrograssbar.prograssCreate(Property_Loan.this);
        Store_data_for_MLloan_POJO pojo = new Store_data_for_MLloan_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SALUTION_LOAN, getApplicationContext()),
                firstname.getText().toString(), middlename.getText().toString(), lastname.getText().toString(),
                moblog.getText().toString(),
                email.getText().toString(), pincodevalue.getText().toString(), state.getText().toString(), city.getText().toString(),
                loanamounthouse.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE, getApplicationContext()),

                SharedPref.getStringFromSharedPref(AppConstants.LOANSUBCATEGORY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.HOMELOANTYPE1, getApplicationContext()));

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
                        Intent it = new Intent(getApplicationContext(), Success.class);
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


    @Override
    public void afterTextChanged(Editable editable) {
        Log.e("hhghghhuu", "bfvn" + middlename.getText().toString().length());
        if (pincodevalue.getText().toString().length() == 6) {
            pin = pincodevalue.getText().toString();
            pincode(pin);
        }
        if (lastname.getText().toString().length() > 0) {
            Log.e("hhghghhuu", "7777" + middlename.getText().toString().length());
            if (middlename.getText().toString().length() == 0) {
                middlename.setVisibility(View.GONE);
            }

        }
        if (lastname.getText().toString().length() == 0) {
            Log.e("hhghghhuu", "7777" + middlename.getText().toString().length());

            middlename.setVisibility(View.VISIBLE);


        }


    }


}


