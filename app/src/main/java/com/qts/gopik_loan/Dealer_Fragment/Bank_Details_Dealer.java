package com.qts.gopik_loan.Dealer_Fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Spinner;
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
import com.qts.gopik_loan.Activity.Home;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.ImageCapture;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Activity.Voter_ID_CARD_Details;
import com.qts.gopik_loan.Model.Bankacc_verification_MODEL;
import com.qts.gopik_loan.Model.Broker_bank_details_update_MODEL;
import com.qts.gopik_loan.Model.Dealer_bank_update_MODEL;
import com.qts.gopik_loan.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_loan.Model.Store_Bank_Document_Details_MODEL;
import com.qts.gopik_loan.Pojo.Bankacc_verification_POJO;
import com.qts.gopik_loan.Pojo.Broker_bank_details_update_POJO;
import com.qts.gopik_loan.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    private final int PERMISSION_REQUEST_CODE = 1000;
    private static final String IMAGE_DIRECTORY = "/gopikbank";
    private int GALLERY = 1, CAMERA = 2;
    EditText ifsccode, accountno, email, name1, reaccountno, branch, accountholdername;
    String acc;
    Integer x = 0;
    Integer y = 0, z = 0;
    LinearLayout layout, reacc, st;
    ImageView search, checkimg;
    TextView bankname, policy_button, tt, phonenumber, address, btn_countinue, statetextview, validaccountno, btn_edit, textviewprofile;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView visibleoff, visible, eye2;
    Boolean flag = false;
    private static int TIME_OUT = 3000;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bank_Details_Dealer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bank_Details_Dealer.
     */
    // TODO: Rename and change types and number of parameters
    public static Bank_Details_Dealer newInstance(String param1, String param2) {
        Bank_Details_Dealer fragment = new Bank_Details_Dealer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank__details__dealer, container, false);
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
        visible.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("hhghghhuu", "apppppppkkkkkk");
                    accountno.setTransformationMethod(null);
                } else {
                    Log.e("hhghghhuu", "apppppppkkkkkkgg");
                    accountno.setTransformationMethod(new PasswordTransformationMethod());
                }

                return true;
            }

        });
        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*  broker_profile_update();*/
                signupvalidation();

            }
        });
        checkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                z = 1;
                y = 1;
                showPictureDialog();
            }
        });
        if (!checkPermission()) {
            requestPermission();
        }
        if (!checkPermission_version()) {
            requestPermission();
        }
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
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int
                            which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
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
        if (resultCode == getActivity().RESULT_CANCELED) {
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

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


            if (z == 1) {

                checkimg.setImageBitmap(thumbnail);
                saveImage(thumbnail);
            }

            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }

    }

    public String saveImage(Bitmap myBitmap) {
        Log.e("TAG", "File Saved1");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Log.e("TAG", "File Saved2");
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
                    File(getActivity().getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikbank");
            Log.e("TAG", "File Saved8");
            f.createNewFile();
            Log.e("TAG", "File Saved9");
            FileOutputStream fo = new FileOutputStream(f);
            Log.e("TAG", "File Saved10");

            fo.write(bytes.toByteArray());
            Log.e("TAG", "File Saved11");
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            Log.e("TAG", "File Saved12");
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

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
        Call<ProfileDetails_DEALER_MODEL> call = restApis.profile_details(pojo);
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
                            Log.e("hhghghhuu", "bfvn");

                            reacc.setVisibility(View.GONE);

                            SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());


                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);

                        }

                    } else {
                        Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }

            }


            @Override
            public void onFailure(Call<ProfileDetails_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
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
                Log.e("hhghghhuu", "apppppppkkkkkk" + t);
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("hhghghhuu", "6666");
        validaccountno.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.e("hhghghhuu", "9999");
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void dealerbank_Details_Update() {
        custPrograssbar.prograssCreate(getContext());

        String mcust_code =
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE,
                        getContext());
        Log.e("testingggg", "testingggg99999" + SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE_HOME_LOAN,
                getContext()));
        String macc_no = accountno.getText().toString();
        String maccountholder_name = accountholdername.getText().toString();
        String mifsc_code = ifsccode.getText().toString();
        String mbranch_name = branch.getText().toString();

        RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), mcust_code);
        RequestBody acc_no = RequestBody.create(MediaType.parse("multipart/form-data"), macc_no);
        RequestBody acc_holdr_name = RequestBody.create(MediaType.parse("multipart/form-data"), maccountholder_name);
        RequestBody ifsc = RequestBody.create(MediaType.parse("multipart/form-data"), mifsc_code);
        RequestBody branch = RequestBody.create(MediaType.parse("multipart/form-data"), mbranch_name);


        File idFile = new
                File(SharedPref.getStringFromSharedPref(AppConstants.BANK_CHEQUE_IMG,
                getContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("chackimage",
                idFile.getName(), mFile1);


        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis =
                NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Dealer_bank_update_MODEL> call = restApis.dealerbank_Details_Update(user_code, acc_no,
                acc_holdr_name, ifsc, branch, vechileDocUpload2);
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
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void requestMultiplePermissions() {


        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void
                    onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void
                    onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                       PermissionToken token) {

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {


                        // Toast.makeText(getContext(), "Some Error! ",Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

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
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("jcdbc", "hfthth");
            if (!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
}

