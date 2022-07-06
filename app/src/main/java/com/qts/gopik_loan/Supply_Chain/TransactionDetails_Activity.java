package com.qts.gopik_loan.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.qts.gopik_loan.Model.DealerINVOICEDoc_MODEL;
import com.qts.gopik_loan.Model.DealerITRDoc_MODEL;
import com.qts.gopik_loan.Model.DealerLEDGERDoc_MODEL;
import com.qts.gopik_loan.Model.DealerTDSDoc_MODEL;
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

public class TransactionDetails_Activity extends AppCompatActivity implements PickiTCallbacks {
    public int x = 0;
    public int y = 0;
    public int PDF_VALID = 0;
    public int Selfie = 0;
    String filePath = "";
    private final int PERMISSION_REQUEST_CODE = 3;
    PickiT pickiT;
    private int GALLERY = 1, CAMERA = 2;
    ImageView CameraButton,GalleryButton,PdfButton;

    Integer Ledger_Valid = 0;
    Integer Invoice_Valid = 0;
    Integer ITR_Valid = 0;
    Integer TDS_Valid = 0;
    Integer GST_Valid = 0;
    private Dialog dialogCondition;
    TextView btsend, save1, save2, save3, save4;

    ImageView arrow,hometoolbar;

    String Pdf_Name;

    private static final int FILE_CHOOSER=123;

    TextView ledger_save_button,invoice_save_button,itr_save_button,TDS_save_button;

    ImageView ledger_dropdown,ledger_dropup2;

    LinearLayout upld_ledger_layout,upld_Invoice_layout,upld_itr_layout,upld_TDS_layout;
    ImageView invoice_dropdown,invoice_dropup2,dropdown_itr,itr_dropup2,Dropdown_TDS,TdS_dropup2,GST_DROPDOWN,Gst_dropup2;

    TextView upld_ledger_hint,upld_ITR_hint,upld_invoice_hint,upld_TDS_hint;
    ImageView upld_ledger_succss,upld_invoice_succss,upld_itr_succss,upld_tds_succss;

    ImageView upld_ledger_buton,upld_invoice_buton,upld_itr_buton,upld_tds_buton;

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

    CustPrograssbar custPrograssbar;

    TextView ledger_name,tds_name,itr_name,invoice_name;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);


        //Pdf name
        ledger_name = findViewById(R.id.ledger_name);
        tds_name = findViewById(R.id.tds_name);
        itr_name = findViewById(R.id.itr_name);
        invoice_name = findViewById(R.id.invoice_name);
        btsend = findViewById(R.id.btsend);


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

                    x = 11;
                    y = 11;
                    PDF_VALID = 7;
                    mSelectedInvoiceStatus = 1;
                    SelectImageDailog();

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
                     DealerLEDGERDoc();
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
                if (!(ITR_Valid==1)){
                    upld_ITR_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
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
                    custPrograssbar.prograssCreate(TransactionDetails_Activity.this);
                    DealerTDSDoc();
                }

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

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransactionDetails_Activity.this, "All Document Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TransactionDetails_Activity.this, MainActivity.class);
                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(intent);
            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(TransactionDetails_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);
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
                         upld_ledger_buton.getLayoutParams().height = 300;
                         upld_ledger_buton.getLayoutParams().width = 300;
                         upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Ledger_Valid = 1;
                        mSelectedLedgerStatus = 0;
                    }else if (x == 11) {
                        upld_invoice_buton.setImageBitmap(bitmap);
                        upld_invoice_hint.setVisibility(View.GONE);
                         upld_invoice_buton.getLayoutParams().height = 300;
                         upld_invoice_buton.getLayoutParams().width = 300;
                         upld_invoice_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        Invoice_Valid = 1;
                        mSelectedInvoiceStatus = 0;
                    }else if (x == 12) {
                        upld_itr_buton.setImageBitmap(bitmap);
                        upld_ITR_hint.setVisibility(View.GONE);
                         upld_itr_buton.getLayoutParams().height = 300;
                         upld_itr_buton.getLayoutParams().width = 300;
                         upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                        ITR_Valid = 1;
                        mSelectedITRStatus = 0;
                    }
                    else if (x == 13) {
                        upld_tds_buton.setImageBitmap(bitmap);
                        upld_TDS_hint.setVisibility(View.GONE);
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
                upld_ledger_buton.getLayoutParams().height = 300;
                upld_ledger_buton.getLayoutParams().width = 300;
                upld_ledger_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Ledger_Valid = 1;
                mSelectedLedgerStatus = 0;
            }
            else if (y==11){
                upld_invoice_buton.setImageBitmap(thumbnail);
                upld_invoice_hint.setVisibility(View.GONE);
                upld_invoice_buton.getLayoutParams().height = 300;
                upld_invoice_buton.getLayoutParams().width = 300;
                upld_invoice_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                Invoice_Valid = 1;
                mSelectedInvoiceStatus = 0;
            }
            else if (y==12){
                upld_itr_buton.setImageBitmap(thumbnail);
                upld_ITR_hint.setVisibility(View.GONE);
                upld_itr_buton.getLayoutParams().height = 300;
                upld_itr_buton.getLayoutParams().width = 300;
                upld_itr_buton.setScaleType(ImageView.ScaleType.FIT_XY);
                ITR_Valid = 1;
                mSelectedITRStatus = 0;
            }else if (y==13){
                upld_tds_buton.setImageBitmap(thumbnail);
                upld_TDS_hint.setVisibility(View.GONE);
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
        Log.e("FileName----->>>>>>>>",Pdf_Name);
        if (mSelectedLedgerStatus == 1) {
            Log.e("LedgerStatus---->>", mFile.toString());
            mLedgerFile = mFile;
            ledger_name.setText(Pdf_Name);
            ledger_name.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedInvoiceStatus == 1) {
            Log.e("Invoice---->>> ", mFile.toString());
            mInvoiceFile = mFile;
            invoice_name.setText(Pdf_Name);
            invoice_name.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedITRStatus == 1) {
            Log.e("ITR--->>> ", mFile.toString());
            mITRFile = mFile;
            itr_name.setText(Pdf_Name);
            itr_name.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        }else if (mSelectedTDSStatus == 1) {
            Log.e("TDS---->>>", mFile.toString());
            mTDSFile = mFile;
            tds_name.setText(Pdf_Name);
            tds_name.setVisibility(View.VISIBLE);
//            mSelectedUDCStatus = 0;
        }


    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

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
        vechileDocUpload2 = MultipartBody.Part.createFormData("LEDGER", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerLEDGERDoc_MODEL> call = restApis.DealerLEDGERDoc(user_code, vechileDocUpload2);
        call.enqueue(new Callback<DealerLEDGERDoc_MODEL>() {
            @Override
            public void onResponse(Call<DealerLEDGERDoc_MODEL> call, Response<DealerLEDGERDoc_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    ledger_dropup2.setVisibility(View.GONE);
                    ledger_dropdown.setVisibility(View.VISIBLE);
                    upld_ledger_layout.setVisibility(View.GONE);
                    upld_ledger_succss.setVisibility(View.VISIBLE);
                    ledger_save_button.setText("Update");
                    mSelectedLedgerStatus = 0;
                    Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerLEDGERDoc_MODEL> call, Throwable t) {


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
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    TdS_dropup2.setVisibility(View.GONE);
                    Dropdown_TDS.setVisibility(View.VISIBLE);
                    upld_TDS_layout.setVisibility(View.GONE);
                    upld_tds_succss.setVisibility(View.VISIBLE);
                    TDS_save_button.setText("Update");
                    mSelectedTDSStatus = 0;
                    Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerTDSDoc_MODEL> call, Throwable t) {


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
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    invoice_dropup2.setVisibility(View.GONE);
                    invoice_dropdown.setVisibility(View.VISIBLE);
                    upld_Invoice_layout.setVisibility(View.GONE);
                    upld_invoice_succss.setVisibility(View.VISIBLE);
                    invoice_save_button.setText("Update");
                    mSelectedInvoiceStatus = 0;
                    Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerINVOICEDoc_MODEL> call, Throwable t) {


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

                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    itr_dropup2.setVisibility(View.GONE);
                    dropdown_itr.setVisibility(View.VISIBLE);
                    upld_itr_layout.setVisibility(View.GONE);
                    upld_itr_succss.setVisibility(View.VISIBLE);
                    itr_save_button.setText("Update");
                    mSelectedITRStatus = 0;
                    Toast.makeText(TransactionDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerITRDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });


    }

}