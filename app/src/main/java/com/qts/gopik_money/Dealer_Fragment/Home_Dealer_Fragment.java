package com.qts.gopik_money.Dealer_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Adapter.CatagoryNameAdapter;
import com.qts.gopik_money.Adapter.NotificationAdapter;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Dealer_Adapter.BannerAdapter;
import com.qts.gopik_money.Dealer_Adapter.Goat_Top_Three_Application_List_DEALER_Adapter;
import com.qts.gopik_money.Dealer_Adapter.Top_Three_Application_List_DEALER_Adapter;
import com.qts.gopik_money.Model.Banner_list_MODEL;
import com.qts.gopik_money.Model.Category_brand_wise_MODEL;
import com.qts.gopik_money.Model.Fetch_Goat_current_appliation_list_DEALER_MODEL;
import com.qts.gopik_money.Model.Fetch_current_appliation_list_DEALER_MODEL;
import com.qts.gopik_money.Model.GoatListResponseModel;
import com.qts.gopik_money.Model.Notification_MODEL;
import com.qts.gopik_money.Pojo.Banner_POJO;
import com.qts.gopik_money.Pojo.Category_brand_wise_POJO;
import com.qts.gopik_money.Pojo.FetchGoatAppListthree_DEALER_POJO;
import com.qts.gopik_money.Pojo.Fetch_current_appliation_list_DEALER_POJO;
import com.qts.gopik_money.Pojo.Notification_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Dealer_Fragment extends Fragment implements ToolTipsManager.TipListener, View.OnClickListener {
    String networkError = "It seems your Network is unstable . Please Try again!";
    ToolTipsManager toolTipsManager;
    ImageView cross;
    ImageView  img;
    TextView tv_tooltipdata;

    RecyclerView rv;

    RelativeLayout relativelogin;
    RecyclerView my_recycler_view;


    BannerAdapter bannerAdapter;

    TextView done;
    NotificationAdapter notificationAdapter;
    RecyclerView applicationlist;
    RecyclerView  noti;

    Top_Three_Application_List_DEALER_Adapter top_three_application_list_dealer_adapter;

    ArrayList<GoatListResponseModel> mGoatListResponseModelArrayList;
    Goat_Top_Three_Application_List_DEALER_Adapter mGoatTopThreeApplicationListDealerAdapter;

    CatagoryNameAdapter catagoryNameAdapter;
    CustPrograssbar custPrograssbar;

    LinearLayout iv_tooltip;

    public Home_Dealer_Fragment() {

        //Do nothing
    }


    public static Home_Dealer_Fragment newInstance() {
        Home_Dealer_Fragment fragment = new Home_Dealer_Fragment();


        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home__kirloskar_, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rvblog);
        custPrograssbar = new CustPrograssbar();
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
        done.setOnClickListener(v1 -> {
            Intent it = new Intent(getActivity(), MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.APPLICATION_DEALER_FRAG);
            startActivity(it);
        });
        cross.setOnClickListener(v12 -> iv_tooltip.setVisibility(View.GONE));
        iv_tooltip.setOnClickListener(view -> {
            Intent it = new Intent(getActivity(), MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.NOTIFICATION_FARG);
            startActivity(it);
        });

        category_brand_wise();
        fetch_current_appliation_list();
        /*   notification();*/
        banner_list1();

        if (SharedPref.getStringFromSharedPref(AppConstants.NOTIFICATIONPOPUP, getContext()).equals("1")) {
            iv_tooltip.setVisibility(View.VISIBLE);
        } else {
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
                if (response.body() != null&&response.body().getCode().equals("200")) {



                        ArrayList category_image = new ArrayList();
                        ArrayList category_name = new ArrayList();

                        ArrayList SUB_CATEGORY_NAME = new ArrayList();

                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {

                                category_image.add(response.body().getPayload().get(i).getSubcategory_img());
                                category_name.add(response.body().getPayload().get(i).getCategory_name());
                                SUB_CATEGORY_NAME.add(response.body().getPayload().get(i).getSubcategory_name());
                                if (response.body().getPayload().size() - 1 == i) {


                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                                    rv.setLayoutManager(gridLayoutManager);

                                    rv.setItemAnimator(new DefaultItemAnimator());
                                    catagoryNameAdapter = new CatagoryNameAdapter(getContext(), category_image, category_name, SUB_CATEGORY_NAME);
                                    rv.setAdapter(catagoryNameAdapter);

                                }
                            }
                        }


                }else{
                    Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category_brand_wise_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void fetch_current_appliation_list() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Fetch_current_appliation_list_DEALER_POJO pojo = new Fetch_current_appliation_list_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));
        FetchGoatAppListthree_DEALER_POJO mFetchGoatAppListthree_DEALER_POJO = new FetchGoatAppListthree_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext()));

        Call<Fetch_current_appliation_list_DEALER_MODEL> call;

        if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getActivity()).equals("Manikstu")) {
            Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> Goatcall = restApis.FetchGoatAppListthree(mFetchGoatAppListthree_DEALER_POJO);
            Goatcall.enqueue(new Callback<Fetch_Goat_current_appliation_list_DEALER_MODEL>() {
                @Override
                public void onResponse(Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> call, Response<Fetch_Goat_current_appliation_list_DEALER_MODEL> response) {
                    if (response.body() != null&&response.body().getCode().equals("200")) {

                            mGoatListResponseModelArrayList = new ArrayList<>();

                            mGoatListResponseModelArrayList.addAll(response.body().getPayload());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            applicationlist.setLayoutManager(layoutManager);
                            applicationlist.setItemAnimator(new DefaultItemAnimator());
                            mGoatTopThreeApplicationListDealerAdapter = new Goat_Top_Three_Application_List_DEALER_Adapter(getContext(), mGoatListResponseModelArrayList);
                            applicationlist.setAdapter(mGoatTopThreeApplicationListDealerAdapter);
                            mGoatTopThreeApplicationListDealerAdapter.notifyDataSetChanged();

                        } else {
                            custPrograssbar.closePrograssBar();

                        }


                }

                @Override
                public void onFailure(Call<Fetch_Goat_current_appliation_list_DEALER_MODEL> call, Throwable t) {

                    Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            call = restApis.fetch_current_appliation_list(pojo);
            call.enqueue(new Callback<Fetch_current_appliation_list_DEALER_MODEL>() {
                @Override
                public void onResponse(Call<Fetch_current_appliation_list_DEALER_MODEL> call, Response<Fetch_current_appliation_list_DEALER_MODEL> response) {
                    if (response.body() != null&&response.body().getCode().equals("200")) {



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

                @Override
                public void onFailure(Call<Fetch_current_appliation_list_DEALER_MODEL> call, Throwable t) {


                    Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    private void banner_list1() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Banner_POJO pojo = new Banner_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, getContext()));
        Call<Banner_list_MODEL> call = restApis.banner_list1(pojo);
        call.enqueue(new Callback<Banner_list_MODEL>() {
            @Override
            public void onResponse(Call<Banner_list_MODEL> call, Response<Banner_list_MODEL> response) {
                if (response.body() != null&&response.body().getCode().equals("200")&&response.body().getPayload().size() > 0) {

                            ArrayList<String> id = new ArrayList<>();
                            ArrayList<String> banners = new ArrayList<>();
                            ArrayList<String> namebanner = new ArrayList<>();
                            for (int i = 0; i < response.body().getPayload().size(); i++) {

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
                    }



            @Override
            public void onFailure(Call<Banner_list_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
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
                if (response.body() != null&&response.body().getCode().equals("200")) {

                        if (response.body().getPayload().size() > 0) {
                            ArrayList<String> message = new ArrayList<>();
                            ArrayList<String> timestamp = new ArrayList<>();
                            ArrayList<String> logo = new ArrayList<>();
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
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


                }
            }

            @Override
            public void onFailure(Call<Notification_MODEL> call, Throwable t) {


                Toast.makeText(getContext(),networkError , Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onClick(View view) {

        //Do nothing
    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

        //Do nothing
    }


}