package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiTCallbacks;
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

public class PendingAtGopik extends AppCompatActivity implements PickiTCallbacks {
    CustPrograssbar custPrograssbar;
    private ArrayList<Shopkeeper_PO_profile> mShopkeeperpodataArraylist;
    RecyclerView mRecVwrSubDealerPoList;
    PoDataDetailsAdapter mPoDataDetailsAdapter;
    TextView taxinvoiceno, taxinvoiceprice, date, status, name, view_invoice;
    ImageView taxinvoiceimg;
    String image;
    String Pdf_Name;
    ImageView hometoolbar ;
    ImageView backarrow ;
    CardView tenure_roi_cardview;
    TextView roi_txt;
    TextView tenure_txt ;
    Boolean valuepdf=false;
    TextView invoice_name;
    Button invoice_view;
    String networkError = "It seems your Network is unstable . Please Try again!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_at_gopik);
        custPrograssbar = new CustPrograssbar();
        taxinvoiceno = (TextView) findViewById(R.id.taxinvoiceno);
        taxinvoiceprice = (TextView) findViewById(R.id.taxinvoiceprice);
        date = (TextView) findViewById(R.id.date);
        status = (TextView) findViewById(R.id.status);
        name = (TextView) findViewById(R.id.name);
        name = (TextView) findViewById(R.id.name);

        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        taxinvoiceimg = (ImageView) findViewById(R.id.taxinvoiceimg);
        view_invoice = findViewById(R.id.view_invoice);
        tenure_roi_cardview = findViewById(R.id.tenure_roi_cardview);
        roi_txt = findViewById(R.id.roi_txt);
        tenure_txt = findViewById(R.id.tenure_txt);
        invoice_name = (TextView) findViewById(R.id.invoice_name);
        invoice_view = (Button) findViewById(R.id.invoice_view);

        taxinvoiceimg.setOnClickListener(v -> InvoiceDialog());
        view_invoice.setOnClickListener(v -> InvoiceDialog());
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(PendingAtGopik.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(PendingAtGopik.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        name.setText(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_NAME, getApplicationContext()));
        shopkeeperpo_data_list();
    }

    private void InvoiceDialog() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new InvoiceViewDownloadDialog().InvoiceDialog(image, PendingAtGopik.this);

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
                        date.setText(response.body().getPayload().getCreated_at());
                        image=response.body().getPayload().getInvoice_image();
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
                            taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                            invoice_name.setText(Pdf_Name);
                            view_invoice.setVisibility(View.GONE);
                            invoice_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(PendingAtGopik.this,mShopkeeperInvoiceImage));
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

                        if (response.body().getPayload().getStatus().equals("Awaiting Disbursal")) {
                            tenure_roi_cardview.setVisibility(View.VISIBLE);
                            tenure_txt.setText(response.body().getPayload().getTenure()+" days");
                            roi_txt.setText(response.body().getPayload().getRate_of_interest()+" %");
                        } else if (response.body().getPayload().getStatus().equals("Disbursed by financer")) {
                            tenure_roi_cardview.setVisibility(View.VISIBLE);
                            tenure_txt.setText(response.body().getPayload().getTenure()+" days");
                            roi_txt.setText(response.body().getPayload().getRate_of_interest()+" %");
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

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        File mFile = new File(path);

        Pdf_Name = mFile.getName();

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }


    private void showPDF2(File mFile) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri URI = FileProvider.getUriForFile(PendingAtGopik.this, PendingAtGopik.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(URI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PendingAtGopik.this.startActivity(viewPdf);
    }

}