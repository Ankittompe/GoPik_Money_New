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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.LogIn;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Adapter.NotificationFragmentAdapter;
import com.qts.gopik_loan.Model.Notification_MODEL;
import com.qts.gopik_loan.Pojo.Notification_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Dealer_Notificarion_Fragment extends Fragment {
    TextView tv_tooltipdata;
    CustPrograssbar custPrograssbar;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> logo = new ArrayList<>();
    NotificationFragmentAdapter notificationFragmentAdapter;
    RecyclerView noti;

    public Dealer_Notificarion_Fragment() {

    }


    public static Dealer_Notificarion_Fragment newInstance(String param1, String param2) {
        Dealer_Notificarion_Fragment fragment = new Dealer_Notificarion_Fragment();
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
        View v= inflater.inflate(R.layout.fragment_kirloskar__notificarion_, container, false);
        custPrograssbar = new CustPrograssbar();
        noti = (RecyclerView) v.findViewById(R.id.notify);
        notification();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getContext());
        return  v;
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
                                    notificationFragmentAdapter = new NotificationFragmentAdapter(getContext(),message , timestamp, logo );
                                    noti.setAdapter(notificationFragmentAdapter);

                                }


                            }
                        }
                    } else {

                            Toast.makeText(getContext(), "No notification!!", Toast.LENGTH_SHORT).show();


                    }

                }
            }

            @Override
            public void onFailure(Call<Notification_MODEL> call, Throwable t) {



            }

        });
    }

}