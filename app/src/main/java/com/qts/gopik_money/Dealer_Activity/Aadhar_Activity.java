package com.qts.gopik_money.Dealer_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.AADHAR_CONSENT_MODEL;
import com.qts.gopik_money.Model.AadharEnterResponseModel;
import com.qts.gopik_money.Model.TagID_MODEL;
import com.qts.gopik_money.Pojo.AADHAR_CONSENT_POJO;
import com.qts.gopik_money.Pojo.AadharNext_POJO;
import com.qts.gopik_money.Pojo.Aadhar_POJO;
import com.qts.gopik_money.Pojo.ClientData;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aadhar_Activity extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    EditText aadharname;
    EditText  mAadharNo;
    EditText  mEdtOtp;
    TextView check;
    TextView mSave_submit;
    TextView mtxtSkipAadhar;

    private static String val3;
    Long tsLong;
    String ts;
    String val;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        custPrograssbar = new CustPrograssbar();
        aadharname = (EditText) findViewById(R.id.aadharname);
        mAadharNo = (EditText) findViewById(R.id.aadharno);
        mSave_submit = (TextView) findViewById(R.id.save_submit);
        mEdtOtp = (EditText) findViewById(R.id.mEdtOtp);
        mtxtSkipAadhar= (TextView) findViewById(R.id.txtSkipAadhar);
        check = (TextView) findViewById(R.id.check1);
        tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();
        val = "" + ((int) (Math.random() * 9000) + 1000);

//        getRandomNumberString();
        //  mAadharNo.setText("831105408819");
        // aadharname.setText("Ankit Dattatray Tompe");

        check.setOnClickListener(v -> GoatAadharValidation(mAadharNo.getText().toString().trim(), aadharname.getText().toString().trim()));
        mtxtSkipAadhar.setOnClickListener(v -> {
            Intent it = new Intent(Aadhar_Activity.this, GOAT_PAN_CARD_Details.class);
            startActivity(it);
        });

//        mSave_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!mEdtOtp.getText().toString().trim().isEmpty()) {
//                    mOtp = mEdtOtp.getText().toString().trim();
//                    GoatAadharValidationNext(mNo, mID, mKey,mOtp);
//                } else {
//                    Toast.makeText(Aadhar_Activity.this, "OTP should not be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void GoatAadharValidation(String mNumber, String mName) {
        custPrograssbar.prograssCreate(Aadhar_Activity.this);
        Aadhar_POJO mAadhar_POJO = new Aadhar_POJO(mName, mNumber);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<AadharEnterResponseModel> call = restApis.GoatAadharValidation(mAadhar_POJO);
        call.enqueue(new Callback<AadharEnterResponseModel>() {
            @Override
            public void onResponse(Call<AadharEnterResponseModel> call, Response<AadharEnterResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.AADHAR_NAME, mName, getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ADHAR_NUMBER, mNumber, getApplicationContext());
                        // Log.e("GETMOBILE",SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER,getApplicationContext()));
                        SharedPref.saveStringInSharedPref(AppConstants.AadharKey, response.body().getPayload().getKey(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AadharKeyID, response.body().getPayload().getId(), getApplicationContext());

                        startActivity(new Intent(Aadhar_Activity.this,Goat_CAP_AADHAR_OTP_DEALER_Verify.class));

                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<AadharEnterResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void GoatAadharValidationNext(String mNo, String mID, String mKey,String mOtp) {
        custPrograssbar.prograssCreate(Aadhar_Activity.this);
        AadharNext_POJO mAadharNext_POJO = new AadharNext_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                mNo, mKey, mID, mOtp);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.GoatAadharValidationNext(mAadharNext_POJO);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }


    public static String getRandomNumberString() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        val3 = String.format("%06d", number);


        return val3;
    }

    private void adharconsentapi() {
        AADHAR_CONSENT_POJO pojo = new AADHAR_CONSENT_POJO("19", "82", "12.12.12.12"
                , "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:80.0) Gecko/20100101 Firefox/80.0",
                val, val3, "y", aadharname.getText().toString(), ts, "I authorize Karza Technologies Private Limited to access my Aadhaar number and help me fetch my details. I understand that Karza will not be storing or sharing the same in any manner."
                , new ClientData(val3));
        RestApis restApis = NetworkHandler.instanceMaker8().create(RestApis.class);
        Call<AADHAR_CONSENT_MODEL> call = restApis.adharconsentapi(pojo);
        call.enqueue(new Callback<AADHAR_CONSENT_MODEL>() {
            @Override
            public void onResponse(Call<AADHAR_CONSENT_MODEL> call, Response<AADHAR_CONSENT_MODEL> response) {
                if (response.body() != null) {
                    SharedPref.saveStringInSharedPref(AppConstants.CASEID, response.body().getClientData().getCaseId(), getApplicationContext());
                    SharedPref.saveStringInSharedPref(AppConstants.ACCESSKEY, response.body().getResult().getAccessKey(), getApplicationContext());

                }
            }

            @Override
            public void onFailure(Call<AADHAR_CONSENT_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });

    }
}