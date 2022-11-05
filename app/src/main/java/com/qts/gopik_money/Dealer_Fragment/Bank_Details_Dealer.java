package com.qts.gopik_money.Dealer_Fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.Bankacc_verification_MODEL;
import com.qts.gopik_money.Model.Dealer_bank_update_MODEL;
import com.qts.gopik_money.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_money.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_money.Pojo.ProfileDetails_DEALER_POJO;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bank_Details_Dealer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bank_Details_Dealer extends Fragment implements TextWatcher, AdapterView.OnItemSelectedListener {
    String networkError = "It seems your Network is unstable . Please Try again!";
    String mMultipartFormData = "multipart/form-data";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final String IMAGE_DIRECTORY = "/gopikbank";
    private int GALLERY = 1;
    private int  CAMERA = 2;
    FileOutputStream fo;
    EditText ifsccode;
    EditText  accountno;
    EditText  reaccountno;
    EditText  branch;
    EditText  accountholdername;


    Integer y = 0;
    Integer  z = 0;

    LinearLayout layout ;
    LinearLayout reacc;
    LinearLayout st;
    ImageView search;
    ImageView  checkimg;
    TextView bankname;
    TextView  policy_button;
    TextView  address;
    TextView  btn_countinue;
    TextView  validaccountno;
    TextView  btn_edit;
    CustPrograssbar custPrograssbar;
    ImageView  visible;
    String mUserType;




    public Bank_Details_Dealer() {
        // Required empty public constructor
    }



    public static Bank_Details_Dealer newInstance() {
        Bank_Details_Dealer fragment = new Bank_Details_Dealer();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank__details__dealer, container, false);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getActivity());
        branch = (EditText) view.findViewById(R.id.branch);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);
        address = (TextView) view.findViewById(R.id.address);
        bankname = (TextView) view.findViewById(R.id.bankname);
        ifsccode = (EditText) view.findViewById(R.id.ifsccode);
        accountno = (EditText) view.findViewById(R.id.accountno);
        checkimg = (ImageView) view.findViewById(R.id.checkimg);
        reacc = (LinearLayout) view.findViewById(R.id.reacc);

        layout = (LinearLayout) view.findViewById(R.id.layout);
        btn_edit = (TextView) view.findViewById(R.id.btn_edit);
        reaccountno = (EditText) view.findViewById(R.id.reaccountno);
        accountholdername = (EditText) view.findViewById(R.id.accountholdername);
        custPrograssbar = new CustPrograssbar();
        reaccountno.addTextChangedListener(this);
        ifsccode.addTextChangedListener(this);
        search = (ImageView) view.findViewById(R.id.search);
        visible = (ImageView) view.findViewById(R.id.eye);
        validaccountno = (TextView) view.findViewById(R.id.validaccountno);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);

        policy_button = (TextView) view.findViewById(R.id.policy_button2);


        policy_button.setLinkTextColor(Color.BLACK);
        policy_button.setMovementMethod(LinkMovementMethod.getInstance());
        visible.setOnTouchListener((v, event) -> {

            if(event.getAction() == MotionEvent.ACTION_DOWN) {

                accountno.setTransformationMethod(null);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                accountno.setTransformationMethod(new PasswordTransformationMethod());
            }
            return true;
        });
        btn_countinue.setOnClickListener(view1 -> {


            signupvalidation();

        });
        checkimg.setOnClickListener(v -> {
            if (!checkPermission()) {
                requestPermission();
            }
            if (!checkPermission_version()) {
                requestPermission();
            }
            z = 1;
            y = 1;
            showPictureDialog();
        });

        profile_details();
        return view;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    if (which == 0) {
                        choosePhotoFromGallary();
                    } else if (which == 1) {
                        takePhotoFromCamera();
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
                            MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                    contentURI);
                    if (y == 1) {
                        checkimg.setImageBitmap(bitmap);

                    }
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();


                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (z == 1) {

                checkimg.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }


        }

    }

    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);

        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir();


        }

        try {

            File f = new
                    File(getActivity().getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikbank");

            f.createNewFile();

            fo = new FileOutputStream(f);


            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();


            SharedPref.saveStringInSharedPref(AppConstants.BANK_CHEQUE_IMG, f.getAbsolutePath(), getActivity());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void signupvalidation() {
        if (accountno.getText().toString().isEmpty()
                && reaccountno.getText().toString().isEmpty()
                && accountholdername.getText().toString().isEmpty() && ifsccode.getText().toString().isEmpty()
                && branch.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter above details", Toast.LENGTH_LONG).show();
        } else if (accountno.getText().toString().isEmpty()) {
            accountno.setError("Please Enter Valid Account Number");

        } else if (reaccountno.getText().toString().isEmpty()) {
            reaccountno.setError("Please Enter Valid Re-Account Number");

        } else if (accountholdername.getText().toString().isEmpty()) {
            accountholdername.setError("Please Enter Valid Account Holder Name");

        } else if (ifsccode.getText().toString().isEmpty()) {
            ifsccode.setError("Please Enter Valid IFSC Code");

        } else if (branch.getText().toString().isEmpty()) {
            branch.setError("Please Enter Valid Branch");

        } else {

            bankacc_verification();

        }

    }

    private void profile_details() {
        custPrograssbar.prograssCreate(getContext());
        ProfileDetails_DEALER_POJO pojo = new ProfileDetails_DEALER_POJO(SharedPref.getStringFromSharedPref(AppConstants.PHONENUMBER, getActivity()), SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getActivity()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<ProfileDetails_DEALER_MODEL> call ;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealer_profile_details(pojo);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.profile_details(pojo);
        } else {
            call = restApis.profile_details(pojo);
        }
        call.enqueue(new Callback<ProfileDetails_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<ProfileDetails_DEALER_MODEL> call, Response<ProfileDetails_DEALER_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode() == 200) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getAcc_holder().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            accountholdername.setText(response.body().getPayload().getProfile().get(0).getAcc_holder());
                            accountno.setText(response.body().getPayload().getProfile().get(0).getAcc_no());
                            branch.setText(response.body().getPayload().getProfile().get(0).getBranch());
                            ifsccode.setText(response.body().getPayload().getProfile().get(0).getIfsc());

                            reacc.setVisibility(View.GONE);

                            SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());


                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);

                            reaccountno.setVisibility(View.GONE);
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            accountno.setFocusable(false);
                            accountholdername.setFocusable(false);
                            ifsccode.setFocusable(false);
                            branch.setFocusable(false);

                        }

                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }

            }


            @Override
            public void onFailure(Call<ProfileDetails_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });


    }

    private void bankacc_verification() {
        custPrograssbar.prograssCreate(getContext());
        Bankacc_verification_POJO pojo = new Bankacc_verification_POJO(accountno.getText().toString(), accountholdername
                .getText().toString(), ifsccode.getText().toString(), "y", "Individual");
        RestApis restApis = NetworkHandler.instanceMaker6().create(RestApis.class);

       Call<Bankacc_verification_MODEL> call = restApis.bankacc_verification(pojo);
        call.enqueue(new Callback<Bankacc_verification_MODEL>() {
            @Override
            public void onResponse(Call<Bankacc_verification_MODEL> call, Response<Bankacc_verification_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();


                    if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC01")) {
                        dealerbank_Details_Update();
                        Toast.makeText(getContext(), "Transaction Successful", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC02")) {
                        Toast.makeText(getContext(), "Credit Transaction Failed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC03")) {
                        Toast.makeText(getContext(), "Invalid Beneficiary Account Number or IFSC", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC04")) {
                        Toast.makeText(getContext(), "Amount Limit Exceeded", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC05")) {
                        Toast.makeText(getContext(), "Account Blocked/Frozen", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC06")) {
                        Toast.makeText(getContext(), "NRE Account", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC07")) {
                        Toast.makeText(getContext(), "Account Closed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC08")) {
                        Toast.makeText(getContext(), "Limit Exceeded For Member Bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC09")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC10")) {
                        Toast.makeText(getContext(), "Function Not Valid", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC11")) {
                        Toast.makeText(getContext(), "Aadhaar Belong To Remitter Bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC12")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC13")) {
                        Toast.makeText(getContext(), "Customer Transaction Limit Exceeded", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC14")) {
                        Toast.makeText(getContext(), "Payee Is An Individual And Not A Merchant", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC15")) {
                        Toast.makeText(getContext(), "Payee Is A Merchant And Not An Individual", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC16")) {
                        Toast.makeText(getContext(), "Foreign Inward Remittance Not Allowed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC17")) {
                        Toast.makeText(getContext(), "Transaction Not Allowed As Invalid Payment Reference", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC18")) {
                        Toast.makeText(getContext(), "Invalid Amount", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC19")) {
                        Toast.makeText(getContext(), "Invalid Remitter Account Number", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC20")) {
                        Toast.makeText(getContext(), "General Error", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC21")) {
                        Toast.makeText(getContext(), "Invalid Transaction Type", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC22")) {
                        Toast.makeText(getContext(), "Invalid Amount Field", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC23")) {
                        Toast.makeText(getContext(), "IMPS Service not available for the selected bank", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC24")) {
                        Toast.makeText(getContext(), "Duplicate Transaction", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC25")) {
                        Toast.makeText(getContext(), "Beneficiary Bank Not Enable For P2a", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC26")) {
                        Toast.makeText(getContext(), "Insufficient Balance In Pool A/C", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC27")) {
                        Toast.makeText(getContext(), "Invalid Account", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC28")) {
                        Toast.makeText(getContext(), "Invalid Response Code", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC29")) {
                        Toast.makeText(getContext(), "Exceeds Account Limit", Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().getData().getSource().get(0).getData().getStatusCode().equals("KC30")) {
                        Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<Bankacc_verification_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validaccountno.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String account = accountno.getText().toString();
        String reaccount = reaccountno.getText().toString();
        if (account.equals(reaccount)) {
            validaccountno.setVisibility(View.GONE);


        }
        if (accountno.length() == 0) {
            validaccountno.setVisibility(View.GONE);

        }
        if (reaccount.length() == 0) {
            validaccountno.setVisibility(View.GONE);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Do nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
       //Do nothing
    }

    private void dealerbank_Details_Update() {
        custPrograssbar.prograssCreate(getContext());

        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,
                        getContext());

        String macc_no = accountno.getText().toString();
        String maccountholder_name = accountholdername.getText().toString();
        String mifsc_code = ifsccode.getText().toString();
        String mbranch_name = branch.getText().toString();

        RequestBody user_code = RequestBody.create(MediaType.parse(mMultipartFormData), mcust_code);
        RequestBody acc_no = RequestBody.create(MediaType.parse(mMultipartFormData), macc_no);
        RequestBody acc_holdr_name = RequestBody.create(MediaType.parse(mMultipartFormData), maccountholder_name);
        RequestBody ifsc = RequestBody.create(MediaType.parse(mMultipartFormData), mifsc_code);
        RequestBody branch = RequestBody.create(MediaType.parse(mMultipartFormData), mbranch_name);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.BANK_CHEQUE_IMG,
                getContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse(mMultipartFormData), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("chackimage",
                idFile.getName(), mFile1);



        RestApis restApis =
                NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_bank_update_MODEL> call ;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealerbankdetailupdate(user_code, acc_no,
                    acc_holdr_name, ifsc, branch, vechileDocUpload2);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.dealerbank_Details_Update(user_code, acc_no,
                    acc_holdr_name, ifsc, branch, vechileDocUpload2);
        } else {
            call = restApis.dealerbank_Details_Update(user_code, acc_no,
                    acc_holdr_name, ifsc, branch, vechileDocUpload2);
        }

        call.enqueue(new Callback<Dealer_bank_update_MODEL>() {
            @Override
            public void
            onResponse(Call<Dealer_bank_update_MODEL> call,
                       Response<Dealer_bank_update_MODEL> response) {
                custPrograssbar.closePrograssBar();
                if (response.body() != null) {


                    Intent it = new Intent(getContext(), MainActivity.class);
                    startActivity(it);
                }


            }

            @Override
            public void
            onFailure(Call<Dealer_bank_update_MODEL> call, Throwable t) {
                Log.e("testingggg", "testingggg10000" + t);
                custPrograssbar.closePrograssBar();
                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });

    }


    private boolean checkPermission() {

        int read_external_storage_permission =
                ContextCompat.checkSelfPermission(getContext(),
                        READ_EXTERNAL_STORAGE);
        int write_external_storage_permission =
                ContextCompat.checkSelfPermission(getContext(),
                        WRITE_EXTERNAL_STORAGE);
        int camera_permission =
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager() &&
                    camera_permission == PackageManager.PERMISSION_GRANTED;
        } else {
            return read_external_storage_permission ==
                    PackageManager.PERMISSION_GRANTED
                    && write_external_storage_permission ==
                    PackageManager.PERMISSION_GRANTED
                    && camera_permission ==
                    PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            try {
                Intent intent = new
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getContext().getPackageName(),
                                null));
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
            ActivityCompat.requestPermissions(getActivity(), new
                    String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission_version() {
        Log.e("jcdbc", "ccnds");
        int read_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23&&!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);



        }
        return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                && camera_permission == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}

