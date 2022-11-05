package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Profile_data_MODEL;
import com.qts.gopik_money.Pojo.Profile_data_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Additional_KYC_DEALER_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner_salution ;
    Spinner spinner_customername;
    Spinner spinner_maritialstatus;
    Spinner spinner_qualification;
    Spinner spinner_accomodationtype;
    Spinner spinner_sameaddress;
    Spinner spinner_organisationtype;
    Spinner spinner_industrtype;
    Spinner spinner_employmenttype;
    Spinner choose_tractor;
    Spinner choose_tractorfinance;
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView  backarrow;
    ArrayAdapter ad;
    ArrayAdapter ad1;
    ArrayAdapter ad2;
    ArrayAdapter ad3;
    ArrayAdapter ad4;
    ArrayAdapter ad5;
    ArrayAdapter ad6;
    ArrayAdapter ad7;
    ArrayAdapter ad8;
    ArrayAdapter ad9;
    ArrayAdapter ad10;
    String name_VOTER = SharedPref.getStringFromSharedPref(AppConstants.NAME_VOTERID, getApplication());
    String name_PAN = SharedPref.getStringFromSharedPref(AppConstants.PAN_NAME, getApplication());
    String[] separated;
    String mSelectOption = "Select Option";
    String[] salution = {"Select Salutation", "Mr.", "Ms."};
    String[] customername = {"Select Customer Name", name_VOTER, name_PAN};
    String[] maritialstatus = {"Select Maritial Status", "SINGLE", "MARRIED", "DIVORCED", "WIDOWED", "SEPARATED"};
    String[] qualification = {"Select Qualification", "10th", "12th", "Primary Schooling", "OTHERS", "ENGINEER", "ARCHITECT",
            "LAWYER", "DOCTOR", "MBA", "CA", "DOCTORATE", "POST GRADUATE", "GRADUATE", "UNDER GRADUATE"};
    String[] accomodationtype = {"Select Accomodation Type", "COMPANY LEADS OR ALLOTED", "JOINTLY OWNED", "OWNED", "PAGADI",
            "PARENTAL OWNED", "RENTED", "SHARED ACCOMODATION"};
    String[] sameaddress = {mSelectOption, "Yes", "No"};
    String[] organisation = {mSelectOption, "PROPRIETORSHIP", "PARTNERSHIP", "CLOSELY HELD - PUBLIC LIMITED", "LISTED- PUBLIC LIMITED", "PRIVATE LIMITED",
            "ASSOCIATION OF PEOPLE", "SUBSIDIARY", "MNC", "SOCIETY", "HUF", "TRUST", "GOVT(ONLY FOR SALARIED)", "PVT LTD_SALARIED"};
    String[] industrytype = {mSelectOption, "AGRI OR AGRI ALLIED", "AGRICULTURE", "ARTISAN", "AUTOMOTIVE", "CEMENT AND CEMENT PRODUCTS",
            "CHEMICALS", "COAL"};
    String[] employementtype = {mSelectOption, "SALARIED", "SELF EMPLOYED"};
    String[] choosetractor = {mSelectOption, "Yes", "No"};
    String[] choosetractorfinance = {mSelectOption, "Yes", "No"};
    String sep;
    LinearLayout salaried;
    LinearLayout self_employed;
    LinearLayout officelayout;
    LinearLayout finance;
    LinearLayout linearspinnertractor;
    LinearLayout linearspinnertractorfinance;
    String TAG = "finalbikepayment";
    TextView save;
    TextView  mobilenoedittext;
    TextView  tractortype;
    TextView  tractorfinance;
    EditText grossmonthlyincomeedittext;
    EditText numberinyearsedittext;
    EditText fullofficeaddressedittext;
    EditText companynameedittext;
    EditText annualincomeedittext;
    EditText totalamountturoveredittext;
    EditText expbussinessedittext;
    EditText noofyearedittext;
    EditText permamentadressedittext;
    EditText currentaddressedittext;
    EditText emailideditext;
    EditText pincodeeditttext;
    EditText officefulladdressedittext1;
    EditText permamentadresspincodeedittext;
    EditText currentaddresspincodeedittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional__k_y_c__d_e_a_l_e_r__details);

        custPrograssbar = new CustPrograssbar();
        salaried = (LinearLayout) findViewById(R.id.salaried);
        self_employed = (LinearLayout) findViewById(R.id.self_employed);
        save = (TextView) findViewById(R.id.save);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        permamentadresspincodeedittext = (EditText) findViewById(R.id.permamentadresspincodeedittext);
        currentaddresspincodeedittext = (EditText) findViewById(R.id.currentaddresspincodeedittext);
        grossmonthlyincomeedittext = (EditText) findViewById(R.id.grossmonthlyincomeedittext);
        numberinyearsedittext = (EditText) findViewById(R.id.numberinyearsedittext);
        pincodeeditttext = (EditText) findViewById(R.id.pincodeeditttext);
        officefulladdressedittext1 = (EditText) findViewById(R.id.officefulladdressedittext1);
        officelayout = (LinearLayout) findViewById(R.id.officelayout);
        companynameedittext = (EditText) findViewById(R.id.companynameedittext);
        annualincomeedittext = (EditText) findViewById(R.id.annualincomeedittext);
        totalamountturoveredittext = (EditText) findViewById(R.id.totalamountturoveredittext);
        expbussinessedittext = (EditText) findViewById(R.id.expbussinessedittext);
        noofyearedittext = (EditText) findViewById(R.id.noofyearedittext);
        permamentadressedittext = (EditText) findViewById(R.id.permamentadressedittext);
        currentaddressedittext = (EditText) findViewById(R.id.currentaddressedittext);
        mobilenoedittext = (TextView) findViewById(R.id.mobilenoedittext);
        emailideditext = (EditText) findViewById(R.id.emailideditext);
        mobilenoedittext.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));

        finance = (LinearLayout) findViewById(R.id.finance);
        linearspinnertractor = (LinearLayout) findViewById(R.id.linearspinnertractor);
        linearspinnertractorfinance = (LinearLayout) findViewById(R.id.linearspinnertractorfinance);
        tractortype = (TextView) findViewById(R.id.tractortype);
        tractorfinance= (TextView) findViewById(R.id.tractorfinance);

        spinner_salution = findViewById(R.id.choose_salution);
        spinner_salution.setOnItemSelectedListener(this);

        spinner_customername = findViewById(R.id.choose_customername);
        spinner_customername.setOnItemSelectedListener(this);

        spinner_maritialstatus = findViewById(R.id.choose_maritialstatus);
        spinner_maritialstatus.setOnItemSelectedListener(this);

        spinner_qualification = findViewById(R.id.choose_qualification);
        spinner_qualification.setOnItemSelectedListener(this);

        spinner_accomodationtype = findViewById(R.id.choose_accomodationtype);
        spinner_accomodationtype.setOnItemSelectedListener(this);

        spinner_sameaddress = findViewById(R.id.choose_current_permanentaddress);
        spinner_sameaddress.setOnItemSelectedListener(this);

        spinner_organisationtype = findViewById(R.id.choose_organizationtype);
        spinner_organisationtype.setOnItemSelectedListener(this);

        spinner_industrtype = findViewById(R.id.choose_industrytype);
        spinner_industrtype.setOnItemSelectedListener(this);

        spinner_employmenttype = findViewById(R.id.choose_employeementtype);
        spinner_employmenttype.setOnItemSelectedListener(this);

        choose_tractor = findViewById(R.id.choose_tractor);
        choose_tractor.setOnItemSelectedListener(this);

        choose_tractorfinance = findViewById(R.id.choose_tractorfinance);
        choose_tractorfinance.setOnItemSelectedListener(this);

         ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                salution);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_salution.setAdapter(ad);

         ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                customername);


        ad1.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_customername.setAdapter(ad1);

         ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                maritialstatus);


        ad2.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_maritialstatus.setAdapter(ad2);

         ad3
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                qualification);


        ad3.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_qualification.setAdapter(ad3);

         ad4
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                accomodationtype);


        ad4.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_accomodationtype.setAdapter(ad4);

         ad5
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                sameaddress);


        ad5.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_sameaddress.setAdapter(ad5);
       ad6
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                organisation);


        ad6.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_organisationtype.setAdapter(ad6);

         ad7
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                industrytype);


        ad7.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_industrtype.setAdapter(ad7);


         ad8
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                employementtype);


        ad8.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinner_employmenttype.setAdapter(ad8);


         ad9 = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                choosetractor);


        ad9.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_tractor.setAdapter(ad9);


         ad10
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                choosetractorfinance);


        ad10.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_tractorfinance.setAdapter(ad10);

        save.setOnClickListener(view -> validation());
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Additional_KYC_DEALER_Details.this, ImageCapture.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Additional_KYC_DEALER_Details.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

    }

    private void validation() {

       /* if (emailideditext.getText().toString().isEmpty()) {
            emailideditext.setError("Please Enter Valid Email");
        } else if (numberinyearsedittext.getText().toString().isEmpty()) {
            numberinyearsedittext.setError("Please Enter The Years");
        } else if (pincodeeditttext.getText().toString().isEmpty()) {
            pincodeeditttext.setError("Please Enter Valid Pincode");
        } else if (fullofficeaddressedittext.getText().toString().isEmpty()) {
            fullofficeaddressedittext.setError("Please Enter Address");
        } else if (companynameedittext.getText().toString().isEmpty()) {
            companynameedittext.setError("Please Enter Address");
        } else if (annualincomeedittext.getText().toString().isEmpty()) {
            annualincomeedittext.setError("Please Enter Valid Annual Income");
        } else if (totalamountturoveredittext.getText().toString().isEmpty()) {
            totalamountturoveredittext.setError("Please Enter Total Turn Over");
        } else if (expbussinessedittext.getText().toString().isEmpty()) {
            expbussinessedittext.setError("Please Enter Bussiness Expierence");
        } else if (noofyearedittext.getText().toString().isEmpty()) {
            noofyearedittext.setError("Please Enter No Of Years");
        } else if (permamentadressedittext.getText().toString().isEmpty()) {
            permamentadressedittext.setError("Please Enter Permanent Address");
        } else if (currentaddressedittext.getText().toString().isEmpty()) {
            currentaddressedittext.setError("Please Enter Current Address");
        } else if (mobilenoedittext.getText().toString().isEmpty()) {
            mobilenoedittext.setError("Please Enter Valid Mobile No");
        } else if (spinner_accomodationtype.getSelectedItem().toString().trim().equals("Select Accomodation Type")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Enter Accomodation Type", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_customername.getSelectedItem().toString().trim().equals("Select Customer Name")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Customer Name", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_maritialstatus.getSelectedItem().toString().trim().equals("Select Maritial Status")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Maritial Status", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_qualification.getSelectedItem().toString().trim().equals("Select Qualification")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Qualification", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_sameaddress.getSelectedItem().toString().trim().equals("Select Option")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Option", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_organisationtype.getSelectedItem().toString().trim().equals("Select Option")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Option", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_industrtype.getSelectedItem().toString().trim().equals("Select Option")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Option", Toast.LENGTH_SHORT).show();
        }
        else if (spinner_employmenttype.getSelectedItem().toString().trim().equals("Select Option")) {
            Toast.makeText(Additional_KYC_Details.this, "Please Select Option", Toast.LENGTH_SHORT).show();
        }*/

        if (emailideditext.getText().toString().isEmpty()) {
            emailideditext.setError("Please Enter Valid Email");
        } else {
            profile_data();
        }

    }

    private void profile_data() {
        custPrograssbar.prograssCreate(Additional_KYC_DEALER_Details.this);
        Profile_data_POJO pojo = new Profile_data_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.SALUTIONTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUSTOMERNAMETYPE, getApplicationContext()), " ",
                emailideditext.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.MARITIALSTATUSTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.QUALIFICATIONTYPE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.ACCOMODATIONTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SAMEADDRESSTYPE, getApplicationContext()), noofyearedittext.getText().toString(), SharedPref
                .getStringFromSharedPref(AppConstants.ORGANISATIONTYPE, getApplicationContext()), officefulladdressedittext1.getText().toString(), pincodeeditttext.getText().toString(),
                numberinyearsedittext.getText().toString(), "ggu", SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext()),
                companynameedittext.getText().toString(), grossmonthlyincomeedittext.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.INDUSTRYTYPE, getApplicationContext())
                , "2022-01-22", expbussinessedittext.getText().toString(), totalamountturoveredittext.getText().toString(), annualincomeedittext.getText().toString(),
                currentaddressedittext.getText().toString(), permamentadressedittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.TRACTOR, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TRACTORFINANCE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Profile_data_MODEL> call = restApis.profile_data(pojo);
        call.enqueue(new Callback<Profile_data_MODEL>() {
            @Override
            public void onResponse(Call<Profile_data_MODEL> call, Response<Profile_data_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        Intent it = new Intent(Additional_KYC_DEALER_Details.this, Customer_Details_Verification_Dealer.class);
                        startActivity(it);


                    } else {

                    }

                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Profile_data_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int index = spinner_salution.getSelectedItemPosition();
        int index1 = spinner_customername.getSelectedItemPosition();
        int index2 = spinner_maritialstatus.getSelectedItemPosition();
        int index3 = spinner_qualification.getSelectedItemPosition();
        int index4 = spinner_accomodationtype.getSelectedItemPosition();
        int index5 = spinner_sameaddress.getSelectedItemPosition();
        int index6 = spinner_organisationtype.getSelectedItemPosition();
        int index7 = spinner_industrtype.getSelectedItemPosition();
        int index8 = spinner_employmenttype.getSelectedItemPosition();
        int index9 = choose_tractor.getSelectedItemPosition();
        int index10 = choose_tractorfinance.getSelectedItemPosition();


        SharedPref.saveStringInSharedPref(AppConstants.SALUTIONTYPE, salution[index], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.CUSTOMERNAMETYPE, customername[index1], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.MARITIALSTATUSTYPE, maritialstatus[index2], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.QUALIFICATIONTYPE, qualification[index3], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.ACCOMODATIONTYPE, accomodationtype[index4], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.SAMEADDRESSTYPE, sameaddress[index5], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.ORGANISATIONTYPE, organisation[index6], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.INDUSTRYTYPE, industrytype[index7], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.EMPLOYEMENTTYPE, employementtype[index8], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.TRACTOR, choosetractor[index9], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.TRACTORFINANCE, choosetractorfinance[index10], getApplicationContext());

        if ((SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext())).equals("SALARIED")) {
            salaried.setVisibility(View.VISIBLE);
            self_employed.setVisibility(View.GONE);
            officelayout.setVisibility(View.VISIBLE);
        } else if ((SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext())).equals("SELF EMPLOYED")) {
            self_employed.setVisibility(View.VISIBLE);
            salaried.setVisibility(View.GONE);
            officelayout.setVisibility(View.VISIBLE);
        }
      /*  sep = SharedPref.getStringFromSharedPref(AppConstants.CUSTOMERNAMETYPE, getApplicationContext());
        separated = sep.split(" ");*/
        if ((SharedPref.getStringFromSharedPref(AppConstants.SAMEADDRESSTYPE, getApplicationContext())).equals("Yes")) {
            permamentadressedittext.setText(currentaddressedittext.getText());
            permamentadresspincodeedittext.setText(currentaddresspincodeedittext.getText());
        } else {
            permamentadressedittext.setText("");
            permamentadresspincodeedittext.setText("");
        }

        if ((SharedPref.getStringFromSharedPref(AppConstants.TRACTOR, getApplicationContext())).equals("Yes")) {
            finance.setVisibility(View.VISIBLE);
        } else {
            finance.setVisibility(View.GONE);
        }
        if ((SharedPref.getStringFromSharedPref(AppConstants.TRACTOR, getApplicationContext())).equals("No")) {
            SharedPref.saveStringInSharedPref(AppConstants.TRACTORFINANCE,"No", getApplicationContext());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

         // DO Nothing
    }
}

