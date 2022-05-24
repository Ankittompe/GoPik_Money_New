package com.qts.gopik_loan.Activity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

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

import com.qts.gopik_loan.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_update_MODEL;
import com.qts.gopik_loan.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_loan.Pojo.Broker_profile_update_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    EditText ifsccode, email, name1, state;
    String acc;
    Integer x = 0;
    LinearLayout layout, reacc, st;
    ImageView search;
    TextView bankname, tt, phonenumber, address, branch, btn_countinue, statetextview, validaccountno, reaccountno, accountno;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView visibleoff, visible, eye2;
    Boolean flag = false;
    ImageView hometoolbar, backarrow;
    private static int TIME_OUT = 3000;
    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        branch = (TextView) findViewById(R.id.branch);
        btn_countinue = (TextView) findViewById(R.id.btn_countinue);
        address = (TextView) findViewById(R.id.address);
        bankname = (TextView) findViewById(R.id.bankname);
        ifsccode = (EditText) findViewById(R.id.ifsccode);
        accountno = (TextView) findViewById(R.id.accountno);
        email = (EditText) findViewById(R.id.email);
        name1 = (EditText) findViewById(R.id.name1);
        state = (EditText) findViewById(R.id.state);
        reacc = (LinearLayout) findViewById(R.id.reacc);
        phonenumber = (TextView) findViewById(R.id.phonenumber);
        layout = (LinearLayout) findViewById(R.id.layout);
        reaccountno = (TextView) findViewById(R.id.reaccountno);
        custPrograssbar = new CustPrograssbar();
        reaccountno.addTextChangedListener(this);
        ifsccode.addTextChangedListener(this);
        search = (ImageView) findViewById(R.id.search);
        choose_identity = (Spinner) findViewById(R.id.choose_identity);
        statetextview = (TextView) findViewById(R.id.statetextview);
        statespinner = (LinearLayout) findViewById(R.id.statespinner);
        st = (LinearLayout) findViewById(R.id.st);
        tt = (TextView) findViewById(R.id.tt);

        visible = (ImageView) findViewById(R.id.eye);

        validaccountno = (TextView) findViewById(R.id.validaccountno);

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getApplicationContext());
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getApplicationContext()));
      /*  st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.VISIBLE);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.VISIBLE);
            }
        });*/
        visible.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("hhghghhuu", "apppppppkkkkkk");
                    accountno.setTransformationMethod(null);
                } else {
                    Log.e("hhghghhuu", "apppppppkkkkkkgg");
                    accountno.setTransformationMethod(new PasswordTransformationMethod());
                }

                return true;
            }

        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acc = ifsccode.getText().toString();
                account(acc);
            }
        });

        statetextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                statespinner.setVisibility(View.VISIBLE);

                return true;
            }
        });


        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.GONE);
                broker_profile_update();
            }
        });



     /*   visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visible.setVisibility(View.VISIBLE);

                accountno.setTransformationMethod(new PasswordTransformationMethod());
            }
        });*/

        //spinner1
        ArrayAdapter ad
                = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                statelocation);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_identity.setAdapter(ad);
        choose_identity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = choose_identity.getSelectedItemPosition();

                SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER, statelocation[index], getApplicationContext());
                /*       SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER_ITEM,statelocation,getContext());*/
                Log.e("hhghghhuu", "bfvn" + SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getApplicationContext()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (x == 1) {
            statespinner.setVisibility(View.GONE);
        }
        broker_profile_details();
       /* SharedPref.saveStringInSharedPref(AppConstants.STATETEXTVIEW,statetextview.getText().toString(),getContext());
        if ((SharedPref.getStringFromSharedPref(AppConstants.STATETEXTVIEW,getContext())).equals("State")) {
            Log.e("cccccc", "cccccc" +statetextview.getText().toString());
            statespinner.setVisibility(View.VISIBLE);

        } else  {
            Log.e("hhghghhuu", "bfvn" + SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext()));
            statetextview.setVisibility(View.VISIBLE);
            statespinner.setVisibility(View.GONE);
            statetextview.setText(SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER,getContext()));

        }*/
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Edit_Profile.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.PROFILE_FRAGMENT);
                startActivity(it);

            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Edit_Profile.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        return;

    }

    private void broker_profile_details() {
        custPrograssbar.prograssCreate(Edit_Profile.this);
        Broker_profile_details_POJO pojo = new Broker_profile_details_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_details_MODEL> call = restApis.broker_profile_details(pojo);
        call.enqueue(new Callback<Broker_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_details_MODEL> call, Response<Broker_profile_details_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getBroker_code(), getApplicationContext());
                        if ((response.body().getPayload().getProfile().get(0).getBroker_name().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            name1.setText(response.body().getPayload().getProfile().get(0).getBroker_name());
                            email.setText(response.body().getPayload().getProfile().get(0).getBroker_email());
                            Log.e("hhghghhuu", "bfvn");
                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME,response.body().getPayload().getProfile().get(0).getBroker_name(),getApplicationContext());
                            address.setText(response.body().getPayload().getProfile().get(0).getBroker_address());
                            bankname.setText(response.body().getPayload().getProfile().get(0).getBank_name());
                            accountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());
                            ifsccode.setText(response.body().getPayload().getProfile().get(0).getIfsc_no());
                            branch.setText(response.body().getPayload().getProfile().get(0).getBranch_name());
                            layout.setVisibility(View.VISIBLE);
                            statetextview.setText(response.body().getPayload().getProfile().get(0).getBroker_state());
                          /*  reaccountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());*/
                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getApplicationContext());
                            /*      int state_position=statelocation.get*/

                            search.setVisibility(View.GONE);

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        validaccountno.setVisibility(View.VISIBLE);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String account = accountno.getText().toString();
        String reaccount = reaccountno.getText().toString();

        if (account.equals(reaccount)) {
            validaccountno.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            ifsccode.setText("");
            search.setVisibility(View.VISIBLE);
            address.setText("");
            bankname.setText("");
            branch.setText("");
        }
        if (accountno.length() == 0) {
            validaccountno.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }
        if (reaccount.length() == 0) {
            validaccountno.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);

        }    Log.e("yyyy","bdhcdsh"+ifsccode.length());

        if(ifsccode.getText().toString().length()==0){
            ifsccode.setError("Please enter valid IFSC code");
            address.setText("");
            branch.setText("");
            bankname.setText("");
        }
        Log.e("yyyy","bdhcdsh"+ifsccode.length());


    }


    private void broker_profile_update() {
        custPrograssbar.prograssCreate(Edit_Profile.this);
        Broker_profile_update_POJO pojo = new Broker_profile_update_POJO(name1.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                email.getText().toString(), phonenumber.getText().toString(),
                bankname.getText().toString(), accountno.getText().toString(), ifsccode.getText().toString(),
                branch.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getApplicationContext())
                , address.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_update_MODEL> call = restApis.broker_profile_update(pojo);
        call.enqueue(new Callback<Broker_profile_update_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_update_MODEL> call, Response<Broker_profile_update_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        broker_profile_details();

                        x = 1;
                        Intent it = new Intent(getApplicationContext(), Home.class);
                        startActivity(it);


                    } else {

                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_update_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*int index = choose_identity.getSelectedItemPosition();
        SharedPref.saveStringInSharedPref(AppConstants.STATE_VALUE, statelocation[index], getContext());*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void account(String acc) {
        custPrograssbar.prograssCreate(getApplicationContext());
        Log.e("ankit", "akit" + acc);
        RestApis restApis = NetworkHandler.instanceMaker3().create(RestApis.class);
        Call<ACCOUNT_NO_MODEL> call = restApis.account(acc);
        call.enqueue(new Callback<ACCOUNT_NO_MODEL>() {
            @Override
            public void onResponse(Call<ACCOUNT_NO_MODEL> call, Response<ACCOUNT_NO_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();


                    address.setText(response.body().getADDRESS());
                    branch.setText(response.body().getBRANCH());

                    bankname.setText(response.body().getBANK());


                }

                else {
                    custPrograssbar.closePrograssBar();

                    ifsccode.setError("Please enter valid IFSC code");
                }

                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<ACCOUNT_NO_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                ifsccode.setError("Please enter IFSC code");
                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

}