package com.qts.gopik_money.Dealer_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Apiget_panid_details_MODEL;
import com.qts.gopik_money.Model.Store_panid_details_MODEL;
import com.qts.gopik_money.Pojo.Apiget_panid_details_POJO;
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



public class PAN_CARD_Details extends AppCompatActivity {
    EditText voteridcard;
    FileOutputStream fo;
    ImageView hometoolbar;
    ImageView backarrow;
    ImageView pancard;
    TextView check;
    TextView name;
    TextView save_submit;
    String mMultipartFormData = "multipart/form-data";
    LinearLayout linearlayout;
    CustPrograssbar custPrograssbar;
    public int x = 0;
    public int y = 0;
    public int z = 0;
    TextView  error1;
    private static final String IMAGE_DIRECTORY = "/gopikmoneypan";
    private int  CAMERA = 2;

    String networkError = "It seems your Network is unstable . Please Try again!";
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
        check.setOnClickListener(view -> validation());
        save_submit.setOnClickListener(view -> {
            if(y==1){
                store_panid_details();
                error1.setVisibility(View.GONE);
            }
            else{
                error1.setVisibility(View.VISIBLE);
                error1.setText("Please upload pan image");
            }

        });
        pancard = (ImageView) findViewById(R.id.pancard);
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(PAN_CARD_Details.this, Voter_ID_CARD_Details.class);
            startActivity(it);

        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(PAN_CARD_Details.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });
        pancard.setOnClickListener(v -> {
            showPictureDialog();

            x = 1;
        });

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
        RequestBody brand = RequestBody.create(MediaType.parse(mMultipartFormData), mbrand);
        RequestBody cust_code = RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);
        RequestBody panno = RequestBody.create(MediaType.parse(mMultipartFormData), mpanno);
        RequestBody panname = RequestBody.create(MediaType.parse(mMultipartFormData), mpanname);

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_PATH, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("panid", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Call<Store_panid_details_MODEL> call = restApis.store_panid_details(brand, cust_code, panno, panname, vechileDocUpload);
        call.enqueue(new Callback<Store_panid_details_MODEL>() {
            @Override
            public void onResponse(Call<Store_panid_details_MODEL> call, Response<Store_panid_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {
                        z = 1;
                        Toast.makeText(getApplicationContext(), "Successfully uploaded document", Toast.LENGTH_LONG).show();
                        Intent it = new Intent(PAN_CARD_Details.this, ImageCapture.class);
                        startActivity(it);

                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<Store_panid_details_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        save_submit.setVisibility(View.VISIBLE);
                        check.setVisibility(View.GONE);
                        linearlayout.setVisibility(View.VISIBLE);
                        name.setText(response.body().getPayload().getResult().getName());
                        SharedPref.saveStringInSharedPref(AppConstants.PAN_NAME, response.body().getPayload().getResult().getName(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.PAN_NO, voteridcard.getText().toString(), getApplicationContext());


                    } else {
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "pangopikmoney");
            f.createNewFile();
            fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_PATH, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
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

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            if (x == 1) {
                y = 1;
                pancard.setImageBitmap(thumbnail);
            }
            saveImage(thumbnail);
        }


    }


}

