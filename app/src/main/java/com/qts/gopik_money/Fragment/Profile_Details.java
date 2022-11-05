package com.qts.gopik_money.Fragment;

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
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.Edit_Profile;
import com.qts.gopik_money.Activity.Home;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Broker_profile_details_MODEL;
import com.qts.gopik_money.Model.Broker_profile_update_MODEL;
import com.qts.gopik_money.Pojo.Broker_profile_details_POJO;
import com.qts.gopik_money.Pojo.Broker_profile_update_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Details extends Fragment implements AdapterView.OnItemSelectedListener {
    String networkError = "It seems your Network is unstable . Please Try again!";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private Context mContext = getActivity();
    private static final String IMAGE_DIRECTORY = "/gopikmoney";
    private int GALLERY = 1;
     private int       CAMERA = 2;

    EditText  email ;
    EditText name1;

    Integer x = 0;
    int z = 0;
     int y = 0;
    LinearLayout layout ;
    LinearLayout st;
    ImageView search;
    TextView  policy_button;
    TextView tt;
    TextView phonenumber;
    TextView btn_countinue;
    TextView statetextview;
    TextView btn_edit;
    TextView textviewprofile;
    CustPrograssbar custPrograssbar;
    LinearLayout statespinner;
    Spinner choose_identity;
    ImageView  visible;
    Boolean flag = false;
    CircleImageView pr;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};
    FileOutputStream fo;

    public Profile_Details() {
        // Required empty public constructor
    }


    public static Profile_Details newInstance() {
        Profile_Details fragment = new Profile_Details();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile__details, container, false);


        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);

        policy_button = (TextView) view.findViewById(R.id.policy_button2);


        policy_button.setMovementMethod(LinkMovementMethod.getInstance());


        policy_button.setLinkTextColor(Color.BLACK);
        email = (EditText) view.findViewById(R.id.email);
        name1 = (EditText) view.findViewById(R.id.name1);
        pr = (CircleImageView) view.findViewById(R.id.pr);
        phonenumber = (TextView) view.findViewById(R.id.phonenumber);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        btn_edit = (TextView) view.findViewById(R.id.btn_edit);

        custPrograssbar = new CustPrograssbar();

        search = (ImageView) view.findViewById(R.id.search);
        choose_identity = (Spinner) view.findViewById(R.id.choose_identity);
        statetextview = (TextView) view.findViewById(R.id.statetextview);
        statespinner = (LinearLayout) view.findViewById(R.id.statespinner);
        textviewprofile = (TextView) view.findViewById(R.id.textviewprofile);
        st = (LinearLayout) view.findViewById(R.id.st);
        tt = (TextView) view.findViewById(R.id.tt);

        visible = (ImageView) view.findViewById(R.id.eye);
        requestMultiplePermissions();
        /*  requestMultiplePermissions();*/

        btn_countinue.setOnClickListener(view14 -> {
            statespinner.setVisibility(View.GONE);
            // broker_profile_update();
            signupvalidation();

        });
        pr.setOnClickListener(view13 -> {

        /*    z = 1;
            y = 1;
            showPictureDialog();*/
            checkPermission();
                    checkPermission_version();

         /*   if (!checkPermission()) {



                requestPermission();

            } else if (!checkPermission_version()) {

                requestPermission();
            }
           else {*/

                showPictureDialog();

                z = 1;
                y = 1;


        });
        btn_edit.setOnClickListener(view12 -> {
            Intent it = new Intent(getContext(), Edit_Profile.class);
            startActivity(it);

        });

        statetextview.setOnTouchListener((view1, motionEvent) -> {
            statespinner.setVisibility(View.VISIBLE);

            return true;
        });

        ArrayAdapter ad
                = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                statelocation);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        choose_identity.setAdapter(ad);
        choose_identity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = choose_identity.getSelectedItemPosition();

                SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER, statelocation[index], getContext());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                //Do nothing
            }
        });
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getContext());
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getContext()));
        name1.setText(SharedPref.getStringFromSharedPref(AppConstants.NAME_BROKER, getContext()));

        broker_profile_details();

        return view;


    }

    private void signupvalidation() {
        if (email.getText().toString().isEmpty()) {

            email.setError("Please Enter Valid Customer Name");

        } else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please Enter Valid Customer Email Address");
        } else {
            statespinner.setVisibility(View.GONE);
            broker_profile_update();

        }

    }

    private void broker_profile_details() {
        custPrograssbar.prograssCreate(getContext());
        Broker_profile_details_POJO pojo = new Broker_profile_details_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER, getContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_details_MODEL> call = restApis.broker_profile_details(pojo);
        call.enqueue(new Callback<Broker_profile_details_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_details_MODEL> call, Response<Broker_profile_details_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getBroker_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getBroker_email().equals("NA"))) {
                            name1.setText(response.body().getPayload().getProfile().get(0).getBroker_name());
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            email.setText(response.body().getPayload().getProfile().get(0).getBroker_email());

                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME, response.body().getPayload().getProfile().get(0).getBroker_name(), getContext());
                            name1.setText(response.body().getPayload().getProfile().get(0).getBroker_name());

                            statetextview.setText(response.body().getPayload().getProfile().get(0).getBroker_state());



                            name1.setFocusable(false);
                            email.setFocusable(false);
                            statetextview.setFocusable(false);
                            statetextview.setClickable(false);
                            statespinner.setFocusable(false);
                            statespinner.setVisibility(View.GONE);
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            textviewprofile.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void broker_profile_update() {
        custPrograssbar.prograssCreate(getContext());

        Broker_profile_update_POJO pojo = new Broker_profile_update_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()),
                        name1.getText().toString(),
                        SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext()),
                        email.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Broker_profile_update_MODEL> call = restApis.broker_profile_update(pojo);
        call.enqueue(new Callback<Broker_profile_update_MODEL>() {
            @Override
            public void onResponse(Call<Broker_profile_update_MODEL> call, Response<Broker_profile_update_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {

                        x = 1;
                        Intent it = new Intent(getContext(), Home.class);
                        startActivity(it);


                    } else {
                        Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Broker_profile_update_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void showPictureDialog() {
        android.app.AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(getActivity());
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

            if (z == 1) {

                pr.setImageBitmap(thumbnail);

            }
            saveImage(thumbnail);

        } else if (requestCode == GALLERY&&data != null) {

                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    if (y == 1) {
                        pr.setImageBitmap(bitmap);

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


        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir();


        }

        try {

            File f = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoney");

            f.createNewFile();

            fo = new FileOutputStream(f);


            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();

            SharedPref.saveStringInSharedPref(AppConstants.IMAGE_LOAN_PROFILE, f.getAbsolutePath(), getContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

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
                        Uri.fromParts("package", getActivity().getPackageName(), null));
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void requestMultiplePermissions() {


        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
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
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        //Do nothing
                    }


                }).
                withErrorListener(error -> Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Do nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        //Do nothing
    }

    private boolean checkPermission_version() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23&&!hasPermissions(mContext, PERMISSIONS)) {

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