package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;

import com.qts.gopik_money.Model.Mymall_shopkeeper_MODEL;

import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;

import com.qts.gopik_money.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shopkeeper_PO_generate extends AppCompatActivity  implements PickiTCallbacks {
    String networkError = "It seems your Network is unstable . Please Try again!";
    ImageView hometoolbar;
    ImageView backarrow;
    ImageView taxinvoiceimg;
    private int GALLERY = 1 ;
    private int CAMERA = 2 ;
    public int x = 0;
    public int y = 0 ;
    public int pdf = 0;
    PickiT pickiT;
    String mMultipartFormData = "multipart/form-data";
    TextView txtSubmitDetails;
    TextView pdf_name;
    EditText taxinvoicenumber;
    EditText texinvoiceprice;
    String currentDateTimeString;
    String format;
    String mPdfName;
    File mBussinessProofFile = null;
    CustPrograssbar custPrograssbar;
    int mSelectedBussinessProofStatus = 0;
    private static final String IMAGE_DIRECTORY = "/financer";
    Button invoice_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_po_generate);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        taxinvoiceimg= (ImageView) findViewById(R.id.taxinvoiceimg);
        txtSubmitDetails=(TextView)findViewById(R.id.txtSubmitDetails);
        pdf_name=(TextView)findViewById(R.id.pdf_name);
        custPrograssbar = new CustPrograssbar();
        texinvoiceprice=(EditText)findViewById(R.id.texinvoiceprice);
        taxinvoicenumber=(EditText)findViewById(R.id.taxinvoicenumber);
        invoice_view=(Button) findViewById(R.id.invoice_view);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format = s.format(new Date());
        currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());



        pickiT = new PickiT(getApplicationContext(), this, Shopkeeper_PO_generate.this);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Shopkeeper_PO_generate.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Shopkeeper_PO_generate.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });

        taxinvoiceimg.setOnClickListener(v -> showPictureDialog());

        txtSubmitDetails.setOnClickListener(v -> {
          if (taxinvoicenumber.getText().toString().equals("")){
              Toast.makeText(Shopkeeper_PO_generate.this, "Please Enter Tax Invoice Number", Toast.LENGTH_SHORT).show();
          }else if(texinvoiceprice.getText().toString().equals("")){
              Toast.makeText(Shopkeeper_PO_generate.this, "Please Enter Tax Invoice Price", Toast.LENGTH_SHORT).show();

          }else if(!((y==1)||(x==1)||(pdf==1))){
              Toast.makeText(Shopkeeper_PO_generate.this, "Please Upload a Tax Invoice Image or PDF", Toast.LENGTH_SHORT).show();

          }else{
              mymall_shopkeeper();
          }

        });
    }



    private void mymall_shopkeeper() {
        custPrograssbar.prograssCreate(this);
        String musercode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext());
        String mtexinvoiceprice = texinvoiceprice.getText().toString();
        String mtaxinvoicenumber = taxinvoicenumber.getText().toString();
        String mtimestamp = format;

        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), musercode);
        RequestBody tax_invoice_price = RequestBody.create(MediaType.parse(mMultipartFormData), mtexinvoiceprice);
        RequestBody tax_invoice_no = RequestBody.create(MediaType.parse(mMultipartFormData), mtaxinvoicenumber);
        RequestBody timestamp = RequestBody.create(MediaType.parse(mMultipartFormData),mtimestamp );
        File idFile;

        if (mSelectedBussinessProofStatus == 1) {
            idFile = mBussinessProofFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_FINANCER_UPLOADIMAGE, getApplicationContext()));


        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("tax_invoice_image", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Mymall_shopkeeper_MODEL> call = restApis.mymall_shopkeeper(user_code,tax_invoice_no,
                tax_invoice_price, vechileDocUpload2,timestamp);
        call.enqueue(new Callback<Mymall_shopkeeper_MODEL>() {
            @Override
            public void onResponse(Call<Mymall_shopkeeper_MODEL> call, Response<Mymall_shopkeeper_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Intent it = new Intent(Shopkeeper_PO_generate.this, HomeShopkeeper.class);
                    it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                    startActivity(it);

                }



            }

            @Override
            public void onFailure(Call<Mymall_shopkeeper_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
        "Select a PDF File"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                        case 2:
                            openPDFSelector();
                            mSelectedBussinessProofStatus = 1;
                            break;

                        default:
                           break;
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
    private void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }
    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                    contentURI);
                    taxinvoiceimg.setImageBitmap(bitmap);
                    taxinvoiceimg.getLayoutParams().height = 400;
                    taxinvoiceimg.getLayoutParams().width = 400;
                    taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                    pdf_name.setVisibility(View.GONE);
                    invoice_view.setVisibility(View.GONE);
                    x=1;
                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            taxinvoiceimg.setImageBitmap(thumbnail);
            taxinvoiceimg.getLayoutParams().height = 400;
            taxinvoiceimg.getLayoutParams().width = 400;
            taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
            pdf_name.setVisibility(View.GONE);
            invoice_view.setVisibility(View.GONE);

            y=1;
            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
            if (mSelectedBussinessProofStatus == 1) {
                pdf = 1;
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

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


            SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_FINANCER_UPLOADIMAGE, f.getAbsolutePath(), getApplicationContext());

            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public void PickiTonUriReturned() {
        // Do nothing because.
    }

    @Override
    public void PickiTonStartListener() {
        // Do nothing because.
    }

    @Override
    public void PickiTonProgressUpdate(int progress) {
        // Do nothing .
    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        File mFile = new File(path);
        mPdfName = mFile.getName();



        if (mSelectedBussinessProofStatus == 1) {

            mBussinessProofFile = mFile;
            pdf_name.setText(mPdfName);
            pdf_name.setVisibility(View.VISIBLE);
            invoice_view.setVisibility(View.VISIBLE);
            taxinvoiceimg.setImageResource(R.drawable.c3);
            taxinvoiceimg.getLayoutParams().height = 150;
            taxinvoiceimg.getLayoutParams().width = 150;
            taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
            invoice_view.setOnClickListener(view -> showPDF2(mFile));
        }

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {
    //Do nothing
    }
/*
    private void ChoosePdfDocument() {
        openPDFSelector();

       */
/* try {
            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
        } catch (Exception exe) {
            exe.printStackTrace();
        }*//*

    }
*/

    private void showPDF2(File mFile) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Log.e("OpenPDF","***");
        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri URI = FileProvider.getUriForFile(Shopkeeper_PO_generate.this, Shopkeeper_PO_generate.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(URI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Shopkeeper_PO_generate.this.startActivity(viewPdf);
    }

}