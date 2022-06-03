package com.qts.gopik_loan.Dealer_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Edit_Profile;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.Edit_delaer_Profile;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_update_MODEL;
import com.qts.gopik_loan.Model.ProfileDetailsMODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Model.Profile_Update_DEALER_MODEL;
import com.qts.gopik_loan.Pojo.Broker_profile_update_POJO;
import com.qts.gopik_loan.Pojo.ProfileDetailsPOJO;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Profile_Update_DEALER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Dealer_Profile_Fragment extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {

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
    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};

    public Dealer_Profile_Fragment() {

    }


    public static Dealer_Profile_Fragment newInstance(String param1, String param2) {
        Dealer_Profile_Fragment fragment = new Dealer_Profile_Fragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dealer__profile_, container, false);
        branch = (TextView) v.findViewById(R.id.branch);
        btn_countinue = (TextView) v.findViewById(R.id.btn_countinue);
        address = (TextView) v.findViewById(R.id.address);
        bankname = (TextView) v.findViewById(R.id.bankname);
        ifsccode = (EditText) v.findViewById(R.id.ifsccode);
        accountno = (EditText) v.findViewById(R.id.accountno);
        email = (EditText) v.findViewById(R.id.email);
        name1 = (EditText) v.findViewById(R.id.name1);
        state = (EditText) v.findViewById(R.id.state);
        gsst= (TextView) v.findViewById(R.id.gs);
        gst = (EditText) v.findViewById(R.id.gst);
        textviewprofile= (TextView) v.findViewById(R.id.textviewprofile);
        validaccountno = (TextView) v.findViewById(R.id.validaccountno);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());
        reacc = (LinearLayout) v.findViewById(R.id.reacc);
        phonenumber = (TextView) v.findViewById(R.id.phonenumber);
        layout = (LinearLayout) v.findViewById(R.id.layout);
        reaccountno = (EditText) v.findViewById(R.id.reaccountno);
        custPrograssbar = new CustPrograssbar();
        reaccountno.addTextChangedListener(this);
        ifsccode.addTextChangedListener(this);
        search = (ImageView) v.findViewById(R.id.search);
        choose_identity = (Spinner) v.findViewById(R.id.choose_identity);
        statetextview = (TextView) v.findViewById(R.id.statetextview);
        statespinner = (LinearLayout) v.findViewById(R.id.statespinner);
        st = (LinearLayout) v.findViewById(R.id.st);
        tt = (TextView) v.findViewById(R.id.tt);
        btn_edit= (TextView)v. findViewById(R.id.btn_edit);
        validaccountno = (TextView) v.findViewById(R.id.validaccountno);
        visible = (ImageView) v.findViewById(R.id.eye);
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getContext()));
        statetextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                statespinner.setVisibility(View.VISIBLE);

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
        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statespinner.setVisibility(View.GONE);

                profile_update();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Edit_delaer_Profile.class);
                startActivity(it);

            }
        });

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
                /*       SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER_ITEM,statelocation,getContext());*/
                Log.e("hhghghhuu", "bfvn" + SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (x == 1) {
            statespinner.setVisibility(View.GONE);
        }
        profile_details();

        return v;
    }

    private void profile_details() {
        custPrograssbar.prograssCreate(getContext());
        ProfileDetails_DEALER_POJO pojo = new ProfileDetails_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getActivity()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getActivity()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<ProfileDetails_DEALER_MODEL> call = restApis.profile_details(pojo);
        call.enqueue(new Callback<ProfileDetails_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<ProfileDetails_DEALER_MODEL> call, Response<ProfileDetails_DEALER_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getGst_no().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            name1.setText(response.body().getPayload().getProfile().get(0).getName());
                            email.setText(response.body().getPayload().getProfile().get(0).getEmail());
                            Log.e("hhghghhuu", "bfvn");

                            address.setText(response.body().getPayload().getProfile().get(0).getAddress());
                            bankname.setText(response.body().getPayload().getProfile().get(0).getBank_name());
                            accountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());
                            ifsccode.setText(response.body().getPayload().getProfile().get(0).getIfsc());
                            branch.setText(response.body().getPayload().getProfile().get(0).getBranch());
                            gst.setText(response.body().getPayload().getProfile().get(0).getGst_no());
                            reacc.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            statetextview.setText(response.body().getPayload().getProfile().get(0).getUserstate());

                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext());
                            /*      int state_position=statelocation.get*/
                            SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER, phonenumber.getText().toString(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());

                            SharedPref.saveStringInSharedPref(AppConstants.NAME_SUBUSER, response.body().getPayload().getProfile().get(0).getName(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DEALER_EMAIL, response.body().getPayload().getProfile().get(0).getEmail(),getContext());
                            search.setVisibility(View.GONE);
                            name1.setFocusable(false);
                            address.setFocusable(false);
                            bankname.setFocusable(false);
                            accountno.setFocusable(false);
                            ifsccode.setFocusable(false);
                            email.setFocusable(false);
                            branch.setFocusable(false);
                            statetextview.setFocusable(false);
                            visible.setVisibility(View.GONE);
                            accountno.setInputType(InputType.TYPE_CLASS_TEXT );
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            textviewprofile.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }


            @Override
            public void onFailure(Call<ProfileDetails_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
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
        Log.e("hhghghhuu", "bfvn" + accountno.getText().toString().length());

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
            address.setText("");
            branch.setText("");
            bankname.setText("");
        }
    }
    private void account(String acc) {
        custPrograssbar.prograssCreate(getContext());
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


                } else {
                    custPrograssbar.closePrograssBar();

                    ifsccode.setError("Please enter valid IFSC code");
                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<ACCOUNT_NO_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void profile_update() {
        custPrograssbar.prograssCreate(getContext());

        Profile_Update_DEALER_POJO pojo = new Profile_Update_DEALER_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()),
                        name1.getText().toString(),email.getText().toString(),
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
                        Intent it = new Intent(getContext(), MainActivity.class);
                        startActivity(it);


                    } else {
                        Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Profile_Update_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
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

