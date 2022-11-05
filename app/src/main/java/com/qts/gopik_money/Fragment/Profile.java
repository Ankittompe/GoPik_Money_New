package com.qts.gopik_money.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.Edit_Profile;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_money.Model.Bankacc_verification_MODEL;
import com.qts.gopik_money.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {
    String networkError = "It seems your Network is unstable . Please Try again!";
    EditText ifsccode;
    EditText accountno;
    EditText email;
    EditText name1;
    EditText reaccountno;
    EditText state;
    EditText accountholdername;
    String acc;
    Integer x = 0;
    LinearLayout layout;
    LinearLayout  reacc;
    LinearLayout st;
    ImageView search;
    TextView bankname ;
    TextView tt;
    TextView phonenumber;
    TextView address;
    TextView branch;
    TextView btn_countinue;
    TextView statetextview;
    TextView validaccountno;
    TextView btn_edit;
    TextView textviewprofile;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView  visible;
    Boolean flag = false;

    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};

    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance() {
        Profile fragment = new Profile();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        branch = (TextView) v.findViewById(R.id.branch);
        btn_countinue = (TextView) v.findViewById(R.id.btn_countinue);
        address = (TextView) v.findViewById(R.id.address);
        bankname = (TextView) v.findViewById(R.id.bankname);
        ifsccode = (EditText) v.findViewById(R.id.ifsccode);
        accountno = (EditText) v.findViewById(R.id.accountno);
        email = (EditText) v.findViewById(R.id.email);
        name1 = (EditText) v.findViewById(R.id.name1);
        state = (EditText) v.findViewById(R.id.state);
        reacc = (LinearLayout) v.findViewById(R.id.reacc);
        phonenumber = (TextView) v.findViewById(R.id.phonenumber);
        layout = (LinearLayout) v.findViewById(R.id.layout);
        btn_edit = (TextView) v.findViewById(R.id.btn_edit);
        reaccountno = (EditText) v.findViewById(R.id.reaccountno);
        accountholdername= (EditText) v.findViewById(R.id.accountholdername);
        custPrograssbar = new CustPrograssbar();
        reaccountno.addTextChangedListener(this);
        ifsccode.addTextChangedListener(this);
        search = (ImageView) v.findViewById(R.id.search);
        choose_identity = (Spinner) v.findViewById(R.id.choose_identity);
        statetextview = (TextView) v.findViewById(R.id.statetextview);
        statespinner = (LinearLayout) v.findViewById(R.id.statespinner);
        textviewprofile = (TextView) v.findViewById(R.id.textviewprofile);
        st = (LinearLayout) v.findViewById(R.id.st);
        tt = (TextView) v.findViewById(R.id.tt);

        visible = (ImageView) v.findViewById(R.id.eye);
        validaccountno = (TextView) v.findViewById(R.id.validaccountno);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getContext());
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getContext()));
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

        statetextview.setOnTouchListener((view, motionEvent) -> {
            statespinner.setVisibility(View.VISIBLE);
            return true;
        });
        btn_edit.setOnClickListener(view -> {
            Intent it = new Intent(getContext(), Edit_Profile.class);
            startActivity(it);

        });
        search.setOnClickListener(view -> {
            acc = ifsccode.getText().toString();
            account(acc);
        });
        btn_countinue.setOnClickListener(view -> {
            statespinner.setVisibility(View.GONE);
          /*  broker_profile_update();*/
     signupvalidation();

        });

        visible.setOnTouchListener((v1, event) -> {


            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                accountno.setTransformationMethod(null);
            } else {

                accountno.setTransformationMethod(new PasswordTransformationMethod());
            }

            return true;
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
                getContext(),
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

                SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER, statelocation[index], getContext());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                //Do nothing
            }
        });

        if (x == 1) {
            statespinner.setVisibility(View.GONE);
        }
      /*  broker_profile_details();*/
       /* SharedPref.saveStringInSharedPref(AppConstants.STATETEXTVIEW,statetextview.getText().toString(),getContext());
        if ((SharedPref.getStringFromSharedPref(AppConstants.STATETEXTVIEW,getContext())).equals("State")) {
            Log.e("cccccc", "cccccc" +statetextview.getText().toString());
            statespinner.setVisibility(View.VISIBLE);

        } else  {
            statetextview.setVisibility(View.VISIBLE);
            statespinner.setVisibility(View.GONE);
            statetextview.setText(SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER,getContext()));

        }*/


        return v;

    }

    private void signupvalidation() {
        if (name1.getText().toString().isEmpty() && email.getText().toString().isEmpty()
         && accountno.getText().toString().isEmpty()
                && reaccountno.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter above details", Toast.LENGTH_LONG).show();
        } else if (name1.getText().toString().isEmpty()) {

            name1.setError("Please Enter Valid Customer Name");

        } else if (email.getText().toString().isEmpty()) {

            email.setError("Please Enter Valid Customer Name");

        }  else if (accountno.getText().toString().isEmpty()) {
            accountno.setError("Please Enter Valid Pincode");

        } else if (reaccountno.getText().toString().isEmpty()) {
            reaccountno.setError("Please Enter Valid State");

        } else {
            statespinner.setVisibility(View.GONE);

            bankacc_verification();
        }

    }

/*
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
                        if ((response.body().getPayload().getProfile().get(0).getBroker_name().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            name1.setText(response.body().getPayload().getProfile().get(0).getBroker_name());
                            email.setText(response.body().getPayload().getProfile().get(0).getBroker_email());
                            Log.e("hhghghhuu", "bfvn");
                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME,response.body().getPayload().getProfile().get(0).getBroker_name(),getContext());
                            address.setText(response.body().getPayload().getProfile().get(0).getBroker_address());
                            bankname.setText(response.body().getPayload().getProfile().get(0).getBank_name());
                            accountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());
                            ifsccode.setText(response.body().getPayload().getProfile().get(0).getIfsc_no());
                            branch.setText(response.body().getPayload().getProfile().get(0).getBranch_name());
                            reacc.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            statetextview.setText(response.body().getPayload().getProfile().get(0).getBroker_state());

                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext());
                            */
/*      int state_position=statelocation.get*//*


                            search.setVisibility(View.GONE);
                            name1.setFocusable(false);
                            address.setFocusable(false);
                            bankname.setFocusable(false);
                            accountno.setFocusable(false);
                            ifsccode.setFocusable(false);
                            email.setFocusable(false);
                            statetextview.setFocusable(false);
                            branch.setFocusable(false);
                            visible.setVisibility(View.GONE);
                            accountno.setInputType(InputType.TYPE_CLASS_TEXT );
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            textviewprofile.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //Do nothing
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

        }
        if (accountno.length() == 0) {
            validaccountno.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }
        if (reaccount.length() == 0) {
            validaccountno.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }
        if(ifsccode.getText().toString().length()==0){
            ifsccode.setError("Please enter valid IFSC code");

            branch.setText("");

        }
    }

    private boolean accountverification() {
        String account = accountno.getText().toString();
        String reaccount = reaccountno.getText().toString();
        if (account.equals(reaccount)) {
            layout.setVisibility(View.VISIBLE);
            flag = true;
        }

        return flag;


    }


    private void account(String acc) {
        custPrograssbar.prograssCreate(getContext());

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


                } else if (ifsccode.getText().toString().equals("")) {
                    address.setText("");
                    branch.setText("");
                    bankname.setText("");


                } else {
                    custPrograssbar.closePrograssBar();

                    ifsccode.setError("Please enter valid IFSC code");
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<ACCOUNT_NO_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                ifsccode.setError("Please enter IFSC code");
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }


/*
    private void broker_profile_update() {
        custPrograssbar.prograssCreate(getContext());
        Broker_profile_update_POJO pojo = new Broker_profile_update_POJO(name1.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()),
                email.getText().toString(), phonenumber.getText().toString(),
                bankname.getText().toString(), accountno.getText().toString(), ifsccode.getText().toString(),
                branch.getText().toString(), SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext())
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
                        Intent it = new Intent(getContext(), Home.class);
                        startActivity(it);


                    } else {

                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_update_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/


    private void bankacc_verification() {
        custPrograssbar.prograssCreate(getContext());
        Bankacc_verification_POJO pojo = new Bankacc_verification_POJO(accountno.getText().toString(),accountholdername
                .getText().toString(), ifsccode.getText().toString(),"y","Individual");
        RestApis restApis = NetworkHandler.instanceMaker6().create(RestApis.class);
        Call<Bankacc_verification_MODEL> call = restApis.bankacc_verification(pojo);
        call.enqueue(new Callback<Bankacc_verification_MODEL>() {
            @Override
            public void onResponse(Call<Bankacc_verification_MODEL> call, Response<Bankacc_verification_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    /* broker_profile_update();*/

                  /*  if (response.body().getCode().equals("200")) {
                        broker_profile_details();
 Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                        x = 1;
                        Intent it = new Intent(getContext(), Home.class);
                        startActivity(it);


                    } else {

                    }


                }*/
                }
            }

            @Override
            public void onFailure(Call<Bankacc_verification_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

            //Do something
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*int index = choose_identity.getSelectedItemPosition();
        SharedPref.saveStringInSharedPref(AppConstants.STATE_VALUE, statelocation[index], getContext());*/
    }

    //Do nothing
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        //Do nothing
    }
}