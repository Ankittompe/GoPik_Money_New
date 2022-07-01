package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;

import com.qts.gopik_loan.Model.Po_all_details_MODEL;
import com.qts.gopik_loan.Model.Update_po_status_MODEL;

import com.qts.gopik_loan.Pojo.Po_all_details_POJO;
import com.qts.gopik_loan.Pojo.Update_po_status_POJO;

import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.PoDetails_Approve_OEM_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoDetail_Approve_OEM_Activity extends AppCompatActivity {
RecyclerView alldetails_recylerview;
PoDetails_Approve_OEM_Adapter poDetails_ApproveOEM_adapter;
TextView textView3,reject;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();
    ArrayList<String> dealer_id = new ArrayList<>();
    ArrayList<String> dealer_name = new ArrayList<>();
    ArrayList<String> product = new ArrayList<>();
    ArrayList<String> prodt_quantity = new ArrayList<>();
    ArrayList<String> update_quantity = new ArrayList<>();
    ArrayList<String> prodt_price = new ArrayList<>();
    ArrayList<String> update_price = new ArrayList<>();
    ArrayList<String> total_price = new ArrayList<>();
    ArrayList<String> update_totl_prc = new ArrayList<>();
    ArrayList<String> financer = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> invoicefile = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_detail);
       /* alldetails_recylerview=(RecyclerView) findViewById(R.id.alldetails_recylerview);
        textView3=(TextView) findViewById(R.id.textView3);
        reject=(TextView) findViewById(R.id.reject);
        po_all_details();
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,"Approve",getApplicationContext());
                update_po_status();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_po_status();
                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,"Reject",getApplicationContext());
                update_po_status();
            }
        });*/
    }
/*
    private void po_all_details() {



        Po_all_details_POJO pojo = new Po_all_details_POJO("47436");
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call = restApis.po_all_details(pojo);
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

                                id.add(response.body().getPayload().get(i).getId());
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                date.add(response.body().getPayload().get(i).getDate());
                                brand.add(response.body().getPayload().get(i).getBrand());
                                dealer_id.add(response.body().getPayload().get(i).getDealer_id());
                                dealer_name.add(response.body().getPayload().get(i).getDealer_name());
                                product.add(response.body().getPayload().get(i).getProduct());
                                prodt_quantity.add(response.body().getPayload().get(i).getProdt_quantity());
                                update_quantity.add(response.body().getPayload().get(i).getUpdate_quantity());
                                prodt_price.add(response.body().getPayload().get(i).getProdt_price());
                                update_price.add(response.body().getPayload().get(i).getUpdate_price());
                                total_price.add(response.body().getPayload().get(i).getTotal_price());
                                update_totl_prc.add(response.body().getPayload().get(i).getUpdate_totl_prc());
                                financer.add(response.body().getPayload().get(i).getFinancer());
                                status.add(response.body().getPayload().get(i).getStatus());
                                invoicefile.add(response.body().getPayload().get(i).getInvoice_file());

                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            PoDetail_Approve_OEM_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    alldetails_recylerview.setLayoutManager(layoutManager);
                                    poDetails_ApproveOEM_adapter = new PoDetails_Approve_OEM_Adapter(getApplicationContext(),
                                            id,po_id,date,brand,dealer_id,dealer_name,product,prodt_quantity,
                                            update_quantity,prodt_price,update_price,total_price,
                                            update_totl_prc,financer,status,invoicefile);
                                    alldetails_recylerview.setAdapter(poDetails_ApproveOEM_adapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(PoDetail_Approve_OEM_Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }
*/
/*
    private void update_po_status() {



        Update_po_status_POJO pojo = new Update_po_status_POJO("47436",
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,getApplicationContext()));
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_po_status_MODEL> call = restApis.update_po_status(pojo);
        call.enqueue(new Callback<Update_po_status_MODEL>() {
            @Override
            public void onResponse(Call<Update_po_status_MODEL> call, Response<Update_po_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");


                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<Update_po_status_MODEL> call, Throwable t) {


                Toast.makeText(PoDetail_Approve_OEM_Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }
*/

}