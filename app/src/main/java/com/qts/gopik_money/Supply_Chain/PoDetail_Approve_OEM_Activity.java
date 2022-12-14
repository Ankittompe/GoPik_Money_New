package com.qts.gopik_money.Supply_Chain;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.Po_all_details_MODEL;
import com.qts.gopik_money.Model.Update_po_status_MODEL;
import com.qts.gopik_money.Model.mod_appr_po_status_Model;
import com.qts.gopik_money.Pojo.PO_ID_POJO;
import com.qts.gopik_money.Pojo.Po_all_details_POJO;
import com.qts.gopik_money.Pojo.Update_po_status_POJO;
import com.qts.gopik_money.Pojo.mod_appr_po_status_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supplychain_Adapter.PoDetails_Approve_OEM_Adapter;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.InvoiceViewDownloadDialog;
import com.qts.gopik_money.Utils.TextViewUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoDetail_Approve_OEM_Activity extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    RecyclerView alldetails_recylerview;
    PoDetails_Approve_OEM_Adapter poDetails_ApproveOEM_adapter;
    TextView textView3, reject, et_po_id, et_date, et_dealer_name, et_status, et_total_qty, et_total_price;
    CustPrograssbar custPrograssbar;
    private Dialog dialogCondition;
    TextView Ok_button;
    ImageView camera_button;
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
    String image;
    String invoice_image;
    Integer temp = 0;
    Integer tempp = 0;
    ImageView arrow, hometoolbar, invoice;
    String rupee_symbol = "???";
    Integer tempmod = 0;
    Integer tempmodd = 0;
    Integer tempmodifyprice = 0;
    Integer tempmodifypricee = 0;
    TextView view;
    String mUserType;
    LinearLayout mLinModifyPrice;
    TextView PODealerName;
    TextView mInvoiceNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_detail);



        dialogCondition = new Dialog(PoDetail_Approve_OEM_Activity.this);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        view = (TextView) findViewById(R.id.view);
        mInvoiceNumber = (TextView) findViewById(R.id.invoice_number);
        PODealerName = (TextView) findViewById(R.id.PODealerName);
        et_po_id = (TextView) findViewById(R.id.et_po_id);
        et_date = (TextView) findViewById(R.id.et_date);
        et_dealer_name = (TextView) findViewById(R.id.et_dealer_name);
        et_status = (TextView) findViewById(R.id.et_status);
        et_total_qty = (TextView) findViewById(R.id.et_total_qty);
        et_total_price = (TextView) findViewById(R.id.et_total_price);
        invoice = (ImageView) findViewById(R.id.invoice);
        custPrograssbar = new CustPrograssbar();
        alldetails_recylerview = (RecyclerView) findViewById(R.id.rclview);
        textView3 = (TextView) findViewById(R.id.textView3);
        reject = (TextView) findViewById(R.id.reject);
        arrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        mLinModifyPrice = findViewById(R.id.linModifyPrice);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        PODealerName.append(TextViewUtils.getColoredString("Purchase order of ", ContextCompat.getColor(PoDetail_Approve_OEM_Activity.this, R.color.black)));
        PODealerName.append(TextViewUtils.getColoredString(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()),
                ContextCompat.getColor(PoDetail_Approve_OEM_Activity.this, R.color.blue)));

        if (mUserType.equals("SubDealer")) {
            mLinModifyPrice.setVisibility(View.INVISIBLE);
        } else if (mUserType.equals("Dealer")) {
            mLinModifyPrice.setVisibility(View.INVISIBLE);
        } else {
            mLinModifyPrice.setVisibility(View.INVISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InvoiceViewDownloadDialog().InvoiceDialog(image, PoDetail_Approve_OEM_Activity
                        .this);
            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PoDetail_Approve_OEM_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });
        po_all_details();
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PoDetail_Approve_OEM_Activity.this, PO_TOP_FIVE_Activity.class);

                startActivity(it);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE, "Approved By Dealer", getApplicationContext());
                if (mUserType.equals("SubDealer")) {
                    mod_appr_po_status();
                } else if (mUserType.equals("Dealer")) {
                    update_po_status();
                } else {
                    update_po_status();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custPrograssbar.prograssCreate(PoDetail_Approve_OEM_Activity.this);
                SharedPref.saveStringInSharedPref(AppConstants.SUPPLYCHAIN_APPROVE, "Rejected By Dealer", getApplicationContext());
                update_po_status();
                reject_po_status();

            }
        });

    }

    private void InvoiceDailog() {


        dialogCondition.setContentView(R.layout.invoice_dailog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);
        camera_button = (ImageView) dialogCondition.findViewById(R.id.camera_button);


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
        Po_all_details_POJO pojo = new Po_all_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealer_po_all_details(pojo);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.po_all_details(pojo);
        } else {
            call = restApis.po_all_details(pojo);
        }
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {
                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                mInvoiceNumber.setText(response.body().getPayload().get(i).getInvoice_no());
                                id.add(response.body().getPayload().get(i).getId());
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                date.add(response.body().getPayload().get(i).getDate());
                                brand.add(response.body().getPayload().get(i).getBrand());
                                dealer_id.add(response.body().getPayload().get(i).getDealer_id());
                               // dealer_name.add(response.body().getPayload().get(i).getDealer_name());
                           //     PODealerName.append(TextViewUtils.getColoredString(response.body().getPayload().get(i).getDealer_name(), ContextCompat.getColor(PoDetail_Approve_OEM_Activity.this, R.color.red)));

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


//                                tempp = temp + Integer.parseInt(response.body().getPayload().get(i).getProdt_quantity());
//                                temp = tempp;


                                et_po_id.setText(response.body().getPayload().get(i).getPo_id());
                                et_date.setText(response.body().getPayload().get(i).getDate());
                              //  et_dealer_name.setText(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()));

                                et_status.setText(response.body().getPayload().get(i).getStatus());
//                                et_total_qty.setText(String.valueOf(temp));


                                String number2 = response.body().getPayload().get(i).getTotal_price();

                                double amount2 = Double.parseDouble(number2);

                                DecimalFormat formatter2 = new DecimalFormat("##,##,###");

                                String formatted2 = formatter2.format(amount2);

                                et_total_price.setText(rupee_symbol + formatted2);
                                image = response.body().getImage();

                                tempp = temp + Integer.valueOf(response.body().getPayload().get(i).getProdt_quantity());
                                temp = tempp;

                                Glide.with(getApplicationContext())
                                        .load(image)
                                        .into(invoice);
                                if (response.body().getPayload().get(i).getUpdate_price().equals("0")) {
                                    et_total_qty.setText(String.valueOf(temp));
                                    String number1 = response.body().getPayload().get(i).getTotal_price();

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat("##,##,###");

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);
                                } else {
                                    if (!response.body().getPayload().get(i).getUpdate_quantity().equals("NA")) {
                                        tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getUpdate_quantity());
                                    }else{
                                        tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getProdt_quantity());
                                    }
                                    tempmod = tempmodd;
                                    et_total_qty.setText(String.valueOf(tempmod));
                                    if (!response.body().getPayload().get(i).getUpdate_totl_prc().equals("NA")) {
                                        tempmodifypricee = tempmodifyprice + Integer.parseInt(response.body().getPayload().get(i).getUpdate_totl_prc());
                                    }else{
                                        tempmodifypricee = tempmodifyprice + Integer.parseInt(response.body().getPayload().get(i).getTotal_price());
                                    }
                                    tempmodifyprice = tempmodifypricee;
                                    String number1 = String.valueOf(tempmodifyprice);

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat("##,##,###");

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);
                                }
                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            PoDetail_Approve_OEM_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    alldetails_recylerview.setLayoutManager(layoutManager);
                                    poDetails_ApproveOEM_adapter = new PoDetails_Approve_OEM_Adapter(getApplicationContext(),
                                            id, po_id, date, brand, dealer_id, dealer_name, product, prodt_quantity,
                                            update_quantity, prodt_price, update_price, total_price,
                                            update_totl_prc, financer, status, invoicefile);
                                    alldetails_recylerview.setAdapter(poDetails_ApproveOEM_adapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                } else {
                    custPrograssbar.closePrograssBar();
                }


            }

            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void update_po_status() {
        custPrograssbar.prograssCreate(this);
        Update_po_status_POJO pojo = new Update_po_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE, getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Update_po_status_MODEL> call = restApis.update_po_status(pojo);
        call.enqueue(new Callback<Update_po_status_MODEL>() {
            @Override
            public void onResponse(Call<Update_po_status_MODEL> call, Response<Update_po_status_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();


                        Intent it = new Intent(PoDetail_Approve_OEM_Activity.this, PO_TOP_FIVE_Activity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                        startActivity(it);
                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Update_po_status_MODEL> call, Throwable t) {

                Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void mod_appr_po_status() {

        custPrograssbar.prograssCreate(this);

        mod_appr_po_status_POJO pojo = new mod_appr_po_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MODIFYTOTALPRICE_PO, getApplicationContext()));

        PO_ID_POJO mPO_ID_POJO = new PO_ID_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<mod_appr_po_status_Model> call;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealer_po_approve(mPO_ID_POJO);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.mod_appr_po_status(pojo);
        } else {
            call = restApis.mod_appr_po_status(pojo);
        }
        call.enqueue(new Callback<mod_appr_po_status_Model>() {
            @Override
            public void onResponse(Call<mod_appr_po_status_Model> call, Response<mod_appr_po_status_Model> response) {
                if (response.body() != null) {

                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();

                        Intent it = new Intent(PoDetail_Approve_OEM_Activity.this, PO_TOP_FIVE_Activity.class);

                        startActivity(it);

                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                }


            }


            @Override
            public void onFailure(Call<mod_appr_po_status_Model> call, Throwable t) {


                Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }

    private void reject_po_status() {
        custPrograssbar.prograssCreate(this);
        mod_appr_po_status_POJO pojo = new mod_appr_po_status_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SUPPLYCHAIN_APPROVE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.MODIFYTOTALPRICE_PO, getApplicationContext()));
        PO_ID_POJO mPO_ID_POJO = new PO_ID_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<mod_appr_po_status_Model> call;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealer_po_reject(mPO_ID_POJO);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.mod_appr_po_status(pojo);
        } else {
            call = restApis.mod_appr_po_status(pojo);
        }
        call.enqueue(new Callback<mod_appr_po_status_Model>() {
            @Override
            public void onResponse(Call<mod_appr_po_status_Model> call, Response<mod_appr_po_status_Model> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();

                        Intent it = new Intent(PoDetail_Approve_OEM_Activity.this, PO_TOP_FIVE_Activity.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<mod_appr_po_status_Model> call, Throwable t) {
                Toast.makeText(PoDetail_Approve_OEM_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }
        });
    }

}