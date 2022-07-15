package com.qts.gopik_loan.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.DealerAGREEMENTDoc_MODEL;
import com.qts.gopik_loan.Model.DealerBUSINESSPROOFDoc_MODEL;
import com.qts.gopik_loan.Model.DealerDISB_BANKDoc_MODEL;
import com.qts.gopik_loan.Model.DealerESCROWDoc_MODEL;
import com.qts.gopik_loan.Model.DealerGSTDoc_MODEL;
import com.qts.gopik_loan.Model.DealerUDCDoc_MODEL;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessDetails_Activity extends AppCompatActivity implements PickiTCallbacks {

    ImageView dropdown_business_proof, dropdown_udc, agreement_dropdown, GST_DROPDOWN, escrow_dropdown, disb_bank_dropdown;
    Integer Business_valid = 0;
    Integer UDC_Valid = 0;
    Integer Agreement_Valid = 0;
    Integer Escrow_Valid = 0;
    public int PDF_VALID = 0;
    Integer Disb_Bank_Valid = 0;
    public int x = 0;
    public int y = 0;
    Integer GST_Valid = 0;
    PickiT pickiT;
    String Pdf_Name;
    Integer upload_business_success = 0;
    Integer upload_udc_success = 0;
    Integer upload_agreement_success = 0;
    Integer upload_escrow_success = 0;
    Integer upload_gst_success = 0;
    Integer upload_disb_bank_success = 0;


    TextView escrow_save_button,disburse_bank_save_button,Gst_save_button,agreement_save_button,save_business,udc_save_button;

    ImageView dropup2_business_proof, dropup2_udc, agreement_dropup2, Gst_dropup2, escrow_dropup2, disb_bank_dropup2;
    private int GALLERY = 1, CAMERA = 2;
    LinearLayout upld_business_layout, upld_udc_layout, upld_agreement_layout;
    ImageView CameraButton,GalleryButton,PdfButton;
    LinearLayout upld_escrow_layout, upld_disburse_layout, upld_Gst_layout;
    private Dialog dialogCondition;
    ImageView arrow,hometoolbar;
    ImageView upld_business_buton,upld_udc_buton,upld_agreement_buton;
    ImageView upld_escrow_buton,upld_disburse_bank_buton,upld_Gst_buton;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    File mBussinessProofFile = null;
    int mSelectedBussinessProofStatus = 0;
    //UDC
    File mUDCFile = null;
    int mSelectedUDCStatus = 0;
    //Agreement
    File mAgreementFile = null;
    int mSelectedAgreementStatus = 0;
    //Escrow
    File mEscrowFile = null;
    int mSelectedEscrowStatus = 0;
    //DisburseBank
    File mDisburseBankFile = null;
    int mSelectedDisburseBankStatus = 0;
    CustPrograssbar custPrograssbar;
    TextView Gst_name,business_name,udc_name,agreement_name,escrow_name,disb_bank_name;

    //GST
    File mGSTFile = null;
    int  mSelectedGSTStatus = 0;
    TextView btsend;
    ImageView upld_busines_succss,upld_udc_succss,upld_agreement_succss,upld_ecsrow_succss,upld_disb_bank_succss,upld_Gst_succss;

    TextView upld_business_hint,upld_udc_hint,upld_agreement_hint,upld_escrow_hint,upld_disb_bank_hint,upld_Gst_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);


        Gst_name = findViewById(R.id.Gst_name);
        business_name = findViewById(R.id.business_name);
        udc_name = findViewById(R.id.udc_name);
        agreement_name = findViewById(R.id.agreement_name);
        escrow_name = findViewById(R.id.escrow_name);
        disb_bank_name = findViewById(R.id.disb_bank_name);

        btsend = findViewById(R.id.btsend);
        custPrograssbar = new CustPrograssbar();
        pickiT = new PickiT(BusinessDetails_Activity.this, this, BusinessDetails_Activity.this);
        //Save button
        save_business= (TextView) findViewById(R.id.save_business);
        udc_save_button= (TextView) findViewById(R.id.udc_save_button);
        agreement_save_button= (TextView) findViewById(R.id.agreement_save_button);
        escrow_save_button= (TextView) findViewById(R.id.escrow_save_button);
        disburse_bank_save_button= (TextView) findViewById(R.id.disburse_bank_save_button);
        Gst_save_button = (TextView) findViewById(R.id.Gst_save_button);

        upld_disb_bank_succss = findViewById(R.id.upld_disb_bank_succss);
        upld_Gst_succss = findViewById(R.id.upld_Gst_succss);
        upld_udc_succss = findViewById(R.id.upld_udc_succss);
        upld_agreement_succss = findViewById(R.id.upld_agreement_succss);
        upld_ecsrow_succss = findViewById(R.id.upld_ecsrow_succss);
        upld_busines_succss = findViewById(R.id.upld_busines_succss);
        //Upload Hint
        upld_business_hint = findViewById(R.id.upld_business_hint);
        upld_udc_hint = findViewById(R.id.upld_udc_hint);
        upld_agreement_hint = findViewById(R.id.upld_agreement_hint);
        upld_escrow_hint = findViewById(R.id.upld_escrow_hint);
        upld_disb_bank_hint = findViewById(R.id.upld_disb_bank_hint);
        upld_Gst_hint = findViewById(R.id.upld_Gst_hint);

        dialogCondition = new Dialog(BusinessDetails_Activity.this);


        upld_Gst_buton = findViewById(R.id.upld_Gst_buton);
        upld_business_buton = findViewById(R.id.upld_business_buton);
        upld_udc_buton = findViewById(R.id.upld_udc_buton);
        upld_agreement_buton = findViewById(R.id.upld_agreement_buton);
        upld_escrow_buton = findViewById(R.id.upld_escrow_buton);
        upld_disburse_bank_buton = findViewById(R.id.upld_disburse_bank_buton);

        //Back and Home
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        //Dropdown BusinessProof
        dropdown_business_proof = findViewById(R.id.dropdown_business_proof);
        dropup2_business_proof = findViewById(R.id.dropup2_business_proof);

        //Dropdown UDC
        dropdown_udc = findViewById(R.id.dropdown_udc);
        dropup2_udc = findViewById(R.id.dropup2_udc);

        //Dropdown Agreement
        agreement_dropdown = findViewById(R.id.agreement_dropdown);
        agreement_dropup2 = findViewById(R.id.agreement_dropup2);

        //Dropdown Escrow
        escrow_dropdown = findViewById(R.id.escrow_dropdown);
        escrow_dropup2 = findViewById(R.id.escrow_dropup2);

        //Disb_Bank Dropdown
        disb_bank_dropdown = findViewById(R.id.disb_bank_dropdown);
        disb_bank_dropup2 = findViewById(R.id.disb_bank_dropup2);
        //Gst
        //Gst
        GST_DROPDOWN = findViewById(R.id.GST_DROPDOWN);
        Gst_dropup2 = findViewById(R.id.Gst_dropup2);
        //Layout
        upld_business_layout = findViewById(R.id.upld_business_layout);
        upld_udc_layout = findViewById(R.id.upld_udc_layout);
        upld_agreement_layout = findViewById(R.id.upld_agreement_layout);
        upld_escrow_layout = findViewById(R.id.upld_escrow_layout);
        upld_disburse_layout = findViewById(R.id.upld_disburse_layout);
        upld_Gst_layout = findViewById(R.id.upld_Gst_layout);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BusinessDetails_Activity.this, MainActivity.class);
                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(intent);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BusinessDetails_Activity.this, MainActivity.class);
                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intent);
            }
        });
        ////Business Dropdown/Dropup
        dropdown_business_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_business_proof.setVisibility(View.GONE);
                dropup2_business_proof.setVisibility(View.VISIBLE);
                upld_business_layout.setVisibility(View.VISIBLE);

            }
        });
        dropup2_business_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup2_business_proof.setVisibility(View.GONE);
                dropdown_business_proof.setVisibility(View.VISIBLE);
                upld_business_layout.setVisibility(View.GONE);

            }
        });

        ////UDC Dropdown/Dropup
        dropdown_udc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_udc.setVisibility(View.GONE);
                dropup2_udc.setVisibility(View.VISIBLE);
                upld_udc_layout.setVisibility(View.VISIBLE);

            }
        });
        dropup2_udc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup2_udc.setVisibility(View.GONE);
                dropdown_udc.setVisibility(View.VISIBLE);
                upld_udc_layout.setVisibility(View.GONE);

            }
        });

        ////Agreement Dropdown/Dropup
        agreement_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreement_dropdown.setVisibility(View.GONE);
                agreement_dropup2.setVisibility(View.VISIBLE);
                upld_agreement_layout.setVisibility(View.VISIBLE);

            }
        });
        agreement_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreement_dropup2.setVisibility(View.GONE);
                agreement_dropdown.setVisibility(View.VISIBLE);
                upld_agreement_layout.setVisibility(View.GONE);

            }
        });

        ////Escrow Dropdown/Dropup
        escrow_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escrow_dropdown.setVisibility(View.GONE);
                escrow_dropup2.setVisibility(View.VISIBLE);
                upld_escrow_layout.setVisibility(View.VISIBLE);

            }
        });
        escrow_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escrow_dropup2.setVisibility(View.GONE);
                escrow_dropdown.setVisibility(View.VISIBLE);
                upld_escrow_layout.setVisibility(View.GONE);

            }
        });
        ////Disburse Dropdown/Dropup
        disb_bank_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disb_bank_dropdown.setVisibility(View.GONE);
                disb_bank_dropup2.setVisibility(View.VISIBLE);
                upld_disburse_layout.setVisibility(View.VISIBLE);

            }
        });
        disb_bank_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disb_bank_dropup2.setVisibility(View.GONE);
                disb_bank_dropdown.setVisibility(View.VISIBLE);
                upld_disburse_layout.setVisibility(View.GONE);

            }
        });
        ////GST Dropdown/Dropup
        GST_DROPDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GST_DROPDOWN.setVisibility(View.GONE);
                Gst_dropup2.setVisibility(View.VISIBLE);
                upld_Gst_layout.setVisibility(View.VISIBLE);

            }
        });
        Gst_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gst_dropup2.setVisibility(View.GONE);
                GST_DROPDOWN.setVisibility(View.VISIBLE);
                upld_Gst_layout.setVisibility(View.GONE);

            }
        });
        //Upload BusinessProof
        upld_business_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                    x = 5;
                    y = 5;
                    PDF_VALID = 1;
                    mSelectedBussinessProofStatus = 1;


            }
        });
        upld_udc_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                    x = 6;
                    y = 6;
                    PDF_VALID = 2;
                    mSelectedUDCStatus = 1;


            }
        });

        upld_agreement_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                    x = 7;
                    y = 7;
                    PDF_VALID = 3;
                    mSelectedAgreementStatus = 1;

            }
        });

        upld_escrow_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                    x = 8;
                    y = 8;
                    PDF_VALID = 4;
                    mSelectedEscrowStatus = 1;


            }
        });
        upld_disburse_bank_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                    x = 9;
                    y = 9;
                   PDF_VALID = 5;
                    mSelectedDisburseBankStatus = 1;


            }
        });

        upld_Gst_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                    x = 14;
                    y = 14;
                    PDF_VALID = 10;
                    mSelectedGSTStatus = 1;
                }


        });

        save_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Business_valid==1)){
                    upld_business_hint.setVisibility(View.VISIBLE);
                }else {
                    custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    DealerBUSINESSPROOFDoc();

                }


            }
        });
        udc_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(UDC_Valid==1)){
                    Log.e("yyy","=====");
                    upld_udc_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    Log.e("yyy","5555555");
                    DealerUDCDoc();
                }

            }
        });
        agreement_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Agreement_Valid==1)){
                    upld_agreement_hint.setVisibility(View.VISIBLE);
                }else {
                    custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    DealerAGREEMENTDoc();
                }

            }
        });
        escrow_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Escrow_Valid==1)){
                    upld_escrow_hint.setVisibility(View.VISIBLE);
                }else{
                   /* escrow_dropup2.setVisibility(View.GONE);
                    escrow_dropdown.setVisibility(View.VISIBLE);
                    upld_escrow_layout.setVisibility(View.GONE);
                    upld_ecsrow_succss.setVisibility(View.VISIBLE);*/
                 custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    Toast.makeText(BusinessDetails_Activity.this, "Image Upload Sucsessfully!", Toast.LENGTH_SHORT).show();
                     DealerESCROWDoc();
                }

            }
        });
        disburse_bank_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Disb_Bank_Valid == 1)){
                    upld_disb_bank_hint.setVisibility(View.VISIBLE);
                }else{

                   /* disb_bank_dropup2.setVisibility(View.GONE);
                    disb_bank_dropdown.setVisibility(View.VISIBLE);
                    upld_disburse_layout.setVisibility(View.GONE);
                    upld_disb_bank_succss.setVisibility(View.VISIBLE);*/
                    custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    Toast.makeText(BusinessDetails_Activity.this, "Image Upload Sucsessfully!", Toast.LENGTH_SHORT).show();
                    DealerDISB_BANKDoc();
                }

            }
        });

        Gst_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(GST_Valid==1)){
                    upld_Gst_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                    DealerGSTDoc();
                }

            }
        });

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(upload_business_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please upload a  Business Document!", Toast.LENGTH_SHORT).show();
                }else if (!(upload_udc_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please Upload an UDC Document!", Toast.LENGTH_SHORT).show();
                } else if (!(upload_agreement_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please Upload an Agreement Document!", Toast.LENGTH_SHORT).show();
                } else if (!(upload_escrow_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please Upload an Escrow Document !", Toast.LENGTH_SHORT).show();
                } else if (!(upload_disb_bank_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please Upload a Disbursal bank Document !", Toast.LENGTH_SHORT).show();
                }else if (!(upload_gst_success==1)){
                    Toast.makeText(BusinessDetails_Activity.this, "Please Upload a Gst Document !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BusinessDetails_Activity.this, "All Documents Submitted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BusinessDetails_Activity.this, TransactionDetails_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void DealerESCROWDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
        //       Log.e("UDC FileName ", mEscrowFile.toString());

        if (mSelectedEscrowStatus == 1) {
            idFile = mEscrowFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("ESCROW", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerESCROWDoc_MODEL> call = restApis.DealerESCROWDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerESCROWDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerESCROWDoc_MODEL> call, Response<DealerESCROWDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    escrow_dropup2.setVisibility(View.GONE);
                    escrow_dropdown.setVisibility(View.VISIBLE);
                    upld_escrow_layout.setVisibility(View.GONE);
                    upld_ecsrow_succss.setVisibility(View.VISIBLE);
                    escrow_save_button.setText("Update");
                    mSelectedEscrowStatus = 0;
                    upload_escrow_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerESCROWDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerAGREEMENTDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
//        Log.e("UDC FileName ", mAgreementFile.toString());

        if (mSelectedAgreementStatus == 1) {
            idFile = mAgreementFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("AGREEMENT", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerAGREEMENTDoc_MODEL> call = restApis.DealerAGREEMENTDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerAGREEMENTDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerAGREEMENTDoc_MODEL> call, Response<DealerAGREEMENTDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    custPrograssbar.closePrograssBar();
                    agreement_dropup2.setVisibility(View.GONE);
                    agreement_dropdown.setVisibility(View.VISIBLE);
                    upld_agreement_layout.setVisibility(View.GONE);
                    upld_agreement_succss.setVisibility(View.VISIBLE);
                    agreement_save_button.setText("Update");

                    mSelectedAgreementStatus = 0;
                    upload_agreement_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerAGREEMENTDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerUDCDoc() {


        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;
        //  Log.e("testingggg", "testingggg99999" );
        //  Log.e("UDC FileName ",""+ mUDCFile.toString());

        if (mSelectedUDCStatus == 1) {
            idFile = mUDCFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("UDC", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerUDCDoc_MODEL> call = restApis.DealerUDCDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerUDCDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerUDCDoc_MODEL> call, Response<DealerUDCDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    dropup2_udc.setVisibility(View.GONE);
                    dropdown_udc.setVisibility(View.VISIBLE);
                    upld_udc_layout.setVisibility(View.GONE);
                    upld_udc_succss.setVisibility(View.VISIBLE);
                    udc_save_button.setText("Update");
                    mSelectedUDCStatus = 0;
                    upload_udc_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerUDCDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void DealerBUSINESSPROOFDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;

        //  Log.e("UDC FileName ", mBussinessProofFile.toString());

        if (mSelectedBussinessProofStatus == 1) {
            idFile = mBussinessProofFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("BUSINESS_PROOF", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerBUSINESSPROOFDoc_MODEL> call = restApis.DealerBUSINESSPROOFDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerBUSINESSPROOFDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerBUSINESSPROOFDoc_MODEL> call, Response<DealerBUSINESSPROOFDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    dropup2_business_proof.setVisibility(View.GONE);
                    dropdown_business_proof.setVisibility(View.VISIBLE);
                    upld_business_layout.setVisibility(View.GONE);
                    upld_busines_succss.setVisibility(View.VISIBLE);
                    save_business.setText("Update");
                    mSelectedBussinessProofStatus = 0;
                    upload_business_success = 1;

                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerBUSINESSPROOFDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }
    private void DealerDISB_BANKDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
        //       Log.e("UDC FileName ", mDisburseBankFile.toString());

        if (mSelectedDisburseBankStatus == 1) {
            idFile = mDisburseBankFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("bankimage", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerDISB_BANKDoc_MODEL> call = restApis.DealerDISB_BANKDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerDISB_BANKDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerDISB_BANKDoc_MODEL> call, Response<DealerDISB_BANKDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    disb_bank_dropup2.setVisibility(View.GONE);
                    disb_bank_dropdown.setVisibility(View.VISIBLE);
                    upld_disburse_layout.setVisibility(View.GONE);
                    upld_disb_bank_succss.setVisibility(View.VISIBLE);
                    disburse_bank_save_button.setText("Update");
                    mSelectedDisburseBankStatus = 0;
                    upload_disb_bank_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerDISB_BANKDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerGSTDoc() {
        String musercode = "47436";

        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
        //    Log.e("UDC FileName ", mGSTFile.toString());

        if (mSelectedGSTStatus == 1) {
            idFile = mGSTFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("GST", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerGSTDoc_MODEL> call = restApis.DealerGSTDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerGSTDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerGSTDoc_MODEL> call, Response<DealerGSTDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    Gst_dropup2.setVisibility(View.GONE);
                    GST_DROPDOWN.setVisibility(View.VISIBLE);
                    upld_Gst_layout.setVisibility(View.GONE);
                    upld_Gst_succss.setVisibility(View.VISIBLE);
                   Gst_save_button.setText("Update");
                   mSelectedGSTStatus = 0;
                   upload_gst_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerGSTDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void SelectImageDailog() {

        dialogCondition.setContentView(R.layout.business_dailog);
        CameraButton = (ImageView) dialogCondition.findViewById(R.id.camera_button);
        GalleryButton = (ImageView) dialogCondition.findViewById(R.id.gallery_button);
        PdfButton = (ImageView) dialogCondition.findViewById(R.id.pdf_button);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
                dialogCondition.dismiss();
            }
        });
        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               choosePhotoFromGallary();
                dialogCondition.dismiss();
            }
        });
        PdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDFSelector();
                dialogCondition.dismiss();
            }
        });



    }
    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    private void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }
    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                    contentURI);
                     if (x == 5) {
                        upld_business_buton.setImageBitmap(bitmap);
                        upld_business_hint.setVisibility(View.GONE);
                         business_name.setVisibility(View.GONE);
                         upld_business_buton.getLayoutParams().height = 300;
                         upld_business_buton.getLayoutParams().width = 300;
                         upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Business_valid = 1;
                        mSelectedBussinessProofStatus = 0;

                    }else if (x == 6) {
                        upld_udc_buton.setImageBitmap(bitmap);
                        upld_udc_hint.setVisibility(View.GONE);
                        udc_name.setVisibility(View.GONE);
                         upld_udc_buton.getLayoutParams().height = 300;
                         upld_udc_buton.getLayoutParams().width = 300;
                         upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        UDC_Valid = 1;
                        mSelectedUDCStatus = 0;

                    }else if (x == 7) {
                        upld_agreement_buton.setImageBitmap(bitmap);
                        upld_agreement_hint.setVisibility(View.GONE);
                        agreement_name.setVisibility(View.GONE);
                         upld_agreement_buton.getLayoutParams().height = 300;
                         upld_agreement_buton.getLayoutParams().width = 300;
                         upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Agreement_Valid = 1;
                        mSelectedAgreementStatus = 0;

                    }else if (x == 8) {
                        upld_escrow_buton.setImageBitmap(bitmap);
                        upld_escrow_hint.setVisibility(View.GONE);
                        escrow_name.setVisibility(View.GONE);
                         upld_escrow_buton.getLayoutParams().height = 300;
                         upld_escrow_buton.getLayoutParams().width = 300;
                         upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Escrow_Valid = 1;
                        mSelectedEscrowStatus = 0;

                    }else if (x == 9) {
                        upld_disburse_bank_buton.setImageBitmap(bitmap);
                        upld_disb_bank_hint.setVisibility(View.GONE);
                        disb_bank_name.setVisibility(View.GONE);
                         upld_disburse_bank_buton.getLayoutParams().height = 300;
                         upld_disburse_bank_buton.getLayoutParams().width = 300;
                         upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Disb_Bank_Valid = 1;
                        mSelectedDisburseBankStatus = 0;

                    }
                    else if (x == 14) {
                        upld_Gst_buton.setImageBitmap(bitmap);
                        upld_Gst_hint.setVisibility(View.GONE);
                        Gst_name.setVisibility(View.GONE);
                         upld_Gst_buton.getLayoutParams().height = 300;
                         upld_Gst_buton.getLayoutParams().width = 300;
                         upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        GST_Valid = 1;
                        mSelectedGSTStatus = 0;

                    }
                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (y==5){
                upld_business_buton.setImageBitmap(thumbnail);
                upld_business_hint.setVisibility(View.GONE);
                business_name.setVisibility(View.GONE);
                upld_business_buton.getLayoutParams().height = 300;
                upld_business_buton.getLayoutParams().width = 300;
                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Business_valid = 1;
                mSelectedBussinessProofStatus = 0;
            }else if (y==6){
                upld_udc_buton.setImageBitmap(thumbnail);
                upld_udc_hint.setVisibility(View.GONE);
                udc_name.setVisibility(View.GONE);
                upld_udc_buton.getLayoutParams().height = 300;
                upld_udc_buton.getLayoutParams().width = 300;
                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                UDC_Valid = 1;
                mSelectedUDCStatus = 0;
            }else if (y==7){
                upld_agreement_buton.setImageBitmap(thumbnail);
                upld_agreement_hint.setVisibility(View.GONE);
                agreement_name.setVisibility(View.GONE);
                upld_agreement_buton.getLayoutParams().height = 300;
                upld_agreement_buton.getLayoutParams().width = 300;
                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Agreement_Valid = 1;
                mSelectedAgreementStatus = 0;
            }else if (y==8){
                upld_escrow_buton.setImageBitmap(thumbnail);
                upld_escrow_hint.setVisibility(View.GONE);
                escrow_name.setVisibility(View.GONE);
                upld_escrow_buton.getLayoutParams().height = 300;
                upld_escrow_buton.getLayoutParams().width = 300;
                upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Escrow_Valid = 1;
                mSelectedEscrowStatus = 0;
            }else if (y==9){
                upld_disburse_bank_buton.setImageBitmap(thumbnail);
                upld_disb_bank_hint.setVisibility(View.GONE);
                disb_bank_name.setVisibility(View.GONE);
                upld_disburse_bank_buton.getLayoutParams().height = 300;
                upld_disburse_bank_buton.getLayoutParams().width = 300;
                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Disb_Bank_Valid = 1;
                mSelectedDisburseBankStatus = 0;
            }else if (y==14){
                upld_Gst_buton.setImageBitmap(thumbnail);
                upld_Gst_hint.setVisibility(View.GONE);
                Gst_name.setVisibility(View.GONE);
                upld_Gst_buton.getLayoutParams().height = 300;
                upld_Gst_buton.getLayoutParams().width = 300;
                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                GST_Valid = 1;
                mSelectedGSTStatus = 0;
            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
          Log.e("SelectingImage","ppppppDf");
            if (mSelectedBussinessProofStatus == 1) {
                Business_valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                Log.e("PdfSelected","PDFFFFFFFFF");
                upld_business_hint.setVisibility(View.GONE);
                Log.e("HideMassage","OKKKK");
            } else if (mSelectedUDCStatus == 1) {
                Log.e("yyy","uuu666");
                UDC_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                Log.e("yyy","0000000000");
                upld_udc_hint.setVisibility(View.GONE);
            }else if (mSelectedAgreementStatus == 1) {
                Agreement_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_agreement_hint.setVisibility(View.GONE);
            }else if (mSelectedEscrowStatus == 1) {
                Escrow_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_escrow_hint.setVisibility(View.GONE);
            }else if (mSelectedDisburseBankStatus == 1) {
                Disb_Bank_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_disb_bank_hint.setVisibility(View.GONE);
            }else if (mSelectedGSTStatus == 1) {
                GST_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_Gst_hint.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public String saveImage(Bitmap myBitmap) {
        Log.e("TAG", "File Saved1");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Log.e("TAG", "File Saved2");
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Log.e("TAG", "File Saved3");
        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);
        Log.e("Full Path", Environment.getExternalStorageDirectory() +
                IMAGE_DIRECTORY);
        Log.e("TAG", "File Saved4");
        // have the object build the directory structure, if needed.
        Log.e("Boolean Value", Boolean.toString(wallpaperDirectory.exists()));
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        Log.e("TAG", "File Saved6");
        try {
            Log.e("TAG", "File Saved7");
            File f = new
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");
            Log.e("TAG", "File Saved8");
            f.createNewFile();
            Log.e("TAG", "File Saved9");
            FileOutputStream fo = new FileOutputStream(f);
            Log.e("TAG", "File Saved10");

            fo.write(bytes.toByteArray());
            Log.e("TAG", "File Saved11");
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            Log.e("TAG", "File Saved12");
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            SharedPref.saveStringInSharedPref(AppConstants.ML_LOAN_IMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
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
        if (mSelectedBussinessProofStatus == 1) {
            Log.e("BussinessProofStatus", mFile.toString());
            mBussinessProofFile = mFile;
            business_name.setText(Pdf_Name);
            business_name.setVisibility(View.VISIBLE);
            upld_business_buton.setImageResource(R.drawable.c3);
            upld_business_buton.getLayoutParams().height = 150;
            upld_business_buton.getLayoutParams().width = 150;
            upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedBussinessProofStatus = 0;
        } else if (mSelectedUDCStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mUDCFile = mFile;
            udc_name.setText(Pdf_Name);
            udc_name.setVisibility(View.VISIBLE);
            upld_udc_buton.setImageResource(R.drawable.c3);
            upld_udc_buton.getLayoutParams().height = 150;
            upld_udc_buton.getLayoutParams().width = 150;
            upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedAgreementStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mAgreementFile = mFile;
            agreement_name.setText(Pdf_Name);
            agreement_name.setVisibility(View.VISIBLE);
            upld_agreement_buton.setImageResource(R.drawable.c3);
            upld_agreement_buton.getLayoutParams().height = 150;
            upld_agreement_buton.getLayoutParams().width = 150;
            upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedEscrowStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mEscrowFile = mFile;
            escrow_name.setText(Pdf_Name);
            escrow_name.setVisibility(View.VISIBLE);
            upld_escrow_buton.setImageResource(R.drawable.c3);

            upld_escrow_buton.getLayoutParams().height = 150;
            upld_escrow_buton.getLayoutParams().width = 150;
            upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedDisburseBankStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mDisburseBankFile = mFile;

            disb_bank_name.setText(Pdf_Name);
            disb_bank_name.setVisibility(View.VISIBLE);

            upld_disburse_bank_buton.setImageResource(R.drawable.c3);

            upld_disburse_bank_buton.getLayoutParams().height = 150;
            upld_disburse_bank_buton.getLayoutParams().width = 150;
            upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);

//            mSelectedUDCStatus = 0;
        }else if (mSelectedGSTStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mGSTFile = mFile;
            Gst_name.setText(Pdf_Name);
            Gst_name.setVisibility(View.VISIBLE);
            upld_Gst_buton.setImageResource(R.drawable.c3);
            upld_Gst_buton.getLayoutParams().height = 150;
            upld_Gst_buton.getLayoutParams().width = 150;
            upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }
}