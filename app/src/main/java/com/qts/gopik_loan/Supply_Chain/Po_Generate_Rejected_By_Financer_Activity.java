package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.qts.gopik_loan.Activity.AppConstants;
import com.qts.gopik_loan.Activity.SharedPref;
import com.qts.gopik_loan.Dealer_Activity.MainActivity;
import com.qts.gopik_loan.Model.Po_all_details_MODEL;
import com.qts.gopik_loan.Model.podisverslreportupdate_MODEL;
import com.qts.gopik_loan.Pojo.Po_all_details_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.PoDetails_Approve_Financer_Adapter;
import com.qts.gopik_loan.Supplychain_Adapter.PoDetails_Rejected_Financer_Adapter;
import com.qts.gopik_loan.Utils.CustPrograssbar;

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

public class Po_Generate_Rejected_By_Financer_Activity extends AppCompatActivity  implements PickiTCallbacks {
    RecyclerView alldetails_recylerview;
    PoDetails_Rejected_Financer_Adapter poDetails_rejected_financer_adapter;
    TextView textView3, reject,et_po_id,et_date,et_dealer_name,et_status,et_total_qty,et_total_price;
    private int GALLERY = 1, CAMERA = 2;
    PickiT pickiT;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_rejected_by_financer);
        custPrograssbar = new CustPrograssbar();

        et_po_id = (TextView) findViewById(R.id.et_po_id);
        et_date = (TextView) findViewById(R.id.et_date);
        et_dealer_name = (TextView) findViewById(R.id.et_dealer_name);
        et_status = (TextView) findViewById(R.id.et_status);
        et_total_qty = (TextView) findViewById(R.id.et_total_qty);
        et_total_price = (TextView) findViewById(R.id.et_total_price);

        alldetails_recylerview=(RecyclerView) findViewById(R.id.rclview);
        textView3=(TextView) findViewById(R.id.textView3);
        arrow=(ImageView) findViewById(R.id.arrow);
        hometoolbar=(ImageView) findViewById(R.id.hometoolbar);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Po_Generate_Rejected_By_Financer_Activity.this, PO_TOP_FIVE_Activity.class);

                startActivity(it);
            }
        });
        hometoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Po_Generate_Rejected_By_Financer_Activity.this, MainActivity.class);
                it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                startActivity(it);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Po_Generate_Rejected_By_Financer_Activity.this, PO_TOP_FIVE_Activity.class);

                startActivity(it);

            }
        });

        po_all_details();

    }


    private void po_all_details() {
        custPrograssbar.prograssCreate(this);
        Po_all_details_POJO pojo = new Po_all_details_POJO( SharedPref.getStringFromSharedPref(AppConstants.PO_ID,getApplicationContext()));
        Log.e("checktopfive","response");
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<Po_all_details_MODEL> call = restApis.po_all_details(pojo);
        call.enqueue(new Callback<Po_all_details_MODEL>() {
            @Override
            public void onResponse(Call<Po_all_details_MODEL> call, Response<Po_all_details_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode().equals("200")) {

                        Log.e("Body", "body2");

                        if (response.body().getPayload().size() > 0) {
                            Log.e(TAG, "getpayloadmethod");
                            Log.e(TAG, "City Payload Size: " + response.body().getPayload().size());
                            for (int i = 0; i < response.body().getPayload().size(); i++) {
                                Log.e("Body", "body3");

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

                                tempp = temp + Integer.valueOf(response.body().getPayload().get(i).getProdt_quantity());
                                temp = tempp;


                                Log.e("Body", "body3"+temp);

                                et_po_id.setText(response.body().getPayload().get(i).getPo_id());
                                et_date.setText(response.body().getPayload().get(i).getDate());
                                et_dealer_name.setText(response.body().getPayload().get(i).getDealer_name());
                                et_status.setText(response.body().getPayload().get(i).getStatus());
                                if(response.body().getPayload().get(i).getUpdate_price().equals("NA")){
                                    et_total_qty.setText(String.valueOf(temp));
                                    String number1 =response.body().getPayload().get(i).getTotal_price();
                                    Log.e("number1","number1--->>"+number1);
                                    double amount = Double.parseDouble(number1);
                                    Log.e("amount","amount--->>"+amount);
                                    DecimalFormat formatter = new DecimalFormat("##,##,###");
                                    Log.e("formatter","formatter--->>"+formatter);
                                    String formatted = formatter.format(amount);
                                    Log.e("formatted","formatted--->>"+formatted);
                                    et_total_price.setText(rupee_symbol +formatted);
                                }

                                else{
                                    tempmodd=tempmod+Integer.valueOf(response.body().getPayload().get(i).getUpdate_quantity());
                                    tempmod=tempmodd;

                                    tempmodifypricee=tempmodifyprice+Integer.valueOf(response.body().getPayload().get(i).getUpdate_totl_prc());
                                    tempmodifyprice=tempmodifypricee;
                                    et_total_qty.setText(String.valueOf(tempmod));
                                    String number1 = String.valueOf(tempmodifyprice);
                                    Log.e("number1","number1--->>"+number1);
                                    double amount = Double.parseDouble(number1);
                                    Log.e("amount","amount--->>"+amount);
                                    DecimalFormat formatter = new DecimalFormat("##,##,###");
                                    Log.e("formatter","formatter--->>"+formatter);
                                    String formatted = formatter.format(amount);
                                    Log.e("formatted","formatted--->>"+formatted);
                                    et_total_price.setText(rupee_symbol+formatted);


                                }
                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            Po_Generate_Rejected_By_Financer_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    alldetails_recylerview.setLayoutManager(layoutManager);
                                    poDetails_rejected_financer_adapter = new PoDetails_Rejected_Financer_Adapter(getApplicationContext(),
                                            id,po_id,date,brand,dealer_id,dealer_name,product,prodt_quantity,
                                            update_quantity,prodt_price,update_price,total_price,
                                            update_totl_prc,financer,status,invoicefile);
                                    alldetails_recylerview.setAdapter(poDetails_rejected_financer_adapter);


                                }
                            }
                        }
                    } else {
                        Toast.makeText(Po_Generate_Rejected_By_Financer_Activity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                    }

                }


            }



            @Override
            public void onFailure(Call<Po_all_details_MODEL> call, Throwable t) {


                Toast.makeText(Po_Generate_Rejected_By_Financer_Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
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



}