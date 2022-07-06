package com.qts.gopik_loan.Supply_Chain;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.qts.gopik_loan.Pojo.GetCatproductPojo;
import com.qts.gopik_loan.Pojo.Po_add_POJO;
import com.qts.gopik_loan.R;
import com.qts.gopik_loan.Retro.ItemClickListener;
import com.qts.gopik_loan.Retro.NetworkHandler;
import com.qts.gopik_loan.Retro.RestApis;
import com.qts.gopik_loan.Supplychain_Adapter.Po_Get_List_Adapter;
import com.qts.gopik_loan.Utils.CustPrograssbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PO_Generate_Form_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Po_Get_List_Adapter po_get_list_adapter;
    ItemClickListener itemClickListener;
    RecyclerView po_form_recyclerview;
    PO_Form_Adapter po_form_adapter;
    Spinner po_spinner;
    CustPrograssbar custPrograssbar;
    ArrayList<String> category_name = new ArrayList();
    ArrayList<PO_Product> mProductArrayList = new ArrayList();
    ArrayList<PO_Product> mSelectedProductArrayList = new ArrayList();
    PO_Product po_product;
    ArrayList<PO_Product> p = new ArrayList<PO_Product>();
    ArrayList<String> PO_List = new ArrayList<>();
    TextView quantity_number, add_bttn, current_date2, current_date, confirm_button, mBtnTotalPrice;
    ImageView qty_decrease_button, qty_increase_button;

    ImageView arrow, hometoolbar;
    String currentDateTimeString;
    int quantity_count = 0;

    String mSpinnerName = "";
    String mSpinnerPrice = "0";
    String format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_generate_form);

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
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format = s.format(new Date());
        current_date = findViewById(R.id.current_date);
        Log.e("Selected ", format);
        currentDateTimeString = java.text.DateFormat.getDateInstance().format(new Date());


        current_date.setText(format);

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
        itemClickListener = (position, mPoProduct) ->
                Toast.makeText(getApplicationContext(), "Position : "
                        + position + " || Value : " + mPoProduct.getQuantity(), Toast.LENGTH_SHORT).show();

        po_get_list_adapter = new Po_Get_List_Adapter(getApplicationContext(), p, itemClickListener);

        getSpinnerData();
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
                    getSpinnerData();
                    quantity_count=0;
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


    }

    private void addpoform() {
        po_product = new PO_Product(SharedPref.getStringFromSharedPref(AppConstants.SPINNER_PO_DATA,
                getApplicationContext()),
              "9100",
                quantity_number.getText().toString());
        p.add(po_product);

        mSelectedProductArrayList.add(new PO_Product(mSpinnerName, mSpinnerPrice, quantity_number.getText().toString()));

        for(int i=0;i<mSelectedProductArrayList.size();i++){

            Log.e("mSelectedProductArrayList",""+mSelectedProductArrayList.get(i).getPrice());

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
                        Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!234!", Toast.LENGTH_LONG).show();
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

        int mTotalPrice = 0;
        int mPrice = 0;


        for (int i = 0; i < mSelectedProductArrayList.size(); i++) {
            Log.e("Added *************", mSelectedProductArrayList.get(i).getName() + "=" + mSelectedProductArrayList.get(i).getPrice() + "=" + mSelectedProductArrayList.get(i).getQuantity());

            mPrice = Integer.parseInt(mSelectedProductArrayList.get(i).getPrice()) * Integer.parseInt(mSelectedProductArrayList.get(i).getQuantity());
            ;

            mTotalPrice = mTotalPrice + mPrice;


        }
        Log.e("mTotalPrice *************", String.valueOf(mTotalPrice));
        mBtnTotalPrice.setText("Total Price is :- "+"₹"+ mTotalPrice);
        Po_add_POJO pojo = new Po_add_POJO("47436", "sai", "Hero", format, mSelectedProductArrayList, String.valueOf(mTotalPrice));
        SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, getApplicationContext());


       RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
      Call<Po_add_MODEL> call = restApis.Po_add(pojo);
        call.enqueue(new Callback<Po_add_MODEL>() {
            @Override
           public void onResponse(Call<Po_add_MODEL> call, Response<Po_add_MODEL> response) {
               if (response.body() != null) {

                   if (response.body().getCode().equals("200")) {
                       Intent it = new Intent(PO_Generate_Form_activity.this, MainActivity.class);
                       it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.MY_MALL_DEALER_FRAG);
                       startActivity(it);

                       Toast.makeText(PO_Generate_Form_activity.this, "Your PO Added Successfully !", Toast.LENGTH_SHORT).show();
                        Log.e("Body", "body2");

                   } else {
                        Toast.makeText(PO_Generate_Form_activity.this, "Something went wrong!234!", Toast.LENGTH_LONG).show();
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


}