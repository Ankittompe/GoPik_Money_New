package com.qts.gopik_loan.Fragment;

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

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Edit_Profile;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.ACCOUNT_NO_MODEL;
import com.qts.gopik_loan.Model.Bankacc_verification_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.Broker_profile_update_MODEL;
import com.qts.gopik_loan.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_loan.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_loan.Pojo.Broker_profile_update_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {
    EditText ifsccode, accountno, email, name1, reaccountno, state,accountholdername;
    String acc;
    Integer x = 0;
    LinearLayout layout, reacc, st;
    ImageView search;
    TextView bankname, tt, phonenumber, address, branch, btn_countinue, statetextview, validaccountno, btn_edit, textviewprofile;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView visibleoff, visible, eye2;
    Boolean flag = false;
    private static int TIME_OUT = 3000;
    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        statetextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                statespinner.setVisibility(View.VISIBLE);

                return true;
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Edit_Profile.class);
                startActivity(it);

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
              /*  broker_profile_update();*/
         signupvalidation();

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
      /*  broker_profile_details();*/
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
        Log.e("ankit", "akit" + flag);
        return flag;


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
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
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

                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
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
}