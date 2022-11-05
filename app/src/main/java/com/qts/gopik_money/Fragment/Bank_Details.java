package com.qts.gopik_money.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.Home;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Bankacc_verification_MODEL;
import com.qts.gopik_money.Model.Broker_bank_details_update_MODEL;
import com.qts.gopik_money.Model.Broker_profile_details_MODEL;
import com.qts.gopik_money.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_money.Pojo.Broker_bank_details_update_POJO;
import com.qts.gopik_money.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bank_Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bank_Details extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {
    EditText ifsccode;
    EditText accountno;
    EditText email;
    EditText name1;
    EditText reaccountno;
    EditText branch;
    EditText accountholdername;
    String acc;
    Integer x = 0;
    String networkError = "It seems your Network is unstable . Please Try again!";
    LinearLayout layout;
    LinearLayout    reacc;
    ImageView search;
    TextView bankname;
    TextView  policy_button;
    TextView  address;
    TextView  btn_countinue;
    TextView   validaccountno;
    TextView btn_edit;
    CustPrograssbar custPrograssbar;

    ImageView  visible;
    Boolean flag = false;


    public Bank_Details() {
        // Required empty public constructor
    }


    public static Bank_Details newInstance() {
        Bank_Details fragment = new Bank_Details();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank__details, container, false);
        branch = (EditText) view.findViewById(R.id.branch);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);
        address = (TextView) view.findViewById(R.id.address);
        bankname = (TextView) view.findViewById(R.id.bankname);
        ifsccode = (EditText) view.findViewById(R.id.ifsccode);
        accountno = (EditText) view.findViewById(R.id.accountno);

        reacc = (LinearLayout) view.findViewById(R.id.reacc);

        layout = (LinearLayout) view.findViewById(R.id.layout);
        btn_edit = (TextView) view.findViewById(R.id.btn_edit);
        reaccountno = (EditText) view.findViewById(R.id.reaccountno);
        accountholdername = (EditText) view.findViewById(R.id.accountholdername);
        custPrograssbar = new CustPrograssbar();
        reaccountno.addTextChangedListener(this);
        ifsccode.addTextChangedListener(this);
        search = (ImageView) view.findViewById(R.id.search);
        visible = (ImageView) view.findViewById(R.id.eye);
        validaccountno = (TextView) view.findViewById(R.id.validaccountno);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);

        policy_button = (TextView) view.findViewById(R.id.policy_button2);

        policy_button.setLinkTextColor(Color.BLACK);
        policy_button.setMovementMethod(LinkMovementMethod.getInstance());
        visible.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {

                accountno.setTransformationMethod(null);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                accountno.setTransformationMethod(new PasswordTransformationMethod());
            }
            return true;
        });
        btn_countinue.setOnClickListener(view1 -> signupvalidation());

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getContext());
        broker_profile_details();
        return view;
    }

    private void signupvalidation() {
        if (accountno.getText().toString().isEmpty()
                && reaccountno.getText().toString().isEmpty()
                && accountholdername.getText().toString().isEmpty() && ifsccode.getText().toString().isEmpty()
                && branch.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter above details", Toast.LENGTH_LONG).show();
        } else if (accountno.getText().toString().isEmpty()) {
            accountno.setError("Please Enter Valid Account Number");

        } else if (reaccountno.getText().toString().isEmpty()) {
            reaccountno.setError("Please Enter Valid Re-Account Number");

        } else if (accountholdername.getText().toString().isEmpty()) {
            accountholdername.setError("Please Enter Valid Account Holder Name");

        } else if (ifsccode.getText().toString().isEmpty()) {
            ifsccode.setError("Please Enter Valid IFSC Code");

        } else if (branch.getText().toString().isEmpty()) {
            branch.setError("Please Enter Valid Branch");

        } else {


            bankacc_verification();
        }

    }

    private void broker_profile_details() {
        custPrograssbar.prograssCreate(getContext());
        Broker_profile_details_POJO pojo = new Broker_profile_details_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER, getContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_details_MODEL> call = restApis.broker_profile_details(pojo);
        call.enqueue(new Callback<Broker_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_details_MODEL> call, Response<Broker_profile_details_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getBroker_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getAcc_holder_name().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            accountholdername.setText(response.body().getPayload().getProfile().get(0).getAcc_holder_name());
                            accountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());
                            branch.setText(response.body().getPayload().getProfile().get(0).getBranch_name());
                            ifsccode.setText(response.body().getPayload().getProfile().get(0).getIfsc_no());

                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME, response.body().getPayload().getProfile().get(0).getBroker_name(), getContext());
                            reaccountno.setVisibility(View.GONE);
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            accountno.setFocusable(false);
                            accountholdername.setFocusable(false);
                            reacc.setVisibility(View.GONE);
                            ifsccode.setFocusable(false);
                            branch.setFocusable(false);

                        }

                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void bankacc_verification() {
        custPrograssbar.prograssCreate(getContext());
        Bankacc_verification_POJO pojo = new Bankacc_verification_POJO(accountno.getText().toString(), accountholdername
                .getText().toString(), ifsccode.getText().toString(), "y", "Individual");
        RestApis restApis = NetworkHandler.instanceMaker6().create(RestApis.class);
        Call<Bankacc_verification_MODEL> call = restApis.bankacc_verification(pojo);
        call.enqueue(new Callback<Bankacc_verification_MODEL>() {
            @Override
            public void onResponse(Call<Bankacc_verification_MODEL> call, Response<Bankacc_verification_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();


                    if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC01")) {

                        Toast.makeText(getContext(), "Transaction Successful", Toast.LENGTH_LONG).show();
                        broker_bank_details_update();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC02")) {
                        Toast.makeText(getContext(), "Credit Transaction Failed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC03")) {
                        Toast.makeText(getContext(), "Invalid Beneficiary Account Number or IFSC", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC04")) {
                        Toast.makeText(getContext(), "Amount Limit Exceeded", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC05")) {
                        Toast.makeText(getContext(), "Account Blocked/Frozen", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC06")) {
                        Toast.makeText(getContext(), "NRE Account", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC07")) {
                        Toast.makeText(getContext(), "Account Closed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC08")) {
                        Toast.makeText(getContext(), "Limit Exceeded For Member Bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC09")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC10")) {
                        Toast.makeText(getContext(), "Function Not Valid", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC11")) {
                        Toast.makeText(getContext(), "Aadhaar Belong To Remitter Bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC12")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC13")) {
                        Toast.makeText(getContext(), "Customer Transaction Limit Exceeded", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC14")) {
                        Toast.makeText(getContext(), "Payee Is An Individual And Not A Merchant", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC15")) {
                        Toast.makeText(getContext(), "Payee Is A Merchant And Not An Individual", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC16")) {
                        Toast.makeText(getContext(), "Foreign Inward Remittance Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC17")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed As Invalid Payment Reference", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC18")) {
                        Toast.makeText(getContext(), "Invalid Amount", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC19")) {
                        Toast.makeText(getContext(), "Invalid Remitter Account Number", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC20")) {
                        Toast.makeText(getContext(), "General Error", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC21")) {
                        Toast.makeText(getContext(), "Invalid Transaction Type", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC22")) {
                        Toast.makeText(getContext(), "Invalid Amount Field", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC23")) {
                        Toast.makeText(getContext(), "IMPS Service not available for the selected bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC24")) {
                        Toast.makeText(getContext(), "Duplicate Transaction", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC25")) {
                        Toast.makeText(getContext(), "Beneficiary Bank Not Enable For P2a", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC26")) {
                        Toast.makeText(getContext(), "Insufficient Balance In Pool A/C", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC27")) {
                        Toast.makeText(getContext(), "Invalid Account", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC28")) {
                        Toast.makeText(getContext(), "Invalid Response Code", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC29")) {
                        Toast.makeText(getContext(), "Exceeds Account Limit", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC30")) {
                        Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Bankacc_verification_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// Nothing to do
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("hhghghhuu", "6666");
        validaccountno.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.e("hhghghhuu", "9999");
        String account = accountno.getText().toString();
        String reaccount = reaccountno.getText().toString();
        if (account.equals(reaccount)) {
            validaccountno.setVisibility(View.GONE);


        }
        if (accountno.length() == 0) {
            validaccountno.setVisibility(View.GONE);

        }
        if (reaccount.length() == 0) {
            validaccountno.setVisibility(View.GONE);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Nothing to do
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
// Nothing to do
    }

    private void broker_bank_details_update() {
        custPrograssbar.prograssCreate(getContext());
        Broker_bank_details_update_POJO pojo = new Broker_bank_details_update_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()),
                accountholdername.getText().toString(), accountno.getText().toString(), ifsccode.getText().toString(),
                branch.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_bank_details_update_MODEL> call = restApis.broker_bank_details_update(pojo);
        call.enqueue(new Callback<Broker_bank_details_update_MODEL>() {
            @Override
            public void onResponse(Call<Broker_bank_details_update_MODEL> call, Response<Broker_bank_details_update_MODEL> response) {
                custPrograssbar.closePrograssBar();
                if (response.body() != null&&response.body().getCode() == 200) {

                        Intent it = new Intent(getContext(), Home.class);
                        startActivity(it);

                        Toast.makeText(getContext(), "Transaction Successful", Toast.LENGTH_LONG).show();





                }

            }

            @Override
            public void onFailure(Call<Broker_bank_details_update_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }
}