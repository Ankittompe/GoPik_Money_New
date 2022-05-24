package com.qts.gopik_loan.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Application_Details;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Adapter.BrandCategory_Adapter;
import com.qts.gopik_loan.Adapter.CatagoryNameAdapter;
import com.qts.gopik_loan.Adapter.NotificationAdapter;
import com.qts.gopik_loan.Adapter.Top_Three_Adapter;
import com.qts.gopik_loan.Model.Fetch_current_loanappliation_list_MODEL;
import com.qts.gopik_loan.Model.Get_ML_SubCat_List_MODEL;
import com.qts.gopik_loan.Model.Notification_MODEL;
import com.qts.gopik_loan.Pojo.Fetch_current_loanappliation_list_POJO;
import com.qts.gopik_loan.Pojo.Get_ML_SubCat_List_POJO;
import com.qts.gopik_loan.Pojo.Notification_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {
    SliderLayout sl;
    TextView done;
    RecyclerView rv;
    LinearLayout personallayout, mortgagelayout;
    LinearLayout applicationlayout;
    RecyclerView applicationlist;
    CustPrograssbar custPrograssbar;

    ArrayList<String> CATEGORY_TYPE = new ArrayList<>();
    CatagoryNameAdapter catagoryNameAdapter;
    ArrayList<String> catagoryimage = new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> CATEGORY_CODE = new ArrayList<>();

    NotificationAdapter notificationAdapter;
    RecyclerView noti, brandcategory;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();


    ArrayList<String> cat_name = new ArrayList<>();
    ArrayList<String> img_url = new ArrayList<>();

    ArrayList<String> logo = new ArrayList<>();

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> user_code = new ArrayList<>();
    ArrayList<String> customer_code = new ArrayList<>();
    ArrayList<String> customer_name = new ArrayList<>();
    ArrayList<String> customer_mobile = new ArrayList<>();
    ArrayList<String> customer_email = new ArrayList<>();
    ArrayList<String> customer_address = new ArrayList<>();
    ArrayList<String> customer_dob = new ArrayList<>();
    ArrayList<String> loan_type = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> date_time = new ArrayList<>();
    ArrayList<String> application_status = new ArrayList<>();
    ArrayList<String> cust_tc_response = new ArrayList<>();
    ImageView cross;
    Integer counter = 0;
    TextView tv_tooltipdata;
    LinearLayout iv_tooltip;
    Top_Three_Adapter top_three_adapter;
    BrandCategory_Adapter brandCategory_adapter;

    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home2, container, false);
        iv_tooltip = (LinearLayout) v.findViewById(R.id.iv_tooltip);
        tv_tooltipdata = (TextView) v.findViewById(R.id.tv_tooltipdata);
        cross = (ImageView) v.findViewById(R.id.cross);
        done = (TextView) v.findViewById(R.id.done);
        applicationlayout = (LinearLayout) v.findViewById(R.id.applicationlayout1);
        applicationlist = (RecyclerView) v.findViewById(R.id.recyleview);
        custPrograssbar = new CustPrograssbar();
        noti = (RecyclerView) v.findViewById(R.id.notify);
        brandcategory = (RecyclerView) v.findViewById(R.id.brandcategory);
      /*  mortgagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE, "Construction", getContext());
                Intent it = new Intent(getContext(), Houseconstruction_Activity.class);
                startActivity(it);
            }
        });*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Application_Details.class);
                startActivity(it);
            }
        });
      /*  personallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.HOMELOANTYPE, "Home", getContext());

                Intent it = new Intent(getContext(), Mortgage_Loan_Application.class);
                startActivity(it);
            }
        });*/
        if( SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATIONPOPUP,getContext()).equals("1")){
          iv_tooltip.setVisibility(View.VISIBLE);
        }
        else{
            iv_tooltip.setVisibility(View.GONE);
        }
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if (counter < message.size()) {
                    String msg = message.get(counter);
                    tv_tooltipdata.setText(msg);
                    counter++;
                } else {
                    iv_tooltip.setVisibility(View.GONE);
                }*/
                iv_tooltip.setVisibility(View.GONE);
              /*  Intent it = new Intent(getActivity(), Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.NOTIFICATION);
                startActivity(it);*/
            }
        });
        iv_tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.NOTIFICATION);
                startActivity(it);
            }
        });

        fetch_current_loanappliation_list();
      /*  notification();*/
        Get_ML_SubCat_List();



        return v;
    }

    private void fetch_current_loanappliation_list() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_current_loanappliation_list_POJO pojo = new Fetch_current_loanappliation_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));

        Call<Fetch_current_loanappliation_list_MODEL> call = restApis.fetch_current_loanappliation_list(pojo);
        call.enqueue(new Callback<Fetch_current_loanappliation_list_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_current_loanappliation_list_MODEL> call, Response<Fetch_current_loanappliation_list_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");


                        if (response.body().getPayload().size() > 0) {

                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                Log.e(TAG, "City Payload Size: " + response.body().getPayload().get(i).getCustomer_name());

                                id.add(response.body().getPayload().get(i).getId());
                                user_code.add(response.body().getPayload().get(i).getUser_code());
                                application_status.add(response.body().getPayload().get(i).getApplication_status());
                                customer_name.add(response.body().getPayload().get(i).getCustomer_name());
                                customer_code.add(response.body().getPayload().get(i).getCustomer_code());


                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.VERTICAL, false
                                    );
                                    applicationlist.setLayoutManager(layoutManager);
                                    applicationlist.setItemAnimator(new DefaultItemAnimator());
                                    Log.e("name", "name" + customer_name);
                                    top_three_adapter = new Top_Three_Adapter(getContext(), id, user_code, customer_code,
                                            customer_name, customer_mobile, customer_email, customer_address, customer_dob, loan_type, state, date_time,
                                            application_status, cust_tc_response);
                                    applicationlist.setAdapter(top_three_adapter);

                                    Log.e("Body", "body3");
                                }
                            }
                        }
                        else if(response.body().getPayload().size()==2){
                            Toast.makeText(getContext(), "No application list available for now", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Fetch_current_loanappliation_list_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void notification() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Notification_POJO pojo = new Notification_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, getContext()), "ANDHRA PRADESH");
        Call<Notification_MODEL> call = restApis.notification(pojo);
        call.enqueue(new Callback<Notification_MODEL>() {
            @Override
            public void onResponse(Call<Notification_MODEL> call, Response<Notification_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                logo.add(response.body().getPayload().get(i).getLogo());
                                timestamp.add(response.body().getPayload().get(i).getTimestamp());
                                message.add(response.body().getPayload().get(i).getMessage());
                              /*  if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.VERTICAL, false
                                    );
                                    noti.setLayoutManager(layoutManager);
                                    noti.setItemAnimator(new DefaultItemAnimator());
                                    notificationAdapter = new NotificationAdapter(getContext(),message , timestamp, logo );
                                    noti.setAdapter(notificationAdapter);

                                }*/
                            }
                        }
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<Notification_MODEL> call, Throwable t) {


            }

        });
    }

    private void Get_ML_SubCat_List() {
        custPrograssbar.prograssCreate(getContext());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Get_ML_SubCat_List_POJO pojo = new Get_ML_SubCat_List_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, getContext()));

        Call<Get_ML_SubCat_List_MODEL> call = restApis.Get_ML_SubCat_List(pojo);
        call.enqueue(new Callback<Get_ML_SubCat_List_MODEL>() {
            @Override
            public void onResponse(Call<Get_ML_SubCat_List_MODEL> call, Response<Get_ML_SubCat_List_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");

                        Log.e("Body", "body3777777"+response.body().getPayload().size());
                        if (response.body().getPayload().size() > 0) {

                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                cat_name.add(response.body().getPayload().get(i).getSubcategory_name());
                                img_url.add(response.body().getPayload().get(i).getSubcategory_img());


                                if (response.body().getPayload().size() - 1 == i) {
                                    Log.e("Payloadsunil",""+response.body().getPayload().size());
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.HORIZONTAL, false
                                    );

                                    brandcategory.setLayoutManager(layoutManager);
                                    brandcategory.setItemAnimator(new DefaultItemAnimator());
                                    Log.e("name", "name" + customer_name);
                                    brandCategory_adapter = new BrandCategory_Adapter(getContext(), cat_name,img_url);
                                    brandcategory.setAdapter(brandCategory_adapter);

                                    Log.e("Body", "body3");
                                }

                            }
                        }

                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Get_ML_SubCat_List_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();

                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }



}