package com.qts.gopik_loan.Dealer_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import com.qts.gopik_loan.Model.Get_voterid_details_MODEL;
import com.qts.gopik_loan.Model.Store_voterid_back_details_MODEL;
import com.qts.gopik_loan.Model.store_voterid_details_MODEL;
import com.qts.gopik_loan.Pojo.Get_voterid_details_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class Voter_ID_CARD_Details extends AppCompatActivity {
    EditText voteridcard;
    TextView check, name, relname, age, address, save_submit;
    String TAG = "loginotp";
    LinearLayout linearlayout;
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar, backarrow, voteridback, voteridfront;
    private static final int RESULT_OK = 123;
    public int x = 0;
    public int z = 0;
    public int m = 0;
    public int validation1 = 0;
    public int validation2 = 0;
    public int savevoter = 0;
    TextView uploadadharfront, uploadadharback, uploaddlfront, uploaddlback;
    CircleImageView imgadharfront, imgadharback, imgdlfront, imgdlback;
    private static final String IMAGE_DIRECTORY = "/gopikmoneyloan";
    private int GALLERY = 1, CAMERA = 2;
    TextView update, setname, setemail, phone, error2, error1;
    ImageView votersideimg, voterbackimg, uploadpanimg, uploadmargincopyimg, uploadbankpassbookimg,
            uploadelectricitybillimg, applicationphptographimg, employmentproofimg;
    TextView btsend, save2, save1;

    File adharfrontfile, adharbackfile, dlfrontfile, dlbackfile;
    FileOutputStream fo;
    ByteArrayOutputStream bytes;
    private final int PERMISSION_REQUEST_CODE = 1000;
    private Context mContext = Voter_ID_CARD_Details.this;

    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter__i_d__c_a_r_d__details);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        voteridcard = (EditText) findViewById(R.id.voteridcard);
        check = (TextView) findViewById(R.id.check);
        name = (TextView) findViewById(R.id.name1);
        relname = (TextView) findViewById(R.id.relname);
        age = (TextView) findViewById(R.id.age);
        address = (TextView) findViewById(R.id.address);
        save_submit = (TextView) findViewById(R.id.save_submit);
        save1 = (TextView) findViewById(R.id.save1);
        save2 = (TextView) findViewById(R.id.save2);
        error2 = (TextView) findViewById(R.id.error2);
        error1 = (TextView) findViewById(R.id.error1);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        /*if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
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
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (z == 1) {
                    savevoter = 1;
                    error1.setVisibility(View.GONE);
                    store_voterid_front_details();
                } else {
                    error1.setVisibility(View.VISIBLE);
                    error1.setText("Please upload the voter front side image");
                }

            }
        });
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m == 1) {
                    error2.setVisibility(View.GONE);
                    store_voterid_back_details();
                } else {
                    error2.setVisibility(View.VISIBLE);
                    error2.setText("Please upload the voter back side image");
                }

            }
        });
        save_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation1 == 1 && validation2 == 2) {
                    Intent it = new Intent(Voter_ID_CARD_Details.this, PAN_CARD_Details.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Plese fill up all the data", Toast.LENGTH_LONG).show();
                }


            }
        });
        voteridback = (ImageView) findViewById(R.id.voteridback);
        voteridfront = (ImageView) findViewById(R.id.voteridfront);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Voter_ID_CARD_Details.this, TermsCondition_DEALER_Activity.class);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Voter_ID_CARD_Details.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });
        voteridfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ghf", "hsd");
                showPictureDialog();
                x = 1;

            }
        });
        voteridback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (z == 1 && savevoter == 1) {
                    showPictureDialog();
                    x = 2;
                    error2.setVisibility(View.GONE);

                } else {
                    error2.setVisibility(View.VISIBLE);
                    error2.setText("Please upload voter front image and save it");
                }


            }
        });

        /*   requestMultiplePermissions();*/
        if (!checkPermission()) {
            requestPermission();
        }
        if (!checkPermission_version()) {
            requestPermission();
        }
    }



    private void validation() {
        if ((voteridcard.getText().toString().isEmpty())) {
            voteridcard.setError("Please Enter Your Voter ID Card No.(EPIC No.)");


        } else {

            get_voterid_details();
        }
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
                    Environment.DIRECTORY_DOWNLOADS), "votergopikmoney");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_PATH_VOTERIDCARD, f.getAbsolutePath(), getApplicationContext());
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
                z = 1;
                voteridfront.setImageBitmap(thumbnail);

            } else if (x == 2) {
                m = 1;
                voteridback.setImageBitmap(thumbnail);

            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }


    }

    private void store_voterid_front_details() {

        custPrograssbar.prograssCreate(Voter_ID_CARD_Details.this);
        String mbrand = SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext());
        String mcust_code = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext());
        String mage = SharedPref.getStringFromSharedPref(AppConstants.AGE, getApplicationContext());
        String mdistrict = SharedPref.getStringFromSharedPref(AppConstants.DISTRICT, getApplicationContext());
        String mdob = SharedPref.getStringFromSharedPref(AppConstants.DOB, getApplicationContext());
        String mepicno = SharedPref.getStringFromSharedPref(AppConstants.EPIC_NO, getApplicationContext());
        String mgender = SharedPref.getStringFromSharedPref(AppConstants.GENDER, getApplicationContext());
        String mhouseno = SharedPref.getStringFromSharedPref(AppConstants.HOUSE_NO, getApplicationContext());
        String mnamevoteridcard = SharedPref.getStringFromSharedPref(AppConstants.NAME_VOTERID, getApplicationContext());
        String mnamev1 = SharedPref.getStringFromSharedPref(AppConstants.NAME_V1, getApplicationContext());
        String mrelnname = SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME, getApplicationContext());
        String mrelnnamev1 = SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME_V1, getApplicationContext());
        String mrelntype = SharedPref.getStringFromSharedPref(AppConstants.RLN_TYPE, getApplicationContext());
        String mstate = SharedPref.getStringFromSharedPref(AppConstants.STATE, getApplicationContext());

        RequestBody brand = RequestBody.create(MediaType.parse("multipart/form-data"), mbrand);
        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcust_code);
        RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), mage);
        RequestBody district = RequestBody.create(MediaType.parse("multipart/form-data"), mdistrict);
        RequestBody dob = RequestBody.create(MediaType.parse("multipart/form-data"), mdob);
        RequestBody epicno = RequestBody.create(MediaType.parse("multipart/form-data"), mepicno);
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), mgender);
        RequestBody houseno = RequestBody.create(MediaType.parse("multipart/form-data"), mhouseno);
        RequestBody namevoteridcard = RequestBody.create(MediaType.parse("multipart/form-data"), mnamevoteridcard);
        RequestBody namev1 = RequestBody.create(MediaType.parse("multipart/form-data"), mnamev1);
        RequestBody relnname = RequestBody.create(MediaType.parse("multipart/form-data"), mrelnname);
        RequestBody relnnamev1 = RequestBody.create(MediaType.parse("multipart/form-data"), mrelnnamev1);
        RequestBody relntype = RequestBody.create(MediaType.parse("multipart/form-data"), mrelntype);
        RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), mstate);

     /*   Log.e("testingggg", "testingggg11111" + mbrand);
        Log.e("testingggg", "testingggg22222" + mcust_code);
        Log.e("testingggg", "testingggg33333" + mpanno);
        Log.e("testingggg", "testingggg4444" + mpanname);
        Log.e("testingggg", "testingggg55555" + brand);
        Log.e("testingggg", "testingggg6666" + cust_code);
        Log.e("testingggg", "testingggg77777" + panno);
        Log.e("testingggg", "testingggg88888" + panname);*/

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_PATH_VOTERIDCARD, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("voterid_front", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload);
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<store_voterid_details_MODEL> call = restApis.store_voterid_front_details(brand, cust_code,
                epicno, namevoteridcard, namev1, relnname, relnnamev1, relntype, gender, age, dob, houseno, district, state,
                vechileDocUpload);
        call.enqueue(new Callback<store_voterid_details_MODEL>() {
            @Override
            public void onResponse(Call<store_voterid_details_MODEL> call, Response<store_voterid_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        validation1 = 1;

                        save1.setText("Saved");

                        Toast.makeText(getApplicationContext(), "Successfully uploaded document", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<store_voterid_details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void store_voterid_back_details() {
        custPrograssbar.prograssCreate(Voter_ID_CARD_Details.this);
        String mbrand = SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext());
        String mcust_code = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext());

        RequestBody brand = RequestBody.create(MediaType.parse("multipart/form-data"), mbrand);
        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcust_code);


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_PATH_VOTERIDCARD, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("voterid_back", idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_voterid_back_details_MODEL> call = restApis.store_voterid_back_details(cust_code, vechileDocUpload2, brand);
        call.enqueue(new Callback<Store_voterid_back_details_MODEL>() {
            @Override
            public void onResponse(Call<Store_voterid_back_details_MODEL> call, Response<Store_voterid_back_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        validation2 = 2;
                        save2.setText("Saved");
                        Toast.makeText(getApplicationContext(), "Successfully uploaded document", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();


            }

            @Override
            public void onFailure(Call<Store_voterid_back_details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

/*
    private void store_voterid_details() {
        custPrograssbar.prograssCreate(Voter_ID_CARD_Details.this);

        Store_voterid_details_POJO pojo = new Store_voterid_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AC_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AC_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AGE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DISTRICT, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOB, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.EPIC_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.GENDER, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.HOUSE_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ID, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LAST_UPDATE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_VOTERID, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_V1, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_V2, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_V3, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PART_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PART_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PC_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PS_LAT_LONG, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PS_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME_V1, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME_V2, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RLN_NAME_V3, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RLN_TYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SECTION_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SLNO_INPART, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ST_CODE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.STATE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<store_voterid_details_MODEL> call = restApis.store_voterid_details(pojo);
        call.enqueue(new Callback<store_voterid_details_MODEL>() {
            @Override
            public void onResponse(Call<store_voterid_details_MODEL> call, Response<store_voterid_details_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {


                        Intent it = new Intent(Voter_ID_CARD_Details.this, PAN_CARD_Details.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }


            }

            @Override
            public void onFailure(Call<store_voterid_details_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/


    private void get_voterid_details() {
        custPrograssbar.prograssCreate(Voter_ID_CARD_Details.this);
        Get_voterid_details_POJO pojo = new Get_voterid_details_POJO(voteridcard.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_voterid_details_MODEL> call = restApis.get_voterid_details(pojo);
        call.enqueue(new Callback<Get_voterid_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_voterid_details_MODEL> call, Response<Get_voterid_details_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        save_submit.setVisibility(View.VISIBLE);
                        check.setVisibility(View.GONE);
                        linearlayout.setVisibility(View.VISIBLE);
                        SharedPref.saveStringInSharedPref(AppConstants.AC_NAME, response.body().getPayload().getResult().getAc_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AC_NO, response.body().getPayload().getResult().getAc_no(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AGE, response.body().getPayload().getResult().getAge(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DISTRICT, response.body().getPayload().getResult().getDistrict(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DOB, response.body().getPayload().getResult().getDob(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.EPIC_NO, response.body().getPayload().getResult().getEpic_no(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.GENDER, response.body().getPayload().getResult().getGender(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.HOUSE_NO, response.body().getPayload().getResult().getHouse_no(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ID, response.body().getPayload().getResult().getId(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.LAST_UPDATE, response.body().getPayload().getResult().getLast_update(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_VOTERID, response.body().getPayload().getResult().getName(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_V1, response.body().getPayload().getResult().getName_v1(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_V2, response.body().getPayload().getResult().getName_v2(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_V3, response.body().getPayload().getResult().getName_v3(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PART_NAME, response.body().getPayload().getResult().getPart_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PART_NO, response.body().getPayload().getResult().getPart_no(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PC_NAME, response.body().getPayload().getResult().getPc_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PS_LAT_LONG, response.body().getPayload().getResult().getPs_lat_long(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PS_NAME, response.body().getPayload().getResult().getPs_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RLN_NAME, response.body().getPayload().getResult().getRln_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RLN_NAME_V1, response.body().getPayload().getResult().getRln_name_v1(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RLN_NAME_V2, response.body().getPayload().getResult().getRln_name_v2(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RLN_NAME_V3, response.body().getPayload().getResult().getRln_name_v3(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RLN_TYPE, response.body().getPayload().getResult().getRln_type(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.SECTION_NO, response.body().getPayload().getResult().getSection_no(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.SLNO_INPART, response.body().getPayload().getResult().getSlno_inpart(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ST_CODE, response.body().getPayload().getResult().getSt_code(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.STATE, response.body().getPayload().getResult().getState(), getApplicationContext());
                        name.setText(response.body().getPayload().getResult().getName());
                        relname.setText(response.body().getPayload().getResult().getRln_name());
                        age.setText(response.body().getPayload().getResult().getAge());
                        address.setText(response.body().getPayload().getResult().getDistrict());


                    } else if (response.body().getPayload().getStatus_code().equals("402")) {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(getApplicationContext(), "Insufficient Credits!!!", Toast.LENGTH_LONG).show();
                    } else if (response.body().getPayload().getStatus_code().equals("103")) {
                        custPrograssbar.closePrograssBar();
                        voteridcard.setError("Please enter a valid voter id card no!!!");
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Get_voterid_details_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                voteridcard.setError("Please enter a valid voter id card no!!!");

            }

        });

    }

    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, Manifest.permission.CAMERA);

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
        int read_external_storage_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(Voter_ID_CARD_Details.this, Manifest.permission.CAMERA);

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("jcdbc","hfthth");
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions(Voter_ID_CARD_Details.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }


        }
        return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && camera_permission == PackageManager.PERMISSION_GRANTED;
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
            ActivityCompat.requestPermissions(Voter_ID_CARD_Details.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(mContext, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
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
}


