package com.qts.gopik_money.Dealer_Activity;

import static android.content.ContentValues.TAG;

import static com.qts.gopik_money.Activity.LogIn.REQUESTPERMISSIONCODE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Adapter.GoatDataAdapter;
import com.qts.gopik_money.Interfaces.ItemAddListener;
import com.qts.gopik_money.Model.Calculation_data_MODEL;
import com.qts.gopik_money.Model.Get_wallet_details_MODEL;
import com.qts.gopik_money.Model.GoatAllCalculateModel;
import com.qts.gopik_money.Model.GoatFinalCalculateModel;
import com.qts.gopik_money.Model.ProductLIstDataModel;
import com.qts.gopik_money.Model.Product_details_Model;
import com.qts.gopik_money.Model.TagID_MODEL;
import com.qts.gopik_money.Pojo.Get_wallet_details_POJO;
import com.qts.gopik_money.Pojo.GoatCalculatePOJO;
import com.qts.gopik_money.Pojo.Hero_Loan_Calculation_Data_POJO;
import com.qts.gopik_money.Pojo.ProductDetailsItem;
import com.qts.gopik_money.Pojo.Product_details_POJO;
import com.qts.gopik_money.Pojo.StoreGoatCalculationResponsePOJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.ItemRemoveClickListener;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.CustPrograssbar;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Goat_Emi_Calculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {
    TextView months;
    TextView down_payment_2;
    TextView loan_amount_2;
    TextView processing_fees;
    TextView gopik_protect_insurance;
    TextView upfront_payment_by_customer;
    TextView emi;
    TextView interst_per_annum;
    TextView mTxtVaccination;
    TextView mTxtDownPayment;
    TextView mTxtLoanAmt;
    TextView mTxtDisbursalAmount;
    String mTenure = "";
    Spinner spino, mData2spinnerGender;
    EditText down_payment;
    EditText couponcode;
    EditText subsidy;
    LinearLayout linearlayout_aftercalculation;
    TextView calculate;
    TextView apply;
    TextView customer_price;
    TextView applycoupon;
    String[] courses = {"6", "9", "12"};
    String[] mGenderList = {"Male", "Female"};
    String[] coursesTwo = {"12"};
    CustPrograssbar custPrograssbar;
    ImageView hometoolbar, backarrow;

    LinearLayout mLinLayoutSubsidy;
    LinearLayout mLinLayConvenience;
    ArrayAdapter ad;

    ImageView mImgUploadOne;
    ImageView mImgUploadTwo;
    File mGoatFrontImageFile;
    File mGoatBackImageFile;
    RecyclerView mRecVwGoatData;
    GoatDataAdapter mGoatDataAdapter;
    ArrayList<ProductLIstDataModel> mProductListModelArrayList;
    ArrayList<ProductDetailsItem> mProductDetailsItemArrayList = new ArrayList<>();
    ArrayList<ProductDetailsItem> mProductDetailsItemArrayListNew = new ArrayList<>();
    ArrayList<String> mProductCatListArrayList;
    ItemAddListener mItemAddListener;
    ItemRemoveClickListener mItemRemoveClickListener;

    private static final String IMAGE_DIRECTORY = "/GoPikMoney";
    int mTempPrice = 8500;

    //            down_payment.setError("Please Enter The Down Payment");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goat_activity_emi_calculator);
        custPrograssbar = new CustPrograssbar();
        SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATIONPOPUP, "3", getApplicationContext());
        calculate = findViewById(R.id.txtGoatCalculate);

        apply = findViewById(R.id.apply);
        mLinLayoutSubsidy = findViewById(R.id.linLayoutSubsidy);
        mLinLayoutSubsidy.setVisibility(View.GONE);

        linearlayout_aftercalculation = findViewById(R.id.linearlayout_aftercalculation);
        mLinLayConvenience = findViewById(R.id.linLayConvenience);

        down_payment = findViewById(R.id.down_payment);
        loan_amount_2 = findViewById(R.id.loan_amount_2);
        processing_fees = findViewById(R.id.processing_fees);
        gopik_protect_insurance = findViewById(R.id.gopik_protect_insurance);
        upfront_payment_by_customer = findViewById(R.id.upfront_payment_by_customer);
        emi = findViewById(R.id.emi);
        interst_per_annum = findViewById(R.id.interst_per_annum);
        mTxtVaccination = findViewById(R.id.txtVaccination);
        mTxtDownPayment = findViewById(R.id.txtDownPayment);
        mTxtLoanAmt = findViewById(R.id.loan_amount_2);
        mTxtDisbursalAmount = findViewById(R.id.txtDisbursalAmount);


        applycoupon = findViewById(R.id.applycoupon);
        couponcode = findViewById(R.id.couponcode);
        spino = findViewById(R.id.choosehubb);

        spino.setOnItemSelectedListener(this);
        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesTwo);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        backarrow = (ImageView) findViewById(R.id.arrow);
        hometoolbar = (ImageView) findViewById(R.id.hometoolbar);
        subsidy = (EditText) findViewById(R.id.subsidy);
        down_payment.addTextChangedListener(this);


        calculate.setOnClickListener(view -> {
//            if (SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()).equals("Hero")) {
//                validationHero();
//            } else {
//                validation();
//            }
//            validationGoat();
//            linearlayout_aftercalculation.setVisibility(View.VISIBLE);


            for (int i = 0; i < mProductDetailsItemArrayList.size(); i++) {
                Log.e("**** Final ProductDetailsItems On Pos", mProductDetailsItemArrayList.size()
                        + " = " + mProductDetailsItemArrayList.get(i).getBreed()
                        + " = " + mProductDetailsItemArrayList.get(i).getPrice()
                        + " = " + mProductDetailsItemArrayList.get(i).getTagid()
                        + " = " + mProductDetailsItemArrayList.get(i).getGender()
                        + " = " + mProductDetailsItemArrayList.get(i).getWeight());

            }
//            mFinalWeight = mTempWeight + mTempWeight;


//            CallGoatCalculateApi(mProductDetailsItemArrayList);
            CallGoatCalculateApi(mProductDetailsItemArrayList);

        });
        apply.setOnClickListener(view -> {
//            calculation_data();
//            uploadPhotosDialog();
            CallAllFinalGoatCalculateApi(mProductDetailsItemArrayList);
        });
        applycoupon.setOnClickListener(view -> {
            Intent it = new Intent(Goat_Emi_Calculator.this, Promocode_Activity.class);
            startActivity(it);
        });
        backarrow.setOnClickListener(v -> {
            Intent it = new Intent(Goat_Emi_Calculator.this, GOAT_ProductDetails_DEALER_Activity.class);
            startActivity(it);
        });
        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(Goat_Emi_Calculator.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);
        });


        TextView mTxtAddRow = findViewById(R.id.txtAddRow);
        customer_price = findViewById(R.id.customer_price);
//        custprice = Integer.valueOf(customer_price.getText().toString());
//        cust = custprice * (15 / 100);

//        customer_price.setText(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()));

        mTxtAddRow.setOnClickListener(view -> {
//            getCatProductList();
//            product_Details();
            mProductDetailsItemArrayList.add(new ProductDetailsItem("", "", "", "", ""));
            mGoatDataAdapter.notifyItemInserted(mProductDetailsItemArrayList.size());
            customer_price.setText("" + (mTempPrice * mProductDetailsItemArrayList.size()));
        });

//        mTxtKgPrice.setText(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()));
        mRecVwGoatData = findViewById(R.id.recVwGoatData);

//        getCatProductList(mProductDetailsItemArrayList);
        product_Details();
        customer_price.setText("" + (mTempPrice));

        mItemAddListener = new ItemAddListener() {
            @Override
            public void onClick(int position, ProductDetailsItem mProductDetailsItem) {
                mProductDetailsItemArrayList.set(position, mProductDetailsItem);
                Log.e("**** ProductDetailsItem", position
                        + " = " + mProductDetailsItem.getBreed()
                        + " = " + mProductDetailsItem.getPrice()
                        + " = " + mProductDetailsItem.getTagid()
                        + " = " + mProductDetailsItem.getGender()
                        + " = " + mProductDetailsItem.getWeight());
                int sum = 0;
                for (int i = 0; i < mProductDetailsItemArrayList.size(); i++) {
                    if (!mProductDetailsItemArrayList.get(i).getPrice().equals("") && !mProductDetailsItemArrayList.get(i).getWeight().equals("")) {
//                        int mTempWeight;
//                        mTempWeight = Integer.parseInt(mProductDetailsItemArrayList.get(i).getPrice()) * Integer.parseInt(mProductDetailsItemArrayList.get(i).getWeight());
//                        sum = sum + mTempWeight;
//                        Log.e("**** mTempWeight", "pos " + i + "=" + mTempWeight + "=" + sum);
//                        customer_price.setText("" + sum);


                    }
                }
            }
        };

        mItemRemoveClickListener = (position -> {
            mProductDetailsItemArrayList.remove(position);
            mGoatDataAdapter.notifyItemRemoved(position);
            int mCurrentPrice = Integer.parseInt(customer_price.getText().toString().trim());
            customer_price.setText("" + (mCurrentPrice - 8500));
        });

    }

    private void CallGoatCalculateApi(ArrayList<ProductDetailsItem> mProductDetailsItemArrayList) {
        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);
        for (int i = 0; i < mProductDetailsItemArrayList.size(); i++) {
            Log.e("****++++++ ProductDetailsItem ", mProductDetailsItemArrayList.get(i).getBreed());
        }

        GoatCalculatePOJO mGoatCalculatePOJO = new GoatCalculatePOJO(
                "" + mProductDetailsItemArrayList.size(),
                gopik_protect_insurance.getText().toString().trim(),
                customer_price.getText().toString().trim(),
                down_payment.getText().toString().trim(),
                mTxtVaccination.getText().toString().trim(),
                interst_per_annum.getText().toString().trim(),
                SharedPref.getStringFromSharedPref(AppConstants.GET_SPINNER_POSITION, getApplicationContext())
        );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GoatAllCalculateModel> call = restApis.GetLoanCalculationForGoat(mGoatCalculatePOJO);
        call.enqueue(new Callback<GoatAllCalculateModel>() {
            @Override
            public void onResponse(Call<GoatAllCalculateModel> call, Response<GoatAllCalculateModel> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                        gopik_protect_insurance.setText("" + response.body().getPayload().getGopik_prtct_ins());
                        customer_price.setText("" + response.body().getPayload().getProduct_price());
                        mTxtDownPayment.setText("" + response.body().getPayload().getDown_payment());
                        mTxtVaccination.setText("" + response.body().getPayload().getVacc_hlth_kit());
                        interst_per_annum.setText("" + response.body().getPayload().getRate_of_intest());
                        mTenure = "" + response.body().getPayload().getTenure();
                        mTxtLoanAmt.setText("" + response.body().getPayload().getLoan_amount());
                        mTxtDisbursalAmount.setText("" + response.body().getPayload().getDisbrs_amnt());
                        processing_fees.setText("" + response.body().getPayload().getProcc_fees());
                        emi.setText("" + response.body().getPayload().getEmi());
                        upfront_payment_by_customer.setText("" + response.body().getPayload().getUp_pay());
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<GoatAllCalculateModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CallAllFinalGoatCalculateApi(ArrayList<ProductDetailsItem> mProductDetailsItemArrayList) {
        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);
        StoreGoatCalculationResponsePOJO mStoreGoatCalculationResponsePOJO = new StoreGoatCalculationResponsePOJO(

                emi.getText().toString().trim(),
                mTxtDisbursalAmount.getText().toString().trim(),
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                mTxtLoanAmt.getText().toString().trim(),
                down_payment.getText().toString(),
                interst_per_annum.getText().toString().trim(),
                gopik_protect_insurance.getText().toString().trim(),
                customer_price.getText().toString().trim(),
                upfront_payment_by_customer.getText().toString().trim(),
                mProductDetailsItemArrayList,
                mTxtVaccination.getText().toString().trim(),
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                mTenure,
                processing_fees.getText().toString().trim()

        );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<GoatFinalCalculateModel> call = restApis.GoatStoreCalculationData(mStoreGoatCalculationResponsePOJO);
        call.enqueue(new Callback<GoatFinalCalculateModel>() {
            @Override
            public void onResponse(Call<GoatFinalCalculateModel> call, Response<GoatFinalCalculateModel> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE, response.body().getPayload().getApplictn_code(), getApplicationContext());
                        uploadPhotosDialog();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<GoatFinalCalculateModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void validationGoat() {
        if ((down_payment.getText().toString().isEmpty())) {
            down_payment.setError("Please Enter The Down Payment");
        } else {
            mLinLayConvenience.setVisibility(View.GONE);
//            loan_calculation();
        }
    }

    private void validation() {
        if ((down_payment.getText().toString().isEmpty())) {
            down_payment.setError("Please Enter The Down Payment");
        } else {
//            loan_calculation();
        }
    }

    private void calculation_data() {

        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);

        Hero_Loan_Calculation_Data_POJO pojo = new Hero_Loan_Calculation_Data_POJO(
                SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "200",
                SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_MODEL_NAME, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_PRICE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.DOWN_PAYMENT, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.TENURE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.LOAN_PAYMENT, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_RATE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_EMI, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_FEES, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_INSURANCE_FEES, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_UP_PAY, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TOTAL_AMOUNT, getApplicationContext()));


        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Calculation_data_MODEL> call = restApis.goat_calculation_data(pojo);
        call.enqueue(new Callback<Calculation_data_MODEL>() {
            @Override
            public void onResponse(Call<Calculation_data_MODEL> call, Response<Calculation_data_MODEL> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        SharedPref.saveStringInSharedPref(AppConstants.CUTOMER_CODE, response.body().getPayload().getCust_code(), getApplicationContext());
                        SharedPref.saveStringInSharedPref(AppConstants.USER_CODE, response.body().getPayload().getUser_code(), getApplicationContext());

//                        SharedPref.saveStringInSharedPref(AppConstants.SUBSIDY, response.body().getPayload().getSubsidy(), getApplicationContext());
                        get_wallet_details();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (response.code() == 400) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String errorMsg = jObjError.getString("message");
                            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Calculation_data_MODEL> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    private void get_wallet_details() {
        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);
        Get_wallet_details_POJO pojo = new Get_wallet_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext()),
                "Gopik Wallet", SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Get_wallet_details_MODEL> call = restApis.get_wallet_details(pojo);
        call.enqueue(new Callback<Get_wallet_details_MODEL>() {
            @Override
            public void onResponse(Call<Get_wallet_details_MODEL> call, Response<Get_wallet_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {
                        if (response.body().getPayload().size() > 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                SharedPref.saveStringInSharedPref(AppConstants.BALANCE, response.body().getPayload().get(i).getBalance(), getApplicationContext());
//                                Intent it = new Intent(Goat_Emi_Calculator.this, HeroWallet_DetailsActivity.class);
                                Intent it = new Intent(Goat_Emi_Calculator.this, HeroCustomerAcceptancePolicy_DEALER_Activity.class);
                                startActivity(it);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Get_wallet_details_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }

        });

    }

    public void uploadPhotosDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.upload_image_custom_dialog);

        Button mBtnUpload = (Button) dialog.findViewById(R.id.btnUpload);
        Button mBtnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        mImgUploadOne = (ImageView) dialog.findViewById(R.id.imgUploadOne);
        mImgUploadTwo = (ImageView) dialog.findViewById(R.id.imgUploadTwo);
        EnableRuntimePermission();
        mImgUploadOne.setOnClickListener(view -> {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 7);
        });
        mImgUploadTwo.setOnClickListener(view -> {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 8);
        });
        mBtnUpload.setOnClickListener(v -> {
            dialog.dismiss();
//            get_wallet_details();
            UploadGoatImages(mGoatFrontImageFile, mGoatBackImageFile);
        });
        mBtnCancel.setOnClickListener(v ->
                dialog.dismiss()
        );
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mImgUploadOne.setImageBitmap(bitmap);
            mGoatFrontImageFile = new File(saveImageFile(bitmap));
        } else if (requestCode == 8 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mImgUploadTwo.setImageBitmap(bitmap);
            mGoatBackImageFile = new File(saveImageFile(bitmap));
        }
    }

    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUESTPERMISSIONCODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        super.onRequestPermissionsResult(RC, per, PResult);
        if (RC == REQUESTPERMISSIONCODE) {
            if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int index = spino.getSelectedItemPosition();

        SharedPref.saveStringInSharedPref(AppConstants.GET_SPINNER_POSITION, coursesTwo[index], getApplicationContext());
        mTenure = courses[index];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().isEmpty()) {
            int mDownPrice = Integer.parseInt(editable.toString());
            if (mDownPrice >= 6000) {
                ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesTwo);
            } else {
                ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesTwo);
            }
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spino.setAdapter(ad);
            ad.notifyDataSetChanged();
        } else {
            ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesTwo);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spino.setAdapter(ad);
            ad.notifyDataSetChanged();
        }
    }

    private void product_Details() {
        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);
        Product_details_POJO pojo = new Product_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_TYPEE, getApplicationContext()),
                SharedPref.getStringFromSharedPref(AppConstants.PRODUCT_IDD, getApplicationContext())
        );
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Product_details_Model> call = restApis.product_Details(pojo);
        call.enqueue(new Callback<Product_details_Model>() {
            @Override
            public void onResponse(Call<Product_details_Model> call, Response<Product_details_Model> response) {
                if (response.body() != null) {
                    if (response.body().getCode().equals("200")) {
                        custPrograssbar.closePrograssBar();
                        mProductListModelArrayList = new ArrayList<>();
                        if (response.body().getPayload().size() > 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                mProductDetailsItemArrayList.add(new ProductDetailsItem(response.body().getPayload().get(i).getProd_name(), response.body().getPayload().get(i).getProd_mrp(), "", "", ""));
                                mProductDetailsItemArrayListNew.add(new ProductDetailsItem(response.body().getPayload().get(i).getProd_name(), response.body().getPayload().get(i).getProd_mrp(), "", "", ""));
                            }
                            mGoatDataAdapter = new GoatDataAdapter(Goat_Emi_Calculator.this, mProductDetailsItemArrayList, mItemAddListener, mItemRemoveClickListener);
                            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecVwGoatData.setLayoutManager(mLinearLayoutManager);
                            mRecVwGoatData.setItemAnimator(new DefaultItemAnimator());
                            mRecVwGoatData.setAdapter(mGoatDataAdapter);
                            mGoatDataAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong!234!", Toast.LENGTH_LONG).show();
                    }
                }
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onFailure(Call<Product_details_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String saveImageFile(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + IMAGE_DIRECTORY);
        Log.e("Full Path", Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            Log.e("TAG", "File Saved5");
            wallpaperDirectory.mkdir();
            Log.e("Yest", Boolean.toString(wallpaperDirectory.mkdir()));

        }
        try {
            File f = new File(Goat_Emi_Calculator.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GoPikMoney");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(Goat_Emi_Calculator.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadGoatImages(File mGoatFrontFile, File mGoatBackFile) {
        custPrograssbar.prograssCreate(Goat_Emi_Calculator.this);
        RequestBody mCustCode = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.CUTOMER_CODE, getApplicationContext()));
        RequestBody mBrand = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref.getStringFromSharedPref(AppConstants.BRAND, getApplicationContext()));
        RequestBody mFileFront, mFileBack;
        MultipartBody.Part mGoatFrontFileData, mGoatBackFileData;
        mFileFront = RequestBody.create(MediaType.parse("multipart/form-data"), mGoatFrontFile);
        mFileBack = RequestBody.create(MediaType.parse("multipart/form-data"), mGoatFrontFile);
        mGoatFrontFileData = MultipartBody.Part.createFormData("prdt_img1", mGoatFrontFile.getName(), mFileFront);
        mGoatBackFileData = MultipartBody.Part.createFormData("prdt_img2", mGoatBackFile.getName(), mFileBack);
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<TagID_MODEL> call = restApis.GoatProductImageUpload(mCustCode, mBrand, mGoatFrontFileData, mGoatBackFileData);
        call.enqueue(new Callback<TagID_MODEL>() {
            @Override
            public void onResponse(Call<TagID_MODEL> call, Response<TagID_MODEL> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        custPrograssbar.closePrograssBar();
//                        ((MainActivity) requireActivity()).addFragment(mSub_dealer_po_fragment);
                        get_wallet_details();
                    }
                }
            }

            @Override
            public void onFailure(Call<TagID_MODEL> call, Throwable t) {
                custPrograssbar.closePrograssBar();
                Toast.makeText(Goat_Emi_Calculator.this, "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }
        });
    }


}