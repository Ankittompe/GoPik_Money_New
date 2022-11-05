package com.qts.gopik_money.Supply_Chain;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.BANK_DOCUMENT_MODEL;
import com.qts.gopik_money.Model.DealerAGREEMENTDoc_MODEL;
import com.qts.gopik_money.Model.DealerBUSINESSPROOFDoc_MODEL;
import com.qts.gopik_money.Model.DealerDISB_BANKDoc_MODEL;
import com.qts.gopik_money.Model.DealerDocDeleteModel;
import com.qts.gopik_money.Model.DealerESCROWDoc_MODEL;
import com.qts.gopik_money.Model.DealerGSTDoc_MODEL;
import com.qts.gopik_money.Model.DealerUDCDoc_MODEL;
import com.qts.gopik_money.Pojo.BankDocumentAddItem;
import com.qts.gopik_money.Pojo.DealerDocDelete_POJO;

import com.qts.gopik_money.Pojo.ShopkeeperdoctoFinance_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.BankRemoveListener;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Supplychain_Adapter.BankDocumentAdapter;
import com.qts.gopik_money.Utils.BankDocumentAddListener;
import com.qts.gopik_money.Utils.CheckPDFOrImage;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.SetImageListener;
import com.qts.gopik_money.Utils.TextViewUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessDetails_Activity extends AppCompatActivity implements PickiTCallbacks {
    FileOutputStream fo;
    File mBankPdf = null;
    SetImageListener setImageListener;
    ArrayList<BankDocumentAddItem> mBankDocumentList = new ArrayList<>();
    Call<DealerDocDeleteModel> mDealerDocDeleteCall;
    BankDocumentAddListener bankDocumentAddListener;
    BankRemoveListener bankRemoveListener;
    ImageView dropdown_business_proof;
    ImageView dropdown_udc;
    String mMultipartFormData = "multipart/form-data";
    ImageView agreement_dropdown;
    ImageView GST_DROPDOWN;
    ImageView escrow_dropdown;
    ImageView disb_bank_dropdown;
    Integer Business_valid = 0;
    Integer UDC_Valid = 0;
    Integer Agreement_Valid = 0;
    Integer Escrow_Valid = 0;
    public int PDF_VALID = 0;
    Integer Disb_Bank_Valid = 0;
    Integer mBankDocumentValid = 0;
    public int x = 0;
    public int y = 0;
    Integer GST_Valid = 0;
    PickiT pickiT;
    String Pdf_Name;
    String mShopKeeper = "Shop Keeper";
    String mDealer = "Dealer";
    String mSubDealer = "SubDealer";
    Integer upload_business_success = 0;
    Integer upload_udc_success = 0;
    Integer upload_agreement_success = 0;
    Integer upload_escrow_success = 0;
    Integer upload_gst_success = 0;
    Integer upload_disb_bank_success = 0;

    String mUpdate = "Update";
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView escrow_save_button;
    TextView disburse_bank_save_button;
    TextView Gst_save_button;
    TextView agreement_save_button;
    TextView save_business;
    TextView udc_save_button;
    ImageView dropup2_business_proof;
    ImageView dropup2_udc;
    ImageView agreement_dropup2;
    ImageView Gst_dropup2;
    ImageView escrow_dropup2;
    ImageView disb_bank_dropup2;
    private int GALLERY = 1;
    private int CAMERA = 2;
    LinearLayout upld_business_layout;
    LinearLayout upld_udc_layout;
    LinearLayout upld_agreement_layout;
    ImageView CameraButton;
    ImageView GalleryButton;
    ImageView PdfButton;
    LinearLayout upld_escrow_layout;
    LinearLayout upld_disburse_layout;
    LinearLayout upld_Gst_layout;
    private Dialog dialogCondition;
    ImageView arrow;
    ImageView hometoolbar;
    ImageView upld_business_buton;
    ImageView upld_udc_buton;
    ImageView upld_agreement_buton;
    ImageView upld_escrow_buton;
    ImageView upld_disburse_bank_buton;
    ImageView upld_Gst_buton;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    File mBussinessProofFile;
    int mSelectedBussinessProofStatus = 0;
    //UDC
    File mUDCFile;
    int mSelectedUDCStatus = 0;
    //Agreement
    File mAgreementFile;
    int mSelectedAgreementStatus = 0;
    //Escrow
    File mEscrowFile;
    int mSelectedEscrowStatus = 0;
    //DisburseBank
    File mDisburseBankFile;
    int mSelectedDisburseBankStatus = 0;
    CustPrograssbar custPrograssbar;
    TextView Gst_name;
    TextView business_name;
    TextView udc_name;
    TextView agreement_name;
    TextView escrow_name;
    TextView disb_bank_name;
    //GST
    File mGSTFile;
    int mSelectedGSTStatus = 0;
    TextView btsend;
    ImageView business_info_button;
    ImageView util_info_button;
    ImageView agrement_info_button;
    ImageView bank_info_button;
    ImageView gst_info_button;
    ImageView upld_busines_succss;
    ImageView upld_udc_succss;
    ImageView upld_agreement_succss;
    ImageView upld_ecsrow_succss;
    ImageView upld_disb_bank_succss;
    ImageView upld_Gst_succss;
    Call<DealerBUSINESSPROOFDoc_MODEL> mBUSINESSPROOFDocCall;
    Call<DealerGSTDoc_MODEL> mGSTDocCall;
    Call<DealerAGREEMENTDoc_MODEL> mAGREEMENTDocCall;
    Call<DealerUDCDoc_MODEL> mUDCDocCall;
    Call<DealerDISB_BANKDoc_MODEL> mBANKDocCall;
    Call<BANK_DOCUMENT_MODEL> mBankDocumentCall;
    TextView Ok_button;
    TextView business_proof_tv;
    TextView udc_tv;
    TextView agreement_tv;
    TextView disburse_bank_tv;
    TextView Gst_tv;
    TextView upld_business_hint;
    TextView upld_udc_hint;
    TextView upld_agreement_hint;
    TextView upld_escrow_hint;
    TextView upld_disb_bank_hint;
    TextView upld_Gst_hint;
    private Context mContext = BusinessDetails_Activity.this;
    TextView mDeleteButton;
    TextView mCanceleButton;
    String mUserType;
    String image;
    Button business_view;
    Button udc_view;
    Button Gst_view;
    Button agreement_view;
    Button disb_bank_view;
    Boolean valuepdf = false;

    ImageView mAddMoreButton;
    ImageView mUploadOtherbankButon1;
    RecyclerView bankDocumentRecyclerview;
    BankDocumentAdapter bankDocumentAdapter;

    ImageButton mDeleteBusinessButton;
    ImageButton mDeleteUtilityButton;
    ImageButton mDeleteAgreementButton;
    ImageButton mDeleteGstButton;
    Integer mBankDocumentPos = 0;
    Boolean mSelectDialogClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);

        mDeleteBusinessButton = findViewById(R.id.delete_businessproof_button);
        mDeleteUtilityButton = findViewById(R.id.delete_utility_button);
        mDeleteAgreementButton = findViewById(R.id.delete_agreement_button);
        mDeleteGstButton = findViewById(R.id.delete_gst_button);
        bankDocumentRecyclerview = findViewById(R.id.bank_document_recyclerview);
        mAddMoreButton = findViewById(R.id.add_more_button);
        business_view = findViewById(R.id.business_view);
        udc_view = findViewById(R.id.udc_view);
        agreement_view = findViewById(R.id.agreement_view);
        disb_bank_view = findViewById(R.id.disb_bank_view);
        Gst_view = findViewById(R.id.Gst_view);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        business_info_button = findViewById(R.id.business_info_button);
        util_info_button = findViewById(R.id.util_info_button);
        agrement_info_button = findViewById(R.id.agrement_info_button);
        bank_info_button = findViewById(R.id.bank_info_button);
        gst_info_button = findViewById(R.id.gst_info_button);

        business_proof_tv = findViewById(R.id.business_proof_tv);

        business_proof_tv.append(TextViewUtils.getColoredString("5. Upload Business Proof", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.black)));
        business_proof_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.red)));

        udc_tv = findViewById(R.id.udc_tv);
        udc_tv.append(TextViewUtils.getColoredString("6. Upload Incorporation Certificate", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.black)));
        udc_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.red)));

        agreement_tv = findViewById(R.id.agreement_tv);
        agreement_tv.append(TextViewUtils.getColoredString("7. Upload OEM Dealer Certificate", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.black)));


        disburse_bank_tv = findViewById(R.id.disburse_bank_tv);
        disburse_bank_tv.append(TextViewUtils.getColoredString("9.Upload Bank Statement", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.black)));
        disburse_bank_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.red)));

        Gst_tv = findViewById(R.id.Gst_tv);
        Gst_tv.append(TextViewUtils.getColoredString("10. Upload GST Certificate", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.black)));
        Gst_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(BusinessDetails_Activity.this, R.color.red)));

        Gst_name = findViewById(R.id.Gst_name);
        business_name = findViewById(R.id.business_name);
        udc_name = findViewById(R.id.udc_name);
        agreement_name = findViewById(R.id.agreement_name);
        escrow_name = findViewById(R.id.escrow_name);
        disb_bank_name = findViewById(R.id.disb_bank_name);

        btsend = findViewById(R.id.btsend);
        custPrograssbar = new CustPrograssbar();
        custPrograssbar.prograssCreate(this);
        pickiT = new PickiT(BusinessDetails_Activity.this, this, BusinessDetails_Activity.this);
        //Save button
        save_business = (TextView) findViewById(R.id.save_business);
        udc_save_button = (TextView) findViewById(R.id.udc_save_button);
        agreement_save_button = (TextView) findViewById(R.id.agreement_save_button);
        escrow_save_button = (TextView) findViewById(R.id.escrow_save_button);
        disburse_bank_save_button = (TextView) findViewById(R.id.disburse_bank_save_button);
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

        business_info_button.setOnClickListener(v -> BusinessInfoDailog());
        util_info_button.setOnClickListener(v -> UtilInfoDailog());
        agrement_info_button.setOnClickListener(v -> AgreementInfoDailog());
        bank_info_button.setOnClickListener(v -> BankInfoDailog());
        gst_info_button.setOnClickListener(v -> GstInfoDailog());


        mDeleteBusinessButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(mContext);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v1 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE, "BUSINESS_PROOF", getApplicationContext());
                DealerDocDelete();
                upload_business_success = 0;
                dialogCondition.dismiss();

            });
            mCanceleButton.setOnClickListener(v16 -> dialogCondition.dismiss());

            dialogCondition.show();

        });
        mDeleteUtilityButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(mContext);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v18 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE, "UDC", getApplicationContext());
                DealerDocDelete();
                upload_udc_success = 0;

                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v17 -> dialogCondition.dismiss());

            dialogCondition.show();

        });
        mDeleteAgreementButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(mContext);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v13 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE, "AGREEMENT", getApplicationContext());
                DealerDocDelete();
                upload_agreement_success = 0;
                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v12 -> dialogCondition.dismiss());

            dialogCondition.show();

        });
        mDeleteGstButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(mContext);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v15 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE, "GST", getApplicationContext());
                DealerDocDelete();
                upload_gst_success = 0;
                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v14 -> dialogCondition.dismiss());
            dialogCondition.show();

        });
        arrow.setOnClickListener(v -> {

            if (mUserType.equals(mShopKeeper)) {
                Intent intentshopkeeper = new Intent(BusinessDetails_Activity.this, PersonalDetails_Activity.class);

                startActivity(intentshopkeeper);

            } else if (mUserType.equals(mDealer)) {
                Intent intentdealer = new Intent(BusinessDetails_Activity.this, PersonalDetails_Activity.class);

                startActivity(intentdealer);
            } else {
                Intent intentdealer = new Intent(BusinessDetails_Activity.this, PersonalDetails_Activity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intentdealer);
            }

        });
        hometoolbar.setOnClickListener(v -> {

            if (mUserType.equals(mShopKeeper)) {
                Intent intentshopkeeper = new Intent(BusinessDetails_Activity.this, HomeShopkeeper.class);
                intentshopkeeper.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                startActivity(intentshopkeeper);

            } else if (mUserType.equals(mDealer)) {
                Intent intentdealer = new Intent(BusinessDetails_Activity.this, MainActivity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intentdealer);
            } else {
                Intent intentdealer = new Intent(BusinessDetails_Activity.this, MainActivity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intentdealer);
            }

        });
        ////Business Dropdown/Dropup
        dropdown_business_proof.setOnClickListener(v -> {
            dropdown_business_proof.setVisibility(View.GONE);
            dropup2_business_proof.setVisibility(View.VISIBLE);
            upld_business_layout.setVisibility(View.VISIBLE);

        });
        dropup2_business_proof.setOnClickListener(v -> {
            dropup2_business_proof.setVisibility(View.GONE);
            dropdown_business_proof.setVisibility(View.VISIBLE);
            upld_business_layout.setVisibility(View.GONE);

        });

        ////UDC Dropdown/Dropup
        dropdown_udc.setOnClickListener(v -> {
            dropdown_udc.setVisibility(View.GONE);
            dropup2_udc.setVisibility(View.VISIBLE);
            upld_udc_layout.setVisibility(View.VISIBLE);

        });
        dropup2_udc.setOnClickListener(v -> {
            dropup2_udc.setVisibility(View.GONE);
            dropdown_udc.setVisibility(View.VISIBLE);
            upld_udc_layout.setVisibility(View.GONE);

        });

        ////Agreement Dropdown/Dropup
        agreement_dropdown.setOnClickListener(v -> {
            agreement_dropdown.setVisibility(View.GONE);
            agreement_dropup2.setVisibility(View.VISIBLE);
            upld_agreement_layout.setVisibility(View.VISIBLE);

        });
        agreement_dropup2.setOnClickListener(v -> {
            agreement_dropup2.setVisibility(View.GONE);
            agreement_dropdown.setVisibility(View.VISIBLE);
            upld_agreement_layout.setVisibility(View.GONE);

        });

        ////Escrow Dropdown/Dropup
        escrow_dropdown.setOnClickListener(v -> {
            escrow_dropdown.setVisibility(View.GONE);
            escrow_dropup2.setVisibility(View.VISIBLE);
            upld_escrow_layout.setVisibility(View.VISIBLE);

        });
        escrow_dropup2.setOnClickListener(v -> {
            escrow_dropup2.setVisibility(View.GONE);
            escrow_dropdown.setVisibility(View.VISIBLE);
            upld_escrow_layout.setVisibility(View.GONE);

        });
        ////Disburse Dropdown/Dropup
        disb_bank_dropdown.setOnClickListener(v -> {
            disb_bank_dropdown.setVisibility(View.GONE);
            disb_bank_dropup2.setVisibility(View.VISIBLE);
            upld_disburse_layout.setVisibility(View.VISIBLE);

        });
        disb_bank_dropup2.setOnClickListener(v -> {
            disb_bank_dropup2.setVisibility(View.GONE);
            disb_bank_dropdown.setVisibility(View.VISIBLE);
            upld_disburse_layout.setVisibility(View.GONE);

        });
        ////GST Dropdown/Dropup
        GST_DROPDOWN.setOnClickListener(v -> {
            GST_DROPDOWN.setVisibility(View.GONE);
            Gst_dropup2.setVisibility(View.VISIBLE);
            upld_Gst_layout.setVisibility(View.VISIBLE);

        });
        Gst_dropup2.setOnClickListener(v -> {
            Gst_dropup2.setVisibility(View.GONE);
            GST_DROPDOWN.setVisibility(View.VISIBLE);
            upld_Gst_layout.setVisibility(View.GONE);

        });
        //Upload BusinessProof
        upld_business_buton.setOnClickListener(v -> {

            SelectImageDailog();
            x = 5;
            y = 5;
            PDF_VALID = 1;
            mSelectedBussinessProofStatus = 1;


        });
        upld_udc_buton.setOnClickListener(v -> {

            SelectImageDailog();
            x = 6;
            y = 6;
            PDF_VALID = 2;
            mSelectedUDCStatus = 1;


        });

        upld_agreement_buton.setOnClickListener(v -> {
            SelectImageDailog();
            x = 7;
            y = 7;
            PDF_VALID = 3;
            mSelectedAgreementStatus = 1;

        });

        upld_escrow_buton.setOnClickListener(v -> {

            SelectImageDailog();
            x = 8;
            y = 8;
            PDF_VALID = 4;
            mSelectedEscrowStatus = 1;


        });
        upld_disburse_bank_buton.setOnClickListener(v -> {

            SelectImageDailog();
            x = 9;
            y = 9;
            PDF_VALID = 5;
            mSelectedDisburseBankStatus = 1;

        });

        upld_Gst_buton.setOnClickListener(v -> {

            SelectImageDailog();
            x = 14;
            y = 14;
            PDF_VALID = 10;
            mSelectedGSTStatus = 1;
        });

        save_business.setOnClickListener(v -> {
            if (Business_valid != 1) {
                upld_business_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                DealerBUSINESSPROOFDoc(true);


            }


        });
        udc_save_button.setOnClickListener(v -> {
            if (UDC_Valid != 1) {

                upld_udc_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);

                DealerUDCDoc(true);
            }

        });
        agreement_save_button.setOnClickListener(v -> {
            if (Agreement_Valid != 1) {
                upld_agreement_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                DealerAGREEMENTDoc(true);
            }

        });
        escrow_save_button.setOnClickListener(v -> {
            if (Escrow_Valid != 1) {
                upld_escrow_hint.setVisibility(View.VISIBLE);
            } else {
               /* escrow_dropup2.setVisibility(View.GONE);
                escrow_dropdown.setVisibility(View.VISIBLE);
                upld_escrow_layout.setVisibility(View.GONE);
                upld_ecsrow_succss.setVisibility(View.VISIBLE);*/
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                Toast.makeText(BusinessDetails_Activity.this, "Image Upload Sucsessfully!", Toast.LENGTH_SHORT).show();
                DealerESCROWDoc();
            }

        });
        disburse_bank_save_button.setOnClickListener(v -> {
            /*if (Disb_Bank_Valid != 1) {
                upld_disb_bank_hint.setVisibility(View.VISIBLE);
            } else {

               *//* disb_bank_dropup2.setVisibility(View.GONE);
                disb_bank_dropdown.setVisibility(View.VISIBLE);
                upld_disburse_layout.setVisibility(View.GONE);
                upld_disb_bank_succss.setVisibility(View.VISIBLE);*//*
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                Toast.makeText(BusinessDetails_Activity.this, "Image Upload Successfully!", Toast.LENGTH_SHORT).show();
                DealerDISB_BANKDoc(true);

               // getBankDocument();
            }

*/
            getBankDocument(mBankDocumentList);
            Log.e("imagearraylist", "" + mBankDocumentList.size());
        });

        Gst_save_button.setOnClickListener(v -> {
            if (GST_Valid != 1) {
                upld_Gst_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(BusinessDetails_Activity.this);
                DealerGSTDoc(true);
            }

        });

        btsend.setOnClickListener(v -> {

            if (upload_business_success != 1) {
                Toast.makeText(BusinessDetails_Activity.this, "Please upload a  Business Document!", Toast.LENGTH_SHORT).show();
            } else if (upload_udc_success != 1) {
                Toast.makeText(BusinessDetails_Activity.this, "Please Upload an Utility Bill", Toast.LENGTH_SHORT).show();
            } else if (upload_disb_bank_success != 1) {
                Toast.makeText(BusinessDetails_Activity.this, "Please Upload a Bank Statement !", Toast.LENGTH_SHORT).show();
            } else if (upload_gst_success != 1) {
                Toast.makeText(BusinessDetails_Activity.this, "Please Upload a Gst Document !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BusinessDetails_Activity.this, "All Documents Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BusinessDetails_Activity.this, TransactionDetails_Activity.class);
                startActivity(intent);
            }
        });


        DealerBUSINESSPROOFDoc(false);
        DealerGSTDoc(false);
        DealerAGREEMENTDoc(false);
        DealerUDCDoc(false);
        DealerDISB_BANKDoc(false);


        setImageListener = (position) -> {
            mBankDocumentValid = 1;
            mBankDocumentPos = position;
            mSelectDialogClicked = true;
            BusinessDetails_Activity.this.SelectImageDailog();
        };
        bankRemoveListener = (position -> {
            mBankDocumentList.remove(position);
            bankDocumentAdapter = new BankDocumentAdapter(BusinessDetails_Activity.this, mBankDocumentList, bankDocumentAddListener, bankRemoveListener, setImageListener);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
            bankDocumentRecyclerview.setLayoutManager(mLinearLayoutManager);
            bankDocumentRecyclerview.setItemAnimator(new DefaultItemAnimator());
            bankDocumentRecyclerview.setAdapter(bankDocumentAdapter);
            bankDocumentAdapter.notifyItemRemoved(position);
        });
        mAddMoreButton.setOnClickListener(v -> {
            if (mBankDocumentList.size() < 6) {
                mBankDocumentList.add(new BankDocumentAddItem(null, null, null));
                bankDocumentAdapter.notifyItemInserted(mBankDocumentList.size());
            } else {
                Toast.makeText(mContext, "Max Document's should be 6", Toast.LENGTH_SHORT).show();
            }
        });
        addBankDocument();
        bankDocumentAddListener = new BankDocumentAddListener() {
            @Override
            public void onClick(int position, BankDocumentAddItem bankDocumentAddItem) {
                mBankDocumentList.set(position, bankDocumentAddItem);
            }
        };

    }


    private void getBankDocument(ArrayList<BankDocumentAddItem> mBankDocumentList) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part mBankDocument1 = null;
        RequestBody mFile2;
        MultipartBody.Part mBankDocument2 = null;
        RequestBody mFile3;
        MultipartBody.Part mBankDocument3 = null;
        RequestBody mFile4;
        MultipartBody.Part mBankDocument4 = null;
        RequestBody mFile5;
        MultipartBody.Part mBankDocument5 = null;
        RequestBody mFile6;
        MultipartBody.Part mBankDocument6 = null;
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mBankDocumentList.size() == 1) {
            File mFileImage1 = new File(saveImage(mBankDocumentList.get(0).getBankdocument1()));
            mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage1);
            mBankDocument1 = MultipartBody.Part.createFormData("bankimage", mFileImage1.getName(), mFile1);
            mBankDocumentCall = restApis.BankDocumentMethod(user_code, mBankDocument1, null, null, null, null, null);

        }
        if (mBankDocumentList.size() == 2) {
            File mFileImage1 = new File(saveImage(mBankDocumentList.get(0).getBankdocument1()));
            mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage1);
            mBankDocument1 = MultipartBody.Part.createFormData("bankimage", mFileImage1.getName(), mFile1);

            File mFileImage2 = new File(saveImage(mBankDocumentList.get(1).getBankdocument1()));
            mFile2 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage2);
            mBankDocument2 = MultipartBody.Part.createFormData("bankimage1", mFileImage2.getName(), mFile2);
            mBankDocumentCall = restApis.BankDocumentMethod(user_code, mBankDocument1, mBankDocument2, null, null, null, null);
           /*   File mFileImage3=new File(saveImage(mBankDocumentList.get(2).getBankdocument1()));
              mFile3 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage3);
              mBankDocument3 = MultipartBody.Part.createFormData("bankimage2", mFileImage3.getName(), mFile3);

              File mFileImage4=new File(saveImage(mBankDocumentList.get(3).getBankdocument1()));
              mFile4 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage4);
              mBankDocument4 = MultipartBody.Part.createFormData("bankimage3", mFileImage4.getName(), mFile4);

              File mFileImage5=new File(saveImage(mBankDocumentList.get(4).getBankdocument1()));
              mFile5 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage5);
              mBankDocument5 = MultipartBody.Part.createFormData("bankimage4", mFileImage5.getName(), mFile5);

              File mFileImage6=new File(saveImage(mBankDocumentList.get(5).getBankdocument1()));
              mFile6 = RequestBody.create(MediaType.parse(mMultipartFormData), mFileImage6);
              mBankDocument6 = MultipartBody.Part.createFormData("bankimage5", mFileImage6.getName(), mFile6);*/

        }


        if (mUserType.equals(mDealer)) {
               /*     mBankDocumentCall = restApis.BankDocumentMethod(user_code, mBankDocument1,mBankDocument2
                    ,mBankDocument3,mBankDocument4,mBankDocument5,mBankDocument6)*/
            ;

            mBankDocumentCall.enqueue(new Callback<BANK_DOCUMENT_MODEL>() {
                @Override
                public void onResponse(Call<BANK_DOCUMENT_MODEL> call, Response<BANK_DOCUMENT_MODEL> response) {
                    if (response.body() != null && response.body().getCode().equals("200")) {

//                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        String mDisbBankURL = response.body().getPayload().getSelfie();
//                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mDisbBankURL);
//                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
//                            upld_disb_bank_succss.setVisibility(View.GONE);
//                        } else {
//                            upld_disb_bank_succss.setVisibility(View.VISIBLE);
//                        }
//                        if (valuepdf) {
//
//                            File mFile = new File(mDisbBankURL);
//
//                            Pdf_Name = mFile.getName();
//                            disb_bank_name.setVisibility(View.VISIBLE);
//                            disb_bank_view.setVisibility(View.VISIBLE);
//                            upld_disburse_bank_buton.setImageResource(R.drawable.c3);
//                            disb_bank_name.setText(Pdf_Name);
//                            disb_bank_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mDisbBankURL));
//                        } else {
//                            try {
//                                URL newurl = new URL(mDisbBankURL);
//                                Bitmap mIcon_val;
//                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//                                upld_disburse_bank_buton.setImageBitmap(mIcon_val);
//                                upld_disburse_bank_buton.getLayoutParams().height = 300;
//                                upld_disburse_bank_buton.getLayoutParams().width = 300;
//                                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }

                @Override
                public void onFailure(Call<BANK_DOCUMENT_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }


    private void DealerDocDelete() {

        custPrograssbar.prograssCreate(this);
        DealerDocDelete_POJO pojo = new DealerDocDelete_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()), SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        if (mUserType.equals("Dealer")) {

            mDealerDocDeleteCall = restApis.DealerDocDelete(pojo);

            mDealerDocDeleteCall.enqueue(new Callback<DealerDocDeleteModel>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerDocDeleteModel> call, Response<DealerDocDeleteModel> response) {
                    if (response.body() != null && response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();

                        response.body().getMessage();
                        response.body().getPayload().getType();
                        response.body().getPayload().getUsercode();
                        switch (SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE, getApplicationContext())) {
                            case "BUSINESS_PROOF":
                                upld_business_buton.setImageResource(R.drawable.camera);
                                upld_business_buton.getLayoutParams().height = 150;
                                upld_business_buton.getLayoutParams().width = 150;
                                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteBusinessButton.setVisibility(View.GONE);
                                business_view.setVisibility(View.GONE);
                                business_name.setVisibility(View.GONE);
                                upld_busines_succss.setVisibility(View.GONE);
                                upload_business_success = 0;
                                Toast.makeText(mContext, "Business Document " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;

                            case "UDC":
                                upld_udc_buton.setImageResource(R.drawable.camera);
                                upld_udc_buton.getLayoutParams().height = 150;
                                upld_udc_buton.getLayoutParams().width = 150;
                                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteUtilityButton.setVisibility(View.GONE);
                                udc_view.setVisibility(View.GONE);
                                udc_name.setVisibility(View.GONE);
                                upld_udc_succss.setVisibility(View.GONE);
                                upload_udc_success = 0;
                                Toast.makeText(mContext, "Utility Document " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                            case "AGREEMENT":
                                upld_agreement_buton.setImageResource(R.drawable.camera);
                                upld_agreement_buton.getLayoutParams().height = 150;
                                upld_agreement_buton.getLayoutParams().width = 150;
                                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteAgreementButton.setVisibility(View.GONE);
                                agreement_view.setVisibility(View.GONE);
                                agreement_name.setVisibility(View.GONE);
                                upld_agreement_succss.setVisibility(View.GONE);
                                Toast.makeText(mContext, "OEM Dealer Certificate " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                upld_Gst_buton.setImageResource(R.drawable.camera);
                                upld_Gst_buton.getLayoutParams().height = 150;
                                upld_Gst_buton.getLayoutParams().width = 150;
                                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteGstButton.setVisibility(View.GONE);
                                Gst_name.setVisibility(View.GONE);
                                Gst_view.setVisibility(View.GONE);
                                upld_Gst_succss.setVisibility(View.GONE);
                                upload_gst_success = 0;
                                Toast.makeText(mContext, "GST Document " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;


                        }


                    } else {

                        Toast.makeText(BusinessDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }


                }


                @Override
                public void onFailure(Call<DealerDocDeleteModel> call, Throwable t) {

                    Toast.makeText(BusinessDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void addBankDocument() {
        mBankDocumentList.add(new BankDocumentAddItem(null, null, null));
        bankDocumentAdapter = new BankDocumentAdapter(BusinessDetails_Activity.this, mBankDocumentList, bankDocumentAddListener, bankRemoveListener, setImageListener);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        bankDocumentRecyclerview.setLayoutManager(mLinearLayoutManager);
        bankDocumentRecyclerview.setItemAnimator(new DefaultItemAnimator());
        bankDocumentRecyclerview.setAdapter(bankDocumentAdapter);
        bankDocumentAdapter.notifyItemInserted(mBankDocumentList.size());
    }

    private void loadDataBankDocument(ArrayList<BankDocumentAddItem> mBankDocumentList) {
        bankDocumentAdapter = new BankDocumentAdapter(BusinessDetails_Activity.this, mBankDocumentList, bankDocumentAddListener, bankRemoveListener, setImageListener);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        bankDocumentRecyclerview.setLayoutManager(mLinearLayoutManager);
        bankDocumentRecyclerview.setItemAnimator(new DefaultItemAnimator());
        bankDocumentRecyclerview.setAdapter(bankDocumentAdapter);
        bankDocumentAdapter.notifyItemInserted(mBankDocumentList.size());
        Log.e("mBankDocumentList ", ""+mBankDocumentList.get(0).getBankdocument1());
        Log.e("mBankDocumentList ", ""+mBankDocumentList.get(1).getBankdocument1());

    }

    private void DealerESCROWDoc() {

        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

        File idFile;

        if (mSelectedEscrowStatus == 1) {
            idFile = mEscrowFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("ESCROW", idFile.getName(), mFile1);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerESCROWDoc_MODEL> call = restApis.DealerESCROWDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerESCROWDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerESCROWDoc_MODEL> call, Response<DealerESCROWDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    escrow_dropup2.setVisibility(View.GONE);
                    escrow_dropdown.setVisibility(View.VISIBLE);
                    upld_escrow_layout.setVisibility(View.GONE);
                    upld_ecsrow_succss.setVisibility(View.VISIBLE);
                    escrow_save_button.setText(mUpdate);
                    mSelectedEscrowStatus = 0;
                    upload_escrow_success = 1;
                    Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DealerESCROWDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerAGREEMENTDoc(boolean mAGREEMENTDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedAgreementStatus == 1) {
            idFile = mAgreementFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("AGREEMENT", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mDealer)) {
            if (mAGREEMENTDocStatus) {
                mAGREEMENTDocCall = restApis.DealerAGREEMENTDoc(user_code, vechileDocUpload2);
            } else {
                mAGREEMENTDocCall = restApis.GetDealerAGREEMENTDoc(data);
            }
            mAGREEMENTDocCall.enqueue(new Callback<DealerAGREEMENTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAGREEMENTDoc_MODEL> call, Response<DealerAGREEMENTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        agreement_dropup2.setVisibility(View.GONE);
                        agreement_dropdown.setVisibility(View.VISIBLE);
                        upld_agreement_layout.setVisibility(View.GONE);
                        //  upld_agreement_succss.setVisibility(View.VISIBLE);
                        agreement_save_button.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_agreement_succss.setVisibility(View.GONE);
                            mDeleteAgreementButton.setVisibility(View.GONE);
                        } else {
                            upld_agreement_succss.setVisibility(View.VISIBLE);
                            mDeleteAgreementButton.setVisibility(View.VISIBLE);
                        }
                        mSelectedAgreementStatus = 0;
                        upload_agreement_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mAgreementDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mAgreementDocURL);

                        if (valuepdf) {
                            File mFile = new File(mAgreementDocURL);
                            Pdf_Name = mFile.getName();
                            agreement_name.setVisibility(View.VISIBLE);
                            agreement_view.setVisibility(View.VISIBLE);
                            upld_agreement_buton.setImageResource(R.drawable.c3);
                            agreement_name.setText(Pdf_Name);
                            mDeleteAgreementButton.setVisibility(View.VISIBLE);
                            agreement_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mAgreementDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mAgreementDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_agreement_buton.setImageBitmap(mIcon_val);
                                upld_agreement_buton.getLayoutParams().height = 300;
                                upld_agreement_buton.getLayoutParams().width = 300;
                                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteAgreementButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAGREEMENTDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mSubDealer)) {
            if (mAGREEMENTDocStatus) {
                mAGREEMENTDocCall = restApis.SubDealerAGREEMENTDoc(user_code, vechileDocUpload2);
            } else {
                mAGREEMENTDocCall = restApis.GetSubDealerAGREEMENTDoc(data);
            }
            mAGREEMENTDocCall.enqueue(new Callback<DealerAGREEMENTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAGREEMENTDoc_MODEL> call, Response<DealerAGREEMENTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        agreement_dropup2.setVisibility(View.GONE);
                        agreement_dropdown.setVisibility(View.VISIBLE);
                        upld_agreement_layout.setVisibility(View.GONE);
                        //    upld_agreement_succss.setVisibility(View.VISIBLE);
                        agreement_save_button.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_agreement_succss.setVisibility(View.GONE);
                        } else {
                            upld_agreement_succss.setVisibility(View.VISIBLE);
                        }
                        mSelectedAgreementStatus = 0;
                        upload_agreement_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mAgreementDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mAgreementDocURL);

                        if (valuepdf) {

                            File mFile = new File(mAgreementDocURL);

                            Pdf_Name = mFile.getName();
                            agreement_name.setVisibility(View.VISIBLE);
                            agreement_view.setVisibility(View.VISIBLE);
                            upld_agreement_buton.setImageResource(R.drawable.c3);
                            agreement_name.setText(Pdf_Name);
                            agreement_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mAgreementDocURL));
                        } else {
                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_agreement_buton.setImageBitmap(mIcon_val);
                                upld_agreement_buton.getLayoutParams().height = 300;
                                upld_agreement_buton.getLayoutParams().width = 300;
                                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAGREEMENTDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mAGREEMENTDocStatus) {
                mAGREEMENTDocCall = restApis.ShopkeeperAGREEMENTDoc(user_code, vechileDocUpload2);
            } else {
                mAGREEMENTDocCall = restApis.GetShopkeeperAGREEMENTDoc(data);
            }
            mAGREEMENTDocCall.enqueue(new Callback<DealerAGREEMENTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAGREEMENTDoc_MODEL> call, Response<DealerAGREEMENTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        agreement_dropup2.setVisibility(View.GONE);
                        agreement_dropdown.setVisibility(View.VISIBLE);
                        upld_agreement_layout.setVisibility(View.GONE);
                        //    upld_agreement_succss.setVisibility(View.VISIBLE);
                        agreement_save_button.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_agreement_succss.setVisibility(View.GONE);
                        } else {
                            upld_agreement_succss.setVisibility(View.VISIBLE);
                        }
                        mSelectedAgreementStatus = 0;
                        upload_agreement_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mAgreementDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mAgreementDocURL);

                        if (valuepdf) {

                            File mFile = new File(mAgreementDocURL);

                            Pdf_Name = mFile.getName();
                            agreement_name.setVisibility(View.VISIBLE);
                            agreement_view.setVisibility(View.VISIBLE);
                            upld_agreement_buton.setImageResource(R.drawable.c3);
                            agreement_name.setText(Pdf_Name);
                            agreement_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mAgreementDocURL));
                        } else {
                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_agreement_buton.setImageBitmap(mIcon_val);
                                upld_agreement_buton.getLayoutParams().height = 300;
                                upld_agreement_buton.getLayoutParams().width = 300;
                                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAGREEMENTDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void DealerUDCDoc(boolean mDealerUDCDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedUDCStatus == 1) {
            idFile = mUDCFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("UDC", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mDealer)) {
            if (mDealerUDCDocStatus) {
                mUDCDocCall = restApis.DealerUDCDoc(user_code, vechileDocUpload2);
            } else {
                mUDCDocCall = restApis.GetDealerUDCDoc(data);

            }
            mUDCDocCall.enqueue(new Callback<DealerUDCDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerUDCDoc_MODEL> call, Response<DealerUDCDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        dropup2_udc.setVisibility(View.GONE);
                        dropdown_udc.setVisibility(View.VISIBLE);
                        upld_udc_layout.setVisibility(View.GONE);
                        //    upld_udc_succss.setVisibility(View.VISIBLE);
                        udc_save_button.setText(mUpdate);
                        mSelectedUDCStatus = 0;
                        upload_udc_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_udc_succss.setVisibility(View.GONE);
                            mDeleteUtilityButton.setVisibility(View.GONE);
                        } else {
                            upld_udc_succss.setVisibility(View.VISIBLE);
                            mDeleteUtilityButton.setVisibility(View.VISIBLE);
                        }
                        String mUDCDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mUDCDocURL);

                        if (valuepdf) {

                            File mFile = new File(mUDCDocURL);

                            Pdf_Name = mFile.getName();
                            udc_name.setVisibility(View.VISIBLE);
                            udc_view.setVisibility(View.VISIBLE);
                            upld_udc_buton.setImageResource(R.drawable.c3);
                            udc_name.setText(Pdf_Name);
                            mDeleteUtilityButton.setVisibility(View.VISIBLE);
                            udc_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mUDCDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mUDCDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_udc_buton.setImageBitmap(mIcon_val);
                                upld_udc_buton.getLayoutParams().height = 300;
                                upld_udc_buton.getLayoutParams().width = 300;
                                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteUtilityButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerUDCDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mSubDealer)) {
            if (mDealerUDCDocStatus) {
                mUDCDocCall = restApis.SubDealerUDCDoc(user_code, vechileDocUpload2);
            } else {
                mUDCDocCall = restApis.GetSubDealerUDCDoc(data);
            }
            mUDCDocCall.enqueue(new Callback<DealerUDCDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerUDCDoc_MODEL> call, Response<DealerUDCDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        dropup2_udc.setVisibility(View.GONE);
                        dropdown_udc.setVisibility(View.VISIBLE);
                        upld_udc_layout.setVisibility(View.GONE);
                        //   upld_udc_succss.setVisibility(View.VISIBLE);
                        udc_save_button.setText(mUpdate);
                        mSelectedUDCStatus = 0;
                        upload_udc_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_udc_succss.setVisibility(View.GONE);
                        } else {
                            upld_udc_succss.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        String mUDCDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mUDCDocURL);

                        if (valuepdf) {

                            File mFile = new File(mUDCDocURL);

                            Pdf_Name = mFile.getName();
                            udc_name.setVisibility(View.VISIBLE);
                            udc_view.setVisibility(View.VISIBLE);
                            upld_udc_buton.setImageResource(R.drawable.c3);
                            udc_name.setText(Pdf_Name);
                            udc_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mUDCDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mUDCDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_udc_buton.setImageBitmap(mIcon_val);
                                upld_udc_buton.getLayoutParams().height = 300;
                                upld_udc_buton.getLayoutParams().width = 300;
                                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerUDCDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mDealerUDCDocStatus) {
                mUDCDocCall = restApis.ShopkeeperUDCDoc(user_code, vechileDocUpload2);
            } else {
                mUDCDocCall = restApis.GetShopkeeperUDCDoc(data);
            }
            mUDCDocCall.enqueue(new Callback<DealerUDCDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerUDCDoc_MODEL> call, Response<DealerUDCDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        dropup2_udc.setVisibility(View.GONE);
                        dropdown_udc.setVisibility(View.VISIBLE);
                        upld_udc_layout.setVisibility(View.GONE);
                        //   upld_udc_succss.setVisibility(View.VISIBLE);
                        udc_save_button.setText(mUpdate);
                        mSelectedUDCStatus = 0;
                        upload_udc_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_udc_succss.setVisibility(View.GONE);
                        } else {
                            upld_udc_succss.setVisibility(View.VISIBLE);
                        }
                        String mUDCDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mUDCDocURL);

                        if (valuepdf) {

                            File mFile = new File(mUDCDocURL);

                            Pdf_Name = mFile.getName();
                            udc_name.setVisibility(View.VISIBLE);
                            udc_view.setVisibility(View.VISIBLE);
                            upld_udc_buton.setImageResource(R.drawable.c3);
                            udc_name.setText(Pdf_Name);
                            udc_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mUDCDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mUDCDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_udc_buton.setImageBitmap(mIcon_val);
                                upld_udc_buton.getLayoutParams().height = 300;
                                upld_udc_buton.getLayoutParams().width = 300;
                                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }


                }

                @Override
                public void onFailure(Call<DealerUDCDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void DealerBUSINESSPROOFDoc(boolean mIsDealerBUSINESSPROOFStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedBussinessProofStatus == 1) {
            idFile = mBussinessProofFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("BUSINESS_PROOF", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mDealer)) {
            if (mIsDealerBUSINESSPROOFStatus) {
                mBUSINESSPROOFDocCall = restApis.DealerBUSINESSPROOFDoc(user_code, vechileDocUpload2);
            } else {
                mBUSINESSPROOFDocCall = restApis.GetDealerBUSINESSPROOFDoc(data);
            }
            mBUSINESSPROOFDocCall.enqueue(new Callback<DealerBUSINESSPROOFDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerBUSINESSPROOFDoc_MODEL> call, Response<DealerBUSINESSPROOFDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup2_business_proof.setVisibility(View.GONE);
                        dropdown_business_proof.setVisibility(View.VISIBLE);
                        upld_business_layout.setVisibility(View.GONE);
                        //  upld_busines_succss.setVisibility(View.VISIBLE);
                        save_business.setText(mUpdate);
                        mSelectedBussinessProofStatus = 0;
                        upload_business_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_busines_succss.setVisibility(View.GONE);
                            mDeleteBusinessButton.setVisibility(View.GONE);
                        } else {
                            upld_busines_succss.setVisibility(View.VISIBLE);
                            mDeleteBusinessButton.setVisibility(View.VISIBLE);
                        }
                        String mBusinessProofURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mBusinessProofURL);

                        if (valuepdf) {

                            File mFile = new File(mBusinessProofURL);

                            Pdf_Name = mFile.getName();
                            business_name.setVisibility(View.VISIBLE);
                            business_view.setVisibility(View.VISIBLE);
                            upld_business_buton.setImageResource(R.drawable.c3);
                            business_name.setText(Pdf_Name);
                            mDeleteBusinessButton.setVisibility(View.VISIBLE);
                            business_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mBusinessProofURL));
                        } else {

                            try {
                                URL newurl = new URL(mBusinessProofURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_business_buton.setImageBitmap(mIcon_val);
                                upld_business_buton.getLayoutParams().height = 300;
                                upld_business_buton.getLayoutParams().width = 300;
                                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteBusinessButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerBUSINESSPROOFDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });
        } else if (mUserType.equals(mSubDealer)) {
            mBUSINESSPROOFDocCall = restApis.DealerBUSINESSPROOFDoc(user_code, vechileDocUpload2);
            if (mIsDealerBUSINESSPROOFStatus) {
                mBUSINESSPROOFDocCall = restApis.SubDealerBUSINESSPROOFDoc(user_code, vechileDocUpload2);
            } else {
                mBUSINESSPROOFDocCall = restApis.GetSubDealerBUSINESSPROOFDoc(data);
            }
            mBUSINESSPROOFDocCall.enqueue(new Callback<DealerBUSINESSPROOFDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerBUSINESSPROOFDoc_MODEL> call, Response<DealerBUSINESSPROOFDoc_MODEL> response) {
                    if (response.body() != null && response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        dropup2_business_proof.setVisibility(View.GONE);
                        dropdown_business_proof.setVisibility(View.VISIBLE);
                        upld_business_layout.setVisibility(View.GONE);
                        //  upld_busines_succss.setVisibility(View.VISIBLE);
                        save_business.setText("Update");
                        mSelectedBussinessProofStatus = 0;
                        upload_business_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mBusinessProofURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mBusinessProofURL);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_busines_succss.setVisibility(View.GONE);
                        } else {
                            upld_busines_succss.setVisibility(View.VISIBLE);
                        }
                        if (valuepdf) {

                            File mFile = new File(mBusinessProofURL);

                            Pdf_Name = mFile.getName();
                            business_name.setVisibility(View.VISIBLE);
                            business_view.setVisibility(View.VISIBLE);
                            upld_business_buton.setImageResource(R.drawable.c3);
                            business_name.setText(Pdf_Name);
                            business_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mBusinessProofURL));
                        } else {
                            try {
                                URL newurl = new URL(mBusinessProofURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_business_buton.setImageBitmap(mIcon_val);
                                upld_business_buton.getLayoutParams().height = 300;
                                upld_business_buton.getLayoutParams().width = 300;
                                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerBUSINESSPROOFDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });
        } else if (mUserType.equals(mShopKeeper)) {
            mBUSINESSPROOFDocCall = restApis.DealerBUSINESSPROOFDoc(user_code, vechileDocUpload2);
            if (mIsDealerBUSINESSPROOFStatus) {
                mBUSINESSPROOFDocCall = restApis.ShopkeeperBUSINESSPROOFDoc(user_code, vechileDocUpload2);
            } else {
                mBUSINESSPROOFDocCall = restApis.GetShopkeeperBUSINESSPROOFDoc(data);
            }
            mBUSINESSPROOFDocCall.enqueue(new Callback<DealerBUSINESSPROOFDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerBUSINESSPROOFDoc_MODEL> call, Response<DealerBUSINESSPROOFDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup2_business_proof.setVisibility(View.GONE);
                        dropdown_business_proof.setVisibility(View.VISIBLE);
                        upld_business_layout.setVisibility(View.GONE);
                        //  upld_busines_succss.setVisibility(View.VISIBLE);
                        save_business.setText(mUpdate);
                        mSelectedBussinessProofStatus = 0;
                        upload_business_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mBusinessProofURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mBusinessProofURL);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_busines_succss.setVisibility(View.GONE);
                        } else {
                            upld_busines_succss.setVisibility(View.VISIBLE);
                        }
                        if (valuepdf) {

                            File mFile = new File(mBusinessProofURL);
                            Pdf_Name = mFile.getName();
                            business_name.setVisibility(View.VISIBLE);
                            business_view.setVisibility(View.VISIBLE);
                            upld_business_buton.setImageResource(R.drawable.c3);
                            business_name.setText(Pdf_Name);
                            business_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mBusinessProofURL));
                        } else {
                            try {
                                URL newurl = new URL(mBusinessProofURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_business_buton.setImageBitmap(mIcon_val);
                                upld_business_buton.getLayoutParams().height = 300;
                                upld_business_buton.getLayoutParams().width = 300;
                                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerBUSINESSPROOFDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void DealerDISB_BANKDoc(boolean mBANKDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedDisburseBankStatus == 1) {
            idFile = mDisburseBankFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("bankimage", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals(mDealer)) {
            if (mBANKDocStatus) {
                mBANKDocCall = restApis.DealerDISB_BANKDoc(user_code, vechileDocUpload2);
            } else {
                mBANKDocCall = restApis.GetDealerDISB_BANKDoc(data);
            }
            mBANKDocCall.enqueue(new Callback<DealerDISB_BANKDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerDISB_BANKDoc_MODEL> call, Response<DealerDISB_BANKDoc_MODEL> response) {
                    if (response.body() != null && response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        disb_bank_dropup2.setVisibility(View.GONE);
                        disb_bank_dropdown.setVisibility(View.VISIBLE);
                        upld_disburse_layout.setVisibility(View.GONE);
                        //  upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        disburse_bank_save_button.setText(mUpdate);
                        upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        mSelectedDisburseBankStatus = 0;
                        upload_disb_bank_success = 1;
//                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        String mDisbBankURL = response.body().getPayload().getSelfie();
//                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mDisbBankURL);
//                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
//                            upld_disb_bank_succss.setVisibility(View.GONE);
//                        } else {
//                            upld_disb_bank_succss.setVisibility(View.VISIBLE);
//                        }
//                        if (valuepdf) {
//
//                            File mFile = new File(mDisbBankURL);
//
//                            Pdf_Name = mFile.getName();
//                            disb_bank_name.setVisibility(View.VISIBLE);
//                            disb_bank_view.setVisibility(View.VISIBLE);
//                            upld_disburse_bank_buton.setImageResource(R.drawable.c3);
//                            disb_bank_name.setText(Pdf_Name);
//                            disb_bank_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mDisbBankURL));
//                        } else {
//                            try {
//                                URL newurl = new URL(mDisbBankURL);
//                                Bitmap mIcon_val;
//                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//                                upld_disburse_bank_buton.setImageBitmap(mIcon_val);
//                                upld_disburse_bank_buton.getLayoutParams().height = 300;
//                                upld_disburse_bank_buton.getLayoutParams().width = 300;
//                                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerDISB_BANKDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mSubDealer)) {
            if (mBANKDocStatus) {
                mBANKDocCall = restApis.SubDealerDISB_BANKDoc(user_code, vechileDocUpload2);
            } else {
                mBANKDocCall = restApis.GetSubDealerDISB_BANKDoc(data);
            }
            mBANKDocCall.enqueue(new Callback<DealerDISB_BANKDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerDISB_BANKDoc_MODEL> call, Response<DealerDISB_BANKDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        disb_bank_dropup2.setVisibility(View.GONE);
                        disb_bank_dropdown.setVisibility(View.VISIBLE);
                        upld_disburse_layout.setVisibility(View.GONE);
                        //   upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        disburse_bank_save_button.setText(mUpdate);
                        mSelectedDisburseBankStatus = 0;
                        upload_disb_bank_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_disb_bank_succss.setVisibility(View.GONE);
                        } else {
                            upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mDisbBankURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mDisbBankURL);

                        if (valuepdf) {

                            File mFile = new File(mDisbBankURL);

                            Pdf_Name = mFile.getName();
                            disb_bank_name.setVisibility(View.VISIBLE);
                            disb_bank_view.setVisibility(View.VISIBLE);
                            upld_disburse_bank_buton.setImageResource(R.drawable.c3);
                            disb_bank_name.setText(Pdf_Name);
                            disb_bank_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mDisbBankURL));
                        } else {
                            try {
                                URL newurl = new URL(mDisbBankURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_disburse_bank_buton.setImageBitmap(mIcon_val);
                                upld_disburse_bank_buton.getLayoutParams().height = 300;
                                upld_disburse_bank_buton.getLayoutParams().width = 300;
                                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerDISB_BANKDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mBANKDocStatus) {
                mBANKDocCall = restApis.ShopkeeperDISB_BANKDoc(user_code, vechileDocUpload2);
            } else {
                mBANKDocCall = restApis.GetShopkeeperDISB_BANKDoc(data);
            }
            mBANKDocCall.enqueue(new Callback<DealerDISB_BANKDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerDISB_BANKDoc_MODEL> call, Response<DealerDISB_BANKDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        disb_bank_dropup2.setVisibility(View.GONE);
                        disb_bank_dropdown.setVisibility(View.VISIBLE);
                        upld_disburse_layout.setVisibility(View.GONE);
                        //   upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        disburse_bank_save_button.setText(mUpdate);
                        mSelectedDisburseBankStatus = 0;
                        upload_disb_bank_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_disb_bank_succss.setVisibility(View.GONE);
                        } else {
                            upld_disb_bank_succss.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mDisbBankURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mDisbBankURL);

                        if (valuepdf) {

                            File mFile = new File(mDisbBankURL);

                            Pdf_Name = mFile.getName();
                            disb_bank_name.setVisibility(View.VISIBLE);
                            disb_bank_view.setVisibility(View.VISIBLE);
                            upld_disburse_bank_buton.setImageResource(R.drawable.c3);
                            business_name.setText(Pdf_Name);
                            disb_bank_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mDisbBankURL));
                        } else {
                            try {
                                URL newurl = new URL(mDisbBankURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_disburse_bank_buton.setImageBitmap(mIcon_val);
                                upld_disburse_bank_buton.getLayoutParams().height = 300;
                                upld_disburse_bank_buton.getLayoutParams().width = 300;
                                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerDISB_BANKDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }


    private void DealerGSTDoc(boolean mGSTDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedGSTStatus == 1) {
            idFile = mGSTFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("GST", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mDealer)) {
            if (mGSTDocStatus) {
                mGSTDocCall = restApis.DealerGSTDoc(user_code, vechileDocUpload2);
            } else {
                mGSTDocCall = restApis.GetDealerGSTDoc(data);
            }
            mGSTDocCall.enqueue(new Callback<DealerGSTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerGSTDoc_MODEL> call, Response<DealerGSTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        Gst_dropup2.setVisibility(View.GONE);
                        GST_DROPDOWN.setVisibility(View.VISIBLE);
                        upld_Gst_layout.setVisibility(View.GONE);
                        //  upld_Gst_succss.setVisibility(View.VISIBLE);
                        Gst_save_button.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_Gst_succss.setVisibility(View.GONE);
                            mDeleteGstButton.setVisibility(View.GONE);
                        } else {
                            upld_Gst_succss.setVisibility(View.VISIBLE);
                            mDeleteGstButton.setVisibility(View.VISIBLE);
                        }
                        mSelectedGSTStatus = 0;
                        upload_gst_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mGSTDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mGSTDocURL);

                        if (valuepdf) {

                            File mFile = new File(mGSTDocURL);

                            Pdf_Name = mFile.getName();
                            Gst_name.setVisibility(View.VISIBLE);
                            Gst_view.setVisibility(View.VISIBLE);
                            upld_Gst_buton.setImageResource(R.drawable.c3);
                            Gst_name.setText(Pdf_Name);
                            mDeleteGstButton.setVisibility(View.VISIBLE);
                            Gst_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mGSTDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mGSTDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_Gst_buton.setImageBitmap(mIcon_val);
                                upld_Gst_buton.getLayoutParams().height = 300;
                                upld_Gst_buton.getLayoutParams().width = 300;
                                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteGstButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerGSTDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mSubDealer)) {
            if (mGSTDocStatus) {
                mGSTDocCall = restApis.SubDealerGSTDoc(user_code, vechileDocUpload2);
            } else {
                mGSTDocCall = restApis.GetSubDealerGSTDoc(data);
            }
            mGSTDocCall.enqueue(new Callback<DealerGSTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerGSTDoc_MODEL> call, Response<DealerGSTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        Gst_dropup2.setVisibility(View.GONE);
                        GST_DROPDOWN.setVisibility(View.VISIBLE);
                        upld_Gst_layout.setVisibility(View.GONE);
                        //  upld_Gst_succss.setVisibility(View.VISIBLE);
                        Gst_save_button.setText(mUpdate);
                        mSelectedGSTStatus = 0;
                        upload_gst_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_Gst_succss.setVisibility(View.GONE);
                        } else {
                            upld_Gst_succss.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mGSTDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mGSTDocURL);

                        if (valuepdf) {

                            File mFile = new File(mGSTDocURL);

                            Pdf_Name = mFile.getName();
                            business_name.setVisibility(View.VISIBLE);
                            business_view.setVisibility(View.VISIBLE);
                            upld_business_buton.setImageResource(R.drawable.c3);
                            business_name.setText(Pdf_Name);
                            business_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mGSTDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mGSTDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_Gst_buton.setImageBitmap(mIcon_val);
                                upld_Gst_buton.getLayoutParams().height = 300;
                                upld_Gst_buton.getLayoutParams().width = 300;
                                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerGSTDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mGSTDocStatus) {
                mGSTDocCall = restApis.ShopkeeperGSTDoc(user_code, vechileDocUpload2);
            } else {
                mGSTDocCall = restApis.GetShopkeeperGSTDoc(data);
            }
            mGSTDocCall.enqueue(new Callback<DealerGSTDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerGSTDoc_MODEL> call, Response<DealerGSTDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        Gst_dropup2.setVisibility(View.GONE);
                        GST_DROPDOWN.setVisibility(View.VISIBLE);
                        upld_Gst_layout.setVisibility(View.GONE);
                        //  upld_Gst_succss.setVisibility(View.VISIBLE);
                        Gst_save_button.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_Gst_succss.setVisibility(View.GONE);
                        } else {
                            upld_Gst_succss.setVisibility(View.VISIBLE);
                        }
                        mSelectedGSTStatus = 0;
                        upload_gst_success = 1;
                        Toast.makeText(BusinessDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String mGSTDocURL = response.body().getPayload().getSelfie();
                        valuepdf = new CheckPDFOrImage().checkIsPDFOrImage(mGSTDocURL);

                        if (valuepdf) {

                            File mFile = new File(mGSTDocURL);

                            Pdf_Name = mFile.getName();
                            Gst_name.setVisibility(View.VISIBLE);
                            Gst_view.setVisibility(View.VISIBLE);
                            upld_Gst_buton.setImageResource(R.drawable.c3);
                            Gst_name.setText(Pdf_Name);
                            Gst_view.setOnClickListener(view -> new CheckPDFOrImage().openPDFFromURL(BusinessDetails_Activity.this, mGSTDocURL));
                        } else {
                            try {
                                URL newurl = new URL(mGSTDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_Gst_buton.setImageBitmap(mIcon_val);
                                upld_Gst_buton.getLayoutParams().height = 300;
                                upld_Gst_buton.getLayoutParams().width = 300;
                                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerGSTDoc_MODEL> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void BusinessInfoDailog() {


        dialogCondition.setContentView(R.layout.business_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();

        Ok_button.setOnClickListener(v -> dialogCondition.dismiss());


    }

    private void UtilInfoDailog() {


        dialogCondition.setContentView(R.layout.utility_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();

        Ok_button.setOnClickListener(v -> dialogCondition.dismiss());


    }

    private void AgreementInfoDailog() {


        dialogCondition.setContentView(R.layout.agreement_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();

        Ok_button.setOnClickListener(v -> dialogCondition.dismiss());


    }

    private void BankInfoDailog() {


        dialogCondition.setContentView(R.layout.bank_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();

        Ok_button.setOnClickListener(v -> dialogCondition.dismiss());


    }

    private void GstInfoDailog() {

        dialogCondition.setContentView(R.layout.gstinfo_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);

        dialogCondition.show();

        Ok_button.setOnClickListener(v -> dialogCondition.dismiss());


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
        CameraButton.setOnClickListener(view -> {
            takePhotoFromCamera();
            dialogCondition.dismiss();
        });
        GalleryButton.setOnClickListener(view -> {
            choosePhotoFromGallary();
            dialogCondition.dismiss();
        });
        PdfButton.setOnClickListener(view -> {
            openPDFSelector();

            dialogCondition.dismiss();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY && data != null) {

            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                Log.e("bitmap", "mBankDocumentPos " + mBankDocumentPos);

                if (mSelectDialogClicked) {
                    mBankDocumentList.set(mBankDocumentPos, new BankDocumentAddItem(bitmap, mBankPdf, Pdf_Name));
                    loadDataBankDocument(mBankDocumentList);
                }


                if (x == 5) {
                    upld_business_buton.setImageBitmap(bitmap);

                    upld_business_hint.setVisibility(View.GONE);
                    business_name.setVisibility(View.GONE);
                    business_view.setVisibility(View.GONE);
                    upld_business_buton.getLayoutParams().height = 300;
                    upld_business_buton.getLayoutParams().width = 300;
                    upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    Business_valid = 1;
                    mSelectedBussinessProofStatus = 0;
                    mDeleteBusinessButton.setVisibility(View.VISIBLE);

                } else if (x == 6) {
                    upld_udc_buton.setImageBitmap(bitmap);
                    upld_udc_hint.setVisibility(View.GONE);
                    udc_name.setVisibility(View.GONE);
                    upld_udc_buton.getLayoutParams().height = 300;
                    upld_udc_buton.getLayoutParams().width = 300;
                    upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    UDC_Valid = 1;
                    mSelectedUDCStatus = 0;
                    mDeleteUtilityButton.setVisibility(View.VISIBLE);

                } else if (x == 7) {
                    upld_agreement_buton.setImageBitmap(bitmap);
                    upld_agreement_hint.setVisibility(View.GONE);
                    agreement_name.setVisibility(View.GONE);
                    upld_agreement_buton.getLayoutParams().height = 300;
                    upld_agreement_buton.getLayoutParams().width = 300;
                    upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    Agreement_Valid = 1;
                    mSelectedAgreementStatus = 0;
                    mDeleteAgreementButton.setVisibility(View.VISIBLE);

                } else if (x == 8) {
                    upld_escrow_buton.setImageBitmap(bitmap);
                    upld_escrow_hint.setVisibility(View.GONE);
                    escrow_name.setVisibility(View.GONE);
                    upld_escrow_buton.getLayoutParams().height = 300;
                    upld_escrow_buton.getLayoutParams().width = 300;
                    upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    Escrow_Valid = 1;
                    mSelectedEscrowStatus = 0;

                } else if (x == 9) {
                    upld_disburse_bank_buton.setImageBitmap(bitmap);
                    upld_disb_bank_hint.setVisibility(View.GONE);
                    disb_bank_name.setVisibility(View.GONE);
                    upld_disburse_bank_buton.getLayoutParams().height = 300;
                    upld_disburse_bank_buton.getLayoutParams().width = 300;
                    upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    Disb_Bank_Valid = 1;
                    mSelectedDisburseBankStatus = 0;
                } else if (x == 14) {
                    upld_Gst_buton.setImageBitmap(bitmap);
                    upld_Gst_hint.setVisibility(View.GONE);
                    Gst_name.setVisibility(View.GONE);
                    upld_Gst_buton.getLayoutParams().height = 300;
                    upld_Gst_buton.getLayoutParams().width = 300;
                    upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                    mDeleteGstButton.setVisibility(View.VISIBLE);
                    GST_Valid = 1;
                    mSelectedGSTStatus = 0;
                } else if (mBankDocumentValid == 1) {
                    Log.e("positionofimagebankvalid", "");
                  /* setImageListener = new SetImageListener() {
                       @Override
                       public void setImageOnClick(int position, Bitmap bitmap) {
                          bankimage = bitmap;
                         Log.e("Image","-->>"+bankimage);
                           setImageOnClick(position, bitmap);
                       }
                    };*/
                }
                saveImage(bitmap);

            } catch (IOException e) {

                e.printStackTrace();

            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            if (mSelectDialogClicked) {
                mBankDocumentList.set(mBankDocumentPos, new BankDocumentAddItem(thumbnail, mBankPdf, Pdf_Name));
                loadDataBankDocument(mBankDocumentList);
            }
            if (y == 5) {
                upld_business_buton.setImageBitmap(thumbnail);
                upld_business_hint.setVisibility(View.GONE);
                business_name.setVisibility(View.GONE);
                business_view.setVisibility(View.GONE);
                upld_business_buton.getLayoutParams().height = 300;
                upld_business_buton.getLayoutParams().width = 300;
                upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Business_valid = 1;
                mSelectedBussinessProofStatus = 0;
                mDeleteBusinessButton.setVisibility(View.VISIBLE);
            } else if (y == 6) {
                upld_udc_buton.setImageBitmap(thumbnail);
                upld_udc_hint.setVisibility(View.GONE);
                udc_name.setVisibility(View.GONE);
                udc_view.setVisibility(View.GONE);
                upld_udc_buton.getLayoutParams().height = 300;
                upld_udc_buton.getLayoutParams().width = 300;
                upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                UDC_Valid = 1;
                mSelectedUDCStatus = 0;
                mDeleteUtilityButton.setVisibility(View.VISIBLE);
            } else if (y == 7) {
                upld_agreement_buton.setImageBitmap(thumbnail);
                upld_agreement_hint.setVisibility(View.GONE);
                agreement_name.setVisibility(View.GONE);
                agreement_view.setVisibility(View.GONE);
                upld_agreement_buton.getLayoutParams().height = 300;
                upld_agreement_buton.getLayoutParams().width = 300;
                upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Agreement_Valid = 1;
                mSelectedAgreementStatus = 0;
                mDeleteAgreementButton.setVisibility(View.VISIBLE);
            } else if (y == 8) {
                upld_escrow_buton.setImageBitmap(thumbnail);
                upld_escrow_hint.setVisibility(View.GONE);
                escrow_name.setVisibility(View.GONE);
                upld_escrow_buton.getLayoutParams().height = 300;
                upld_escrow_buton.getLayoutParams().width = 300;
                upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Escrow_Valid = 1;
                mSelectedEscrowStatus = 0;
            } else if (y == 9) {
                upld_disburse_bank_buton.setImageBitmap(thumbnail);
                mUploadOtherbankButon1.setImageBitmap(thumbnail);
                upld_disb_bank_hint.setVisibility(View.GONE);
                disb_bank_name.setVisibility(View.GONE);
                disb_bank_view.setVisibility(View.GONE);
                upld_disburse_bank_buton.getLayoutParams().height = 300;
                upld_disburse_bank_buton.getLayoutParams().width = 300;
                upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);

                mUploadOtherbankButon1.getLayoutParams().height = 300;
                mUploadOtherbankButon1.getLayoutParams().width = 300;
                mUploadOtherbankButon1.setScaleType(ImageView.ScaleType.FIT_XY);
                Disb_Bank_Valid = 1;
                mSelectedDisburseBankStatus = 0;
            } else if (y == 14) {
                upld_Gst_buton.setImageBitmap(thumbnail);
                upld_Gst_hint.setVisibility(View.GONE);
                Gst_name.setVisibility(View.GONE);
                Gst_view.setVisibility(View.GONE);
                upld_Gst_buton.getLayoutParams().height = 300;
                upld_Gst_buton.getLayoutParams().width = 300;
                upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                GST_Valid = 1;
                mSelectedGSTStatus = 0;
                mDeleteGstButton.setVisibility(View.VISIBLE);
            }

            saveImage(thumbnail);

        } else {
            if (mSelectDialogClicked) {
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
               /* mBankDocumentList.set(mBankDocumentPos, new BankDocumentAddItem(thumbnail));
                loadDataBankDocument(mBankDocumentList);*/
            }

            if (mSelectedBussinessProofStatus == 1) {
                Business_valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

                upld_business_hint.setVisibility(View.GONE);

            } else if (mSelectedUDCStatus == 1) {

                UDC_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

                upld_udc_hint.setVisibility(View.GONE);
            } else if (mSelectedAgreementStatus == 1) {
                Agreement_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_agreement_hint.setVisibility(View.GONE);
            } else if (mSelectedEscrowStatus == 1) {
                Escrow_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_escrow_hint.setVisibility(View.GONE);
            } else if (mSelectedDisburseBankStatus == 1) {
                Disb_Bank_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_disb_bank_hint.setVisibility(View.GONE);
            } else if (mSelectedGSTStatus == 1) {
                GST_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_Gst_hint.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        try {
            File f = new File(BusinessDetails_Activity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GoPikMoneyDocument");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(BusinessDetails_Activity.this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
  /*  public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdir();
        }
        try {
            File f = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");
            f.createNewFile();
            fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            SharedPref.saveStringInSharedPref(AppConstants.ML_LOAN_IMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }*/


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

        if (mSelectDialogClicked) {
            mBankPdf = new File(path);
            Pdf_Name = mFile.getName();
            Log.e("PdfName", ">>>>>>" + mBankPdf + Pdf_Name);
            mBankDocumentList.set(mBankDocumentPos, new BankDocumentAddItem(null, mBankPdf, Pdf_Name));
            loadDataBankDocument(mBankDocumentList);
        }
        Pdf_Name = mFile.getName();

        if (mSelectedBussinessProofStatus == 1) {

            mBussinessProofFile = mFile;

            business_name.setText(Pdf_Name);

            business_view.setVisibility(View.VISIBLE);
            business_name.setVisibility(View.VISIBLE);
            business_view.setOnClickListener(view -> showPDF2(mFile));
            upld_business_buton.setImageResource(R.drawable.c3);
            upld_business_buton.getLayoutParams().height = 150;
            upld_business_buton.getLayoutParams().width = 150;
            upld_business_buton.setScaleType(ImageView.ScaleType.FIT_XY);
            mDeleteBusinessButton.setVisibility(View.VISIBLE);
//            mSelectedBussinessProofStatus = 0;
        } else if (mSelectedUDCStatus == 1) {

            mUDCFile = mFile;
            udc_name.setText(Pdf_Name);
            udc_view.setVisibility(View.VISIBLE);
            udc_name.setVisibility(View.VISIBLE);
            udc_view.setOnClickListener(v -> showPDF2(mFile));
            upld_udc_buton.setImageResource(R.drawable.c3);
            upld_udc_buton.getLayoutParams().height = 150;
            upld_udc_buton.getLayoutParams().width = 150;
            upld_udc_buton.setScaleType(ImageView.ScaleType.FIT_XY);
            mDeleteUtilityButton.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        } else if (mSelectedAgreementStatus == 1) {

            mAgreementFile = mFile;
            agreement_name.setText(Pdf_Name);
            agreement_view.setVisibility(View.VISIBLE);
            agreement_name.setVisibility(View.VISIBLE);
            agreement_view.setOnClickListener(v -> showPDF2(mFile));
            upld_agreement_buton.setImageResource(R.drawable.c3);
            upld_agreement_buton.getLayoutParams().height = 150;
            upld_agreement_buton.getLayoutParams().width = 150;
            upld_agreement_buton.setScaleType(ImageView.ScaleType.FIT_XY);
            mDeleteAgreementButton.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        } else if (mSelectedEscrowStatus == 1) {

            mEscrowFile = mFile;
            escrow_name.setText(Pdf_Name);

            escrow_name.setVisibility(View.VISIBLE);
            escrow_name.setOnClickListener(v -> showPDF2(mFile));
            upld_escrow_buton.setImageResource(R.drawable.c3);

            upld_escrow_buton.getLayoutParams().height = 150;
            upld_escrow_buton.getLayoutParams().width = 150;
            upld_escrow_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        } else if (mSelectedDisburseBankStatus == 1) {

            mDisburseBankFile = mFile;
            disb_bank_view.setVisibility(View.VISIBLE);
            disb_bank_name.setText(Pdf_Name);
            disb_bank_name.setVisibility(View.VISIBLE);
            disb_bank_view.setOnClickListener(v -> showPDF2(mFile));

            upld_disburse_bank_buton.setImageResource(R.drawable.c3);

            upld_disburse_bank_buton.getLayoutParams().height = 150;
            upld_disburse_bank_buton.getLayoutParams().width = 150;
            upld_disburse_bank_buton.setScaleType(ImageView.ScaleType.FIT_XY);

//            mSelectedUDCStatus = 0;
        } else if (mSelectedGSTStatus == 1) {

            mGSTFile = mFile;
            Gst_name.setText(Pdf_Name);
            Gst_view.setVisibility(View.VISIBLE);

            Gst_name.setVisibility(View.VISIBLE);
            Gst_view.setOnClickListener(v -> showPDF2(mFile));

            upld_Gst_buton.setImageResource(R.drawable.c3);
            upld_Gst_buton.getLayoutParams().height = 150;
            upld_Gst_buton.getLayoutParams().width = 150;
            upld_Gst_buton.setScaleType(ImageView.ScaleType.FIT_XY);
            mDeleteGstButton.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        }
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

        //Do nothing
    }

    private void showPDF2(File mFile) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri URI = FileProvider.getUriForFile(BusinessDetails_Activity.this, BusinessDetails_Activity.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(URI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        BusinessDetails_Activity.this.startActivity(viewPdf);
    }

}