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
import com.qts.gopik_money.Model.Shopkeeperpo_loan_alldetail_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeperpo_loan_alldetail_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.ShopkeeperAdapter.ShopkeeperLoanPoAllDetails_Adapter;
import com.qts.gopik_money.Utils.CheckPDFOrImage;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.InvoiceViewDownloadDialog;

import java.io.File;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopkeeperLoanDetails extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView taxinvoiceno;
    TextView taxinvoiceprice;
    TextView date;
    TextView status;
    TextView name;
    TextView loanid;
    TextView disamt;
    TextView roi;
    TextView ten;
    ImageView taxinvoiceimg;
    String image;
    String mPdfName;
    CustPrograssbar custPrograssbar;
    ShopkeeperLoanPoAllDetails_Adapter mShopkeeperloanPoAllDetailsAdapter;
    RecyclerView product_recyclerview;
    TextView et_total_qty,et_total_price,ok_button;
    ImageView arrow;
    ImageView hometoolbar;
    String rupee_symbol = "₹";
    TextView view_invoice;
    Boolean valuepdf=false;
    TextView invoice_name;
    Button invoice_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_loan_details);
        custPrograssbar = new CustPrograssbar();
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        taxinvoiceno = (TextView) findViewById(R.id.taxinvoiceno);
        taxinvoiceprice = (TextView) findViewById(R.id.taxinvoiceprice);
        date = (TextView) findViewById(R.id.date);
        status = (TextView) findViewById(R.id.status);
        name = (TextView) findViewById(R.id.name);
        loanid = (TextView) findViewById(R.id.loanid);
        disamt= (TextView) findViewById(R.id.disbu);
        roi= (TextView) findViewById(R.id.roi);
        ten= (TextView) findViewById(R.id.ten);
        taxinvoiceimg = (ImageView) findViewById(R.id.taxinvoiceimg);
        view_invoice = (TextView) findViewById(R.id.view_invoice);
        invoice_name = (TextView) findViewById(R.id.invoice_name);
        invoice_view = (Button) findViewById(R.id.invoice_view);

        name.setText(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_NAME, getApplicationContext()));
        taxinvoiceimg.setOnClickListener(v -> InvoiceDialog());
        view_invoice.setOnClickListener(v -> InvoiceDialog());
     /*   ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopkeeperLoanDetails.this, LoanStatus.class);
                startActivity(intent);
            }
        });*/
        arrow.setOnClickListener(v -> {
            Intent it = new Intent(ShopkeeperLoanDetails.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(ShopkeeperLoanDetails.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        shopkeeperpo_loan_alldetail();
    }
    private void InvoiceDialog() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new InvoiceViewDownloadDialog().InvoiceDialog(image, ShopkeeperLoanDetails.this);

    }


    private void shopkeeperpo_loan_alldetail() {
        custPrograssbar.prograssCreate(this);
        Shopkeeperpo_loan_alldetail_POJO pojo = new Shopkeeperpo_loan_alldetail_POJO( SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_ID,getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeperpo_loan_alldetail_MODEL> call = restApis.shopkeeperpo_loan_alldetail(pojo);
        call.enqueue(new Callback<Shopkeeperpo_loan_alldetail_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeperpo_loan_alldetail_MODEL> call, Response<Shopkeeperpo_loan_alldetail_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode()==200) {
                        taxinvoiceno.setText(response.body().getPayload().getPo_id());
                        loanid.setText(response.body().getPayload().getLoan_id());
                        roi.setText(response.body().getPayload().getRate_of_interest()+" %");
                        ten.setText(response.body().getPayload().getTenure()+" days");


                            taxinvoiceprice.setText("₹"+response.body().getPayload().getInvoice_price());


                        image=response.body().getPayload().getInvoice_image();
                        String mShopkeeperInvoiceImage = response.body().getPayload().getInvoice_image();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mShopkeeperInvoiceImage);
                        /*   name.setText(response.body().getPayload().get(i).getShopkeeper_name());*/
                        if(valuepdf){

                            File mFile = new File(mShopkeeperInvoiceImage);
                            mPdfName = mFile.getName();
                            invoice_name.setVisibility(View.VISIBLE);
                            invoice_view.setVisibility(View.VISIBLE);
                            taxinvoiceimg.setImageResource(R.drawable.c3);
                            taxinvoiceimg.getLayoutParams().height = 100;
                            taxinvoiceimg.getLayoutParams().width =100;
                            taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                            invoice_name.setText(mPdfName);
                            view_invoice.setVisibility(View.GONE);
                            invoice_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(ShopkeeperLoanDetails.this,mShopkeeperInvoiceImage));
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
                                view_invoice.setVisibility(View.VISIBLE);
                                taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                        disamt.setText(response.body().getPayload().getDisburse_amount());
                        date.setText(response.body().getPayload().getDisburse_date());

                        status.setText(response.body().getPayload().getStatus());


                }}


            }



            @Override
            public void onFailure(Call<Shopkeeperpo_loan_alldetail_MODEL> call, Throwable t) {


                Toast.makeText(ShopkeeperLoanDetails.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }


}