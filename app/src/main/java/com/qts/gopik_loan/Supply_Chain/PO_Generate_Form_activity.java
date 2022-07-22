package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;

import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Dealer_Adapter.PO_Form_Adapter;
import com.qts.gopik_loan.Model.GetCatproductModel;
import com.qts.gopik_loan.Model.Po_add_MODEL;
import com.qts.gopik_loan.Model.Po_img_add_MODEL;
import com.qts.gopik_loan.Model.Po_products_Model;
import com.qts.gopik_loan.Model.dealer_doc_confirm_MODEL;
import com.qts.gopik_loan.Model.podisverslreportupdate_MODEL;
import com.qts.gopik_loan.Pojo.Dealer_doc_confirm_POJO;
import com.qts.gopik_loan.Pojo.GetCatproductPojo;
import com.qts.gopik_loan.Pojo.Po_add_POJO;
import com.qts.gopik_loan.Pojo.Po_products_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.ItemClickListener;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.Po_Get_List_Adapter;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_Generate_Form_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Po_Get_List_Adapter po_get_list_adapter;
    ItemClickListener itemClickListener;
    RecyclerView po_form_recyclerview;
    PO_Form_Adapter po_form_adapter;
    Spinner po_spinner;
    ImageView invoicefile_upload_bttn;
    ImageView CameraButton, GalleryButton;
    private int GALLERY = 1, CAMERA = 2;
    public int x = 0, y = 0;
    CustPrograssbar custPrograssbar;
    private static final String IMAGE_DIRECTORY = "/supplychain";
    ArrayList<String> category_name = new ArrayList();
    ArrayList<PO_Product> mProductArrayList = new ArrayList();
    ArrayList<PO_Product> mSelectedProductArrayList = new ArrayList();
    PO_Product po_product;
    ArrayList<PO_Product> p = new ArrayList<PO_Product>();
    ArrayList<String> PO_List = new ArrayList<>();
    TextView quantity_number, add_bttn, current_date2, current_date, confirm_button, mBtnTotalPrice, Ok_button;
    ImageView qty_decrease_button, qty_increase_button;

    ImageView arrow, hometoolbar;
    String currentDateTimeString;
    int quantity_count = 0;

    String mSpinnerName = "";
    String mSpinnerPrice = "0";
    String format;
    private Dialog dialogCondition;
    TextView invoice_upload_bttn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_form);

        invoicefile_upload_bttn = findViewById(R.id.invoicefile_upload_bttn);
        invoice_upload_bttn2 = findViewById(R.id.invoice_upload_bttn2);

        dialogCondition = new Dialog(PO_Generate_Form_activity.this);


        custPrograssbar = new CustPrograssbar();
        po_form_recyclerview = findViewById(R.id.po_form_recyclerview);
        po_spinner = findViewById(R.id.po_spinner);
        quantity_number = findViewById(R.id.quantity_number);
        add_bttn = findViewById(R.id.add_bttn);
        qty_decrease_button = findViewById(R.id.qty_decrease_button);
        qty_increase_button = findViewById(R.id.qty_increase_button);
        current_date2 = findViewById(R.id.current_date2);
        confirm_button = findViewById(R.id.confirm_button);
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        mBtnTotalPrice = findViewById(R.id.btnTotalPrice);
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format = s.format(new Date());
        current_date = findViewById(R.id.current_date);
        Log.e("Selected ", format);
        currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());


        current_date.setText(format);
        current_date2.setText(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));

        //Changes by 20/07/2022 Am**
        invoicefile_upload_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
            }
        });
        invoice_upload_bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageDailog();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_Generate_Form_activity.this, PO_TOP_FIVE_Activity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME_PO_GENARETE_ACTIVITY);
                startActivity(it);

            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PO_Generate_Form_activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
                startActivity(it);

            }
        });


        quantity_number.setText(String.valueOf(quantity_count));

        po_form_recyclerview.setVisibility(View.GONE);

        qty_increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_count = quantity_count + 1;
                quantity_number.setText(String.valueOf(quantity_count));
            }
        });
        qty_decrease_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(quantity_count).equals("0")) {

                } else {
                    quantity_count = quantity_count - 1;
                    quantity_number.setText(String.valueOf(quantity_count));
                }

            }
        });
        itemClickListener = (position, mPoProduct) -> {
            mSelectedProductArrayList.remove(position);

            po_get_list_adapter.notifyItemRemoved(position);


           /* Toast.makeText(getApplicationContext(), "Position : "
                    + position + " || Value : " + mPoProduct.getQuantity(), Toast.LENGTH_SHORT).show();*/

        };

        po_get_list_adapter = new Po_Get_List_Adapter(getApplicationContext(), p, itemClickListener);
        po_products();
        //   getSpinnerData();
        GetFormAdapter();
        po_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = po_spinner.getSelectedItemPosition();
                SharedPref.saveStringInSharedPref(AppConstants.SPINNER_PO_DATA, category_name.get(index), getApplicationContext());

                mSpinnerName = mProductArrayList.get(index).getName();
                mSpinnerPrice = mProductArrayList.get(index).getPrice();
                Log.e("Selected ", mSpinnerName + "=" + mSpinnerPrice);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        add_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(quantity_count).equals("0")) {
                    po_form_recyclerview.setVisibility(View.VISIBLE);
                    addpoform();
                    po_products();
                    //   getSpinnerData();
                    quantity_count = 0;
                    quantity_number.setText(String.valueOf(quantity_count));


                } else {
                    Toast.makeText(PO_Generate_Form_activity.this, "Please add product quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Po_add();
            }
        });

        dealer_doc_confirm();
    }

    //Changes by 20/07/2022 Am**
    private void SelectImageDailog() {

        dialogCondition.setContentView(R.layout.upload_image_dailog);
        CameraButton = (ImageView) dialogCondition.findViewById(R.id.camera_button);
        GalleryButton = (ImageView) dialogCondition.findViewById(R.id.gallery_button);
        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
                dialogCondition.dismiss();
            }
        });
        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
                dialogCondition.dismiss();
            }
        });


    }

    //Changes by 20/07/2022,16:15 Am**
    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //Changes by 20/07/2022 Am**
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
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                                    contentURI);
                    invoicefile_upload_bttn.setImageBitmap(bitmap);
                    x = 1;
                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            invoicefile_upload_bttn.setImageBitmap(thumbnail);

            y = 1;
            saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);


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
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");
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

            SharedPref.saveStringInSharedPref(AppConstants.PO_GENERATE_UPLOAD_IMG, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void dealer_doc_confirm() {

        Dealer_doc_confirm_POJO pojo = new Dealer_doc_confirm_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<dealer_doc_confirm_MODEL> call = restApis.dealer_doc_confirm(pojo);
        call.enqueue(new Callback<dealer_doc_confirm_MODEL>() {
            @Override
            public void onResponse(Call<dealer_doc_confirm_MODEL> call, Response<dealer_doc_confirm_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode() == 200) {

                        if (response.body().getPayload().getDealer_doc().equals("0")) {
                            CheckDocUploadStatus();
                        }
                    } else {

                    }

                }

            }

            @Override
            public void onFailure(Call<dealer_doc_confirm_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void CheckDocUploadStatus() {

        dialogCondition.setContentView(R.layout.po_dailog_for_doc_upload);
        Ok_button = (TextView) dialogCondition.findViewById(R.id.Ok_button);

        dialogCondition.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        dialogCondition.setCancelable(true);
        dialogCondition.show();
        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PO_Generate_Form_activity.this, PersonalDetails_Activity.class);
                startActivity(intent);
            }
        });


    }

    private void addpoform() {
        po_product = new PO_Product(SharedPref.getStringFromSharedPref(AppConstants.SPINNER_PO_DATA,
                getApplicationContext()),
                "9100",
                quantity_number.getText().toString());
        p.add(po_product);

        mSelectedProductArrayList.add(new PO_Product(mSpinnerName, mSpinnerPrice, quantity_number.getText().toString()));

        for (int i = 0; i < mSelectedProductArrayList.size(); i++) {

            Log.e("mSelectedProductArrayList", "" + mSelectedProductArrayList.get(i).getPrice());

        }
        SharedPref.saveArrayListInSharedPref("poData", mSelectedProductArrayList, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_Generate_Form_activity.this, LinearLayoutManager.VERTICAL, false
        );
        po_form_recyclerview.setLayoutManager(layoutManager);

        po_get_list_adapter.notifyItemInserted(mSelectedProductArrayList.size());
        po_form_recyclerview.setAdapter(po_get_list_adapter);
        getrecylerviewpodata();
    }

    private void getrecylerviewpodata() {
        for (int i = 0; i < SharedPref.getArrayListFromSharedPref("poData", getApplicationContext()).size(); i++) {
            Log.e("Data ", "" + SharedPref.getArrayListFromSharedPref("poData", getApplicationContext()).get(i).getName());
            Log.e("Data ", "" + SharedPref.getArrayListFromSharedPref("poData", getApplicationContext()).get(i).getQuantity());
        }

    }


    private void GetFormAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_Generate_Form_activity.this, LinearLayoutManager.VERTICAL, false
        );
        po_form_recyclerview.setLayoutManager(layoutManager);
        po_form_adapter = new PO_Form_Adapter(PO_Generate_Form_activity.this, PO_List);
        po_form_recyclerview.setAdapter(po_form_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getSpinnerData() {
        custPrograssbar.prograssCreate(this);
        GetCatproductPojo pojo = new GetCatproductPojo(SharedPref.getStringFromSharedPref(AppConstants.CAT_TYPE, PO_Generate_Form_activity.this),
                SharedPref.getStringFromSharedPref(AppConstants.CAT_NAME, PO_Generate_Form_activity.this), SharedPref.getStringFromSharedPref(AppConstants.BRAND, PO_Generate_Form_activity.this));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GetCatproductModel> call = restApis.getCatproduct(pojo);
        call.enqueue(new Callback<GetCatproductModel>() {
            @Override
            public void onResponse(Call<GetCatproductModel> call, Response<GetCatproductModel> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");
                        mProductArrayList.clear();

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                category_name.add(response.body().getPayload().get(i).getProd_name());
                                Log.e("getProd_name ", response.body().getPayload().get(i).getProd_name() + "=" + response.body().getPayload().get(i).getProd_mrp());
                                mProductArrayList.add(new PO_Product(response.body().getPayload().get(i).getProd_name(), response.body().getPayload().get(i).getProd_mrp(), response.body().getPayload().get(i).getProd_qty()));

                                if (response.body().getPayload().size() - 1 == i) {
//                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PO_Generate_Form_activity.this,
//                                            android.R.layout.simple_spinner_item, category_name);

                                    CustomProductsSpinner mCustomProductsSpinner = new CustomProductsSpinner(getApplicationContext(), mProductArrayList);

//                                    mCustomProductsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    // product_spinner.setAdapter(spinnerArrayAdapter);
                                    po_spinner.setAdapter(mCustomProductsSpinner);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetCatproductModel> call, Throwable t) {

                Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void Po_add() {
        custPrograssbar.prograssCreate(this);
        int mTotalPrice = 0;
        int mPrice = 0;


        for (int i = 0; i < mSelectedProductArrayList.size(); i++) {
            Log.e("Added *************", mSelectedProductArrayList.get(i).getName() + "=" + mSelectedProductArrayList.get(i).getPrice() + "=" + mSelectedProductArrayList.get(i).getQuantity());

            mPrice = Integer.parseInt(mSelectedProductArrayList.get(i).getPrice()) * Integer.parseInt(mSelectedProductArrayList.get(i).getQuantity());
            ;

            mTotalPrice = mTotalPrice + mPrice;


        }
        Log.e("mTotalPrice *************", String.valueOf(mTotalPrice));
        String number1 = String.valueOf(mTotalPrice);
        Log.e("number1", "number1--->>" + number1);
        double amount = Double.parseDouble(number1);
        Log.e("amount", "amount--->>" + amount);
        DecimalFormat formatter = new DecimalFormat("##,##,###");
        Log.e("formatter", "formatter--->>" + formatter);
        String formatted = formatter.format(amount);
        Log.e("formatted", "formatted--->>" + formatted);
        mBtnTotalPrice.setText("Estimated Price :- " + "₹" + formatted);
        Po_add_POJO pojo = new Po_add_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext())
                , SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                format, mSelectedProductArrayList, String.valueOf(mTotalPrice));
        SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext());


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_add_MODEL> call = restApis.Po_add(pojo);
        call.enqueue(new Callback<Po_add_MODEL>() {
            @Override
            public void onResponse(Call<Po_add_MODEL> call, Response<Po_add_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {
                        SharedPref.saveStringInSharedPref(AppConstants.PO_ID_GENERATE, response.body().getPayload().getPo_id(), getApplicationContext());

                        if(x==1||y==1){
                            podisverslreportupdate();
                        }
                        else{
                            Intent it = new Intent(PO_Generate_Form_activity.this, MainActivity.class);
                            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                            startActivity(it);
                            Toast.makeText(PO_Generate_Form_activity.this, "Your PO Added Successfully !", Toast.LENGTH_SHORT).show();
                            Log.e("Body", "body2");
                        }


                    } else {
                        Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<Po_add_MODEL> call, Throwable t) {


                Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PO_Generate_Form_activity.this, LinearLayoutManager.VERTICAL, false
        );

    }

    private void podisverslreportupdate() {

        String musercode = SharedPref.getStringFromSharedPref(AppConstants.PO_ID_GENERATE, getApplicationContext());
        RequestBody po_id = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile;
//        Log.e("testingggg", "testingggg99999" + idFile);
        //    Log.e("UDC FileName ", mGSTFile.toString());


        idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.PO_GENERATE_UPLOAD_IMG, getApplicationContext()));
        Log.e("testingggg", "testingggg99999" + idFile);
        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("po_img", idFile.getName(), mFile1);
        Log.e("testingggg", "testingggg10000" + vechileDocUpload2);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_img_add_MODEL> call = restApis.Po_img_add(po_id, vechileDocUpload2);
        call.enqueue(new Callback<Po_img_add_MODEL>() {
            @Override
            public void onResponse(Call<Po_img_add_MODEL> call, Response<Po_img_add_MODEL> response) {
                if (response.body() != null) {

                    if (response.body().getCode() == 200) {

                        //   Toast.makeText(PO_Generate_Form_activity.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                        //  Intent it = new Intent(PO_Generate_Form_activity.this, PO_TOP_FIVE_Activity.class);
                        //   it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                        //   startActivity(it);
                        Intent it = new Intent(PO_Generate_Form_activity.this, MainActivity.class);
                        it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                        startActivity(it);
                    }


                }


            }

            @Override
            public void onFailure(Call<Po_img_add_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void po_products() {
        custPrograssbar.prograssCreate(this);
        Po_products_POJO pojo = new Po_products_POJO(SharedPref.getStringFromSharedPref(AppConstants.BRAND, PO_Generate_Form_activity.this));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_products_Model> call = restApis.po_products(pojo);
        call.enqueue(new Callback<Po_products_Model>() {
            @Override
            public void onResponse(Call<Po_products_Model> call, Response<Po_products_Model> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        Log.e("Body", "body2");
                        mProductArrayList.clear();

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                category_name.add(response.body().getPayload().get(i).getProd_name());
                                Log.e("getProd_name ", response.body().getPayload().get(i).getProd_name() + "=" + response.body().getPayload().get(i).getProd_mrp());
                                mProductArrayList.add(new PO_Product(response.body().getPayload().get(i).getProd_name(), response.body().getPayload().get(i).getProd_mrp(), response.body().getPayload().get(i).getProd_qty()));

                                if (response.body().getPayload().size() - 1 == i) {
//                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PO_Generate_Form_activity.this,
//                                            android.R.layout.simple_spinner_item, category_name);

                                    CustomProductsSpinner mCustomProductsSpinner = new CustomProductsSpinner(getApplicationContext(), mProductArrayList);

//                                    mCustomProductsSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    // product_spinner.setAdapter(spinnerArrayAdapter);
                                    po_spinner.setAdapter(mCustomProductsSpinner);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<Po_products_Model> call, Throwable t) {

                Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

}