package com.qts.gopik_money.Shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeper_PO_profile;
import com.qts.gopik_money.Model.Shopkeeperpo_data_MODEL_datalist;
import com.qts.gopik_money.Model.Shopkeeperpo_disversal_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeperpo_data_list_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.ShopkeeperAdapter.PoDataDetailsAdapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

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

public class ApprovedFinacerShopkeeper extends AppCompatActivity implements PickiTCallbacks {
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
    TextView mPdfName;
    TextView confirm;
    ImageView taxinvoiceimg;
    private static final String IMAGE_DIRECTORY = "/financer";
    PickiT pickiT;

    private int GALLERY = 1, CAMERA = 2;
    public int x = 0, y = 0;
    public int pdf = 0;
    ImageView hometoolbar;
    ImageView backarrow;
    int mSelectedBussinessProofStatus = 0;
    String Pdf_Name;
    File mBussinessProofFile = null;
    CardView tenure_roi_cardview;
    TextView tenure_txt;
    TextView roi_txt;
    Button invoice_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_finacer_shopkeeper);
        custPrograssbar = new CustPrograssbar();
        mPdfName=(TextView)findViewById(R.id.pdf_name);
        taxinvoiceno = (TextView) findViewById(R.id.taxinvoiceno);
        taxinvoiceprice = (TextView) findViewById(R.id.taxinvoiceprice);
        date = (TextView) findViewById(R.id.date);
        status = (TextView) findViewById(R.id.status);
        name = (TextView) findViewById(R.id.name);
        confirm = (TextView) findViewById(R.id.confirm);
        taxinvoiceimg= (ImageView) findViewById(R.id.taxinvoiceimg);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        taxinvoiceimg = (ImageView) findViewById(R.id.taxinvoiceimg);
        //Cardview
        tenure_roi_cardview = (CardView) findViewById(R.id.tenure_roi_cardview);
        invoice_view = (Button) findViewById(R.id.invoice_view);

        //ROI and Tenure
        tenure_txt = (TextView) findViewById(R.id.tenure_txt);
        roi_txt = (TextView) findViewById(R.id.roi_txt);
        name.setText(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_NAME, getApplicationContext()));
        pickiT = new PickiT(getApplicationContext(), this, ApprovedFinacerShopkeeper.this);

        taxinvoiceimg.setOnClickListener(v -> {

            mSelectedBussinessProofStatus = 1;
            showPictureDialog();
        });
        confirm.setOnClickListener(v -> {
             if(!((y==1)||(x==1)||(pdf==1))){
                Toast.makeText(ApprovedFinacerShopkeeper.this, "Please Upload a Tax Invoice Image or PDF", Toast.LENGTH_SHORT).show();

            }
             else{
                 shopkeeperpo_disversal();
             }

        });

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(ApprovedFinacerShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(ApprovedFinacerShopkeeper.this, HomeShopkeeper.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
            startActivity(it);

        });
        shopkeeperpo_data_list();
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
                        tenure_txt.setText(response.body().getPayload().getTenure()+" days");
                        roi_txt.setText(response.body().getPayload().getRate_of_interest()+" %");
                        taxinvoiceno.setText(response.body().getPayload().getInvoice_no());
                        taxinvoiceprice.setText(response.body().getPayload().getInvoice_price());
                        status.setText(response.body().getPayload().getStatus());
                        date.setText(response.body().getPayload().getCreated_at());
                        /*   name.setText(response.body().getPayload().get(i).getShopkeeper_name());*/

                    }


                }
            }

            @Override
            public void onFailure(Call<Shopkeeperpo_data_MODEL_datalist> call, Throwable t) {


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
                    taxinvoiceimg.setImageBitmap(bitmap);
                    taxinvoiceimg.getLayoutParams().height = 300;
                    taxinvoiceimg.getLayoutParams().width = 300;
                    taxinvoiceimg.setScaleType(ImageView.ScaleType.FIT_XY);
                    mPdfName.setVisibility(View.GONE);
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
            mPdfName.setVisibility(View.GONE);
            invoice_view.setVisibility(View.GONE);
            y=1;
            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this, "Image Saved!",Toast.LENGTH_SHORT).show();

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


            SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_PO_FINANCER_UPLOADIMAGE, f.getAbsolutePath(), getApplicationContext());
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

            mBussinessProofFile = mFile;
            mPdfName.setText(Pdf_Name);
            mPdfName.setVisibility(View.VISIBLE);
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

    }

    private void shopkeeperpo_disversal() {
        custPrograssbar.prograssCreate(this);
        String musercode = SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_IDD,getApplicationContext());


        RequestBody po_id = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);
        File idFile;

        if (mSelectedBussinessProofStatus == 1) {
            idFile = mBussinessProofFile;
        } else {
            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.SHOPKEEPER_PO_FINANCER_UPLOADIMAGE, getApplicationContext()));
        }
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("shopkeeper_po_report", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeperpo_disversal_MODEL> call = restApis.shopkeeperpo_disversal(po_id,vechileDocUpload2);
        call.enqueue(new Callback<Shopkeeperpo_disversal_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeperpo_disversal_MODEL> call, Response<Shopkeeperpo_disversal_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
               Intent it = new Intent(ApprovedFinacerShopkeeper.this, HomeShopkeeper.class);
                    it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                    startActivity(it);

                }



            }

            @Override
            public void onFailure(Call<Shopkeeperpo_disversal_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }
    private void showPDF2(File mFile) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri URI = FileProvider.getUriForFile(ApprovedFinacerShopkeeper.this, ApprovedFinacerShopkeeper.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(URI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ApprovedFinacerShopkeeper.this.startActivity(viewPdf);
    }

}