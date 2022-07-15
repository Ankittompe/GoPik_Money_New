package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.DealerPoLoans_Model;
import com.qts.gopik_loan.Model.Po_all_details_MODEL;
import com.qts.gopik_loan.Pojo.DealerPoLoans_POJO;
import com.qts.gopik_loan.Pojo.Po_all_details_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.LoanStatusAdapter;
import com.qts.gopik_loan.Supplychain_Adapter.PoRequest_Adapter;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanStatus extends AppCompatActivity {

    LoanStatusAdapter loanStatusAdapter;
    RecyclerView rclview;
    CustPrograssbar custPrograssbar;
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> disb_amount = new ArrayList<>();
    ArrayList<String> date_of_closer = new ArrayList<>();
    ArrayList<String> loan_id = new ArrayList<>();
    ArrayList<String> disb_date = new ArrayList<>();
    ImageView arrow,hometoolbar;
    String rupee_symbol = "₹";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_status);
        custPrograssbar = new CustPrograssbar();
        rclview = findViewById(R.id.rclview);
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoanStatus.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoanStatus.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });

        Getdealerpoloans();

    }
    private void Getdealerpoloans() {

        custPrograssbar.prograssCreate(this);

        DealerPoLoans_POJO pojo = new DealerPoLoans_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerPoLoans_Model> call = restApis.dealerpoloans(pojo);
        call.enqueue(new Callback<DealerPoLoans_Model>() {
            @Override
            public void onResponse(Call<DealerPoLoans_Model> call, Response<DealerPoLoans_Model> response) {
                if (response.body() != null) {

                    Log.e("Response","GetCode--->>");
                    if (response.body().getCode()==200) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                disb_amount.add(response.body().getPayload().get(i).getApproved_amount());
                                date_of_closer.add(response.body().getPayload().get(i).getEnd_date());
                                loan_id.add(response.body().getPayload().get(i).getLoan_no());
                                disb_date.add(response.body().getPayload().get(i).getStart_date());



                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            LoanStatus.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    rclview.setLayoutManager(layoutManager);
                                    loanStatusAdapter = new LoanStatusAdapter(getApplicationContext(),po_id,disb_amount,date_of_closer,loan_id,disb_date);
                                    rclview.setAdapter(loanStatusAdapter);


                                }
                            }
                        }
                    }

                } else {
                    Log.e("400","Do Data----->>");
                    custPrograssbar.closePrograssBar();
                    Toast.makeText(LoanStatus.this,"No Data Found !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DealerPoLoans_Model> call, Throwable t) {

                Toast.makeText(LoanStatus.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }

        });


    }

}