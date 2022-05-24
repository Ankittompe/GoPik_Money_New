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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Adapter.Application_status_DEALER_Adapter;
import com.qts.gopik_loan.Model.Fetch_application_list_MODEL;
import com.qts.gopik_loan.Pojo.Fetch_application_list_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Dealer_ApplicationList_Fragment extends Fragment {
    ArrayList<String> firstname = new ArrayList<>();
    ArrayList<String> lastname = new ArrayList<>();
    ArrayList<String> cust_code = new ArrayList<>();
    ArrayList<String> hubbleid = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();
    ArrayList<String> applicationstatus = new ArrayList<>();
    ArrayList<String> loanamount = new ArrayList<>();
    ArrayList<String> submittedon = new ArrayList<>();
    ArrayList<String> updatedon = new ArrayList<>();
    ArrayList<String> appstatus = new ArrayList<>();
    RecyclerView applicationlist;
    ImageView hometoolbar,backarrow;
    Application_status_DEALER_Adapter application_status_dealer_adapter;
    public Dealer_ApplicationList_Fragment() {
        // Required empty public constructor
    }


    public static Dealer_ApplicationList_Fragment newInstance(String param1, String param2) {
        Dealer_ApplicationList_Fragment fragment = new Dealer_ApplicationList_Fragment();
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
        View v= inflater.inflate(R.layout.fragment_kirloskar__application_list_, container, false);

        fetch_application_list();
        applicationlist=  (RecyclerView) v.findViewById(R.id.applicationlist);
/*
        application_status_adapter =new Application_status_Adapter(getContext(),firstname,lastname,
                cust_code, hubbleid,model,applicationstatus,loanamount,submittedon,updatedon,appstatus);*/
        applicationlist.setAdapter(application_status_dealer_adapter);
//        backarrow = (ImageView) v.findViewById(R.id.arrow);
//        hometoolbar = (ImageView)v. findViewById(R.id.hometoolbar);

       /* backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
*/
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());

        return v;
    }

    private void fetch_application_list() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_application_list_POJO pojo = new Fetch_application_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getContext()));

        Call<Fetch_application_list_MODEL> call = restApis.fetch_application_list(pojo);
        call.enqueue(new Callback<Fetch_application_list_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_application_list_MODEL> call, Response<Fetch_application_list_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if(response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");
                        ArrayList<String> firstname = new ArrayList<>();
                        ArrayList<String> lastname = new ArrayList<>();
                        ArrayList<String> cust_code = new ArrayList<>();
                        ArrayList<String> hubbleid = new ArrayList<>();
                        ArrayList<String> model = new ArrayList<>();
                        ArrayList<String> applicationstatus = new ArrayList<>();
                        ArrayList<String> loanamount = new ArrayList<>();
                        ArrayList<String> submittedon = new ArrayList<>();
                        ArrayList<String> updatedon = new ArrayList<>();
                        ArrayList<String> appstatus = new ArrayList<>();
                        ArrayList<String> appstarttimestamp = new ArrayList<>();
                        ArrayList<String> appendtimestp = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {

                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: "+response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                firstname.add(response.body().getPayload().get(i).getCust_first_name());
                                lastname.add(response.body().getPayload().get(i).getCust_last_name());
                                hubbleid.add(response.body().getPayload().get(i).getApi_id());
                                applicationstatus.add(response.body().getPayload().get(i).getApp_status());
                                loanamount.add(response.body().getPayload().get(i).getLoan_amt_app());
                                cust_code.add(response.body().getPayload().get(i).getCust_code());
                                submittedon.add(response.body().getPayload().get(i).getApp_end_tmstmp());
                                model.add(response.body().getPayload().get(i).getModel());
                                updatedon.add(response.body().getPayload().get(i).getUpdated_at());
                                appstatus.add(response.body().getPayload().get(i).getApp_status());
                                appstarttimestamp.add(response.body().getPayload().get(i).getApp_start_tmstmp());
                                appendtimestp.add(response.body().getPayload().get(i).getApp_end_tmstmp());

                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getContext(), LinearLayoutManager.VERTICAL, false
                                    );
                                    applicationlist.setLayoutManager(layoutManager);
                                    applicationlist.setItemAnimator(new DefaultItemAnimator());
                                    application_status_dealer_adapter = new Application_status_DEALER_Adapter(getContext(),firstname,lastname,cust_code,
                                            hubbleid, model,applicationstatus,loanamount,submittedon,updatedon,appstatus,appstarttimestamp,appendtimestp );
                                    applicationlist.setAdapter(application_status_dealer_adapter);

                                }
                            }
                        }
                    }
                    else  {
                        Toast.makeText(getContext(),"Something went wrong!234!",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Fetch_application_list_MODEL> call, Throwable t) {



                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }

        });
    }
}

