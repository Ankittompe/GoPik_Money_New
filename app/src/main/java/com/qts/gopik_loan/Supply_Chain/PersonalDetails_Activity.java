package com.qts.gopik_loan.Supply_Chain;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.LogIn_Otp_Verify;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Activity.Voter_ID_CARD_Details;
import com.qts.gopik_loan.Model.DealerAadharFrontDoc_MODEL;
import com.qts.gopik_loan.Model.DealerAdharBackDoc_MODEL;
import com.qts.gopik_loan.Model.DealerPanDoc_MODEL;
import com.qts.gopik_loan.Model.DealerSelfieDoc_MODEL;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

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

public class PersonalDetails_Activity extends AppCompatActivity {
    private final int PERMISSION_REQUEST_CODE = 1000;
    ImageView selfie_upld_sucss,adhaar_upld_back_sucss,upld_pan_front_success,adhaar_upld_sucss;

    LinearLayout upload_selfie_layout,upload_adhaar_front_layout,adhaar_back_layout,pan_front_layout;
    ImageView upload_selfie,upload_adhaar_front,upload_adhaar_back,upload_pancard_front_button;
    ImageView dropdown1_selfie,dropup1_selfie,dropdown1_adhar_front,dropup1_adhar_front,dropdown1_adhar_back,dropup1_adhar_back,dropdown_pan_front,dropup2_pan_front;
    ImageView arrow,hometoolbar;
    private Dialog dialogCondition;
    ImageView CameraButton,GalleryButton;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    private int GALLERY = 1, CAMERA = 2;
    public int x = 0;
    public int y = 0;

    CustPrograssbar custPrograssbar;
    Integer adhar_front_valid1 = 0;
    Integer adhar_front_valid2 = 0;
    Integer adhar_back_valid1 = 0;
    Integer adhar_back_valid2 = 0;
    Integer Pan_valid = 0;
    Integer upld_selfie = 0;
    TextView btsend;
    Integer upload_selfie_success = 0;
    Integer upload_adhar_front_success = 0;
    Integer upload_adhar_back_success = 0;
    Integer upload_pan_success = 0;

    TextView save_selfie,save_adhar_front,save1_adhaar_back,save_pan_front;

    TextView upld_adhar_front_hint,upld_selfie_hint,adhaar_back_hint,upld_pan_front_hint;
    private Context mContext = PersonalDetails_Activity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);


        btsend = findViewById(R.id.submit_prsnl_button);
        custPrograssbar = new CustPrograssbar();
        upld_adhar_front_hint = (TextView) findViewById(R.id.upld_img_hint);
        upld_selfie_hint = findViewById(R.id.upld_selfie_hint);
        adhaar_back_hint = findViewById(R.id.adhaar_back_hint);
        upld_pan_front_hint = findViewById(R.id.upld_pan_front_hint);
        selfie_upld_sucss = findViewById(R.id.selfie_upld_sucss);
        adhaar_upld_back_sucss = findViewById(R.id.adhaar_upld_back_sucss);
        adhaar_upld_sucss = (ImageView) findViewById(R.id.adhaar_upld_sucss);
        upld_pan_front_success = findViewById(R.id.upld_pan_front_success);

        dialogCondition = new Dialog(PersonalDetails_Activity.this);

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(upload_selfie_success==1)){
                    Toast.makeText(PersonalDetails_Activity.this, "Please upload a  Selfie Image", Toast.LENGTH_SHORT).show();
                }else if (!(upload_adhar_front_success==1)){
                    Toast.makeText(PersonalDetails_Activity.this, "Please Upload an Adhaar Front Image ", Toast.LENGTH_SHORT).show();
                } else if (!(upload_adhar_back_success==1)){
                    Toast.makeText(PersonalDetails_Activity.this, "Please Upload an Adhaar Back Image ", Toast.LENGTH_SHORT).show();
                } else if (!(upload_pan_success==1)){
                    Toast.makeText(PersonalDetails_Activity.this, "Please Upload a PanCard  Image ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PersonalDetails_Activity.this, "All Document Submitted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonalDetails_Activity.this,BusinessDetails_Activity.class);
                    startActivity(intent);
                }
            }
        });
        //Back and Home
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        //Upload Button
        upload_selfie= (ImageView) findViewById(R.id.upload_selfie);
        upload_adhaar_front = (ImageView) findViewById(R.id.upload_adhaar);
        upload_adhaar_back = (ImageView) findViewById(R.id.upload_adhaar_back);
        upload_pancard_front_button = (ImageView) findViewById(R.id.upload_pancard_front_button);

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

        //Layouts
        upload_selfie_layout = (LinearLayout)findViewById(R.id.upload_selfie_layout);
        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        adhaar_back_layout = findViewById(R.id.adhaar_back_layout);
        pan_front_layout = findViewById(R.id.pan_front_layout);

        //Save Button
        save_selfie= (TextView) findViewById(R.id.save_selfie);
        save_adhar_front= (TextView) findViewById(R.id.save_adhar_front);
        save1_adhaar_back= (TextView) findViewById(R.id.save1_adhaar_back);
        save_pan_front= (TextView) findViewById(R.id.save_pan_front);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetails_Activity.this, MainActivity.class);

                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(intent);
            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetails_Activity.this, MainActivity.class);

                intent.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intent);
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
        //Upload Selfie
        upload_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                y = 4;
                x=4;
            }
        });
        upload_adhaar_front_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                x = 1;
                y = 1;
            }
        });
        upload_adhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                x = 2;
                y = 2;
            }
        });
        upload_pancard_front_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
                x = 3;
                y = 3;

            }
        });
        //Save Document
        save_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(upld_selfie==1)){
                    upld_selfie_hint.setVisibility(View.VISIBLE);
                }else {
                    custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                    DealerSelfieDoc();

                }

            }
        });
        save_adhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(adhar_front_valid1== 1||adhar_front_valid2==1)){
                    upld_adhar_front_hint.setVisibility(View.VISIBLE);

                }else{
                    custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                    DealerAadharFrontDoc();
                }

            }
        });
        save1_adhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(adhar_back_valid1==2||adhar_back_valid2==2)){
                    adhaar_back_hint.setVisibility(View.VISIBLE);
                }else{
                    custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
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
                    custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                    DealerPanDoc();
                }

            }
        });
        if (!checkPermission()) {
            requestPermission();
        }
        if (!checkPermission_version()) {
            requestPermission();
        }
    }
    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, Manifest.permission.CAMERA);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager() && camera_permission == PackageManager.PERMISSION_GRANTED;
        }  else {
            return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && camera_permission == PackageManager.PERMISSION_GRANTED;

        }


    }
    private boolean checkPermission_version() {
        Log.e("jcdbc","ccnds");
        int read_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, Manifest.permission.CAMERA);

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("jcdbc","hfthth");
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions(PersonalDetails_Activity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }


        }
        return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && camera_permission == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            Log.e("jcdbc","bbbbbb");
            /*try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }*/
            try {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(PersonalDetails_Activity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void SelectImageDailog() {

            dialogCondition.setContentView(R.layout.upload_image_dailog);
            CameraButton = (ImageView) dialogCondition.findViewById(R.id.camera_button);
            GalleryButton = (ImageView) dialogCondition.findViewById(R.id.gallery_button);
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
                        upload_adhaar_front.setImageBitmap(bitmap);
                        adhar_front_valid1 = 1;
                        upload_adhaar_front.getLayoutParams().height = 300;
                        upload_adhaar_front.getLayoutParams().width = 300;
                        upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                        upld_adhar_front_hint.setVisibility(View.GONE);
                    } else if (x == 2) {
                        upload_adhaar_back.setImageBitmap(bitmap);
                        adhaar_back_hint.setVisibility(View.GONE);
                        upload_adhaar_back.getLayoutParams().height = 300;
                        upload_adhaar_back.getLayoutParams().width = 300;
                        upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                        adhar_back_valid1 = 2;

                    } else if (x == 3) {
                        upload_pancard_front_button.setImageBitmap(bitmap);
                        upld_pan_front_hint.setVisibility(View.GONE);
                        upload_pancard_front_button.getLayoutParams().height = 300;
                        upload_pancard_front_button.getLayoutParams().width = 300;
                        upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                        Pan_valid = 1;
                    } else if (x==4){
                        upload_selfie.setImageBitmap(bitmap);
                        upld_selfie_hint.setVisibility(View.GONE);
                        upload_selfie.getLayoutParams().height = 300;
                        upload_selfie.getLayoutParams().width = 300;
                        upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                        upld_selfie = 1;
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

                upload_adhaar_front.setImageBitmap(thumbnail);
                adhar_front_valid2 = 1;
                upld_adhar_front_hint.setVisibility(View.GONE);
                upload_adhaar_front.getLayoutParams().height = 300;
                upload_adhaar_front.getLayoutParams().width = 300;
                upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
            } else if (y == 2) {
                upload_adhaar_back.setImageBitmap(thumbnail);
                adhaar_back_hint.setVisibility(View.GONE);
                adhar_back_valid2 = 2;
                upload_adhaar_back.getLayoutParams().height = 300;
                upload_adhaar_back.getLayoutParams().width = 300;
                upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);

            } else if (y == 3) {
                upload_pancard_front_button.setImageBitmap(thumbnail);
                upld_pan_front_hint.setVisibility(View.GONE);
                Pan_valid = 1;
                upload_pancard_front_button.getLayoutParams().height = 300;
                upload_pancard_front_button.getLayoutParams().width = 300;
                upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);


            }else if (y==4){
                upload_selfie.setImageBitmap(thumbnail);
                upload_selfie.getLayoutParams().height = 300;
                upload_selfie.getLayoutParams().width = 300;
                upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                upld_selfie_hint.setVisibility(View.GONE);
                upld_selfie = 1;
            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public String saveImage(Bitmap myBitmap) {
        Log.e("TAG", "File Saved1");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Log.e("TAG", "File Saved2---->>>>>>");
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
    private void DealerSelfieDoc() {
        Log.e("Method_Call","calling--->");
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));
        Log.e("Method_Call","calling--->2");
        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("selfie", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("testingggg", "network-->>");
        Call<DealerSelfieDoc_MODEL> call = restApis.DealerSelfieDoc(user_code, vechileDocUpload2);
        Log.e("testingggg", "Call_network-->>");
        call.enqueue(new Callback<DealerSelfieDoc_MODEL>() {

            @Override
            public void onResponse(Call<DealerSelfieDoc_MODEL> call, Response<DealerSelfieDoc_MODEL> response) {
                Log.e("testingggg", "Response-->>");
                if (response.body() != null) {

                    Log.e("testingggg", "success_pan");
                    custPrograssbar.closePrograssBar();
                    selfie_upld_sucss.setVisibility(View.VISIBLE);
                    upload_selfie_layout.setVisibility(View.GONE);
                    dropdown1_selfie.setVisibility(View.VISIBLE);
                    dropup1_selfie.setVisibility(View.GONE);
                    save_selfie.setText("Update");
                    upload_selfie_success = 1;
                    Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<DealerSelfieDoc_MODEL> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                Log.e("Amit","Fail--->>"+t);
            }

        });

    }
    private void DealerAadharFrontDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("testingggg", "network-->>");
        Call<DealerAadharFrontDoc_MODEL> call = restApis.DealerAadharFrontDoc(user_code, vechileDocUpload2);
        Log.e("testingggg", "Call_network-->>");

        call.enqueue(new Callback<DealerAadharFrontDoc_MODEL>() {

            @Override
            public void onResponse(Call<DealerAadharFrontDoc_MODEL> call, Response<DealerAadharFrontDoc_MODEL> response) {
                Log.e("testingggg", "responseCalll-->>");
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_pan");
                    upload_adhaar_front_layout.setVisibility(View.GONE);
                    adhaar_upld_sucss.setVisibility(View.VISIBLE);
                    dropdown1_adhar_front.setVisibility(View.VISIBLE);
                    dropup1_adhar_front.setVisibility(View.GONE);
                    save_adhar_front.setText("Update");
                    upload_adhar_front_success = 1;
                    Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<DealerAadharFrontDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                Log.e("Amit","Fail--->>");
            }

        });

    }
    private void DealerAdharBackDoc() {
        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

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
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");

                    dropup1_adhar_back.setVisibility(View.GONE);
                    dropdown1_adhar_back.setVisibility(View.VISIBLE);
                    adhaar_back_layout.setVisibility(View.GONE);
                    adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                    save1_adhaar_back.setText("Update");
                    upload_adhar_back_success=1;
                    Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }



            }

            @Override
            public void onFailure(Call<DealerAdharBackDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });


    }

    private void DealerPanDoc() {

        String musercode = "47436";
        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,getApplicationContext()));

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
                    custPrograssbar.closePrograssBar();
                    Log.e("testingggg", "success_adhar_back");
                    dropup2_pan_front.setVisibility(View.GONE);
                    dropdown_pan_front.setVisibility(View.VISIBLE);
                    pan_front_layout.setVisibility(View.GONE);
                    upld_pan_front_success.setVisibility(View.VISIBLE);
                    save_pan_front.setText("Update");
                    upload_pan_success = 1;
                    Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<DealerPanDoc_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }



}