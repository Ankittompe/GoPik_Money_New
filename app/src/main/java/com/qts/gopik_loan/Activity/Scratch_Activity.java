package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.qts.gopik_loan.Fragment.Reward;
import com.qts.gopik_loan.MainActivity;
import com.qts.gopik_loan.Model.Add_scratchcard_MODEL;
import com.qts.gopik_loan.Model.PASSPORT_MODEL;
import com.qts.gopik_loan.Model.Update_scratchcard_MODEL;
import com.qts.gopik_loan.Pojo.Add_scratchcard_POJO;
import com.qts.gopik_loan.Pojo.PASSPORT_POJO;
import com.qts.gopik_loan.Pojo.Update_scratchcard_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import org.jetbrains.annotations.NotNull;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scratch_Activity extends AppCompatActivity implements ScratchListener {
    ScratchCardLayout scratchCardLayout;
    CustPrograssbar custPrograssbar;
    TextView textView_points_show;
    String imh;
    ImageView hometoolbar, backarrow;
    TextView btn_proceed,textscratch,textscratch2;
    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_);
        custPrograssbar = new CustPrograssbar();
        scratchCardLayout = (ScratchCardLayout) findViewById(R.id.idScratchCardIv);
        textView_points_show = (TextView) findViewById(R.id.textView_points_show);
        scratchCardLayout.setScratchListener(Scratch_Activity.this);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        btn_proceed = (TextView) findViewById(R.id.btn_proceed);
        textscratch = (TextView) findViewById(R.id.textscratch);
        textscratch2= (TextView) findViewById(R.id.textscratch2);
        animationView= (LottieAnimationView) findViewById(R.id.animationView);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getApplicationContext());
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_scratchcard();

            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Scratch_Activity.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Scratch_Activity.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        add_scratchcard();
        /*if(SharedPref.getStringFromSharedPref(AppConstants.SCRATCH_COMPLTER_VALUE,getApplicationContext()).equals("true")){
            update_scratchcard();
        }*/
       /* scratchCardLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SharedPref.saveStringInSharedPref(AppConstants.SCRATCH_COMPLTER_VALUE, "true", getApplicationContext());
                Log.e("uuuuuuuu", "yyyyryryytyty");
                if (SharedPref.getStringFromSharedPref(AppConstants.SCRATCH_COMPLTER_VALUE, getApplicationContext()).equals("true")) {
                    update_scratchcard();
                }
                return false;
            }
        });*/

    }

    private void add_scratchcard() {
        custPrograssbar.prograssCreate(Scratch_Activity.this);
        Add_scratchcard_POJO pojo = new Add_scratchcard_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.APPLICATION_NUMBER, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Add_scratchcard_MODEL> call = restApis.add_scratchcard(pojo);
        call.enqueue(new Callback<Add_scratchcard_MODEL>() {
            @Override
            public void onResponse(Call<Add_scratchcard_MODEL> call, Response<Add_scratchcard_MODEL> response) {
                if (response.body() != null) {

                    custPrograssbar.closePrograssBar();

                    if(response.body().getAmount().equals("0")){
                        animationView.setVisibility(View.GONE);
                        textscratch.setText("Better luck next time!!");
                        textscratch.setVisibility(View.VISIBLE);
                        textscratch2.setVisibility(View.GONE);

                    }
                    else{
                        animationView.setVisibility(View.VISIBLE);
                        textscratch2.setText("Congratulation you won!!!");
                        textscratch2.setVisibility(View.VISIBLE);
                        textscratch.setVisibility(View.GONE);
                        textView_points_show.setText(response.body().getAmount());
                    }
                    SharedPref.saveStringInSharedPref(AppConstants.SCRATCH_ID, response.body().getId(), getApplicationContext());


                }
            }

            @Override
            public void onFailure(Call<Add_scratchcard_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onScratchComplete() {
        Log.e("11111", "gdsdsdsdsdsdshj");


    }

    @Override
    public void onScratchProgress(@NotNull ScratchCardLayout scratchCardLayout, int i) {

        Log.e("11111", "5456666566" + i);


    }

    @Override
    public void onScratchStarted() {
        Log.e("11111", "222222222222222222222222");


    }

    private void update_scratchcard() {
        custPrograssbar.prograssCreate(Scratch_Activity.this);
        Update_scratchcard_POJO pojo = new Update_scratchcard_POJO(SharedPref.getStringFromSharedPref(AppConstants.SCRATCH_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_scratchcard_MODEL> call = restApis.update_scratchcard(pojo);
        call.enqueue(new Callback<Update_scratchcard_MODEL>() {
            @Override
            public void onResponse(Call<Update_scratchcard_MODEL> call, Response<Update_scratchcard_MODEL> response) {
                if (response.body() != null) {

                    custPrograssbar.closePrograssBar();

                    Intent it = new Intent(getApplicationContext(), Home.class);
                    it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.REWARD_FRAGMENT);
                    startActivity(it);


                }
            }

            @Override
            public void onFailure(Call<Update_scratchcard_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

}