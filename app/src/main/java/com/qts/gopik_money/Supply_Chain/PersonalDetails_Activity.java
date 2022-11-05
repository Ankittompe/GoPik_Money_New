package com.qts.gopik_money.Supply_Chain;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.DealerAadharFrontDoc_MODEL;
import com.qts.gopik_money.Model.DealerAdharBackDoc_MODEL;
import com.qts.gopik_money.Model.DealerDocDeleteModel;
import com.qts.gopik_money.Model.DealerPanDoc_MODEL;
import com.qts.gopik_money.Model.DealerSelfieDoc_MODEL;
import com.qts.gopik_money.Model.DealerdoctoFinance_MODEL;
import com.qts.gopik_money.Pojo.DealerDocDelete_POJO;
import com.qts.gopik_money.Pojo.DealerdoctoFinance_POJO;
import com.qts.gopik_money.Pojo.ShopkeeperdoctoFinance_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Shopkeeper.HomeShopkeeper;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.TextViewUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetails_Activity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 100;
    String mMultipartFormData = "multipart/form-data";
    String mShopKeeper = "Shop Keeper";
    String mDealer = "Dealer";
    String mSubDealer = "SubDealer";
    String mUpdate = "Update";
    private final int PERMISSION_REQUEST_CODE = 1000;
    ImageView selfie_upld_sucss ;
    ImageView adhaar_upld_back_sucss;
    ImageView upld_pan_front_success;
    ImageView adhaar_upld_sucss;
    Call<DealerDocDeleteModel> mDealerDocDeleteCall;
    LinearLayout upload_selfie_layout ;
    LinearLayout upload_adhaar_front_layout;
    LinearLayout adhaar_back_layout;
    LinearLayout pan_front_layout;
    ImageView upload_selfie;
    ImageView upload_adhaar_front;
    ImageView upload_adhaar_back;
    ImageView upload_pancard_front_button;
    ImageView dropdown1_selfie ;
    ImageView dropup1_selfie;
    ImageView dropdown1_adhar_front;
    ImageView dropup1_adhar_front;
    ImageView dropdown1_adhar_back;
    ImageView dropup1_adhar_back;
    ImageView dropdown_pan_front;
    ImageView dropup2_pan_front;
    ImageView arrow;
    ImageView  hometoolbar;
    private Dialog dialogCondition;
    ImageView CameraButton;
    ImageView  GalleryButton;
    private static final String IMAGE_DIRECTORY = "/supplychaingopikmoneyimg";
    private int GALLERY = 1, CAMERA = 2;
    public int x = 0;
    public int y = 0;
    Bitmap thumbnail, bitmap;
    String networkError = "It seems your Network is unstable . Please Try again!";
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
    AppCompatImageView doc_image;
    String mUserType;
    Call<DealerSelfieDoc_MODEL> mSelfieDocCall;
    Call<DealerAadharFrontDoc_MODEL> mAadharFrontDocCall;
    Call<DealerAdharBackDoc_MODEL> mAadharBackDocCall;
    Call<DealerPanDoc_MODEL> mPanDocCall;
    TextView save_selfie ;
    TextView save_adhar_front;
    TextView save1_adhaar_back;
    TextView save_pan_front;

    TextView upld_adhar_front_hint;
    TextView upld_selfie_hint;
    TextView adhaar_back_hint;
    TextView upld_pan_front_hint;
    private Context mContext = PersonalDetails_Activity.this;
    String image;


    boolean mSelfieStatus = false;
    String usercode, doc_status;
    boolean mAadharStatus = false;
    boolean mAadharBackStatus = false;
    boolean mPANStatus = false;
    TextView uploadselfie_tv ;
    TextView upld_adhaar_front_tv;
    TextView upload_adhaar_back_tv;
    TextView upload_pancard_txt;
    TextView mSelfieShowHideButton;
    TextView mAdhaarFrontShowHideButton;
    TextView mAdhaarBackShowHideButton;
    TextView mPanCardShowHideButton;
    Boolean isSelfieVisible;
    Boolean isAdharFrontVisible;
    Boolean isAdharBackVisible;
    Boolean isPanVisible;
    ImageButton mDeleteSelfieButton;
    ImageButton mDeleteAdharFrontButton;
    ImageButton mDeleteAdharBackButton;
    ImageButton mDeletePanButton;
    TextView mDeleteButton;
    TextView mCanceleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());

        btsend = findViewById(R.id.submit_prsnl_button);

        mDeleteSelfieButton = findViewById(R.id.delete_selfie_button);
        mDeleteAdharFrontButton = findViewById(R.id.delete_adharfront_button);
        mDeleteAdharBackButton = findViewById(R.id.delete_adharback_button);
        mDeletePanButton = findViewById(R.id.delete_pan_button);


        mSelfieShowHideButton = findViewById(R.id.selfie_show_hide_button);
        mAdhaarFrontShowHideButton = findViewById(R.id.adhar_front_show_hide_button);
        mAdhaarBackShowHideButton = findViewById(R.id.adhar_back_show_hide_button);
        mPanCardShowHideButton = findViewById(R.id.pan_show_hide_button);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.prograssCreate(this);
        upld_adhar_front_hint = (TextView) findViewById(R.id.upld_img_hint);
        upld_selfie_hint = findViewById(R.id.upld_selfie_hint);
        adhaar_back_hint = findViewById(R.id.adhaar_back_hint);
        upld_pan_front_hint = findViewById(R.id.upld_pan_front_hint);
        selfie_upld_sucss = findViewById(R.id.selfie_upld_sucss);
        adhaar_upld_back_sucss = findViewById(R.id.adhaar_upld_back_sucss);
        adhaar_upld_sucss = (ImageView) findViewById(R.id.adhaar_upld_sucss);
        upld_pan_front_success = findViewById(R.id.upld_pan_front_success);

        uploadselfie_tv = findViewById(R.id.uploadselfie_tv);
        uploadselfie_tv.append(TextViewUtils.getColoredString("1. Take a Selfie", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.black)));
        uploadselfie_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.red)));

        upld_adhaar_front_tv = findViewById(R.id.upld_adhaar_front_tv);
        upld_adhaar_front_tv.append(TextViewUtils.getColoredString("2. Upload Adhaar Card Front", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.black)));
        upld_adhaar_front_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.red)));

        upload_adhaar_back_tv = findViewById(R.id.upload_adhaar_back_tv);
        upload_adhaar_back_tv.append(TextViewUtils.getColoredString("3. Upload Adhaar Card Back", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.black)));
        upload_adhaar_back_tv.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.red)));

        upload_pancard_txt = findViewById(R.id.upload_pancard_txt);
        upload_pancard_txt.append(TextViewUtils.getColoredString("4. Upload Pan Card", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.black)));
        upload_pancard_txt.append(TextViewUtils.getColoredString(" *", ContextCompat.getColor(PersonalDetails_Activity.this, R.color.red)));
        dialogCondition = new Dialog(PersonalDetails_Activity.this);

        mSelfieShowHideButton.setOnClickListener(v -> {
            if (Boolean.FALSE.equals(isSelfieVisible)){
                upload_selfie_layout.setVisibility(View.VISIBLE);
                dropdown1_selfie.setVisibility(View.GONE);
                dropup1_selfie.setVisibility(View.VISIBLE);
                isSelfieVisible = true;
            } else {
                upload_selfie_layout.setVisibility(View.GONE);
                dropdown1_selfie.setVisibility(View.VISIBLE);
                dropup1_selfie.setVisibility(View.GONE);
                isSelfieVisible = false;
            }
        });
        // custPrograssbar.prograssCreate(this);
        btsend.setOnClickListener(v -> {
            if (upload_selfie_success != 1) {
                Toast.makeText(PersonalDetails_Activity.this, "Please upload a  Selfie Image", Toast.LENGTH_SHORT).show();
            } else if (upload_adhar_front_success != 1) {
                Toast.makeText(PersonalDetails_Activity.this, "Please Upload an Adhaar Front Image ", Toast.LENGTH_SHORT).show();
            } else if (upload_adhar_back_success != 1) {
                Toast.makeText(PersonalDetails_Activity.this, "Please Upload an Adhaar Back Image ", Toast.LENGTH_SHORT).show();
            } else if (upload_pan_success != 1) {
                Toast.makeText(PersonalDetails_Activity.this, "Please Upload a PanCard  Image ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PersonalDetails_Activity.this, "All Document Submitted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PersonalDetails_Activity.this, BusinessDetails_Activity.class);
                startActivity(intent);
            }
        });
        //Back and Home
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        //Upload Button
        upload_selfie = (ImageView) findViewById(R.id.upload_selfie);
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
        upload_selfie_layout = (LinearLayout) findViewById(R.id.upload_selfie_layout);
        upload_adhaar_front_layout = findViewById(R.id.upload_adhaar_front_layout);
        adhaar_back_layout = findViewById(R.id.adhaar_back_layout);
        pan_front_layout = findViewById(R.id.pan_front_layout);

        //Save Button
        save_selfie = (TextView) findViewById(R.id.save_selfie);
        save_adhar_front = (TextView) findViewById(R.id.save_adhar_front);
        save1_adhaar_back = (TextView) findViewById(R.id.save1_adhaar_back);
        save_pan_front = (TextView) findViewById(R.id.save_pan_front);
        mDeleteSelfieButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"Selfie",getApplicationContext());
                    DealerDocDelete();
                    upld_selfie = 0;
                    dialogCondition.dismiss();
                }
            });
            mCanceleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCondition.dismiss();
                }
            });

            dialogCondition.show();

        });
        mDeleteAdharFrontButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v13 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"adhar_front_img",getApplicationContext());
                DealerDocDelete();
                adhar_front_valid1 = 0;
                adhar_front_valid2 = 0;

                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v12 -> dialogCondition.dismiss());

            dialogCondition.show();

        });
        mDeleteAdharBackButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"adhar_back_img",getApplicationContext());
                    DealerDocDelete();
                    adhar_back_valid1 = 0;
                    adhar_back_valid2 = 0;
                    dialogCondition.dismiss();
                }
            });
            mCanceleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCondition.dismiss();
                }
            });

            dialogCondition.show();

        });

        mDeletePanButton.setOnClickListener(v -> {
            dialogCondition = new Dialog(this);
            dialogCondition.setContentView(R.layout.delete_dialog);
            mDeleteButton = (TextView) dialogCondition.findViewById(R.id.delete_button);
            mCanceleButton  = (TextView) dialogCondition.findViewById(R.id.cancel_button);

            dialogCondition.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogCondition.setCancelable(true);
            mDeleteButton.setOnClickListener(v14 -> {
                SharedPref.saveStringInSharedPref(AppConstants.DOC_TYPE,"pan_img",getApplicationContext());
                DealerDocDelete();
                adhar_front_valid1 = 0;
                adhar_front_valid2 = 0;
                dialogCondition.dismiss();
            });
            mCanceleButton.setOnClickListener(v1 -> dialogCondition.dismiss());

            dialogCondition.show();

        });

        arrow.setOnClickListener(v -> {
            if (mUserType.equals(mShopKeeper)) {
                Intent intentshopkeeper = new Intent(PersonalDetails_Activity.this, HomeShopkeeper.class);
                intentshopkeeper.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                startActivity(intentshopkeeper);

            } else if (mUserType.equals(mDealer)) {
                Intent intentdealer = new Intent(PersonalDetails_Activity.this, MainActivity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(intentdealer);
            }

        });
        hometoolbar.setOnClickListener(v -> {
            if (mUserType.equals(mShopKeeper)) {
                Intent intentshopkeeper = new Intent(PersonalDetails_Activity.this, HomeShopkeeper.class);
                intentshopkeeper.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);
                startActivity(intentshopkeeper);

            } else if (mUserType.equals(mDealer)) {
                Intent intentdealer = new Intent(PersonalDetails_Activity.this, MainActivity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intentdealer);
            } else {
                Intent intentdealer = new Intent(PersonalDetails_Activity.this, MainActivity.class);
                intentdealer.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(intentdealer);
            }


        });

        ///Selfie Dropdown/DropUp
        dropdown1_selfie.setOnClickListener(v -> {
            dropup1_selfie.setVisibility(View.VISIBLE);
            dropdown1_selfie.setVisibility(View.GONE);
            upload_selfie_layout.setVisibility(View.VISIBLE);
        });

        dropup1_selfie.setOnClickListener(v -> {
            dropdown1_selfie.setVisibility(View.VISIBLE);
            dropup1_selfie.setVisibility(View.GONE);
            upload_selfie_layout.setVisibility(View.GONE);

        });

        ////Adhaar Front Dropdown/Dropup
        dropdown1_adhar_front.setOnClickListener(v -> {
            dropdown1_adhar_front.setVisibility(View.GONE);
            dropup1_adhar_front.setVisibility(View.VISIBLE);
            upload_adhaar_front_layout.setVisibility(View.VISIBLE);
        });
        dropup1_adhar_front.setOnClickListener(v -> {
            dropup1_adhar_front.setVisibility(View.GONE);
            dropdown1_adhar_front.setVisibility(View.VISIBLE);
            upload_adhaar_front_layout.setVisibility(View.GONE);

        });

        ////Adhaar Back Dropdown/Dropup
        dropdown1_adhar_back.setOnClickListener(v -> {
            dropdown1_adhar_back.setVisibility(View.GONE);
            dropup1_adhar_back.setVisibility(View.VISIBLE);
            adhaar_back_layout.setVisibility(View.VISIBLE);

        });
        dropup1_adhar_back.setOnClickListener(v -> {
            dropup1_adhar_back.setVisibility(View.GONE);
            dropdown1_adhar_back.setVisibility(View.VISIBLE);
            adhaar_back_layout.setVisibility(View.GONE);

        });

        ////PAN Dropdown/Dropup
        dropdown_pan_front.setOnClickListener(v -> {

            dropdown_pan_front.setVisibility(View.GONE);
            dropup2_pan_front.setVisibility(View.VISIBLE);
            pan_front_layout.setVisibility(View.VISIBLE);

        });
        dropup2_pan_front.setOnClickListener(v -> {
            dropup2_pan_front.setVisibility(View.GONE);
            dropdown_pan_front.setVisibility(View.VISIBLE);
            pan_front_layout.setVisibility(View.GONE);
        });
        //Upload Selfie
        upload_selfie.setOnClickListener(v -> {
            SelectImageDailog();
            y = 4;
            x = 4;
        });
        upload_adhaar_front_layout.setOnClickListener(v -> {
            SelectImageDailog();
            x = 1;
            y = 1;
        });
        upload_adhaar_back.setOnClickListener(v -> {
            SelectImageDailog();
            x = 2;
            y = 2;
        });
        upload_pancard_front_button.setOnClickListener(v -> {
            SelectImageDailog();
            x = 3;
            y = 3;

        });
        //Save Document

        save_selfie.setOnClickListener(v -> {
            if (upld_selfie != 1) {
                upld_selfie_hint.setVisibility(View.VISIBLE);
            } else {
                // Glide.with(upload_selfie).clear(upload_selfie);
                //upload_selfie.setImageDrawable(null);
                dropdown1_adhar_front.setVisibility(View.GONE);
                dropup1_adhar_front.setVisibility(View.VISIBLE);
                upload_adhaar_front_layout.setVisibility(View.VISIBLE);
                custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                mSelfieStatus = true;
                DealerSelfieDoc(mSelfieStatus);
            }

        });
        save_adhar_front.setOnClickListener(v -> {
            if (adhar_front_valid1 != 1 && adhar_front_valid2 != 1) {
                upld_adhar_front_hint.setVisibility(View.VISIBLE);

            } else {
                custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                DealerAadharFrontDoc(true);
            }

        });
        save1_adhaar_back.setOnClickListener(v -> {
            if (adhar_back_valid1 != 2 && adhar_back_valid2 != 2) {
                adhaar_back_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                DealerAadharBackDoc(true);
            }

        });

        save_pan_front.setOnClickListener(v -> {
            if (Pan_valid != 1) {
                upld_pan_front_hint.setVisibility(View.VISIBLE);
            } else {
                custPrograssbar.prograssCreate(PersonalDetails_Activity.this);
                DealerPanDoc(true);
            }

        });
        /*if (!checkPermission()) {
            requestPermission();
        }
        if (!checkPermission_version()) {
            requestPermission();
        }*/

        DealerSelfieDoc(mSelfieStatus);
        DealerAadharFrontDoc(mAadharStatus);
        DealerAadharBackDoc(mAadharBackStatus);
        DealerPanDoc(mPANStatus);
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
    }

    private void DealerDocDelete() {


        custPrograssbar.prograssCreate(this);
        DealerDocDelete_POJO pojo = new DealerDocDelete_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);


        if (mUserType.equals("Dealer")) {
            Log.e("DocType","-->>"+SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext()));

            mDealerDocDeleteCall = restApis.DealerDocDelete(pojo);

            mDealerDocDeleteCall.enqueue(new Callback<DealerDocDeleteModel>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<DealerDocDeleteModel> call, Response<DealerDocDeleteModel> response) {
                    if (response.body() != null&&response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();

                        Log.e("DocType","-->>"+SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext()));
                        response.body().getMessage();
                       response.body().getPayload().getType();
                       response.body().getPayload().getUsercode();
                       switch (SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())){
                           case "Selfie":
                               upload_selfie.setImageResource(R.drawable.camera);
                               upload_selfie.getLayoutParams().height = 150;
                               upload_selfie.getLayoutParams().width = 150;
                               upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                               mDeleteSelfieButton.setVisibility(View.GONE);
                               selfie_upld_sucss.setVisibility(View.GONE);
                               upload_selfie_success = 0;
                               Toast.makeText(mContext, SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                               break;
                       //    adhar_front_img, adhar_back_img, pan_img, BUSINESS_PROOF, UDC , AGREEMENT , ESCROW , DISB_BANK , INVOICE , ITR , TDS , GST
                           case "adhar_front_img":
                               upload_adhaar_front.setImageResource(R.drawable.camera);
                               upload_adhaar_front.getLayoutParams().height = 150;
                               upload_adhaar_front.getLayoutParams().width = 150;
                               upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                               mDeleteAdharFrontButton.setVisibility(View.GONE);
                               adhaar_upld_sucss.setVisibility(View.GONE);
                               upload_adhar_front_success = 0;
                               Toast.makeText(mContext, SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                               break;
                           case "adhar_back_img":
                               upload_adhaar_back.setImageResource(R.drawable.camera);
                               upload_adhaar_back.getLayoutParams().height = 150;
                               upload_adhaar_back.getLayoutParams().width = 150;
                               upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                               mDeleteAdharBackButton.setVisibility(View.GONE);
                               adhaar_upld_back_sucss.setVisibility(View.GONE);
                               upload_adhar_back_success = 0;
                               Toast.makeText(mContext, SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                               break;
                           default:
                               upload_pancard_front_button.setImageResource(R.drawable.camera);
                               upload_pancard_front_button.getLayoutParams().height = 150;
                               upload_pancard_front_button.getLayoutParams().width = 150;
                               upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                               mDeletePanButton.setVisibility(View.GONE);
                               upld_pan_front_success.setVisibility(View.GONE);
                               upload_pan_success = 0;
                               Toast.makeText(mContext, SharedPref.getStringFromSharedPref(AppConstants.DOC_TYPE,getApplicationContext())+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                               break;


                       }

                    }else {

                        Toast.makeText(PersonalDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<DealerDocDeleteModel> call, Throwable t) {

                    Toast.makeText(PersonalDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                }

            });
        }}

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(PersonalDetails_Activity.this, new String[]{permission}, requestCode);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalDetails_Activity.this);
                builder.setMessage("Please allow camera permission");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    startActivity(intent);
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
//        } else if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.e("Permission ","Storage Permission Granted");
//            } else {
//                Log.e("Permission ","Storage Permission Denied");
//                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
//            }
//        }
    }
    private void DealerdoctoFinance() {

        custPrograssbar.prograssCreate(this);
        DealerdoctoFinance_POJO pojo = new DealerdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<DealerdoctoFinance_MODEL> call = restApis.DealerdoctoFinance(pojo);
        call.enqueue(new Callback<DealerdoctoFinance_MODEL>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DealerdoctoFinance_MODEL> call, Response<DealerdoctoFinance_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {
                        usercode = response.body().getPayload().getUsercode();
                        doc_status = response.body().getPayload().getDoc_status();

                        Toast.makeText(PersonalDetails_Activity.this, "Ducument sent to Financer", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PersonalDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                } else {
                    custPrograssbar.closePrograssBar();
                }


            }


            @Override
            public void onFailure(Call<DealerdoctoFinance_MODEL> call, Throwable t) {


                Toast.makeText(PersonalDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }


    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, Manifest.permission.CAMERA);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager() && camera_permission == PackageManager.PERMISSION_GRANTED;
        } else {
            return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && camera_permission == PackageManager.PERMISSION_GRANTED;

        }


    }

    private boolean checkPermission_version() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(PersonalDetails_Activity.this, Manifest.permission.CAMERA);

        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {

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
        CameraButton.setOnClickListener(view -> {
            takePhotoFromCamera();
            dialogCondition.dismiss();


        });
        GalleryButton.setOnClickListener(view -> {
            choosePhotoFromGallary();
            dialogCondition.dismiss();
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
        if (requestCode == GALLERY && data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap =
                            MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                    contentURI);
                    if (x == 1) {
                        upload_adhaar_front.setImageBitmap(bitmap);
                        adhar_front_valid1 = 1;
                        upload_adhaar_front.getLayoutParams().height = 300;
                        upload_adhaar_front.getLayoutParams().width = 300;
                        upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                        upld_adhar_front_hint.setVisibility(View.GONE);
                        mDeleteAdharFrontButton.setVisibility(View.VISIBLE);
                    } else if (x == 2) {
                        upload_adhaar_back.setImageBitmap(bitmap);
                        adhaar_back_hint.setVisibility(View.GONE);
                        upload_adhaar_back.getLayoutParams().height = 300;
                        upload_adhaar_back.getLayoutParams().width = 300;
                        upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                        mDeleteAdharBackButton.setVisibility(View.VISIBLE);
                        adhar_back_valid1 = 2;

                    } else if (x == 3) {
                        upload_pancard_front_button.setImageBitmap(bitmap);
                        upld_pan_front_hint.setVisibility(View.GONE);
                        upload_pancard_front_button.getLayoutParams().height = 300;
                        upload_pancard_front_button.getLayoutParams().width = 300;
                        upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                        mDeletePanButton.setVisibility(View.VISIBLE);
                        Pan_valid = 1;
                    } else if (x == 4) {

                        upld_selfie_hint.setVisibility(View.GONE);
                        upload_selfie.getLayoutParams().height = 300;
                        upload_selfie.getLayoutParams().width = 300;
                        upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                        upld_selfie = 1;
                        upload_selfie.setImageBitmap(bitmap);
                        mDeleteSelfieButton.setVisibility(View.VISIBLE);
                    }
                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }



        } else if (requestCode == CAMERA) {
            thumbnail = (Bitmap) data.getExtras().get("data");


            if (y == 1) {

                upload_adhaar_front.setImageBitmap(thumbnail);
                adhar_front_valid2 = 1;
                upld_adhar_front_hint.setVisibility(View.GONE);
                upload_adhaar_front.getLayoutParams().height = 300;
                upload_adhaar_front.getLayoutParams().width = 300;
                upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                mDeleteAdharFrontButton.setVisibility(View.VISIBLE);
            } else if (y == 2) {
                upload_adhaar_back.setImageBitmap(thumbnail);
                adhaar_back_hint.setVisibility(View.GONE);
                adhar_back_valid2 = 2;
                upload_adhaar_back.getLayoutParams().height = 300;
                upload_adhaar_back.getLayoutParams().width = 300;
                upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                mDeleteAdharBackButton.setVisibility(View.VISIBLE);

            } else if (y == 3) {
                upload_pancard_front_button.setImageBitmap(thumbnail);
                upld_pan_front_hint.setVisibility(View.GONE);
                Pan_valid = 1;
                upload_pancard_front_button.getLayoutParams().height = 300;
                upload_pancard_front_button.getLayoutParams().width = 300;
                upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                mDeletePanButton.setVisibility(View.VISIBLE);

            } else if (y == 4) {
                upload_selfie.setImageBitmap(thumbnail);
                upload_selfie.getLayoutParams().height = 300;
                upload_selfie.getLayoutParams().width = 300;
                upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                upld_selfie_hint.setVisibility(View.GONE);
                upld_selfie = 1;
                mDeleteSelfieButton.setVisibility(View.VISIBLE);
            }

            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
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


            SharedPref.saveStringInSharedPref(AppConstants.ML_LOAN_IMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void DealerSelfieDoc(boolean mSelfieStatus) {
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("selfie", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mSubDealer)) {
            if (mSelfieStatus) {
                mSelfieDocCall = restApis.DealerSubSelfieDoc(user_code, vechileDocUpload2);
            } else {
                mSelfieDocCall = restApis.GetSubDealerSelfieDoc(data);
            }
            mSelfieDocCall.enqueue(new Callback<DealerSelfieDoc_MODEL>() {

                @Override
                public void onResponse(Call<DealerSelfieDoc_MODEL> call, Response<DealerSelfieDoc_MODEL> response) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {

                            custPrograssbar.closePrograssBar();
                            upload_selfie_success = 1;

                            image = response.body().getPayload().getSelfie();
                            if ((response.body().getPayload().getSelfie()).equals("NA")) {
                                selfie_upld_sucss.setVisibility(View.GONE);
                                mDeleteSelfieButton.setVisibility(View.GONE);
                            } else {
                                selfie_upld_sucss.setVisibility(View.VISIBLE);
                                mDeleteSelfieButton.setVisibility(View.VISIBLE);
                            }
                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upload_selfie.setImageBitmap(mIcon_val);
                                upload_selfie.getLayoutParams().height = 300;
                                upload_selfie.getLayoutParams().width = 300;
                                upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteSelfieButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(PersonalDetails_Activity.this, "Please upload image", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DealerSelfieDoc_MODEL> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }

            });
        } else if (mUserType.equals(mDealer)) {
            if (mSelfieStatus) {
                mSelfieDocCall = restApis.DealerSelfieDoc(user_code, vechileDocUpload2);
            } else {
                mSelfieDocCall = restApis.GetDealerSelfieDoc(data);
            }
            mSelfieDocCall.enqueue(new Callback<DealerSelfieDoc_MODEL>() {

                @Override
                public void onResponse(Call<DealerSelfieDoc_MODEL> call, Response<DealerSelfieDoc_MODEL> response) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            mDeleteSelfieButton.setVisibility(View.VISIBLE);
                            upload_selfie_layout.setVisibility(View.GONE);
                            dropdown1_selfie.setVisibility(View.VISIBLE);
                            dropup1_selfie.setVisibility(View.GONE);
                            image = response.body().getPayload().getSelfie();
                           // selfie_upld_sucss.setVisibility(View.VISIBLE);
                            custPrograssbar.closePrograssBar();
                            upload_selfie_success = 1;
                            if ((response.body().getPayload().getSelfie()).equals("NA")) {
                                selfie_upld_sucss.setVisibility(View.GONE);
                                mDeleteSelfieButton.setVisibility(View.GONE);
                            } else {
                                selfie_upld_sucss.setVisibility(View.VISIBLE);
                                mDeleteSelfieButton.setVisibility(View.VISIBLE);
                            }
                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upload_selfie.setImageBitmap(mIcon_val);
                                upload_selfie.getLayoutParams().height = 300;
                                upload_selfie.getLayoutParams().width = 300;
                                upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                                mDeleteSelfieButton.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(PersonalDetails_Activity.this, "Please upload image", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DealerSelfieDoc_MODEL> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mSelfieStatus) {
                mSelfieDocCall = restApis.ShopkeeperSelfieDoc(user_code, vechileDocUpload2);
            } else {
                mSelfieDocCall = restApis.GetShopkeeperSelfieDocImage(data);
            }
            mSelfieDocCall.enqueue(new Callback<DealerSelfieDoc_MODEL>() {

                @Override
                public void onResponse(Call<DealerSelfieDoc_MODEL> call, Response<DealerSelfieDoc_MODEL> response) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            image = response.body().getPayload().getSelfie();
                          //  selfie_upld_sucss.setVisibility(View.VISIBLE);
                            custPrograssbar.closePrograssBar();
                            upload_selfie_success = 1;
                            if ((response.body().getPayload().getSelfie()).equals("NA")) {
                                selfie_upld_sucss.setVisibility(View.GONE);
                            } else {
                                selfie_upld_sucss.setVisibility(View.VISIBLE);
                            }

                            try {
                                URL newurl = new URL(image);
                                Bitmap mIcon_val;
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                upload_selfie.setImageBitmap(mIcon_val);
                                upload_selfie.getLayoutParams().height = 300;
                                upload_selfie.getLayoutParams().width = 300;
                                upload_selfie.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(PersonalDetails_Activity.this, "Please upload image", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DealerSelfieDoc_MODEL> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }

            });
        }
    }

    private void DealerAadharFrontDoc(boolean mAadharStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mSubDealer)) {
            if (mAadharStatus) {
                mAadharFrontDocCall = restApis.SubDealerAadharFrontDoc(user_code, vechileDocUpload2);
            } else {
                mAadharFrontDocCall = restApis.GetSubDealerAadharFrontDoc(data);
            }
            mAadharFrontDocCall.enqueue(new Callback<DealerAadharFrontDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAadharFrontDoc_MODEL> call, Response<DealerAadharFrontDoc_MODEL> response) {

                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        upload_adhaar_front_layout.setVisibility(View.GONE);
                     //   adhaar_upld_sucss.setVisibility(View.VISIBLE);
                        dropdown1_adhar_front.setVisibility(View.VISIBLE);
                        dropup1_adhar_front.setVisibility(View.GONE);
                        save_adhar_front.setText(mUpdate);
                        upload_adhar_front_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_sucss.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_sucss.setVisibility(View.VISIBLE);
                        }
                        dropdown1_adhar_back.setVisibility(View.GONE);
                        dropup1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_front.setImageBitmap(mIcon_val);
                            upload_adhaar_front.getLayoutParams().height = 300;
                            upload_adhaar_front.getLayoutParams().width = 300;
                            upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                }

                @Override
                public void onFailure(Call<DealerAadharFrontDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }
            });
        } else if (mUserType.equals(mDealer)) {
            if (mAadharStatus) {
                mAadharFrontDocCall = restApis.DealerAadharFrontDoc(user_code, vechileDocUpload2);
            } else {
                mAadharFrontDocCall = restApis.GetDealerAadharFrontDoc(data);
            }
            mAadharFrontDocCall.enqueue(new Callback<DealerAadharFrontDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAadharFrontDoc_MODEL> call, Response<DealerAadharFrontDoc_MODEL> response) {

                    if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();

                        upload_adhaar_front_layout.setVisibility(View.GONE);
                      //  adhaar_upld_sucss.setVisibility(View.VISIBLE);
                        dropdown1_adhar_front.setVisibility(View.VISIBLE);
                        dropup1_adhar_front.setVisibility(View.GONE);
                        save_adhar_front.setText(mUpdate);
                        upload_adhar_front_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_sucss.setVisibility(View.GONE);
                            mDeleteAdharFrontButton.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_sucss.setVisibility(View.VISIBLE);
                            mDeleteAdharFrontButton.setVisibility(View.VISIBLE);
                        }
                        dropdown1_adhar_back.setVisibility(View.GONE);
                        dropup1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_front.setImageBitmap(mIcon_val);
                            upload_adhaar_front.getLayoutParams().height = 300;
                            upload_adhaar_front.getLayoutParams().width = 300;
                            upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                            mDeleteAdharFrontButton.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DealerAadharFrontDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }
            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mAadharStatus) {
                mAadharFrontDocCall = restApis.ShopkeeperAadharFrontDoc(user_code, vechileDocUpload2);
            } else {
                mAadharFrontDocCall = restApis.GetShopkeeperAadharFrontDoc(data);
            }
            mAadharFrontDocCall.enqueue(new Callback<DealerAadharFrontDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAadharFrontDoc_MODEL> call, Response<DealerAadharFrontDoc_MODEL> response) {

                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();

                        upload_adhaar_front_layout.setVisibility(View.GONE);
                      //  adhaar_upld_sucss.setVisibility(View.VISIBLE);
                        dropdown1_adhar_front.setVisibility(View.VISIBLE);
                        dropup1_adhar_front.setVisibility(View.GONE);
                        save_adhar_front.setText(mUpdate);
                        upload_adhar_front_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_sucss.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_sucss.setVisibility(View.VISIBLE);
                        }
                        dropdown1_adhar_back.setVisibility(View.GONE);
                        dropup1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_front.setImageBitmap(mIcon_val);
                            upload_adhaar_front.getLayoutParams().height = 300;
                            upload_adhaar_front.getLayoutParams().width = 300;
                            upload_adhaar_front.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(Call<DealerAadharFrontDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void DealerAadharBackDoc(boolean mAadharBackStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("adharimage", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);

        if (mUserType.equals(mSubDealer)) {
            if (mAadharBackStatus) {
                mAadharBackDocCall = restApis.SubDealerAdharBackDoc(user_code, vechileDocUpload2);
            } else {
                mAadharBackDocCall = restApis.GetSubDealerAdharBackDoc(data);
            }
            mAadharBackDocCall.enqueue(new Callback<DealerAdharBackDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAdharBackDoc_MODEL> call, Response<DealerAdharBackDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup1_adhar_back.setVisibility(View.GONE);
                        dropdown1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.GONE);
                     //   adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                        save1_adhaar_back.setText(mUpdate);
                        upload_adhar_back_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_back_sucss.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                        }
                        dropdown_pan_front.setVisibility(View.GONE);
                        dropup2_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_back.setImageBitmap(mIcon_val);
                            upload_adhaar_back.getLayoutParams().height = 300;
                            upload_adhaar_back.getLayoutParams().width = 300;
                            upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAdharBackDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });

        } else if (mUserType.equals(mDealer)) {
            if (mAadharBackStatus) {
                mAadharBackDocCall = restApis.DealerAdharBackDoc(user_code, vechileDocUpload2);
            } else {
                mAadharBackDocCall = restApis.GetDealerAdharBackDoc(data);
            }
            mAadharBackDocCall.enqueue(new Callback<DealerAdharBackDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAdharBackDoc_MODEL> call, Response<DealerAdharBackDoc_MODEL> response) {
                    if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        dropup1_adhar_back.setVisibility(View.GONE);
                        dropdown1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.GONE);
                    //    adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                        save1_adhaar_back.setText(mUpdate);
                        upload_adhar_back_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_back_sucss.setVisibility(View.GONE);
                            mDeleteAdharBackButton.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                            mDeleteAdharBackButton.setVisibility(View.VISIBLE);
                        }
                        dropdown_pan_front.setVisibility(View.GONE);
                        dropup2_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_back.setImageBitmap(mIcon_val);
                            upload_adhaar_back.getLayoutParams().height = 300;
                            upload_adhaar_back.getLayoutParams().width = 300;
                            upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                            mDeleteAdharBackButton.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAdharBackDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mAadharBackStatus) {
                mAadharBackDocCall = restApis.ShopkeeperAdharBackDoc(user_code, vechileDocUpload2);
            } else {
                mAadharBackDocCall = restApis.GetShopkeeperAdharBackDoc(data);
            }
            mAadharBackDocCall.enqueue(new Callback<DealerAdharBackDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerAdharBackDoc_MODEL> call, Response<DealerAdharBackDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup1_adhar_back.setVisibility(View.GONE);
                        dropdown1_adhar_back.setVisibility(View.VISIBLE);
                        adhaar_back_layout.setVisibility(View.GONE);
                     //   adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                        save1_adhaar_back.setText(mUpdate);
                        upload_adhar_back_success = 1;
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            adhaar_upld_back_sucss.setVisibility(View.GONE);
                        } else {
                            adhaar_upld_back_sucss.setVisibility(View.VISIBLE);
                        }
                        dropdown_pan_front.setVisibility(View.GONE);
                        dropup2_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.VISIBLE);
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_adhaar_back.setImageBitmap(mIcon_val);
                            upload_adhaar_back.getLayoutParams().height = 300;
                            upload_adhaar_back.getLayoutParams().width = 300;
                            upload_adhaar_back.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerAdharBackDoc_MODEL> call, Throwable t) {


                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void DealerPanDoc(boolean mPANStatus) {
        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        ShopkeeperdoctoFinance_POJO data = new ShopkeeperdoctoFinance_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.ML_LOAN_IMAGE, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("panimage", idFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        if (mUserType.equals(mSubDealer)) {
            if (mPANStatus) {
                mPanDocCall = restApis.SubDealerPanDoc(user_code, vechileDocUpload2);
            } else {
                mPanDocCall = restApis.GetSubDealerPanDoc(data);
            }
            mPanDocCall.enqueue(new Callback<DealerPanDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerPanDoc_MODEL> call, Response<DealerPanDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup2_pan_front.setVisibility(View.GONE);
                        dropdown_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.GONE);
                   //     upld_pan_front_success.setVisibility(View.VISIBLE);
                        save_pan_front.setText(mUpdate);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_pan_front_success.setVisibility(View.GONE);
                        } else {
                            upld_pan_front_success.setVisibility(View.VISIBLE);
                        }
                        upload_pan_success = 1;
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_pancard_front_button.setImageBitmap(mIcon_val);
                            upload_pancard_front_button.getLayoutParams().height = 300;
                            upload_pancard_front_button.getLayoutParams().width = 300;
                            upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerPanDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });

        } else if (mUserType.equals(mDealer)) {
            if (mPANStatus) {
                mPanDocCall = restApis.DealerPanDoc(user_code, vechileDocUpload2);
            } else {
                mPanDocCall = restApis.GetDealerPanDoc(data);
            }
            mPanDocCall.enqueue(new Callback<DealerPanDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerPanDoc_MODEL> call, Response<DealerPanDoc_MODEL> response) {
                    if (response.body() != null&&response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        dropup2_pan_front.setVisibility(View.GONE);
                        dropdown_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.GONE);
                     //   upld_pan_front_success.setVisibility(View.VISIBLE);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_pan_front_success.setVisibility(View.GONE);
                            mDeletePanButton.setVisibility(View.GONE);
                        } else {
                            upld_pan_front_success.setVisibility(View.VISIBLE);
                            mDeletePanButton.setVisibility(View.VISIBLE);
                        }
                        save_pan_front.setText(mUpdate);
                        upload_pan_success = 1;
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_pancard_front_button.setImageBitmap(mIcon_val);
                            upload_pancard_front_button.getLayoutParams().height = 300;
                            upload_pancard_front_button.getLayoutParams().width = 300;
                            pan_front_layout.setVisibility(View.GONE);
                            upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                            mDeletePanButton.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerPanDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });
        } else if (mUserType.equals(mShopKeeper)) {
            if (mPANStatus) {
                mPanDocCall = restApis.ShopkeeperPanDoc(user_code, vechileDocUpload2);
            } else {
                mPanDocCall = restApis.GetShopkeeperPanDoc(data);
            }
            mPanDocCall.enqueue(new Callback<DealerPanDoc_MODEL>() {
                @Override
                public void onResponse(Call<DealerPanDoc_MODEL> call, Response<DealerPanDoc_MODEL> response) {
                    if (response.body() != null) {
                        custPrograssbar.closePrograssBar();
                        dropup2_pan_front.setVisibility(View.GONE);
                        dropdown_pan_front.setVisibility(View.VISIBLE);
                        pan_front_layout.setVisibility(View.GONE);
                    //    upld_pan_front_success.setVisibility(View.VISIBLE);
                        if ((response.body().getPayload().getSelfie()).equals("NA")) {
                            upld_pan_front_success.setVisibility(View.GONE);
                        } else {
                            upld_pan_front_success.setVisibility(View.VISIBLE);
                        }
                        save_pan_front.setText(mUpdate);
                        upload_pan_success = 1;
                        Toast.makeText(PersonalDetails_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        image = response.body().getPayload().getSelfie();

                        try {
                            URL newurl = new URL(image);
                            Bitmap mIcon_val;
                            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            upload_pancard_front_button.setImageBitmap(mIcon_val);
                            upload_pancard_front_button.getLayoutParams().height = 300;
                            upload_pancard_front_button.getLayoutParams().width = 300;
                            upload_pancard_front_button.setScaleType(ImageView.ScaleType.FIT_XY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DealerPanDoc_MODEL> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}