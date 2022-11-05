package com.qts.gopik_money.Supply_Chain;

import android.annotation.SuppressLint;
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
import com.qts.gopik_money.Activity.OEMDetailsActivity;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.DealerDocDeleteModel;
import com.qts.gopik_money.Model.DealerINVOICEDoc_MODEL;
import com.qts.gopik_money.Model.DealerITRDoc_MODEL;
import com.qts.gopik_money.Model.DealerLEDGERDoc_MODEL;
import com.qts.gopik_money.Model.DealerTDSDoc_MODEL;
import com.qts.gopik_money.Model.DealerdoctoFinance_MODEL;
import com.qts.gopik_money.Model.dealer_doc_confirm_MODEL;
import com.qts.gopik_money.Pojo.DealerDocDelete_POJO;
import com.qts.gopik_money.Pojo.Dealer_doc_confirm_POJO;
import com.qts.gopik_money.Pojo.DealerdoctoFinance_POJO;
import com.qts.gopik_money.Pojo.OtherDocumentAddItem;
import com.qts.gopik_money.Pojo.ShopkeeperdoctoFinance_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Supplychain_Adapter.OtherDocumentAdapter;
import com.qts.gopik_money.Utils.CheckPDFOrImage;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.OtherDocumentAddListener;
import com.qts.gopik_money.Utils.RemoveOtherDocumentListener;
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

public class TransactionDetails_Activity extends AppCompatActivity implements PickiTCallbacks {
    private Context mContext = TransactionDetails_Activity.this;
    Call<DealerDocDeleteModel> mDealerDocDeleteCall;
    String networkError = "It seems your Network is unstable . Please Try again!";
    public int x = 0;
    public int y = 0;
    public int PDF_VALID = 0;
    public int Selfie = 0;

    PickiT pickiT;
    private int GALLERY = 1 ;
    private int CAMERA = 2;
    ImageView CameraButton;
    ImageView GalleryButton;
    ImageView PdfButton;
    TextView Ok_button;
    Integer Ledger_Valid = 0;
    Integer Invoice_Valid = 0;
    Integer ITR_Valid = 0;
    Integer TDS_Valid = 0;
    Integer GST_Valid = 0;
    private Dialog dialogCondition;
    TextView btsend, save1, save2, save3, save4;
    Integer upload_ledger_success = 0;
    Integer upload_invoice_success = 0;
    Integer upload_itr_success = 0;
    Integer upload_tds_success = 0;
    ImageView arrow,hometoolbar;
    ArrayList<OtherDocumentAddItem>otherDocAddItemArrayList  = new ArrayList<>();
    OtherDocumentAdapter otherDocumentAdapter;
    OtherDocumentAddListener otherDocumentAddListener;
    RemoveOtherDocumentListener removeOtherDocumentListener;
    String Pdf_Name,sendtofinancerbuttonvisible;

    private static final int FILE_CHOOSER=123;

    TextView ledger_save_button,invoice_save_button,itr_save_button,TDS_save_button;

    ImageView ledger_dropdown,ledger_dropup2;

    LinearLayout upld_ledger_layout,upld_Invoice_layout,upld_itr_layout,upld_TDS_layout;
    ImageView invoice_dropdown,invoice_dropup2,dropdown_itr,itr_dropup2,Dropdown_TDS,TdS_dropup2,GST_DROPDOWN,Gst_dropup2;

    TextView upld_ledger_hint,upld_ITR_hint,upld_invoice_hint,upld_TDS_hint;
    ImageView upld_ledger_succss,upld_invoice_succss,upld_itr_succss,upld_tds_succss;

    ImageView upld_ledger_buton,upld_invoice_buton,upld_itr_buton,upld_tds_buton;
    //Doc 1
    ImageView mDropdownOtherDoc1;
    ImageView mDropUpOtherDoc1;

    //Doc 2
    ImageView mDropdownOtherDoc2;
    ImageView mDropUpOtherDoc2;

    LinearLayout mUploadOtherdocLayout_1;
    LinearLayout mUploadOtherdocLayout_2;

    ImageView mUploadOtherDocument1_button;
    ImageView mUploadOtherDocument2_button;

    TextView mUpldOtherDoc1_Hint;
    TextView mUpldOtherDoc2_Hint;

    TextView mSaveOtherDocument1;
    TextView mSaveOtherDocument2;
    //Ledger
    File mLedgerFile ;
    int  mSelectedLedgerStatus = 0;
    //Invoice
    File mInvoiceFile ;
    int  mSelectedInvoiceStatus = 0;
    //ITR
    File mITRFile ;
    int  mSelectedITRStatus = 0;
    //TDS
    File mTDSFile = null;
    int  mSelectedTDSStatus = 0;
    String mUserType;
    String image;
    Call<DealerLEDGERDoc_MODEL> mLEDGERDocCall;
    Call<DealerITRDoc_MODEL> mITRDocCall;
    Call<DealerTDSDoc_MODEL> mTDSDocCall;
    CustPrograssbar custPrograssbar;
    TextView send_to_financer_button;
    String usercode,doc_status;
    ImageView ledger_info_button,itr_info_button,tds_info_button;
    TextView ledger_name,tds_name,itr_name,invoice_name;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    TextView ledger_tv;
    Call<DealerdoctoFinance_MODEL> mDooctoFinanceCall;
    Button ledger_view,itr_view,tds_view;
    Boolean valuepdf=false;
    RecyclerView mOtherDocRecyclerview;
    ImageButton mDeleteLedgerButton;
    ImageButton mDeleteItrButton;
    ImageButton mDeleteAuditedButton1;
    ImageButton mDeleteAuditedButton2;
    TextView mDeleteButton;
    TextView mCanceleButton;
    ImageView mAddMoreButton;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        mAddMoreButton = findViewById(R.id.add_more_button);
        mDeleteLedgerButton = findViewById(R.id.delete_ledger_button);
        mDeleteItrButton = findViewById(R.id.delete_itr_button);
        mDeleteAuditedButton1 = findViewById(R.id.delete_audited_button1);
        mDeleteAuditedButton2 = findViewById(R.id.delete_audited_button2);


        mOtherDocRecyclerview = findViewById(R.id.other_document_recyclerview);


        mDropdownOtherDoc1 = findViewById(R.id.dropdown_other_doc1);
        mDropUpOtherDoc1 = findViewById(R.id.dropup_other_doc1);

        mDropdownOtherDoc2 = findViewById(R.id.dropdown_other_doc2);
        mDropUpOtherDoc2 = findViewById(R.id.dropup_other_doc2);

        mUploadOtherdocLayout_1 = findViewById(R.id.upload_otherdoc1_layout);
        mUploadOtherdocLayout_2 = findViewById(R.id.upload_otherdoc2_layout);

        mUploadOtherDocument1_button = findViewById(R.id.upload_otherdocument1_button);
        mUploadOtherDocument2_button = findViewById(R.id.upload_otherdocument2_button);

        mUpldOtherDoc1_Hint = findViewById(R.id.upld_otherdoc1_hint);
        mUpldOtherDoc2_Hint = findViewById(R.id.upld_otherdoc2_hint);

        mSaveOtherDocument1 = findViewById(R.id.save_other_document1);
        mSaveOtherDocument2 = findViewById(R.id.save_other_document2);

        ledger_view = findViewById(R.id.ledger_view);
        itr_view = findViewById(R.id.itr_view);
        tds_view = findViewById(R.id.tds_view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());

        ledger_tv = findViewById(R.id.ledger_tv);

        ledger_tv.append(TextViewUtils.getColoredString("10. Upload Ledger Copy", ContextCompat.getColor(TransactionDetails_Activity.this, R.color.black)));
        ledger_tv.append(TextViewUtils.getColoredString(" *",ContextCompat.getColor(TransactionDetails_Activity.this, R.color.red)));
        send_to_financer_button = findViewById(R.id.send_to_financer_button);
        ledger_info_button = findViewById(R.id.ledger_info_button);
        itr_info_button = findViewById(R.id.itr_info_button);
        tds_info_button = findViewById(R.id.tds_info_button);

        //Pdf name
        ledger_name = findViewById(R.id.ledger_name);
        tds_name = findViewById(R.id.tds_name);
        itr_name = findViewById(R.id.itr_name);
        invoice_name = findViewById(R.id.invoice_name);
        btsend = findViewById(R.id.btsend1);



        pickiT = new PickiT(TransactionDetails_Activity.this, this, TransactionDetails_Activity.this);

        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);

        custPrograssbar = new CustPrograssbar();
        dialogCondition = new Dialog(TransactionDetails_Activity.this);
        //Hint
        upld_TDS_hint = findViewById(R.id.upld_TDS_hint);
        upld_ledger_hint = findViewById(R.id.upld_ledger_hint);
        upld_ITR_hint = findViewById(R.id.upld_ITR_hint);
        upld_invoice_hint = findViewById(R.id.upld_invoice_hint);
        send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_grey));
        send_to_financer_button.setEnabled(false);
        send_to_financer_button.setClickable(false);
        //Upload Button
        upld_ledger_buton = findViewById(R.id.upld_ledger_buton);
        upld_invoice_buton = findViewById(R.id.upld_invoice_buton);
        upld_itr_buton = findViewById(R.id.upld_itr_buton);
        upld_tds_buton = findViewById(R.id.upld_tds_buton);

        //Upload Success
        upld_invoice_succss = findViewById(R.id.upld_invoice_succss);
        upld_ledger_succss = findViewById(R.id.upld_ledger_succss);
        upld_itr_succss = findViewById(R.id.upld_itr_succss);
        upld_tds_succss = findViewById(R.id.upld_tds_succss);

        //Save Buttons
        ledger_save_button= (TextView) findViewById(R.id.ledger_save_button);
        invoice_save_button= (TextView) findViewById(R.id.invoice_save_button);
        itr_save_button = (TextView) findViewById(R.id.itr_save_button);
        TDS_save_button = (TextView) findViewById(R.id.TDS_save_button);
        //Dropdown Dropup
        //ledger
        ledger_dropdown = findViewById(R.id.ledger_dropdown);
        ledger_dropup2 = findViewById(R.id.ledger_dropup2);

        //Invoice
        invoice_dropdown = findViewById(R.id.invoice_dropdown);
        invoice_dropup2 = findViewById(R.id.invoice_dropup2);

        //Itr
        dropdown_itr = findViewById(R.id.dropdown_itr);
        itr_dropup2 = findViewById(R.id.itr_dropup2);

        //Tds
        Dropdown_TDS = findViewById(R.id.Dropdown_TDS);
        TdS_dropup2 = findViewById(R.id.TdS_dropup2);
        //Layouts
        upld_ledger_layout = findViewById(R.id.upld_ledger_layout);
        upld_Invoice_layout = findViewById(R.id.upld_Invoice_layout);
        upld_itr_layout = findViewById(R.id.upld_itr_layout);
        upld_TDS_layout = findViewById(R.id.upld_TDS_layout);
        mDeleteLedgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCondition = new Dialog(TransactionDetails_Activity.this);
                dialogCondition.setContentView(R.layout.delete_dialog);
                mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
                mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

                dialogCondition.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                dialogCondition.setCancelable(true);
                mDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call Delete Method Here
                        Toast.makeText(TransactionDetails_Activity.this, "Image Successfully Deleted", Toast.LENGTH_SHORT).show();

                        dialogCondition.dismiss();
                    }
                });
                mCanceleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCondition.dismiss();
                    }
                });

                dialogCondition.show();

            }
        });
        mDeleteItrButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(TransactionDetails_Activity.this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v1 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"ITR",getApplicationContext());
                DealerDocDelete();
                ITR_Valid = 0;
                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v12 -> dialogCondition.dismiss());
            dialogCondition.show();
        });
        mDeleteAuditedButton1.setOnClickListener(v -> {
            dialogCondition = new Dialog(TransactionDetails_Activity.this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"TDS",getApplicationContext());
                    DealerDocDelete();
                    TDS_Valid = 0;
                    dialogCondition.dismiss();
                }
            });
            mCanceleButton.setOnClickListener(v13 -> {
                dialogCondition.dismiss();
            });

            dialogCondition.show();
        });
        mDeleteAuditedButton2.setOnClickListener(v -> {
            dialogCondition = new Dialog(TransactionDetails_Activity.this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call Delete Method Here
                    Toast.makeText(TransactionDetails_Activity.this, "Image Successfully Deleted", Toast.LENGTH_SHORT).show();

                    dialogCondition.dismiss();
                }
            });
            mCanceleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCondition.dismiss();
                }
            });

            dialogCondition.show();
        });
        mDropdownOtherDoc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUploadOtherdocLayout_1.setVisibility(View.VISIBLE);
                mDropdownOtherDoc1.setVisibility(View.GONE);
                mDropUpOtherDoc1.setVisibility(View.VISIBLE);
            }
        });
        mDropUpOtherDoc1.setOnClickListener(v -> {
            mUploadOtherdocLayout_1.setVisibility(View.GONE);
            mDropdownOtherDoc1.setVisibility(View.VISIBLE);
            mDropUpOtherDoc1.setVisibility(View.GONE);
        });

        mDropdownOtherDoc2.setOnClickListener(v -> {
            mUploadOtherdocLayout_2.setVisibility(View.VISIBLE);
            mDropdownOtherDoc2.setVisibility(View.GONE);
            mDropUpOtherDoc2.setVisibility(View.VISIBLE);
        });
        mDropUpOtherDoc2.setOnClickListener(v -> {
            mUploadOtherdocLayout_2.setVisibility(View.GONE);
            mDropdownOtherDoc2.setVisibility(View.VISIBLE);
            mDropUpOtherDoc2.setVisibility(View.GONE);
        });
        mUploadOtherDocument1_button.setOnClickListener(v -> {
            SelectImageDailog();
        });
        mUploadOtherDocument2_button.setOnClickListener(v -> {
            SelectImageDailog();
        });
        send_to_financer_button.setOnClickListener(v -> {
            if (upload_ledger_success != 1){
                Toast.makeText(TransactionDetails_Activity.this, "Please upload a  Ledger Document!", Toast.LENGTH_SHORT).show();

            }else{
                // Toast.makeText(TransactionDetails_Activity.this, "Document Successfully sent to financer", Toast.LENGTH_SHORT).show();
                DealerdoctoFinance();
            }
        });

        ledger_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LedgerInfoDailog();
            }
        });
        itr_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ITRInfoDailog();
            }
        });
        tds_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TDSInfoDailog();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUserType.equals("Shop Keeper")){
                    Intent intentshopkeeper = new Intent(TransactionDetails_Activity.this, BusinessDetails_Activity.class);

                    startActivity(intentshopkeeper);

                }
                else if (mUserType.equals("Dealer")){
                    Intent intentdealer = new Intent(TransactionDetails_Activity.this, BusinessDetails_Activity.class);

                    startActivity(intentdealer);
                }
                else  {
                    Intent intentdealer = new Intent(TransactionDetails_Activity.this, BusinessDetails_Activity.class);
                    intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                    startActivity(intentdealer);
                }
            }
        });
        ////Ledger Dropdown/Dropup
        ledger_dropdown.setOnClickListener(v -> {
            ledger_dropdown.setVisibility(View.GONE);
            ledger_dropup2.setVisibility(View.VISIBLE);
            upld_ledger_layout.setVisibility(View.VISIBLE);

        });
        ledger_dropup2.setOnClickListener(v -> {
            ledger_dropup2.setVisibility(View.GONE);
            ledger_dropdown.setVisibility(View.VISIBLE);
            upld_ledger_layout.setVisibility(View.GONE);

        });

        ////Ledger Dropdown/Dropup
        invoice_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoice_dropdown.setVisibility(View.GONE);
                invoice_dropup2.setVisibility(View.VISIBLE);
                upld_Invoice_layout.setVisibility(View.VISIBLE);

            }
        });
        invoice_dropup2.setOnClickListener(v -> {
            invoice_dropup2.setVisibility(View.GONE);
            invoice_dropdown.setVisibility(View.VISIBLE);
            upld_Invoice_layout.setVisibility(View.GONE);

        });

        ////ITR Dropdown/Dropup
        dropdown_itr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_itr.setVisibility(View.GONE);
                itr_dropup2.setVisibility(View.VISIBLE);
                upld_itr_layout.setVisibility(View.VISIBLE);

            }
        });
        itr_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itr_dropup2.setVisibility(View.GONE);
                dropdown_itr.setVisibility(View.VISIBLE);
                upld_itr_layout.setVisibility(View.GONE);

            }
        });
        ////TDS Dropdown/Dropup
        Dropdown_TDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dropdown_TDS.setVisibility(View.GONE);
                TdS_dropup2.setVisibility(View.VISIBLE);
                upld_TDS_layout.setVisibility(View.VISIBLE);

            }
        });
        TdS_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TdS_dropup2.setVisibility(View.GONE);
                Dropdown_TDS.setVisibility(View.VISIBLE);
                upld_TDS_layout.setVisibility(View.GONE);

            }
        });
        upld_ledger_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                x = 10;
                y = 10;
                PDF_VALID = 6;
                mSelectedLedgerStatus =1;


            }
        });
        upld_invoice_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                x = 11;
                y = 11;
                PDF_VALID = 7;
                mSelectedInvoiceStatus = 1;


            }
        });
        upld_itr_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();

                x = 12;
                y = 12;
                PDF_VALID = 8;
                mSelectedITRStatus = 1;


            }
        });
        upld_tds_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImageDailog();
                x = 13;
                y = 13;
                PDF_VALID = 9;
                mSelectedTDSStatus = 1;

            }
        });

        ledger_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Ledger_Valid==1)){
                    upld_ledger_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
                   /* ledger_dropup2.setVisibility(View.GONE);
                    ledger_dropdown.setVisibility(View.VISIBLE);
                    upld_ledger_layout.setVisibility(View.GONE);
                    upld_ledger_succss.setVisibility(View.VISIBLE);*/

                    Toast.makeText(TransactionDetails_Activity.this, "Image Upload Successfully!", Toast.LENGTH_SHORT).show();
                    DealerLEDGERDoc(true);
                }

            }
        });
        invoice_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Invoice_Valid==1)){
                    upld_invoice_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
                    DealerINVOICEDoc();
                }

            }
        });
        itr_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ITR_Valid != 1){
                    upld_ITR_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
                    DealerITRDoc(true);
                }

            }
        });

        TDS_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TDS_Valid==1)){
                    upld_TDS_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
                    DealerTDSDoc(true);
                }

            }
        });

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(upload_ledger_success==1)){

                    Toast.makeText(TransactionDetails_Activity.this, "Please upload a  Ledger Document!", Toast.LENGTH_SHORT).show();

                }else{
                    dealer_doc_confirm();

                }

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mUserType.equals("Shop Keeper")){
                    Intent intentshopkeeper = new Intent(TransactionDetails_Activity.this, HomeShopkeeper.class);
                    intentshopkeeper.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                    startActivity(intentshopkeeper);

                }
                else if (mUserType.equals("Dealer")){
                    Intent intentdealer = new Intent(TransactionDetails_Activity.this, MainActivity.class);
                    intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                    startActivity(intentdealer);
                }
                else  {
                    Intent intentdealer = new Intent(TransactionDetails_Activity.this, MainActivity.class);
                    intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                    startActivity(intentdealer);
                }
            }
        });
        DealerLEDGERDoc(false);
        DealerITRDoc(false);
        DealerTDSDoc(false);
        getOtherDocument();

        otherDocumentAddListener = (position, otherDocumentAddItem) ->{
            otherDocAddItemArrayList.set(position, otherDocumentAddItem);
        };
        removeOtherDocumentListener = (position -> {
            otherDocAddItemArrayList.remove(position);
            otherDocumentAdapter.notifyItemRemoved(position);
        });
        mAddMoreButton.setOnClickListener(v -> {
            if (otherDocAddItemArrayList.size()<6){
                otherDocAddItemArrayList.add(new OtherDocumentAddItem(""));
                otherDocumentAdapter.notifyItemInserted(otherDocAddItemArrayList.size());
            }else {
                Toast.makeText(this, "Max Document's should be 6", Toast.LENGTH_SHORT).show();
            }
        });
        getOtherDocument();
    }
    private void DealerDocDelete() {

        custPrograssbar.prograssCreate(this);
        DealerDocDelete_POJO pojo = new DealerDocDelete_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE, getApplicationContext()));
        Log.e("DocType","-->>"+SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        if (mUserType.equals("Dealer")) {

            mDealerDocDeleteCall = restApis.DealerDocDelete(pojo);

            mDealerDocDeleteCall.enqueue(new Callback<DealerDocDeleteModel>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerDocDeleteModel> call, Response<DealerDocDeleteModel> response) {
                    if (response.body() != null&&response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();

                        response.body().getMessage();
                        response.body().getPayload().getType();
                        response.body().getPayload().getUsercode();
                        switch (SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())){
                            case "ITR":
                                upld_itr_buton.setImageResource(R.drawable.camera);
                                upld_itr_buton.getLayoutParams().height = 150;
                                upld_itr_buton.getLayoutParams().width = 150;
                                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteItrButton.setVisibility(View.GONE);
                                Toast.makeText(TransactionDetails_Activity.this,"ITR Document "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                upld_tds_buton.setImageResource(R.drawable.camera);
                                upld_tds_buton.getLayoutParams().height = 150;
                                upld_tds_buton.getLayoutParams().width = 150;
                                upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);

                                Toast.makeText(mContext, "TDS Document "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;

                        }




                    }else {

                        Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }


                }


                @Override
                public void onFailure(Call<DealerDocDeleteModel> call, Throwable t) {

                    Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }}

    private void getOtherDocument() {
        otherDocAddItemArrayList.add(new OtherDocumentAddItem(""));
        otherDocumentAdapter = new OtherDocumentAdapter(TransactionDetails_Activity.this, otherDocAddItemArrayList, otherDocumentAddListener, removeOtherDocumentListener);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mOtherDocRecyclerview.setLayoutManager(mLinearLayoutManager);
        mOtherDocRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mOtherDocRecyclerview.setAdapter(otherDocumentAdapter);
        otherDocumentAdapter.notifyDataSetChanged();
    }

    private void TDSInfoDailog() {

        dialogCondition.setContentView(R.layout.tds_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

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

    private void ITRInfoDailog() {

        dialogCondition.setContentView(R.layout.itr_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

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

    private void LedgerInfoDailog() {


        dialogCondition.setContentView(R.layout.ledger_info_dialog);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

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

       /* btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upload_ledger_success==1){
                    dealer_doc_confirm();
                } else {
                    Toast.makeText(TransactionDetails_Activity.this, "Please upload a  Ledger Document!", Toast.LENGTH_SHORT).show();

                }
            }
        });*/


    }
    private void DealerdoctoFinance() {

        custPrograssbar.prograssCreate(this);
        DealerdoctoFinance_POJO pojo = new DealerdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        if (mUserType.equals("Dealer")) {

                mDooctoFinanceCall = restApis.DealerdoctoFinance(pojo);

            mDooctoFinanceCall.enqueue(new Callback<DealerdoctoFinance_MODEL>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerdoctoFinance_MODEL> call, Response<DealerdoctoFinance_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        if (response.body().getCode()==200) {
                            Toast.makeText(TransactionDetails_Activity.this, "Document sent to Financer", Toast.LENGTH_SHORT).show();

                            usercode = response.body().getPayload().getUsercode();
                            doc_status=response.body().getPayload().getDoc_status();

                            Intent intent = new Intent(TransactionDetails_Activity.this, OEMDetailsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        custPrograssbar.closePrograssBar();
                    }


                }


                @Override
                public void onFailure(Call<DealerdoctoFinance_MODEL> call, Throwable t) {


                    Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
        else  if (mUserType.equals("SubDealer")) {

            mDooctoFinanceCall = restApis.SubDealerdoctoFinance(pojo);

            mDooctoFinanceCall.enqueue(new Callback<DealerdoctoFinance_MODEL>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerdoctoFinance_MODEL> call, Response<DealerdoctoFinance_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        if (response.body().getCode()==200) {
                            usercode = response.body().getPayload().getUsercode();
                            doc_status=response.body().getPayload().getDoc_status();

                            Toast.makeText(TransactionDetails_Activity.this, "Document sent to Financer", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TransactionDetails_Activity.this, OEMDetailsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        custPrograssbar.closePrograssBar();
                    }


                }


                @Override
                public void onFailure(Call<DealerdoctoFinance_MODEL> call, Throwable t) {


                    Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
        else  if (mUserType.equals("Shop Keeper")) {

            mDooctoFinanceCall = restApis.ShopkeeperdoctoFinance(pojo);

            mDooctoFinanceCall.enqueue(new Callback<DealerdoctoFinance_MODEL>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerdoctoFinance_MODEL> call, Response<DealerdoctoFinance_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        if (response.body().getCode()==200) {
                            usercode = response.body().getPayload().getUsercode();
                            doc_status=response.body().getPayload().getDoc_status();

                            Toast.makeText(TransactionDetails_Activity.this, "Ducument sent to Financer", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TransactionDetails_Activity.this, HomeShopkeeper.class);
                            intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        custPrograssbar.closePrograssBar();
                    }


                }


                @Override
                public void onFailure(Call<DealerdoctoFinance_MODEL> call, Throwable t) {


                    Toast.makeText(TransactionDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }




    }

    private void dealer_doc_confirm() {

        Dealer_doc_confirm_POJO pojo = new Dealer_doc_confirm_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<dealer_doc_confirm_MODEL> call = restApis.dealer_doc_confirm(pojo);
        call.enqueue(new Callback<dealer_doc_confirm_MODEL>() {
            @Override
            public void onResponse(Call<dealer_doc_confirm_MODEL> call, Response<dealer_doc_confirm_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode()==200) {
if(sendtofinancerbuttonvisible.equals("false")){
    send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_grey));
    send_to_financer_button.setEnabled(false);
    send_to_financer_button.setClickable(false);
}
else{
    send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_round));
    send_to_financer_button.setEnabled(true);
    send_to_financer_button.setClickable(true);
}

                        Toast.makeText(TransactionDetails_Activity.this, "All Document Submitted Successfully", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(TransactionDetails_Activity.this, MainActivity.class);
                        intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                        startActivity(intent);*/

                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<dealer_doc_confirm_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                    if (x == 10) {
                        upld_ledger_buton.setImageBitmap(bitmap);
                        upld_ledger_hint.setVisibility(View.GONE);
                        ledger_name.setVisibility(View.GONE);
                        ledger_view.setVisibility(View.GONE);
                        upld_ledger_buton.getLayoutParams().height = 300;
                        upld_ledger_buton.getLayoutParams().width = 300;
                        upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Ledger_Valid = 1;
                        mSelectedLedgerStatus = 0;
                    }else if (x == 11) {
                        upld_invoice_buton.setImageBitmap(bitmap);
                        upld_invoice_hint.setVisibility(View.GONE);
                        invoice_name.setVisibility(View.GONE);
                        upld_invoice_buton.getLayoutParams().height = 300;
                        upld_invoice_buton.getLayoutParams().width = 300;
                        upld_invoice_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Invoice_Valid = 1;
                        mSelectedInvoiceStatus = 0;
                    }else if (x == 12) {
                        upld_itr_buton.setImageBitmap(bitmap);
                        upld_ITR_hint.setVisibility(View.GONE);
                        itr_name.setVisibility(View.GONE);
                        itr_view.setVisibility(View.GONE);
                        upld_itr_buton.getLayoutParams().height = 300;
                        upld_itr_buton.getLayoutParams().width = 300;
                        upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        ITR_Valid = 1;
                        mSelectedITRStatus = 0;
                    }
                    else if (x == 13) {
                        upld_tds_buton.setImageBitmap(bitmap);
                        upld_TDS_hint.setVisibility(View.GONE);
                        tds_name.setVisibility(View.GONE);
                        tds_view.setVisibility(View.GONE);
                        upld_tds_buton.getLayoutParams().height = 300;
                        upld_tds_buton.getLayoutParams().width = 300;
                        upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        TDS_Valid = 1;
                        mSelectedTDSStatus = 0;

                    }

                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (y==10){
                upld_ledger_buton.setImageBitmap(thumbnail);
                upld_ledger_hint.setVisibility(View.GONE);
                ledger_name.setVisibility(View.GONE);
                upld_ledger_buton.getLayoutParams().height = 300;
                upld_ledger_buton.getLayoutParams().width = 300;
                upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Ledger_Valid = 1;
                mSelectedLedgerStatus = 0;
            }
            else if (y==11){
                upld_invoice_buton.setImageBitmap(thumbnail);
                upld_invoice_hint.setVisibility(View.GONE);
                invoice_name.setVisibility(View.GONE);
                upld_invoice_buton.getLayoutParams().height = 300;
                upld_invoice_buton.getLayoutParams().width = 300;
                upld_invoice_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Invoice_Valid = 1;
                mSelectedInvoiceStatus = 0;
            }
            else if (y==12){
                upld_itr_buton.setImageBitmap(thumbnail);
                upld_ITR_hint.setVisibility(View.GONE);
                itr_name.setVisibility(View.GONE);
                upld_itr_buton.getLayoutParams().height = 300;
                upld_itr_buton.getLayoutParams().width = 300;
                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                ITR_Valid = 1;
                mSelectedITRStatus = 0;
            }else if (y==13){
                upld_tds_buton.setImageBitmap(thumbnail);
                upld_TDS_hint.setVisibility(View.GONE);
                tds_name.setVisibility(View.GONE);
                upld_tds_buton.getLayoutParams().height = 300;
                upld_tds_buton.getLayoutParams().width = 300;
                upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                TDS_Valid = 1;
                mSelectedTDSStatus = 0;
            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this, "Image Saved!",Toast.LENGTH_SHORT).show();
            if (mSelectedLedgerStatus == 1) {
                Ledger_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_ledger_hint.setVisibility(View.GONE);

            }else if (mSelectedInvoiceStatus == 1) {
                Invoice_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_invoice_hint.setVisibility(View.GONE);
            }else if (mSelectedITRStatus == 1) {
                ITR_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_ITR_hint.setVisibility(View.GONE);
            }else if (mSelectedTDSStatus == 1) {
                TDS_Valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_TDS_hint.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);

        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir();


        }

        try {

            File f = new
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");

            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);


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

        if (mSelectedLedgerStatus == 1) {

            mLedgerFile = mFile;
            ledger_name.setText(Pdf_Name);
            ledger_view.setVisibility(View.VISIBLE);
            upld_ledger_buton.setImageResource(R.drawable.c3);
            ledger_name.setVisibility(View.VISIBLE);
            ledger_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPDF2(mFile);
                }
            });
            upld_ledger_buton.getLayoutParams().height = 150;
            upld_ledger_buton.getLayoutParams().width = 150;
            upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedInvoiceStatus == 1) {

            mInvoiceFile = mFile;
            invoice_name.setText(Pdf_Name);
            upld_invoice_buton.setImageResource(R.drawable.c3);
            invoice_name.setVisibility(View.VISIBLE);
            upld_invoice_buton.getLayoutParams().height = 150;
            upld_invoice_buton.getLayoutParams().width = 150;
            upld_invoice_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedITRStatus == 1) {

            mITRFile = mFile;
            itr_name.setText(Pdf_Name);
            itr_view.setVisibility(View.VISIBLE);
            upld_itr_buton.setImageResource(R.drawable.c3);
            itr_name.setVisibility(View.VISIBLE);
            itr_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPDF2(mFile);
                }
            });
            upld_itr_buton.getLayoutParams().height = 150;
            upld_itr_buton.getLayoutParams().width = 150;
            upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedTDSStatus == 1) {

            mTDSFile = mFile;
            tds_name.setText(Pdf_Name);
            tds_view.setVisibility(View.VISIBLE);

            upld_tds_buton.setImageResource(R.drawable.c3);
            tds_name.setVisibility(View.VISIBLE);
            tds_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPDF2(mFile);
                }
            });

            upld_tds_buton.getLayoutParams().height = 150;
            upld_tds_buton.getLayoutParams().width = 150;
            upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
//            mSelectedUDCStatus = 0;
        }


    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }

    private void DealerLEDGERDoc(boolean mLEDGERDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedLedgerStatus == 1) {
            idFile = mLedgerFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("LEDGER", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals("Dealer")) {
            if (mLEDGERDocStatus) {
                mLEDGERDocCall = restApis.DealerLEDGERDoc(user_code, vechileDocUpload2);
            } else {
                mLEDGERDocCall = restApis.GetDealerLEDGERDoc(data);
            }
            mLEDGERDocCall.enqueue(new Callback<DealerLEDGERDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerLEDGERDoc_MODEL> call, Response<DealerLEDGERDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        ledger_dropup2.setVisibility(View.GONE);
                        ledger_dropdown.setVisibility(View.VISIBLE);
                        upld_ledger_layout.setVisibility(View.GONE);
                      //  upld_ledger_succss.setVisibility(View.VISIBLE);
                        ledger_save_button.setText("Update");
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            sendtofinancerbuttonvisible="false";
                            upld_ledger_succss.setVisibility(View.GONE);

                        } else {
                            upld_ledger_succss.setVisibility(View.VISIBLE);
                            send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_round));
                            send_to_financer_button.setEnabled(true);
                            send_to_financer_button.setClickable(true);
                        }
                        mSelectedLedgerStatus = 0;
                        upload_ledger_success = 1;
                        dropdown_itr.setVisibility(View.GONE);
                        itr_dropup2.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.VISIBLE);

                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        String   mLEDGERDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mLEDGERDocURL);

                        if(valuepdf){

                            File mFile = new File(mLEDGERDocURL);

                            Pdf_Name = mFile.getName();
                            ledger_name.setVisibility(View.VISIBLE);
                            ledger_view.setVisibility(View.VISIBLE);
                            upld_ledger_buton.setImageResource(R.drawable.c3);
                            ledger_name.setText(Pdf_Name);
                            ledger_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mLEDGERDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mLEDGERDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_ledger_buton.setImageBitmap(mIcon_val);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerLEDGERDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals("SubDealer")) {
            if (mLEDGERDocStatus) {
                mLEDGERDocCall = restApis.SubDealerLEDGERDoc(user_code, vechileDocUpload2);
            } else {
                mLEDGERDocCall = restApis.GetSubDealerLEDGERDoc(data);
            }
            mLEDGERDocCall.enqueue(new Callback<DealerLEDGERDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerLEDGERDoc_MODEL> call, Response<DealerLEDGERDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        ledger_dropup2.setVisibility(View.GONE);
                        ledger_dropdown.setVisibility(View.VISIBLE);
                        upld_ledger_layout.setVisibility(View.GONE);
                      //  upld_ledger_succss.setVisibility(View.VISIBLE);
                        ledger_save_button.setText("Update");
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            sendtofinancerbuttonvisible="false";

                            upld_ledger_succss.setVisibility(View.GONE);
                        } else {
                            send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_round));
                            send_to_financer_button.setEnabled(true);
                            send_to_financer_button.setClickable(true);
                            upld_ledger_succss.setVisibility(View.VISIBLE);
                        }
                        mSelectedLedgerStatus = 0;
                        upload_ledger_success = 1;
                        dropdown_itr.setVisibility(View.GONE);
                        itr_dropup2.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.VISIBLE);

                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        String   mLEDGERDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mLEDGERDocURL);

                        if(valuepdf){

                            File mFile = new File(mLEDGERDocURL);

                            Pdf_Name = mFile.getName();
                            ledger_name.setVisibility(View.VISIBLE);
                            ledger_view.setVisibility(View.VISIBLE);
                            upld_ledger_buton.setImageResource(R.drawable.c3);
                            ledger_name.setText(Pdf_Name);
                            ledger_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mLEDGERDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mLEDGERDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_ledger_buton.setImageBitmap(mIcon_val);
                                upld_ledger_buton.getLayoutParams().height = 300;
                                upld_ledger_buton.getLayoutParams().width = 300;
                                upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerLEDGERDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
        else if (mUserType.equals("Shop Keeper")) {
            if (mLEDGERDocStatus) {
                mLEDGERDocCall = restApis.ShopkeeperLEDGERDoc(user_code, vechileDocUpload2);
            } else {
                mLEDGERDocCall = restApis.GetShopkeeperLEDGERDoc(data);
            }
            mLEDGERDocCall.enqueue(new Callback<DealerLEDGERDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerLEDGERDoc_MODEL> call, Response<DealerLEDGERDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        ledger_dropup2.setVisibility(View.GONE);
                        ledger_dropdown.setVisibility(View.VISIBLE);
                        upld_ledger_layout.setVisibility(View.GONE);
                       // upld_ledger_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            sendtofinancerbuttonvisible="false";
                            upld_ledger_succss.setVisibility(View.GONE);
                        } else {
                            upld_ledger_succss.setVisibility(View.VISIBLE);
                        }
                        ledger_save_button.setText("Update");
                        mSelectedLedgerStatus = 0;
                        upload_ledger_success = 1;
                        dropdown_itr.setVisibility(View.GONE);
                        itr_dropup2.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.VISIBLE);
                        send_to_financer_button.setBackground(getResources().getDrawable(R.drawable.button_round));
                        send_to_financer_button.setEnabled(true);
                        send_to_financer_button.setClickable(true);
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        String   mLEDGERDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mLEDGERDocURL);

                        if(valuepdf){

                            File mFile = new File(mLEDGERDocURL);

                            Pdf_Name = mFile.getName();
                            ledger_name.setVisibility(View.VISIBLE);
                            ledger_view.setVisibility(View.VISIBLE);
                            upld_ledger_buton.setImageResource(R.drawable.c3);
                            ledger_name.setText(Pdf_Name);
                            ledger_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mLEDGERDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_ledger_buton.setImageBitmap(mIcon_val);
                                upld_ledger_buton.getLayoutParams().height = 300;
                                upld_ledger_buton.getLayoutParams().width = 300;
                                upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerLEDGERDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void DealerTDSDoc(boolean mTDSDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedTDSStatus == 1) {
            idFile = mTDSFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("TDS", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals("Dealer")) {
            if (mTDSDocStatus) {
                mTDSDocCall = restApis.DealerTDSDoc(user_code, vechileDocUpload2);
            } else {
                mTDSDocCall = restApis.GetDealerTDSDoc(data);
            }
            mTDSDocCall.enqueue(new Callback<DealerTDSDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerTDSDoc_MODEL> call, Response<DealerTDSDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        TdS_dropup2.setVisibility(View.GONE);
                        Dropdown_TDS.setVisibility(View.VISIBLE);
                        upld_TDS_layout.setVisibility(View.GONE);
                      //  upld_tds_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_tds_succss.setVisibility(View.GONE);
                        } else {
                            upld_tds_succss.setVisibility(View.VISIBLE);
                        }
                        TDS_save_button.setText("Update");
                        mSelectedTDSStatus = 0;
                        upload_tds_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mTDSDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mTDSDocURL);

                        if(valuepdf){

                            File mFile = new File(mTDSDocURL);

                            Pdf_Name = mFile.getName();
                            tds_name.setVisibility(View.VISIBLE);
                            tds_view.setVisibility(View.VISIBLE);
                            upld_tds_buton.setImageResource(R.drawable.c3);
                            tds_name.setText(Pdf_Name);
                            tds_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mTDSDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mTDSDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_tds_buton.setImageBitmap(mIcon_val);
                                upld_tds_buton.getLayoutParams().height = 300;
                                upld_tds_buton.getLayoutParams().width = 300;
                                upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerTDSDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals("SubDealer")) {
            if (mTDSDocStatus) {
                mTDSDocCall = restApis.SubDealerTDSDoc(user_code, vechileDocUpload2);
            } else {
                mTDSDocCall = restApis.GetSubDealerTDSDoc(data);
            }
            mTDSDocCall.enqueue(new Callback<DealerTDSDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerTDSDoc_MODEL> call, Response<DealerTDSDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        TdS_dropup2.setVisibility(View.GONE);
                        Dropdown_TDS.setVisibility(View.VISIBLE);
                        upld_TDS_layout.setVisibility(View.GONE);
                   //     upld_tds_succss.setVisibility(View.VISIBLE);
                        TDS_save_button.setText("Update");
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_tds_succss.setVisibility(View.GONE);
                        } else {
                            upld_tds_succss.setVisibility(View.VISIBLE);
                        }
                        mSelectedTDSStatus = 0;
                        upload_tds_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mTDSDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mTDSDocURL);

                        if(valuepdf){

                            File mFile = new File(mTDSDocURL);

                            Pdf_Name = mFile.getName();
                            tds_name.setVisibility(View.VISIBLE);
                            tds_view.setVisibility(View.VISIBLE);
                            upld_tds_buton.setImageResource(R.drawable.c3);
                            tds_name.setText(Pdf_Name);
                            tds_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mTDSDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mTDSDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_tds_buton.setImageBitmap(mIcon_val);
                                upld_tds_buton.getLayoutParams().height = 300;
                                upld_tds_buton.getLayoutParams().width = 300;
                                upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerTDSDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
        else if (mUserType.equals("Shop Keeper")) {
            if (mTDSDocStatus) {
                mTDSDocCall = restApis.ShopkeeperTDSDoc(user_code, vechileDocUpload2);
            } else {
                mTDSDocCall = restApis.GetShopkeeperTDSDoc(data);
            }
            mTDSDocCall.enqueue(new Callback<DealerTDSDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerTDSDoc_MODEL> call, Response<DealerTDSDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        TdS_dropup2.setVisibility(View.GONE);
                        Dropdown_TDS.setVisibility(View.VISIBLE);
                        upld_TDS_layout.setVisibility(View.GONE);
                      //  upld_tds_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_tds_succss.setVisibility(View.GONE);
                        } else {
                            upld_tds_succss.setVisibility(View.VISIBLE);
                        }
                        TDS_save_button.setText("Update");
                        mSelectedTDSStatus = 0;
                        upload_tds_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mTDSDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mTDSDocURL);

                        if(valuepdf){

                            File mFile = new File(mTDSDocURL);

                            Pdf_Name = mFile.getName();
                            tds_name.setVisibility(View.VISIBLE);
                            tds_view.setVisibility(View.VISIBLE);
                            upld_tds_buton.setImageResource(R.drawable.c3);
                            tds_name.setText(Pdf_Name);
                            tds_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mTDSDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mTDSDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_tds_buton.setImageBitmap(mIcon_val);
                                upld_tds_buton.getLayoutParams().height = 300;
                                upld_tds_buton.getLayoutParams().width = 300;
                                upld_tds_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerTDSDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void DealerINVOICEDoc() {


        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

        File idFile;



        if (mSelectedInvoiceStatus == 1) {
            idFile = mInvoiceFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("INVOICE", idFile.getName(), mFile1);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerINVOICEDoc_MODEL> call = restApis.DealerINVOICEDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerINVOICEDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerINVOICEDoc_MODEL> call, Response<DealerINVOICEDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    invoice_dropup2.setVisibility(View.GONE);
                    invoice_dropdown.setVisibility(View.VISIBLE);
                    upld_Invoice_layout.setVisibility(View.GONE);
                  //  upld_invoice_succss.setVisibility(View.VISIBLE);
                    invoice_save_button.setText("Update");
                    if (( response.body().getPayload().getSelfie()).equals("NA")) {
                        upld_invoice_succss.setVisibility(View.GONE);
                    } else {
                        upld_invoice_succss.setVisibility(View.VISIBLE);
                    }
                    mSelectedInvoiceStatus = 0;
                    upload_invoice_success = 1;
                    Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DealerINVOICEDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerITRDoc(boolean mITRDocStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile;
        if (mSelectedITRStatus == 1) {
            idFile = mITRFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("ITR", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals("Dealer")) {
            if (mITRDocStatus) {
                mITRDocCall = restApis.DealerITRDoc(user_code, vechileDocUpload2);
            } else {
                mITRDocCall = restApis.GetDealerITRDoc(data);
            }
            mITRDocCall.enqueue(new Callback<DealerITRDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerITRDoc_MODEL> call, Response<DealerITRDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        itr_dropup2.setVisibility(View.GONE);
                        dropdown_itr.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.GONE);
                     //   upld_itr_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_itr_succss.setVisibility(View.GONE);
                        } else {
                            upld_itr_succss.setVisibility(View.VISIBLE);
                        }
                        itr_save_button.setText("Update");
                        mSelectedITRStatus = 0;
                        upload_itr_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mITRDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mITRDocURL);

                        if(valuepdf){

                            File mFile = new File(mITRDocURL);

                            Pdf_Name = mFile.getName();
                            itr_name.setVisibility(View.VISIBLE);
                            itr_view.setVisibility(View.VISIBLE);
                            upld_itr_buton.setImageResource(R.drawable.c3);
                            itr_name.setText(Pdf_Name);
                            itr_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mITRDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mITRDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_itr_buton.setImageBitmap(mIcon_val);
                                upld_itr_buton.getLayoutParams().height = 300;
                                upld_itr_buton.getLayoutParams().width = 300;
                                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerITRDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals("SubDealer")) {
            if (mITRDocStatus) {
                mITRDocCall = restApis.SubDealerITRDoc(user_code, vechileDocUpload2);
            } else {
                mITRDocCall = restApis.GetSubDealerITRDoc(data);
            }
            mITRDocCall.enqueue(new Callback<DealerITRDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerITRDoc_MODEL> call, Response<DealerITRDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        itr_dropup2.setVisibility(View.GONE);
                        dropdown_itr.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.GONE);
                       // upld_itr_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_itr_succss.setVisibility(View.GONE);
                        } else {
                            upld_itr_succss.setVisibility(View.VISIBLE);
                        }
                        itr_save_button.setText("Update");
                        mSelectedITRStatus = 0;
                        upload_itr_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mITRDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mITRDocURL);

                        if(valuepdf){

                            File mFile = new File(mITRDocURL);

                            Pdf_Name = mFile.getName();
                            itr_name.setVisibility(View.VISIBLE);
                            itr_view.setVisibility(View.VISIBLE);
                            upld_itr_buton.setImageResource(R.drawable.c3);
                            itr_name.setText(Pdf_Name);
                            itr_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mITRDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mITRDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_itr_buton.setImageBitmap(mIcon_val);
                                upld_itr_buton.getLayoutParams().height = 300;
                                upld_itr_buton.getLayoutParams().width = 300;
                                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerITRDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
        else if (mUserType.equals("Shop Keeper")) {
            if (mITRDocStatus) {
                mITRDocCall = restApis.ShopkeeperITRDoc(user_code, vechileDocUpload2);
            } else {
                mITRDocCall = restApis.GetShopkeeperITRDoc(data);
            }
            mITRDocCall.enqueue(new Callback<DealerITRDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerITRDoc_MODEL> call, Response<DealerITRDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        itr_dropup2.setVisibility(View.GONE);
                        dropdown_itr.setVisibility(View.VISIBLE);
                        upld_itr_layout.setVisibility(View.GONE);
                   //     upld_itr_succss.setVisibility(View.VISIBLE);
                        if (( response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_itr_succss.setVisibility(View.GONE);
                        } else {
                            upld_itr_succss.setVisibility(View.VISIBLE);
                        }
                        itr_save_button.setText("Update");
                        mSelectedITRStatus = 0;
                        upload_itr_success = 1;
                        Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        String   mITRDocURL = response.body().getPayload().getSelfie();
                        valuepdf= new CheckPDFOrImage().checkIsPDFOrImage(mITRDocURL);

                        if(valuepdf){

                            File mFile = new File(mITRDocURL);

                            Pdf_Name = mFile.getName();
                            itr_name.setVisibility(View.VISIBLE);
                            itr_view.setVisibility(View.VISIBLE);
                            upld_itr_buton.setImageResource(R.drawable.c3);
                            itr_name.setText(Pdf_Name);
                            itr_view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CheckPDFOrImage().openPDFFromURL(TransactionDetails_Activity.this,mITRDocURL);
                                }
                            });
                        }
                        else {
                            try {
                                URL newurl = new URL(mITRDocURL);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upld_itr_buton.setImageBitmap(mIcon_val);
                                upld_itr_buton.getLayoutParams().height = 300;
                                upld_itr_buton.getLayoutParams().width = 300;
                                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerITRDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }
    private void showPDF2(File mFile) {

        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri URI = FileProvider.getUriForFile(TransactionDetails_Activity.this, TransactionDetails_Activity.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(URI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        TransactionDetails_Activity.this.startActivity(viewPdf);
    }

}