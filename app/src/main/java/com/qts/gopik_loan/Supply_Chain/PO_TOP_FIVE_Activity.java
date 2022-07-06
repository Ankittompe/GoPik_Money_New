package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Supplychain_Adapter.Top_Five_Adapter;
import com.qts.gopik_loan.Model.top_five_POs_MODEL;
import com.qts.gopik_loan.Pojo.top_five_POs_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_TOP_FIVE_Activity extends AppCompatActivity {

    TextView view_all_button;
    RecyclerView top_five_recyclerview;
    Top_Five_Adapter top_five_adapter;
    CustPrograssbar custPrograssbar;
    ArrayList<String> Top_five_list = new ArrayList<>();
    ArrayList<String> Top_five_ID = new ArrayList<>();
    ArrayList<String> Top_Five_PO_ID = new ArrayList<>();
    ArrayList<String> Top_Five_Date = new ArrayList<>();
    ArrayList<String> Top_Five_Brand = new ArrayList<>();
    ArrayList<String> Top_Five_Quantity = new ArrayList<>();
    ArrayList<String> Top_Five_Amount = new ArrayList<>();
    ArrayList<String> Status = new ArrayList<>();
    ImageView arrow, hometoolbar;
    ConstraintLayout add_form_button;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate);

        custPrograssbar = new CustPrograssbar();
        view_all_button = findViewById(R.id.view_all_button);
        add_form_button = findViewById(R.id.add_form_button);
        top_five_recyclerview = findViewById(R.id.top_five_recyclerview);
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
  /*      mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
*/
      /*  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_status();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });*/
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_TOP_FIVE_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.TOP_FIVE_ACTIVITY);
                startActivity(it);
            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_TOP_FIVE_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });

        GetTopFiveList();
        view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent po_all_list = new Intent(PO_TOP_FIVE_Activity.this, PO_Generate__AllList_Activity.class);
                startActivity(po_all_list);

            }
        });
        add_form_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent po_all_list = new Intent(PO_TOP_FIVE_Activity.this, PO_Generate_Form_activity.class);
                startActivity(po_all_list);

            }
        });

    }

    private void GetTopFiveList() {
        custPrograssbar.prograssCreate(this);

        top_five_POs_POJO pojo = new top_five_POs_POJO("47436");
        SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext());

        Log.e("checktopfive", "response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<top_five_POs_MODEL> call = restApis.top_five_POs(pojo);
        call.enqueue(new Callback<top_five_POs_MODEL>() {
            @Override
            public void onResponse(Call<top_five_POs_MODEL> call, Response<top_five_POs_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode()==200) {

                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

                                Top_five_list.add(response.body().getPayload().get(i).getProduct());
                                Top_Five_PO_ID.add(response.body().getPayload().get(i).getPo_id());
                                Top_Five_Date.add(response.body().getPayload().get(i).getDate());
                                Top_five_ID.add(response.body().getPayload().get(i).getId());
                                Top_Five_Amount.add(response.body().getPayload().get(i).getTotal_price());
                                Top_Five_Brand.add(response.body().getPayload().get(i).getBrand());
                                Top_Five_Quantity.add(response.body().getPayload().get(i).getProdt_quantity());
                                Status.add(response.body().getPayload().get(i).getStatus());
                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            PO_TOP_FIVE_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    top_five_recyclerview.setLayoutManager(layoutManager);
                                    top_five_adapter = new Top_Five_Adapter(getApplicationContext(), Top_five_list, Top_five_ID, Top_Five_PO_ID, Top_Five_Date,
                                            Top_Five_Quantity, Top_Five_Brand, Top_Five_Amount,Status);
                                    top_five_recyclerview.setAdapter(top_five_adapter);


                                }
                            }
                        }
                    }
                   else  if (response.body().getCode()==400) {
                        custPrograssbar.closePrograssBar();
                    }
                    else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(PO_TOP_FIVE_Activity.this, "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    custPrograssbar.closePrograssBar();
                    Toast.makeText(PO_TOP_FIVE_Activity.this, "No data available", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<top_five_POs_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Toast.makeText(PO_TOP_FIVE_Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_TOP_FIVE_Activity.this, LinearLayoutManager.VERTICAL, false
        );

    }
}