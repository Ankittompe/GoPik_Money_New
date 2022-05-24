package com.qts.gopik_loan.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Apiget_panid_details_MODEL;
import com.qts.gopik_loan.Model.Store_panid_details_MODEL;
import com.qts.gopik_loan.Pojo.Apiget_panid_details_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class PAN_CARD_Details extends AppCompatActivity {
    EditText voteridcard;

    ImageView hometoolbar, backarrow, pancard;
    TextView check, name, save_submit;
    String TAG = "loginotp";
    LinearLayout linearlayout;
    CustPrograssbar custPrograssbar;
    private final int PERMISSION_REQUEST_CODE = 1000;
    private static final int RESULT_OK = 123;
    public int x = 0;
    public int y = 0;
    public int z = 0;
    TextView uploadadharfront, uploadadharback, uploaddlfront, uploaddlback, error1;
    CircleImageView imgadharfront, imgadharback, imgdlfront, imgdlback;
    private static final String IMAGE_DIRECTORY = "/gopikmoneypan";
    private int GALLERY = 1, CAMERA = 2;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_a_n__c_a_r_d__details);
        custPrograssbar = new CustPrograssbar();
        error1 = (TextView) findViewById(R.id.error1);
        voteridcard = (EditText) findViewById(R.id.voteridcard);
        name = (TextView) findViewById(R.id.name);
        save_submit = (TextView) findViewById(R.id.save_submit);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"3",getApplicationContext());
        check = (TextView) findViewById(R.id.check);
      /* if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }*/
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
        save_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(y==1){
                    store_panid_details();
                    error1.setVisibility(View.GONE);
                }
                else{
                    error1.setVisibility(View.VISIBLE);
                    error1.setText("Please upload pan image");
                }

            }
        });
        pancard = (ImageView) findViewById(R.id.pancard);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PAN_CARD_Details.this, Voter_ID_CARD_Details.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PAN_CARD_Details.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
        pancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ghf", "hsd");
                showPictureDialog();

                x = 1;
            }
        });
        /* requestMultiplePermissions();*/
      /*  if (!checkPermission()) {
            requestPermission();
        }*/
    }

    private void validation() {
        if ((voteridcard.getText().toString().isEmpty())) {
            voteridcard.setError("Please Enter Your Pan Card No");


        } else {

            apiget_panid_details();
        }
    }

    private void store_panid_details() {
        custPrograssbar.prograssCreate(PAN_CARD_Details.this);
        String mbrand = SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext());
        String mcust_code = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext());
        String mpanno = SharedPref.getStringFromSharedPref(AppConstants.PAN_NO, getApplicationContext());
        String mpanname = SharedPref.getStringFromSharedPref(AppConstants.PAN_NAME, getApplicationContext());
        RequestBody brand = RequestBody.create(MediaType.parse("multipart/form-data"), mbrand);
        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcust_code);
        RequestBody panno = RequestBody.create(MediaType.parse("multipart/form-data"), mpanno);
        RequestBody panname = RequestBody.create(MediaType.parse("multipart/form-data"), mpanname);

        Log.e("testingggg", "testingggg11111" + mbrand);
        Log.e("testingggg", "testingggg22222" + mcust_code);
        Log.e("testingggg", "testingggg33333" + mpanno);
        Log.e("testingggg", "testingggg4444" + mpanname);
        Log.e("testingggg", "testingggg55555" + brand);
        Log.e("testingggg", "testingggg6666" + cust_code);
        Log.e("testingggg", "testingggg77777" + panno);
        Log.e("testingggg", "testingggg88888" + panname);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_PATH, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("panid", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload);
       /* Store_panid_details_POJO pojo = new Store_panid_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND,getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE,getApplicationContext()),voteridcard.getText().toString(),
                SharedPref.getStringFromSharedPref(AppConstants.PAN_NAME,getApplicationContext()));*/
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_panid_details_MODEL> call = restApis.store_panid_details(brand, cust_code, panno, panname, vechileDocUpload);
        call.enqueue(new Callback<Store_panid_details_MODEL>() {
            @Override
            public void onResponse(Call<Store_panid_details_MODEL> call, Response<Store_panid_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        z = 1;
                        Toast.makeText(getApplicationContext(), "Successfully uploaded document", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(PAN_CARD_Details.this, ImageCapture.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Store_panid_details_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void apiget_panid_details() {
        custPrograssbar.prograssCreate(PAN_CARD_Details.this);
        Apiget_panid_details_POJO pojo = new Apiget_panid_details_POJO(voteridcard.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Apiget_panid_details_MODEL> call = restApis.apiget_panid_details(pojo);
        call.enqueue(new Callback<Apiget_panid_details_MODEL>() {
            @Override
            public void onResponse(Call<Apiget_panid_details_MODEL> call, Response<Apiget_panid_details_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        save_submit.setVisibility(View.VISIBLE);
                        check.setVisibility(View.GONE);
                        linearlayout.setVisibility(View.VISIBLE);
                        name.setText(response.body().getPayload().getResult().getName());
                        SharedPref.saveStringInSharedPref(AppConstants.PAN_NAME, response.body().getPayload().getResult().getName(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PAN_NO, voteridcard.getText().toString(), getApplicationContext());


                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Apiget_panid_details_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                voteridcard.setError("Please enter a valid Pan Id no!!!");

            }

        });

    }

    private void requestMultiplePermissions() {


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {


                        // Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "pangopikmoney");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_PATH, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhotoFromCamera();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (x == 1) {
                y = 1;
                pancard.setImageBitmap(thumbnail);

            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(PAN_CARD_Details.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(PAN_CARD_Details.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(PAN_CARD_Details.this, Manifest.permission.CAMERA);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager() && camera_permission == PackageManager.PERMISSION_GRANTED;
        } else {
            return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && camera_permission == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
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
            ActivityCompat.requestPermissions(PAN_CARD_Details.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}

