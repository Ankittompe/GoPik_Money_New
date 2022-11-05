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
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.Edit_delaer_Profile;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.ProfileDetails_DEALER_MODEL;
import com.qts.gopik_money.Model.Profile_Update_DEALER_MODEL;
import com.qts.gopik_money.Pojo.ProfileDetails_DEALER_POJO;
import com.qts.gopik_money.Pojo.Profile_Update_DEALER_POJO;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Details_Dealer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Details_Dealer extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private Context mContext = getActivity();
    private static final String IMAGE_DIRECTORY = "/gopikmoney";
    private int GALLERY = 1;
    private int CAMERA = 2;
    FileOutputStream fo;
    EditText  email;
    EditText   name1;
    EditText   gst;

    Integer x = 0;
    int z = 0;
    int  y = 0;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LinearLayout layout;
    ImageView search;
    TextView  policy_button;
    TextView  phonenumber;
    TextView   btn_countinue;
    TextView  btn_edit;
    TextView   textviewprofile;
    CustPrograssbar custPrograssbar;

    Spinner choose_identity;
    ImageView  visible;

    CircleImageView pr;
    String mUserType;


    public Profile_Details_Dealer() {
        // Required empty public constructor
    }


    public static Profile_Details_Dealer newInstance() {
        Profile_Details_Dealer fragment = new Profile_Details_Dealer();


        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile__details__dealer, container, false);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getActivity());

        policy_button = (TextView) view.findViewById(R.id.policy_button2);


        policy_button.setMovementMethod(LinkMovementMethod.getInstance());


        policy_button.setLinkTextColor(Color.BLACK);
        email = (EditText) view.findViewById(R.id.email);
        gst = (EditText) view.findViewById(R.id.gst);
        name1 = (EditText) view.findViewById(R.id.name1);
        pr = (CircleImageView) view.findViewById(R.id.pr);
        phonenumber = (TextView) view.findViewById(R.id.phonenumber);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        btn_edit = (TextView) view.findViewById(R.id.btn_edit);

        custPrograssbar = new CustPrograssbar();

        search = (ImageView) view.findViewById(R.id.search);
        choose_identity = (Spinner) view.findViewById(R.id.choose_identity);

        textviewprofile = (TextView) view.findViewById(R.id.textviewprofile);
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getContext());

        visible = (ImageView) view.findViewById(R.id.eye);
        requestMultiplePermissions();
        /*  requestMultiplePermissions();*/

        btn_countinue.setOnClickListener(view1 -> {

            // broker_profile_update();
            signupvalidation();

        });
        pr.setOnClickListener(view12 -> {
           /* if (!checkPermission()) {
                requestPermission();
            }
            if (!checkPermission_version()) {
                requestPermission();
            }
            else{*/
            checkPermission();
            checkPermission_version();
                showPictureDialog();
                z=1;
                y=1;

        });

        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.DEALER_MOBILE_NUMBER, getContext()));
        profile_details();
        btn_edit.setOnClickListener(view13 -> {
            Intent it = new Intent(getContext(), Edit_delaer_Profile.class);
            startActivity(it);

        });
        return view;


    }

    private void signupvalidation() {
        if (name1.getText().toString().isEmpty() && email.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter above details", Toast.LENGTH_LONG).show();
        } else if (name1.getText().toString().isEmpty()) {

            name1.setError("Please Enter Valid Customer Name");

        } else if (email.getText().toString().isEmpty()) {

            email.setError("Please Enter Valid Customer Name");

        }else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please Enter Valid Customer Email Address");
        }else{
            profile_update();
        }

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

                        //Nothing to Do
                    }


                }).
                withErrorListener(error -> {
                    //Do nothing
                })
                .onSameThread()
                .check();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Nothing to do
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        //Do nothing
    }

    private void profile_update() {
        custPrograssbar.prograssCreate(getContext());

        Profile_Update_DEALER_POJO pojo = new Profile_Update_DEALER_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()),
                        name1.getText().toString(),
                        email.getText().toString(),
                        gst.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Profile_Update_DEALER_MODEL> call ;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealerprofileupdate(pojo);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.profile_update(pojo);
        } else {
            call = restApis.profile_update(pojo);
        }

        call.enqueue(new Callback<Profile_Update_DEALER_MODEL>() {
            @Override
            public void onResponse(Call<Profile_Update_DEALER_MODEL> call, Response<Profile_Update_DEALER_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode() == 200) {

                        x = 1;
                        Intent it = new Intent(getContext(), MainActivity.class);
                        startActivity(it);


                    } else {
                        Toast.makeText(getContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Profile_Update_DEALER_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
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


                    if (response.body().getCode()==200) {
                        name1.setText(response.body().getPayload().getProfile().get(0).getName());
                        SharedPref.saveStringInSharedPref(AppConstants.DEALER_NAME, response.body().getPayload().getProfile().get(0).getName(), getContext());
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getGst_no().equals("NA"))) {
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();

                            email.setText(response.body().getPayload().getProfile().get(0).getEmail());
                            Log.e("hhghghhuu", "bfvn");


                            gst.setText(response.body().getPayload().getProfile().get(0).getGst_no());



                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext());
                            /*      int state_position=statelocation.get*/
                            SharedPref.saveStringInSharedPref(AppConstants.MOBILE_NUMBER, phonenumber.getText().toString(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getUser_code(), getContext());

                            SharedPref.saveStringInSharedPref(AppConstants.NAME_SUBUSER, response.body().getPayload().getProfile().get(0).getName(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.DEALER_EMAIL, response.body().getPayload().getProfile().get(0).getEmail(),getContext());

                            name1.setFocusable(false);

                            email.setFocusable(false);

                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            textviewprofile.setVisibility(View.GONE);
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
    private boolean checkPermission_version() {
        Log.e("jcdbc","ccnds");
        int read_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("jcdbc","hfthth");
            if (!hasPermissions(mContext, PERMISSIONS)) {
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

