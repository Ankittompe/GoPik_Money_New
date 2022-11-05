package com.qts.gopik_money.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.Po_all_details_MODEL;
import com.qts.gopik_money.Model.podisverslreportupdate_MODEL;
import com.qts.gopik_money.Pojo.Po_all_details_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supplychain_Adapter.PoDetails_Approve_Financer_Adapter;
import com.qts.gopik_money.Utils.CustPrograssbar;
import com.qts.gopik_money.Utils.TextViewUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Po_Generate_Approved_By_Financer_Activity extends AppCompatActivity implements PickiTCallbacks {
    RecyclerView alldetails_recylerview;
    PoDetails_Approve_Financer_Adapter poDetails_approve_financer_adapter;
    TextView textView3, reject,et_po_id,et_date,et_dealer_name,et_status,et_total_qty,et_total_price,tenure,rateofinterest;
    private int GALLERY = 1, CAMERA = 2;
    public int x = 0, y = 0;
    PickiT pickiT;
    String networkError = "It seems your Network is unstable . Please Try again!";
    private static final String IMAGE_DIRECTORY = "/financer";
    CustPrograssbar custPrograssbar;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> po_id = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> brand = new ArrayList<>();
    ArrayList<String> dealer_id = new ArrayList<>();
    ArrayList<String> dealer_name = new ArrayList<>();
    ArrayList<String> product = new ArrayList<>();
    ArrayList<String> prodt_quantity = new ArrayList<>();
    ArrayList<String> update_quantity = new ArrayList<>();
    ArrayList<String> prodt_price = new ArrayList<>();
    ArrayList<String> update_price = new ArrayList<>();
    ArrayList<String> total_price = new ArrayList<>();
    ArrayList<String> update_totl_prc = new ArrayList<>();
    ArrayList<String> financer = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> invoicefile = new ArrayList<>();
    ImageView arrow, hometoolbar,upld_receipt_button;
    Integer temp=0;
    Integer tempp=0;
    Integer tempmod=0;
    Integer tempmodd=0;
    Integer tempmodifyprice=0;
    Integer tempmodifypricee=0;
    String rupee_symbol = "₹";
    String mUserType;
    TextView invoiceName;
    LinearLayout mLinModifyPrice;
    TextView mInvoiceNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_approved_by_financer);
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        custPrograssbar = new CustPrograssbar();
        mInvoiceNumber = (TextView) findViewById(R.id.invoice_number);
        et_po_id = (TextView) findViewById(R.id.et_po_id);
        et_date = (TextView) findViewById(R.id.et_date);
        et_dealer_name = (TextView) findViewById(R.id.et_dealer_name);
        et_status = (TextView) findViewById(R.id.et_status);
        et_total_qty = (TextView) findViewById(R.id.et_total_qty);
        et_total_price = (TextView) findViewById(R.id.et_total_price);
        rateofinterest = (TextView) findViewById(R.id.rateofinterest);
        tenure = (TextView) findViewById(R.id.tenure);
        invoiceName = (TextView) findViewById(R.id.invoiceName);

        pickiT = new PickiT(getApplicationContext(), this, Po_Generate_Approved_By_Financer_Activity.this);
        alldetails_recylerview=(RecyclerView) findViewById(R.id.rclview);
        textView3=(TextView) findViewById(R.id.textView3);
        reject=(TextView) findViewById(R.id.reject);
        arrow=(ImageView) findViewById(R.id.arrow);
        hometoolbar=(ImageView) findViewById(R.id.hometoolbar);
        upld_receipt_button=(ImageView) findViewById(R.id.invoicefile);
        mLinModifyPrice = findViewById(R.id.linModifyPrice);
        mLinModifyPrice.setVisibility(View.INVISIBLE);

        invoiceName.append(TextViewUtils.getColoredString("Invoice of ", ContextCompat.getColor(Po_Generate_Approved_By_Financer_Activity.this, R.color.black)));
        invoiceName.append(TextViewUtils.getColoredString(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()),
                ContextCompat.getColor(Po_Generate_Approved_By_Financer_Activity.this, R.color.blue)));
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Po_Generate_Approved_By_Financer_Activity.this,PO_TOP_FIVE_Activity.class);
                startActivity(in);
            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Po_Generate_Approved_By_Financer_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });
        upld_receipt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(x==1||y==1)){
                    Toast.makeText(Po_Generate_Approved_By_Financer_Activity.this, "Please Upload Delivery Order", Toast.LENGTH_SHORT).show();
                }else{
                    custPrograssbar.prograssCreate(Po_Generate_Approved_By_Financer_Activity.this);
                    podisverslreportupdate();
                }

            }
        });

        po_all_details();

    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new
                AlertDialog.Builder(this);
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
    private void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }
    private void takePhotoFromCamera() {
        Intent intent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void ChoosePdfDocument() {
        openPDFSelector();

       /* try {
            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), FILE_CHOOSER);
        } catch (Exception exe) {
            exe.printStackTrace();
        }*/
    }

    private void openPDFSelector() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);

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
                    upld_receipt_button.setImageBitmap(bitmap);
                    x=1;
                    saveImage(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();

                    // Toast.makeText(getActivity(), "Failed!",Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            upld_receipt_button.setImageBitmap(thumbnail);

             y=1;
           saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this, "Image Saved!",Toast.LENGTH_SHORT).show();

                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);


        }
        super.onActivityResult(requestCode, resultCode, data);


    }
    public String saveImage(Bitmap myBitmap) {


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(

                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        IMAGE_DIRECTORY);



        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdir();


        }
        try {

            File f = new
                    File(getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "gopikmoneykyc1");

            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);


            fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

            fo.close();


            SharedPref.saveStringInSharedPref(AppConstants.PO_FINANCER_UPLOADIMAGE, f.getAbsolutePath(), getApplicationContext());
            return f.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void po_all_details() {
        custPrograssbar.prograssCreate(this);
        Po_all_details_POJO pojo = new Po_all_details_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call;
        if (mUserType.equals("SubDealer")) {
            call = restApis.subdealer_po_all_details(pojo);
        } else if (mUserType.equals("Dealer")) {
            call = restApis.po_all_details(pojo);
        } else {
            call = restApis.po_all_details(pojo);
        }
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {
                        if (response.body().getPayload().size() > 0) {
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                mInvoiceNumber.setText(response.body().getPayload().get(i).getInvoice_no());
                                id.add(response.body().getPayload().get(i).getId());
                                po_id.add(response.body().getPayload().get(i).getPo_id());
                                date.add(response.body().getPayload().get(i).getDate());
                                brand.add(response.body().getPayload().get(i).getBrand());
                                dealer_id.add(response.body().getPayload().get(i).getDealer_id());
                                dealer_name.add(response.body().getPayload().get(i).getDealer_name());
                                product.add(response.body().getPayload().get(i).getProduct());
                                prodt_quantity.add(response.body().getPayload().get(i).getProdt_quantity());
                                update_quantity.add(response.body().getPayload().get(i).getUpdate_quantity());
                                prodt_price.add(response.body().getPayload().get(i).getProdt_price());
                                update_price.add(response.body().getPayload().get(i).getUpdate_price());
                                total_price.add(response.body().getPayload().get(i).getTotal_price());
                                update_totl_prc.add(response.body().getPayload().get(i).getUpdate_totl_prc());
                                financer.add(response.body().getPayload().get(i).getFinancer());
                                status.add(response.body().getPayload().get(i).getStatus());
                                invoicefile.add(response.body().getPayload().get(i).getInvoice_file());
                                tempp = temp + Integer.parseInt(response.body().getPayload().get(i).getProdt_quantity());
                                temp = tempp;
                                rateofinterest.setText(response.body().getPayload().get(i).getRoi() + " %");
                                tenure.setText(response.body().getPayload().get(i).getTenure() + " days");

                                et_po_id.setText(response.body().getPayload().get(i).getPo_id());
                                et_date.setText(response.body().getPayload().get(i).getDate());
                                et_dealer_name.setText(SharedPref.getStringFromSharedPref(AppConstants.DEALER_NAME, getApplicationContext()));
                                et_status.setText(response.body().getPayload().get(i).getStatus());
                                if (response.body().getPayload().get(i).getUpdate_price().equals("0")) {
                                    et_total_qty.setText(String.valueOf(temp));
                                    String number1 = response.body().getPayload().get(i).getTotal_price();

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat("##,##,###");

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);
                                } else {
                                    if (!response.body().getPayload().get(i).getUpdate_quantity().equals("NA")) {
                                        tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getUpdate_quantity());
                                    }else{
                                        tempmodd = tempmod + Integer.parseInt(response.body().getPayload().get(i).getProdt_quantity());
                                    }
                                    tempmod = tempmodd;
                                    et_total_qty.setText(String.valueOf(tempmod));

//                                    tempmodd = tempmod + Integer.valueOf(response.body().getPayload().get(i).getUpdate_quantity());
//                                    tempmod = tempmodd;
//                                    et_total_qty.setText(String.valueOf(tempmod));


                                    if (!response.body().getPayload().get(i).getUpdate_totl_prc().equals("NA")) {
                                        tempmodifypricee = tempmodifyprice + Integer.parseInt(response.body().getPayload().get(i).getUpdate_totl_prc());
                                    }else{
                                        tempmodifypricee = tempmodifyprice + Integer.parseInt(response.body().getPayload().get(i).getTotal_price());
                                    }

//                                    tempmodifypricee = tempmodifyprice + Integer.valueOf(response.body().getPayload().get(i).getUpdate_totl_prc());
                                    tempmodifyprice = tempmodifypricee;


                                    String number1 = String.valueOf(tempmodifyprice);

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat("##,##,###");

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);


                                }
                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            Po_Generate_Approved_By_Financer_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    alldetails_recylerview.setLayoutManager(layoutManager);
                                    poDetails_approve_financer_adapter = new PoDetails_Approve_Financer_Adapter(getApplicationContext(),
                                            id, po_id, date, brand, dealer_id, dealer_name, product, prodt_quantity,
                                            update_quantity, prodt_price, update_price, total_price,
                                            update_totl_prc, financer, status, invoicefile);
                                    alldetails_recylerview.setAdapter(poDetails_approve_financer_adapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(Po_Generate_Approved_By_Financer_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }

                }


            }


            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(Po_Generate_Approved_By_Financer_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


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
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {

    }

    @Override
    public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

    }


    private void podisverslreportupdate() {
        String musercode = SharedPref.getStringFromSharedPref(AppConstants.PO_ID,getApplicationContext());
        RequestBody po_id = RequestBody.create(MediaType.parse("multipart/form-data"), musercode);


        File idFile;



            idFile = new File(SharedPref.getStringFromSharedPref(AppConstants.PO_FINANCER_UPLOADIMAGE, getApplicationContext()));

        RequestBody mFile1;
        MultipartBody.Part vechileDocUpload2;
        mFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), idFile);
        vechileDocUpload2 = MultipartBody.Part.createFormData("dealer_final_report", idFile.getName(), mFile1);

        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<podisverslreportupdate_MODEL> call = restApis.podisverslreportupdate(po_id, vechileDocUpload2);
        call.enqueue(new Callback<podisverslreportupdate_MODEL>() {
            @Override
            public void onResponse(Call<podisverslreportupdate_MODEL> call, Response<podisverslreportupdate_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    Intent it = new Intent(Po_Generate_Approved_By_Financer_Activity.this, PO_TOP_FIVE_Activity.class);
                    it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                    startActivity(it);

                }



            }

            @Override
            public void onFailure(Call<podisverslreportupdate_MODEL> call, Throwable t) {


                Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
            }

        });

    }

}