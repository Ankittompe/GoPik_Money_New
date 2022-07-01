package com.qts.gopik_loan.Supply_Chain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.IOUtils;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Fragment.My_Mall_Fragment;
import com.qts.gopik_loan.Model.DealerAGREEMENTDoc_MODEL;
import com.qts.gopik_loan.Model.DealerAadharFrontDoc_MODEL;
import com.qts.gopik_loan.Model.DealerAdharBackDoc_MODEL;
import com.qts.gopik_loan.Model.DealerBUSINESSPROOFDoc_MODEL;
import com.qts.gopik_loan.Model.DealerDISB_BANKDoc_MODEL;
import com.qts.gopik_loan.Model.DealerESCROWDoc_MODEL;
import com.qts.gopik_loan.Model.DealerGSTDoc_MODEL;
import com.qts.gopik_loan.Model.DealerINVOICEDoc_MODEL;
import com.qts.gopik_loan.Model.DealerITRDoc_MODEL;
import com.qts.gopik_loan.Model.DealerLEDGERDoc_MODEL;
import com.qts.gopik_loan.Model.DealerPanDoc_MODEL;
import com.qts.gopik_loan.Model.DealerSelfieDoc_MODEL;
import com.qts.gopik_loan.Model.DealerTDSDoc_MODEL;
import com.qts.gopik_loan.Model.DealerUDCDoc_MODEL;
import com.qts.gopik_loan.Model.Dealer_adhar_molldoc_MODEL;
import com.qts.gopik_loan.Model.Dealer_bank_molldoc_MODEL;
import com.qts.gopik_loan.Model.Dealer_pan_molldoc_MODEL;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Image_Upload_SupplyChain extends AppCompatActivity implements PickiTCallbacks {
    public int x = 0;
    public int y = 0;
    public int PDF_VALID = 0;
    public int Selfie = 0;
    String filePath = "";
    private final int PERMISSION_REQUEST_CODE = 3;
    PickiT pickiT;
    private int GALLERY = 1, CAMERA = 2;
    Integer Bill_Image_saved_status1 = 0;
    Integer ApplicationImage_saved_status = 0;
    Integer MarginPhoto_saved_status = 0;
    Integer Income_saved_status = 0;
    Integer Bank_statment_saved_status = 0;
    Integer adhar_front_valid1 = 0;
    Integer adhar_front_valid2 = 0;
    Integer adhar_back_valid1 = 0;
    Integer adhar_back_valid2 = 0;
    Integer Pan_valid = 0;
    Integer Business_valid = 0;
    Integer UDC_Valid = 0;
    Integer Agreement_Valid = 0;
    Integer Escrow_Valid = 0;
    Integer Disb_Bank_Valid = 0;
    Integer Ledger_Valid = 0;
    Integer Invoice_Valid = 0;
    Integer ITR_Valid = 0;
    Integer TDS_Valid = 0;
    Integer GST_Valid = 0;

    TextView btsend, save1, save2, save3, save4;
    Integer save_selfie_status = 0;
    Integer save_adhar_front_status = 0;
    Integer save_adhar_back_status = 0;
    Integer save_pan_status = 0;
    Integer save_business_status = 0;
    Integer save_udc_status = 0;
    Integer save_agreemnt_status = 0;
    Integer save_escrow_status = 0;
    Integer save_disb_bank_status = 0;
    Integer save_ledger_status = 0;
    Integer save_invoice_status = 0;
    Integer save_itr_status = 0;
    Integer save_tds_status = 0;
    Integer save_gst_status = 0;
    Integer upld_selfie = 0;
    Integer upld_selfie_scss = 0;

    Integer save_success4 = 0;

    ImageView arrow,hometoolbar;
    ImageView adhaar_upld_sucss,upload_selfie,
            bill_upld_sucss, upld_mrgn_succ, income_success_img, land_upld_sccs, upload_bank_succs, upld_application_succss;
    private static final int FILE_CHOOSER=123;
    ImageView upload_adhaar,upload_adhaar_back,upload_pancard_front_button,upld_pan_back_button,upload_bank_statement,upload_salaryslip;
    ImageView
            dropdown1, dropdown_App_photo, dropdown_margin_photo, dropdown_income_photo, dropdown_bank_photo, dropdown_lanf_photo;
    ImageView dropup1, dropup2, dropup3, dropup4, dropup5, dropup6;
    LinearLayout upload_adhaar_front_layout, application_copy_layout, margin_copy_layout, income_proof_layout, bank_statement_layout, land_docu_layout;
    TextView unsaved_img_error_massage,unsaved_img_error_massage2,unsaved_img_error_massage3,unsaved_img_error_massage4,unsaved_img_error_massage5;
    TextView
            upld_img_hint, upld_appltn_hint, upld_mrgn_hint, upld_income_proof, upld_bank_stmnt_hint, error1;
    TextView uploadelectricitybill,pdf_name,save_selfie,save_adhar_front,save1_adhaar_back,save_pan_front,save_business,udc_save_button,agreement_save_button;
    TextView escrow_save_button,disburse_bank_save_button,ledger_save_button,invoice_save_button,itr_save_button,TDS_save_button,Gst_save_button;
    ImageView pdf_upload_success;

    ImageView dropdown_pan_front,dropdown1_adhar_front,dropdown1_selfie,dropdown1_adhar_back,dropdown_business_proof,dropdown_udc,agreement_dropdown;
    ImageView dropdown_pan_back,dropup2_pan_back,dropup1_adhar_front,dropup1_selfie,dropup1_adhar_back,dropup2_pan_front,dropup2_business_proof,dropup2_udc,agreement_dropup2;
    ImageView escrow_dropdown,escrow_dropup2,disb_bank_dropdown,disb_bank_dropup2,ledger_dropdown,ledger_dropup2;
    LinearLayout upload_selfie_layout,adhaar_back_layout,pan_front_layout,upld_business_layout,upld_udc_layout,upld_agreement_layout;
    LinearLayout upld_escrow_layout,upld_disburse_layout,upld_ledger_layout,upld_Invoice_layout,upld_itr_layout,upld_TDS_layout,upld_Gst_layout;
    ImageView invoice_dropdown,invoice_dropup2,dropdown_itr,itr_dropup2,Dropdown_TDS,TdS_dropup2,GST_DROPDOWN,Gst_dropup2;

    TextView upld_selfie_hint,adhaar_back_hint,upld_pan_front_hint,upld_business_hint,upld_udc_hint,upld_agreement_hint,upld_escrow_hint,upld_disb_bank_hint,upld_ledger_hint,upld_ITR_hint,upld_invoice_hint;
    ImageView selfie_upld_sucss,adhaar_upld_back_sucss,upld_busines_succss,upld_udc_succss,upld_agreement_succss,upld_ecsrow_succss,upld_pan_front_success,upld_disb_bank_succss,upld_ledger_succss,upld_invoice_succss,upld_Gst_succss,upld_itr_succss,upld_tds_succss;

    ImageView upld_business_buton,upld_udc_buton,upld_agreement_buton;
    ImageView upld_escrow_buton,upld_disburse_bank_buton,upld_ledger_buton,upld_invoice_buton,upld_itr_buton,upld_tds_buton,upld_Gst_buton;

    TextView upld_TDS_hint,upld_Gst_hint;
    //Business
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
    //Ledger
    File mLedgerFile = null;
    int  mSelectedLedgerStatus = 0;
    //Invoice
    File mInvoiceFile = null;
    int  mSelectedInvoiceStatus = 0;
    //ITR
    File mITRFile = null;
    int  mSelectedITRStatus = 0;
    //TDS
    File mTDSFile = null;
    int  mSelectedTDSStatus = 0;
    //GST
    File mGSTFile = null;
    int  mSelectedGSTStatus = 0;
    TextView adhar_front_err_msg,adhar_back_err_msg,pan_front_err_msg,business_err_msg,udc_err_msg,agreement_err_msg,escrow_err_msg,disb_bank_err_msg,ledger_err_msg,invoice_err_msg,itr_err_msg,tds_err_msg,Gst_err_msg;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_supply_chain);




        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        adhar_front_err_msg = findViewById(R.id.adhar_front_err_msg);
        adhar_back_err_msg = findViewById(R.id.adhar_back_err_msg);
        pan_front_err_msg = findViewById(R.id.pan_front_err_msg);
        business_err_msg = findViewById(R.id.business_err_msg);
        udc_err_msg = findViewById(R.id.udc_err_msg);
        agreement_err_msg = findViewById(R.id.agreement_err_msg);
        escrow_err_msg = findViewById(R.id.escrow_err_msg);
        disb_bank_err_msg = findViewById(R.id.disb_bank_err_msg);
        ledger_err_msg = findViewById(R.id.ledger_err_msg);
        invoice_err_msg = findViewById(R.id.invoice_err_msg);
        itr_err_msg = findViewById(R.id.itr_err_msg);
        tds_err_msg = findViewById(R.id.tds_err_msg);
        Gst_err_msg = findViewById(R.id.Gst_err_msg);


        upld_TDS_hint = findViewById(R.id.upld_TDS_hint);
        upld_Gst_hint = findViewById(R.id.upld_Gst_hint);
        upld_Gst_succss = findViewById(R.id.upld_Gst_succss);
        upld_itr_succss = findViewById(R.id.upld_itr_succss);
        upld_tds_succss = findViewById(R.id.upld_tds_succss);
        upld_Gst_buton = findViewById(R.id.upld_Gst_buton);
        upld_tds_buton = findViewById(R.id.upld_tds_buton);
        upld_itr_buton = findViewById(R.id.upld_itr_buton);
        upld_ITR_hint = findViewById(R.id.upld_ITR_hint);
        upld_invoice_succss = findViewById(R.id.upld_invoice_succss);
        upld_invoice_hint = findViewById(R.id.upld_invoice_hint);
        upld_invoice_buton = findViewById(R.id.upld_invoice_buton);
        upld_ledger_succss = findViewById(R.id.upld_ledger_succss);
        upld_ledger_buton = findViewById(R.id.upld_ledger_buton);
        upld_ledger_hint = findViewById(R.id.upld_ledger_hint);
        upld_disb_bank_succss = findViewById(R.id.upld_disb_bank_succss);
        upld_disb_bank_hint = findViewById(R.id.upld_disb_bank_hint);
        upld_disburse_bank_buton = findViewById(R.id.upld_disburse_bank_buton);
        upld_ecsrow_succss = findViewById(R.id.upld_ecsrow_succss);
        upld_escrow_buton = findViewById(R.id.upld_escrow_buton);
        upld_agreement_succss = findViewById(R.id.upld_agreement_succss);
        upld_agreement_hint = findViewById(R.id.upld_agreement_hint);
        upld_agreement_buton = findViewById(R.id.upld_agreement_buton);
        upld_udc_buton = findViewById(R.id.upld_udc_buton);
        selfie_upld_sucss = findViewById(R.id.selfie_upld_sucss);
        adhaar_upld_back_sucss = findViewById(R.id.adhaar_upld_back_sucss);
        upld_busines_succss = findViewById(R.id.upld_busines_succss);
        upld_udc_succss = findViewById(R.id.upld_udc_succss);
        upld_pan_front_success = findViewById(R.id.upld_pan_front_success);

        upld_selfie_hint = findViewById(R.id.upld_selfie_hint);
        adhaar_back_hint = findViewById(R.id.adhaar_back_hint);
        upld_pan_front_hint = findViewById(R.id.upld_pan_front_hint);
        upld_business_hint = findViewById(R.id.upld_business_hint);
        upld_escrow_hint = findViewById(R.id.upld_escrow_hint);
        upld_udc_hint = findViewById(R.id.upld_udc_hint);

        //Adhaar
        upload_adhaar = (ImageView) findViewById(R.id.upload_adhaar);
        upload_adhaar_back = (ImageView) findViewById(R.id.upload_adhaar_back);
        //Pan Card
        upload_pancard_front_button = (ImageView) findViewById(R.id.upload_pancard_front_button);
        upld_pan_back_button = (ImageView) findViewById(R.id.upld_pan_back_button);
        //Business Proof
        upload_bank_statement = (ImageView) findViewById(R.id.upload_bank_statement);
        upload_salaryslip = (ImageView) findViewById(R.id.upload_salaryslip);
        upld_business_buton = (ImageView) findViewById(R.id.upld_business_buton);

        upload_selfie= (ImageView) findViewById(R.id.upload_selfie);
        uploadelectricitybill = (TextView) findViewById(R.id.uploadelectricitybill);
        pdf_name = (TextView) findViewById(R.id.pdf_name);
        pdf_upload_success = (ImageView) findViewById(R.id.pdf_upload_success);

        //Dropdown Selfie
        dropdown1_selfie = findViewById(R.id.dropdown1_selfie);
        dropup1_selfie = findViewById(R.id.dropup1_selfie);

        dropdown1_adhar_front = findViewById(R.id.dropdown1_adhar_front);
        dropup1_adhar_front = findViewById(R.id.dropup1_adhar_front);

        //Dropdown Adhar back
        dropdown1_adhar_back = findViewById(R.id.dropdown1_adhar_back);
        dropup1_adhar_back = findViewById(R.id.dropup1_adhar_back);

        //Dropdown PAN
        dropdown_pan_front = findViewById(R.id.dropdown_pan_front);
        dropup2_pan_front = findViewById(R.id.dropup2_pan_front);
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
        //Gst
        GST_DROPDOWN = findViewById(R.id.GST_DROPDOWN);
        Gst_dropup2 = findViewById(R.id.Gst_dropup2);




        dropup2 = findViewById(R.id.dropup2);
        dropup3 = findViewById(R.id.dropup3);
        dropup4 = findViewById(R.id.dropup4);

        btsend = (TextView) findViewById(R.id.btsend);
        save1 = (TextView) findViewById(R.id.save1);
        save2 = (TextView) findViewById(R.id.save2);
        save3 = (TextView) findViewById(R.id.save3);
        save4 = (TextView) findViewById(R.id.save4);

        //Save Button
        save_selfie= (TextView) findViewById(R.id.save_selfie);
        save_adhar_front= (TextView) findViewById(R.id.save_adhar_front);
        save1_adhaar_back= (TextView) findViewById(R.id.save1_adhaar_back);
        save_pan_front= (TextView) findViewById(R.id.save_pan_front);
        save_business= (TextView) findViewById(R.id.save_business);
        udc_save_button= (TextView) findViewById(R.id.udc_save_button);
        agreement_save_button= (TextView) findViewById(R.id.agreement_save_button);
        escrow_save_button= (TextView) findViewById(R.id.escrow_save_button);
        disburse_bank_save_button= (TextView) findViewById(R.id.disburse_bank_save_button);
        ledger_save_button= (TextView) findViewById(R.id.ledger_save_button);
        invoice_save_button= (TextView) findViewById(R.id.invoice_save_button);
        itr_save_button = (TextView) findViewById(R.id.itr_save_button);
        TDS_save_button = (TextView) findViewById(R.id.TDS_save_button);
        Gst_save_button = (TextView) findViewById(R.id.Gst_save_button);

        //Layouts
        upload_selfie_layout = (LinearLayout)findViewById(R.id.upload_selfie_layout);
        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        adhaar_back_layout = findViewById(R.id.adhaar_back_layout);
        pan_front_layout = findViewById(R.id.pan_front_layout);
        upld_business_layout = findViewById(R.id.upld_business_layout);
        upld_udc_layout = findViewById(R.id.upld_udc_layout);
        upld_agreement_layout = findViewById(R.id.upld_agreement_layout);
        upld_escrow_layout = findViewById(R.id.upld_escrow_layout);
        upld_disburse_layout = findViewById(R.id.upld_disburse_layout);
        upld_ledger_layout = findViewById(R.id.upld_ledger_layout);
        upld_Invoice_layout = findViewById(R.id.upld_Invoice_layout);
        upld_itr_layout = findViewById(R.id.upld_itr_layout);
        upld_TDS_layout = findViewById(R.id.upld_TDS_layout);
        upld_Gst_layout = findViewById(R.id.upld_Gst_layout);


        adhaar_upld_sucss = (ImageView) findViewById(R.id.adhaar_upld_sucss);
        upld_mrgn_succ = (ImageView) findViewById(R.id.upld_mrgn_succ);
        income_success_img =
                (ImageView) findViewById(R.id.income_success_img);
        land_upld_sccs = (ImageView) findViewById(R.id.land_upld_sccs);
        upload_bank_succs =
                (ImageView) findViewById(R.id.upload_bank_succs);
        upld_application_succss =
                (ImageView) findViewById(R.id.upld_application_succss);


        application_copy_layout = findViewById(R.id.upload_applctn_copy);
        margin_copy_layout = findViewById(R.id.upload_margin_layout);
        income_proof_layout = findViewById(R.id.upload_income_layout);
        arrow = findViewById(R.id.arrow);
        dropdown_App_photo = findViewById(R.id.dropdown_App_photo);
        dropdown_margin_photo = findViewById(R.id.dropdown_margin_photo);
        dropdown_income_photo = findViewById(R.id.dropdown_income_photo);
        dropdown_bank_photo = findViewById(R.id.dropdown_bank_photo);
        dropdown_lanf_photo = findViewById(R.id.dropdown_lanf_photo);
        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        application_copy_layout = findViewById(R.id.upload_applctn_copy);
        margin_copy_layout = findViewById(R.id.upload_margin_layout);
        upld_img_hint = (TextView) findViewById(R.id.upld_img_hint);
        upld_appltn_hint = (TextView) findViewById(R.id.upld_appltn_hint);
        upld_mrgn_hint = (TextView) findViewById(R.id.upld_mrgn_hint);
        upld_income_proof = (TextView) findViewById(R.id.upld_income_proof);
        upld_bank_stmnt_hint = (TextView) findViewById(R.id.upld_bank_stmnt_hint);
        error1 = (TextView) findViewById(R.id.error1);
        unsaved_img_error_massage = (TextView) findViewById(R.id.unsaved_img_error_massage);
        unsaved_img_error_massage2 = (TextView) findViewById(R.id.unsaved_img_error_massage2);
        unsaved_img_error_massage3= (TextView)findViewById(R.id.unsaved_img_error_massage3);
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Image_Upload_SupplyChain.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });
        //upload Selfie
        upload_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showonlyCamera();
                y = 4;
            }
        });
        //Upload Adhaar front
        upload_adhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_selfie_status==1)){
                    adhar_front_err_msg.setVisibility(View.VISIBLE);
                }else{
                    SelectPictureDailog();
                    x = 1;
                    y = 1;
                }

            }
        });
        //Upload Adhaar back
        upload_adhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_adhar_front_status==1)){
                    adhar_back_err_msg.setVisibility(View.VISIBLE);
                }else{
                    SelectPictureDailog();
                    x = 2;
                    y = 2;
                }

            }
        });
        //Upload Pan Card
        upload_pancard_front_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_adhar_back_status==1)){
                    pan_front_err_msg.setVisibility(View.VISIBLE);
                }else{
                    SelectPictureDailog();
                    x = 3;
                    y = 3;
                }

            }
        });

        //Upload BusinessProof
        upld_business_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_pan_status==1)){
                    business_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 5;
                    y = 5;
                    PDF_VALID = 1;
                    mSelectedBussinessProofStatus = 1;
                }

            }
        });
        upld_udc_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_business_status==1)){
                    udc_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 6;
                    y = 6;
                    PDF_VALID = 2;
                    mSelectedUDCStatus = 1;
                }

            }
        });

        upld_agreement_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_udc_status==1)){
                    agreement_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 7;
                    y = 7;
                    PDF_VALID = 3;
                    mSelectedAgreementStatus = 1;
                }

            }
        });

        upld_escrow_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_agreemnt_status==1)){
                    escrow_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 8;
                    y = 8;
                    PDF_VALID = 4;
                    mSelectedEscrowStatus = 1;
                }

            }
        });
        upld_disburse_bank_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_escrow_status==1)){
                    disb_bank_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 9;
                    y = 9;
                    PDF_VALID = 5;
                    mSelectedDisburseBankStatus = 1;
                }

            }
        });
        upld_ledger_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_disb_bank_status==1)){
                    ledger_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 10;
                    y = 10;
                    PDF_VALID = 6;
                    mSelectedLedgerStatus =1;
                }

            }
        });
        upld_invoice_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_ledger_status==1)){
                    invoice_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 11;
                    y = 11;
                    PDF_VALID = 7;
                    mSelectedLedgerStatus = 1;
                }

            }
        });
        upld_itr_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_invoice_status==1)){
                    itr_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 12;
                    y = 12;
                    PDF_VALID = 8;
                    mSelectedITRStatus = 1;
                }

            }
        });
        upld_tds_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_itr_status==1)){
                    tds_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 13;
                    y = 13;
                    PDF_VALID = 9;
                    mSelectedTDSStatus = 1;
                }

            }
        });

        upld_Gst_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_tds_status==1)){
                    Gst_err_msg.setVisibility(View.VISIBLE);
                }else{
                    showPictureDialog();
                    x = 14;
                    y = 14;
                    PDF_VALID = 10;
                    mSelectedGSTStatus = 1;
                }

            }
        });
        ///Selfie Dropdown/DropUp
        dropdown1_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup1_selfie.setVisibility(View.VISIBLE);
                dropdown1_selfie.setVisibility(View.GONE);
                upload_selfie_layout.setVisibility(View.VISIBLE);

            }
        });

        dropup1_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown1_selfie.setVisibility(View.VISIBLE);
                dropup1_selfie.setVisibility(View.GONE);
                upload_selfie_layout.setVisibility(View.GONE);

            }
        });

        ////Adhaar Front Dropdown/Dropup
        dropdown1_adhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown1_adhar_front.setVisibility(View.GONE);
                dropup1_adhar_front.setVisibility(View.VISIBLE);
                upload_adhaar_front_layout.setVisibility(View.VISIBLE);

            }
        });
        dropup1_adhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup1_adhar_front.setVisibility(View.GONE);
                dropdown1_adhar_front.setVisibility(View.VISIBLE);
                upload_adhaar_front_layout.setVisibility(View.GONE);

            }
        });

        ////Adhaar Back Dropdown/Dropup
        dropdown1_adhar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown1_adhar_back.setVisibility(View.GONE);
                dropup1_adhar_back.setVisibility(View.VISIBLE);
                adhaar_back_layout.setVisibility(View.VISIBLE);

            }
        });
        dropup1_adhar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup1_adhar_back.setVisibility(View.GONE);
                dropdown1_adhar_back.setVisibility(View.VISIBLE);
                adhaar_back_layout.setVisibility(View.GONE);

            }
        });

        ////PAN Dropdown/Dropup
        dropdown_pan_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown_pan_front.setVisibility(View.GONE);
                dropup2_pan_front.setVisibility(View.VISIBLE);
                pan_front_layout.setVisibility(View.VISIBLE);

            }
        });
        dropup2_pan_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropup2_pan_front.setVisibility(View.GONE);
                dropdown_pan_front.setVisibility(View.VISIBLE);
                pan_front_layout.setVisibility(View.GONE);

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

        ////Ledger Dropdown/Dropup
        ledger_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ledger_dropdown.setVisibility(View.GONE);
                ledger_dropup2.setVisibility(View.VISIBLE);
                upld_ledger_layout.setVisibility(View.VISIBLE);

            }
        });
        ledger_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ledger_dropup2.setVisibility(View.GONE);
                ledger_dropdown.setVisibility(View.VISIBLE);
                upld_ledger_layout.setVisibility(View.GONE);

            }
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
        invoice_dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoice_dropup2.setVisibility(View.GONE);
                invoice_dropdown.setVisibility(View.VISIBLE);
                upld_Invoice_layout.setVisibility(View.GONE);

            }
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


        upload_bank_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upld_mrgn_hint.setVisibility(View.GONE);
                try {
                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("application/pdf");
                    intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
                } catch (Exception exe) {
                    exe.printStackTrace();
                }
               /* if (ApplicationImage_saved_status==1){
                    //showonlyCamera();



                    y = 3;
                    Log.e("TAG", "sai5");
                }
                else {
                    unsaved_img_error_massage2.setVisibility(View.VISIBLE);
                }*/
            }
        });
        upload_salaryslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               upld_income_proof.setVisibility(View.GONE);
                try {
                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("application/pdf");
                    intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
                } catch (Exception exe) {
                    exe.printStackTrace();
                }

               /* if (MarginPhoto_saved_status==1){
                   // showonlyGallery();
                    x = 4;
                    Log.e("TAG", "sai4");
                }else{
                    unsaved_img_error_massage3.setVisibility(View.VISIBLE);
                }*/
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Image_Upload_SupplyChain.this, MainActivity.class);

                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(intent);
            }
        });
//api call
        save_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(upld_selfie==1)){
                    upld_selfie_hint.setVisibility(View.VISIBLE);
                }else {
                    DealerSelfieDoc();

                }

            }
        });
        save_adhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(adhar_front_valid1== 1||adhar_front_valid2==1)){
                   upld_img_hint.setVisibility(View.VISIBLE);

                }else{
                    DealerAadharFrontDoc();
                }

            }
        });
        save1_adhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(adhar_back_valid1==2||adhar_back_valid2==1)){
                    adhaar_back_hint.setVisibility(View.VISIBLE);
                }else{
                    DealerAdharBackDoc();
                }

            }
        });

        save_pan_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Pan_valid==1)){
                    upld_pan_front_hint.setVisibility(View.VISIBLE);
                }else {
                    DealerPanDoc();
                }

            }
        });
        save_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Business_valid==1)){
                    upld_business_hint.setVisibility(View.VISIBLE);
                }else {

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
                    escrow_dropup2.setVisibility(View.GONE);
                    escrow_dropdown.setVisibility(View.VISIBLE);
                    upld_escrow_layout.setVisibility(View.GONE);
                    upld_ecsrow_succss.setVisibility(View.VISIBLE);
                    disb_bank_err_msg.setVisibility(View.GONE);
                    save_escrow_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, "Image Upload Sucsessfully!", Toast.LENGTH_SHORT).show();
                   // DealerESCROWDoc();
                }

            }
        });
        disburse_bank_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Disb_Bank_Valid == 1)){
                   upld_disb_bank_hint.setVisibility(View.VISIBLE);
                }else{

                    disb_bank_dropup2.setVisibility(View.GONE);
                    disb_bank_dropdown.setVisibility(View.VISIBLE);
                    upld_disburse_layout.setVisibility(View.GONE);
                    upld_disb_bank_succss.setVisibility(View.VISIBLE);
                    save_disb_bank_status = 1;
                    ledger_err_msg.setVisibility(View.GONE);
                    Toast.makeText(Image_Upload_SupplyChain.this, "Image Upload Sucsessfully!", Toast.LENGTH_SHORT).show();
                  //  DealerDISB_BANKDoc();
                }

            }
        });
        ledger_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Ledger_Valid==1)){
                    upld_ledger_hint.setVisibility(View.VISIBLE);
                }else{
                    ledger_dropup2.setVisibility(View.GONE);
                    ledger_dropdown.setVisibility(View.VISIBLE);
                    upld_ledger_layout.setVisibility(View.GONE);
                    upld_ledger_succss.setVisibility(View.VISIBLE);
                    invoice_err_msg.setVisibility(View.GONE);
                    save_ledger_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, "Image Upload Successfully!", Toast.LENGTH_SHORT).show();
                   // DealerLEDGERDoc();
                }

            }
        });
        invoice_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Invoice_Valid==1)){
                    upld_invoice_hint.setVisibility(View.VISIBLE);
                }else{
                    DealerINVOICEDoc();
                }

            }
        });
        itr_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ITR_Valid==1)){
                    upld_ITR_hint.setVisibility(View.VISIBLE);
                }else{
                    DealerITRDoc();
                }

            }
        });

        TDS_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TDS_Valid==1)){
                    upld_TDS_hint.setVisibility(View.VISIBLE);
                }else{
                    DealerTDSDoc();
                }

            }
        });
        Gst_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(GST_Valid==1)){
                    upld_Gst_hint.setVisibility(View.VISIBLE);
                }else{
                    DealerGSTDoc();
                }

            }
        });


        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(save_gst_status==1)){
                    Toast.makeText(Image_Upload_SupplyChain.this, "Please save GST Image !", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Image_Upload_SupplyChain.this, "All Document Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SelectPictureDailog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }
    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);
    }


    private void showonlyCamera() {

        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                takePhotoFromCamera();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera","Select a PDF File"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                ChoosePdfDocument();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }

    private void ChoosePdfDocument() {
        openPDFSelector();

       /* try {
            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
        } catch (Exception exe) {
            exe.printStackTrace();
        }*/
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
                    if (x == 1) {
                        upload_adhaar.setImageBitmap(bitmap);
                        adhar_front_valid1 = 1;
                        upld_img_hint.setVisibility(View.GONE);
                    } else if (x == 2) {
                        upload_adhaar_back.setImageBitmap(bitmap);
                        adhaar_back_hint.setVisibility(View.GONE);
                        adhar_back_valid1 = 2;

                    } else if (x == 3) {
                        upload_pancard_front_button.setImageBitmap(bitmap);
                        upld_pan_front_hint.setVisibility(View.GONE);
                        Pan_valid = 1;
                    } else if (x == 5) {
                        upld_business_buton.setImageBitmap(bitmap);
                        upld_business_hint.setVisibility(View.GONE);
                        Business_valid = 1;
                        mSelectedBussinessProofStatus = 0;

                    }else if (x == 6) {
                        upld_udc_buton.setImageBitmap(bitmap);
                        upld_udc_hint.setVisibility(View.GONE);
                        UDC_Valid = 1;
                        mSelectedUDCStatus = 0;

                    }else if (x == 7) {
                        upld_agreement_buton.setImageBitmap(bitmap);
                        upld_agreement_hint.setVisibility(View.GONE);
                        Agreement_Valid = 1;
                        mSelectedAgreementStatus = 0;

                    }else if (x == 8) {
                        upld_escrow_buton.setImageBitmap(bitmap);
                        upld_escrow_hint.setVisibility(View.GONE);
                        Escrow_Valid = 1;
                        mSelectedEscrowStatus = 0;

                    }else if (x == 9) {
                        upld_disburse_bank_buton.setImageBitmap(bitmap);
                        upld_disb_bank_hint.setVisibility(View.GONE);
                        Disb_Bank_Valid = 1;
                        mSelectedDisburseBankStatus = 0;

                    }else if (x == 10) {
                        upld_ledger_buton.setImageBitmap(bitmap);
                        upld_ledger_hint.setVisibility(View.GONE);
                        Ledger_Valid = 1;
                        mSelectedLedgerStatus = 0;
                    }else if (x == 11) {
                        upld_invoice_buton.setImageBitmap(bitmap);
                        upld_invoice_hint.setVisibility(View.GONE);
                        Invoice_Valid = 1;
                        mSelectedInvoiceStatus = 0;
                    }else if (x == 12) {
                        upld_itr_buton.setImageBitmap(bitmap);
                        upld_ITR_hint.setVisibility(View.GONE);
                        ITR_Valid = 1;
                        mSelectedITRStatus = 0;
                    }
                    else if (x == 13) {
                        upld_tds_buton.setImageBitmap(bitmap);
                        upld_TDS_hint.setVisibility(View.GONE);
                        TDS_Valid = 1;
                        mSelectedTDSStatus = 0;

                    }
                    else if (x == 14) {
                        upld_Gst_buton.setImageBitmap(bitmap);
                        upld_Gst_hint.setVisibility(View.GONE);
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


            if (y == 1) {
                // upload_uploadelectricitybillimg_valid = 1;
                upload_adhaar.setImageBitmap(thumbnail);
                adhar_front_valid2 = 1;
                upld_img_hint.setVisibility(View.GONE);
            } else if (y == 2) {
                upload_adhaar_back.setImageBitmap(thumbnail);
                adhaar_back_hint.setVisibility(View.GONE);
                adhar_back_valid2 = 1;

            } else if (y == 3) {
                upload_pancard_front_button.setImageBitmap(thumbnail);
                upld_pan_front_hint.setVisibility(View.GONE);
                Pan_valid = 1;

            }else if (y==4){
                upload_selfie.setImageBitmap(thumbnail);
                upld_selfie_hint.setVisibility(View.GONE);
                upld_selfie = 1;
            }else if (y==5){
                upld_business_buton.setImageBitmap(thumbnail);
                upld_business_hint.setVisibility(View.GONE);
                Business_valid = 1;
                mSelectedBussinessProofStatus = 0;
            }else if (y==6){
                upld_udc_buton.setImageBitmap(thumbnail);
                upld_udc_hint.setVisibility(View.GONE);
                UDC_Valid = 1;
                mSelectedUDCStatus = 0;
            }else if (y==7){
                upld_agreement_buton.setImageBitmap(thumbnail);
                upld_agreement_hint.setVisibility(View.GONE);
                Agreement_Valid = 1;
                mSelectedAgreementStatus = 0;
            }else if (y==8){
                upld_escrow_buton.setImageBitmap(thumbnail);
                upld_escrow_hint.setVisibility(View.GONE);
                Escrow_Valid = 1;
                mSelectedEscrowStatus = 0;
            }else if (y==9){
                upld_disburse_bank_buton.setImageBitmap(thumbnail);
                upld_disb_bank_hint.setVisibility(View.GONE);
                Disb_Bank_Valid = 1;
                mSelectedDisburseBankStatus = 0;
            }else if (y==10){
                upld_ledger_buton.setImageBitmap(thumbnail);
                upld_ledger_hint.setVisibility(View.GONE);
                Ledger_Valid = 1;
                mSelectedLedgerStatus = 0;
            }
            else if (y==11){
                upld_invoice_buton.setImageBitmap(thumbnail);
                upld_invoice_hint.setVisibility(View.GONE);
                Invoice_Valid = 1;
                mSelectedInvoiceStatus = 0;
            }
            else if (y==12){
                upld_itr_buton.setImageBitmap(thumbnail);
                upld_ITR_hint.setVisibility(View.GONE);
                ITR_Valid = 1;
                mSelectedITRStatus = 0;
            }else if (y==13){
                upld_tds_buton.setImageBitmap(thumbnail);
                upld_TDS_hint.setVisibility(View.GONE);
                TDS_Valid = 1;
                mSelectedTDSStatus = 0;
            }else if (y==14){
                upld_Gst_buton.setImageBitmap(thumbnail);
                upld_Gst_hint.setVisibility(View.GONE);
                GST_Valid = 1;
                mSelectedGSTStatus = 0;
            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
          //  Toast.makeText(this, "Image Saved!",Toast.LENGTH_SHORT).show();
            if (mSelectedBussinessProofStatus == 1) {
                Business_valid = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
                upld_business_hint.setVisibility(View.GONE);
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
            }else if (mSelectedLedgerStatus == 1) {
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
    private void DealerGSTDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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

                    Log.e("testingggg", "success_adhar_back");
                    Gst_dropup2.setVisibility(View.GONE);
                    GST_DROPDOWN.setVisibility(View.VISIBLE);
                    upld_Gst_layout.setVisibility(View.GONE);
                    upld_Gst_succss.setVisibility(View.VISIBLE);
                    Gst_err_msg.setVisibility(View.GONE);
                    save_gst_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerGSTDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerTDSDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
 //       Log.e("UDC FileName ", mTDSFile.toString());

        if (mSelectedTDSStatus == 1) {
            idFile = mTDSFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("TDS", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerTDSDoc_MODEL> call = restApis.DealerTDSDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerTDSDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerTDSDoc_MODEL> call, Response<DealerTDSDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    TdS_dropup2.setVisibility(View.GONE);
                    Dropdown_TDS.setVisibility(View.VISIBLE);
                    upld_TDS_layout.setVisibility(View.GONE);
                    upld_tds_succss.setVisibility(View.VISIBLE);
                    Gst_err_msg.setVisibility(View.GONE);
                    save_tds_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerTDSDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerITRDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
 //       Log.e("UDC FileName ", mITRFile.toString());

        if (mSelectedITRStatus == 1) {
            idFile = mITRFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("ITR", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerITRDoc_MODEL> call = restApis.DealerITRDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerITRDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerITRDoc_MODEL> call, Response<DealerITRDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    itr_dropup2.setVisibility(View.GONE);
                    dropdown_itr.setVisibility(View.VISIBLE);
                    upld_itr_layout.setVisibility(View.GONE);
                    upld_itr_succss.setVisibility(View.VISIBLE);
                    itr_err_msg.setVisibility(View.GONE);
                    save_itr_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerITRDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });


    }

    private void DealerINVOICEDoc() {


        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
 //       Log.e("UDC FileName ", mInvoiceFile.toString());

        if (mSelectedInvoiceStatus == 1) {
            idFile = mInvoiceFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("INVOICE", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerINVOICEDoc_MODEL> call = restApis.DealerINVOICEDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerINVOICEDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerINVOICEDoc_MODEL> call, Response<DealerINVOICEDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    invoice_dropup2.setVisibility(View.GONE);
                    invoice_dropdown.setVisibility(View.VISIBLE);
                    upld_Invoice_layout.setVisibility(View.GONE);
                    upld_invoice_succss.setVisibility(View.VISIBLE);
                    itr_err_msg.setVisibility(View.GONE);
                    save_invoice_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerINVOICEDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerLEDGERDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
//        Log.e("UDC FileName ", mUDCFile.toString());

        if (mSelectedLedgerStatus == 1) {
            idFile = mLedgerFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        }
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("DealerLEDGERDoc", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerLEDGERDoc_MODEL> call = restApis.DealerLEDGERDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerLEDGERDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerLEDGERDoc_MODEL> call, Response<DealerLEDGERDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    ledger_dropup2.setVisibility(View.GONE);
                    ledger_dropdown.setVisibility(View.VISIBLE);
                    upld_ledger_layout.setVisibility(View.GONE);
                    upld_ledger_succss.setVisibility(View.VISIBLE);
                    save_ledger_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerLEDGERDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerDISB_BANKDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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

                    Log.e("testingggg", "success_adhar_back");
                    disb_bank_dropup2.setVisibility(View.GONE);
                    disb_bank_dropdown.setVisibility(View.VISIBLE);
                    upld_disburse_layout.setVisibility(View.GONE);
                    upld_disb_bank_succss.setVisibility(View.VISIBLE);
                    save_disb_bank_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerDISB_BANKDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerESCROWDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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
        vechileDocUpload2 = MultipartBody.Part.createFormData("DealerESCROWDoc", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerESCROWDoc_MODEL> call = restApis.DealerESCROWDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerESCROWDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerESCROWDoc_MODEL> call, Response<DealerESCROWDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    escrow_dropup2.setVisibility(View.GONE);
                    escrow_dropdown.setVisibility(View.VISIBLE);
                    upld_escrow_layout.setVisibility(View.GONE);
                    upld_ecsrow_succss.setVisibility(View.VISIBLE);
                    save_escrow_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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

                    agreement_dropup2.setVisibility(View.GONE);
                    agreement_dropdown.setVisibility(View.VISIBLE);
                    upld_agreement_layout.setVisibility(View.GONE);
                    upld_agreement_succss.setVisibility(View.VISIBLE);
                    escrow_err_msg.setVisibility(View.GONE);
                    save_agreemnt_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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

                    Log.e("testingggg", "success_adhar_back");
                    dropup2_udc.setVisibility(View.GONE);
                    dropdown_udc.setVisibility(View.VISIBLE);
                    upld_udc_layout.setVisibility(View.GONE);
                    upld_udc_succss.setVisibility(View.VISIBLE);
                    agreement_err_msg.setVisibility(View.GONE);
                    save_udc_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

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

                    Log.e("testingggg", "success_adhar_back");
                    dropup2_business_proof.setVisibility(View.GONE);
                    dropdown_business_proof.setVisibility(View.VISIBLE);
                    upld_business_layout.setVisibility(View.GONE);
                    upld_busines_succss.setVisibility(View.VISIBLE);
                    udc_err_msg.setVisibility(View.GONE);
                    save_business_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerBUSINESSPROOFDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void DealerPanDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("panimage", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerPanDoc_MODEL> call = restApis.DealerPanDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerPanDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerPanDoc_MODEL> call, Response<DealerPanDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");
                    dropup2_pan_front.setVisibility(View.GONE);
                    dropdown_pan_front.setVisibility(View.VISIBLE);
                    pan_front_layout.setVisibility(View.GONE);
                    upld_pan_front_success.setVisibility(View.VISIBLE);
                    business_err_msg.setVisibility(View.GONE);
                    save_pan_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerPanDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void DealerAdharBackDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerAdharBackDoc_MODEL> call = restApis.DealerAdharBackDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerAdharBackDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerAdharBackDoc_MODEL> call, Response<DealerAdharBackDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_adhar_back");

                    dropup1_adhar_back.setVisibility(View.GONE);
                    dropdown1_adhar_back.setVisibility(View.VISIBLE);
                    adhaar_back_layout.setVisibility(View.GONE);
                    adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                    pan_front_err_msg.setVisibility(View.GONE);
                    save_adhar_back_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }



            }

            @Override
            public void onFailure(Call<DealerAdharBackDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });


    }

    private void DealerAadharFrontDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerAadharFrontDoc_MODEL> call = restApis.DealerAadharFrontDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerAadharFrontDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerAadharFrontDoc_MODEL> call, Response<DealerAadharFrontDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_pan");
                    upload_adhaar_front_layout.setVisibility(View.GONE);
                    adhaar_upld_sucss.setVisibility(View.VISIBLE);
                    dropdown1_adhar_front.setVisibility(View.VISIBLE);
                    dropup1_adhar_front.setVisibility(View.GONE);
                    adhar_back_err_msg.setVisibility(View.GONE);
                    save_adhar_front_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<DealerAadharFrontDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void DealerSelfieDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("selfie", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerSelfieDoc_MODEL> call = restApis.DealerSelfieDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerSelfieDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerSelfieDoc_MODEL> call, Response<DealerSelfieDoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_pan");

                    selfie_upld_sucss.setVisibility(View.VISIBLE);
                    upload_selfie_layout.setVisibility(View.GONE);
                    dropdown1_selfie.setVisibility(View.VISIBLE);
                    dropup1_selfie.setVisibility(View.GONE);
                    adhar_front_err_msg.setVisibility(View.GONE);
                    save_selfie_status = 1;
                    Toast.makeText(Image_Upload_SupplyChain.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }



            }

            @Override
            public void onFailure(Call<DealerSelfieDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Dealer_pan_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("panimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_pan_molldoc_MODEL> call = restApis.Dealer_pan_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_pan_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_pan_molldoc_MODEL> call, Response<Dealer_pan_molldoc_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "success_pan");

                }



            }

            @Override
            public void onFailure(Call<Dealer_pan_molldoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }




    private void Dealer_adhar_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_adhar_molldoc_MODEL> call = restApis.Dealer_adhar_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_adhar_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_adhar_molldoc_MODEL> call, Response<Dealer_adhar_molldoc_MODEL> response) {
                if (response.body() != null) {
                 upload_adhaar.setImageResource(response.body().getPayload().getAdhar_img());
                    Log.e("testingggg", "success");

                }



            }

            @Override
            public void onFailure(Call<Dealer_adhar_molldoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }


    private void Dealer_bank_molldoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.SUPPLY_CHAIN_PDF_FILE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("bankimage", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_bank_molldoc_MODEL> call = restApis.Dealer_bank_molldoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<Dealer_bank_molldoc_MODEL>() {
            @Override
            public void onResponse(Call<Dealer_bank_molldoc_MODEL> call, Response<Dealer_bank_molldoc_MODEL> response) {
                if (response.body() != null) {


                    Log.e("testingggg", "success");

                }



            }

            @Override
            public void onFailure(Call<Dealer_bank_molldoc_MODEL> call, Throwable t) {
                Log.e("testingggg", "success"+t);

                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
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

        if (mSelectedBussinessProofStatus == 1) {
            Log.e("BussinessProofStatus ", mFile.toString());
            mBussinessProofFile = mFile;
//            mSelectedBussinessProofStatus = 0;
        } else if (mSelectedUDCStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mUDCFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedAgreementStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mAgreementFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedEscrowStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mEscrowFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedDisburseBankStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mDisburseBankFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedLedgerStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mLedgerFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedInvoiceStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mInvoiceFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedITRStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mITRFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedTDSStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mTDSFile = mFile;
//            mSelectedUDCStatus = 0;
        }else if (mSelectedGSTStatus == 1) {
            Log.e("UDCStatus ", mFile.toString());
            mGSTFile = mFile;
//            mSelectedUDCStatus = 0;
        }
      /*  if (PDF_VALID==1){

            dropup2_business_proof.setVisibility(View.GONE);
            dropdown_business_proof.setVisibility(View.VISIBLE);
            upld_business_layout.setVisibility(View.GONE);
            upld_busines_succss.setVisibility(View.VISIBLE);

        }else if(PDF_VALID==2){

            dropup2_udc.setVisibility(View.GONE);
            dropdown_udc.setVisibility(View.VISIBLE);
            upld_udc_layout.setVisibility(View.GONE);
            upld_udc_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==3){

            agreement_dropup2.setVisibility(View.GONE);
            agreement_dropdown.setVisibility(View.VISIBLE);
            upld_agreement_layout.setVisibility(View.GONE);
            upld_agreement_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==4){

            escrow_dropup2.setVisibility(View.GONE);
            escrow_dropdown.setVisibility(View.VISIBLE);
            upld_escrow_layout.setVisibility(View.GONE);
            upld_ecsrow_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==5){

            disb_bank_dropup2.setVisibility(View.GONE);
            disb_bank_dropdown.setVisibility(View.VISIBLE);
            upld_disburse_layout.setVisibility(View.GONE);
            upld_disb_bank_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==6){

            ledger_dropup2.setVisibility(View.GONE);
            ledger_dropdown.setVisibility(View.VISIBLE);
            upld_ledger_layout.setVisibility(View.GONE);
            upld_ledger_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==7){

            invoice_dropup2.setVisibility(View.GONE);
            invoice_dropdown.setVisibility(View.VISIBLE);
            upld_Invoice_layout.setVisibility(View.GONE);
            upld_invoice_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==8){

            itr_dropup2.setVisibility(View.GONE);
            dropdown_itr.setVisibility(View.VISIBLE);
            upld_itr_layout.setVisibility(View.GONE);
            upld_itr_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==9){

            TdS_dropup2.setVisibility(View.GONE);
            Dropdown_TDS.setVisibility(View.VISIBLE);
            upld_TDS_layout.setVisibility(View.GONE);
            upld_tds_succss.setVisibility(View.VISIBLE);

        }else if (PDF_VALID==10){

            Gst_dropup2.setVisibility(View.GONE);
            GST_DROPDOWN.setVisibility(View.VISIBLE);
            upld_Gst_layout.setVisibility(View.GONE);
            upld_Gst_succss.setVisibility(View.VISIBLE);

        }

*/

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }
}


/*    public String getFilePathFromURI(Context context, Uri contentUri, TextView pdf_name) {
//copy file and send new file path
        String fileName = getFileName(Image_Upload_SupplyChain.this, contentUri);
        Log.e("filee","files"+fileName);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(context.getExternalFilesDir("") + File.separator + fileName);
            Log.e("filee1x","files1xz"+copyFile);
            copy(context, contentUri, copyFile);
            Log.e("filee1xv","filesss"+fileName);
            return copyFile.getAbsolutePath();

        }
        return null;
    }
    public static String getFileName(Image_Upload_SupplyChain  image_upload_supplyChain, Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        Log.e("logggFile","path..."+path);

        int cut = path.lastIndexOf('/');
        Log.e("filenameXYZPath","XYZP"+cut);
        if (cut != -1) {
            fileName = path.substring(cut + 1);

            Log.e("filenameXYZ","XYZ"+fileName);

        }
        return fileName;

    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

/*
    public String getStringPdf(Uri filepath) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {
            inputStream = getContentResolver().openInputStream(filepath);

            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
    }
*/
