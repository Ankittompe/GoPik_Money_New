package com.qts.gopik_money.Dealer_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.Application_status_DEALER_Adapter;
import com.qts.gopik_money.Dealer_Adapter.Goat_Application_status_DEALER_Adapter;
import com.qts.gopik_money.Model.Fetch_Goat_current_appliation_list_DEALER_MODEL;
import com.qts.gopik_money.Model.Fetch_application_list_MODEL;
import com.qts.gopik_money.Model.GoatListResponseModel;
import com.qts.gopik_money.Pojo.FetchGoatAppListthree_DEALER_POJO;
import com.qts.gopik_money.Pojo.Fetch_application_list_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dealer_ApplicationList_Fragment extends Fragment {
    String networkError = "It seems your Network is unstable . Please Try again!";
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

    Application_status_DEALER_Adapter application_status_dealer_adapter;

    ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList;
    Goat_Application_status_DEALER_Adapter mGoat_application_status_dealer_adapter;


    public Dealer_ApplicationList_Fragment() {
        // Required empty public constructor
    }


    public static Dealer_ApplicationList_Fragment newInstance() {
        Dealer_ApplicationList_Fragment fragment = new Dealer_ApplicationList_Fragment();


        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kirloskar__application_list_, container, false);

        if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getActivity()).equals("Manikstu")) {
            fetch_current_goat_appliation_list();
        } else {
            fetch_application_list();
        }


        applicationlist = (RecyclerView) v.findViewById(R.id.applicationlist);

        applicationlist.setAdapter(application_status_dealer_adapter);


        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getContext());

        return v;
    }


    private void fetch_current_goat_appliation_list() {
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        FetchGoatAppListthree_DEALER_POJO mFetchGoatAppListthree_DEALER_POJO = new FetchGoatAppListthree_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> Goatcall = restApis.FetchGoatApplicationList(mFetchGoatAppListthree_DEALER_POJO);
        Goatcall.enqueue(new Callback<Fetch_Goat_current_appliation_list_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> call, Response<Fetch_Goat_current_appliation_list_DEALER_MODEL> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {

                        mGoatListResponseModelArrayList = new ArrayList<>();
                        mGoatListResponseModelArrayList.addAll(response.body().getPayload());

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        applicationlist.setLayoutManager(layoutManager);
                        applicationlist.setItemAnimator(new DefaultItemAnimator());
                        mGoat_application_status_dealer_adapter = new Goat_Application_status_DEALER_Adapter(getContext(), mGoatListResponseModelArrayList);
                        applicationlist.setAdapter(mGoat_application_status_dealer_adapter);

                }
            }

            @Override
            public void onFailure(Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> call, Throwable t) {
                //Do nothing
            }
        });
    }


    private void fetch_application_list() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_application_list_POJO pojo = new Fetch_application_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));

        Call<Fetch_application_list_MODEL> call;

        call = restApis.fetch_application_list(pojo);
        call.enqueue(new Callback<Fetch_application_list_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_application_list_MODEL> call, Response<Fetch_application_list_MODEL> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {



                        ArrayList<String> appstarttimestamp = new ArrayList<>();
                        ArrayList<String> appendtimestp = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
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
                                    application_status_dealer_adapter = new Application_status_DEALER_Adapter(getContext(), firstname, lastname, cust_code,
                                            hubbleid, model, applicationstatus, loanamount, submittedon, updatedon, appstatus, appstarttimestamp, appendtimestp);
                                    applicationlist.setAdapter(application_status_dealer_adapter);

                                }

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Fetch_application_list_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }
}

