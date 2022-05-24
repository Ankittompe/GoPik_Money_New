package com.qts.gopik_loan.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.qts.gopik_loan.Model.Broker_profile_details_MODEL;
import com.qts.gopik_loan.Model.DL_MODEL;
import com.qts.gopik_loan.Model.MLAddproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLBnkproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLDoc1_upload_MODEL;
import com.qts.gopik_loan.Model.MLDoc3_upload_MODEL;
import com.qts.gopik_loan.Model.MLEmpproof_upload_MODEL;
import com.qts.gopik_loan.Model.MLIDproofBackUpload_MODEL;
import com.qts.gopik_loan.Model.MLIDproofFrontUpload_MODEL;
import com.qts.gopik_loan.Model.MLIDproof_upload_MODEL;
import com.qts.gopik_loan.Model.PAN_MODEL;
import com.qts.gopik_loan.Model.PASSPORT_MODEL;
import com.qts.gopik_loan.Model.VOTER_MODEL;
import com.qts.gopik_loan.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_loan.Pojo.DL_POJO;
import com.qts.gopik_loan.Pojo.MLDoc1_upload_POJO;
import com.qts.gopik_loan.Pojo.MLDoc3_upload_POJO;
import com.qts.gopik_loan.Pojo.MLIDproof_upload_POJO;
import com.qts.gopik_loan.Pojo.PAN_POJO;
import com.qts.gopik_loan.Pojo.PASSPORT_POJO;
import com.qts.gopik_loan.Pojo.VOTER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class KYC_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Integer validation1 = 0;
    Integer validation2 = 0;
    Integer validation3 = 0;
    Integer validation4 = 0;
    //voter identity validation
    public int validvoterfront = 0;
    public int validvoterback = 0;
    //passport identity validation
    public int validpassportfront = 0;
    public int validpassportback = 0;
    public int validpan = 0;
    public int validdl = 0;
    public int validbankstatement=0;


    public int save_button_voter_id = 0;
    public int save_button_passport_id = 0;

    public int x = 0;
    public int y = 0;
    private static final String IMAGE_DIRECTORY = "/gopikmoney";
    private int GALLERY = 1, CAMERA = 2;
    private static final int RESULT_OK = 123;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    ImageView hometoolbar, backarrow;
    CustPrograssbar custPrograssbar;
    ImageView dropdown1, dropup1, dropdown2, dropup2, dropup3, dropdown3, dropup4, dropdown4;
    ImageView correct1, correct2, correct3, correct4;

    //error textview for identity
    TextView error1, error2, error3, error4,error5,error6,error7;

    LinearLayout identitylayout, addresslayout, incomelayout, banklayout;
    LinearLayout voteridcardlayout, panlayout, dllayout, passportlayout;
    LinearLayout voter_address_layout, aadhar_address_layout, dl_address_layout, passport_address_layout;
    LinearLayout salaried_income_layout, selfemployeed_income_layout;
    String[] proofofidentity = {"Select Proof Of Identity", "PAN Card", "Voter ID Card", "Driving License", "Passport"};
    String[] proofofaddress = {"Select Proof Of Address", "Voter ID Card", "Aadhar Card", "Driving License", "Passport"};
    String[] proofofincome = {"Select Proof Of Income", "Salaried", "Self Employeed"};

    Spinner choose_identity, choose_address, choose_income;
    TextView check1, check2, check3, check4;
    TextView save_submit1, save_submit2, save_submit3, save_submit4;
    TextView save1, save2, save3, save4, save1_voter_back_address, save2_aadhar_back_address, save4_passport_back_address;
    TextView savestatement;
    TextView finalsave;
    TextView savedata1, savedata2;
    CardView edittext;
    TextView alldetails;
    EditText pannumber, voteridnumber, dlnumber, dob_dl, passportname, passportnumber, dobpassport, doipassport;
    ImageView panimg, voterfrontimg, voterbackimg, dlimg, passportfrontimg, passportbackimg;
    ImageView voter_front_address, voter_back_address, aadhar_front_address, aadhar_back_address, dl_front_address, passport_front_address, passport_back_address;
    ImageView ITER_income_img, bank_income_address, bank_selfemployeed_address, ITER_selfemployeed_img, bank_statement_img;

    LinearLayout income_radiobutton_layout1,income_radiobutton_layout;
    RadioButton yes2,no2,yes1,no1,selfemployeed,salaried;
    TextView errormsg;
    private final int PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c__details);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP,"2",getApplicationContext());
        error1 = (TextView) findViewById(R.id.error1);
        error2 = (TextView) findViewById(R.id.error2);
        error3 = (TextView) findViewById(R.id.error3);
        error4 = (TextView) findViewById(R.id.error4);
        error5 = (TextView) findViewById(R.id.error5);
        error6 = (TextView) findViewById(R.id.error6);
        error7= (TextView) findViewById(R.id.error7);
        //all details set
        edittext = (CardView) findViewById(R.id.edittext);
        alldetails = (TextView) findViewById(R.id.alldetails);
        //toolbar
        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        // api
        pannumber = (EditText) findViewById(R.id.pannumber);
        voteridnumber = (EditText) findViewById(R.id.voteridnumber);
        dob_dl = (EditText) findViewById(R.id.dob_dl);
        dlnumber = (EditText) findViewById(R.id.dlnumber);
        passportname = (EditText) findViewById(R.id.passportname);
        passportnumber = (EditText) findViewById(R.id.passportnumber);
        dobpassport = (EditText) findViewById(R.id.dobpassport);
        doipassport = (EditText) findViewById(R.id.doipassport);

        //correct image button
        correct1 = (ImageView) findViewById(R.id.correct1);
        correct2 = (ImageView) findViewById(R.id.correct2);
        correct3 = (ImageView) findViewById(R.id.correct3);
        correct4 = (ImageView) findViewById(R.id.correct4);

        //imageview api
        panimg = (ImageView) findViewById(R.id.panimg);
        voterfrontimg = (ImageView) findViewById(R.id.voterfrontimg);
        voterbackimg = (ImageView) findViewById(R.id.voterbackimg);
        dlimg = (ImageView) findViewById(R.id.dlimg);
        passportfrontimg = (ImageView) findViewById(R.id.passportfrontimg);
        passportbackimg = (ImageView) findViewById(R.id.passportbackimg);
        voter_front_address = (ImageView) findViewById(R.id.voter_front_address);
        voter_back_address = (ImageView) findViewById(R.id.voter_back_address);
        aadhar_front_address = (ImageView) findViewById(R.id.aadhar_front_address);
        aadhar_back_address = (ImageView) findViewById(R.id.aadhar_back_address);
        dl_front_address = (ImageView) findViewById(R.id.dl_front_address);
        passport_front_address = (ImageView) findViewById(R.id.passport_front_address);
        passport_back_address = (ImageView) findViewById(R.id.passport_back_address);
        ITER_income_img = (ImageView) findViewById(R.id.ITER_income_img);
        bank_income_address = (ImageView) findViewById(R.id.bank_income_address);
        bank_selfemployeed_address = (ImageView) findViewById(R.id.bank_selfemployeed_address);
        ITER_selfemployeed_img = (ImageView) findViewById(R.id.ITER_selfemployeed_img);
        bank_statement_img = (ImageView) findViewById(R.id.bank_statement_img);

        //imageminimizemaximize
        dropup1 = (ImageView) findViewById(R.id.dropup1);
        dropdown1 = (ImageView) findViewById(R.id.dropdown1);
        dropdown2 = (ImageView) findViewById(R.id.dropdown2);
        dropup2 = (ImageView) findViewById(R.id.dropup2);
        dropdown3 = (ImageView) findViewById(R.id.dropdown3);
        dropup3 = (ImageView) findViewById(R.id.dropup3);
        dropdown4 = (ImageView) findViewById(R.id.dropdown4);
        dropup4 = (ImageView) findViewById(R.id.dropup4);

        //mainlayout
        identitylayout = (LinearLayout) findViewById(R.id.identitylayout);
        addresslayout = (LinearLayout) findViewById(R.id.addresslayout);
        incomelayout = (LinearLayout) findViewById(R.id.incomelayout);
        banklayout = (LinearLayout) findViewById(R.id.banklayout);
        //spinner
        choose_identity = (Spinner) findViewById(R.id.choose_identity);
        choose_address = (Spinner) findViewById(R.id.choose_address);
        //cardview
        panlayout = (LinearLayout) findViewById(R.id.panlayout);
        voteridcardlayout = (LinearLayout) findViewById(R.id.voteridcardlayout);
        dllayout = (LinearLayout) findViewById(R.id.dllayout);
        passportlayout = (LinearLayout) findViewById(R.id.passportlayout);
        voter_address_layout = (LinearLayout) findViewById(R.id.voter_address_layout);
        aadhar_address_layout = (LinearLayout) findViewById(R.id.aadhar_address_layout);
        passport_address_layout = (LinearLayout) findViewById(R.id.passport_address_layout);
        dl_address_layout = (LinearLayout) findViewById(R.id.dl_address_layout);
        salaried_income_layout = (LinearLayout) findViewById(R.id.salaried_income_layout);
        selfemployeed_income_layout = (LinearLayout) findViewById(R.id.selfemployeed_income_layout);

        //checkbutton for identity
        check1 = (TextView) findViewById(R.id.check1);
        check2 = (TextView) findViewById(R.id.check2);
        check3 = (TextView) findViewById(R.id.check3);
        check4 = (TextView) findViewById(R.id.check4);

        //savebutton for identity
        save_submit1 = (TextView) findViewById(R.id.save_submit1);
        save_submit2 = (TextView) findViewById(R.id.save_submit2);
        save_submit3 = (TextView) findViewById(R.id.save_submit3);
        save_submit4 = (TextView) findViewById(R.id.save_submit4);



        //savebutton for address
        save1 = (TextView) findViewById(R.id.save1);
        save2 = (TextView) findViewById(R.id.save2);
        save3 = (TextView) findViewById(R.id.save3);
        save4 = (TextView) findViewById(R.id.save4);
        save1_voter_back_address = (TextView) findViewById(R.id.save1_voter_back_address);
        save2_aadhar_back_address = (TextView) findViewById(R.id.save2_aadhar_back_address);
        save4_passport_back_address = (TextView) findViewById(R.id.save4_passport_back_address);
        //save button for income
        savedata1 = (TextView) findViewById(R.id.savedata1);
        savedata2 = (TextView) findViewById(R.id.savedata2);

        //save bank statement
        savestatement = (TextView) findViewById(R.id.savestatement);
        //final save
        finalsave = (TextView) findViewById(R.id.finalsave);

        //linear layout for income radio button layout
        income_radiobutton_layout1 = (LinearLayout) findViewById(R.id.income_radiobutton_layout1);
        income_radiobutton_layout = (LinearLayout) findViewById(R.id.income_radiobutton_layout);

        yes1 = (RadioButton) findViewById(R.id.yes1);
        no1 = (RadioButton) findViewById(R.id.no1);
        yes2 = (RadioButton) findViewById(R.id.yes2);
        no2 = (RadioButton) findViewById(R.id.no2);
        selfemployeed = (RadioButton) findViewById(R.id.selfemployeed);
        salaried = (RadioButton) findViewById(R.id.salaried);

        errormsg=(TextView)findViewById(R.id.errormsg);

        selfemployeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfemployeed_income_layout.setVisibility(View.VISIBLE);
                income_radiobutton_layout.setVisibility(View.VISIBLE);
                identitylayout.setVisibility(View.GONE);
                voter_address_layout.setVisibility(View.GONE);
                dl_address_layout.setVisibility(View.GONE);
                passport_address_layout.setVisibility(View.GONE);
                passport_address_layout.setVisibility(View.GONE);
                salaried_income_layout.setVisibility(View.GONE);
                income_radiobutton_layout1.setVisibility(View.GONE);
                savedata1.setVisibility(View.GONE);
                savedata2.setVisibility(View.VISIBLE);
                correct3.setVisibility(View.GONE);
                SharedPref.saveStringInSharedPref(AppConstants.INCOME_TYPE, "SELFEMP", getApplicationContext());
            }
        });
        salaried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salaried_income_layout.setVisibility(View.VISIBLE);
                income_radiobutton_layout1.setVisibility(View.VISIBLE);
                identitylayout.setVisibility(View.GONE);
                aadhar_address_layout.setVisibility(View.GONE);
                dl_address_layout.setVisibility(View.GONE);
                passport_address_layout.setVisibility(View.GONE);
                selfemployeed_income_layout.setVisibility(View.GONE);
                income_radiobutton_layout.setVisibility(View.GONE);
                savedata1.setVisibility(View.VISIBLE);
                savedata2.setVisibility(View.GONE);
                correct3.setVisibility(View.GONE);

                SharedPref.saveStringInSharedPref(AppConstants.INCOME_TYPE, "SAL", getApplicationContext());
            }
        });

        yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"Yes",getApplicationContext());
            }
        });

        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"No",getApplicationContext());
            }
        });

        yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"Yes",getApplicationContext());
            }
        });

        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"No",getApplicationContext());
            }
        });

        panimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 1;
                showPictureDialog();


            }
        });
        voterfrontimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 2;
                showPictureDialog();
            }
        });
        voterbackimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validvoterfront == 1 && save_button_voter_id == 1) {
                    x = 3;
                    error2.setVisibility(View.GONE);
                    showPictureDialog();
                } else {
                    error2.setVisibility(View.VISIBLE);
                    error2.setText("Please upload first voter front image");
                }

            }
        });
        dlimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 4;
                showPictureDialog();
            }
        });
        passportfrontimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 5;
                showPictureDialog();
            }
        });
        passportbackimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validpassportfront == 1 && save_button_passport_id == 1) {
                    x = 6;
                    error4.setVisibility(View.GONE);
                    showPictureDialog();
                } else {
                    error4.setVisibility(View.VISIBLE);
                    error4.setText("please upload first passport front image");
                }

            }
        });
        voter_front_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 7;
                showPictureDialog();
            }
        });
        voter_back_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 8;
                showPictureDialog();
            }
        });
        aadhar_front_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 9;
                showPictureDialog();
            }
        });
        aadhar_back_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 10;
                showPictureDialog();
            }
        });
        dl_front_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 11;
                showPictureDialog();
            }
        });
        passport_front_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 12;
                showPictureDialog();
            }
        });
        passport_back_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 13;
                showPictureDialog();
            }
        });
        ITER_income_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = 1;
                choosePhotoFromGallary();
            }
        });
        ITER_selfemployeed_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = 2;
                choosePhotoFromGallary();
            }
        });
        bank_selfemployeed_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = 3;
                choosePhotoFromGallary();
            }
        });
        bank_income_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = 4;
                choosePhotoFromGallary();
            }
        });

        bank_statement_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y = 5;
                choosePhotoFromGallary();
            }
        });
        dropdown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identitylayout.setVisibility(View.VISIBLE);
                dropdown1.setVisibility(View.GONE);
                dropup1.setVisibility(View.VISIBLE);
                addresslayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);


            }
        });
        dropup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identitylayout.setVisibility(View.GONE);
                dropdown1.setVisibility(View.VISIBLE);
                dropup1.setVisibility(View.GONE);
                addresslayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);

            }
        });
        dropdown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addresslayout.setVisibility(View.VISIBLE);
                dropdown2.setVisibility(View.GONE);
                dropup2.setVisibility(View.VISIBLE);
                identitylayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);
            }
        });
        dropup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addresslayout.setVisibility(View.GONE);
                dropdown2.setVisibility(View.VISIBLE);
                dropup2.setVisibility(View.GONE);
                identitylayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);
            }
        });
        dropdown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomelayout.setVisibility(View.VISIBLE);
                dropdown3.setVisibility(View.GONE);
                dropup3.setVisibility(View.VISIBLE);
                identitylayout.setVisibility(View.GONE);
                addresslayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);
            }
        });
        dropup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomelayout.setVisibility(View.GONE);
                dropdown3.setVisibility(View.VISIBLE);
                dropup3.setVisibility(View.GONE);
                identitylayout.setVisibility(View.GONE);
                addresslayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.GONE);
            }
        });
        dropdown4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banklayout.setVisibility(View.VISIBLE);
                dropdown4.setVisibility(View.GONE);
                dropup4.setVisibility(View.VISIBLE);
                identitylayout.setVisibility(View.GONE);
                addresslayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                savestatement.setVisibility(View.VISIBLE);
            }
        });
        dropup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banklayout.setVisibility(View.GONE);
                dropdown4.setVisibility(View.VISIBLE);
                dropup4.setVisibility(View.GONE);
                identitylayout.setVisibility(View.GONE);
                addresslayout.setVisibility(View.GONE);
                incomelayout.setVisibility(View.GONE);
                savestatement.setVisibility(View.GONE);
            }
        });
        //spinner1
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                proofofidentity);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_identity.setAdapter(ad);

        //spinner2
        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                proofofaddress);

        ad1.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_address.setAdapter(ad1);
        //spinner3
     /*   ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                proofofincome);

        ad2.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_income.setAdapter(ad2);*/

        choose_identity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = choose_identity.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, proofofidentity[index], getApplicationContext());
                Log.e("taf", "onResponse: " + SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()));


                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals("PAN Card")) {
                    panlayout.setVisibility(View.VISIBLE);
                    check1.setVisibility(View.VISIBLE);
                    check2.setVisibility(View.GONE);
                    check3.setVisibility(View.GONE);
                    check4.setVisibility(View.GONE);
                    edittext.setVisibility(View.GONE);
                    voteridcardlayout.setVisibility(View.GONE);
                    addresslayout.setVisibility(View.GONE);
                    dllayout.setVisibility(View.GONE);
                    voteridcardlayout.setVisibility(View.GONE);
                    passportlayout.setVisibility(View.GONE);
                    save_submit1.setVisibility(View.GONE);
                    save_submit2.setVisibility(View.GONE);
                    save_submit3.setVisibility(View.GONE);
                    save_submit4.setVisibility(View.GONE);
                    correct1.setVisibility(View.GONE);

                }
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals("Voter ID Card")) {
                    voteridcardlayout.setVisibility(View.VISIBLE);
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.VISIBLE);
                    check3.setVisibility(View.GONE);
                    check4.setVisibility(View.GONE);
                    edittext.setVisibility(View.GONE);
                    panlayout.setVisibility(View.GONE);
                    addresslayout.setVisibility(View.GONE);
                    dllayout.setVisibility(View.GONE);
                    passportlayout.setVisibility(View.GONE);
                    panlayout.setVisibility(View.GONE);
                    save_submit1.setVisibility(View.GONE);
                    save_submit2.setVisibility(View.GONE);
                    save_submit3.setVisibility(View.GONE);
                    save_submit4.setVisibility(View.GONE);
                    correct1.setVisibility(View.GONE);

                }
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals("Driving License")) {
                    dllayout.setVisibility(View.VISIBLE);
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.GONE);
                    check3.setVisibility(View.VISIBLE);
                    check4.setVisibility(View.GONE);
                    edittext.setVisibility(View.GONE);
                    addresslayout.setVisibility(View.GONE);
                    passportlayout.setVisibility(View.GONE);
                    voteridcardlayout.setVisibility(View.GONE);
                    panlayout.setVisibility(View.GONE);
                    save_submit1.setVisibility(View.GONE);
                    save_submit2.setVisibility(View.GONE);
                    save_submit3.setVisibility(View.GONE);
                    save_submit4.setVisibility(View.GONE);
                    correct1.setVisibility(View.GONE);

                }
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals("Passport")) {
                    passportlayout.setVisibility(View.VISIBLE);
                    check1.setVisibility(View.GONE);
                    check2.setVisibility(View.GONE);
                    check3.setVisibility(View.GONE);
                    check4.setVisibility(View.VISIBLE);
                    edittext.setVisibility(View.GONE);
                    dllayout.setVisibility(View.GONE);
                    voteridcardlayout.setVisibility(View.GONE);
                    panlayout.setVisibility(View.GONE);
                    addresslayout.setVisibility(View.GONE);
                    save_submit1.setVisibility(View.GONE);
                    save_submit2.setVisibility(View.GONE);
                    save_submit3.setVisibility(View.GONE);
                    save_submit4.setVisibility(View.GONE);
                    correct1.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        choose_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = choose_address.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, proofofaddress[index], getApplicationContext());
                Log.e("taf", "onResponse: " + SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()));


                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals("Voter ID Card")) {
                    voter_address_layout.setVisibility(View.VISIBLE);
                    identitylayout.setVisibility(View.GONE);
                    aadhar_address_layout.setVisibility(View.GONE);
                    dl_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    save1.setVisibility(View.VISIBLE);
                    save1_voter_back_address.setVisibility(View.VISIBLE);
                    save2.setVisibility(View.GONE);
                    save2_aadhar_back_address.setVisibility(View.GONE);
                    save3.setVisibility(View.GONE);
                    save4.setVisibility(View.GONE);
                    save4_passport_back_address.setVisibility(View.GONE);
                    correct2.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "VOTER", getApplicationContext());
                }
                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals("Aadhar Card")) {
                    aadhar_address_layout.setVisibility(View.VISIBLE);
                    identitylayout.setVisibility(View.GONE);
                    voter_address_layout.setVisibility(View.GONE);
                    dl_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);
                    save1_voter_back_address.setVisibility(View.GONE);
                    save2.setVisibility(View.VISIBLE);
                    save2_aadhar_back_address.setVisibility(View.VISIBLE);
                    save3.setVisibility(View.GONE);
                    save4.setVisibility(View.GONE);
                    save4_passport_back_address.setVisibility(View.GONE);
                    correct2.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "AADHAR", getApplicationContext());

                }
                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals("Driving License")) {
                    dl_address_layout.setVisibility(View.VISIBLE);
                    identitylayout.setVisibility(View.GONE);
                    voter_address_layout.setVisibility(View.GONE);
                    aadhar_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);
                    save1_voter_back_address.setVisibility(View.GONE);
                    save2.setVisibility(View.GONE);
                    save2_aadhar_back_address.setVisibility(View.GONE);
                    save3.setVisibility(View.VISIBLE);
                    save4.setVisibility(View.GONE);
                    save4_passport_back_address.setVisibility(View.GONE);
                    correct2.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "DL", getApplicationContext());

                }
                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals("Passport")) {
                    passport_address_layout.setVisibility(View.VISIBLE);
                    identitylayout.setVisibility(View.GONE);
                    voter_address_layout.setVisibility(View.GONE);
                    aadhar_address_layout.setVisibility(View.GONE);
                    dl_address_layout.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);
                    save1_voter_back_address.setVisibility(View.GONE);
                    save2.setVisibility(View.GONE);
                    save2_aadhar_back_address.setVisibility(View.GONE);
                    save3.setVisibility(View.GONE);
                    save4.setVisibility(View.VISIBLE);
                    save4_passport_back_address.setVisibility(View.VISIBLE);
                    correct2.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "PASSPORT", getApplicationContext());

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

/*
        choose_income.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = choose_income.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.INCOME_SPINNER_VALUE, proofofincome[index], getApplicationContext());
                Log.e("taf", "onResponse: " + SharedPref.getStringFromSharedPref(AppConstants.INCOME_SPINNER_VALUE, getApplicationContext()));


                if (SharedPref.getStringFromSharedPref(AppConstants.INCOME_SPINNER_VALUE, getApplicationContext()).equals("Salaried")) {
                    salaried_income_layout.setVisibility(View.VISIBLE);
                    income_radiobutton_layout1.setVisibility(View.VISIBLE);
                    identitylayout.setVisibility(View.GONE);
                    aadhar_address_layout.setVisibility(View.GONE);
                    dl_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    selfemployeed_income_layout.setVisibility(View.GONE);
                    income_radiobutton_layout.setVisibility(View.GONE);
                    savedata1.setVisibility(View.VISIBLE);
                    savedata2.setVisibility(View.GONE);
                    correct3.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.INCOME_TYPE, "SAL", getApplicationContext());
                }
                if (SharedPref.getStringFromSharedPref(AppConstants.INCOME_SPINNER_VALUE, getApplicationContext()).equals("Self Employeed")) {
                    selfemployeed_income_layout.setVisibility(View.VISIBLE);
                    income_radiobutton_layout.setVisibility(View.VISIBLE);

                    identitylayout.setVisibility(View.GONE);
                    voter_address_layout.setVisibility(View.GONE);
                    dl_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    passport_address_layout.setVisibility(View.GONE);
                    salaried_income_layout.setVisibility(View.GONE);
                    income_radiobutton_layout1.setVisibility(View.GONE);
                    savedata1.setVisibility(View.GONE);
                    savedata2.setVisibility(View.VISIBLE);
                    correct3.setVisibility(View.GONE);
                    SharedPref.saveStringInSharedPref(AppConstants.INCOME_TYPE, "SELFEMP", getApplicationContext());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

        //pan api save
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panapi();
            }
        });
        save_submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pannumber.setFocusable(false);
                MLDoc1_upload();


            }
        });
        //voter api save
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voterapi();
            }
        });
        save_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voteridnumber.setFocusable(false);
                MLDoc1_upload();


            }
        });
        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlapi();
            }
        });
        save_submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlnumber.setFocusable(false);
                MLDoc1_upload();




            }
        });
        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passportverificationapi();
            }
        });
        save_submit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passportnumber.setFocusable(false);
                MLDoc1_upload();





            }
        });
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /*     MLAddproof_upload();*/

            }
        });
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   MLAddproof_upload();*/

            }
        });
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      /*          MLAddproof_upload();*/

            }
        });
        save4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   MLAddproof_upload();*/

            }
        });
        savedata1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   MLEmpproof_upload();*/
                MLDoc3_upload();
            }
        });
        savedata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  MLEmpproof_upload();*/
                MLDoc3_upload();
            }
        });
        savestatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  /*  MLBnkproof_upload();*/



            }
        });

        finalsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("valida", "va;lida" + validation1);
                Log.e("valida", "va;lida" + validation2);
                Log.e("valida", "va;lida" + validation3);
                Log.e("valida", "va;lida" + validation4);


                if (validation1 == 1 && validation2 == 2 ) {
                    Intent it = new Intent(getApplicationContext(), Success.class);
                    startActivity(it);
                    errormsg.setVisibility(View.GONE);
                } else {
                    errormsg.setVisibility(View.VISIBLE);
                }

            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateLabel();

            }
        };
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, month);
                myCalendar1.set(Calendar.DAY_OF_MONTH, day);
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, month);
                myCalendar2.set(Calendar.DAY_OF_MONTH, day);
                updateLabel1();

            }
        };
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, month);
                myCalendar2.set(Calendar.DAY_OF_MONTH, day);
                updateLabel2();

            }
        };

        dob_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hhhfg", "vfdfjv");
                new DatePickerDialog(KYC_Details.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dobpassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hhhfg", "vfdfjv");
                new DatePickerDialog(KYC_Details.this, date1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        doipassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hhhfg", "vfdfjv");
                new DatePickerDialog(KYC_Details.this, date2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(KYC_Details.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });

        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(KYC_Details.this, Home.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
                startActivity(it);

            }
        });
      /*  if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }*/

        /*  requestMultiplePermissions();*/
       /* if (!checkPermission()) {
            requestPermission();
        }*/
    }


    private void panapi() {
        custPrograssbar.prograssCreate(KYC_Details.this);
        PAN_POJO pojo = new PAN_POJO("y", pannumber.getText().toString());
        RestApis restApis = NetworkHandler.instanceMaker4().create(RestApis.class);
        Call<PAN_MODEL> call = restApis.panapi(pojo);
        call.enqueue(new Callback<PAN_MODEL>() {
            @Override
            public void onResponse(Call<PAN_MODEL> call, Response<PAN_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();


                    if (response.body().getStatuscode().equals("101")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Name: " + response.body().getResult().getName());
                        save_submit1.setVisibility(View.VISIBLE);
                        check1.setVisibility(View.GONE);
                        SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "PAN", getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ID_PROOF_NO, pannumber.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_IDENTITY, response.body().getResult().getName(), getApplicationContext());

                    } else if (response.body().getStatuscode().equals("102")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Entered PAN Card No is wrong!!!");
                        check1.setVisibility(View.VISIBLE);
                        save_submit1.setVisibility(View.GONE);
                    }

                }


            }

            @Override
            public void onFailure(Call<PAN_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void voterapi() {
        custPrograssbar.prograssCreate(KYC_Details.this);
        VOTER_POJO pojo = new VOTER_POJO("y", voteridnumber.getText().toString());
        RestApis restApis = NetworkHandler.instanceMaker4().create(RestApis.class);
        Call<VOTER_MODEL> call = restApis.voterapi(pojo);
        call.enqueue(new Callback<VOTER_MODEL>() {
            @Override
            public void onResponse(Call<VOTER_MODEL> call, Response<VOTER_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getStatuscode().equals("101")) {

                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Name: " + response.body().getResult().getName() +
                                System.getProperty("line.separator") +
                                "Relation Name: " + response.body().getResult().getRln_name() +
                                System.getProperty("line.separator") +
                                "Age: " + response.body().getResult().getAge() +
                                System.getProperty("line.separator") +
                                "Gender: " + response.body().getResult().getGender());
                        save_submit2.setVisibility(View.VISIBLE);

                        check2.setVisibility(View.GONE);
                        SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "VOTER", getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ID_PROOF_NO, voteridnumber.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_IDENTITY, response.body().getResult().getName(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.AGE_IDENTITY, response.body().getResult().getAge(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RELATIVENAME_IDENTITY, response.body().getResult().getRln_name(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.GENDER_IDENTITY, response.body().getResult().getGender(), getApplicationContext());


                    } else if (response.body().getStatuscode().equals("103")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Entered Voter ID is wrong!!!");
                        check2.setVisibility(View.VISIBLE);
                        save_submit2.setVisibility(View.GONE);

                    }
                }

                custPrograssbar.closePrograssBar();

            }

            @Override
            public void onFailure(Call<VOTER_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void dlapi() {
        custPrograssbar.prograssCreate(KYC_Details.this);
        DL_POJO pojo = new DL_POJO(dlnumber.getText().toString(), dob_dl.getText().toString(), "true", "y");
        RestApis restApis = NetworkHandler.instanceMaker7().create(RestApis.class);
        Call<DL_MODEL> call = restApis.dl(pojo);
        call.enqueue(new Callback<DL_MODEL>() {
            @Override
            public void onResponse(Call<DL_MODEL> call, Response<DL_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getStatusCode().equals("101")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Name: " + response.body().getResult().getName() +
                                System.getProperty("line.separator") +
                                "Father/husband: " + response.body().getResult().getFatherhusband() +
                                System.getProperty("line.separator") +
                                "bloodGroup: " + response.body().getResult().getBloodGroup() +
                                System.getProperty("line.separator") +
                                "DOB: " + response.body().getResult().getDob() +
                                System.getProperty("line.separator") +
                                "DLNumber: " + response.body().getResult().getDlNumber());
                        save_submit3.setVisibility(View.VISIBLE);
                        check3.setVisibility(View.GONE);
                        SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "DL", getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ID_PROOF_NO, dlnumber.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_IDENTITY, response.body().getResult().getName(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DOB_IDENTITY, response.body().getResult().getDob(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.BLOODGROUP_IDENTITY, response.body().getResult().getBloodGroup(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.RELATIVENAME_IDENTITY, response.body().getResult().getFatherhusband(), getApplicationContext());
                    } else if (response.body().getStatusCode().equals("102")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Entered DL details is wrong!!!");
                        check3.setVisibility(View.VISIBLE);
                        save_submit3.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<DL_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void passportverificationapi() {
        custPrograssbar.prograssCreate(KYC_Details.this);
        PASSPORT_POJO pojo = new PASSPORT_POJO("y", "BO3072344560818", dobpassport.getText().toString(),
                passportnumber.getText().toString(), doipassport.getText().toString(), passportname.getText().toString());
        RestApis restApis = NetworkHandler.instanceMaker4().create(RestApis.class);
        Call<PASSPORT_MODEL> call = restApis.passportverificationapi(pojo);
        call.enqueue(new Callback<PASSPORT_MODEL>() {
            @Override
            public void onResponse(Call<PASSPORT_MODEL> call, Response<PASSPORT_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getStatusCode().equals("101")) {
                        custPrograssbar.closePrograssBar();
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Name: " + response.body().getResult().getName().getNameFromPassport() + " " +
                                response.body().getResult().getName().getSurnameFromPassport() +
                                System.getProperty("line.separator") +
                                "DispatchedOnFromSource: " + response.body().getResult().getDateOfIssue().getDispatchedOnFromSource() +
                                System.getProperty("line.separator") +
                                "PassportNumberFromSource: " + response.body().getResult().getPassportNumber().getPassportNumberFromSource());
                        save_submit4.setVisibility(View.VISIBLE);
                        check4.setVisibility(View.GONE);
                        SharedPref.saveStringInSharedPref(AppConstants.PROOF_TYPE, "PASSPORT", getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.ID_PROOF_NO, passportnumber.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.NAME_IDENTITY, response.body().getResult().getName().getNameFromPassport() + " " +
                                response.body().getResult().getName().getSurnameFromPassport(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DOB_IDENTITY, dobpassport.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.SURNAME, response.body().getResult().getName().getSurnameFromPassport(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DOI_IDENTITY, doipassport.getText().toString(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.DISPATCHFRM_SOURCE_IDENTITY, response.body().getResult().getDateOfIssue().getDispatchedOnFromSource(), getApplicationContext());

                    } else if (response.body().getStatusCode().equals("102")) {
                        edittext.setVisibility(View.VISIBLE);
                        alldetails.setText("Entered Passport details is wrong!!!");
                        check4.setVisibility(View.VISIBLE);
                        save_submit4.setVisibility(View.GONE);

                    }

                }
            }

            @Override
            public void onFailure(Call<PASSPORT_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
    private void MLDoc1_upload() {
        custPrograssbar.prograssCreate(getApplicationContext());
        MLDoc1_upload_POJO pojo = new MLDoc1_upload_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ID_PROOF_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RELATIVENAME_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AGE_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.GENDER_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOB_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOI_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.BLOODGROUP_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DISPATCHFRM_SOURCE_IDENTITY, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<MLDoc1_upload_MODEL> call = restApis.MLDoc1_upload(pojo);
        call.enqueue(new Callback<MLDoc1_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLDoc1_upload_MODEL> call, Response<MLDoc1_upload_MODEL> response) {
                if (response.body() != null) {
custPrograssbar.closePrograssBar();

                    if (response.body().getCode().equals("200")) {
                        identitylayout.setVisibility(View.GONE);
                        correct1.setVisibility(View.VISIBLE);
                        validation1 = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<MLDoc1_upload_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void MLDoc3_upload() {
        custPrograssbar.prograssCreate(getApplicationContext());
        MLDoc3_upload_POJO pojo = new MLDoc3_upload_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN,
                getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.INCOME_TYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.SALARIED_INCOMEVALUE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<MLDoc3_upload_MODEL> call = restApis.MLDoc3_upload(pojo);
        call.enqueue(new Callback<MLDoc3_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLDoc3_upload_MODEL> call, Response<MLDoc3_upload_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();

                    if (response.body().getCode().equals("200")) {
                        correct3.setVisibility(View.VISIBLE);
                        incomelayout.setVisibility(View.GONE);
                        validation2 = 2;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<MLDoc3_upload_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }


/*
    private void MLIDproof_upload() {
        Log.e("testingggg", "testingggg11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());
        String mprooftype = SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext());
        String mproofno = SharedPref.getStringFromSharedPref(AppConstants.ID_PROOF_NO, getApplicationContext());
        String mnameidentity = SharedPref.getStringFromSharedPref(AppConstants.NAME_IDENTITY, getApplicationContext());
        String mrelativeidentity = SharedPref.getStringFromSharedPref(AppConstants.RELATIVENAME_IDENTITY, getApplicationContext());
        String mageidentity = SharedPref.getStringFromSharedPref(AppConstants.AGE_IDENTITY, getApplicationContext());
        String mgenderidentity = SharedPref.getStringFromSharedPref(AppConstants.GENDER_IDENTITY, getApplicationContext());
        String mdobidentity = SharedPref.getStringFromSharedPref(AppConstants.DOB_IDENTITY, getApplicationContext());
        String mdoiidentity = SharedPref.getStringFromSharedPref(AppConstants.DOI_IDENTITY, getApplicationContext());
        String mbloodgroupidentity = SharedPref.getStringFromSharedPref(AppConstants.BLOODGROUP_IDENTITY, getApplicationContext());
        String mdispatchsourceidentity = SharedPref.getStringFromSharedPref(AppConstants.DISPATCHFRM_SOURCE_IDENTITY, getApplicationContext());


        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);
        RequestBody prooftype = RequestBody.create(MediaType.parse("multipart/form-data"), mprooftype);
        RequestBody proofno = RequestBody.create(MediaType.parse("multipart/form-data"), mproofno);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), mnameidentity);
        RequestBody relative = RequestBody.create(MediaType.parse("multipart/form-data"), mrelativeidentity);
        RequestBody ageidentity = RequestBody.create(MediaType.parse("multipart/form-data"), mageidentity);
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), mgenderidentity);
        RequestBody dob = RequestBody.create(MediaType.parse("multipart/form-data"), mdobidentity);
        RequestBody doi = RequestBody.create(MediaType.parse("multipart/form-data"), mdoiidentity);
        RequestBody bloodgroup = RequestBody.create(MediaType.parse("multipart/form-data"), mbloodgroupidentity);
        RequestBody dispatchsource = RequestBody.create(MediaType.parse("multipart/form-data"), mdispatchsourceidentity);
        Log.e("testingggg", "testingggg11111");


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("idproof_file_front", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg11111");


        File idFile2 = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile2;
        MultipartBody.Part vechileDocUpload2;
        mFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile2);
        vechileDocUpload2 = MultipartBody.Part.createFormData("idproof_file_back", idFile2.getName(), mFile2);
        Log.e("testingggg", "testingggg11111");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("testingggg", "testingggg22222");

        Call<MLIDproof_upload_MODEL> call = restApis.MLIDproof_upload(cust_code, prooftype, proofno, name, relative,
                ageidentity, gender, dob, doi, bloodgroup, dispatchsource, vechileDocUpload, vechileDocUpload2);
        Log.e("testingggg", "testingggg22222");
        call.enqueue(new Callback<MLIDproof_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLIDproof_upload_MODEL> call, Response<MLIDproof_upload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        identitylayout.setVisibility(View.GONE);
                        correct1.setVisibility(View.VISIBLE);
                        validation1 = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLIDproof_upload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/

/*
    private void MLAddproof_upload() {
        Log.e("demotestting", "demotestting11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());
        String mprooftype = SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext());


        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);
        RequestBody prooftype = RequestBody.create(MediaType.parse("multipart/form-data"), mprooftype);

        Log.e("demotestting", "demotestting22222");

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("idproof_file_front", idFile.getName(), mFile1);
        Log.e("demotestting", "demotestting333333");


        File idFile2 = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile2;
        MultipartBody.Part vechileDocUpload2;
        mFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile2);
        vechileDocUpload2 = MultipartBody.Part.createFormData("idproof_file_back", idFile2.getName(), mFile2);
        Log.e("demotestting", "demotestting44444");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("demotestting", "demotestting333333");

        Call<MLAddproof_upload_MODEL> call = restApis.MLAddproof_upload(cust_code, prooftype, vechileDocUpload, vechileDocUpload2);
        Log.e("demotestting", "demotestting555555");
        call.enqueue(new Callback<MLAddproof_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLAddproof_upload_MODEL> call, Response<MLAddproof_upload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        correct2.setVisibility(View.VISIBLE);
                        addresslayout.setVisibility(View.GONE);
                        validation2 = 2;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLAddproof_upload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/

/*
    private void MLEmpproof_upload() {
        Log.e("testingggg", "testingggg11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());
        String mprooftype = SharedPref.getStringFromSharedPref(AppConstants.INCOME_TYPE, getApplicationContext());


        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);
        RequestBody prooftype = RequestBody.create(MediaType.parse("multipart/form-data"), mprooftype);

        Log.e("testingggg", "testingggg11111");

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("bankstmt_file", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg11111");


        File idFile2 = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile2;
        MultipartBody.Part vechileDocUpload2;
        mFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile2);
        vechileDocUpload2 = MultipartBody.Part.createFormData("salaryslip_file", idFile2.getName(), mFile2);

        File idFile3 = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile3;
        MultipartBody.Part vechileDocUpload3;
        mFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile3);
        vechileDocUpload3 = MultipartBody.Part.createFormData("itr_file", idFile3.getName(), mFile3);


        Log.e("testingggg", "testingggg11111");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("testingggg", "testingggg22222");

        Call<MLEmpproof_upload_MODEL> call = restApis.MLEmpproof_upload(cust_code, prooftype, vechileDocUpload, vechileDocUpload2, vechileDocUpload3);
        Log.e("testingggg", "testingggg22222");
        call.enqueue(new Callback<MLEmpproof_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLEmpproof_upload_MODEL> call, Response<MLEmpproof_upload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        correct3.setVisibility(View.VISIBLE);
                        incomelayout.setVisibility(View.GONE);
                        validation3 = 3;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLEmpproof_upload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/

/*
    private void MLBnkproof_upload() {
        Log.e("testingggg", "testingggg11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());


        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);

        Log.e("testingggg", "testingggg11111");

        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("bankproof_file", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg11111");

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Log.e("testingggg", "testingggg22222");

        Call<MLBnkproof_upload_MODEL> call = restApis.MLBnkproof_upload(cust_code, vechileDocUpload);
        Log.e("testingggg", "testingggg22222");
        call.enqueue(new Callback<MLBnkproof_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLBnkproof_upload_MODEL> call, Response<MLBnkproof_upload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        correct4.setVisibility(View.VISIBLE);
                        banklayout.setVisibility(View.GONE);
                        savestatement.setVisibility(View.GONE);
                        validation4 = 4;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLBnkproof_upload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }
*/

/*
    private void MLIDproofFrontUpload() {
        Log.e("testingggg", "testingggg11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());
        String mprooftype = SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext());
        String mproofno = SharedPref.getStringFromSharedPref(AppConstants.ID_PROOF_NO, getApplicationContext());
        String mnameidentity = SharedPref.getStringFromSharedPref(AppConstants.NAME_IDENTITY, getApplicationContext());
        String mrelativeidentity = SharedPref.getStringFromSharedPref(AppConstants.RELATIVENAME_IDENTITY, getApplicationContext());
        String mageidentity = SharedPref.getStringFromSharedPref(AppConstants.AGE_IDENTITY, getApplicationContext());
        String mgenderidentity = SharedPref.getStringFromSharedPref(AppConstants.GENDER_IDENTITY, getApplicationContext());
        String mdobidentity = SharedPref.getStringFromSharedPref(AppConstants.DOB_IDENTITY, getApplicationContext());
        String mdoiidentity = SharedPref.getStringFromSharedPref(AppConstants.DOI_IDENTITY, getApplicationContext());
        String mbloodgroupidentity = SharedPref.getStringFromSharedPref(AppConstants.BLOODGROUP_IDENTITY, getApplicationContext());
        String mdispatchsourceidentity = SharedPref.getStringFromSharedPref(AppConstants.DISPATCHFRM_SOURCE_IDENTITY, getApplicationContext());


        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);
        RequestBody prooftype = RequestBody.create(MediaType.parse("multipart/form-data"), mprooftype);
        RequestBody proofno = RequestBody.create(MediaType.parse("multipart/form-data"), mproofno);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), mnameidentity);
        RequestBody relative = RequestBody.create(MediaType.parse("multipart/form-data"), mrelativeidentity);
        RequestBody ageidentity = RequestBody.create(MediaType.parse("multipart/form-data"), mageidentity);
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), mgenderidentity);
        RequestBody dob = RequestBody.create(MediaType.parse("multipart/form-data"), mdobidentity);
        RequestBody doi = RequestBody.create(MediaType.parse("multipart/form-data"), mdoiidentity);
        RequestBody bloodgroup = RequestBody.create(MediaType.parse("multipart/form-data"), mbloodgroupidentity);
        RequestBody dispatchsource = RequestBody.create(MediaType.parse("multipart/form-data"), mdispatchsourceidentity);
        Log.e("testingggg", "testingggg11111");


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("idproof_file_front", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg11111");


        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Log.e("testingggg", "testingggg22222");

        Call<MLIDproofFrontUpload_MODEL> call = restApis.MLIDproofFrontUpload(cust_code, prooftype, proofno, name, relative,
                ageidentity, gender, dob, doi, bloodgroup, dispatchsource, vechileDocUpload);
        Log.e("testingggg", "testingggg22222");
        call.enqueue(new Callback<MLIDproofFrontUpload_MODEL>() {
            @Override
            public void onResponse(Call<MLIDproofFrontUpload_MODEL> call, Response<MLIDproofFrontUpload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("testingggg", "testingggg10000" + response.body().getCode());
                    if (response.body().getCode() == 200) {
                        Toast.makeText(getApplicationContext(), "Successfully uploaded the document", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLIDproofFrontUpload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/

/*
    private void MLIDproofBackUpload() {
        Log.e("testingggg", "testingggg11111");
        String mcustcode = SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext());
        String mprooftype = SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext());

        RequestBody cust_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcustcode);
        RequestBody prooftype = RequestBody.create(MediaType.parse("multipart/form-data"), mprooftype);

        Log.e("testingggg", "testingggg11111");


        File idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.IMAGE_KYC_PROOF, getApplicationContext()));
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload = MultipartBody.Part.createFormData("idproof_file_back", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg11111");


        RestApis restApis = NetworkHandler.instanceMaker11().create(RestApis.class);
        Log.e("testingggg", "testingggg22222");

        Call<MLIDproofBackUpload_MODEL> call = restApis.MLIDproofBackUpload(cust_code, prooftype, vechileDocUpload);
        Log.e("testingggg", "testingggg22222");
        call.enqueue(new Callback<MLIDproofBackUpload_MODEL>() {
            @Override
            public void onResponse(Call<MLIDproofBackUpload_MODEL> call, Response<MLIDproofBackUpload_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode() == 200) {
                        Toast.makeText(getApplicationContext(), "Successfully uploaded the document", Toast.LENGTH_LONG).show();
                        identitylayout.setVisibility(View.GONE);
                        correct1.setVisibility(View.VISIBLE);
                        validation1 = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<MLIDproofBackUpload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }
*/


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
                            case 1:
                                choosePhotoFromGallary();
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

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

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
                validpan = 1;
                panimg.setImageBitmap(thumbnail);

            } else if (x == 2) {
                validvoterfront = 1;
                voterfrontimg.setImageBitmap(thumbnail);
            } else if (x == 3) {
                validvoterback = 1;
                voterbackimg.setImageBitmap(thumbnail);
            } else if (x == 4) {
                validdl = 1;
                dlimg.setImageBitmap(thumbnail);
            } else if (x == 5) {
                validpassportfront = 1;
                passportfrontimg.setImageBitmap(thumbnail);
            } else if (x == 6) {
                validpassportback = 1;
                passportbackimg.setImageBitmap(thumbnail);
            } else if (x == 7) {
                voter_front_address.setImageBitmap(thumbnail);
            } else if (x == 8) {
                voter_back_address.setImageBitmap(thumbnail);
            } else if (x == 9) {
                aadhar_front_address.setImageBitmap(thumbnail);
            } else if (x == 10) {
                aadhar_back_address.setImageBitmap(thumbnail);
            } else if (x == 11) {
                dl_front_address.setImageBitmap(thumbnail);
            } else if (x == 12) {
                passport_front_address.setImageBitmap(thumbnail);
            } else if (x == 13) {
                passport_back_address.setImageBitmap(thumbnail);
            }
            saveImage(thumbnail);

        } else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    if (y == 1) {
                        ITER_income_img.setImageBitmap(bitmap);

                    } else if (y == 2) {
                        ITER_selfemployeed_img.setImageBitmap(bitmap);

                    } else if (y == 3) {
                        bank_selfemployeed_address.setImageBitmap(bitmap);

                    } else if (y == 4) {
                        bank_income_address.setImageBitmap(bitmap);

                    } else if (y == 5) {
                        validbankstatement=1;
                        bank_statement_img.setImageBitmap(bitmap);

                    }
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    public String saveImage(Bitmap myBitmap) {
        Log.e("TAG", "File Saved1");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Log.e("TAG", "File Saved2");
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Log.e("TAG", "File Saved3");
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        Log.e("Full Path", Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
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
            File f = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoney");
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
            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_KYC_PROOF, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(KYC_Details.this, READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(KYC_Details.this, WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(KYC_Details.this, Manifest.permission.CAMERA);

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
            ActivityCompat.requestPermissions(KYC_Details.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void requestMultiplePermissions() {


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dob_dl.setText(dateFormat.format(myCalendar.getTime()));
        SharedPref.saveStringInSharedPref(AppConstants.DOBVALUE, dob_dl.getText().toString(), getApplicationContext());

    }

    private void updateLabel1() {

        String myFormat2 = "dd/MM/yyyy";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(myFormat2, Locale.US);
        dobpassport.setText(dateFormat2.format(myCalendar1.getTime()));

    }

    private void updateLabel2() {

        String myFormat3 = "dd/MM/yyyy";
        SimpleDateFormat dateFormat3 = new SimpleDateFormat(myFormat3, Locale.US);
        doipassport.setText(dateFormat3.format(myCalendar2.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
/*
    private void MLIDproof_upload() {
        custPrograssbar.prograssCreate(KYC_Details.this);
        MLIDproof_upload_POJO pojo = new MLIDproof_upload_POJO(SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PROOF_TYPE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.ID_PROOF_NO, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.NAME_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.RELATIVENAME_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.AGE_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.GENDER_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOB_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOI_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.BLOODGROUP_IDENTITY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DISPATCHFRM_SOURCE_IDENTITY, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<MLIDproof_upload_MODEL> call = restApis.MLIDproof_upload(pojo);
        call.enqueue(new Callback<MLIDproof_upload_MODEL>() {
            @Override
            public void onResponse(Call<MLIDproof_upload_MODEL> call, Response<MLIDproof_upload_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                }
            }

            @Override
            public void onFailure(Call<MLIDproof_upload_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }*/
