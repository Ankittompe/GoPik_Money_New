package com.qts.gopik_money.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Adapter.Contest_Adapter;
import com.qts.gopik_money.Model.Getcontestresult_MODEL;
import com.qts.gopik_money.Model.Getusercontest_MODEL;
import com.qts.gopik_money.Pojo.Getusercontest_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Contest extends Fragment {
    String networkError = "It seems your Network is unstable . Please Try again!";
    CustPrograssbar custPrograssbar;
    RecyclerView recyclerview;
    Integer counter = 1;
    CardView progressBar;
    ConstraintLayout tooltip_points_layout;

    TextView info_button;
    TextView tv_tooltip_points;
    TextView username_tv;
    TextView minimum_points;
    TextView  current_points;
    TextView  required_points;
    TextView score_count;
    public List<String> contest_name = new ArrayList<>();
    public List<String> contest_state = new ArrayList<>();
    public List<Integer> contest_points = new ArrayList<>();
    public List<Integer> contest_id = new ArrayList<>();

    private Dialog dialogCondition;
    private Dialog dialogCondition2;
    TextView first_prize_tv;
    TextView second_prize_tv;
    TextView third_prize_tv;
    TextView fourth_prize_tv;
    TextView contest_description;

    ImageView gift_image1;
    ImageView gift_image2;
    ImageView gift_image3;
    ImageView gift_image4;
    ImageView popup_close_button2 ;
    ImageView popup_close_button3;
    ImageView popup_close_button1;
    ImageView popup_close_button4;
    ImageView check_one;
    ImageView check_two;
    ImageView check_three;
    ImageView check_four;

    Integer gift_check_valid1 =0;
    Integer gift_check_valid2 =0;
    Integer gift_check_valid3 =0;
    Integer gift_check_valid4 =0;

    Button trip_ok_button_one;
    Button trip_ok_button_two;
    Button trip_ok_button_three;
    Button trip_ok_button_four;



    public Contest() {
        // Required empty public constructor
    }


    public static Contest newInstance() {
        Contest fragment = new Contest();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contest, container, false);
        recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);


        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getContext());

        contest_description = v.findViewById(R.id.contest_description);
        tv_tooltip_points=v.findViewById(R.id.tv_tooltip_points);
        gift_image1 = v.findViewById(R.id.gift_image1);
        gift_image2 = v.findViewById(R.id.gift_image2);
        gift_image3 = v.findViewById(R.id.gift_image3);
        gift_image4 = v.findViewById(R.id.gift_image4);

        dialogCondition = new Dialog(getActivity());
        dialogCondition2 = new Dialog(getActivity());


        minimum_points = v.findViewById(R.id.minimum_points);
        current_points = v.findViewById(R.id.current_points);
        required_points = v.findViewById(R.id.required_points);
        score_count = v.findViewById(R.id.score_count);
        info_button = v.findViewById(R.id.info_button);
        tooltip_points_layout = v.findViewById(R.id.tooltip_points_layout);
        custPrograssbar = new CustPrograssbar();
        username_tv= v.findViewById(R.id.username_tv);
        check_one = v.findViewById(R.id.check_one);
        check_two = v.findViewById(R.id.check_two);
        check_three = v.findViewById(R.id.check_three);
        check_four = v.findViewById(R.id.check_four);
        if(SharedPref.getStringFromSharedPref(AppConstants.CONTEST_NAME,getContext()).equals("NA")){
            username_tv.setText("Your Name");

        }
        else{
            username_tv.setText(SharedPref.getStringFromSharedPref(AppConstants.CONTEST_NAME,getContext()));

        }

        gift_image1.setOnClickListener(v12 -> {

            TripDailog_One();
            gift_check_valid1 = 1;

        });
        gift_image2.setOnClickListener(v13 -> {

            TripDailog_Two();

            gift_check_valid2 = 1;
        });
        gift_image3.setOnClickListener(v1 -> {

            TripDailog_Three();
            gift_check_valid3 = 1;
        });

        gift_image4.setOnClickListener(v14 -> TripDailog_Four());



        info_button.setOnClickListener(v15 -> tooltip_points_layout.setVisibility(View.VISIBLE));
        tooltip_points_layout.setOnClickListener(v16 -> tooltip_points_layout.setVisibility(View.GONE));
        getusercontest();
        getcontestresult();

        return v;
    }

    private void TripDailog_Four() {
        dialogCondition2.setContentView(R.layout.trip_dailog_four);
        trip_ok_button_four = dialogCondition2.findViewById(R.id.trip_ok_button_four);
        fourth_prize_tv = dialogCondition2.findViewById(R.id.fourth_prize_tv);
        popup_close_button4 = (ImageView) dialogCondition2.findViewById(R.id.popup_close_button4);
        dialogCondition2.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialogCondition2.setCancelable(true);
        dialogCondition2.show();
        popup_close_button4.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_four.setVisibility(View.GONE);
        });
        trip_ok_button_four.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_three.setVisibility(View.GONE);
            check_one.setVisibility(View.GONE);
            check_four.setVisibility(View.VISIBLE);
            check_two.setVisibility(View.GONE);
        });
    }

    private void TripDailog_Three() {

        dialogCondition2.setContentView(R.layout.trip_dailog_three);
        trip_ok_button_three = dialogCondition2.findViewById(R.id.trip_ok_button_three);
        third_prize_tv = dialogCondition2.findViewById(R.id.third_prize_tv);
        popup_close_button3 = (ImageView) dialogCondition2.findViewById(R.id.popup_close_button3);
        dialogCondition2.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialogCondition2.setCancelable(true);
        dialogCondition2.show();
        third_prize_tv.setText(SharedPref.getStringFromSharedPref(AppConstants.THIRD_TRIP, getContext()));
        popup_close_button3.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_three.setVisibility(View.GONE);
        });
        trip_ok_button_three.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_three.setVisibility(View.VISIBLE);
            check_one.setVisibility(View.GONE);
            check_four.setVisibility(View.GONE);
            check_two.setVisibility(View.GONE);
        });
    }

    private void TripDailog_Two() {
        dialogCondition2.setContentView(R.layout.trip_dailog_layout);
        trip_ok_button_two = dialogCondition2.findViewById(R.id.trip_ok_button_two);
        second_prize_tv = dialogCondition2.findViewById(R.id.second_prize_tv);
        popup_close_button2 = (ImageView) dialogCondition2.findViewById(R.id.popup_close_button2);
        dialogCondition2.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialogCondition2.setCancelable(true);
        dialogCondition2.show();
        second_prize_tv.setText(SharedPref.getStringFromSharedPref(AppConstants.SECOND_TRIP, getContext()));
        popup_close_button2.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_two.setVisibility(View.GONE);
        });
        trip_ok_button_two.setOnClickListener(v -> {
            dialogCondition2.dismiss();
            check_two.setVisibility(View.VISIBLE);
            check_three.setVisibility(View.GONE);
            check_four.setVisibility(View.GONE);
            check_one.setVisibility(View.GONE);
        });
    }

    private void TripDailog_One() {

        dialogCondition.setContentView(R.layout.info_dailog_layout);
        trip_ok_button_one = dialogCondition.findViewById(R.id.trip_ok_button_one);
        first_prize_tv = dialogCondition.findViewById(R.id.first_prize_tv);
        popup_close_button1 = (ImageView) dialogCondition.findViewById(R.id.popup_close_button1);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));

        dialogCondition.setCancelable(true);
        dialogCondition.show();
        first_prize_tv.setText(SharedPref.getStringFromSharedPref(AppConstants.FIRST_TRIP, getContext()));
        popup_close_button1.setOnClickListener(v -> {
            dialogCondition.dismiss();
            check_one.setVisibility(View.GONE);
        });
        trip_ok_button_one.setOnClickListener(v -> {
            dialogCondition.dismiss();
            check_one.setVisibility(View.VISIBLE);
            check_two.setVisibility(View.GONE);
            check_three.setVisibility(View.GONE);
            check_four.setVisibility(View.GONE);
        });
    }



    private void getusercontest() {

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Getusercontest_POJO usercontest_pojo = new Getusercontest_POJO("64356");
        Call<Getusercontest_MODEL> call = restApis.getusercontest(usercontest_pojo);
        call.enqueue(new Callback<Getusercontest_MODEL>() {
            @Override
            public void onResponse(Call<Getusercontest_MODEL> call, Response<Getusercontest_MODEL> response) {
                if (response.body() != null&&response.body().getCode().equals("200")) {

                        tv_tooltip_points.setText(response.body().getInfo());
                        minimum_points.setText(response.body().getMinimum_points());
                        required_points.setText(response.body().getRequire());
                        current_points.setText(response.body().getPoints());
                        if (response.body().getPosition().equals("NA")) {
                            score_count.setText("0");
                        } else {
                            score_count.setText(response.body().getPosition());

                        }
                }else {
                    Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Getusercontest_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getcontestresult() {
        custPrograssbar.prograssCreate(getContext());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Getcontestresult_MODEL> call = restApis.getcontestresult();
        call.enqueue(new Callback<Getcontestresult_MODEL>() {
            @Override
            public void onResponse(Call<Getcontestresult_MODEL> call, Response<Getcontestresult_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    SharedPref.saveStringInSharedPref(AppConstants.FIRST_TRIP, response.body().getFirst_Prize(), getContext());
                    SharedPref.saveStringInSharedPref(AppConstants.SECOND_TRIP, response.body().getSecond_Prize(), getContext());
                    SharedPref.saveStringInSharedPref(AppConstants.THIRD_TRIP, response.body().getThird_Prize(), getContext());




                    if (response.body().getPayload().size() > 0) {

                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            contest_state.add(response.body().getPayload().get(i).getState());
                            contest_name.add(response.body().getPayload().get(i).getUsername());
                            contest_points.add(response.body().getPayload().get(i).getPoints());
                            contest_id.add(counter++);


                            if (response.body().getPayload().size() - 1 == i) {

                                LinearLayoutManager layoutManager = new LinearLayoutManager(
                                        getContext(), LinearLayoutManager.VERTICAL, false
                                );
                                recyclerview.setLayoutManager(layoutManager);
                                recyclerview.setItemAnimator(new DefaultItemAnimator());

                                Contest_Adapter contest_Adapter = new Contest_Adapter(getActivity(), contest_name, contest_points, contest_id,
                                        contest_state);
                                recyclerview.setAdapter(contest_Adapter);

                            }
                        }


                    }
                } else {
                    custPrograssbar.closePrograssBar();
                    Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Getcontestresult_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }


}