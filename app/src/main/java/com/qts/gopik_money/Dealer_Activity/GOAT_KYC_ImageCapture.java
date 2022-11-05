package com.qts.gopik_money.Dealer_Activity;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GOAT_KYC_ImageCapture extends AppCompatActivity {

    ImageView mImgAnimalFront, mImgAnimalSide, mImgFarmerPhoto, bankPassbook_Photo;
    File mImgAnimalFrontFile, mImgAnimalSideFile, mImgFarmerPhotoFile, bankPassbook_PhotoFile;
    private static final String IMAGE_DIRECTORY = "/GoPikMoney";
    Button mBtnSend;
    CustPrograssbar custPrograssbar;
    int mOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goat_activity_image_capture);
        custPrograssbar = new CustPrograssbar();

        mImgAnimalFront = findViewById(R.id.imgAnimalFront);
        mImgAnimalSide = findViewById(R.id.imgAnimalSide);
        mImgFarmerPhoto = findViewById(R.id.imgFarmerPhoto);
        bankPassbook_Photo = findViewById(R.id.bankPassbook_Photo);
        mBtnSend = findViewById(R.id.btnSend);

        mImgAnimalFront.setOnClickListener(view -> {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 7);
            mOption = 1;
            selectImageDialog();
        });
        mImgAnimalSide.setOnClickListener(view -> {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 8);
            mOption = 2;
            selectImageDialog();
        });
        mImgFarmerPhoto.setOnClickListener(view -> {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 9);
            mOption = 3;
            selectImageDialog();
        });
        bankPassbook_Photo.setOnClickListener(view -> {
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 10);
            mOption = 4;
            selectImageDialog();
        });
        mBtnSend.setOnClickListener(view -> {
            if (mImgAnimalFrontFile != null && mImgAnimalSideFile != null && mImgFarmerPhotoFile != null && bankPassbook_PhotoFile != null) {
                UploadGoatImages(mImgAnimalFrontFile, mImgAnimalSideFile, mImgFarmerPhotoFile, bankPassbook_PhotoFile);
            } else {
                Toast.makeText(GOAT_KYC_ImageCapture.this, "All Field are mandatory", Toast.LENGTH_SHORT).show();
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
                mImgAnimalFront.setImageBitmap(bitmap);
                mImgAnimalFrontFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Uri contentURI = data.getData();
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        mImgAnimalFront.setImageBitmap(bitmap);
                        mImgAnimalFrontFile = new File(saveImageFile(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (mOption == 2) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImgAnimalSide.setImageBitmap(bitmap);
                mImgAnimalSideFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Uri contentURI = data.getData();
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        mImgAnimalSide.setImageBitmap(bitmap);
                        mImgAnimalSideFile = new File(saveImageFile(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (mOption == 3) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImgFarmerPhoto.setImageBitmap(bitmap);
                mImgFarmerPhotoFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Uri contentURI = data.getData();
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        mImgFarmerPhoto.setImageBitmap(bitmap);
                        mImgFarmerPhotoFile = new File(saveImageFile(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (mOption == 4) {
            if (requestCode == 7 && resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bankPassbook_Photo.setImageBitmap(bitmap);
                bankPassbook_PhotoFile = new File(saveImageFile(bitmap));
            } else if (requestCode == 8 && resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Uri contentURI = data.getData();
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        bankPassbook_Photo.setImageBitmap(bitmap);
                        bankPassbook_PhotoFile = new File(saveImageFile(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String saveImageFile(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        try {
            File f = new File(GOAT_KYC_ImageCapture.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GoPikMoney");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(GOAT_KYC_ImageCapture.this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void UploadGoatImages(File mImgAnimalFrontFile, File mImgAnimalSideFile, File mImgFarmerPhotoFile, File mbankPassbook_PhotoFile) {
        custPrograssbar.prograssCreate(GOAT_KYC_ImageCapture.this);
        RequestBody mCustCode = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RequestBody mBrand = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()));

        RequestBody mRequestBodyFront, mRequestBodySide, mRequestBodyFarmerPhoto, mRequestBodyBankPassbook;
        MultipartBody.Part mGoatFrontFileData, mGoatSideFileData, mGoatFarmerFileData, mBankPassbookData;

        mRequestBodyFront = RequestBody.create(MediaType.parse("multipart/form-data"), mImgAnimalFrontFile);
        mRequestBodySide = RequestBody.create(MediaType.parse("multipart/form-data"), mImgAnimalSideFile);
        mRequestBodyFarmerPhoto = RequestBody.create(MediaType.parse("multipart/form-data"), mImgFarmerPhotoFile);
        mRequestBodyBankPassbook = RequestBody.create(MediaType.parse("multipart/form-data"), mbankPassbook_PhotoFile);

        mGoatFrontFileData = MultipartBody.Part.createFormData("animl_front", mImgAnimalFrontFile.getName(), mRequestBodyFront);
        mGoatSideFileData = MultipartBody.Part.createFormData("animl_side", mImgAnimalSideFile.getName(), mRequestBodySide);
        mGoatFarmerFileData = MultipartBody.Part.createFormData("farmer_image", mImgFarmerPhotoFile.getName(), mRequestBodyFarmerPhoto);
        mBankPassbookData = MultipartBody.Part.createFormData("bank_image", mbankPassbook_PhotoFile.getName(), mRequestBodyBankPassbook);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.StoreGoatOtherDocDetails(mCustCode, mBrand, mGoatFrontFileData, mGoatSideFileData, mGoatFarmerFileData, mBankPassbookData);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
//                        ((MainActivity) requireActivity()).addFragment(mSub_dealer_po_fragment);
                        Log.e(TAG, "UploadGoatImages: " + response.body());
                        startActivity(new Intent(GOAT_KYC_ImageCapture.this, GOAT_Additional_KYC_DEALER_Details.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(GOAT_KYC_ImageCapture.this, "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }
        });
    }


}