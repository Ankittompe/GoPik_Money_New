package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeper_PO_profile;
import com.qts.gopik_money.Model.Shopkeeperpo_data_MODEL_datalist;
import com.qts.gopik_money.Pojo.Shopkeeperpo_data_list_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.ShopkeeperAdapter.PoDataDetailsAdapter;
import com.qts.gopik_money.Utils.CheckPDFOrImage;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.InvoiceViewDownloadDialog;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectedShopkeeper extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    CustPrograssbar custPrograssbar;
    private ArrayList<Shopkeeper_PO_profile> mShopkeeperpodataArraylist;
    RecyclerView mRecVwrSubDealerPoList;
    PoDataDetailsAdapter mPoDataDetailsAdapter;
    TextView taxinvoiceno;
    TextView taxinvoiceprice;
    TextView date;
    TextView status;
    TextView name;
    TextView view_invoice;

    ImageView taxinvoiceimg;
    String image;
    String Pdf_Name;
    TextView status_reason;
    ImageView hometoolbar;
    ImageView backarrow;
    Boolean valuepdf=false;
    TextView invoice_name;
    Button invoice_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_shopkeeper);
        custPrograssbar = new CustPrograssbar();
        status_reason = findViewById(R.id.status_reason);
        taxinvoiceno = (TextView) findViewById(R.id.taxinvoiceno);
        taxinvoiceprice = (TextView) findViewById(R.id.taxinvoiceprice);
        date = (TextView) findViewById(R.id.date);
        status = (TextView) findViewById(R.id.status);
        name = (TextView) findViewById(R.id.name);
        name = (TextView) findViewById(R.id.name);
        taxinvoiceimg = (ImageView) findViewById(R.id.taxinvoiceimg);
        view_invoice = findViewById(R.id.view_invoice);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        invoice_name = (TextView) findViewById(R.id.invoice_name);
        invoice_view = (Button) findViewById(R.id.invoice_view);

        taxinvoiceimg.setOnClickListener(v -> InvoiceDialog());
        view_invoice.setOnClickListener(v -> InvoiceDialog());
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(RejectedShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(RejectedShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        name.setText(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_NAME, getApplicationContext()));
        shopkeeperpo_data_list();
    }

    private void InvoiceDialog() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new InvoiceViewDownloadDialog().InvoiceDialog(image, RejectedShopkeeper.this);

    }
    private void shopkeeperpo_data_list() {
        custPrograssbar.prograssCreate(this);
        Shopkeeperpo_data_list_POJO pojo = new Shopkeeperpo_data_list_POJO(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeperpo_data_MODEL_datalist> call = restApis.shopkeeperpo_data_list(pojo);
        call.enqueue(new Callback<Shopkeeperpo_data_MODEL_datalist>() {
            @Override
            public void onResponse(Call<Shopkeeperpo_data_MODEL_datalist> call, Response<Shopkeeperpo_data_MODEL_datalist> response) {
                if (response.body() != null) {

                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {

                        taxinvoiceno.setText(response.body().getPayload().getInvoice_no());
                        taxinvoiceprice.setText(response.body().getPayload().getInvoice_price());
                        status.setText(response.body().getPayload().getStatus());

                            status_reason.setText(response.body().getPayload().getReason_of_rejection());


                        date.setText(response.body().getPayload().getCreated_at());
                        image=response.body().getPayload().getInvoice_image();
                        /*   name.setText(response.body().getPayload().get(i).getShopkeeper_name());*/
                        String mShopkeeperInvoiceImage = response.body().getPayload().getInvoice_image();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mShopkeeperInvoiceImage);
                        /*   name.setText(response.body().getPayload().get(i).getShopkeeper_name());*/
                        if(valuepdf){

                            File mFile = new File(mShopkeeperInvoiceImage);

                            Pdf_Name = mFile.getName();
                            invoice_name.setVisibility(View.VISIBLE);
                            invoice_view.setVisibility(View.VISIBLE);
                            taxinvoiceimg.setImageResource(R.drawable.c3);
                            taxinvoiceimg.getLayoutParams().height = 100;
                            taxinvoiceimg.getLayoutParams().width =100;
                            view_invoice.setVisibility(View.GONE);
                            taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                            invoice_name.setText(Pdf_Name);
                            invoice_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(RejectedShopkeeper.this,mShopkeeperInvoiceImage));
                        }
                        else {

                            try {
                                URL newurl = new URL(mShopkeeperInvoiceImage);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                taxinvoiceimg.setImageBitmap(mIcon_val);
                                invoice_name.setVisibility(View.GONE);
                                invoice_view.setVisibility(View.GONE);
                                taxinvoiceimg.getLayoutParams().height = 300;
                                taxinvoiceimg.getLayoutParams().width = 400;
                                taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                                view_invoice.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }

                    }


                }
            }

            @Override
            public void onFailure(Call<Shopkeeperpo_data_MODEL_datalist> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

            }
        });
    }

}