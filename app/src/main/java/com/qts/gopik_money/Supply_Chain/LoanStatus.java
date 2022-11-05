package com.qts.gopik_money.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.DealerPoLoans_Model;
import com.qts.gopik_money.Pojo.DealerPoLoans_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supplychain_Adapter.LoanStatusAdapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanStatus extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    LoanStatusAdapter loanStatusAdapter;
    RecyclerView rclview;
    String mSubDealer = "SubDealer";
    String mDealer = "Dealer";
    CustPrograssbar custPrograssbar;
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> disb_amount = new ArrayList<>();
    ArrayList<String> date_of_closer = new ArrayList<>();
    ArrayList<String> loan_id = new ArrayList<>();
    ArrayList<String> disb_date = new ArrayList<>();
    ArrayList<String> tenure = new ArrayList<>();
    ArrayList<String> rateofinterest = new ArrayList<>();
    ImageView arrow;
    ImageView hometoolbar;
    String rupee_symbol = "₹";
    String mUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_status);
        custPrograssbar = new CustPrograssbar();
        rclview = findViewById(R.id.rclview);
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());

        arrow.setOnClickListener(v -> {
            Intent it = new Intent(LoanStatus.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(LoanStatus.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        Getdealerpoloans();

    }
    private void Getdealerpoloans() {

        custPrograssbar.prograssCreate(this);

        DealerPoLoans_POJO pojo = new DealerPoLoans_POJO( SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerPoLoans_Model> call;
        if (mUserType.equals(mSubDealer)) {
            call = restApis.subdealerpoloans(pojo);
        } else if (mUserType.equals(mDealer)) {
            call = restApis.dealerpoloans(pojo);
        } else {
            call = restApis.dealerpoloans(pojo);
        }
        call.enqueue(new Callback<DealerPoLoans_Model>() {
            @Override
            public void onResponse(Call<DealerPoLoans_Model> call, Response<DealerPoLoans_Model> response) {
                if (response.body() != null) {


                    if (response.body().getCode()==200) {
                        custPrograssbar.closePrograssBar();


                        if (response.body().getPayload().size() > 0) {


                            for (int i = 0; i < response.body().getPayload().size(); i++) {

                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                disb_amount.add(response.body().getPayload().get(i).getApproved_amount());
                                date_of_closer.add(response.body().getPayload().get(i).getEnd_date());
                                loan_id.add(response.body().getPayload().get(i).getLoan_no());
                                disb_date.add(response.body().getPayload().get(i).getStart_date());
                                tenure.add(response.body().getPayload().get(i).getTenure());
                                rateofinterest.add(response.body().getPayload().get(i).getRoi());



                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            LoanStatus.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    rclview.setLayoutManager(layoutManager);
                                    loanStatusAdapter = new LoanStatusAdapter(getApplicationContext(),po_id,disb_amount,date_of_closer,loan_id,disb_date,
                                            tenure,rateofinterest);
                                    rclview.setAdapter(loanStatusAdapter);


                                }
                            }
                        }
                    }

                } else {

                    custPrograssbar.closePrograssBar();
                    Toast.makeText(LoanStatus.this,"No Data Found !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DealerPoLoans_Model> call, Throwable t) {

                Toast.makeText(LoanStatus.this, networkError, Toast.LENGTH_LONG).show();

            }

        });


    }

}