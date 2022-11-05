package com.qts.gopik_money.Dealer_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Adapter.NotificationFragmentAdapter;
import com.qts.gopik_money.Model.Notification_MODEL;
import com.qts.gopik_money.Pojo.Notification_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dealer_Notificarion_Fragment extends Fragment {
    TextView  tv;
    CustPrograssbar custPrograssbar;


    NotificationFragmentAdapter notificationFragmentAdapter;
    RecyclerView noti;

    public Dealer_Notificarion_Fragment() {

        //Do nothing
    }


    public static Dealer_Notificarion_Fragment newInstance() {
        Dealer_Notificarion_Fragment fragment = new Dealer_Notificarion_Fragment();


        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kirloskar__notificarion_, container, false);
        custPrograssbar = new CustPrograssbar();
        tv = (TextView) v.findViewById(R.id.tv);
        noti = (RecyclerView) v.findViewById(R.id.notify);
        notification();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getContext());
        return v;
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
                                    notificationFragmentAdapter = new NotificationFragmentAdapter(getContext(), message, timestamp, logo);
                                    noti.setAdapter(notificationFragmentAdapter);

                                }


                            }
                        }
                    }
                    else {

                        tv.setVisibility(View.VISIBLE);



                }
            }

            @Override
            public void onFailure(Call<Notification_MODEL> call, Throwable t) {
                //Do Nothing

            }

        });
    }

}