package com.qts.gopik_loan.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.Scratch_Activity;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Update_scratchcard_MODEL;
import com.qts.gopik_loan.Pojo.Update_scratchcard_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scratch_Card_Adapter  extends RecyclerView.Adapter<Scratch_Card_Adapter.ListViewHolder> {

    RelativeLayout relative_layout;
    private final Context context;
    ImageView cratch_cover_img;
    ImageView close_button;
    ArrayList<String>id= new ArrayList<>();
    private final ArrayList<String> Amount;
    private final ArrayList<String> status;
    ArrayList<String>expdate= new ArrayList<>();
    Integer Scratch_valid=0;
    ArrayList<String>created_at= new ArrayList<>();
    ScratchCardLayout scratchCardLayout;



    public Scratch_Card_Adapter(Context context, ArrayList<String> amount, ArrayList<String> status,
                                ArrayList<String>expdate,  ArrayList<String>created_at,
                                ArrayList<String>id) {
        Log.e("recyclev0", "kkkkkk");
        this.context = context;
        this.Amount = amount;
        this.status = status;
        this.expdate = expdate;
        this.created_at = created_at;
        this.id = id;

        Log.e("recyclev999", "ooooooo");
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("recyclev999", "5555555555555");
        View view = LayoutInflater.from(context).inflate(R.layout.scratch_layout, parent, false);//View maker
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.scratch_name.setText(new StringBuilder().append("₹").append(Amount.get(position)).toString());


        if (status.get(position).toString().equals("1")){
            holder.cratch_cover_img.setVisibility(View.GONE);
            holder.simple_img.setVisibility(View.VISIBLE);
        }else if (status.get(position).toString().equals("0")) {
            holder.cratch_cover_img.setVisibility(View.VISIBLE);
            holder.simple_img.setVisibility(View.GONE);

        }
      holder.date_tv.setText(created_at.get(position));






        holder.cratch_cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.SCRATCHID,id.get(position),context);
                update_scratchcard();
                Dialog dialogCondition = new Dialog(v.getRootView().getContext());

                View view= LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.scratch_dailog,null);
                Log.e("xyz","xyz22");


                dialogCondition.setContentView(R.layout.scratch_dailog);
                Log.e("xyz","xyz22er");

                close_button = dialogCondition.findViewById(R.id.close_button);
                scratchCardLayout = dialogCondition.findViewById(R.id.scratch_view);
                TextView textView_points_show,scratch_text,scratch_text2;
                LottieAnimationView animationView,better_luck_Animation;
                textView_points_show = dialogCondition.findViewById(R.id.textView_points_show);
                scratch_text = dialogCondition.findViewById(R.id.scratch_text);
                scratch_text2 = dialogCondition.findViewById(R.id.scratch_text2);
                animationView = dialogCondition.findViewById(R.id.animationView);
                better_luck_Animation = dialogCondition.findViewById(R.id.better_luck_Animation);

                scratchCardLayout.setScratchListener(new ScratchListener() {

                    @Override
                    public void onScratchStarted() {
                        scratchCardLayout.setRevealFullAtPercent(20);

                        scratchCardLayout.setScratchEnabled(true);

                    }

                    @Override
                    public void onScratchProgress(ScratchCardLayout scratchCardLayout, int i) {
                        Scratch_valid = 1;
                        if (Amount.get(position).toString().equals("0")){
                            scratch_text2.setVisibility(View.VISIBLE);
                            scratch_text.setVisibility(View.GONE);
                            textView_points_show.setVisibility(View.GONE);
                            better_luck_Animation.setVisibility(View.VISIBLE);

                        }else {
                            scratch_text.setVisibility(View.VISIBLE);
                            scratch_text2.setVisibility(View.GONE);
                            animationView.setVisibility(View.VISIBLE);
                            textView_points_show.setText(new StringBuilder().append("₹").append(Amount.get(position)).toString());


                        }
                    }

                    @Override
                    public void onScratchComplete() {


                        Scratch_valid = 1;
                        if (Amount.get(position).toString().equals("0")){
                            scratch_text2.setVisibility(View.VISIBLE);
                            scratch_text.setVisibility(View.GONE);
                            textView_points_show.setVisibility(View.GONE);
                            better_luck_Animation.setVisibility(View.VISIBLE);

                        }else {
                            scratch_text.setVisibility(View.VISIBLE);
                            scratch_text2.setVisibility(View.GONE);
                            animationView.setVisibility(View.VISIBLE);
                            textView_points_show.setText(new StringBuilder().append("₹").append(Amount.get(position)).toString());


                        }



                    }
                });

                Log.e("xyz","xyz223");
                dialogCondition.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));


                Log.e("yxz","wer");

                dialogCondition.setCancelable(true);
                Log.e("xyz","xyz26");
                dialogCondition.show();
                Log.e("xyz","xyz2225");
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Scratch_valid==1){
                            holder.cratch_cover_img.setVisibility(View.GONE);
                            holder.simple_img.setVisibility(View.VISIBLE);
                        }
                        dialogCondition.cancel();
                    }
                });
            }
        });

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
    }

    private void update_scratchcard() {

        Update_scratchcard_POJO pojo = new Update_scratchcard_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.SCRATCHID,context
        ));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_scratchcard_MODEL> call = restApis.update_scratchcard(pojo);
        call.enqueue(new Callback<Update_scratchcard_MODEL>() {
            @Override
            public void onResponse(Call<Update_scratchcard_MODEL> call, Response<Update_scratchcard_MODEL> response) {
                if (response.body() != null) {





                }
            }

            @Override
            public void onFailure(Call<Update_scratchcard_MODEL> call, Throwable t) {



            }

        });
    }





    @Override
    public int getItemCount() {
        return Amount.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        CardView scratch_cardview;
        ImageView scratch_img,imageView;
        ImageView cratch_cover_img,simple_img;
        TextView scratch_name,date_tv;
        ScratchCardLayout scratch_view;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            //scratch_view = itemView.findViewById(R.id.scratch_view);
            cratch_cover_img = itemView.findViewById(R.id.cratch_cover_img);
            scratch_name = itemView.findViewById(R.id.scratch_name);
            simple_img = itemView.findViewById(R.id.simple_img);
            date_tv = itemView.findViewById(R.id.date_tv);
            //ticket_layout = itemView.findViewById(R.id.simple_img);
            //dialogCondition = new Dialog(context.getApplicationContext());


        }
    }
}