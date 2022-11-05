package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;

import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Store_App_Document_Details_MODEL;
import com.qts.gopik_money.Model.Store_Bank_Document_Details_MODEL;
import com.qts.gopik_money.Model.Store_Income_Document_Details_MODEL;
import com.qts.gopik_money.Model.Store_Land_Document_Details_MODEL;
import com.qts.gopik_money.Model.Store_Margin_Document_Details_MODEL;
import com.qts.gopik_money.Model.Store_Utility_Document_Details_MODEL;
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

public class ImageCapture extends AppCompatActivity {
    String mMultipartFormData = "multipart/form-data";
    String networkError = "It seems your Network is unstable . Please Try again!";
    String mSelectAction ="Select Action";
    public int x = 0;
    public int y = 0;
    Integer Bill_Image_saved_status1 = 0;
    Integer ApplicationImage_saved_status = 0;
    Integer MarginPhoto_saved_status = 0;
    Integer Income_saved_status = 0;
    Integer Bank_statment_saved_status = 0;
    Integer upload_bill_valid = 0;
    Integer upload_applcation_valid = 0;
    Integer upload_income_valid = 0;
    Integer upload_bank_valid = 0;
    Integer upload_land_valid = 0;
    Integer upload_mrgn_valid = 0;
    Integer upload_uploadelectricitybillimg_valid = 0;
    Integer upload_applicationphptographimg_valid = 0;
    FileOutputStream fo;
    Integer save_success1 = 0;
    Integer save_success2 = 0;
    Integer save_success3 = 0;
    Integer save_success4 = 0;
    Integer save_success5 = 0;
    Integer save_success6 = 0;
    private static final String IMAGE_DIRECTORY = "/gopikkyc1";
    private int GALLERY = 1;
    private int CAMERA = 2;
    TextView  applicationphptograph;
    ImageView votersideimg;
    ImageView voterbackimg;
    ImageView uploadpanimg;
    ImageView uploadmargincopyimg;
    ImageView uploadbankpassbookimg;
    ImageView uploadelectricitybillimg;
    ImageView applicationphptographimg;
    ImageView income_proof_image;
    ImageView uploadlandimg;
    TextView btsend;
    TextView save1;
    TextView save2;
    TextView save3;
    TextView save4;
    TextView save5;
    TextView save6;
    String TAG = "loginotp";
    ImageView hometoolbar;
    ImageView backarrow;
    CustPrograssbar custPrograssbar;

    ImageView dropdown1;
    ImageView dropdown_App_photo;
    ImageView dropdown_margin_photo;
    ImageView dropdown_income_photo;
    ImageView dropdown_bank_photo;
    ImageView dropdown_lanf_photo;
    ImageView dropup1;
    ImageView dropup2;
    ImageView dropup3;
    ImageView dropup4;
    ImageView dropup5;
    ImageView dropup6;
    TextView upld_img_hint;
    TextView upld_appltn_hint;
    TextView upld_mrgn_hint;
    TextView upld_income_proof;
    TextView upld_bank_stmnt_hint;
    TextView error1;
    ImageView bill_upld_sucss;
    ImageView upld_mrgn_succ;
    ImageView income_success_img;
    ImageView land_upld_sccs;
    ImageView upload_bank_succs;
    ImageView upld_application_succss;
    LinearLayout utility_bill_layout;
    LinearLayout application_copy_layout;
    LinearLayout margin_copy_layout;
    LinearLayout income_proof_layout;
    LinearLayout bank_statement_layout;
    LinearLayout land_docu_layout;
    TextView unsaved_img_error_massage;
    TextView unsaved_img_error_massage2;
    TextView unsaved_img_error_massage3;
    TextView unsaved_img_error_massage4;
    TextView unsaved_img_error_massage5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        votersideimg = (ImageView) findViewById(R.id.votersideimg);
        voterbackimg = (ImageView) findViewById(R.id.voterbackimg);
        uploadpanimg = (ImageView) findViewById(R.id.uploadpanimg);

        uploadmargincopyimg = (ImageView) findViewById(R.id.uploadmargincopyimg);
        custPrograssbar = new CustPrograssbar();

        applicationphptograph = (TextView) findViewById(R.id.applicationphptograph);

        uploadbankpassbookimg = (ImageView) findViewById(R.id.uploadbankpassbookimg);

        applicationphptographimg = (ImageView) findViewById(R.id.applicationphptographimg);

        income_proof_image = (ImageView) findViewById(R.id.income_proof_image);

        uploadelectricitybillimg = (ImageView) findViewById(R.id.uploadelectricitybillimg);
        uploadlandimg = (ImageView) findViewById(R.id.uploadlandimg);

        btsend = (TextView) findViewById(R.id.btsend);
        save1 = (TextView) findViewById(R.id.save1);
        save2 = (TextView) findViewById(R.id.save2);
        save3 = (TextView) findViewById(R.id.save3);
        save4 = (TextView) findViewById(R.id.save4);
        save5 = (TextView) findViewById(R.id.save5);
        save6 = (TextView) findViewById(R.id.save6);

        bill_upld_sucss = (ImageView) findViewById(R.id.bill_upld_sucss);
        upld_mrgn_succ = (ImageView) findViewById(R.id.upld_mrgn_succ);
        income_success_img =
                (ImageView) findViewById(R.id.income_success_img);
        land_upld_sccs = (ImageView) findViewById(R.id.land_upld_sccs);
        upload_bank_succs =
                (ImageView) findViewById(R.id.upload_bank_succs);
        upld_application_succss =
                (ImageView) findViewById(R.id.upld_application_succss);

        utility_bill_layout = findViewById(R.id.upload_bill_layout);
        application_copy_layout =
                findViewById(R.id.upload_applctn_copy);
        margin_copy_layout = findViewById(R.id.upload_margin_layout);
        income_proof_layout = findViewById(R.id.upload_income_layout);
        bank_statement_layout = findViewById(R.id.upload_bank_layout);
        land_docu_layout = findViewById(R.id.upload_land_layout);


        dropdown1 = findViewById(R.id.dropdown1);
        dropdown_App_photo = findViewById(R.id.dropdown_App_photo);
        dropdown_margin_photo =
                findViewById(R.id.dropdown_margin_photo);
        dropdown_income_photo =
                findViewById(R.id.dropdown_income_photo);
        dropdown_bank_photo = findViewById(R.id.dropdown_bank_photo);
        dropdown_lanf_photo = findViewById(R.id.dropdown_lanf_photo);


        dropup1 = findViewById(R.id.dropup1);
        dropup2 = findViewById(R.id.dropup2);
        dropup3 = findViewById(R.id.dropup3);
        dropup4 = findViewById(R.id.dropup4);
        dropup5 = findViewById(R.id.dropup5);
        dropup6 = findViewById(R.id.dropup6);

        upld_img_hint = (TextView) findViewById(R.id.upld_img_hint);
        upld_appltn_hint =
                (TextView) findViewById(R.id.upld_appltn_hint);
        upld_mrgn_hint = (TextView) findViewById(R.id.upld_mrgn_hint);
        upld_income_proof =
                (TextView) findViewById(R.id.upld_income_proof);
        upld_bank_stmnt_hint =
                (TextView) findViewById(R.id.upld_bank_stmnt_hint);
        error1 = (TextView) findViewById(R.id.error1);


        unsaved_img_error_massage = (TextView) findViewById(R.id.unsaved_img_error_massage);
        unsaved_img_error_massage2 = (TextView) findViewById(R.id.unsaved_img_error_massage2);
        unsaved_img_error_massage3= (TextView)findViewById(R.id.unsaved_img_error_massage3);
        unsaved_img_error_massage4= (TextView)findViewById(R.id.unsaved_img_error_massage4);
        unsaved_img_error_massage5= (TextView)findViewById(R.id.unsaved_img_error_massage5);


        dropdown1.setOnClickListener(v -> {
            dropup1.setVisibility(View.VISIBLE);
            utility_bill_layout.setVisibility(View.VISIBLE);
            dropdown1.setVisibility(View.GONE);
        });

        dropup1.setOnClickListener(v -> {
            dropdown1.setVisibility(View.VISIBLE);
            dropup1.setVisibility(View.GONE);
            utility_bill_layout.setVisibility(View.GONE);
        });
        dropdown_App_photo.setOnClickListener(v -> {
            dropup2.setVisibility(View.VISIBLE);
            dropdown_App_photo.setVisibility(View.GONE);
            application_copy_layout.setVisibility(View.VISIBLE);
        });
        dropup2.setOnClickListener(v -> {
            dropdown_App_photo.setVisibility(View.VISIBLE);
            dropup2.setVisibility(View.GONE);
            application_copy_layout.setVisibility(View.GONE);
        });

        dropdown_margin_photo.setOnClickListener(v -> {
            dropup3.setVisibility(View.VISIBLE);
            dropdown_margin_photo.setVisibility(View.GONE);
            margin_copy_layout.setVisibility(View.VISIBLE);
        });
        dropup3.setOnClickListener(v -> {
            dropup3.setVisibility(View.GONE);
            dropdown_margin_photo.setVisibility(View.VISIBLE);
            margin_copy_layout.setVisibility(View.GONE);
        });

        dropdown_income_photo.setOnClickListener(v -> {
            dropdown_income_photo.setVisibility(View.GONE);
            dropup4.setVisibility(View.VISIBLE);
            income_proof_layout.setVisibility(View.VISIBLE);
        });
        dropup4.setOnClickListener(v -> {
            dropdown_income_photo.setVisibility(View.VISIBLE);
            dropup4.setVisibility(View.GONE);
            income_proof_layout.setVisibility(View.GONE);
        });

        dropdown_bank_photo.setOnClickListener(v -> {
            dropdown_bank_photo.setVisibility(View.GONE);
            dropup5.setVisibility(View.VISIBLE);
            bank_statement_layout.setVisibility(View.VISIBLE);
        });
        dropup5.setOnClickListener(v -> {
            dropdown_bank_photo.setVisibility(View.VISIBLE);
            dropup5.setVisibility(View.GONE);
            bank_statement_layout.setVisibility(View.GONE);
        });

        dropdown_lanf_photo.setOnClickListener(v -> {
            dropdown_lanf_photo.setVisibility(View.GONE);
            dropup6.setVisibility(View.VISIBLE);
            land_docu_layout.setVisibility(View.VISIBLE);
        });
        dropup6.setOnClickListener(v -> {
            dropdown_lanf_photo.setVisibility(View.VISIBLE);
            dropup6.setVisibility(View.GONE);
            land_docu_layout.setVisibility(View.GONE);
        });




        votersideimg.setOnClickListener(v -> {
            showPictureDialog();

        });
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);

        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(ImageCapture.this,
                    PAN_CARD_Details.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(ImageCapture.this,
                    MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY,
                    AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });

        btsend.setOnClickListener(v -> {

            if (save_success1 == 1 && save_success2 == 1 &&
                    save_success3 == 1 && save_success4 == 1 && save_success5 == 1
                    && save_success6 == 1) {
                Intent it = new Intent(getApplicationContext(),
                        Additional_KYC_DEALER_Details.class);
                startActivity(it);
            } else {
                Toast.makeText(ImageCapture.this, "Please fill up all the data ", Toast.LENGTH_SHORT).show();
            }

        });

        save1.setOnClickListener(v -> {
            if (upload_bill_valid == 1 ||
                    upload_uploadelectricitybillimg_valid == 1) {
                upld_img_hint.setVisibility(View.GONE);
                Store_Utility_Document_Details();
                Bill_Image_saved_status1=1;
                unsaved_img_error_massage.setVisibility(View.GONE);
            } else {
                upld_img_hint.setVisibility(View.VISIBLE);
            }

        });
        save2.setOnClickListener(v -> {
            if (upload_applcation_valid == 2 ||
                    upload_applicationphptographimg_valid == 1) {
                upld_appltn_hint.setVisibility(View.GONE);
                Store_App_Document_Details();
                ApplicationImage_saved_status=1;
                unsaved_img_error_massage2.setVisibility(View.GONE);
            } else {
                upld_appltn_hint.setVisibility(View.VISIBLE);
            }


        });
        save3.setOnClickListener(v -> {

            if (upload_mrgn_valid == 6) {
                upld_mrgn_hint.setVisibility(View.GONE);
                Store_Margin_Document_Details();
                MarginPhoto_saved_status=1;
                unsaved_img_error_massage3.setVisibility(View.GONE);

            } else {
                upld_mrgn_hint.setVisibility(View.VISIBLE);

            }


        });
        save4.setOnClickListener(v -> {

            if (upload_income_valid == 3) {
                upld_income_proof.setVisibility(View.GONE);
                Store_Income_Document_Details();
                unsaved_img_error_massage4.setVisibility(View.GONE);
                Income_saved_status=1;
            } else {
                upld_income_proof.setVisibility(View.VISIBLE);
            }


        });
        save5.setOnClickListener(v -> {

            if (upload_bank_valid == 4) {
                upld_bank_stmnt_hint.setVisibility(View.GONE);
                Store_Bank_Document_Details();
                unsaved_img_error_massage5.setVisibility(View.GONE);
                Bank_statment_saved_status=1;

            } else {
                upld_bank_stmnt_hint.setVisibility(View.VISIBLE);
            }

        });
        save6.setOnClickListener(v -> {
            if (upload_land_valid == 5) {
                error1.setVisibility(View.GONE);
                Store_Land_Document_Details();
            } else {
                error1.setVisibility(View.VISIBLE);
            }
        });

        uploadmargincopyimg.setOnClickListener(v -> {
            if (ApplicationImage_saved_status==1){
                showonlyCamera();

                y = 3;
            }
            else {
                unsaved_img_error_massage2.setVisibility(View.VISIBLE);
            }
        });

        income_proof_image.setOnClickListener(v -> {
            if (MarginPhoto_saved_status==1){
                showonlyGallery();
                x = 4;
            }else{
                unsaved_img_error_massage3.setVisibility(View.VISIBLE);
            }

        });


        uploadbankpassbookimg.setOnClickListener(v -> {
            if (Income_saved_status==1){
                showonlyGallery();
                x = 3;
            }else{
                unsaved_img_error_massage4.setVisibility(View.VISIBLE);

            }

        });

        uploadelectricitybillimg.setOnClickListener(v -> {
            showPictureDialog();
            x = 1;
            y = 1;
        });
        applicationphptographimg.setOnClickListener(v -> {
            if (Bill_Image_saved_status1==1){
                showPictureDialog();
                x = 2;
                y = 2;
            }else {
                unsaved_img_error_massage.setVisibility(View.VISIBLE);
            }

        });
        uploadlandimg.setOnClickListener(v -> {
            if (Bank_statment_saved_status==1){
                showonlyGallery();
                x = 5;
            }else {
                unsaved_img_error_massage5.setVisibility(View.VISIBLE);
            }

        });


    }

    private void showonlyGallery() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle(mSelectAction);
        String[] pictureDialogItems = {
                "Select photo from gallery"
        };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    if (which == 0) {
                        choosePhotoFromGallary();
                    }
                });
        pictureDialog.show();
    }


    private void showonlyCamera() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle(mSelectAction);
        String[] pictureDialogItems = {
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    if (which == 0) {
                        takePhotoFromCamera();
                    }
                });
        pictureDialog.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
        pictureDialog.setTitle(mSelectAction);
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                        default:
                            takePhotoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
                    if (x == 1) {
                        uploadelectricitybillimg.setImageBitmap(bitmap);
                        upload_bill_valid = 1;
                    } else if (x == 2) {
                        applicationphptographimg.setImageBitmap(bitmap);
                        upload_applcation_valid = 2;
                    } else if (x == 4) {
                        income_proof_image.setImageBitmap(bitmap);
                        upload_income_valid = 3;

                    } else if (x == 3) {
                        uploadbankpassbookimg.setImageBitmap(bitmap);
                        upload_bank_valid = 4;
                    } else if (x == 5) {
                        uploadlandimg.setImageBitmap(bitmap);
                        upload_land_valid = 5;

                    }
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (y == 1) {
                upload_uploadelectricitybillimg_valid = 1;
                uploadelectricitybillimg.setImageBitmap(thumbnail);

            } else if (y == 2) {
                upload_applicationphptographimg_valid = 1;
                applicationphptographimg.setImageBitmap(thumbnail);

            } else if (y == 3) {
                uploadmargincopyimg.setImageBitmap(thumbnail);

                upload_mrgn_valid = 6;
            }

            saveImage(thumbnail);
        }

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

            fo= new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();


            SharedPref.saveStringInSharedPref(AppConstants.KYC_DETAILS_IAMGEE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void Store_Utility_Document_Details() {
        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("utility",
                idFile.getName(), mFile1);
        RestApis restApis =
                NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_Utility_Document_Details_MODEL> call =
                restApis.Store_Utility_Document_Details(brand, cust_code, vechileDocUpload2
                );
        call.enqueue(new
                             Callback<Store_Utility_Document_Details_MODEL>() {
                                 @Override
                                 public void
                                 onResponse(Call<Store_Utility_Document_Details_MODEL> call,
                                            Response<Store_Utility_Document_Details_MODEL> response) {
                                     if (response.body() != null) {
                                         custPrograssbar.closePrograssBar();
                                         if (response.body().getCode() == 200) {


                                             utility_bill_layout.setVisibility(View.GONE);
                                             dropup1.setVisibility(View.GONE);
                                             dropdown1.setVisibility(View.VISIBLE);
                                             bill_upld_sucss.setVisibility(View.VISIBLE);
                                             utility_bill_layout.setVisibility(View.GONE);
                                             save_success1 = 1;

                                             Toast.makeText(ImageCapture.this, "Utility bill document uploaded successfully", Toast.LENGTH_SHORT).show();
                                         } else {
                                             Toast.makeText(getApplicationContext(),
                                                     networkError, Toast.LENGTH_LONG).show();
                                         }


                                     }

                                 }

                                 @Override
                                 public void
                                 onFailure(Call<Store_Utility_Document_Details_MODEL> call, Throwable t) {


                                     Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                                 }

                             });

    }

    private void Store_App_Document_Details() {

        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("appltn",
                idFile.getName(), mFile1);

        RestApis restApis =
                NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_App_Document_Details_MODEL> call =
                restApis.Store_App_Document_Details(brand, cust_code, vechileDocUpload2);
        call.enqueue(new Callback<Store_App_Document_Details_MODEL>() {
            @Override
            public void
            onResponse(Call<Store_App_Document_Details_MODEL> call,
                       Response<Store_App_Document_Details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {


                        application_copy_layout.setVisibility(View.GONE);
                        dropdown_App_photo.setVisibility(View.VISIBLE);
                        dropup2.setVisibility(View.GONE);
                        upld_application_succss.setVisibility(View.VISIBLE);
                        utility_bill_layout.setVisibility(View.GONE);
                        save_success2 = 1;
                        Toast.makeText(ImageCapture.this, "Application document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Store_App_Document_Details_MODEL>
                                          call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Store_Margin_Document_Details() {
        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("margin",
                idFile.getName(), mFile1);



        RestApis restApis =
                NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_Margin_Document_Details_MODEL> call =
                restApis.Store_Margin_Document_Details(brand, cust_code, vechileDocUpload2
                );
        call.enqueue(new Callback<Store_Margin_Document_Details_MODEL>() {
            @Override
            public void
            onResponse(Call<Store_Margin_Document_Details_MODEL> call,
                       Response<Store_Margin_Document_Details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {

                        margin_copy_layout.setVisibility(View.GONE);
                        dropup3.setVisibility(View.GONE);

                        dropdown_margin_photo.setVisibility(View.VISIBLE);
                        upld_mrgn_succ.setVisibility(View.VISIBLE);
                        margin_copy_layout.setVisibility(View.GONE);
                        save_success3 = 1;

                        Toast.makeText(ImageCapture.this, "Margin document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void
            onFailure(Call<Store_Margin_Document_Details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Store_Land_Document_Details() {
        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("land",
                idFile.getName(), mFile1);
        RestApis restApis =
                NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_Land_Document_Details_MODEL> call =
                restApis.Store_Land_Document_Details(brand, cust_code, vechileDocUpload2
                );
        call.enqueue(new Callback<Store_Land_Document_Details_MODEL>() {
            @Override
            public void
            onResponse(Call<Store_Land_Document_Details_MODEL> call,
                       Response<Store_Land_Document_Details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {
                        dropup6.setVisibility(View.GONE);
                        dropdown_lanf_photo.setVisibility(View.VISIBLE);
                        land_upld_sccs.setVisibility(View.VISIBLE);
                        land_docu_layout.setVisibility(View.GONE);
                        save_success6 = 1;
                        Toast.makeText(ImageCapture.this, "Land document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void
            onFailure(Call<Store_Land_Document_Details_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void Store_Bank_Document_Details() {
        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("bank",
                idFile.getName(), mFile1);

        RestApis restApis =
                NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Store_Bank_Document_Details_MODEL> call =
                restApis.Store_Bank_Document_Details(brand, cust_code, vechileDocUpload2
                );
        call.enqueue(new Callback<Store_Bank_Document_Details_MODEL>() {
            @Override
            public void
            onResponse(Call<Store_Bank_Document_Details_MODEL> call,
                       Response<Store_Bank_Document_Details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {

                        dropdown_bank_photo.setVisibility(View.VISIBLE);
                        dropup5.setVisibility(View.GONE);
                        upload_bank_succs.setVisibility(View.VISIBLE);
                        bank_statement_layout.setVisibility(View.GONE);
                        save_success5 = 1;
                        Toast.makeText(ImageCapture.this, "Bank document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void
            onFailure(Call<Store_Bank_Document_Details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Store_Income_Document_Details() {
        custPrograssbar.prograssCreate(ImageCapture.this);
        String mbrand =
                SharedPref.getStringFromSharedPref(AppConstants.BRAND,
                        getApplicationContext());
        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,
                        getApplicationContext());

        RequestBody brand =
                RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code =
                RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.KYC_DETAILS_IAMGEE,
                getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("income",
                idFile.getName(), mFile1);

        RestApis restApis =
                NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Store_Income_Document_Details_MODEL> call =
                restApis.Store_Income_Document_Details(brand, cust_code, vechileDocUpload2
                );
        call.enqueue(new Callback<Store_Income_Document_Details_MODEL>() {
            @Override
            public void
            onResponse(Call<Store_Income_Document_Details_MODEL> call,
                       Response<Store_Income_Document_Details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {
                        income_proof_layout.setVisibility(View.GONE);
                        dropdown_income_photo.setVisibility(View.VISIBLE);
                        dropup4.setVisibility(View.GONE);
                        income_success_img.setVisibility(View.VISIBLE);
                        save_success4 = 1;
                        Toast.makeText(ImageCapture.this, "Income document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                networkError, Toast.LENGTH_LONG).show();
                    }

                }
                custPrograssbar.closePrograssBar();
            }
            @Override
            public void
            onFailure(Call<Store_Income_Document_Details_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }
        });
    }
}