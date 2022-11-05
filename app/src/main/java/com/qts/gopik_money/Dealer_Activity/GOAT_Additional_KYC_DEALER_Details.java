package com.qts.gopik_money.Dealer_Activity;

import android.app.DatePickerDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.GOATKYCSTORE_MODEL;
import com.qts.gopik_money.Model.Profile_data_MODEL;
import com.qts.gopik_money.Pojo.GOATKYCSTOREPOJO;
import com.qts.gopik_money.Pojo.Profile_data_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GOAT_Additional_KYC_DEALER_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner_salution;
    Spinner spinner_customername;
    Spinner spinner_maritialstatus;
    Spinner spinner_qualification;
    Spinner spinner_accomodationtype;
    Spinner spinner_sameaddress;
    Spinner spinner_employmenttype;
    Spinner mSpinnerCaste;
    Spinner spinnerGender;
    String networkError = "It seems your Network is unstable . Please Try again!";
    String mSelectOption = "Select Option";
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar;
    ImageView  backarrow;
    final Calendar myCalendar= Calendar.getInstance();
    String name_VOTER = SharedPref.getStringFromSharedPref(AppConstants.NAME_VOTERID, getApplication());
    String name_PAN = SharedPref.getStringFromSharedPref(AppConstants.PAN_NAME, getApplication());
    String[] salution = {"Select Salutation", "Mr.", "Ms."};
    String[] customername = {"Select Customer Name", name_VOTER, name_PAN};
    String[] maritialstatus = {"Select Maritial Status", "SINGLE", "MARRIED", "DIVORCED", "WIDOWED", "SEPARATED"};
    String[] qualification = {"Select Qualification", "10th", "12th", "Primary Schooling", "OTHERS", "ENGINEER", "ARCHITECT",
            "LAWYER", "DOCTOR", "MBA", "CA", "DOCTORATE", "POST GRADUATE", "GRADUATE", "UNDER GRADUATE"};
    String[] accomodationtype = {"Select Accomodation Type", "COMPANY LEADS OR ALLOTED", "JOINTLY OWNED", "OWNED", "PAGADI",
            "PARENTAL OWNED", "RENTED", "SHARED ACCOMODATION"};
    String[] sameaddress = {mSelectOption, "Yes", "No"};
    String[] employementtype = {mSelectOption, "SALARIED", "SELF EMPLOYED"};
    String[] mSpinnerCasteList = {"Select Caste", "General", "SC", "ST", "OBC"};
    String[] mSpinnerGenderList = {"Select Gender", "Male", "Female"};

    LinearLayout salaried;
    LinearLayout  self_employed;
    LinearLayout  officelayout;
    String TAG = "finalbikepayment";
    TextView save;
    TextView  mobilenoedittext;
    TextView  tractortype;
    TextView  tractorfinance;
    TextView dateofbirth;
    EditText grossmonthlyincomeedittext;
    EditText numberinyearsedittext;
    EditText companynameedittext;
    EditText annualincomeedittext;
    EditText totalamountturoveredittext;
    EditText expbussinessedittext;
    EditText noofyearedittext;
    EditText permamentadressedittext;
    EditText currentaddressedittext;
    EditText emailideditext;
    EditText mEdtFatherName;
    EditText mEdtMotherName;
    EditText pincodeeditttext;
    EditText officefulladdressedittext1;
    EditText currentaddresspincodeedittext;
    EditText edtBankName;
    EditText edtBank_Number;
    EditText edtBank_IFSC_number;
    String catagory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goat_activity_additional__k_y_c__d_e_a_l_e_r__details);

        custPrograssbar = new CustPrograssbar();
        salaried = (LinearLayout) findViewById(R.id.salaried);
        self_employed = (LinearLayout) findViewById(R.id.self_employed);
        mSpinnerCaste = (Spinner) findViewById(R.id.spinnerCaste);
        save = (TextView) findViewById(R.id.save);
        dateofbirth = (TextView) findViewById(R.id.dateofbirth);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
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

        mEdtFatherName = (EditText) findViewById(R.id.edtFatherName);
        mEdtMotherName = (EditText) findViewById(R.id.edtMothersName);
        edtBankName = (EditText) findViewById(R.id.edtBankName);
        edtBank_Number = (EditText) findViewById(R.id.edtBank_Number);
        edtBank_IFSC_number = (EditText) findViewById(R.id.edtBank_IFSC_number);
        grossmonthlyincomeedittext = (EditText) findViewById(R.id.grossmonthlyincomeedittext);
        mobilenoedittext.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));

        tractortype = (TextView) findViewById(R.id.tractortype);
        tractorfinance = (TextView) findViewById(R.id.tractorfinance);

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

        spinner_employmenttype = findViewById(R.id.choose_employeementtype);
        spinner_employmenttype.setOnItemSelectedListener(this);

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerGender.setOnItemSelectedListener(this);

        ArrayAdapter gender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mSpinnerGenderList);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(gender);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, salution);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salution.setAdapter(ad);

        mSpinnerCaste.setOnItemSelectedListener(this);
        ArrayAdapter ad0 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mSpinnerCasteList);
        ad0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCaste.setAdapter(ad0);

        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, customername);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_customername.setAdapter(ad1);

        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maritialstatus);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maritialstatus.setAdapter(ad2);

        ArrayAdapter ad3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, qualification);
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_qualification.setAdapter(ad3);

        ArrayAdapter ad4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, accomodationtype);
        ad4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_accomodationtype.setAdapter(ad4);

        ArrayAdapter ad5 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sameaddress);
        ad5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sameaddress.setAdapter(ad5);

        ArrayAdapter ad8 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, employementtype);
        ad8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_employmenttype.setAdapter(ad8);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        dateofbirth.setOnClickListener(v -> new DatePickerDialog(GOAT_Additional_KYC_DEALER_Details.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        save.setOnClickListener(view -> {
            SubmitKYCDetails();


        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(GOAT_Additional_KYC_DEALER_Details.this, GOAT_KYC_ImageCapture.class);
            startActivity(it);
        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(GOAT_Additional_KYC_DEALER_Details.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);
        });

    }

    private void updateLabel() {
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateofbirth.setText(dateFormat.format(myCalendar.getTime()));
        dateofbirth.setTextColor(getColor(R.color.black));
        SharedPref.saveStringInSharedPref(AppConstants.CUST_DATE_OF_BIRTH,dateFormat.format(myCalendar.getTime()),getApplicationContext());

    }


    private void profile_data() {
        custPrograssbar.prograssCreate(GOAT_Additional_KYC_DEALER_Details.this);
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
                if (response.body() != null&&response.body().getCode().equals("200")) {
                        Intent it = new Intent(GOAT_Additional_KYC_DEALER_Details.this, Hero_Customer_Details_Verification_Dealer.class);
                        startActivity(it);
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Profile_data_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void SubmitKYCDetails() {
        GOATKYCSTOREPOJO mGOATKYCSTOREPOJO = new GOATKYCSTOREPOJO(
                SharedPref.getStringFromSharedPref(AppConstants.ORGANISATIONTYPE, getApplicationContext()),
                numberinyearsedittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.MARITIALSTATUSTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ACCOMODATIONTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()),
                noofyearedittext.getText().toString(),
                permamentadressedittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext()),
                expbussinessedittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.GENDER_TYPE, getApplicationContext()),
                officefulladdressedittext1.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.QUALIFICATIONTYPE, getApplicationContext()),
                currentaddressedittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.SAMEADDRESSTYPE, getApplicationContext()),
                "NA",
                totalamountturoveredittext.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.SALUTIONTYPE, getApplicationContext()),
                pincodeeditttext.getText().toString(),
               SharedPref.getStringFromSharedPref(AppConstants.INDUSTRYTYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUSTOMERNAMETYPE, getApplicationContext()),
                companynameedittext.getText().toString(),
                emailideditext.getText().toString(),
                "NA",
                grossmonthlyincomeedittext.getText().toString(),
                "NA",
                "NA",
                mEdtFatherName.getText().toString(),
                mEdtMotherName.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.CATAGORY_TYPE,getApplicationContext()),
                edtBankName.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.CUST_DATE_OF_BIRTH,getApplicationContext()),
                edtBank_Number.getText().toString(),
                edtBank_IFSC_number.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GOATKYCSTORE_MODEL> call = restApis.GoatKycdetailstore(mGOATKYCSTOREPOJO);
        call.enqueue(new Callback<GOATKYCSTORE_MODEL>() {
            @Override
            public void onResponse(Call<GOATKYCSTORE_MODEL> call, Response<GOATKYCSTORE_MODEL> response) {
                if (response.body() != null&&response.body().getCode() == 200) {
                        Toast.makeText(GOAT_Additional_KYC_DEALER_Details.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(GOAT_Additional_KYC_DEALER_Details.this, Goat_Customer_Details_Verification_Dealer.class);
                        SharedPref.saveStringInSharedPref(AppConstants.CUSTOMER_WORK_EXP,numberinyearsedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NUBER_OF_YEAR_IN_CURRENT_ADDRS, noofyearedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PERMANENT_ADDRS,permamentadressedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.BUSINESS_EXP,expbussinessedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CURRENT_ADDRS,currentaddressedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.TOTAL_TURNOUVER,totalamountturoveredittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_OFFICE_PIN,pincodeeditttext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.COMPANY_NAME,companynameedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_EMAIL,emailideditext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.GROSS_MONTHLY_INCM,grossmonthlyincomeedittext.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_FATHER_NAME,mEdtFatherName.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_MOTHER_NAME,mEdtMotherName.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_BANK_NAME,edtBankName.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_BANK_NUMBER,edtBank_Number.getText().toString(),getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.CUST_IFSC,edtBank_IFSC_number.getText().toString(),getApplicationContext());
                        startActivity(it);
                    } else {
                        Toast.makeText(GOAT_Additional_KYC_DEALER_Details.this, networkError, Toast.LENGTH_LONG).show();
                    }
            }
            @Override
            public void onFailure(Call<GOATKYCSTORE_MODEL> call, Throwable t) {
                Toast.makeText(GOAT_Additional_KYC_DEALER_Details.this, networkError, Toast.LENGTH_LONG).show();
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
        int index8 = spinner_employmenttype.getSelectedItemPosition();
        int index9 = mSpinnerCaste.getSelectedItemPosition();
        int index10 = spinnerGender.getSelectedItemPosition();
        SharedPref.saveStringInSharedPref(AppConstants.SALUTIONTYPE, salution[index], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.CUSTOMERNAMETYPE, customername[index1], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.MARITIALSTATUSTYPE, maritialstatus[index2], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.QUALIFICATIONTYPE, qualification[index3], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.ACCOMODATIONTYPE, accomodationtype[index4], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.SAMEADDRESSTYPE, sameaddress[index5], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.EMPLOYEMENTTYPE, employementtype[index8], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.CATAGORY_TYPE, mSpinnerCasteList[index9], getApplicationContext());
        SharedPref.saveStringInSharedPref(AppConstants.GENDER_TYPE, mSpinnerGenderList[index10], getApplicationContext());

        if ((SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext())).equals("Select Option")) {
            salaried.setVisibility(View.GONE);
            self_employed.setVisibility(View.GONE);
            officelayout.setVisibility(View.GONE);
        } else if ((SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext())).equals("SALARIED")) {
            salaried.setVisibility(View.VISIBLE);
            self_employed.setVisibility(View.GONE);
            officelayout.setVisibility(View.VISIBLE);
        } else if ((SharedPref.getStringFromSharedPref(AppConstants.EMPLOYEMENTTYPE, getApplicationContext())).equals("SELF EMPLOYED")) {
            self_employed.setVisibility(View.VISIBLE);
            salaried.setVisibility(View.GONE);
            officelayout.setVisibility(View.VISIBLE);
        }

        if ((SharedPref.getStringFromSharedPref(AppConstants.SAMEADDRESSTYPE, getApplicationContext())).equals("Yes")) {
            permamentadressedittext.setText(currentaddressedittext.getText());

        } else {
            permamentadressedittext.setText("");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}

