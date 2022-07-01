package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Adapter.All_product_list_Adapter;
import com.qts.gopik_loan.Model.all_POs_MODEL;
import com.qts.gopik_loan.Pojo.all_POs_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_Generate__AllList_Activity extends AppCompatActivity {

    RecyclerView all_list_recycleview;
    ArrayList<String> All_product_list = new ArrayList<>();
    ArrayList<String> All_product_id = new ArrayList<>();
    ArrayList<String> All_po_date = new ArrayList<>();
    ArrayList<String> All_po_id = new ArrayList<>();
    ArrayList<String> All_po_Status = new ArrayList<>();
    All_product_list_Adapter all_product_list_adapter;
    ImageView arrow,hometoolbar;
    CustPrograssbar custPrograssbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_all_list);
        custPrograssbar = new CustPrograssbar();
        all_list_recycleview = findViewById(R.id.all_list_recycleview);
        arrow = findViewById(R.id.arrow);
        hometoolbar=findViewById(R.id.hometoolbar);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_Generate__AllList_Activity.this, PO_TOP_FIVE_Activity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.PO_ALL_LIST_ACTIVITY);
                startActivity(it);
            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_Generate__AllList_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        GetAllProduct_List();



    }

    private void GetAllProduct_List() {
        custPrograssbar.prograssCreate(this);
        all_POs_POJO pojo = new all_POs_POJO("47436");
        //SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext());

        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<all_POs_MODEL> call = restApis.all_POs(pojo);
        call.enqueue(new Callback<all_POs_MODEL>() {
            @Override
            public void onResponse(Call<all_POs_MODEL> call, Response<all_POs_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

                                All_po_date.add(response.body().getPayload().get(i).getDate());
                                All_po_id.add(response.body().getPayload().get(i).getPo_id());
                                All_po_Status.add(response.body().getPayload().get(i).getStatus());



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
                        Toast.makeText(PO_Generate__AllList_Activity.this, "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<all_POs_MODEL> call, Throwable t) {


                Toast.makeText(PO_Generate__AllList_Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_Generate__AllList_Activity.this, LinearLayoutManager.VERTICAL, false
        );


    }
}