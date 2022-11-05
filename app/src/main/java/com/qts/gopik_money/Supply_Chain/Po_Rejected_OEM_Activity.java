package com.qts.gopik_money.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.Po_all_details_MODEL;
import com.qts.gopik_money.Model.Update_po_status_MODEL;
import com.qts.gopik_money.Pojo.Po_all_details_POJO;
import com.qts.gopik_money.Pojo.Update_po_status_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supplychain_Adapter.PoDetails_Approve_OEM_Adapter;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.TextViewUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Po_Rejected_OEM_Activity extends AppCompatActivity {

    String mDealer = "Dealer";
    String mSubDealer = "SubDealer";
    String networkError = "It seems your Network is unstable . Please Try again!";
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
    ImageView arrow, hometoolbar;

    CustPrograssbar custPrograssbar;
    RecyclerView alldetails_recylerview;
    PoDetails_Approve_OEM_Adapter poDetails_approve_oem_adapter;
    TextView textView3, reject,et_po_id,et_date,et_dealer_name,et_status,et_total_qty,et_total_price;
    Integer temp=0;
    Integer tempp=0;
    String mUserType;
    String rupee_symbol = "₹";
    TextView PODealerName;
    LinearLayout mLinModifyPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_rejected_oem);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        et_po_id = (TextView) findViewById(R.id.et_po_id);
        PODealerName = (TextView) findViewById(R.id.PODealerName);
        et_date = (TextView) findViewById(R.id.et_date);
        et_dealer_name = (TextView) findViewById(R.id.et_dealer_name);
        et_status = (TextView) findViewById(R.id.et_status);
        et_total_qty = (TextView) findViewById(R.id.et_total_qty);
        et_total_price = (TextView) findViewById(R.id.et_total_price);
        arrow=(ImageView) findViewById(R.id.arrow);
        hometoolbar=(ImageView) findViewById(R.id.hometoolbar);
        mLinModifyPrice = findViewById(R.id.linModifyPrice);
        mLinModifyPrice.setVisibility(View.INVISIBLE);
        po_all_details();

        textView3=(TextView) findViewById(R.id.textView3);
        alldetails_recylerview=(RecyclerView) findViewById(R.id.rclview);
        PODealerName.append(TextViewUtils.getColoredString("Purchase order of ", ContextCompat.getColor(Po_Rejected_OEM_Activity.this, R.color.black)));
        PODealerName.append(TextViewUtils.getColoredString(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()),
                ContextCompat.getColor(Po_Rejected_OEM_Activity.this, R.color.blue)));
        arrow.setOnClickListener(v -> {
            Intent it = new Intent(Po_Rejected_OEM_Activity.this, PO_TOP_FIVE_Activity.class);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Po_Rejected_OEM_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
            startActivity(it);
        });
        textView3.setOnClickListener(v -> {
            Intent it = new Intent(Po_Rejected_OEM_Activity.this, PO_TOP_FIVE_Activity.class);

            startActivity(it);

        });

    }
    private void update_po_status() {

        Update_po_status_POJO pojo = new Update_po_status_POJO( SharedPref.getStringFromSharedPref(AppConstants.PO_ID,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_po_status_MODEL> call = restApis.update_po_status(pojo);
        call.enqueue(new Callback<Update_po_status_MODEL>() {
            @Override
            public void onResponse(Call<Update_po_status_MODEL> call, Response<Update_po_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {


                        Intent it = new Intent(Po_Rejected_OEM_Activity.this, MainActivity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                        startActivity(it);


                    } else {
                        Toast.makeText(Po_Rejected_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                }


            }




            @Override
            public void onFailure(Call<Update_po_status_MODEL> call, Throwable t) {


                Toast.makeText(Po_Rejected_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }

    private void po_all_details() {
        custPrograssbar.prograssCreate(this);


        Po_all_details_POJO pojo = new Po_all_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call;
        if (mUserType.equals(mSubDealer)) {
            call = restApis.subdealer_po_all_details(pojo);
        } else if (mUserType.equals(mDealer)) {
            call = restApis.po_all_details(pojo);
        } else {
            call = restApis.po_all_details(pojo);
        }
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {



                        if (response.body().getPayload().size() > 0) {


                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                id.add(response.body().getPayload().get(i).getId());
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                date.add(response.body().getPayload().get(i).getDate());
                                brand.add(response.body().getPayload().get(i).getBrand());
                                dealer_id.add(response.body().getPayload().get(i).getDealer_id());
                               // dealer_name.add(response.body().getPayload().get(i).getDealer_name());

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
                                tempp = temp + Integer.valueOf(response.body().getPayload().get(i).getProdt_quantity());
                                temp = tempp;

                                et_po_id.setText(response.body().getPayload().get(i).getPo_id());
                                et_date.setText(response.body().getPayload().get(i).getDate());
                                et_dealer_name.setText(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()));
                                et_status.setText(response.body().getPayload().get(i).getStatus());
                                et_total_qty.setText(String.valueOf(temp));

                                String number2 = response.body().getPayload().get(i).getTotal_price();

                                double amount2 = Double.parseDouble(number2);

                                DecimalFormat formatter2 = new DecimalFormat("##,##,###");

                                String formatted2 = formatter2.format(amount2);

                                et_total_price.setText(rupee_symbol + formatted2);

                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            Po_Rejected_OEM_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    alldetails_recylerview.setLayoutManager(layoutManager);
                                    poDetails_approve_oem_adapter = new PoDetails_Approve_OEM_Adapter(getApplicationContext(),
                                            id, po_id, date, brand, dealer_id, dealer_name, product, prodt_quantity,
                                            update_quantity, prodt_price, update_price, total_price,
                                            update_totl_prc, financer, status, invoicefile);
                                    alldetails_recylerview.setAdapter(poDetails_approve_oem_adapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(Po_Rejected_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                }


            }


            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(Po_Rejected_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }

}