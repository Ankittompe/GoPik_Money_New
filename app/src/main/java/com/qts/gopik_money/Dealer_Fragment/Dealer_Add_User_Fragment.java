package com.qts.gopik_money.Dealer_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Dealer_Adapter.AddUserAdapter;
import com.qts.gopik_money.Model.Dealer_Add_SubUser_POJO;
import com.qts.gopik_money.Model.Dealer_Subuser_fetch_MODEL;
import com.qts.gopik_money.Model.Dealer_Subuser_insert_MODEL;
import com.qts.gopik_money.Model.FetechSubuserModel;
import com.qts.gopik_money.Pojo.Dealer_Subuser_fetch_POJO;
import com.qts.gopik_money.Pojo.Dealer_Subuser_insert_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dealer_Add_User_Fragment extends Fragment {
    String enterMobile = "Please Enter Valid Mobile Number";
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView adduser ;
    TextView dealercode;
    TextView name;
    EditText username;
    EditText  mobile;
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
    ArrayList<FetechSubuserModel> mSubUserModelArrayList;
    AddUserAdapter addUserAdapter;

    EditText mEdtSubDealerName;
    EditText   mEdtSubDealerPhone;
    Button mBtnSubmitDetails;
    androidx.appcompat.widget.SearchView mSearchVw;
    TextView mTxtBack;

    public Dealer_Add_User_Fragment() {
        // Required empty public constructor
    }

    public static Dealer_Add_User_Fragment newInstance() {
        Dealer_Add_User_Fragment fragment = new Dealer_Add_User_Fragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kirloskar__add__user_, container, false);
        adduser = v.findViewById(R.id.adduser);
        mTxtBack = v.findViewById(R.id.txtBack);
        custPrograssbar = new CustPrograssbar();
        mobile = v.findViewById(R.id.mobile);
        username = v.findViewById(R.id.username);
        dealercode = v.findViewById(R.id.dealercode);
        useradd = v.findViewById(R.id.useraddrecycle);
        name = v.findViewById(R.id.name);
        mSearchVw = v.findViewById(R.id.searchVw);

        mTxtBack.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();
        });

        mSearchVw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (addUserAdapter!=null){
                    addUserAdapter.getFilter().filter(s);
                }
                return false;
            }
        });

        mEdtSubDealerName = v.findViewById(R.id.txtSubDealerName);
        mEdtSubDealerPhone = v.findViewById(R.id.txtSubDealerPhone);
        mBtnSubmitDetails = v.findViewById(R.id.txtSubmitDetails);

        mBtnSubmitDetails.setOnClickListener(new OnAddSubUserClickListener());

        dealercode.setText(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        if (name.getText().toString().equals("NA")) {
            name.setText("Enter Here");
        } else {
            name.setText(SharedPref.getStringFromSharedPref(AppConstants.NAME_SUBUSER, getContext()));
        }

        adduser.setOnClickListener(view -> signupvalidation());

        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getContext());

        Dealer_Subuser_fetch();
        return v;
    }

    private class OnAddSubUserClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!mEdtSubDealerName.getText().toString().isEmpty() && !mEdtSubDealerPhone.getText().toString().isEmpty()) {
                CallAddSubUser(mEdtSubDealerName.getText().toString().trim(), mEdtSubDealerPhone.getText().toString().trim());
            } else {
                Toast.makeText(getActivity(), "Field should not be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CallAddSubUser(String mName, String mPhoneNo) {
        custPrograssbar.prograssCreate(getContext());
        Dealer_Add_SubUser_POJO pojo = new Dealer_Add_SubUser_POJO(mName, mPhoneNo, SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_insert_MODEL> call = restApis.Subdealeradd(pojo);
        call.enqueue(new Callback<Dealer_Subuser_insert_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_insert_MODEL> call, Response<Dealer_Subuser_insert_MODEL> response) {
                custPrograssbar.closePrograssBar();
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        Toast.makeText(getContext(), "Successfully added SubUser", Toast.LENGTH_LONG).show();
                        mEdtSubDealerName.setText("");
                        mEdtSubDealerPhone.setText("");
                        Dealer_Subuser_fetch();
                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_insert_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signupvalidation() {

        if ((username.getText().toString().isEmpty())) {
            username.setError("Please Enter User Name");


        } else if (!(android.util.Patterns.PHONE.matcher(mobile.getText().toString()).matches())) {


            mobile.setError(enterMobile);
        } else if (mobile.getText().toString().length() < 10) {
            mobile.setError(enterMobile);

        } else if (mobile.getText().toString().length() > 10) {
            mobile.setError(enterMobile);


        } else {

            Dealer_Subuser_insert();

        }

    }

    private void Dealer_Subuser_insert() {
        custPrograssbar.prograssCreate(getContext());
        Dealer_Subuser_insert_POJO pojo = new Dealer_Subuser_insert_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()),
                name.getText().toString(), username.getText().toString(), mobile.getText().toString(), "SUBDLR");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_insert_MODEL> call = restApis.Dealer_Subuser_insert(pojo);
        call.enqueue(new Callback<Dealer_Subuser_insert_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_insert_MODEL> call, Response<Dealer_Subuser_insert_MODEL> response) {
                custPrograssbar.closePrograssBar();

                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {

                        Toast.makeText(getContext(), "Successfully added SubUser", Toast.LENGTH_LONG).show();
                        Dealer_Subuser_fetch();
                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_insert_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Dealer_Subuser_fetch() {
        custPrograssbar.prograssCreate(getContext());
        Dealer_Subuser_fetch_POJO pojo = new Dealer_Subuser_fetch_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_Subuser_fetch_MODEL> call = restApis.Dealer_Subuser_fetch(pojo);
        call.enqueue(new Callback<Dealer_Subuser_fetch_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_Subuser_fetch_MODEL> call, Response<Dealer_Subuser_fetch_MODEL> response) {
                custPrograssbar.closePrograssBar();
                mSubUserModelArrayList = new ArrayList<>();
                if (response.body() != null&&response.body().getCode().equals("200")&&response.body().getPayload().size() > 0) {


                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                id.add(response.body().getPayload().get(i).getId());
                                firm_name.add(response.body().getPayload().get(i).getFirm_name());
                                mob.add(response.body().getPayload().get(i).getMob());
                                role.add(response.body().getPayload().get(i).getRole());
                                status.add(response.body().getPayload().get(i).getStatus());
                                sub_user.add(response.body().getPayload().get(i).getSub_user());

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                useradd.setLayoutManager(layoutManager);
                                useradd.setItemAnimator(new DefaultItemAnimator());

                                mSubUserModelArrayList.add(new FetechSubuserModel(
                                        response.body().getPayload().get(i).getId(),
                                        response.body().getPayload().get(i).getCode(),
                                        response.body().getPayload().get(i).getFirm_name(),
                                        response.body().getPayload().get(i).getSub_user(),
                                        response.body().getPayload().get(i).getStatus(),
                                        response.body().getPayload().get(i).getRole(),
                                        response.body().getPayload().get(i).getMob(),
                                        response.body().getPayload().get(i).getMod_timestamp()));
                                addUserAdapter = new AddUserAdapter(getContext(),mSubUserModelArrayList);
                                useradd.setAdapter(addUserAdapter);
                            }


                }
            }

            @Override
            public void onFailure(Call<Dealer_Subuser_fetch_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }


}