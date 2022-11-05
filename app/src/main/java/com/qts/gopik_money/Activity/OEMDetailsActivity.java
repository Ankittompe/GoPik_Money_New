package com.qts.gopik_money.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qts.gopik_money.Model.LoanLimit_Details_MODEL;
import com.qts.gopik_money.Pojo.OEMDetailsPOJO;
import com.qts.gopik_money.R;
import com.qts.gopik_money.Retro.NetworkHandler;
import com.qts.gopik_money.Retro.RestApis;
import com.qts.gopik_money.Utils.ShowToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OEMDetailsActivity extends AppCompatActivity {

    EditText mEdtBusinessYrs, mEdtOEMName, mEdtOEMPercent;
    Boolean mIsBusinessYrs = false;
    Boolean mIsOEMName = false;
    Boolean mIsOEMPercent = false;
    Button mBtnSubmitOEMDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oemdetails);
        initData();
    }

    private void initData() {
        mEdtBusinessYrs = findViewById(R.id.edtBusinessYrs);
        mEdtOEMName = findViewById(R.id.edtOEMName);
        mEdtOEMPercent = findViewById(R.id.edtOEMPercent);
        mBtnSubmitOEMDetails = findViewById(R.id.btnSubmitOEMDetails);

        mBtnSubmitOEMDetails.setOnClickListener(new SubmitAllDetailsClickListener());
    }

    private class SubmitAllDetailsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (mEdtBusinessYrs.getText().toString().trim().isEmpty()) {
                new ShowToast().ShowToastHere(OEMDetailsActivity.this, "Business years should not Empty");
                mIsBusinessYrs = false;
            } else {
                mIsBusinessYrs = true;
            }

            if (mEdtOEMName.getText().toString().trim().isEmpty()) {
                new ShowToast().ShowToastHere(OEMDetailsActivity.this, "OEM Name should not Empty");
                mIsOEMName = false;
            } else {
                mIsOEMName = true;
            }

            if (mEdtOEMPercent.getText().toString().trim().isEmpty()) {
                new ShowToast().ShowToastHere(OEMDetailsActivity.this, "OEM Percentage should not Empty");
                mIsOEMPercent = false;
            } else {
                mIsOEMPercent = true;
            }

            if (mIsBusinessYrs) {
                if (mIsBusinessYrs) {
                    if (mIsBusinessYrs) {
                        SubmitOEMDetails(
                                mEdtBusinessYrs.getText().toString().trim(),
                                mEdtOEMName.getText().toString().trim(),
                                mEdtOEMPercent.getText().toString().trim()
                        );
                    }
                }
            }
        }

        private void SubmitOEMDetails(String mYears, String mOEMName, String mOEMPer) {
            String mUserType, mUserCode;
            mUserType = SharedPref.getStringFromSharedPref(AppConstants.BRANDUSER, OEMDetailsActivity.this);
            mUserCode = SharedPref.getStringFromSharedPref(AppConstants.USER_CODE, OEMDetailsActivity.this);
            OEMDetailsPOJO pojo = new OEMDetailsPOJO(mUserCode, mYears, mOEMName, mOEMPer);
            RestApis restApis = NetworkHandler.getRetrofit().create(RestApis.class);
            if (mUserType.equals("SubDealer")) {
                Call<LoanLimit_Details_MODEL> call = restApis.Subdealerbusinessyear(pojo);
                call.enqueue(new Callback<LoanLimit_Details_MODEL>() {
                    @Override
                    public void onResponse(Call<LoanLimit_Details_MODEL> call, Response<LoanLimit_Details_MODEL> response) {
                        if (response.body() != null&&response.body().getCode()==200) {
                        }
                    }

                    @Override
                    public void onFailure(Call<LoanLimit_Details_MODEL> call, Throwable t) {
                    }
                });
            } else if (mUserType.equals("Dealer")) {
                Call<LoanLimit_Details_MODEL> call = restApis.Dealerbusinessyear(pojo);
                call.enqueue(new Callback<LoanLimit_Details_MODEL>() {
                    @Override
                    public void onResponse(Call<LoanLimit_Details_MODEL> call, Response<LoanLimit_Details_MODEL> response) {
                        if (response.body() != null) {
                            Log.e("SubmitOEMDetails ", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoanLimit_Details_MODEL> call, Throwable t) {

                    }
                });
            }
        }
    }

}