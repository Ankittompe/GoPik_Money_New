package com.qts.gopik_money.Shopkeeper;

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
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Model.Shopkeeper_profile_details_MODEL;
import com.qts.gopik_money.Model.Shopkeeper_profile_update_MODEL;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_details_POJO;
import com.qts.gopik_money.Pojo.Shopkeeper_profile_update_POJO;
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
 * Use the {@link Profile_Details_Shopkeeper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Details_Shopkeeper extends Fragment implements AdapterView.OnItemSelectedListener {
    private final int PERMISSION_REQUEST_CODE = 1000;
    private Context mContext = getActivity();
    private static final String IMAGE_DIRECTORY = "/gopikmoney";
    private int GALLERY = 1 ;
    private int CAMERA = 2;
    String networkError = "It seems your Network is unstable . Please Try again!";

    EditText  email ;
    EditText name1;
    EditText shopname;
    EditText shopaddress;

    Integer x = 0;
    int z = 0 ;
    int y = 0;
    LinearLayout layout;
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
    private static int TIME_OUT = 3000;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] statelocation = {"Select State", "Odisha", "Arunachal Pradesh", "Assam", "Bihar",
            "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal", "Andhra Pradesh"};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile_Details_Shopkeeper() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Details_Shopkeeper.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Details_Shopkeeper newInstance(String param1, String param2) {
        Profile_Details_Shopkeeper fragment = new Profile_Details_Shopkeeper();
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
        View view = inflater.inflate(R.layout.fragment_profile__details__shopkeeper, container, false);
        btn_countinue = (TextView) view.findViewById(R.id.btn_countinue);

        policy_button = (TextView) view.findViewById(R.id.policy_button2);


        policy_button.setMovementMethod(LinkMovementMethod.getInstance());


        policy_button.setLinkTextColor(Color.BLACK);
        email = (EditText) view.findViewById(R.id.email);
        name1 = (EditText) view.findViewById(R.id.name1);
        pr = (CircleImageView) view.findViewById(R.id.pr);
        phonenumber = (TextView) view.findViewById(R.id.phonenumber);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        btn_edit = (TextView) view.findViewById(R.id.btn_editt);
        custPrograssbar = new CustPrograssbar();
        shopaddress = (EditText) view.findViewById(R.id.shopaddress);
        shopname = (EditText) view.findViewById(R.id.shopname);
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

        btn_countinue.setOnClickListener(view1 -> {
            statespinner.setVisibility(View.GONE);
            // broker_profile_update();
            signupvalidation();

        });
        pr.setOnClickListener(view14 -> {

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
            Intent it = new Intent(getContext(), EditShopkeeperProfile.class);
            startActivity(it);

        });

        statetextview.setOnTouchListener((view13, motionEvent) -> {
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
                /*       SharedPref.saveStringInSharedPref(AppConstants.STATE_SPINNER_ITEM,statelocation,getContext());*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "2", getContext());
        phonenumber.setText(SharedPref.getStringFromSharedPref(AppConstants.MOBILE_NUMBER, getContext()));
        name1.setText(SharedPref.getStringFromSharedPref(AppConstants.NAME_BROKER, getContext()));

        shopkeeper_profile_details();

        return view;


    }

    private void signupvalidation() {
        if (email.getText().toString().isEmpty()) {

            email.setError("Please Enter Valid Customer Name");

        } else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please Enter Valid Customer Email Address");
        } else {
            statespinner.setVisibility(View.GONE);
            shopkeeper_profile_update();

        }

    }

    private void shopkeeper_profile_details() {
        custPrograssbar.prograssCreate(getContext());
        Shopkeeper_profile_details_POJO pojo = new Shopkeeper_profile_details_POJO(SharedPref.getStringFromSharedPref(
                AppConstants.MOBILE_NUMBER, getContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TOKEN, getContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_details_MODEL> call = restApis.shopkeeper_profile_details(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_details_MODEL>() {

            @Override
            public void onResponse(Call<Shopkeeper_profile_details_MODEL> call, Response<Shopkeeper_profile_details_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getProfile().get(0).getShopkeeper_code(), getContext());
                        if ((response.body().getPayload().getProfile().get(0).getShopkeeper_email().equals("NA"))) {
                            name1.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_name());
                            SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_NAME, response.body().getPayload().getProfile().get(0).getShopkeeper_name(), getContext());
                            custPrograssbar.closePrograssBar();
                        } else {
                            custPrograssbar.closePrograssBar();
                            email.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_email());

                            SharedPref.saveStringInSharedPref(AppConstants.SHOPKEEPER_NAME, response.body().getPayload().getProfile().get(0).getShopkeeper_name(), getContext());
                            SharedPref.saveStringInSharedPref(AppConstants.CONTEST_NAME, response.body().getPayload().getProfile().get(0).getShopkeeper_name(), getContext());
                            name1.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_name());
                            shopname.setText(response.body().getPayload().getProfile().get(0).getShop_name());
                            shopaddress.setText(response.body().getPayload().getProfile().get(0).getShop_address());
                            statetextview.setText(response.body().getPayload().getProfile().get(0).getShopkeeper_state());

                            String state_index = SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext());


                            name1.setFocusable(false);
                            email.setFocusable(false);
                            statetextview.setFocusable(false);
                            statetextview.setClickable(false);
                            statespinner.setFocusable(false);
                            shopaddress.setFocusable(false);
                            shopname.setFocusable(false);
                            statespinner.setVisibility(View.GONE);
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_countinue.setVisibility(View.GONE);
                            textviewprofile.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<Shopkeeper_profile_details_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void shopkeeper_profile_update() {
        custPrograssbar.prograssCreate(getContext());

        Shopkeeper_profile_update_POJO pojo = new Shopkeeper_profile_update_POJO
                (SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getActivity()),
                        name1.getText().toString(),
                        SharedPref.getStringFromSharedPref(AppConstants.STATE_SPINNER, getContext()),
                        email.getText().toString(), shopname.getText().toString(),
                        shopaddress.getText().toString());
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Shopkeeper_profile_update_MODEL> call = restApis.shopkeeper_profile_update(pojo);
        call.enqueue(new Callback<Shopkeeper_profile_update_MODEL>() {
            @Override
            public void onResponse(Call<Shopkeeper_profile_update_MODEL> call, Response<Shopkeeper_profile_update_MODEL> response) {
                if (response.body() != null) {


                    if (response.body().getCode().equals("200")) {
                        x = 1;
                        Intent it = new Intent(getContext(), HomeShopkeeper.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.SHOPKEEPER_MYMALL_FRAGMENT);

                        startActivity(it);

                    } else {

                        Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();


                    }


                }
            }

            @Override
            public void onFailure(Call<Shopkeeper_profile_update_MODEL> call, Throwable t) {


                Toast.makeText(getContext(), networkError, Toast.LENGTH_LONG).show();
            }

        });
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                        default:
                           //Do Something
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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

        } else if (requestCode == GALLERY &&data != null) {
           // if (data != null) {
                Uri contentURI = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    if (y == 1) {
                        pr.setImageBitmap(bitmap);

                    }
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }

           // }
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

            FileOutputStream fo = new FileOutputStream(f);


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

                    }


                }).
                withErrorListener(error -> {


                    // Toast.makeText(getContext(), networkError, Toast.LENGTH_SHORT).show();
                })
                .onSameThread()
                .check();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean checkPermission_version() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
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