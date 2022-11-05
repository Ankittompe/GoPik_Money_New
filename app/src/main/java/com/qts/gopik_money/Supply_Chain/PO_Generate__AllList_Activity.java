package com.qts.gopik_money.Supply_Chain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Pojo.PO_POJO;
import com.qts.gopik_money.Supplychain_Adapter.All_product_list_Adapter;
import com.qts.gopik_money.Model.all_POs_MODEL;
import com.qts.gopik_money.Pojo.all_POs_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_Generate__AllList_Activity extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    RecyclerView all_list_recycleview;
    ArrayList<String> All_product_list = new ArrayList<>();
    ArrayList<String> All_product_id = new ArrayList<>();
    ArrayList<String> All_po_date = new ArrayList<>();
    ArrayList<String> All_po_id = new ArrayList<>();
    ArrayList<String> All_po_Status = new ArrayList<>();
    All_product_list_Adapter all_product_list_adapter;
    ImageView arrow;
    String mShopKeeper = "Shop Keeper";
    String mDealer = "Dealer";
    String mSubDealer = "SubDealer";
    ImageView hometoolbar;
    CustPrograssbar custPrograssbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String mUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_all_list);
        custPrograssbar = new CustPrograssbar();
        all_list_recycleview = findViewById(R.id.all_list_recycleview);
        arrow = findViewById(R.id.arrow);
        hometoolbar=findViewById(R.id.hometoolbar);
        mSwipeRefreshLayout=findViewById(R.id.swiperefreshlayout);

        arrow.setOnClickListener(v -> {
            Intent it = new Intent(PO_Generate__AllList_Activity.this, PO_TOP_FIVE_Activity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.PO_ALL_LIST_ACTIVITY);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(PO_Generate__AllList_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(false);
            GetAllProduct_List();
        });


        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        GetAllProduct_List();
    }

    private void GetAllProduct_List() {

        All_po_id.clear();
        All_po_date.clear();
        All_po_Status.clear();
        custPrograssbar.prograssCreate(this);
        all_POs_POJO pojo = new all_POs_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        PO_POJO pojo1 = new PO_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext());


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<all_POs_MODEL> call ;
        if (mUserType.equals(mSubDealer)) {
            call = restApis.GetSubDealerAllPODataOne(pojo1);
        } else if (mUserType.equals(mDealer)) {
            call = restApis.all_POs(pojo);
        } else {
            call = restApis.all_POs(pojo);
        }

        call.enqueue(new Callback<all_POs_MODEL>() {
            @Override
            public void onResponse(Call<all_POs_MODEL> call, Response<all_POs_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();


                        if (response.body().getPayload().size() > 0) {


                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                All_po_date.add(response.body().getPayload().get(i).getDate());
                                All_po_id.add(response.body().getPayload().get(i).getPo_id());
                                All_po_Status.add(response.body().getPayload().get(i).getStatus());
                                All_product_list.add(response.body().getPayload().get(i).getProduct());

                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            PO_Generate__AllList_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );
                                    all_list_recycleview.setLayoutManager(layoutManager);
                                    all_product_list_adapter = new All_product_list_Adapter(getApplicationContext(),All_product_list,All_product_id,All_po_date,All_po_id,All_po_Status);
                                    all_list_recycleview.setAdapter(all_product_list_adapter);
                                }
                            }
                        }
                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(PO_Generate__AllList_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<all_POs_MODEL> call, Throwable t) {
                Toast.makeText(PO_Generate__AllList_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });

    }
}