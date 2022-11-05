package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Model.DL_MODEL;
import com.qts.gopik_money.Model.MLDoc1_upload_MODEL;
import com.qts.gopik_money.Model.MLDoc3_upload_MODEL;
import com.qts.gopik_money.Model.PAN_MODEL;
import com.qts.gopik_money.Model.PASSPORT_MODEL;
import com.qts.gopik_money.Model.VOTER_MODEL;
import com.qts.gopik_money.Pojo.DL_POJO;
import com.qts.gopik_money.Pojo.MLDoc1_upload_POJO;
import com.qts.gopik_money.Pojo.MLDoc3_upload_POJO;
import com.qts.gopik_money.Pojo.PAN_POJO;
import com.qts.gopik_money.Pojo.PASSPORT_POJO;
import com.qts.gopik_money.Pojo.VOTER_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KYC_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String networkError = "It seems your Network is unstable . Please Try again!";
    String mName = "Name: ";
    String mLineSeparator = "line.separator";
    String mVoter = "Voter ID Card";
    String mLicence = "Driving License";
    String mPassport = "Passport";
    Integer validation1 = 0;
    Integer validation2 = 0;
    public int validvoterfront = 0;
    public int validvoterback = 0;
    FileOutputStream fo;
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
    private int GALLERY = 1;
    private int CAMERA = 2;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    ImageView hometoolbar;
    ImageView backarrow;
    CustPrograssbar custPrograssbar;
    ImageView dropdown1;
    ImageView dropup1;
    ImageView dropdown2;
    ImageView dropup2;
    ImageView dropup3;
    ImageView dropdown3;
    ImageView dropup4;
    ImageView dropdown4;
    ImageView correct1;
    ImageView correct2;
    ImageView correct3;
    ImageView correct4;


    TextView error1;
    TextView error2;
    TextView error3;
    TextView error4;
    TextView error5;
    TextView error6;
    TextView error7;

    LinearLayout identitylayout;
    LinearLayout addresslayout;
    LinearLayout incomelayout;
    LinearLayout banklayout;
    LinearLayout voteridcardlayout;
    LinearLayout panlayout;
    LinearLayout dllayout;
    LinearLayout passportlayout;
    LinearLayout voter_address_layout;
    LinearLayout aadhar_address_layout;
    LinearLayout dl_address_layout;
    LinearLayout passport_address_layout;
    LinearLayout salaried_income_layout;
    LinearLayout selfemployeed_income_layout;
    String[] proofofidentity = {"Select Proof Of Identity", "PAN Card", mVoter, mLicence, mPassport};
    String[] proofofaddress = {"Select Proof Of Address", mVoter, "Aadhar Card", mLicence, mPassport};

    Spinner choose_identity;
    Spinner choose_address;
    TextView check1;
    TextView check2;
    TextView check3;
    TextView check4;
    TextView save_submit1;
    TextView save_submit2;
    TextView save_submit3;
    TextView save_submit4;
    TextView save1;
    TextView save2;
    TextView save3;
    TextView save4;
    TextView save1_voter_back_address;
    TextView save2_aadhar_back_address;
    TextView save4_passport_back_address;
    TextView savestatement;
    TextView finalsave;
    TextView savedata1;
    TextView savedata2;
    CardView edittext;
    TextView alldetails;
    EditText pannumber;
    EditText voteridnumber;
    EditText dlnumber;
    EditText dob_dl;
    EditText passportname;
    EditText passportnumber;
    EditText dobpassport;
    EditText doipassport;
    ImageView panimg;
    ImageView voterfrontimg;
    ImageView voterbackimg;
    ImageView dlimg;
    ImageView passportfrontimg;
    ImageView passportbackimg;
    ImageView voter_front_address;
    ImageView voter_back_address;
    ImageView aadhar_front_address;
    ImageView aadhar_back_address;
    ImageView dl_front_address;
    ImageView passport_front_address;
    ImageView passport_back_address;
    ImageView ITER_income_img;
    ImageView bank_income_address;
    ImageView bank_selfemployeed_address;
    ImageView ITER_selfemployeed_img;
    ImageView bank_statement_img;

    LinearLayout income_radiobutton_layout1;
    LinearLayout income_radiobutton_layout;
    RadioButton yes2;
    RadioButton no2;
    RadioButton yes1;
    RadioButton no1;
    RadioButton selfemployeed;
    RadioButton salaried;
    TextView errormsg;

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

        selfemployeed.setOnClickListener(view -> {
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
        });
        salaried.setOnClickListener(view -> {
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
        });

        yes1.setOnClickListener(view -> SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"Yes",getApplicationContext()));

        no1.setOnClickListener(view -> SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"No",getApplicationContext()));

        yes2.setOnClickListener(view -> SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"Yes",getApplicationContext()));

        no2.setOnClickListener(view -> SharedPref.saveStringInSharedPref(AppConstants.SALARIED_INCOMEVALUE,"No",getApplicationContext()));

        panimg.setOnClickListener(view -> {
            x = 1;
            showPictureDialog();
        });
        voterfrontimg.setOnClickListener(view -> {
            x = 2;
            showPictureDialog();
        });
        voterbackimg.setOnClickListener(view -> {
            if (validvoterfront == 1 && save_button_voter_id == 1) {
                x = 3;
                error2.setVisibility(View.GONE);
                showPictureDialog();
            } else {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Please upload first voter front image");
            }

        });
        dlimg.setOnClickListener(view -> {
            x = 4;
            showPictureDialog();
        });
        passportfrontimg.setOnClickListener(view -> {
            x = 5;
            showPictureDialog();
        });
        passportbackimg.setOnClickListener(view -> {
            if (validpassportfront == 1 && save_button_passport_id == 1) {
                x = 6;
                error4.setVisibility(View.GONE);
                showPictureDialog();
            } else {
                error4.setVisibility(View.VISIBLE);
                error4.setText("please upload first passport front image");
            }

        });
        voter_front_address.setOnClickListener(view -> {
            x = 7;
            showPictureDialog();
        });
        voter_back_address.setOnClickListener(view -> {
            x = 8;
            showPictureDialog();
        });
        aadhar_front_address.setOnClickListener(view -> {
            x = 9;
            showPictureDialog();
        });
        aadhar_back_address.setOnClickListener(view -> {
            x = 10;
            showPictureDialog();
        });
        dl_front_address.setOnClickListener(view -> {
            x = 11;
            showPictureDialog();
        });
        passport_front_address.setOnClickListener(view -> {
            x = 12;
            showPictureDialog();
        });
        passport_back_address.setOnClickListener(view -> {
            x = 13;
            showPictureDialog();
        });
        ITER_income_img.setOnClickListener(view -> {
            y = 1;
            choosePhotoFromGallary();
        });
        ITER_selfemployeed_img.setOnClickListener(view -> {
            y = 2;
            choosePhotoFromGallary();
        });
        bank_selfemployeed_address.setOnClickListener(view -> {
            y = 3;
            choosePhotoFromGallary();
        });
        bank_income_address.setOnClickListener(view -> {
            y = 4;
            choosePhotoFromGallary();
        });

        bank_statement_img.setOnClickListener(view -> {
            y = 5;
            choosePhotoFromGallary();
        });
        dropdown1.setOnClickListener(view -> {
            identitylayout.setVisibility(View.VISIBLE);
            dropdown1.setVisibility(View.GONE);
            dropup1.setVisibility(View.VISIBLE);
            addresslayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);


        });
        dropup1.setOnClickListener(view -> {
            identitylayout.setVisibility(View.GONE);
            dropdown1.setVisibility(View.VISIBLE);
            dropup1.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);

        });
        dropdown2.setOnClickListener(view -> {
            addresslayout.setVisibility(View.VISIBLE);
            dropdown2.setVisibility(View.GONE);
            dropup2.setVisibility(View.VISIBLE);
            identitylayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);
        });
        dropup2.setOnClickListener(view -> {
            addresslayout.setVisibility(View.GONE);
            dropdown2.setVisibility(View.VISIBLE);
            dropup2.setVisibility(View.GONE);
            identitylayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);
        });
        dropdown3.setOnClickListener(view -> {
            incomelayout.setVisibility(View.VISIBLE);
            dropdown3.setVisibility(View.GONE);
            dropup3.setVisibility(View.VISIBLE);
            identitylayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);
        });
        dropup3.setOnClickListener(view -> {
            incomelayout.setVisibility(View.GONE);
            dropdown3.setVisibility(View.VISIBLE);
            dropup3.setVisibility(View.GONE);
            identitylayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);
        });
        dropdown4.setOnClickListener(view -> {
            banklayout.setVisibility(View.VISIBLE);
            dropdown4.setVisibility(View.GONE);
            dropup4.setVisibility(View.VISIBLE);
            identitylayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            savestatement.setVisibility(View.VISIBLE);
        });
        dropup4.setOnClickListener(view -> {
            banklayout.setVisibility(View.GONE);
            dropdown4.setVisibility(View.VISIBLE);
            dropup4.setVisibility(View.GONE);
            identitylayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            incomelayout.setVisibility(View.GONE);
            savestatement.setVisibility(View.GONE);
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


        choose_identity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = choose_identity.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, proofofidentity[index], getApplicationContext());

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
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals(mVoter)) {
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
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals(mLicence)) {
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
                if (SharedPref.getStringFromSharedPref(AppConstants.IDENTITY_SPINNER_VALUE, getApplicationContext()).equals(mPassport)) {
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

                //Do nothing
            }
        });

        choose_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = choose_address.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, proofofaddress[index], getApplicationContext());

                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals(mVoter)) {
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
                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals(mLicence)) {
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
                if (SharedPref.getStringFromSharedPref(AppConstants.ADDRESS_SPINNER_VALUE, getApplicationContext()).equals(mPassport)) {
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

                //Do nothing
            }
        });

        //pan api save
        check1.setOnClickListener(view -> panapi());
        save_submit1.setOnClickListener(view -> {
            pannumber.setFocusable(false);
            MLDoc1_upload();


        });
        //voter api save
        check2.setOnClickListener(view -> voterapi());
        save_submit2.setOnClickListener(view -> {
            voteridnumber.setFocusable(false);
            MLDoc1_upload();


        });
        check3.setOnClickListener(view -> dlapi());
        save_submit3.setOnClickListener(view -> {
            dlnumber.setFocusable(false);
            MLDoc1_upload();




        });
        check4.setOnClickListener(view -> passportverificationapi());
        save_submit4.setOnClickListener(view -> {
            passportnumber.setFocusable(false);
            MLDoc1_upload();





        });
        save1.setOnClickListener(view -> {
       /*     MLAddproof_upload();*/

        });
        save2.setOnClickListener(view -> {
         /*   MLAddproof_upload();*/

        });
        save3.setOnClickListener(view -> {
  /*          MLAddproof_upload();*/

        });
        save4.setOnClickListener(view -> {
         /*   MLAddproof_upload();*/

        });
        savedata1.setOnClickListener(view -> {
         /*   MLEmpproof_upload();*/
            MLDoc3_upload();
        });
        savedata2.setOnClickListener(view -> {
          /*  MLEmpproof_upload();*/
            MLDoc3_upload();
        });
        savestatement.setOnClickListener(view -> {

              /*  MLBnkproof_upload();*/



        });

        finalsave.setOnClickListener(view -> {
            if (validation1 == 1 && validation2 == 2 ) {
                Intent it = new Intent(getApplicationContext(), Success.class);
                startActivity(it);
                errormsg.setVisibility(View.GONE);
            } else {
                errormsg.setVisibility(View.VISIBLE);
            }

        });
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            updateLabel();

        };
        DatePickerDialog.OnDateSetListener date1 = (view, year, month, day) -> {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, day);
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, day);
            updateLabel1();

        };
        DatePickerDialog.OnDateSetListener date2 = (view, year, month, day) -> {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, day);
            updateLabel2();

        };

        dob_dl.setOnClickListener(view -> new DatePickerDialog(KYC_Details.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        dobpassport.setOnClickListener(view -> new DatePickerDialog(KYC_Details.this, date1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show());
        doipassport.setOnClickListener(view -> new DatePickerDialog(KYC_Details.this, date2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show());
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(KYC_Details.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(KYC_Details.this, Home.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_FRAGMENT);
            startActivity(it);

        });

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
                        alldetails.setText(mName + response.body().getResult().getName());
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


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                        alldetails.setText(mName + response.body().getResult().getName() +
                                System.getProperty(mLineSeparator) +
                                "Relation Name: " + response.body().getResult().getRln_name() +
                                System.getProperty(mLineSeparator) +
                                "Age: " + response.body().getResult().getAge() +
                                System.getProperty(mLineSeparator) +
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


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                        alldetails.setText(mName + response.body().getResult().getName() +
                                System.getProperty(mLineSeparator) +
                                "Father/husband: " + response.body().getResult().getFatherhusband() +
                                System.getProperty(mLineSeparator) +
                                "bloodGroup: " + response.body().getResult().getBloodGroup() +
                                System.getProperty(mLineSeparator) +
                                "DOB: " + response.body().getResult().getDob() +
                                System.getProperty(mLineSeparator) +
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


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                        alldetails.setText(mName + response.body().getResult().getName().getNameFromPassport() + " " +
                                response.body().getResult().getName().getSurnameFromPassport() +
                                System.getProperty(mLineSeparator) +
                                "DispatchedOnFromSource: " + response.body().getResult().getDateOfIssue().getDispatchedOnFromSource() +
                                System.getProperty(mLineSeparator) +
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


                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<MLDoc1_upload_MODEL> call, Throwable t) {

                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<MLDoc3_upload_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();

                Toast.makeText(getApplicationContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }









    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {

                        case 0:
                            takePhotoFromCamera();
                            break;
                        default:
                            choosePhotoFromGallary();
                            break;
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
        if (resultCode == Activity.RESULT_CANCELED) {
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

        } else if (requestCode == GALLERY&&data != null) {

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
                }
        }
    }
    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));
        }
        try {
            File f = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoney");
            f.createNewFile();
             fo= new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_KYC_PROOF, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
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
        // Do nothing
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}
