package com.qts.gopik_loan.Fragment;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.LogIn_Otp_Verify;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Adapter.Contest_Adapter;
import com.qts.gopik_loan.Model.Getcontestresult_MODEL;
import com.qts.gopik_loan.Model.Getusercontest_MODEL;
import com.qts.gopik_loan.Pojo.Getusercontest_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;
import com.qts.gopik_loan.Utils.TextViewOutline;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contest extends Fragment {
    boolean doubleBackToExitPressedOnce = false;
    CustPrograssbar custPrograssbar;
    RecyclerView recyclerview;
    Integer counter = 1;
    CardView progressBar;
    ConstraintLayout tooltip_points_layout;
    TextView Contest_name, Total_amount, User_id, Contest_Points;
    TextView info_button,tv_tooltip_points,username_tv;
    TextView minimum_points, current_points, required_points, score_count;
    public ArrayList<String> contest_name = new ArrayList<>();
    public ArrayList<String> contest_state = new ArrayList<>();
    public ArrayList<Integer> contest_points = new ArrayList<>();
    public ArrayList<Integer> contest_id = new ArrayList<>();
    public ArrayList<Integer> contest_data = new ArrayList<>();
    private Dialog dialogCondition;
    private Dialog dialogCondition2;
    TextView first_prize_tv, second_prize_tv, third_prize_tv,fourth_prize_tv;
    TextView contest_description;

    ImageView gift_image1, gift_image2, gift_image3,gift_image4;
    ImageView popup_close_button2, popup_close_button3;
    ImageView popup_close_button1,popup_close_button4;
    ImageView check_one,check_two,check_three,check_four;

    Integer gift_check_valid1 =0;
    Integer gift_check_valid2 =0;
    Integer gift_check_valid3 =0;
    Integer gift_check_valid4 =0;

    Button trip_ok_button_one,trip_ok_button_two,trip_ok_button_three,trip_ok_button_four;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contest.
     */
    // TODO: Rename and change types and number of parameters
    public static Contest newInstance(String param1, String param2) {
        Contest fragment = new Contest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contest, container, false);
        recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
      /*  Contest_name =(TextView) v.findViewById(R.id.contest_name);
        Contest_Points = (TextView)v.findViewById(R.id.user_points);*/

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

        gift_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TripDailog_One();
                gift_check_valid1 = 1;

            }
        });
        gift_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TripDailog_Two();

                gift_check_valid2 = 1;
            }
        });
        gift_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TripDailog_Three();
                gift_check_valid3 = 1;
            }
        });

        gift_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripDailog_Four();
            }
        });

        /*popup_close_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                close_dailog();

            }
        });*/


        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltip_points_layout.setVisibility(View.VISIBLE);
            }
        });
        tooltip_points_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltip_points_layout.setVisibility(View.GONE);
            }
        });
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
        popup_close_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_four.setVisibility(View.GONE);
            }
        });
        trip_ok_button_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_three.setVisibility(View.GONE);
                check_one.setVisibility(View.GONE);
                check_four.setVisibility(View.VISIBLE);
                check_two.setVisibility(View.GONE);
            }
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
        popup_close_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_three.setVisibility(View.GONE);
            }
        });
        trip_ok_button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_three.setVisibility(View.VISIBLE);
                check_one.setVisibility(View.GONE);
                check_four.setVisibility(View.GONE);
                check_two.setVisibility(View.GONE);
            }
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
        popup_close_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_two.setVisibility(View.GONE);
            }
        });
        trip_ok_button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition2.dismiss();
                check_two.setVisibility(View.VISIBLE);
                check_three.setVisibility(View.GONE);
                check_four.setVisibility(View.GONE);
                check_one.setVisibility(View.GONE);
            }
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
        popup_close_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition.dismiss();
                check_one.setVisibility(View.GONE);
            }
        });
        trip_ok_button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition.dismiss();
                check_one.setVisibility(View.VISIBLE);
                check_two.setVisibility(View.GONE);
                check_three.setVisibility(View.GONE);
                check_four.setVisibility(View.GONE);
            }
        });
    }

    private void close_dailog() {
        dialogCondition.dismiss();

    }

    private void info_dailog() {
        dialogCondition.setContentView(R.layout.info_dailog_layout);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();


    }

    private void getusercontest() {

        // progressBar.setVisibility(View.VISIBLE);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Getusercontest_POJO usercontest_pojo = new Getusercontest_POJO("64356");
        Call<Getusercontest_MODEL> call = restApis.getusercontest(usercontest_pojo);
        call.enqueue(new Callback<Getusercontest_MODEL>() {
            @Override
            public void onResponse(Call<Getusercontest_MODEL> call, Response<Getusercontest_MODEL> response) {
                if (response.body() != null) {
                    Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        //   progressBar.setVisibility(View.GONE)
                        tv_tooltip_points.setText(response.body().getInfo());
                        minimum_points.setText(response.body().getMinimum_points());
                        required_points.setText(response.body().getRequire());
                        current_points.setText(response.body().getPoints());
                        if (response.body().getPosition().equals("NA")) {
                            score_count.setText("0");
                        } else {
                            score_count.setText(response.body().getPosition());

                        }

                    } else {
                        Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Getusercontest_MODEL> call, Throwable t) {


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

                       /* first_prize_tv.setText(response.body().getFirst_Prize());
                        second_prize_tv.setText(response.body().getSecond_Prize());
                        third_prize_tv.setText(response.body().getThird_Prize());
                        contest_description.setText(response.body().getMessage());*/


                    if (response.body().getPayload().size() > 0) {

                        for (int i = 0; i < response.body().getPayload().size(); i++) {
                            Log.e("Body", "body3");
                            Log.e("TAG", "City Payload Size: " + response.body().getPayload().get(i).getUsername());
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

                                Log.e("Body", "body3");
                            }
                        }


                    }
                } else {
                    custPrograssbar.closePrograssBar();
                    Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Getcontestresult_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }


}