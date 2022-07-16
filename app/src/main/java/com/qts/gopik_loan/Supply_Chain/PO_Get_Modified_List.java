package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.Po_all_details_MODEL;
import com.qts.gopik_loan.Model.Update_po_status_MODEL;
import com.qts.gopik_loan.Pojo.Po_all_details_POJO;
import com.qts.gopik_loan.Pojo.Update_po_status_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.PoDetails_Approve_OEM_Adapter;
import com.qts.gopik_loan.Supplychain_Adapter.PoRequest_Adapter;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_Get_Modified_List extends AppCompatActivity {

    public ArrayList<Integer> Serial_number = new ArrayList<>();
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
    RecyclerView po_list_recyclerview;
    PoRequest_Adapter poRequest_adapter;
    ArrayList<String>po_list= new ArrayList<>();
    ArrayList<String>initial_qty= new ArrayList<>();
    ArrayList<String>later_qty= new ArrayList<>();
    ArrayList<String>final_price= new ArrayList<>();
    public ArrayList<Integer> contest_id = new ArrayList<>();
    Integer counter = 0;
    ImageView arrow,prod_image,hometoolbar;
    ImageView invoice;
    CustPrograssbar custPrograssbar;
    String rupee_symbol = "₹";
    TextView view;
    private Dialog dialogCondition;
    TextView Ok_button;
    ImageView camera_button;
    String image ;
    Integer temp=0;
    Integer tempmod=0;
    Integer tempmodd=0;
    Integer tempp=0;
    Integer tempmodifyprice=0;
    Integer tempmodifypricee=0;
    TextView textView3,et_total_qty2, reject,et_po_id,et_date,et_dealer_name,et_status,et_total_qty,et_total_price,modified_total_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_get_list);

        dialogCondition = new Dialog(PO_Get_Modified_List.this);
        view = (TextView) findViewById(R.id.view);

        textView3=(TextView) findViewById(R.id.textView3);
        reject=(TextView) findViewById(R.id.reject);
        et_po_id = (TextView) findViewById(R.id.et_po_id);
        et_date = (TextView) findViewById(R.id.et_date);
        et_dealer_name = (TextView) findViewById(R.id.et_dealer_name);
        et_status = (TextView) findViewById(R.id.et_status);
        et_total_qty = (TextView) findViewById(R.id.et_total_qty);
        et_total_qty2= (TextView) findViewById(R.id.et_total_qty2);
        et_total_price = (TextView) findViewById(R.id.et_total_price);
        modified_total_price = (TextView) findViewById(R.id.modified_total_price);
        po_list_recyclerview = findViewById(R.id.rclview);
        invoice=(ImageView) findViewById(R.id.invoice);
        custPrograssbar = new CustPrograssbar();
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceDailog();
            }
        });
        po_all_details();
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_Get_Modified_List.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PO_Get_Modified_List.this, PO_Generate__AllList_Activity.class);
                startActivity(intent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,"Approved By Dealer",getApplicationContext());
                update_po_status();

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,"Rejected By Dealer",getApplicationContext());
                update_po_status();
            }
        });
        ShowList();

    }
    private void InvoiceDailog() {

        dialogCondition.setContentView(R.layout.invoice_dailog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);
        camera_button = (ImageView) dialogCondition.findViewById(R.id.camera_button);

        Log.e("Body", "body3"+image);
        Glide.with(getApplicationContext())
                .load(image)
                .into(camera_button);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();


        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition.dismiss();
            }
        });


    }

    private void po_all_details() {

        custPrograssbar.prograssCreate(this);

        Po_all_details_POJO pojo = new Po_all_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.PO_ID,getApplicationContext()));
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call = restApis.po_all_details(pojo);
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
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
                                image=response.body().getImage();

                                tempp = temp + Integer.valueOf(response.body().getPayload().get(i).getProdt_quantity());
                                temp = tempp;

                                tempmodd=tempmod+Integer.valueOf(response.body().getPayload().get(i).getUpdate_quantity());
                                tempmod=tempmodd;

                              /*  tempmodifypricee=tempmodifyprice+Integer.valueOf(response.body().getPayload().get(i).getUpdate_totl_prc());
                                tempmodifyprice=tempmodifypricee;*/



                                Glide.with(getApplicationContext())
                                        .load(image)
                                        .into(invoice);
                                et_po_id.setText(response.body().getPayload().get(i).getPo_id());
                                et_date.setText(response.body().getPayload().get(i).getDate());
                                et_dealer_name.setText(response.body().getPayload().get(i).getDealer_name());
                                et_status.setText(response.body().getPayload().get(i).getStatus());
                                et_total_qty.setText(String.valueOf(temp));
                                et_total_qty2.setText(String.valueOf(tempmod));

                                SharedPref.saveStringInSharedPref(AppConstants.MODIFYQUANTITY_PO,String.valueOf(tempmod),getApplicationContext());
                                String number1 = response.body().getPayload().get(i).getTotal_price();
                                Log.e("number1","number1--->>"+number1);
                                double amount = Double.parseDouble(number1);
                                Log.e("amount","amount--->>"+amount);
                                DecimalFormat formatter = new DecimalFormat("##,##,###");
                                Log.e("formatter","formatter--->>"+formatter);
                                String formatted = formatter.format(amount);
                                Log.e("formatted","formatted--->>"+formatted);
                                et_total_price.setText(rupee_symbol+formatted);
                                Serial_number.add(counter++);

                                String number2 = response.body().getPayload().get(i).getUpdate_totl_prc();
                                Log.e("number1","number1--->>"+number2);
                                double amount2 = Double.parseDouble(number2);
                                Log.e("amount","amount--->>"+amount2);
                                DecimalFormat formatter2 = new DecimalFormat("##,##,###");
                                Log.e("formatter","formatter--->>"+formatter);
                                String formatted2 = formatter2.format(amount2);
                                Log.e("formatted","formatted--->>"+formatted);
                                modified_total_price.setText(rupee_symbol+formatted2);
                                SharedPref.saveStringInSharedPref(AppConstants.MODIFYTOTALPRICE_PO,String.valueOf(tempmodifyprice),getApplicationContext());

                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            PO_Get_Modified_List.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    po_list_recyclerview.setLayoutManager(layoutManager);
                                    poRequest_adapter = new PoRequest_Adapter(getApplicationContext(),
                                            id,po_id,date,brand,dealer_id,dealer_name,product,prodt_quantity,
                                            update_quantity,prodt_price,update_price,total_price,
                                            update_totl_prc,financer,status,invoicefile,Serial_number);
                                    po_list_recyclerview.setAdapter(poRequest_adapter);


                                }
                            }
                        }
                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(PO_Get_Modified_List.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(PO_Get_Modified_List.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }
    private void update_po_status() {

        custPrograssbar.prograssCreate(this);

        Update_po_status_POJO pojo = new Update_po_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE,getApplicationContext()));
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_po_status_MODEL> call = restApis.update_po_status(pojo);
        call.enqueue(new Callback<Update_po_status_MODEL>() {
            @Override
            public void onResponse(Call<Update_po_status_MODEL> call, Response<Update_po_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");
                        Intent it = new Intent(PO_Get_Modified_List.this, PO_TOP_FIVE_Activity.class);

                        startActivity(it);

                    } else {
                        Toast.makeText(PO_Get_Modified_List.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<Update_po_status_MODEL> call, Throwable t) {


                Toast.makeText(PO_Get_Modified_List.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });


    }

    private void ShowList() {
        contest_id.add(counter++);

    }
}