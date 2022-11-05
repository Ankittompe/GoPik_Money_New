package com.qts.gopik_money.Dealer_Activity;

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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.TagID_MODEL;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
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

public class GOATAadharImageCaptureActivity extends AppCompatActivity implements PickiTCallbacks {
    String mMultipartFormData = "multipart/form-data";
    FileOutputStream fo;
    String networkError = "It seems your Network is unstable . Please Try again!";
    ImageView mImgAadhaFront;
    ImageView mImgAadharBack;
    ImageView mImgInvoice;
    File mImgAadhaFrontFile;
    File mImgAadharBackFile;
    File mImgInvoiceFile;
    private static final String IMAGE_DIRECTORY = "/GoPikMoney";
    Button mBtnSend;
    CustPrograssbar custPrograssbar;
    int mOption = 0;
    private Dialog dialogCondition;
    PickiT pickiT;
    TextView mTxtInvoiceFile;
    ImageView  mBackarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goat_activity_aadhar_capture);
        custPrograssbar = new CustPrograssbar();
        pickiT = new PickiT(GOATAadharImageCaptureActivity.this, this, GOATAadharImageCaptureActivity.this);
        dialogCondition = new Dialog(GOATAadharImageCaptureActivity.this);
        mImgAadhaFront = findViewById(R.id.imgAnimalFront);
        mImgAadharBack = findViewById(R.id.imgAnimalSide);
        mImgInvoice = findViewById(R.id.imgFarmerPhoto);
        mTxtInvoiceFile = findViewById(R.id.txtInvoiceFile);
        mBackarrow = (ImageView) findViewById(R.id.arrow);
        mBtnSend = findViewById(R.id.btnSend);
        mBackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(GOATAadharImageCaptureActivity.this, Aadhar_Activity.class);
                startActivity(it);

            }
        });
        mImgAadhaFront.setOnClickListener(view -> {
            mOption = 1;
            selectImageDialog();
        });
        mImgAadharBack.setOnClickListener(view -> {
            mOption = 2;
            selectImageDialog();
        });
        mImgInvoice.setOnClickListener(view -> {
            mOption = 3;
            SelectImagePDFDialog();
        });
        mBtnSend.setOnClickListener(view -> {
            if (mImgAadhaFrontFile != null && mImgAadharBackFile != null && mImgInvoiceFile != null) {
                uploadGoatImages(mImgAadhaFrontFile, mImgAadharBackFile, mImgInvoiceFile);
            } else {
                Toast.makeText(GOATAadharImageCaptureActivity.this, "All Field are mandatory", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void selectImageDialog() {
        final Dialog dialogCondition = new Dialog(this);
        dialogCondition.setContentView(R.layout.upload_image_dailog);
        ImageView mCameraButton = (ImageView) dialogCondition.findViewById(R.id.camera_button);
        ImageView mGalleryButton = (ImageView) dialogCondition.findViewById(R.id.gallery_button);
        dialogCondition.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        mCameraButton.setOnClickListener(view -> {
            takePhotoFromCamera();
            dialogCondition.dismiss();
        });
        mGalleryButton.setOnClickListener(view -> {
            choosePhotoFromGallery();
            dialogCondition.dismiss();
        });
    }

    private void SelectImagePDFDialog() {
        dialogCondition.setContentView(R.layout.business_dailog);
        ImageView mCameraButton = dialogCondition.findViewById(R.id.camera_button);
        ImageView mGalleryButton = dialogCondition.findViewById(R.id.gallery_button);
        ImageView mPdfButton = dialogCondition.findViewById(R.id.pdf_button);
        dialogCondition.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        mCameraButton.setOnClickListener(view -> {
            takePhotoFromCamera();
            dialogCondition.dismiss();
        });
        mGalleryButton.setOnClickListener(view -> {
            choosePhotoFromGallery();
            dialogCondition.dismiss();
        });
        mPdfButton.setOnClickListener(view -> {
            openPDFSelector();
            dialogCondition.dismiss();
        });
    }

    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);
    }

    private void showPDF2(File mFile) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent viewPdf = new Intent(Intent.ACTION_VIEW);
        viewPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri mURI = FileProvider.getUriForFile(GOATAadharImageCaptureActivity.this, GOATAadharImageCaptureActivity.this.getApplicationContext().getPackageName() + ".provider", mFile);
        viewPdf.setDataAndType(mURI, "application/pdf");
        viewPdf.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        GOATAadharImageCaptureActivity.this.startActivity(viewPdf);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 8);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mOption == 1) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImgAadhaFront.setImageBitmap(bitmap);
                mImgAadhaFrontFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK && data != null) {

                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap;
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    mImgAadhaFront.setImageBitmap(bitmap);
                    mImgAadhaFrontFile = new File(saveImageFile(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (mOption == 2) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImgAadharBack.setImageBitmap(bitmap);
                mImgAadharBackFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK && data != null) {

                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap;
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    mImgAadharBack.setImageBitmap(bitmap);
                    mImgAadharBackFile = new File(saveImageFile(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else if (mOption == 3) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImgInvoice.setImageBitmap(bitmap);
                mImgInvoiceFile = new File(saveImageFile(bitmap));
            }else if (requestCode == 3 && resultCode == RESULT_OK) {
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            } else if (requestCode == 8 && resultCode == RESULT_OK && data != null) {
                try {
                    Uri contentURI = data.getData();
                    Bitmap bitmap;
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    mImgInvoice.setImageBitmap(bitmap);
                    mTxtInvoiceFile.setVisibility(View.GONE);
                    mImgInvoiceFile = new File(saveImageFile(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String saveImageFile(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdir();

        }
        try {
            File f = new File(GOATAadharImageCaptureActivity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GoPikMoney");
            f.createNewFile();
            fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(GOATAadharImageCaptureActivity.this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void uploadGoatImages(File mImgAadhaFrontFile, File mImgAadharBackFile, File mImgInvoiceFile) {
        custPrograssbar.prograssCreate(GOATAadharImageCaptureActivity.this);
       // RequestBody mApplicationNo = RequestBody.create(MediaType.parse(mMultipartFormData), "MNK092215850");

        RequestBody mApplicationNo = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        Log.e("Application","-->>"+mApplicationNo);
        RequestBody mBrand = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()));

        RequestBody mRequestBodyFront, mRequestBodySide, mRequestBodyFarmerPhoto;
        MultipartBody.Part mGoatFrontFileData, mGoatSideFileData, mGoatFarmerFileData;

        mRequestBodyFront = RequestBody.create(MediaType.parse(mMultipartFormData), mImgAadhaFrontFile);
        mRequestBodySide = RequestBody.create(MediaType.parse(mMultipartFormData), mImgAadharBackFile);
        mRequestBodyFarmerPhoto = RequestBody.create(MediaType.parse(mMultipartFormData), mImgInvoiceFile);

        mGoatFrontFileData = MultipartBody.Part.createFormData("aadhar_frnt", mImgAadhaFrontFile.getName(), mRequestBodyFront);
        mGoatSideFileData = MultipartBody.Part.createFormData("aadhar_back", mImgAadharBackFile.getName(), mRequestBodySide);
        mGoatFarmerFileData = MultipartBody.Part.createFormData("invc_img", mImgInvoiceFile.getName(), mRequestBodyFarmerPhoto);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.StoreGoatAadharDocDetails(mBrand, mApplicationNo, mGoatFrontFileData, mGoatSideFileData, mGoatFarmerFileData);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.body() != null && response.body().getCode() == 200) {
                    custPrograssbar.closePrograssBar();
                    startActivity(new Intent(GOATAadharImageCaptureActivity.this, GOAT_PAN_CARD_Details.class));
                }
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(GOATAadharImageCaptureActivity.this, networkError, Toast.LENGTH_LONG).show();
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
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String mReason) {
        File mFile = new File(path);
        mImgInvoiceFile = mFile;
        mTxtInvoiceFile.setVisibility(View.VISIBLE);
        mTxtInvoiceFile.setText(mImgInvoiceFile.getAbsolutePath() + "  ==> Click to Open");
        Log.e("mFile ", mFile.getAbsolutePath());
        mTxtInvoiceFile.setOnClickListener(view -> showPDF2(mFile));
    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String mReason) {

    }

}