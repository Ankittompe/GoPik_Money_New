package com.qts.gopik_loan.Dealer_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Adapter.AddUserAdapter;
import com.qts.gopik_loan.Model.Dealer_Subuser_fetch_MODEL;
import com.qts.gopik_loan.Model.Dealer_Subuser_insert_MODEL;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_fetch_POJO;
import com.qts.gopik_loan.Pojo.Dealer_Subuser_insert_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Dealer_Add_User_Fragment extends Fragment {

    TextView adduser,dealercode,name;
    EditText username,mobile;
    CustPrograssbar custPrograssbar;
    RecyclerView useradd;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> firm_name = new ArrayList<>();
    ArrayList<String> sub_user = new ArrayList<>();
    ArrayList<String> mob = new ArrayList<>();
    ArrayList<String> role = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> mod_timestamp = new ArrayList<>();
    AddUserAdapter addUserAdapter;
    public Dealer_Add_User_Fragment() {
        // Required empty public constructor
    }


    public static Dealer_Add_User_Fragment newInstance(String param1, String param2) {
        Dealer_Add_User_Fragment fragment = new Dealer_Add_User_Fragment();
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

        View v= inflater.inflate(R.layout.fragment_kirloskar__add__user_, container, false);
        adduser=(TextView)v.findViewById(R.id.adduser);
        custPrograssbar = new CustPrograssbar();
        mobile=(EditText)v.findViewById(R.id.mobile);
        username=(EditText)v.findViewById(R.id.username);
        dealercode=(TextView)v.findViewById(R.id.dealercode);
        useradd=(RecyclerView)v.findViewById(R.id.useraddrecycle);
        name=(TextView)v.findViewById(R.id.name);
        dealercode.setText(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()));
        if(name.getText().toString().equals("NA")){
            name.setText("Enter Here");
        }
        else{
            name.setText(SharedPref.getStringFromSharedPref(AppConstants.NAME_SUBUSER,getContext()));
        }

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupvalidation();
            }
        });

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());

        Dealer_Subuser_fetch();
        return  v;
    }
    private void signupvalidation() {

        if ((username.getText().toString().isEmpty()) ) {
            username.setError("Please Enter User Name");


        }
        else if (!(android.util.Patterns.PHONE.matcher(mobile.getText().toString()).matches())) {


            mobile.setError("Please Enter Valid Mobile Number");
        }
        else if (mobile.getText().toString().length() < 10) {
            mobile.setError("Please Enter Valid Mobile Number");

        }
        else if ( mobile.getText().toString().length() > 10 ) {
            mobile.setError("Please Enter Valid Mobile Number");


        }
        else
        {

            Dealer_Subuser_insert();

        }

    }
    private void Dealer_Subuser_insert() {
        custPrograssbar.prograssCreate(getContext());
        Dealer_Subuser_insert_POJO pojo = new Dealer_Subuser_insert_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()),
                name.getText().toString(),username.getText().toString(),mobile.getText().toString(),"SUBDLR");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_insert_MODEL> call = restApis.Dealer_Subuser_insert(pojo);
        call.enqueue(new Callback<Dealer_Subuser_insert_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_insert_MODEL> call, Response<Dealer_Subuser_insert_MODEL> response) {
                custPrograssbar.closePrograssBar();

                if (response.body() != null) {


                    if(response.body().getCode().equals("200")){

                        Toast.makeText(getContext(),"Successfully added SubUser",Toast.LENGTH_LONG).show();
                        Dealer_Subuser_fetch();
                    }
                    else  {
                        Toast.makeText(getContext(),"Something went wrong!!",Toast.LENGTH_LONG).show();
                    }




                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_insert_MODEL> call, Throwable t) {



                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Dealer_Subuser_fetch() {
        custPrograssbar.prograssCreate(getContext());
        Dealer_Subuser_fetch_POJO pojo = new Dealer_Subuser_fetch_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_fetch_MODEL> call = restApis.Dealer_Subuser_fetch(pojo);
        call.enqueue(new Callback<Dealer_Subuser_fetch_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_fetch_MODEL> call, Response<Dealer_Subuser_fetch_MODEL> response) {
                custPrograssbar.closePrograssBar();

                if (response.body() != null) {
                    ArrayList<String> id = new ArrayList<>();
                    ArrayList<String> code = new ArrayList<>();
                    ArrayList<String> firm_name = new ArrayList<>();
                    ArrayList<String> sub_user = new ArrayList<>();
                    ArrayList<String> mob = new ArrayList<>();
                    ArrayList<String> role = new ArrayList<>();
                    ArrayList<String> status = new ArrayList<>();
                    ArrayList<String> mod_timestamp = new ArrayList<>();


                    if(response.body().getCode().equals("200")) {
                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                id.add(response.body().getPayload().get(i).getId());
                                firm_name.add(response.body().getPayload().get(i).getFirm_name());
                                mob.add(response.body().getPayload().get(i).getMob());
                                role.add(response.body().getPayload().get(i).getRole());
                                status.add(response.body().getPayload().get(i).getStatus());
                                sub_user.add(response.body().getPayload().get(i).getSub_user());

                                if (response.body().getPayload().size() - 1 == i) {


                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getContext(), LinearLayoutManager.VERTICAL, false
                                    );
                                    useradd.setLayoutManager(layoutManager);
                                    useradd.setItemAnimator(new DefaultItemAnimator());
                                    addUserAdapter = new AddUserAdapter(getContext(), id, code, firm_name, sub_user, mob,role,status,mod_timestamp);
                                    useradd.setAdapter(addUserAdapter);

                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                        }


                    }

                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_fetch_MODEL> call, Throwable t) {



                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });

    }




}