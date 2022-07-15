package com.qts.gopik_loan.Dealer_Fragment;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Application_Details;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Adapter.CatagoryNameAdapter;
import com.qts.gopik_loan.Adapter.NotificationAdapter;
import com.qts.gopik_loan.Adapter.Top_Three_Application_List_Adapter;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Adapter.BannerAdapter;
import com.qts.gopik_loan.Dealer_Adapter.Top_Three_Application_List_DEALER_Adapter;
import com.qts.gopik_loan.Model.Banner_list_MODEL;
import com.qts.gopik_loan.Model.Category_brand_wise_MODEL;
import com.qts.gopik_loan.Model.Fetch_current_appliation_list_DEALER_MODEL;
import com.qts.gopik_loan.Model.Fetch_current_loanappliation_list_MODEL;
import com.qts.gopik_loan.Model.Notification_MODEL;
import com.qts.gopik_loan.Pojo.Banner_POJO;
import com.qts.gopik_loan.Pojo.Category_brand_wise_POJO;
import com.qts.gopik_loan.Pojo.Fetch_current_appliation_list_DEALER_POJO;
import com.qts.gopik_loan.Pojo.Fetch_current_loanappliation_list_POJO;
import com.qts.gopik_loan.Pojo.Notification_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;
import com.smarteist.autoimageslider.SliderLayout;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Home_Dealer_Fragment extends Fragment implements ToolTipsManager.TipListener, View.OnClickListener {
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> logo = new ArrayList<>();
    ToolTipsManager toolTipsManager;
    ImageView cross, img;
    TextView tv_tooltipdata;
    LinearLayout tooltip;
    TextView textview;
    RecyclerView rv;
    RecyclerView rvblog1;
    RelativeLayout relativelogin;
    RecyclerView my_recycler_view;
    SliderLayout sl;
    ArrayList<String> banners = new ArrayList<>();
    ArrayList<String> namebanner = new ArrayList<>();
    BannerAdapter bannerAdapter;
    ArrayList<String> catagoryimage = new ArrayList<>();
    ArrayList<String> catagoryname = new ArrayList<>();
    ArrayList<String> CATEGORY_CODE = new ArrayList<>();
    TextView done;
    NotificationAdapter notificationAdapter;
    RecyclerView applicationlist, noti;
    LinearLayout applicationlayout;
    Top_Three_Application_List_DEALER_Adapter top_three_application_list_dealer_adapter;
    ArrayList<String> CATEGORY_TYPE = new ArrayList<>();
    CatagoryNameAdapter catagoryNameAdapter;
    CustPrograssbar custPrograssbar;

    LinearLayout iv_tooltip;

    public Home_Dealer_Fragment() {

    }


    public static Home_Dealer_Fragment newInstance(String param1, String param2) {
        Home_Dealer_Fragment fragment = new Home_Dealer_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_home__kirloskar_, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rvblog);

        done = (TextView) v.findViewById(R.id.done);
        applicationlist = (RecyclerView) v.findViewById(R.id.applicationlist);
        noti = (RecyclerView) v.findViewById(R.id.notify);
        relativelogin = (RelativeLayout) v.findViewById(R.id.relativelogin);
        my_recycler_view = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        toolTipsManager = new ToolTipsManager(this);
        cross = (ImageView) v.findViewById(R.id.cross);
        iv_tooltip = (LinearLayout) v.findViewById(R.id.iv_tooltip);
        img = (ImageView) v.findViewById(R.id.i);
        tv_tooltipdata = (TextView) v.findViewById(R.id.tv_tooltipdata);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.APPLICATION_DEALER_FRAG);
                startActivity(it);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_tooltip.setVisibility(View.GONE);

            }
        });
        iv_tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.NOTIFICATION_FARG);
                startActivity(it);
            }
        });

        category_brand_wise();
        fetch_current_appliation_list();
        /*   notification();*/
        banner_list1();

        if( SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATIONPOPUP,getContext()).equals("1")){
            iv_tooltip.setVisibility(View.VISIBLE);
        }
        else{
            iv_tooltip.setVisibility(View.GONE);
        }
        return v;
    }

    private void category_brand_wise() {
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Category_brand_wise_POJO pojo = new Category_brand_wise_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, getContext()));
        Call<Category_brand_wise_MODEL> call = restApis.category_brand_wise(pojo);
        call.enqueue(new Callback<Category_brand_wise_MODEL>() {
            @Override
            public void onResponse(Call<Category_brand_wise_MODEL> call, Response<Category_brand_wise_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        ArrayList category_image = new ArrayList();
                        ArrayList category_name = new ArrayList();
                        ArrayList CATEGORY_CODE = new ArrayList();
                        ArrayList CATEGORY_TYPE = new ArrayList();
                        ArrayList SUB_CATEGORY_NAME = new ArrayList();

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {

                                category_image.add(response.body().getPayload().get(i).getSubcategory_img());
                                category_name.add(response.body().getPayload().get(i).getCategory_name());
                                SUB_CATEGORY_NAME.add(response.body().getPayload().get(i).getSubcategory_name());
                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.HORIZONTAL, false
                                    );
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                                    rv.setLayoutManager(gridLayoutManager);

                                    rv.setItemAnimator(new DefaultItemAnimator());
                                    catagoryNameAdapter = new CatagoryNameAdapter(getContext(), category_image, category_name, SUB_CATEGORY_NAME);
                                    rv.setAdapter(catagoryNameAdapter);

                                }
                            }
                        }
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<Category_brand_wise_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }


    private void fetch_current_appliation_list() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_current_appliation_list_DEALER_POJO pojo = new Fetch_current_appliation_list_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));

        Call<Fetch_current_appliation_list_DEALER_MODEL> call = restApis.fetch_current_appliation_list(pojo);
        call.enqueue(new Callback<Fetch_current_appliation_list_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Fetch_current_appliation_list_DEALER_MODEL> call, Response<Fetch_current_appliation_list_DEALER_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode().equals("200")) {

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

                        if (response.body().getPayload().size() > 0) {

                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
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

                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.VERTICAL, false
                                    );
                                    applicationlist.setLayoutManager(layoutManager);
                                    applicationlist.setItemAnimator(new DefaultItemAnimator());
                                    top_three_application_list_dealer_adapter = new Top_Three_Application_List_DEALER_Adapter(getContext(), firstname, lastname, cust_code,
                                            hubbleid, model, applicationstatus, loanamount, submittedon, updatedon, appstatus);
                                    applicationlist.setAdapter(top_three_application_list_dealer_adapter);

                                }
                            }
                        }
                    } else {
                        custPrograssbar.closePrograssBar();

                    }

                }
            }

            @Override
            public void onFailure(Call<Fetch_current_appliation_list_DEALER_MODEL> call, Throwable t) {


            }

        });
    }

    private void banner_list1() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Banner_POJO pojo = new Banner_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, getContext()));
        Call<Banner_list_MODEL> call = restApis.banner_list1(pojo);
        call.enqueue(new Callback<Banner_list_MODEL>() {
            @Override
            public void onResponse(Call<Banner_list_MODEL> call, Response<Banner_list_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            ArrayList<String> id = new ArrayList<>();
                            ArrayList<String> banners = new ArrayList<>();
                            ArrayList<String> namebanner = new ArrayList<>();
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                id.add(response.body().getPayload().get(i).getId());
                                banners.add(response.body().getPayload().get(i).getBanner_image());
                                namebanner.add(response.body().getPayload().get(i).getName());
                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManagerblog = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.HORIZONTAL, false
                                    );


                                    my_recycler_view.setLayoutManager(layoutManagerblog);
                                    my_recycler_view.setItemAnimator(new DefaultItemAnimator());
                                    bannerAdapter = new BannerAdapter(getActivity(), id, banners, namebanner);
                                    final int time = 3000;
                                    final Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            if (layoutManagerblog.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() - 1)) {

                                                layoutManagerblog.smoothScrollToPosition(my_recycler_view, new RecyclerView.State(), layoutManagerblog.findLastCompletelyVisibleItemPosition() + 1);
                                            } else if (layoutManagerblog.findLastCompletelyVisibleItemPosition() == (bannerAdapter.getItemCount() - 1)) {

                                                layoutManagerblog.smoothScrollToPosition(my_recycler_view, new RecyclerView.State(), 0);
                                            }
                                        }
                                    }, 0, time);

                                    my_recycler_view.setAdapter(bannerAdapter);

                                }
                            }
                        }
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<Banner_list_MODEL> call, Throwable t) {


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
                            ArrayList<String> message = new ArrayList<>();
                            ArrayList<String> timestamp = new ArrayList<>();
                            ArrayList<String> logo = new ArrayList<>();
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                logo.add(response.body().getPayload().get(i).getLogo());
                                timestamp.add(response.body().getPayload().get(i).getTimestamp());
                                message.add(response.body().getPayload().get(i).getMessage());
                                if (response.body().getPayload().size() - 1 == i) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            getActivity(), LinearLayoutManager.VERTICAL, false
                                    );
                                    noti.setLayoutManager(layoutManager);
                                    noti.setItemAnimator(new DefaultItemAnimator());
                                    notificationAdapter = new NotificationAdapter(getContext(), message, timestamp, logo);
                                    noti.setAdapter(notificationAdapter);

                                }
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


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

    }


}