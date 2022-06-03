package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Edit_Profile;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Model.Profile_Update_DEALER_MODEL;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Profile_Update_DEALER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_delaer_Profile extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    EditText ifsccode, accountno, email, name1, reaccountno, state,gst;
    String acc;
    Integer x = 0;
    LinearLayout layout, reacc, st;
    ImageView search;
    TextView bankname, tt, phonenumber, address, branch, btn_countinue, validaccountno,statetextview,gsst,btn_edit,textviewprofile;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView visibleoff, visible,eye2;
    ImageView hometoolbar, backarrow;

    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delaer__profile);

        btn_countinue = (TextView) findViewById(R.id.btn_countinue);

        email = (EditText) findViewById(R.id.email);
        name1 = (EditText) findViewById(R.id.name1);
        state = (EditText)findViewById(R.id.state);
        gsst= (TextView) findViewById(R.id.gs);
        gst = (EditText) findViewById(R.id.gst);
        textviewprofile= (TextView) findViewById(R.id.textviewprofile);

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());

        phonenumber = (TextView) findViewById(R.id.phonenumber);

        custPrograssbar = new CustPrograssbar();

        btn_edit= (TextView) findViewById(R.id.btn_edit);

        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));


        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                profile_update();
            }
        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Edit_delaer_Profile.this,
                        MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY,
                        AppConstants.PROFILE__DEALER_FRAGMENT);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Edit_delaer_Profile.this,
                        MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY,
                        AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });






        profile_details();

        return ;
    }

    private void profile_details() {
        custPrograssbar.prograssCreate(getApplicationContext());
        ProfileDetails_DEALER_POJO pojo = new ProfileDetails_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<ProfileDetails_DEALER_MODEL> call = restApis.profile_details(pojo);
        call.enqueue(new Callback<ProfileDetails_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<ProfileDetails_DEALER_MODEL> call, Response<ProfileDetails_DEALER_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getApplicationContext());
                        if ((response.body().getPayload().getProfile().get(0).getGst_no().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            name1.setText(response.body().getPayload().getProfile().get(0).getName());
                            email.setText(response.body().getPayload().getProfile().get(0).getEmail());
                            Log.e("hhghghhuu", "bfvn");

                            gst.setText(response.body().getPayload().getProfile().get(0).getGst_no());


                            SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER, phonenumber.getText().toString(), getApplicationContext());
                            SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getApplicationContext());

                            SharedPref.saveStringInSharedPref(AppConstants.NAME_SUBUSER, response.body().getPayload().getProfile().get(0).getName(), getApplicationContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DEALER_EMAIL, response.body().getPayload().getProfile().get(0).getEmail(),getApplicationContext());



                            textviewprofile.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }


            @Override
            public void onFailure(Call<ProfileDetails_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void profile_update() {
        custPrograssbar.prograssCreate(getApplicationContext());

        Profile_Update_DEALER_POJO pojo = new Profile_Update_DEALER_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                        name1.getText().toString(),
                        email.getText().toString(),
                      gst.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Profile_Update_DEALER_MODEL> call = restApis.profile_update(pojo);
        call.enqueue(new Callback<Profile_Update_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Profile_Update_DEALER_MODEL> call, Response<Profile_Update_DEALER_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        profile_details();


                        x = 1;
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);


                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Profile_Update_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


