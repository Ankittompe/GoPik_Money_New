package com.qts.gopik_money.Supply_Chain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Dealer_Activity.MainActivity;
import com.qts.gopik_money.Model.LoanPoAllDetails_MODEL;
import com.qts.gopik_money.Pojo.LoanPoAllDetails_POJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Supplychain_Adapter.LoanPoAllDetails_Adapter;
import com.qts.gopik_money.Utils.CustPrograssbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanDetails_Activity extends AppCompatActivity {
    String networkError = "It seems your Network is unstable . Please Try again!";
    TextView po_id_tv ;
    TextView disb_amount_tv;
    TextView disb_date_tv;
    TextView date_of_closer_tv;
    TextView loan_id_tv;
    TextView tenure;
    String mSubDealer = "SubDealer";
    String mDealer = "Dealer";
    TextView rateofinterest;
    Integer temp = 0;
    Integer tempp = 0;
    Integer tempmod = 0;
    Integer tempmodd = 0;
    Integer tempmodifyprice = 0;
    Integer tempmodifypricee = 0;
    CustPrograssbar custPrograssbar;
    String mDecimalFormatter = "##,##,###";
    LoanPoAllDetails_Adapter loanPoAllDetailsAdapter;
    RecyclerView product_recyclerview;
    TextView et_total_qty ;
    TextView et_total_price;
    TextView ok_button;
    ImageView arrow ;
    ImageView hometoolbar;
    String rupee_symbol = "₹";
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
    String mUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);


        custPrograssbar = new CustPrograssbar();
        mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, getApplicationContext());
        arrow = findViewById(R.id.arrow);
        hometoolbar = findViewById(R.id.hometoolbar);
        po_id_tv = findViewById(R.id.po_id_tv);
        disb_amount_tv = findViewById(R.id.disb_amount_tv);
        disb_date_tv = findViewById(R.id.disb_date_tv);
        date_of_closer_tv = findViewById(R.id.date_of_closer_tv);

        loan_id_tv = findViewById(R.id.loan_id_tv);
        product_recyclerview = findViewById(R.id.product_recyclerview);
        et_total_qty = findViewById(R.id.et_total_qty);
        et_total_price = findViewById(R.id.et_total_price);
        ok_button = findViewById(R.id.ok_button);
        tenure = findViewById(R.id.tenure);
        rateofinterest = findViewById(R.id.rateofinterest);

        ok_button.setOnClickListener(v -> {
            Intent intent = new Intent(LoanDetails_Activity.this, LoanStatus.class);
            startActivity(intent);
        });
        arrow.setOnClickListener(v -> {
            Intent it = new Intent(LoanDetails_Activity.this, LoanStatus.class);
            startActivity(it);
        });

        hometoolbar.setOnClickListener(v -> {
            Intent it = new Intent(LoanDetails_Activity.this, MainActivity.class);
            it.putExtra(AppConstants.ACTFRAG_TYPE_KEY, AppConstants.HOME__DELAER_FRAGMENT);
            startActivity(it);

        });
        GetLoanPoAllDetails();
    }

    private void GetLoanPoAllDetails() {
        custPrograssbar.prograssCreate(this);
        LoanPoAllDetails_POJO pojo = new LoanPoAllDetails_POJO(SharedPref.getStringFromSharedPref(AppConstants.PO_ID, getApplicationContext()));
        RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
        Call<LoanPoAllDetails_MODEL> call;
        if (mUserType.equals(mSubDealer)) {
            call = restApis.subdealerloanpoalldetails(pojo);
        } else if (mUserType.equals(mDealer)) {
            call = restApis.loanpoalldetails(pojo);
        } else {
            call = restApis.loanpoalldetails(pojo);
        }

        call.enqueue(new Callback<LoanPoAllDetails_MODEL>() {
            @Override
            public void onResponse(Call<LoanPoAllDetails_MODEL> call, Response<LoanPoAllDetails_MODEL> response) {
                if (response.body() != null) {
                    custPrograssbar.closePrograssBar();
                    if (response.body().getCode() == 200) {
                        po_id_tv.setText(response.body().getPo_id());
                        loan_id_tv.setText(response.body().getLoan_id());
                        if (response.body().getDisbursal_amount().equals("NA")) {
                            disb_amount_tv.setText("₹" + response.body().getDisbursal_amount());
                        } else {
                            String number1 = response.body().getDisbursal_amount();

                            double amount = Double.parseDouble(number1);

                            DecimalFormat formatter = new DecimalFormat(mDecimalFormatter);

                            String formatted = formatter.format(amount);

                            disb_amount_tv.setText("₹" + formatted);
                        }
                        disb_date_tv.setText(response.body().getDate_of_disbursal());
                        date_of_closer_tv.setText(response.body().getDate_of_closure());
                        if (response.body().getPayload().size() > 0) {

                            for (int i = 0; i < response.body().getPayload().size(); i++) {


                                id.add(String.valueOf(response.body().getPayload().get(i).getId()));
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
                                rateofinterest.setText(response.body().getPayload().get(i).getRoi() + " %");
                                tenure.setText(response.body().getPayload().get(i).getTenure() + " days");




                                if (response.body().getPayload().get(i).getUpdate_price().equals("NA")) {
                                    et_total_qty.setText(String.valueOf(temp));
                                    String number1 = response.body().getPayload().get(i).getTotal_price();

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat(mDecimalFormatter);

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);
                                } else {
                                    tempmodd = tempmod + Integer.valueOf(response.body().getPayload().get(i).getUpdate_quantity());
                                    tempmod = tempmodd;

                                    tempmodifypricee = tempmodifyprice + Integer.valueOf(response.body().getPayload().get(i).getUpdate_totl_prc());
                                    tempmodifyprice = tempmodifypricee;

                                    et_total_qty.setText(String.valueOf(tempmod));

                                    String number1 = String.valueOf(tempmodifyprice);

                                    double amount = Double.parseDouble(number1);

                                    DecimalFormat formatter = new DecimalFormat(mDecimalFormatter);

                                    String formatted = formatter.format(amount);

                                    et_total_price.setText(rupee_symbol + formatted);


                                }


                                if (response.body().getPayload().size() - 1 == i) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                                            LoanDetails_Activity.this, LinearLayoutManager.VERTICAL, false
                                    );

                                    product_recyclerview.setLayoutManager(layoutManager);
                                    loanPoAllDetailsAdapter = new LoanPoAllDetails_Adapter(getApplicationContext(),
                                            id, po_id, date, brand, dealer_id, dealer_name, product, prodt_quantity,
                                            update_quantity, prodt_price, update_price, total_price,
                                            update_totl_prc, financer, status, invoicefile);
                                    product_recyclerview.setAdapter(loanPoAllDetailsAdapter);


                                }
                            }
                        }
                    } else {
                        custPrograssbar.closePrograssBar();
                        Toast.makeText(LoanDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanPoAllDetails_MODEL> call, Throwable t) {


                Toast.makeText(LoanDetails_Activity.this, networkError, Toast.LENGTH_LONG).show();
            }

        });


    }


}