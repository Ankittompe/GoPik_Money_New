package com.qts.gopik_loan.Dealer_Fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Model.Bank_Upload_MODEL;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supply_Chain.Image_Upload_SupplyChain;
import com.qts.gopik_loan.Supply_Chain.PO_Get_Modified_List;
import com.qts.gopik_loan.Supply_Chain.PO_TOP_FIVE_Activity;
import com.qts.gopik_loan.Supply_Chain.PersonalDetails_Activity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link My_Mall_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class My_Mall_Fragment extends Fragment implements PickiTCallbacks {

    ImageView upload_document;
    private final int PERMISSION_REQUEST_CODE = 1000;

    ImageView po_button;
    ConstraintLayout po_layout;

    PickiT pickiT;
    private static final int REQUEST = 112;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public My_Mall_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment My_Mall_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static My_Mall_Fragment newInstance(String param1, String param2) {
        My_Mall_Fragment fragment = new My_Mall_Fragment();
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
        pickiT = new PickiT(getActivity(), this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my__mall_, container, false);
        View v = inflater.inflate(R.layout.fragment_my__mall_, container, false);
        upload_document= (ImageView)v.findViewById(R.id.upload_document);
        po_button= (ImageView)v.findViewById(R.id.po_button);
        po_layout= (ConstraintLayout) v.findViewById(R.id.po_layout);

        po_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent po_intent = new Intent(getActivity(), PO_TOP_FIVE_Activity.class);
                startActivity(po_intent);
            }
        });

        po_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent po_intent = new Intent(getActivity(), PO_TOP_FIVE_Activity.class);
                startActivity(po_intent);
            }
        });

   upload_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doc_upload = new Intent(getActivity(), PersonalDetails_Activity.class);
                startActivity(doc_upload);
            }
        });

     /* upload_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDFSelector();
            }
        });
*/

      /*  if (!checkPermission()) {
            requestPermission();
        }
        if (!checkPermission_version()) {
            requestPermission();
        }*/
        return v;
    }
    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Upload_Bank_Document_Details(File myPdfFile) {

        String mUserCode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getContext());
        RequestBody mCode = RequestBody.create(MediaType.parse("multipart/form-data"), mUserCode);
        RequestBody mFile1;
        MultipartBody.Part mPdfDocUpload;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), myPdfFile);
        mPdfDocUpload = MultipartBody.Part.createFormData("bankimage", myPdfFile.getName(), mFile1);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Bank_Upload_MODEL> call = restApis.Dealer_Bank_File_Upload(mCode, mPdfDocUpload);
        call.enqueue(new Callback<Bank_Upload_MODEL>() {
            @Override
            public void
            onResponse(Call<Bank_Upload_MODEL> call, Response<Bank_Upload_MODEL> response) {
                if (response.body() != null) {

                    Log.e("TAG", "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getCode() == 200) {
                        Toast.makeText(requireActivity(), "Bank document uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void
            onFailure(Call<Bank_Upload_MODEL> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission() {

        int read_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager() && camera_permission == PackageManager.PERMISSION_GRANTED;
        }  else {
            return read_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && write_external_storage_permission == PackageManager.PERMISSION_GRANTED
                    && camera_permission == PackageManager.PERMISSION_GRANTED;

        }


    }
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            Log.e("jcdbc","bbbbbb");

            try {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getContext().getPackageName(), null));
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
    private boolean checkPermission_version() {
        Log.e("jcdbc","ccnds");
        int read_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int write_external_storage_permission = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int camera_permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            Log.e("jcdbc","hfthth");
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

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }
    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        Log.e("pickiT ", path);
        File mFile = new File(path);
        Upload_Bank_Document_Details(mFile);

    }
}